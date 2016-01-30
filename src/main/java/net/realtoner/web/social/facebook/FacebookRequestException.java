package net.realtoner.web.social.facebook;

/**
 *
 * @author RyuIkHan
 */
public class FacebookRequestException extends Exception{

    public FacebookRequestException() {
    }

    public FacebookRequestException(String message) {
        super(message);
    }

    public FacebookRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacebookRequestException(Throwable cause) {
        super(cause);
    }

    public FacebookRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
