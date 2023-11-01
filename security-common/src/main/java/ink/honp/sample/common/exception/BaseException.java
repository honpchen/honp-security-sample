package ink.honp.sample.common.exception;

import ink.honp.sample.common.enums.ErrorCode;
import lombok.Getter;

/**
 * @author jeff chen
 * @since 1.0.0
 */
@Getter
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = -23413237930443948L;

    private final String errCode;

    public BaseException(ErrorCode error) {
        super(error.getMessage());
        this.errCode = error.getCode();
    }

    public BaseException(ErrorCode error, Throwable cause) {
        super(error.getMessage(), cause);
        this.errCode = error.getCode();
    }

    public BaseException(String errCode, String errMessage) {
        super(errMessage);
        this.errCode = errCode;
    }

    public BaseException(String errCode, String errMessage, Throwable cause) {
        super(errMessage, cause);
        this.errCode = errCode;
    }

}
