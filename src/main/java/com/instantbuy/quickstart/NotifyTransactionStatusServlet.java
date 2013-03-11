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
          .setIat(System.currentTimeMillis()/1000)
          .setExp(clock.now().plus(JwtGenerator.EXPIRATION_DELTA).getMillis()/1000)
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