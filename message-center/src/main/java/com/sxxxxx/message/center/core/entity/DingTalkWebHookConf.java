package com.sxxxxx.message.center.core.entity;

import lombok.Data;

@Data
public class DingTalkWebHookConf {

    private String receiver;

    private String webhook;

    private String secretKey;
}
