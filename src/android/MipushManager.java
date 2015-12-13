package com.ihealth.plugin;

import com.xiaomi.mipush.sdk.MiPushClient;

import android.content.Context;

public class MipushManager {

	public static final String MSG_REGISTER_SUCCESS = "com.msg.register.success";
	public static final String MSG_REGISTER_SUCCESS_EXTRA = "com.msg.register.success.extra";
	public static final String MSG_RECEIVER_MESSAGE_REGID = "com.msg.register.success.extra";
	public static final String MSG_REGISTER_FAIL = "com.msg.register.fail";
	public static final String MSG_REGISTER_FAIL_EXTRA = "com.msg.register.fail.extra";
	public static final String MSG_RECEIVER_MESSAGE = "com.msg.receiver.message";
	public static final String MSG_RECEIVER_MESSAGE_EXTRA = "com.msg.receiver.message.extra";
	
	private static class SingletonHolder {
		static final MipushManager INSTANCE = new MipushManager();
	}

	public static MipushManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private MipushManager() {

	}
	
	private Context mContext;
	public void init(Context context){
		this.mContext = context;
	}
	
	public void destory(){
		
	}
	
	private String APPID = "2882303761517340443";
	private String APPSECRET = "5571734098443";

	public void setIdAndSecret(String id, String secret){
		this.APPID = id;
		this.APPSECRET = secret;
	}
	
	public void register(){
		new Thread(){
			public void run(){
				MiPushClient.registerPush(mContext, APPID, APPSECRET);
			}
		}.start();
	}
	
}
