package ink.honp.sample.common.enums;

/**
 * @author jeff chen
 * @since 2023-04-03 10:08
 */
public interface ResponseCode {

    /**
     * 获取状态码
     * @return 状态码
     */
    String getCode();

    /**
     * 获取提示信息
     * @return 提示信息
     */
    String getMessage();
}
