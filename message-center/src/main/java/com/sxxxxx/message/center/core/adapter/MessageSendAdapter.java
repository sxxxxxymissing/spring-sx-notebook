package com.sxxxxx.message.center.core.adapter;

import java.util.List;


import com.sxxxxx.message.center.core.send.MessageHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageSendAdapter {

    private final List<MessageHandler> messageHandlers;

    public void sendMessage() {
        for (MessageHandler messageHandler : messageHandlers) {
            messageHandler.handle();
        }
    }

    public MessageSendAdapter(List<MessageHandler> messageHandlers) {
        this.messageHandlers = messageHandlers;
    }
}
