package ink.honp.sample.infra.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ink.honp.sample.common.Response;
import ink.honp.sample.common.enums.CommonErrorCode;
import ink.honp.sample.common.util.JwtUtil;
import ink.honp.sample.infra.auth.CustomUserDetails;
import ink.honp.sample.infra.auth.TokenInfo;
import ink.honp.sample.infra.auth.UserInfo;
import ink.honp.sample.infra.config.filter.CustomUserPasswordAuthenticationFilter;
import ink.honp.sample.infra.config.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jeffchen
 * @date 2023/11/01 16:59
 */
@Configuration
public class SecurityConfiguration {

    private final ObjectMapper objectMapper;

    public SecurityConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Spring 新版本中，推荐使用组件化的方式实现配置，不推荐旧的实现方式(extends {@link WebSecurityConfigurerAdapter})
     * @param http http 安全配置
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager)
            throws Exception {
        return http
                .authorizeRequests(req -> req.antMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(customUserPasswordAuthenticationFilter(authenticationManager))
                .addFilterBefore(jwtAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exp -> exp
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler()))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
    @Bean
    public CustomUserPasswordAuthenticationFilter customUserPasswordAuthenticationFilter(
            AuthenticationManager authenticationManager) {

        CustomUserPasswordAuthenticationFilter authenticationFilter =
                new CustomUserPasswordAuthenticationFilter(authenticationManager);

        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());

        return authenticationFilter;
    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationManager authenticationManager) {

        return new JwtAuthenticationFilter(authenticationManager);
    }


    private AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            UserInfo userInfo = new UserInfo();
            userInfo.setId(userDetails.getId());
            userInfo.setUserName(userDetails.getPassword());
            userInfo.setRoles(roles);

            String accessToken = JwtUtil.generateToken(JwtKeys.PRIVATE_KEY, userInfo);
            TokenInfo tokenInfo = new TokenInfo();
            tokenInfo.setUserId(userDetails.getId());
            tokenInfo.setUsername(userDetails.getUsername());
            tokenInfo.setAccessToken(accessToken);

            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().println(objectMapper.writeValueAsString(Response.of(tokenInfo)));
        };
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, authentication) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().println(objectMapper.writeValueAsString(
                    Response.error(CommonErrorCode.NOT_AUTH.getCode(), "用户名或密码错误")));
        };
    }

    // 实现 AccessDeniedHandler 接口，返回 403 错误
    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, authentication) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().println(objectMapper.writeValueAsString(
                    Response.error(CommonErrorCode.NOT_AUTH.getCode(), "access denied")));
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().println(objectMapper.writeValueAsString(
                    Response.error(CommonErrorCode.NOT_AUTH)));
        };
    }

}
