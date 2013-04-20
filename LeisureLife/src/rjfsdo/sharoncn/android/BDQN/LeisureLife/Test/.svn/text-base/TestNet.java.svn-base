package rjfsdo.sharoncn.android.BDQN.LeisureLife.Test;

import java.io.IOException;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.HttpConnection;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLParam;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLProtocol;
import android.test.AndroidTestCase;
import android.util.Log;

public class TestNet extends AndroidTestCase {
	private static final String TAG = "TestNet";

	public void testHttpConnection() throws Exception {
		URLParam param = new URLParam();
		String result;
		// 注册
		param.addParam(URLProtocol.CMD, URLProtocol.REGISTER_CMD_VALUE);
		param.addParam(URLProtocol.NAME, "military");
		param.addParam(URLProtocol.PASSWORD, "123456");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);

		// 登录
		// uid:53d0e563-8b1d-4b92-be5e-acc77bd5a4b9
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.LOGIN_CMD_VALUE);
		param.addParam(URLProtocol.UID, "53d0e563-8b1d-4b92-be5e-acc77bd5a4b9");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);

		// 美食列表
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.DELICACISE_CMD_VALUE);
		param.addParam(URLProtocol.LIMIT, "0");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);
		// 美食详情
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.DELICACISE_DETAIL_CMD_VALUE);
		param.addParam(URLProtocol.DID, "6");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);

		// 展览列表
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.DISPLAY_CMD_VALUE);
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);
		
		// 展览详情
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.DISPLAY_DETAIL_CMD_VALUE);
		param.addParam(URLProtocol.DID, "1");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);

		// 音乐会列表
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.MUSIC_CMD_VALUE);
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);
		
		// 音乐会详情
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.MUSIC_DETAIL_CMD_VALUE);
		param.addParam(URLProtocol.MID, "1");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);

		//
		// 京剧列表
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.PKO_CMD_VALUE);
		param.addParam(URLProtocol.LIMIT, "6");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);
		
		// 京剧详情
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.PKO_DETAIL_CMD_VALUE);
		param.addParam(URLProtocol.PID, "1");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);

		// 话剧列表
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.PLAY_CMD_VALUE);
		param.addParam(URLProtocol.LIMIT, "6");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);
		// 话剧详情
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.PLAY_DETAIL_CMD_VALUE);
		param.addParam(URLProtocol.PID, "1");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);

		// 电影列表
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.MOVIE_CMD_VALUE);
		param.addParam(URLProtocol.LIMIT, "6");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);
		// 电影详情
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.MOVIE_DETAIL_CMD_VALUE);
		param.addParam(URLProtocol.MID, "1");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);

		// 演唱会列表
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.CONCERT_CMD_VALUE);
		param.addParam(URLProtocol.LIMIT, "6");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);
		// 演唱会详情
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.CONCERT_DETAIL_CMD_VALUE);
		param.addParam(URLProtocol.CID, "1");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);

		// 即将上映列表
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.WILLMOVIE_CMD_VALUE);
		param.addParam(URLProtocol.LIMIT, "6");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);
		// 即将上映详情
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.WILLMOVIE_DETAIL_CMD_VALUE);
		param.addParam(URLProtocol.MID, "1");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);

		//获取评论
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.GET_COMMENT_CMD_VALUE);
		param.addParam(URLProtocol.TYPE, "1");
		param.addParam(URLProtocol.TID, "6");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);
		//发布评论
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.SEND_COMMENT_CMD_VALUE);
		param.addParam(URLProtocol.NAME, "0");
		param.addParam(URLProtocol.TYPE, "0");
		param.addParam(URLProtocol.CONTENT, "0");
		param.addParam(URLProtocol.ID, "6");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);
	}

	public void testGetImage() throws IOException {
		// 获取图片
		URLParam param = new URLParam();
		String result;
		param.addParam(URLProtocol.CMD, URLProtocol.IMAGE_CMD_VALUE);
		param.addParam(URLProtocol.IMAGE, "10002");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		// assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);
	}
	
	public void testSingle() throws IOException{
		URLParam param = new URLParam();
		String result;
		
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.MOVIE_CMD_VALUE);
		param.addParam(URLProtocol.LIMIT, "0");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.MOVIE_CMD_VALUE);
		param.addParam(URLProtocol.LIMIT, "1");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.MOVIE_CMD_VALUE);
		param.addParam(URLProtocol.LIMIT, "2");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.MOVIE_CMD_VALUE);
		param.addParam(URLProtocol.LIMIT, "3");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);
		param.clear();
		param.addParam(URLProtocol.CMD, URLProtocol.MOVIE_CMD_VALUE);
		param.addParam(URLProtocol.LIMIT, "4");
		result = HttpConnection.getContentString(URLProtocol.URL, param);
		assertTrue(result.contains("'code':'0'"));
		Log.i(TAG, "result:" + result);
	}
}
