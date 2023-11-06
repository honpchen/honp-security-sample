package ink.honp.sample.config;

import ink.honp.sample.handler.CustomSaml2AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.saml2.provider.service.authentication.OpenSamlAuthenticationProvider;
import org.springframework.security.saml2.provider.service.authentication.OpenSamlAuthenticationRequestFactory;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.servlet.filter.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.saml2.provider.service.servlet.filter.Saml2WebSsoAuthenticationRequestFilter;
import org.springframework.security.saml2.provider.service.web.DefaultRelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.DefaultSaml2AuthenticationRequestContextResolver;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jeff chen
 * @since 2023-11-04 17:44
 */
@Configuration
public class SecurityConfiguration {


    private final RelyingPartyRegistrationRepository relyingPartyRegistrationRepository;

    public SecurityConfiguration(RelyingPartyRegistrationRepository relyingPartyRegistrationRepository) {
        this.relyingPartyRegistrationRepository = relyingPartyRegistrationRepository;
    }

    /**
     * Spring 新版本中，推荐使用组件化的方式实现配置，不推荐旧的实现方式(extends {@link WebSecurityConfigurerAdapter})
     * @param http http 安全配置
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager)
            throws Exception {
        return http
                .authorizeRequests(req -> req.antMatchers("/saml2/**", "/login/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(saml2WebSsoAuthenticationRequestFilter())
                .addFilter(saml2WebSsoAuthenticationFilter())
                .csrf(AbstractHttpConfigurer::disable)
//                .exceptionHandling(exp -> exp
//                        .authenticationEntryPoint(authenticationEntryPoint())
//                        .accessDeniedHandler(accessDeniedHandler()))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private Saml2WebSsoAuthenticationRequestFilter saml2WebSsoAuthenticationRequestFilter() {
        Converter<HttpServletRequest, RelyingPartyRegistration> relyingPartyRegistrationResolver =
                new DefaultRelyingPartyRegistrationResolver(this.relyingPartyRegistrationRepository);

        DefaultSaml2AuthenticationRequestContextResolver requestResolver = new DefaultSaml2AuthenticationRequestContextResolver(relyingPartyRegistrationResolver);
        return new Saml2WebSsoAuthenticationRequestFilter(requestResolver, new OpenSamlAuthenticationRequestFactory());
    }


    private Saml2WebSsoAuthenticationFilter saml2WebSsoAuthenticationFilter() {
        Saml2WebSsoAuthenticationFilter authenticationFilter =
                new Saml2WebSsoAuthenticationFilter(relyingPartyRegistrationRepository);
        authenticationFilter.setAuthenticationSuccessHandler(new CustomSaml2AuthenticationSuccessHandler());
        authenticationFilter.setAuthenticationManager(new ProviderManager(new OpenSamlAuthenticationProvider()));
        return authenticationFilter;
    }



}
