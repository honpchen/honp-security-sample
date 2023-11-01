package ink.honp.sample.common.exception;


import ink.honp.sample.common.enums.CommonErrorCode;

/**
 * 系统异常
 * @author jeff chen
 * @since 1.0.0
 */
public class SystemException extends BaseException {
    private static final long serialVersionUID = 5553075792435630816L;

    public SystemException() {
        super(CommonErrorCode.SYSTEM_ERROR);
    }

    public SystemException(String message) {
        super(CommonErrorCode.SYSTEM_ERROR.getCode(), message);
    }

    public SystemException(String message, Throwable cause) {
        super(CommonErrorCode.SYSTEM_ERROR.getCode(), message, cause);
    }

    public SystemException(Throwable cause) {
        super(CommonErrorCode.SYSTEM_ERROR, cause);
    }
}
