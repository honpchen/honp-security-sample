package ink.honp.sample.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author jeff chen
 * @since 2023-10-31 23:01
 */
@EnableWebSecurity(debug = true) // 启用 debug 模式，可以看到更多的日志信息
@SpringBootApplication
public class SecuritySampleAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecuritySampleAuthApplication.class, args);
    }
}
