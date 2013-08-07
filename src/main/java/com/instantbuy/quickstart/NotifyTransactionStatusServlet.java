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

import com.google.wallet.online.jwt.JwtRequests;
import com.google.wallet.online.jwt.JwtResponseContainer;
import com.google.wallet.online.jwt.TransactionStatusNotification;
import com.google.wallet.online.jwt.util.GsonHelper;
import com.google.wallet.online.jwt.util.JwtGenerator;
import net.oauth.jsontoken.Clock;
import net.oauth.jsontoken.SystemClock;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to send a notify transaction status
 */
public class NotifyTransactionStatusServlet extends HttpServlet {


  private static final Logger logger = Logger.getLogger(NotifyTransactionStatusServlet.class
      .getSimpleName());

  /**
   * Class representing the post body (after being converted from JSON)
   */
  static class Request {
    /**
     * The fullWalletResponse Jwt sent by the client
     */
    String jwt;
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    BufferedReader reader = null;

    try {

      reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
      Request req = GsonHelper.getGson().fromJson(reader, Request.class);

      if (req.jwt == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameter jwt");
        return;
      }

      JwtResponseContainer jwtResponse = JwtGenerator.jwtToJava(JwtResponseContainer.class, req.jwt,
          Config.getMerchantSecret());

      Clock clock = new SystemClock();
      // Get the full wallet jwt and create a NotifyTransactionStatus
      JwtRequests.TransactionStatusContainer notifyStatus = JwtRequests.newTransactionStatusBuilder()
          .setIss(Config.getMerchantId())
          .setIat(TimeUnit.SECONDS.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS))
          .setExp(TimeUnit.SECONDS.convert(clock.now().plus(JwtGenerator.EXPIRATION_DELTA)
              .getMillis(), TimeUnit.MILLISECONDS))
          .setRequest(TransactionStatusNotification.newBuilder()
            .setGoogleTransactionId(jwtResponse.getResponse().getGoogleTransactionId())
            .setStatus(TransactionStatusNotification.Status.SUCCESS)
            .build()
          ).build();

      response.getWriter().write(JwtGenerator.javaToJWT(notifyStatus, Config.getMerchantSecret()));

    } catch (SignatureException e) {

      logger.log(Level.SEVERE, "Signature exception ", e);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

    } catch (InvalidKeyException e) {

      logger.log(Level.SEVERE, "Invalid key exception", e);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

    } finally {
      if (reader != null) {
        reader.close();
      }
    }
  }
}
