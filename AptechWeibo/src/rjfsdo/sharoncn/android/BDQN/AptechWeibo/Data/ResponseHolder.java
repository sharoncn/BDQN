package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.net.RequestListener;

/**
 * Weibo回调接口的实现类
 * @author sharoncn
 *
 */
public class ResponseHolder implements RequestListener,WeiboAuthListener{
	private static final String TAG = "ResponseHolder";
	private Handler handler;
	private JsonParser parser;
	private int flag = WeiboDataManager.MSGTYPE_PARSECONTENT;
	
	public ResponseHolder(Handler handler){
		this(handler, WeiboDataManager.MSGTYPE_PARSECONTENT);
	}
	
	public ResponseHolder(Handler handler,int flag){
		this.handler = handler;
		this.flag = flag;
	}
	
	public void setParser(JsonParser parser){
		this.parser = parser;
	}
	
	//RequestListener
	@Override
	public void onComplete(String json) {
		Log.v(TAG, "onComplete:" + json);
		if(parser == null){
			sendFailMessage("程序错误:解析器不存在,无法解析内容");
			return;
		}
		final ArrayList<Serializable> result = parser.parse(json);
		Bundle data = new Bundle();
		data.putBoolean(WeiboDataManager.FLAG_ISSUCCESS, true);
		data.putSerializable(WeiboDataManager.FLAG_DATA, result);
		sendParseMessage(data,flag);
	}

	@Override
	public void onError(WeiboException err) {
		sendFailMessage(err.getMessage());
	}

	@Override
	public void onIOException(IOException e) {
		sendFailMessage(e.getMessage());
	}
	
	//WeiboAuthListener
	@Override
	public void onCancel() {
		sendFailMessage("Operation is Cancelled");
	}

	@Override
	public void onComplete(Bundle result) {
		sendParseMessage(result,WeiboDataManager.MSGTYPE_LOGIN);
	}

	@Override
	public void onError(WeiboDialogError err) {
		sendFailMessage(err.getMessage());
	}

	@Override
	public void onWeiboException(WeiboException e) {
		sendFailMessage(e.getMessage());
	}
	
	/**
	 * 发送异常或者错误消息
	 * @param content 消息内容
	 */
	private void sendFailMessage(String content){
		Message msg = handler.obtainMessage();
		msg.what = WeiboDataManager.MSGTYPE_FAIL;
		Bundle data = new Bundle();
		data.putBoolean(WeiboDataManager.FLAG_ISSUCCESS, false);
		data.putString(WeiboDataManager.FLAG_ERR_MSG, content);
		msg.setData(data);
		msg.sendToTarget();
	}
	
	private void sendParseMessage(Bundle data,int what) {
		Message msg = handler.obtainMessage();
		msg.what = what;
		msg.setData(data);
		msg.sendToTarget();
	}
}
