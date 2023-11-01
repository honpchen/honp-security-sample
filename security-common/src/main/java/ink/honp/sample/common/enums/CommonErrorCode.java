package ink.honp.sample.common.enums;

/**
 * 通用错误码
 * @author jeff chen
 * @since 2023/3/27 21:42
 */
public enum CommonErrorCode implements ErrorCode {

    SYSTEM_ERROR("00001", "系统异常"),
    ILLEGAL_PARAMETER("00004", "非法参数"),
    NOT_AUTH("10000", "未认证"),
    FREQUENTLY("10401", "请求频繁"),
    ;

    private final String code;

    private final String message;

    CommonErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
