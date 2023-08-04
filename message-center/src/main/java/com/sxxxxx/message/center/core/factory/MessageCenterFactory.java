package com.sxxxxx.message.center.core.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sxxxxx.message.center.core.adapter.MessageSendAdapter;
import com.sxxxxx.message.center.core.config.MessageConfiguration;
import com.sxxxxx.message.center.core.context.MessageContext;
import com.sxxxxx.message.center.core.enums.MessageTypeEnum;
import com.sxxxxx.message.center.core.exception.MessageException;
import com.sxxxxx.message.center.core.send.MessageHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageCenterFactory {

    private MessageTypeEnum messageTypeEnum;

    private final Map<String, MessageHandler> messageHandlers = new HashMap<>();

    public MessageCenterFactory setType(MessageTypeEnum messageTypeEnum) {
        this.messageTypeEnum = messageTypeEnum;
        return this;
    }

    public MessageSendAdapter build() {
        return new MessageSendAdapter(new ArrayList(this.messageHandlers.values()));
    }

    public MessageCenterFactory setConfiguration(MessageConfiguration messageConfiguration, MessageContext messageContext) {
        if (this.messageTypeEnum == null) {
            throw new MessageException("MessageCenterFactory need to configure this setType first before setConfiguration");
        }
        try {
            MessageHandler messageHandler = messageHandlers.get(messageTypeEnum.getType());
            if (messageHandler == null) {
                Class<? extends MessageHandler> messageHandlerClass = this.messageTypeEnum.getMessageHandler();
                messageHandler = messageHandlerClass.getConstructor().newInstance();
                this.messageHandlers.put(messageTypeEnum.getType(), messageHandler);
            }
            messageHandler.setConfiguration(messageConfiguration);
            messageHandler.setMessageContext(messageContext);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MessageException(e.getMessage());
        }
        return this;
    }
}
