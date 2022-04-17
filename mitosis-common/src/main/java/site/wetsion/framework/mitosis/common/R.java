package site.wetsion.framework.mitosis.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wetsion
 * @date 4/17/22
 */
@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = -1953635779264826856L;

    public static final Integer SUCCESS_CODE = 200;

    public static final String SUCCESS_MSG = "success";

    public static final String FAIL_MSG = "system error";

    public static final Integer FAIL_CODE = 501;

    private Integer code;

    private T data;

    private String message;

    public boolean isSuccess() {
        return SUCCESS_CODE.equals(code);
    }

    public R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public R(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> R<T> success(T data) {
        return new R<>(SUCCESS_CODE, SUCCESS_MSG, data);
    }

    public static <T> R success(T data, String message) {
        return new R<>(SUCCESS_CODE, message, data);
    }

    public static R fail(Integer code, String message) {
        return new R<>(code, message);
    }

    public static R sysError(String message) {
        return fail(FAIL_CODE, message);
    }

    public static R justFail() {
        return fail(FAIL_CODE, FAIL_MSG);
    }
}
