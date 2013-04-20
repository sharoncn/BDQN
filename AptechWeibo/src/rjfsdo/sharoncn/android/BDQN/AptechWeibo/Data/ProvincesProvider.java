package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.City;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Province;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Listeners.OnComplatedListener;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Constants;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Util;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.stream.JsonReader;

public class ProvincesProvider implements OnComplatedListener{
	private static final String TAG = null;
	private static ProvincesProvider me;
	private static ArrayList<Province> provinces;
	private static AssetManager am;
	
	static{
		me = new ProvincesProvider();
	}
	
	private ProvincesProvider(){}
	
	public static ProvincesProvider getInstance(Context context){
		if(am == null){
			am = context.getAssets();
		}
		return me;
	}
	
	public ArrayList<Province> getProvinces(){
		try {
			if(provinces == null){
				InputStream is = am.open("provinces.json");
				
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String tmp;
				StringBuffer sb = new StringBuffer();
				while((tmp = br.readLine()) != null){
					sb.append(tmp);
				}
				
				String json = sb.toString();
				json = json.replace("{\"provinces\":", "");
				json = json.substring(0,json.lastIndexOf("}"));
				Log.i(TAG,json);
				provinces = parseProvince(json);
			}
			return provinces;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private ArrayList<Province> parseProvince(String json){
		JsonReader jr = new JsonReader(new StringReader(json));
		try {
			ArrayList<Province> provinces = new ArrayList<Province>();
			jr.beginArray();
			while(jr.hasNext()){
				jr.beginObject();
				Province province = new Province();
				while(jr.hasNext()){
					String key = jr.nextName();
					Log.i(TAG,"TAGNAME:" + key);
					if(key.equals("id")){
						int value = jr.nextInt();
						Log.i(TAG,"VALUE:" + value);
						province.setId(value);
					}else if(key.equals("name")){
						String value = jr.nextString();
						Log.i(TAG,"VALUE:" + value);
						province.setName(value);
					}else if(key.equals("citys")){
						jr.beginArray();
						ArrayList<City> cities = new ArrayList<City>();
						while(jr.hasNext()){
							City city = new City();
							jr.beginObject();
							while(jr.hasNext()){
								String id = jr.nextName();
								String name = jr.nextString();
								Log.i(TAG,"name:" + id + "  value:" + name);
								city.setId(id);
								city.setName(name);
							}
							cities.add(city);
							jr.endObject();
						}
						province.setCitys(cities);
						jr.endArray();
					}
				}
				jr.endObject();
				provinces.add(province);
			}
			jr.endArray();
			return provinces;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void onComplated(String result) {
		if(result != null && !"".equals(result)){
			System.out.println(result);
			ByteArrayInputStream bis = new ByteArrayInputStream(result.getBytes());
			Util.saveFile(bis, Constants.PROVINCESFILE);
		}else{
			System.out.println("获取城市列表失败！");
		}
		
	}
}
