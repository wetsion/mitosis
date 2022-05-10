package site.wetsion.framework.mitosis.common.exception;

/**
 * @author wetsion
 * @date 2022-05-11 00:50
 */
public class UnsupportedException extends RuntimeException {

    public UnsupportedException(String message) {
        super(message);
    }

    public UnsupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedException(Throwable cause) {
        super(cause);
    }
}
