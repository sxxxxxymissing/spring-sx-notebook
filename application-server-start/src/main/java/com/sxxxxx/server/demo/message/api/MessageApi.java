package com.sxxxxx.server.demo.message.api;

import com.sxxxxx.server.demo.message.service.MessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/send/message")
public class MessageApi {

    @Resource
    private MessageService messageService;

    @RequestMapping("/email")
    public void sendEmail(String title, String body, String link) {
        messageService.sendEmail(title, body, link);
    }
}
