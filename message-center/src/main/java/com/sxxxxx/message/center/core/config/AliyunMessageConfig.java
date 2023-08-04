package com.sxxxxx.message.center.core.config;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AliyunMessageConfig implements MessageConfiguration {

    @NotBlank(message = "accessKeyId, 不可为空")
    private String accessKeyId;

    @NotBlank(message = "accessKeySecret, 不可为空")
    private String accessKeySecret;

    @NotBlank(message = "signName, 不可为空")
    private String signName;

    @NotBlank(message = "templateCode, 不可为空")
    private String templateCode;
}
