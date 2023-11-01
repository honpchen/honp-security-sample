package ink.honp.sample.common.exception;


import ink.honp.sample.common.enums.ErrorCode;

/**
 * 业务异常
 * @author jeff chen
 * @since 1.0.0
 */
public class BizException extends BaseException {
    private static final long serialVersionUID = -5879947061425399565L;

    public BizException(ErrorCode error) {
        super(error);
    }

    public BizException(ErrorCode error, Throwable cause) {
        super(error, cause);
    }

    public BizException(String errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public BizException(String errCode, String errMessage, Throwable cause) {
        super(errMessage, errMessage, cause);
    }

}
