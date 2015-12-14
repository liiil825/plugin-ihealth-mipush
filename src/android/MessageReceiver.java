package com.ihealth.plugin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

public class MessageReceiver extends PushMessageReceiver {

	private String TAG = "MessageReceiver::";
	private MipushManager mMipushManager;
	@Override
	public void onCommandResult(Context context, MiPushCommandMessage message) {
		String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
		if (MiPushClient.COMMAND_REGISTER.equals(command)) {//注册返回
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                //注册成功
                Log.i(TAG, "注册成功");  
                
                mIntent = new Intent(MipushManager.MSG_REGISTER_SUCCESS);
				mIntent.putExtra(MipushManager.MSG_REGISTER_SUCCESS_EXTRA, message.getReason());
        		mIntent.putExtra(MipushManager.MSG_RECEIVER_MESSAGE_REGID, cmdArg);
        		context.sendBroadcast(mIntent);	
                           
                // MiPushClient.subscribe(context, "", null);
            } else {
                //注册失败
                Log.i(TAG, "注册失败");
                mIntent = new Intent(MipushManager.MSG_REGISTER_FAIL);
        		mIntent.putExtra(MipushManager.MSG_REGISTER_FAIL_EXTRA, message.getReason());
        		context.sendBroadcast(mIntent);
            }
        } 
	}

	private Intent mIntent;
	private final String packageName = "com.ihealth.datun.doctor";
	private final String main_entry = "com.ihealth.datun.doctor.CordovaApp";
	@Override
	public void onReceiveMessage(Context context, MiPushMessage message) {
		Log.i(TAG, "收到推送信息" + message.getContent());
		mIntent = new Intent(MipushManager.MSG_RECEIVER_MESSAGE);
		mIntent.putExtra(MipushManager.MSG_RECEIVER_MESSAGE_EXTRA, message.getContent());
		context.sendBroadcast(mIntent);
		
		ComponentName cn = new ComponentName(packageName, main_entry);
        Intent intent = new Intent();
        intent.setComponent(cn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
		
	}

}
