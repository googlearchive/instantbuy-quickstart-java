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

import java.util.Collection;

/**
 * 
 * This object contains payment instrument data
 * 
 * @author pying
 *
 */
public class PayResponse {

  private String estimatedTotalPrice;
  private String currencyCode;
  private String objectId;
  private Integer expirationMonth;
  private Integer expirationYear;
  private Collection<String> description;
  private Address billingAddress;

  public PayResponse() {
    // Empty constructor used in Gson conversion of JSON -> Java Objects
  }

  public String getEstimatedTotalPrice() {
    return estimatedTotalPrice;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public String getObjectId() {
    return objectId;
  }

  public Integer getExpirationMonth() {
    return expirationMonth;
  }

  public Integer getExpirationYear() {
    return expirationYear;
  }

  public Collection<String> getDescription() {
    return description;
  }

  public Address getBillingAddress() {
    return billingAddress;
  }
}
