package ink.honp.sample.common.entity;

import ink.honp.sample.common.enums.ErrorCode;
import ink.honp.sample.common.enums.SuccessCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 统一数据封装
 * @author jeff chen
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
public class Response<T>  {

    /**
     * 响应状态
     */
    private String code;

    /**
     * 响应提示
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;


    public boolean isSuccess() {
        return SuccessCode.SUCCESS.getCode().equals(code);
    }


    /**
     * 成功响应
     * @param data 数据
     * @param <T> 数据类型
     * @return 成功响应
     */
    public static <T> Response<T> of(T data) {
        Response<T> response = new Response<>();
        response.setCode(SuccessCode.SUCCESS.getCode());
        response.setMessage(SuccessCode.SUCCESS.getMessage());
        response.setData(data);

        return response;
    }

    /**
     * 无数据成功响应
     */
    public static <T> Response<T> success() {
        Response<T> response = new Response<>();
        response.setCode(SuccessCode.SUCCESS.getCode());
        response.setMessage(SuccessCode.SUCCESS.getMessage());

        return response;
    }

    /**
     * 错误响应
     * @param errCode      错误状态码
     * @param errMessage   错误提示
     * @param <T>       -
     * @return 错误响应
     */
    public static <T> Response<T> error(String errCode, String errMessage) {

        Response<T> response = new Response<>();
        response.setCode(errCode);
        response.setMessage(errMessage);

        return response;
    }

    /**
     * 错误响应
     * @param error   错误状态码
     * @param <T>       -
     * @return 错误响应
     */
    public static <T> Response<T> error(ErrorCode error) {

        Response<T> response = new Response<>();
        response.setCode(error.getCode());
        response.setMessage(error.getMessage());

        return response;
    }

}
