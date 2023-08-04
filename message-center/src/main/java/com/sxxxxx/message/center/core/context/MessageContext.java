package com.sxxxxx.message.center.core.context;

import com.sxxxxx.message.center.core.config.UserConfiguration;
import lombok.Data;

@Data
public class MessageContext {

    private String messageTitle;

    private String messageBody;

    private String hyperlink;

    private String url;

    private String msgType;

    private boolean async;

    private UserConfiguration userConfiguration;

    public MessageContext() {

    }

    public MessageContext(String messageTitle, String messageBody, String hyperlink, String url, String msgType, boolean async, UserConfiguration userConfiguration) {
        this.messageTitle = messageTitle;
        this.messageBody = messageBody;
        this.hyperlink = hyperlink;
        this.url = url;
        this.msgType = msgType;
        this.async = async;
        this.userConfiguration = userConfiguration;
    }
}
