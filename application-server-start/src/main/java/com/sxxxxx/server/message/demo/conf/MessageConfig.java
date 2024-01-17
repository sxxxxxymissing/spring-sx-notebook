package com.sxxxxx.server.message.demo.conf;

import com.sxxxxx.message.center.core.config.EmailConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MessageConfig {

    @Bean
    public EmailConfig initializationEmailConfig() {
        EmailConfig configuration = new EmailConfig();
        configuration.setHost("smtp.qq.com");
        configuration.setUsername("xxxxxx@qq.com");
        configuration.setPassword("Password");
        Map<String, String> source = new HashMap<>();
        source.put("mail.smtp.auth", "true");
        source.put("mail.smtp.starttls.enable", "true");
        source.put("mail.smtp.starttls.required", "true");
        configuration.setProperties(source);
        return configuration;
    }

}
