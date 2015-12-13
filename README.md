# Cordova iHealth Plugin for Mipush

This is a cordova plugin for Mipush.

## Using Cordova plugins directly in your meteor application

### Add the plugin

    $ meteor add cordova:com.ihealth.plugin.mipushmanagercordova@https://github.com/iHealthLab/plugin-ihealth-mipush/tarball/52e6cc1790662b5431b736f6286a881d2fcc59b8


### Remove the plugin

    $ meteor remove cordova:com.ihealth.plugin.mipushmanagercordova
    
# 流程如下：

1.  执行 register 方法。

plugin callback 会返回的如下信息
{"message":"d\/\/igwEhgBGCI2TG6lWqlGg3xk18zCpPtkKstQSM5ovG3AOCaSLxj9FC3hgogjTSM2IpX1q\/1RE6GqgF0WdvKiipUcmUNlpNv0NR2JuQQvc=","regid":"d\/\/igwEhgBGCI2TG6lWqlGg3xk18zCpPtkKstQSM5ovG3AOCaSLxj9FC3hgogjTSM2IpX1q\/1RE6GqgF0WdvKiipUcmUNlpNv0NR2JuQQvc=","result":"success","msg":"register"}

2.  会收到 RegId 的callback，这个RegId相当于pushToken，需要发送到cloud端。
3.  如果cloud端发推送过来，meteor app端会收到 plugin 上传的 message callback。

> 特别注意：
小米推送中使用的包名必须是在小米开发者网站上注册。这个包名我写到mobile-config.js中，app.info的id.
    
