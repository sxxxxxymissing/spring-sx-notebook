package com.sxxxxx.message.center.demo;

import com.sxxxxx.message.center.core.config.EmailConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = "short.message.conf", havingValue = "true")
public class ShortMessage {

    @Bean
    public EmailConfig initializationEamil() {
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
