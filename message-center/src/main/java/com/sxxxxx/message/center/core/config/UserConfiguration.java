package com.sxxxxx.message.center.core.config;

import java.util.List;


import com.sxxxxx.message.center.core.entity.DingTalkWebHookConf;
import com.sxxxxx.message.center.core.entity.EmailAndPhoneConf;
import lombok.Data;

@Data
public class UserConfiguration {

    private List<String> dingTalkUserId;

    private List<String> userPhoneNum;

    private List<DingTalkWebHookConf> dingTalkWebHookConf;

    private List<EmailAndPhoneConf> emailAndPhoneConf;


    public UserConfiguration() {

    }

    public UserConfiguration(List<String> dingTalkUserId, List<String> userPhoneNum, List<DingTalkWebHookConf> dingTalkWebHookConf, List<EmailAndPhoneConf> emailAndPhoneConf) {
        this.dingTalkUserId = dingTalkUserId;
        this.userPhoneNum = userPhoneNum;
        this.dingTalkWebHookConf = dingTalkWebHookConf;
        this.emailAndPhoneConf = emailAndPhoneConf;
    }
}
