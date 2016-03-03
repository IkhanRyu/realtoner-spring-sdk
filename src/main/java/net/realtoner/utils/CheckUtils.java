package net.realtoner.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author RyuIkHan
 */
public class CheckUtils {

    /**
     * @param str string
     * @return indicates whether given string is empty or not
     */
    public static boolean isEmptyString(String str) {
        return str == null || str.trim().equalsIgnoreCase("");
    }

    /**
     *
     * */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1.trim().equalsIgnoreCase(str2.trim());
    }

    /**
     *
     * */
    public static boolean isEmptyList(List list) {
        return list == null || list.size() == 0;
    }

    /**
     *
     * */
    public static boolean isEmptyArray(Object[] arr) {
        return arr == null || arr.length == 0;
    }

    private static final String EMAIL_REG_EXP = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     *
     * */
    public static boolean isValidEmail(String email) {
        return !isEmptyString(email) && MatchUtils.matches(email, EMAIL_REG_EXP);
    }

    private static String URL_REG_EXP = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    /**
     *
     * */
    public static boolean isValidURL(String url){
        return !isEmptyString(url) && MatchUtils.matches(url, URL_REG_EXP);
    }

    public static boolean isEqualWithConvertingToMD5(String unConverted, String converted) {

        MessageDigest md;
        String encodedPassword;

        try {
            md = MessageDigest.getInstance("MD5");
            md.update(unConverted.getBytes());

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            encodedPassword = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            return false;
        }

        return converted.equals(encodedPassword);
    }
}
