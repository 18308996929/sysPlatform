package com.sys.login;

import com.sys.login.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ：liyong
 * @date ：Created in 2022/9/16 17:24
 * @description：
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@SpringBootApplication
@EnableSwagger2
public class UacApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(UacApplication.class);
        app.run(args);
    }
}
