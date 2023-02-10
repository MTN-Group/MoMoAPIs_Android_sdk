package com.momo.sdk.util;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

import java.nio.charset.Charset;

import okio.ByteString;

/** Factory for HTTP authorization credentials. */
public final class Credentials {
  private Credentials() {
  }

  /** Returns an auth credential for the Basic scheme. */
  public static String basic(String apiUser, String apiKey) {
    return basic(apiUser, apiKey, ISO_8859_1);
  }

  public static String basic(String apiUser, String apiKey, Charset charset) {
    String usernameAndPassword = apiUser + ":" + apiKey;
    String encoded = ByteString.encodeString(usernameAndPassword, charset).base64();
    return "Basic "+encoded;
  }
}
