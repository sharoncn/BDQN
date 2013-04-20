package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter.DefaultAdapter;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.HasImage;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Listener.OnComplatedListener;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLProtocol;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;
/*
* 20130313重构
* 从SeeDisplayActivity，MovieListBaseActivity，FindDelicacies，ShowActivity中抽
* 取数据处理的共同接口
*/

/**
 * 
 * 请初始化ListView,此类不检查ListView是否为空。
 * 类中使用{@link DefaultAdapter},如果需要改变构造的实例，
 * 请在initData之前使用(setAdapterClazz(Class))方法。
 * 
 * @author sharoncn
 * @see ListBaseActivity#setAdapterClazz(Class)
 */
public abstract class ListBaseActivity extends BaseActivity {
	private static final String TAG = "ListBaseActivity";
	private static final int FLAG_IMAGE_OK = 101;
	private static int size;
	protected ListView list;
	protected DefaultAdapter adapter;
	private ProgressDialog pd;
	private Class<? extends DefaultAdapter> adapterClazz = null; 

	private List<Object> data;
	
	protected Handler handler = new Handler(){
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case FLAG_IMAGE_OK://获取图片的消息
				Log.v(TAG,"init image size:" + size);
				if((--size) == 0){
					if(list.getAdapter() == null){
						list.setAdapter(adapter);
					}else{
						((BaseAdapter)list.getAdapter()).notifyDataSetChanged();
					}
					if(pd != null)pd.dismiss();
					Log.v(TAG,"init image FLAG_IMAGE_OK " + FLAG_IMAGE_OK);
				}
				break;
			default://默认情况为从服务器获取基本信息
				if(msg.what == DataManager.FLAG_TOAST_MSG){
					break;
				}
				Log.v(TAG,"filldata is ok");
				boolean isSuccess = msg.getData().getBoolean(DataManager.FLAG_ISSUCCESS);
				if(isSuccess){
					data = (List<Object>) msg.getData().getSerializable(DataManager.FLAG_DATA);
					Log.v(TAG,"data is Success,data.size:" + data.size());
					if(adapter == null){
						if(adapterClazz != null){
							try {
								Class<?>[] parameterTypes = new Class<?>[]{Context.class,List.class};
								Constructor<?>  cst = adapterClazz.getConstructor(parameterTypes);
								adapter = (DefaultAdapter) cst.newInstance(new Object[]{ListBaseActivity.this, data});
							} catch (Exception e){
								e.printStackTrace();
							}
						}else{
							adapter = new DefaultAdapter(ListBaseActivity.this, data);
						}
						setProjection(adapter);
						addTextViewHeader(adapter);
					}else{
						adapter.getData().addAll(data);
					}
					
					size = data.size();
					for(int i = 0;i < data.size();i++){
						//获取完基本信息，初始化图片
						DataManager.getInstance(ListBaseActivity.this).getImage(i,((HasImage)data.get(i)).getImage(), listener);
						Log.v(TAG,"init image " + i);
					}
				}else{
					if(pd != null)pd.dismiss();
				}
				
				break;
			}
			ListBaseActivity.this.handleMessage(msg);
			super.handleMessage(msg);
		}
	};
	
	private OnComplatedListener listener = new OnComplatedListener() {
		@Override
		public void onComplated(int index, String imageId) {
			//这里并没有使用index，本来设计是获取完基本数据先再listview中显示出来，然后获取到图片
			//再更新Adapter的数据然后NotifyDatasetChange。算是一个思路吧。
			Log.v(TAG,"OnComplated");
			handler.sendEmptyMessage(FLAG_IMAGE_OK);
		}
	};
	
	/**
	 * 初始化数据
	 * @param context 上下文
	 * @param clazz   从DataManager构造什么类的对象
	 * @param cmd     命令字符串，应为:URLProtocol.?_CMD_VALUE之一
	 * @param flag    标识Flag，应为：DataManager.FLAG_?之一
	 * @param limit   页码
	 */
	protected final void initData(final Context context,final Class<?> clazz,final String cmd, final int flag,final int limit){
		pd = Util.showProgressDialog(this, getString(R.string.data_loading));
		new Thread(){
			@Override
			public void run() {
				HashMap<String,String> args = new HashMap<String,String>();
				args.put(URLProtocol.CMD, cmd);
				args.put(URLProtocol.LIMIT, "" + limit);
				try {
					DataManager.getInstance(context).getData(clazz, flag, handler, args);
					Log.v(TAG, "Thread getData");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	/**
	 * 如果使用的Adapter是DefaultAdapter的子类，请设置Adapter的类型
	 * @param adapterClazz  Adapter的类型
	 */
	public void setAdapterClazz(Class<? extends DefaultAdapter> adapterClazz) {
		this.adapterClazz = adapterClazz;
	}
	
	/**
	 * 为adapter显示的内容映射方法
	 * @param adapter
	 */
	protected abstract void setProjection(DefaultAdapter adapter);
	
	/**
	 * 为adapter显示的文本内容加入头部
	 * @param adapter
	 */
	protected abstract void addTextViewHeader(DefaultAdapter adapter);
}
