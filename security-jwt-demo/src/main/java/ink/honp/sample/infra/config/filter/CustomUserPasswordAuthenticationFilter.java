package ink.honp.sample.infra.config.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jeffchen
 * @date 2023/11/01 17:06
 */
public class CustomUserPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/auth/login",
            "POST");

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomUserPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
        setRequiresAuthenticationRequestMatcher(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            JsonNode jsonNode = objectMapper.readTree(request.getInputStream());
            JsonNode usernameNode = jsonNode.get("username");
            JsonNode passwordNode = jsonNode.get("password");
            if (null == usernameNode) {
                throw new BadCredentialsException("用户名不能为空");
            }

            if (null == passwordNode) {
                throw new BadCredentialsException("密码不能为空");
            }

            UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken
                    .unauthenticated(usernameNode.asText(), passwordNode.asText());

            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            throw new BadCredentialsException("认证失败");
        }
    }
}
