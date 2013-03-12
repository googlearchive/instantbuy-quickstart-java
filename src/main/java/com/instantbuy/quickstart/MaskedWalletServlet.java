/**
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in
 * compliance with the License.You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.  See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.instantbuy.quickstart;

import com.google.gson.Gson;
import com.google.wallet.online.jwt.JwtRequests;
import com.google.wallet.online.jwt.MaskedWalletRequest;
import com.google.wallet.online.jwt.Pay;
import com.google.wallet.online.jwt.Ship;
import com.google.wallet.online.jwt.util.JwtGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple class that creates a MaskedWallet request based on the parameters
 * namely estimatedTotalPrice and currencyCode
 */
public class MaskedWalletServlet extends HttpServlet {

  private static final Logger logger = Logger.getLogger(MaskedWalletServlet.class.getSimpleName());

  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.00");
  /**
   * Class associated with the JSON Post body
   */
  public static class Request {
    String estimatedTotalPrice;
    String currencyCode;
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws
      IOException, ServletException {

    InputStreamReader reader = null;

    try {
      reader = new InputStreamReader(request.getInputStream(), "UTF-8");
      // Parse the req from the POST body
      Request req = new Gson().fromJson(reader, Request.class);

      if (!isValid(req.estimatedTotalPrice) && req.currencyCode == null) {
        response.sendError(400, "Missing required parameters");
        return;
      }

      response.setContentType("text/plain");

      // Create a masked wallet request
      JwtRequests.MaskedWalletContainer result = JwtRequests.newMaskedWalletBuilder()
          .setAud(JwtRequests.DEFAULT_AUDIENCE)
          .setIss(Config.getMerchantId())
          .setRequest(
              MaskedWalletRequest.newBuilder()
                  .setClientId(Config.getOauthClientId())
                  .setMerchantName(Config.getMerchantName())
                  .setOrigin(Config.getDomain(request))
                  .setPhoneNumberRequired(true)
                  .setPay(
                      Pay.newBuilder()
                          .setEstimatedTotalPrice(req.estimatedTotalPrice)
                          .setCurrencyCode(req.currencyCode).build()
                  )
                  .setShip(new Ship())
                  .build()
          )
          .build();

      String generatedJwt = JwtGenerator.javaToJWT(result, Config.getMerchantSecret());

      PrintWriter out = response.getWriter();
      out.write(generatedJwt);

    } catch (InvalidKeyException ex) {
      // this is an Internal error
      logger.log(Level.SEVERE, "Invalid key", ex);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } catch (SignatureException ex) {
      // this is an Internal error
      logger.log(Level.SEVERE, "SignatureException", ex);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

    } finally {
      if (reader != null) {
        reader.close();
      }
    }
  }

  private boolean isValid(String estimatedTotalPrice) {
    try {
      return DECIMAL_FORMAT.parse(estimatedTotalPrice) != null;
    } catch (Exception ex) {
      return false;
    }
  }
}
