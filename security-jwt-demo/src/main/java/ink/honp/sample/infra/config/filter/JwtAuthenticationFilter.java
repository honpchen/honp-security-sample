package ink.honp.sample.infra.config.filter;

import ink.honp.sample.common.util.JwtUtil;
import ink.honp.sample.infra.auth.UserInfo;
import ink.honp.sample.infra.config.JwtKeys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jeffchen
 * @date 2023/11/02 15:54
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            UsernamePasswordAuthenticationToken authRequest = convert(request);
            if (authRequest == null) {
                this.logger.trace("未处理身份验证请求，accessToken 过期或无效");
                chain.doFilter(request, response);
                return;
            }
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authRequest);
            SecurityContextHolder.setContext(context);

            onSuccessfulAuthentication(request, response, authRequest);
        } catch (AuthenticationException ex) {
            SecurityContextHolder.clearContext();
            this.logger.debug("Failed to process authentication request", ex);
            onUnsuccessfulAuthentication(request, response, ex);
            return;
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken convert(HttpServletRequest request) {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }

        UserInfo userInfo = JwtUtil.parseToken(JwtKeys.PUBLIC_KEY, accessToken, UserInfo.class);
        if (null == userInfo) {
            return null;
        }

        List<GrantedAuthority> authorities = userInfo.getRoles()
                .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(userInfo, null, authorities);
    }
}
