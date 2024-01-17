package com.sxxxxx.message.center.core.config;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.springframework.util.Base64Utils;

@Data
public class EmailConfig implements MessageConfiguration {

    @NotBlank(message = "host, 不可为空")
    private String host;

    @NotBlank(message = "username, 不可为空")
    private String username;

    @NotBlank(message = "password, 不可为空")
    private String password;

    private Integer port;

    private String protocol = "smtp";

    private String encoding = "UTF-8";

    private Map<String, String> properties;

    @Override
    public void decryptBase64() {
        host = new String(Base64Utils.decode(host.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        username = new String(Base64Utils.decode(username.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        password = new String(Base64Utils.decode(password.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }
}
