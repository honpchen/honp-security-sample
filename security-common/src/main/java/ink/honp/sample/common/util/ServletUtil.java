package ink.honp.sample.common.util;

import ink.honp.sample.common.enums.CommonErrorCode;
import ink.honp.sample.common.exception.BizException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jeff chen
 * @since 1.0.0
 */
public abstract class ServletUtil {

    public static void out(HttpServletResponse response, Object data) throws IOException {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.getWriter().print(JsonUtil.toStr(data));
    }

    /**
     * 获取 HttpServletRequest, 不支持异步获取
     * @return 成功返回 {@link HttpServletRequest}, 失败抛出 {@link BizException}
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            throw new BizException(CommonErrorCode.SYSTEM_ERROR);
        }

        return attributes.getRequest();
    }
}
