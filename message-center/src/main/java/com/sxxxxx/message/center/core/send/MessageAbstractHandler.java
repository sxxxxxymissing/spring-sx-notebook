package com.sxxxxx.message.center.core.send;

import com.sxxxxx.message.center.core.config.MessageConfiguration;
import com.sxxxxx.message.center.core.context.MessageContext;
import com.sxxxxx.message.center.core.exception.MessageException;
import com.sxxxxx.message.center.util.ValidateUtil;
import lombok.Data;

@Data
public abstract class MessageAbstractHandler implements MessageHandler {

    private MessageContext messageContext;

    public abstract Class<? extends MessageConfiguration> getMessageConfiguration();

    public void setConfiguration(MessageConfiguration messageConfiguration) {
        Class<? extends MessageConfiguration> configurationClass = getMessageConfiguration();
        if (!messageConfiguration.getClass().isAssignableFrom(getMessageConfiguration())) {
            throw new MessageException(this.getClass().getSimpleName() + "'s messageConfiguration should be of the specified type" + configurationClass.getSimpleName());
        }
        ValidateUtil.validate(messageConfiguration);
        this.setHandlerConfiguration(messageConfiguration);
    }
}
