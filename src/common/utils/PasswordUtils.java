package common.utils;

import java.security.MessageDigest;

public class PasswordUtils {

    public static String sha256Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] b = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte x : b) {
                sb.append(String.format("%02x", x));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(String plain, String hashedHex) {
        if (plain == null || hashedHex == null) return false;
        return sha256Hex(plain).equals(hashedHex);
    }
}
