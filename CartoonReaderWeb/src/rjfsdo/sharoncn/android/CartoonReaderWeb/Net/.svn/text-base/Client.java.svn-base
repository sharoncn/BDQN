package rjfsdo.sharoncn.android.CartoonReaderWeb.Net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class Client {
	/**
	 * 请求一个URL地址获得返回的文件
	 * @param url 请求的url
	 * @return InputStream 返回文件流
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws Exception
	 */
	public static InputStream requestFile(String url) throws ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		//设置网络超时
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		HttpGet get = new HttpGet(url);
		//结果
		HttpResponse response = client.execute(get);
		HttpEntity responesEntity = response.getEntity();
		return responesEntity.getContent();
	}
	
	/**
	 * 请求一个URL地址获得返回的内容
	 * @param url 请求的url
	 * @param params 参数列表
	 * @return InputStream 返回Content流
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws Exception
	 */
	public static InputStream requestContent(String url,List<NameValuePair> params) throws ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		//设置网络超时
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		//添加post参数
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"utf-8");
		HttpPost post = new HttpPost(url);
		post.setEntity(entity);
		//结果
		HttpResponse response = client.execute(post);
		HttpEntity responesEntity = response.getEntity();
		return responesEntity.getContent();
	}
	
	/**
	 * 请求一个URL地址获得返回的内容
	 * @param url 请求的url
	 * @param params 参数列表
	 * @return String 返回Content字符串
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws Exception
	 * @see Client.requestContent
	 */
	public static String requestStringContent(String url,List<NameValuePair> params) throws ClientProtocolException, IOException{
		InputStream is = requestContent(url,params);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
		boolean flag = false;
		String line = null;
		while((line = br.readLine()) != null){
			if(line.startsWith("<?xml")){
				flag = true;
			}
			if(flag){
				sb.append(line + "\n");
			}
		}
		is.close();
		br.close();
		return sb.toString();
	}
}
