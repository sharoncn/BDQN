package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.ProvincesProvider;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.StatusParser;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.City;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Province;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Service.DataService;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.ExpressionGetter;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Util;
import android.content.Intent;
import android.test.AndroidTestCase;
import android.util.Log;

import com.google.gson.Gson;


public class Test extends AndroidTestCase {
	private static final String TAG = "Test";

	public void testLogin() {

	}

	public void testParseStatus() throws Exception {
		StringBuffer sb = new StringBuffer();
		// InputStream is = getContext().getAssets().open("status.txt");
		InputStream is = getContext().getAssets().open("friendTimeLine2.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		StatusParser sp = StatusParser.getInstance();
		ArrayList<Serializable> data = sp.parse(sb.toString());
		assertTrue(data.size() > 0);
		Log.w(TAG, "data size:" + data.size());
		for (Serializable s : data) {
			Log.w(TAG, "data start-------------------------------------");
			Method[] methods = s.getClass().getMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				if (methodName.startsWith("get")) {
					Object value = method.invoke(s, new Object[] {});
					Log.i(TAG, "Method name:" + methodName);
					Log.v(TAG, "Value is:" + value);
				}
			}
			Log.w(TAG, "data end----------------------------------------");
		}
	}

	public void testGetImage() throws Exception {
		String src = "http://tp1.sinaimg.cn/2024714892/50/5598700648/0";
		Log.v(TAG, Util.getContentString(src, "utf-8"));
		// try {
		// Bitmap bmp = Util.getImageFromNet(src);
		// final String filePath = Constants.IMAGE_CACHE_DIR + "/" + fileName;
		// if(!Util.createFile(filePath)){
		// return;
		// }
		// FileOutputStream fos = null;
		// File file = new File(filePath);
		// try {
		// if (file.createNewFile()) {
		// fos = new FileOutputStream(file);
		// if (bmp.compress(Bitmap.CompressFormat.PNG, 100, fos))
		// return;
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// return;
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	public void testFormatContent() throws Exception {
		ExpressionGetter getter = ExpressionGetter.getInstance(mContext);
		System.out
				.println(getter
						.format("[笑哈哈][得意地笑][转发][江南style][偷乐][bm可爱][助力广东][助力山东][moc转发][BOBO爱你][ppb鼓掌][din推撞] [草泥马][神马][浮云][给力][围观][威武][熊猫][兔子][奥特曼][囧][互粉][礼物]"));
	}

	public void testProvincesProvider() throws Exception {
		ProvincesProvider pp = ProvincesProvider.getInstance(getContext());
		Intent intent = new Intent(getContext(), DataService.class);
		getContext().startService(intent);
		Thread.sleep(2000);// 等待服务启动
		ArrayList<Province> provinces = pp.getProvinces();

		assertTrue(provinces != null);
		assertTrue(provinces.size() > 0);
		for (Province p : provinces) {
			System.out.println(p.toString());
		}
	}
	
	public void testToJsonString(){
		Gson gson = new Gson();
		Province p1 = new Province();
		p1.setId(1);
		p1.setName("北京");
		ArrayList<City> city2 = new ArrayList<City>();
		for(int i = 0;i < 5;i++){
			City city = new City("" + i,"sxxss" + i);
			city2.add(city);
		}
		p1.setCitys(city2);
		
		Province p2 = new Province();
		p2.setId(2);
		p2.setName("武汉");
		ArrayList<City> citys1 = new ArrayList<City>();
		for(int i = 0;i < 7;i++){
			City city = new City("" + i,"sxxss" + i);
			citys1.add(city);
		}
		p2.setCitys(citys1);
		
		ArrayList<Province> provinces = new ArrayList<Province>();
		provinces.add(p1);
		provinces.add(p2);
		
		String json = gson.toJson(provinces);
		System.out.println(json);
		
	}
}
