package com.sxxxxx.message.center.core.send.handler;

import java.util.List;

import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.sxxxxx.message.center.core.config.AliyunMessageConfig;
import com.sxxxxx.message.center.core.config.MessageConfiguration;
import com.sxxxxx.message.center.core.config.UserConfiguration;
import com.sxxxxx.message.center.core.context.MessageContext;
import com.sxxxxx.message.center.core.entity.EmailAndPhoneConf;
import com.sxxxxx.message.center.core.exception.MessageException;
import com.sxxxxx.message.center.core.send.MessageAbstractHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShortMsgHandler extends MessageAbstractHandler {

    private AliyunMessageConfig aliyunMessageConfig;

    @Override
    public void handle() throws MessageException {
        UserConfiguration userConfiguration = getMessageContext().getUserConfiguration();
        this.sendShortMessageByAliyun(userConfiguration, getMessageContext());
    }

    @Override
    public void setHandlerConfiguration(MessageConfiguration messageConfiguration) {
        this.aliyunMessageConfig = (AliyunMessageConfig) messageConfiguration;
    }

    private void sendShortMessageByAliyun(UserConfiguration userConfiguration, MessageContext message) {
        List<EmailAndPhoneConf> receiveConfs = userConfiguration.getEmailAndPhoneConf();
        for (EmailAndPhoneConf receiveConf : receiveConfs) {
            sendShortMessageByAliyun(receiveConf, message);
        }

    }

    private void sendShortMessageByAliyun(EmailAndPhoneConf emailAndPhoneConf, MessageContext message) {
        try {

            com.aliyun.dysmsapi20170525.Client client = createClient();
            com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                    .setPhoneNumbers(emailAndPhoneConf.getPhone())
                    .setTemplateParam(message.getMessageBody())
                    .setSignName(aliyunMessageConfig.getSignName())
                    .setTemplateCode(aliyunMessageConfig.getTemplateCode());
            try {
                SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, new RuntimeOptions());
                log.info("阿里云短信调用状态 status: ${}", sendSmsResponse.getBody().getMessage());
            }
            catch (TeaException error) {
                log.error(error.getMessage(), error);
                // 如有需要，请打印 error
                com.aliyun.teautil.Common.assertAsString(error.message);
            }
            catch (Exception _error) {
                TeaException error = new TeaException(_error.getMessage(), _error);
                // 如有需要，请打印 error
                com.aliyun.teautil.Common.assertAsString(error.message);
            }

        }
        catch (Exception e) {
            log.error("aliyun send short message failed, error message is: " + e.getMessage(), e);
            throw new MessageException(e.getMessage());
        }
    }


    /**
     * 此处通过从环境变量中读取AccessKey，初始化云呼叫中心Client
     * @return Client
     * @throws Exception
     */
    public com.aliyun.dysmsapi20170525.Client createClient()
            throws Exception {
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户
        // 此处以把AccessKey 和 AccessKeySecret 保存在环境变量为例说明。您也可以根据业务需要，保存到配置文件里
        // 强烈建议不要把 AccessKey 和 AccessKeySecret 保存到代码里，会存在密钥泄漏风险
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(aliyunMessageConfig.getAccessKeyId())
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(aliyunMessageConfig.getAccessKeySecret());
        // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    @Override
    public Class<? extends MessageConfiguration> getMessageConfiguration() {
        return AliyunMessageConfig.class;
    }
}
