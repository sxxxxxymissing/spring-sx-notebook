package com.sxxxxx.message.center.core.config;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DingTalkConfiguration implements MessageConfiguration {

    @NotBlank(message = "accessKeyId, 不可为空")
    private String accessKeyId;

    @NotBlank(message = "accessKeySecret, 不可为空")
    private String accessKeySecret;

    @NotNull(message = "agentId, 不可为空")
    private Long agentId;
}
