package net.prismarray.openhivebedwars.config;

/**
 * This exception is thrown, if a provided config file deviates from the expected contents in a way that can not be
 * compensated by omissions or default values.
 */
public class ConfigValidationException extends RuntimeException {

    public ConfigValidationException() {
        super();
    }

    public ConfigValidationException(String message) {
        super(message);
    }
}
