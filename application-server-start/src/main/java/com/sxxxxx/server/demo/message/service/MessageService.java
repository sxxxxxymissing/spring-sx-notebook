package com.sxxxxx.server.demo.message.service;

import com.sxxxxx.message.center.core.config.EmailConfig;
import com.sxxxxx.message.center.core.config.UserConfiguration;
import com.sxxxxx.message.center.core.context.MessageContext;
import com.sxxxxx.message.center.core.entity.EmailAndPhoneConf;
import com.sxxxxx.message.center.core.enums.MessageTypeEnum;
import com.sxxxxx.message.center.core.factory.MessageCenterFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Resource
    private EmailConfig emailConfig;

    public void sendEmail(String title, String body, String link) {
        List<EmailAndPhoneConf> emails = new ArrayList<>();
        EmailAndPhoneConf email = new EmailAndPhoneConf();
        email.setEmail("xxxxxx.com");
        emails.add(email);
        UserConfiguration userConfiguration = new UserConfiguration(null, null, null, emails);
        MessageContext messageContext = new MessageContext(title, body, link,
                link, "type", false, userConfiguration);
        MessageCenterFactory messageCenterFactory = new MessageCenterFactory();
        messageCenterFactory.setType(MessageTypeEnum.EMAIL).setConfiguration(emailConfig, messageContext);
        messageCenterFactory.build().sendMessage();
    }
}
