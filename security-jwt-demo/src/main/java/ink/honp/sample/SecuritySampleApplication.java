package ink.honp.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author jeffchen
 * @date 2023/11/01 16:58
 */
@Slf4j
@EnableWebSecurity(debug = true)
@SpringBootApplication
public class SecuritySampleApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SecuritySampleApplication.class);
        app.setBannerMode(Banner.Mode.OFF);

        Environment env = app.run(args).getEnvironment();
        String applicationName = env.getProperty("spring.application.name");
        String port = env.getProperty("server.port");

        log.info("【{}:{}】服务启动成功", applicationName, port);
    }
}
