package site.wetsion.framework.mitosis.common.exception;

/**
 * @author wetsion
 * @date 2022-05-13 00:42
 */
public class InvalidDataException extends RuntimeException  {
    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataException(Throwable cause) {
        super(cause);
    }
}
