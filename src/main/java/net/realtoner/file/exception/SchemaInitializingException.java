package net.realtoner.file.exception;

/**
 * @author RyuIkHan
 */
public class SchemaInitializingException extends Exception{

    public SchemaInitializingException() {
    }

    public SchemaInitializingException(String message) {
        super(message);
    }

    public SchemaInitializingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchemaInitializingException(Throwable cause) {
        super(cause);
    }

    public SchemaInitializingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
