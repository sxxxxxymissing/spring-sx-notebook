package com.sxxxxx.server.demo.message.conf;

import com.sxxxxx.message.center.core.config.EmailConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MessBeanConfiguration {

    @Bean
    public EmailConfig initializationEamil() {
        EmailConfig configuration = new EmailConfig();
        configuration.setHost("smtp.qq.com");
        //邮箱
        configuration.setUsername("xxxxxxxq.com");
        //授权码
        configuration.setPassword("xxxxxx");
        Map<String, String> source = new HashMap<>();
        source.put("mail.smtp.auth", "true");
        source.put("mail.smtp.starttls.enable", "true");
        source.put("mail.smtp.starttls.required", "true");
        configuration.setProperties(source);
        return configuration;
    }
}
