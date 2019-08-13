package com.internship.tmontica.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA512Util {

    public static String encryptSHA512(String target) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-512");
            sha.update(target.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : sha.digest()) stringBuffer.append(Integer.toHexString(0xff & b));
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
