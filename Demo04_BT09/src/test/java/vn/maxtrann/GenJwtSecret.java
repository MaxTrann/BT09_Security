package vn.maxtrann;

import java.util.Base64;
import java.security.SecureRandom;

public class GenJwtSecret {
  public static void main(String[] args) {
    byte[] key = new byte[32]; // 256-bit
    new SecureRandom().nextBytes(key);
    System.out.println(Base64.getEncoder().encodeToString(key));
  }
}

