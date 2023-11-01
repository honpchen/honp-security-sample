package ink.honp.sample.infra.config.handler;

import ink.honp.sample.common.entity.Response;
import ink.honp.sample.common.util.CollectionUtil;
import ink.honp.sample.common.util.JwtUtil;
import ink.honp.sample.common.util.ServletUtil;
import ink.honp.sample.infra.auth.TokenInfo;
import ink.honp.sample.infra.config.JwtKeys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author jeffchen
 * @date 2023/11/01 17:55
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Map<String, String> data = CollectionUtil.newHashMap();
        data.put("username", userDetails.getUsername());

        String accessToken = JwtUtil.generateToken(JwtKeys.PRIVATE_KEY, data);
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUsername(userDetails.getUsername());
        tokenInfo.setAccessToken(accessToken);

        ServletUtil.out(response, Response.of(tokenInfo));
    }
}
