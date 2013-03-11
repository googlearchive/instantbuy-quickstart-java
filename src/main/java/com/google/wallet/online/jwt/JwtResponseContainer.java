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
 * Generic response container for both masked and full wallet responses.
 * @author nageshs
 */
public class JwtResponseContainer extends BaseJwt {

  public static final String MASKED_WALLET = "google/wallet/online/masked/v2/response";
  public static final String FULL_WALLET   = "google/wallet/online/full/v2/response";

  private JwtResponse response;

  public JwtResponseContainer() {
    // Empty constructor used in Gson conversion of JSON -> Java Objects
  }

  public JwtResponse getResponse() {
    return response;
  }
}
