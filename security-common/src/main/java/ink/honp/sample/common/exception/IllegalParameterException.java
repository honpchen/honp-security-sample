package ink.honp.sample.common.exception;


import ink.honp.sample.common.enums.CommonErrorCode;

/**
 * 非法参数异常
 * @author jeff chen
 * @since 1.0.0
 */
public class IllegalParameterException extends BaseException{
    private static final long serialVersionUID = 5567500636199659470L;

    public IllegalParameterException(String message) {
        super(CommonErrorCode.ILLEGAL_PARAMETER.getCode(), message);
    }
}
