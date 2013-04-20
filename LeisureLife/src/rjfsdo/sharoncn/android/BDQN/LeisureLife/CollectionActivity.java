package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.BaseHeader;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.BaseMovie;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.Collection;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Display;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.HasImage;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.SaveDataUtils;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.Show;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Listener.OnComplatedListener;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLProtocol;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 收藏界面
 * 
 * @author sharoncn
 * 
 */
public class CollectionActivity extends BaseActivity implements OnItemClickListener {
	private static final String TAG = "CollectionActivity";
	private static final int FLAG_COLLECTION = -1;
	private static final int FLAG_IMAGE_OK = 101;
	private static final int FIRST = 0;
	private ListView list;
	private TextView tips;
	private BaseHeader header;
	private BaseAdapter adapter;
	private List<String> items = new ArrayList<String>();
	private ProgressDialog pd;
	private List<Collection> data;
	protected Class<?> detailClazz;
	protected String detailTitle = "";
	private Handler handler = new Handler() {
		private int size = 0;
		private ArrayList<Object> data;

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case FLAG_IMAGE_OK:
				Log.v(TAG, "init image size:" + size);
				if ((--size) == 0) {
					pd.dismiss();
					Log.v(TAG, "init image FLAG_IMAGE_OK " + FLAG_IMAGE_OK);
					Intent intent = new Intent(CollectionActivity.this,detailClazz);
					intent.putExtra(ShowDetail.FLAG_TITLE, CollectionActivity.this.detailTitle);
					intent.putExtra(ShowDetail.FLAG_POSITION, position);
					intent.putExtra(ShowDetail.FLAG_DATA, data);
					intent.putExtra(ShowDetail.FLAG_IDSIGN, idSign);
					intent.putExtra(ShowDetail.FLAG_WHO, "" + detailCmd);
					startActivity(intent);
				}
				break;
			case FLAG_COLLECTION:
				Log.v(TAG, "filldata is ok");
				boolean isSuccess = msg.getData().getBoolean(DataManager.FLAG_ISSUCCESS);
				if (isSuccess) {
					data = (ArrayList<Object>) msg.getData().getSerializable(DataManager.FLAG_DATA);
					Log.v(TAG, "data is Success,data.size:" + data.size());

					size = data.size();
					for (int i = 0; i < data.size(); i++) {
						DataManager.getInstance(CollectionActivity.this).getImage(i, ((HasImage) data.get(i)).getImage(),
								listener);
						Log.v(TAG, "init image " + i);
					}
				} else {
					pd.dismiss();
				}

				break;
			default:
				if(msg.what == DataManager.FLAG_TOAST_MSG){
					break;
				}
				if(adapter == null){
					adapter = new ArrayAdapter<String>(CollectionActivity.this, android.R.layout.simple_list_item_1, items);
					list.setAdapter(adapter);
				}else{
					adapter.notifyDataSetChanged();
				}
				
				if (adapter.getCount() == 0) {
					tips.setVisibility(View.VISIBLE);
				} else {
					tips.setVisibility(View.GONE);
				}
				pd.dismiss();
				
			}
			CollectionActivity.this.handleMessage(msg);
			super.handleMessage(msg);
		}
	};
	private int position;
	private int cmd;
	private int detailCmd;
	private int imageId;
	private String idSign;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_collection);

		list = (ListView) findViewById(R.id.collection_list);
		list.setOnItemClickListener(this);
		this.registerForContextMenu(list);
		tips = (TextView) findViewById(R.id.collection_empty);
		header = (BaseHeader) findViewById(R.id.collection_header);
		header.setTitle(getString(R.string.collection));
	}
	
	@Override
	protected void onResume() {
		pd = Util.showProgressDialog(this, getString(R.string.data_loading));

		new Thread() {
			@Override
			public void run() {
				data = SaveDataUtils.getInstance(CollectionActivity.this).getCollection();
				items.clear();
				for (Collection c : data) {
					items.add(c.getType() + ":" + c.getName());
				}
				handler.sendEmptyMessage(0);
			}
		}.start();
		super.onResume();
	}

	private int getCmd() {
		int cmd = ((int)(this.imageId / 100)) + 1;
		Log.i(TAG,"CMD: " + cmd);
		return cmd;
	}

	private OnComplatedListener listener = new OnComplatedListener() {
		@Override
		public void onComplated(int index, String imageId) {
			Log.v(TAG,"OnComplated");
			handler.sendEmptyMessage(FLAG_IMAGE_OK);
		}
	};

	private String getId(String content) {
		Log.v(TAG, content);
		String[] parts = content.split(",");
		final int count = parts.length;
		for (int i = 0; i < count; i++) {
			if (parts[i].startsWith("id:")) {
				return parts[i].replace("id:", "");
			}
		}
		return null;
	}

	private String getLimit(String id) {
		int limit = (Integer.parseInt(id) - 1) / 5;
		return "" + limit;
	}

	private int getPosition(String id) {
		return (Integer.parseInt(id) - 1) % 10;
	}

	@Override
	public void onItemClick(AdapterView<?> adpView, View view, int position, long id) {
		final String name = adapter.getItem(position).toString();
		Log.v(TAG, "item被点击:" + position + name);
		final Collection r = data.get(position);
		final String _id = getId(r.getContent());
		this.imageId = Integer.parseInt(r.getImageId());
		final String limit = getLimit(_id);
		this.position = getPosition(_id);
		Log.v(TAG, "itemID:" + _id + "  limit:" + limit + "  position:" + this.position + "  imageId:" + imageId);

		Class<?> _clazz = null;
		if (name.startsWith("电影:")) {
			_clazz = BaseMovie.class;
			this.detailClazz = MovieDetail.class;
		} else if (name.startsWith("展览:")) {
			this.idSign = URLProtocol.DID;
			_clazz = Display.class;
			this.detailClazz = DisplayDetail.class;
		} else {
			_clazz = Show.class;
			this.detailClazz = ShowDetail.class;
		}
		this.cmd = getCmd();
		this.detailCmd = this.cmd + 1;
		if(detailCmd == 502 || detailCmd == 102 || detailCmd == 802){
			if(detailCmd == 502){this.detailTitle = getString(R.string.music);}
			this.idSign = URLProtocol.MID;
		}else if(detailCmd == 602 || detailCmd == 702){
			if(detailCmd == 602){
				this.detailTitle = getString(R.string.pko);
			}else{
				this.detailTitle = getString(R.string.play);
			}
			this.idSign = URLProtocol.PID;
		}else if(detailCmd == 202){
			this.detailTitle = getString(R.string.concert);
			this.idSign = URLProtocol.CID;
		}
		final Class<?> clazz = _clazz;

		pd = Util.showProgressDialog(this, getString(R.string.data_loading));
		new Thread() {
			@Override
			public void run() {
				HashMap<String, String> args = new HashMap<String, String>();
				args.put(URLProtocol.CMD, "" + cmd);
				args.put(URLProtocol.LIMIT, "" + limit);
				try {
					DataManager.getInstance(CollectionActivity.this).getData(clazz, FLAG_COLLECTION, handler, args);
					Log.v(TAG, "Thread getData");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.setHeaderTitle(R.string.seleteop);
		menu.add(0, FIRST, Menu.NONE, R.string.delete);
		menu.add(0, FIRST + 1, Menu.NONE, R.string.deleteall);
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		final int position = menuInfo.position;
		
		Log.v(TAG,"" + position);
		Collection c = data.get(position);
		switch(item.getItemId()){
		case FIRST:
			if (SaveDataUtils.getInstance(this).deleteCollection(c)) {
				Util.showMsg(this, R.string.delete_success);
				data.remove(position);
				items.remove(position);
				this.adapter.notifyDataSetChanged();
			}else{
				Util.showMsg(this, R.string.delete_faill);
			}
			break;
		case FIRST +1:
			if(SaveDataUtils.getInstance(this).clearCollection()){
				Util.showMsg(this, R.string.delete_success);
				data.clear();
				items.clear();
				this.adapter.notifyDataSetChanged();
			}else{
				Util.showMsg(this, R.string.delete_faill);
			}
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//TabMain内部Activity，为了防止back事件被处理，当back时返回false
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
