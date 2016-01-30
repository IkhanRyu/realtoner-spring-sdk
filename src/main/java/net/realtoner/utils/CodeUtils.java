package net.realtoner.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author RyuIkHan
 */
public class CodeUtils {

    private static final String HASH_ALGORITHM = "HmacSHA256";

    public static String decodeBySHA256(String keyStr , String content){
        SecretKeySpec key = new SecretKeySpec(keyStr.getBytes(), HASH_ALGORITHM);

        Mac mac;

        try {
            mac = Mac.getInstance(key.getAlgorithm());
            mac.init(key);

        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (InvalidKeyException e) {
            return null;
        }

        byte[] bytes;

        try {
            bytes = mac.doFinal(content.getBytes("ASCII"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        StringBuffer hash = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hash.append('0');
            }
            hash.append(hex);
        }

        return hash.toString();
    }
}
