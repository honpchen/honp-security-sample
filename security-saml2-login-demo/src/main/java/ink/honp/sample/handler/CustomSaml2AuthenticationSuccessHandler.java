package ink.honp.sample.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jeff chen
 * @since 2023-11-05 22:09
 */
@Slf4j
public class CustomSaml2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        log.info("SAML2 登录认证成功: {}", new ObjectMapper().writeValueAsString(authentication));

        response.setHeader("Authorization", "测试");
        response.sendRedirect("https://www.baidu.com");

    }
}
