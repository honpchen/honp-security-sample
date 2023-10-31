package ink.honp.sample.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author jeff chen
 * @since 2023-10-31 22:53
 */
@Configuration
public class SecurityConfiguration  {

    /**
     * Spring 新版本中，推荐使用组件化的方式实现配置，不推荐旧的实现方式(extends {@link WebSecurityConfigurerAdapter})
     * @param http http 安全配置
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests(req -> req.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .csrf(Customizer.withDefaults())
                .build();
    }


    /**
     * web 安全配置
     */
    @Bean
    public WebSecurityCustomizer webSecurity() {
        return web -> web.ignoring().mvcMatchers("/test/**"); // 忽略不必要安全检查
    }
}
