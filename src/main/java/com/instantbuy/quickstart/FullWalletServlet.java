package com.instantbuy.quickstart;

import com.google.wallet.online.jwt.Cart;
import com.google.wallet.online.jwt.FullWalletRequest;
import com.google.wallet.online.jwt.JwtRequests;
import com.google.wallet.online.jwt.JwtResponseContainer;
import com.google.wallet.online.jwt.LineItem;
import com.google.wallet.online.jwt.util.GsonHelper;
import com.google.wallet.online.jwt.util.JwtGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Class that creates a full wallet request based on the parameters.
 */
public class FullWalletServlet extends HttpServlet {

  private static final Logger logger = Logger.getLogger(FullWalletServlet.class.getSimpleName());

  /**
   * Class representing the post body of this Servlet.
   */
  static class Request {
    Cart cart;
    String jwt;
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    BufferedReader reader = null;

    try {
      reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
      Request req = GsonHelper.getGson().fromJson(reader, Request.class);

      if (req.jwt == null || req.cart == null || req.cart.getLineItems() == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required post parameters");
        return;
      }

      response.setContentType("text/plain");

      // deserialize the jwt to a MaskedWallet response
      JwtResponseContainer jwtResponse = JwtGenerator.jwtToJava(JwtResponseContainer.class, req.jwt,
          Config.getMerchantSecret());

      Cart.Builder cartBuilder = Cart.newBuilder();

      for (LineItem item : req.cart.getLineItems()) {

        LineItem.Builder lineItemBuilder = item.toBuilder();

        cartBuilder.addLineItem(lineItemBuilder.build());
      }

      cartBuilder.setCurrencyCode(req.cart.getCurrencyCode());
      cartBuilder.setTotalPrice(req.cart.getTotalPrice());

      JwtRequests.FullWalletContainer fullWalletContainer = JwtRequests.newFullWalletBuilder()
          .setIss(Config.getMerchantId())
          .setRequest(FullWalletRequest.newBuilder()
            .setCart(cartBuilder.build())
            .setGoogleTransactionId(jwtResponse.getResponse().getGoogleTransactionId())
            .setClientId(Config.getOauthClientId())
            .setMerchantName(Config.getMerchantName())
            .setOrigin(Config.getDomain(request))
            .build()
          )
          .build();

      String fullWalletJwt = JwtGenerator.javaToJWT(fullWalletContainer,
          Config.getMerchantSecret());


      PrintWriter out = response.getWriter();
      out.write(fullWalletJwt);

    } catch (SignatureException e) {
      logger.log(Level.SEVERE, "Signature exception", e);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } catch (InvalidKeyException e) {
      logger.log(Level.SEVERE, "Invalid Key Exception", e);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } finally {
      if (reader != null) {
        reader.close();
      }
    }
  }
}
