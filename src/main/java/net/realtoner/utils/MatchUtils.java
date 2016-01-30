package net.realtoner.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author RyuIkHan
 */
public class MatchUtils {

    /**
     *
     * @param str
     * @param regExp
     * @param index
     * @return
     * */
    public static String removeMathchOneUsingIndex(String str , String regExp , int index){

        Matcher m = getMatcher(str , regExp);

        int count = 0;

        while(m.find()){

            if(count == index){

                String head = str.substring(0 , m.start());
                String tail = str.substring(m.end() , str.length());

                return head + tail;

            }else{
                count++;
            }
        }

        return str;
    }

    /**
     * @param str
     * @param regExp
     * @return
     */
    public static String removeLastMatchOne(String str, String regExp) {

        Matcher m = getMatcher(str , regExp);

        int start = -1 , end = -1;

        while(m.find()){
            start = m.start();
            end = m.end();
        }

        if(start != -1 && end != -1){

            String head = str.substring(0 , start);
            String tail = str.substring(end , str.length());

            str = head + tail;
        }

        return str;
    }

    public static boolean matches(String str , String regExp){

        return getMatcher(str , regExp).matches();
    }

    public static String find(String str , String regExp){

        return null;
    }

    public static Matcher getMatcher(String str , String regExp){

        return Pattern.compile(regExp).matcher(str);
    }

    private static final String EXTENSION_REG_EXP = "\\.[a-zA-Z0-9]{1,6}$";;

    /**
     *
     * @param fileName
     * */
    public static String removeExtensionFromFileName(String fileName){

        Matcher m = MatchUtils.getMatcher(fileName , EXTENSION_REG_EXP);

        if(m.find()){
            fileName = fileName.substring(0 , m.start());
        }

        return fileName;
    }

    /**
     *
     * @param fileName
     * */
    public static String extractExtensionFromFileName(String fileName){

        String fileExtension;

        Matcher m = MatchUtils.getMatcher(fileName, EXTENSION_REG_EXP);

        if(m.find()){
            fileExtension = fileName.substring(m.start() + 1 , m.end());

        }else{
            fileExtension = null;
        }

        return fileExtension;
    }
}
