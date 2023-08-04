# Sxxxxx open resouce - message - 消息中心
##### 一些学习使用记录

###目录
* [配置](#1)
  ### 建议使用环节变量 + 加密解密模式
##### *钉钉*: 
```java
DingTalkConfiguration configuration = new DingTalkConfiguration();

configuration.setAccessKeyId("AccessKeyId");
configuration.setAccessKeySecret("AccessKeySecret");
configuration.setAgentId(AgentId);
```
##### *阿里云短信*:
```java
//建议使用环节变量 + 加密解密模式
AliyunMessageConfig configuration = new AliyunMessageConfig();
configuration.setAccessKeyId("AccessKeyId");
configuration.setAccessKeySecret("AccessKeySecret");
configuration.setTemplateCode("TemplateCode");
configuration.setSignName("SignName");
```
##### *邮箱*:
```java
EmailConfig configuration = new EmailConfig();
configuration.setHost("smtp.qq.com");
configuration.setUsername("xxxxxx@qq.com");
configuration.setPassword("Password");
Map<String, String> source = new HashMap<>();
source.put("mail.smtp.auth", "true");
source.put("mail.smtp.starttls.enable", "true");
source.put("mail.smtp.starttls.required", "true");
configuration.setProperties(source);
```
* [使用](#2)
### 基于上述配置出的 configuration
```java
UserConfiguration userConfiguration = new UserConfiguration(null, userPhoneNum, dingTalkWebHookConfList, emailAndPhoneConfs);
MessageContext messageContext = new MessageContext("messageTitle", "messageBody", "Hyperlink",
        "url", "type", false, userConfiguration);
MessageCenterFactory messageCenterFactory = new MessageCenterFactory();
messageCenterFactory
        .setType(MessageTypeEnum.DING_TALK).setConfiguration(dingTalkConfiguration), messageContext)
        .setType(MessageTypeEnum.EMAIL).setConfiguration(emailConfiguration), messageContext);
messageCenterFactory.build().sendMessage();
```

