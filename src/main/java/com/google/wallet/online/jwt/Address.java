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
 * Address Bean
 */
public class Address {

  private String name;
  private String address1;
  private String address2;
  private String address3;
  private String countryCode;
  private String city;
  private String state;
  private String postalCode;
  private String phoneNumber;
  private Boolean postBox;
  private String companyName;

  public Address() {
    // Empty constructor used in Gson conversion of JSON -> Java Objects
  }

  private Address(Builder builder) {
    this.name = builder.name;
    this.address1 = builder.address1;
    this.address2 = builder.address2;
    this.address3 = builder.address3;
    this.countryCode = builder.countryCode;
    this.city = builder.city;
    this.state = builder.state;
    this.postalCode = builder.postalCode;
    this.phoneNumber = builder.phoneNumber;
    this.postBox = builder.postBox;
    this.companyName = builder.companyName;
  }

  public String getName() {
    return name;
  }

  public String getAddress1() {
    return address1;
  }

  public String getAddress2() {
    return address2;
  }

  public String getAddress3() {
    return address3;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public Boolean getPostBox() {
    return postBox;
  }

  public String getCompanyName() {
    return companyName;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * A simple class to help generate the Address bean
   */
  public static class Builder {
    private String name;
    private String address1;
    private String address2;
    private String address3;
    private String countryCode;
    private String city;
    private String state;
    private String postalCode;
    private String phoneNumber;
    private Boolean postBox;
    private String companyName;

    private Builder() {
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setAddress1(String address1) {
      this.address1 = address1;
      return this;
    }

    public Builder setAddress2(String address2) {
      this.address2 = address2;
      return this;
    }

    public Builder setAddress3(String address3) {
      this.address3 = address3;
      return this;
    }

    public Builder setCountryCode(String countryCode) {
      this.countryCode = countryCode;
      return this;
    }

    public Builder setCity(String city) {
      this.city = city;
      return this;
    }

    public Builder setState(String state) {
      this.state = state;
      return this;
    }

    public Builder setPostalCode(String postalCode) {
      this.postalCode = postalCode;
      return this;
    }

    public Builder setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    public Builder setPostBox(Boolean postBox) {
      this.postBox = postBox;
      return this;
    }

    public Builder setCompanyName(String companyName) {
      this.companyName = companyName;
      return this;
    }

    public Address build() {
      // validate and return
      return new Address(this);
    }
  }
}
