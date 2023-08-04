package com.sxxxxx.message.center.core.send.handler;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.sxxxxx.message.center.core.config.DingTalkConfiguration;
import com.sxxxxx.message.center.core.config.MessageConfiguration;
import com.sxxxxx.message.center.core.config.UserConfiguration;
import com.sxxxxx.message.center.core.context.MessageContext;
import com.sxxxxx.message.center.core.entity.DingTalkWebHookConf;
import com.sxxxxx.message.center.core.exception.MessageException;
import com.sxxxxx.message.center.core.send.MessageAbstractHandler;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.taobao.api.ApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DingTalkHandler extends MessageAbstractHandler {

    private DingTalkConfiguration dingTalkConfiguration;

    private String token;

    private String userIds;

    @Override
    public void handle() throws MessageException {
        this.initSendAppToken();
        this.initDingTalkUserId(getMessageContext());

        //推送钉钉工作通知
        this.sendDingTalkWorkNotice(getMessageContext());
        //推送钉钉机器人
//        this.sendDingTalkRobot(message);
    }

    @Override
    public void setHandlerConfiguration(MessageConfiguration messageConfiguration) {
        this.dingTalkConfiguration = (DingTalkConfiguration) messageConfiguration;
    }

    public void initSendAppToken() {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest req = new OapiGettokenRequest();
            req.setAppkey(dingTalkConfiguration.getAccessKeyId());
            req.setAppsecret(dingTalkConfiguration.getAccessKeySecret());
            req.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(req);
            String token = response.getAccessToken();
            if (StringUtils.isBlank(token)) {
                throw new MessageException(response.getErrmsg());
            }
            this.token = token;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MessageException(e.getMessage());
        }
    }

    public void initDingTalkUserId(MessageContext message) {
        UserConfiguration userConfiguration = message.getUserConfiguration();
        List<String> dingTalkUserId = userConfiguration.getDingTalkUserId();
        List<String> userPhoneNum = message.getUserConfiguration().getUserPhoneNum();
        if (CollectionUtils.isEmpty(dingTalkUserId)) {
            dingTalkUserId = new ArrayList<>();
        }
        if (CollectionUtils.isNotEmpty(userPhoneNum)) {
            try {
                OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
                req.setSupportExclusiveAccountSearch(false);
                for (String phoneNum : userPhoneNum) {
                    DingTalkClient client = new DefaultDingTalkClient(
                        "https://oapi.dingtalk.com/topapi/v2/user/getbymobile");
                    req.setMobile(phoneNum);
                    OapiV2UserGetbymobileResponse rsp = client.execute(req, this.token);
                    if (!"ok".equals(rsp.getErrmsg())) {
                        throw new MessageException(rsp.getErrmsg());
                    }
                    dingTalkUserId.add(rsp.getResult().getUserid());
                }
            }
            catch (ApiException e) {
                throw new MessageException(e.getMessage());
            }
        }
        userIds = String.join(",", dingTalkUserId);
    }

    private void sendDingTalkWorkNotice(MessageContext messageContext) {
        if (StringUtils.isNotBlank(this.token)) {
            DingTalkClient client = new DefaultDingTalkClient(
                "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
            OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
            request.setAgentId(dingTalkConfiguration.getAgentId());
            request.setUseridList(this.userIds);
            request.setToAllUser(false);
            this.initWorkNotice(request, messageContext);
            try {
                OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(request, this.token);
                log.info("钉钉工作通知推送结果" + rsp.getErrmsg());
            }
            catch (ApiException exception) {
                log.error(exception.getMessage(), exception);
                throw new MessageException(exception.getMessage());
            }
        }
    }

    private void initWorkNotice(OapiMessageCorpconversationAsyncsendV2Request request, MessageContext messageContext) {
        // 以下是设置各种消息格式的方法
        switch (messageContext.getMsgType()) {
            case "markdown":
                markdownWorkNotice(request, messageContext);
                break;
            case "link":
                linkWorkNotice(request, messageContext);
                break;
            default:
                textWorkNotice(request, messageContext);
        }
    }

    private static void textWorkNotice(OapiMessageCorpconversationAsyncsendV2Request request,
        MessageContext messageContext) {

        String messageTitle = messageContext.getMessageTitle();
        String messageBody = messageContext.getMessageBody();
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        OapiMessageCorpconversationAsyncsendV2Request.Text text = new OapiMessageCorpconversationAsyncsendV2Request.Text();
        text.setContent(messageTitle + "\n" + messageBody);
        msg.setText(text);
        request.setMsg(msg);
    }

    private static void linkWorkNotice(OapiMessageCorpconversationAsyncsendV2Request request,
        MessageContext messageContext) {
        String messageTitle = messageContext.getMessageTitle();
        String messageBody = messageContext.getMessageBody();
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("link");
        OapiMessageCorpconversationAsyncsendV2Request.Link link = new OapiMessageCorpconversationAsyncsendV2Request.Link();
        link.setTitle(messageTitle);
        link.setText(messageBody);
        link.setMessageUrl(messageContext.getUrl());
        msg.setLink(link);
        request.setMsg(msg);
    }

    private static void markdownWorkNotice(OapiMessageCorpconversationAsyncsendV2Request request,
        MessageContext messageContext) {

        String messageTitle = messageContext.getMessageTitle();
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("markdown");
        OapiMessageCorpconversationAsyncsendV2Request.Markdown markdown = new OapiMessageCorpconversationAsyncsendV2Request.Markdown();
        markdown.setTitle(messageTitle + "\n");

        markdown.setText(getMarkdownBody(messageContext));
        msg.setMarkdown(markdown);
        request.setMsg(msg);
    }

    public static String getMarkdownBody(MessageContext messageContext) {
        String messageBody = messageContext.getMessageBody();
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotBlank(messageContext.getHyperlink())) {
            messageBody = messageBody.replace(messageContext.getHyperlink(),
                    " [**" + messageContext.getHyperlink() + "**](" + messageContext.getUrl() + ") ");
        }
        builder.append("#### **").append(messageContext.getMessageTitle()).append("**\n").append(messageBody);
        return builder.toString();
    }

    private void sendDingTalkRobot(MessageContext message) {

        List<DingTalkWebHookConf> dingTalkWebHookConf = message.getUserConfiguration().getDingTalkWebHookConf();
        if (CollectionUtils.isNotEmpty(dingTalkWebHookConf)) {
            for (DingTalkWebHookConf mcDingTalkConfPo : dingTalkWebHookConf) {
                this.sendExternalDingTalk(mcDingTalkConfPo, message);
            }
        }
    }

    private void sendExternalDingTalk(DingTalkWebHookConf mcDingTalkConfPo, MessageContext message) {
        try {
            Long timestamp = System.currentTimeMillis();
            String sign = dingHmacSHA256(String.valueOf(timestamp), mcDingTalkConfPo.getSecretKey());
            DingTalkClient client = new DefaultDingTalkClient(
                mcDingTalkConfPo.getWebhook() + "&timestamp=" + timestamp + "&sign=" + sign);
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            if (StringUtils.isBlank(mcDingTalkConfPo.getReceiver())) {
                at.setIsAtAll(true);
            }
            else {
                String[] split = mcDingTalkConfPo.getReceiver().split(",");
                at.setAtMobiles(Arrays.asList(split));
                at.setAtUserIds(Arrays.asList(split));
                at.setIsAtAll(false);
            }
            request.setAt(at);
            this.initMessage(request, message);
            client.execute(request);
        }
        catch (Exception exception) {
            throw new MessageException("钉钉机器人推送失败");
        }
    }

    private void initMessage(OapiRobotSendRequest request, MessageContext messageContext) {
        // 以下是设置各种消息格式的方法
        switch (messageContext.getMsgType()) {
            case "text":
                sentText(request, messageContext);
                break;
            case "link":
                sendLink(request, messageContext);
                break;
            case "markdown":
                sendMarkdown(request, messageContext);
                break;
            default:
        }
    }

    public static void sentText(OapiRobotSendRequest request, MessageContext messageContext) {
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(
            messageContext.getMessageTitle() + "\n" + messageContext.getMessageBody() + "\n" + messageContext.getUrl());

        request.setMsgtype("text");
        request.setText(text);
    }

    public static void sendLink(OapiRobotSendRequest request, MessageContext messageContext) {
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setTitle(messageContext.getMessageTitle());
        link.setText(messageContext.getMessageBody());
        link.setMessageUrl(messageContext.getUrl());
        // link.setPicUrl("http://www.baidu.com/img/bd_logo1.png");

        request.setMsgtype("link");
        request.setLink(link);
    }

    public static void sendMarkdown(OapiRobotSendRequest request, MessageContext messageContext) {
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle(messageContext.getMessageTitle());
        markdown.setText(getMarkdownBody(messageContext));
        request.setMsgtype("markdown");
        request.setMarkdown(markdown);
    }

    /**
     * 钉钉自定义机器人安全设置 把timestamp+"\n"+密钥当做签名字符串，使用HmacSHA256算法计算签名，然后进行Base64
     * encode，最后再把签名参数再进行urlEncode，得到最终的签名（需要使用UTF-8字符集）
     * 
     * @param secret
     * @return
     */
    public static String dingHmacSHA256(String timestamp, String secret) throws Exception {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        return URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
    }

    @Override
    public Class<? extends MessageConfiguration> getMessageConfiguration() {
        return DingTalkConfiguration.class;
    }
}
