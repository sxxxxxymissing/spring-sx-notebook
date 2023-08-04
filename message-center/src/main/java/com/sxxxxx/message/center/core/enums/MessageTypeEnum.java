package com.sxxxxx.message.center.core.enums;

import com.sxxxxx.message.center.core.cache.EnumCache;
import com.sxxxxx.message.center.core.exception.EnumCacheException;
import com.sxxxxx.message.center.core.send.MessageHandler;
import com.sxxxxx.message.center.core.send.handler.DingTalkHandler;
import com.sxxxxx.message.center.core.send.handler.EmailHandler;
import com.sxxxxx.message.center.core.send.handler.ShortMsgHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public enum MessageTypeEnum {

//    All("all", null),

    STATION("stationMessage", null),

    DING_TALK("dingTalkMessage", DingTalkHandler.class),

    ALIYUN_SHORT_MSG("aliyunShortMessage", ShortMsgHandler.class),

    EMAIL("emailMessage", EmailHandler.class);

    private final String type;

    private final Class<? extends MessageHandler> messageHandler;

    MessageTypeEnum(String type, Class<? extends MessageHandler> messageHandler) {
        this.type = type;
        this.messageHandler = messageHandler;
    }

    static {
        try {
            // 通过名称构建缓存,通过EnumCache.findByName(StatusEnum.class,"SUCCESS",null);调用能获取枚举
            EnumCache.registerByName(MessageTypeEnum.class, MessageTypeEnum.values());
            // 通过code构建缓存,通过EnumCache.findByValue(StatusEnum.class,"S",null);调用能获取枚举
            EnumCache.registerByValue(MessageTypeEnum.class, MessageTypeEnum.values(), MessageTypeEnum::getType);
        }
        catch (EnumCacheException e) {
            log.error(e.getMessage());
        }
    }

}
