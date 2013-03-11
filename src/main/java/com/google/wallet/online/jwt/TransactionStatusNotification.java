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
 * Class that represents the Transaction Status Notification
 * sent to the API.
 */
public class TransactionStatusNotification {
  
  /**
   * Enumeration to define the payment processing status
   */
  public enum Status {
    SUCCESS, FAILURE
  }

  /**
   * Enumeration to define the failure reason
   */
  public enum Reason {
    BAD_CVC, BAD_CARD, DECLINED, OTHER
  }

  private String googleTransactionId;
  private String merchantTransactionId;

  private Status status;
  private Reason reason;
  private String detailedReason;

  public TransactionStatusNotification() {
    // Empty constructor used in Gson conversion of JSON -> Java Objects
  }

  private TransactionStatusNotification(Builder builder) {
    this.googleTransactionId = builder.googleTransactionId;
    this.merchantTransactionId = builder.merchantTransactionId;
    this.status = builder.status;
    this.reason = builder.reason;
    this.detailedReason = builder.detailedReason;
  }

  /**
   * Returns the defined status
   *
   * @return defined status
   */
  public Status getStatus() {
    return status;
  }

  /**
   * Returns the notification payment processing failure reason
   *
   * @return failure reason
   */
  public Reason getReason() {
    return reason;
  }

  public String getDetailedReason() {
    return detailedReason;
  }

  public String getGoogleTransactionId() {
    return googleTransactionId;
  }

  public String getMerchantTransactionId() {
    return merchantTransactionId;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Helper class to generate the TransactionStatusNotification
   */
  public static class Builder {
    private String googleTransactionId;
    private String merchantTransactionId;
    private Status status;
    private Reason reason;
    private String detailedReason;

    private Builder() {
    }

    public Builder setGoogleTransactionId(String googleTransactionId) {
      this.googleTransactionId = googleTransactionId;
      return this;
    }

    public Builder setMerchantTransactionId(String merchantTransactionId) {
      this.merchantTransactionId = merchantTransactionId;
      return this;
    }

    public Builder setStatus(Status status) {
      this.status = status;
      return this;
    }

    public Builder setReason(Reason reason) {
      this.reason = reason;
      return this;
    }

    public Builder setDetailedReason(String detailedReason) {
      this.detailedReason = detailedReason;
      return this;
    }

    public TransactionStatusNotification build() {
      // validate and return
      return new TransactionStatusNotification(this);
    }
  }
}
