package com.sxxxxx.message.center.core.config;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;

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

    @Override
    public void decryptBase64() {
        accessKeyId = new String(Base64Utils.decode(accessKeyId.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        accessKeySecret = new String(Base64Utils.decode(accessKeySecret.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }
}
