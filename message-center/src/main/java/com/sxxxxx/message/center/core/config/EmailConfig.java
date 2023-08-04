package com.sxxxxx.message.center.core.config;

import java.util.Map;

import javax.validation.constraints.NotBlank;

import lombok.Data;

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
}
