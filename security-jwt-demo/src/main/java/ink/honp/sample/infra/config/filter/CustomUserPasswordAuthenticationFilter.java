package ink.honp.sample.infra.config.filter;

import ink.honp.sample.infra.config.handler.CustomAuthenticationSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jeffchen
 * @date 2023/11/01 17:06
 */
public class CustomUserPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomUserPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        AntPathRequestMatcher pathRequestMatcher = new AntPathRequestMatcher("/auth/login", "POST");

        setAuthenticationManager(authenticationManager);
        setRequiresAuthenticationRequestMatcher(pathRequestMatcher);
        setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        
        return super.attemptAuthentication(request, response);
    }
}
