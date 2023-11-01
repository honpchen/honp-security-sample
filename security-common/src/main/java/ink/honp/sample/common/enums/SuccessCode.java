package ink.honp.sample.common.enums;


import ink.honp.sample.common.entity.ResponseCode;

/**
 * @author jeff chen
 * @since 2023/3/27 21:33
 */
public enum SuccessCode implements ResponseCode {

    SUCCESS("00000", "success");

    private final String code;

    private final String message;

    SuccessCode(String code, String message) {
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
