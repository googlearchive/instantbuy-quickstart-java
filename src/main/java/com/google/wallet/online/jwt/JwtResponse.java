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

package com.google.wallet.online.jwt;

/**
 * A simple class to represent a JWTResponse
 */
public class JwtResponse {

  private String googleTransactionId;
  private String merchantTransactionId;
  private String email;

  private PayResponse pay;
  private ShipResponse ship;

  public JwtResponse() {
    // Empty constructor used in Gson conversion of JSON -> Java Objects    
  }

  public String getGoogleTransactionId() {
    return googleTransactionId;
  }

  public String getMerchantTransactionId() {
    return merchantTransactionId;
  }

  public PayResponse getPay() {
    return pay;
  }

  public ShipResponse getShip() {
    return ship;
  }

  public String getEmail() {
    return email;
  }
}
