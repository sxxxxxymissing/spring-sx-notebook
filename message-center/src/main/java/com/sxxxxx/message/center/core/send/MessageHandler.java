package com.sxxxxx.message.center.core.send;

import com.sxxxxx.message.center.core.config.MessageConfiguration;
import com.sxxxxx.message.center.core.context.MessageContext;
import com.sxxxxx.message.center.core.exception.MessageException;

public interface MessageHandler {

    void handle() throws MessageException;

    void setConfiguration(MessageConfiguration messageConfiguration);

    void setHandlerConfiguration(MessageConfiguration messageConfiguration);

    void setMessageContext(MessageContext messageContext);
}
