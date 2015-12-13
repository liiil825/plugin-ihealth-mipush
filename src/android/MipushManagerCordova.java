package com.ihealth.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import org.json.JSONException;
import org.json.JSONObject;

public class MipushManagerCordova extends CordovaPlugin{

	private Context mContext;
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		mContext = webView.getContext();
		initReceiver();
		MipushManager.getInstance().init(mContext);	
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unReceiver();
	}

	private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MipushManager.MSG_RECEIVER_MESSAGE);
        intentFilter.addAction(MipushManager.MSG_REGISTER_SUCCESS);
        intentFilter.addAction(MipushManager.MSG_REGISTER_FAIL);
        mContext.registerReceiver(mReceiver, intentFilter);
    }
    
    private void unReceiver(){
        mContext.unregisterReceiver(mReceiver);
    }
    
	private void keepCallback(final CallbackContext callbackContext, String message) {
        PluginResult r = new PluginResult(PluginResult.Status.OK, message);
        r.setKeepCallback(true);
        callbackContext.sendPluginResult(r);
    }
	
	private CallbackContext mCallbackContext;
	@Override
	public boolean execute(String action, CordovaArgs args,
			CallbackContext callbackContext) throws JSONException {
		mCallbackContext = callbackContext;
		if (action.equals("register")) {
			MipushManager.getInstance().register();
            return true;
        }else if(action.equals("registerEx")){
        	String id = args.getString(0);
        	String secret = args.getString(1);
			MipushManager.getInstance().setIdAndSecret(id, secret)
        	MipushManager.getInstance().register();
        }else{
        	return false;
        }
	}
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver(){
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(MipushManager.MSG_RECEIVER_MESSAGE.equals(action)){
				String msg = intent.getStringExtra(MipushManager.MSG_RECEIVER_MESSAGE_EXTRA);
				JSONObject o = null;
				try {
                	o = new JSONObject();
                	o.put("msg", "receive");              	
                	o.put("message", msg);					
            	} catch (Exception e) {                   
                	e.printStackTrace();
            	}	
				keepCallback(mCallbackContext, o.toString());
				
			}else if(MipushManager.MSG_REGISTER_SUCCESS.equals(action)){
				String msg = intent.getStringExtra(MipushManager.MSG_REGISTER_SUCCESS_EXTRA);
				String regId = intent.getStringExtra(MipushManager.MSG_RECEIVER_MESSAGE_REGID);
				JSONObject o = null;
            	try {
                	o = new JSONObject();
                	o.put("msg", "register");
                	o.put("result", "success");
                	o.put("message", msg);
					o.put("regid", regId);
            	} catch (Exception e) {                   
                	e.printStackTrace();
            	}			
				keepCallback(mCallbackContext, o.toString());
				
			}else if(MipushManager.MSG_REGISTER_FAIL.equals(action)){
				String msg = intent.getStringExtra(MipushManager.MSG_REGISTER_FAIL_EXTRA);
				keepCallback(mCallbackContext, msg);
				JSONObject o = null;
            	try {
                	o = new JSONObject();
                	o.put("msg", "register");
                	o.put("result", "fail");
                	o.put("message", msg);
            	} catch (Exception e) {                   
                	e.printStackTrace();
            	}			
				keepCallback(mCallbackContext, o.toString());
				
			}
		}
	};
	
}
