package com.sxxxxx.message.center.core.send.handler;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.sxxxxx.message.center.core.config.EmailConfig;
import com.sxxxxx.message.center.core.config.MessageConfiguration;
import com.sxxxxx.message.center.core.context.MessageContext;
import com.sxxxxx.message.center.core.entity.EmailAndPhoneConf;
import com.sxxxxx.message.center.core.exception.MessageException;
import com.sxxxxx.message.center.core.send.MessageAbstractHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailHandler extends MessageAbstractHandler {

    private EmailConfig emailConfig;

    @Override
    public Class<? extends MessageConfiguration> getMessageConfiguration() {
        return EmailConfig.class;
    }

    @Override
    public void handle() throws MessageException {
        List<EmailAndPhoneConf> emailAndPhoneConf = getMessageContext().getUserConfiguration().getEmailAndPhoneConf();
        if (CollectionUtils.isNotEmpty(emailAndPhoneConf)) {
            List<String> toEmails = emailAndPhoneConf.stream().map(EmailAndPhoneConf::getEmail)
                .collect(Collectors.toList());
            this.sendEmail(getMessageContext(), toEmails);
        }
    }

    @Override
    public void setHandlerConfiguration(MessageConfiguration messageConfiguration) {
        this.emailConfig = (EmailConfig) messageConfiguration;
    }

    public void sendEmail(MessageContext messageContext, List<String> toEmails) {
        JavaMailSender sender = this.getJavaMailSender();
        String[] emails = toEmails.toArray(new String[0]);
        try {
            if (StringUtils.isNotBlank(messageContext.getUrl()) && StringUtils.isNotBlank(messageContext.getHyperlink())) {
                MimeMessage message = sender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setFrom(emailConfig.getUsername());
                helper.setTo(emails);
                helper.setSubject(messageContext.getMessageTitle());
                helper.setText(this.initLinkBody(messageContext.getMessageBody(), messageContext.getUrl(), messageContext.getHyperlink()), true);
                sender.send(message);
            }
            else {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(emails);
                message.setSubject(messageContext.getMessageTitle());
                message.setText(messageContext.getMessageBody());
                sender.send(message);
            }
        }
        catch (MessagingException exception) {
            log.error(exception.getMessage(), exception);
            throw new MessageException(exception.getMessage());
        }
    }

    public String initLinkBody(String body, String url, String hyperlink) {
        body = body.replace(hyperlink, "<a href=\"" + url + "\" target=\"_blank\">" + hyperlink + "</a>\n");
        return "<html>\n" +
        "<style>\n" +
        "    .td{width:150px;height:70px}\n" +
        "</style>\n" +
        "<body>\n" +
                body +
        "</body>\n" +
        "</html>";
    }

    public JavaMailSender getJavaMailSender() {
        String username = emailConfig.getUsername();
        JavaMailSender javaMailSender = JavaMailSenderInstance.instance.get(username);
        if (javaMailSender == null) {
            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost(emailConfig.getHost());
            sender.setUsername(emailConfig.getUsername());
            sender.setPassword(emailConfig.getPassword());
            sender.setProtocol(emailConfig.getProtocol());
            if (emailConfig.getPort() != null) {
                sender.setPort(emailConfig.getPort());
            }
            if (emailConfig.getEncoding() != null) {
                sender.setDefaultEncoding(emailConfig.getEncoding());
            }
            if (!emailConfig.getProperties().isEmpty()) {
                Properties properties = new Properties();
                properties.putAll(emailConfig.getProperties());
                sender.setJavaMailProperties(properties);
            }
            JavaMailSenderInstance.instance.put(username, sender);
            javaMailSender = sender;
        }
        return javaMailSender;
    }

    /**
     * 静态内部类
     **/
    private static class JavaMailSenderInstance {
        private static final Map<String, JavaMailSender> instance = new ConcurrentHashMap<>();
    }

}
