package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.FilesManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Adapter.FileAdapter;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Adapter.OnSetSingleCheckBoxVisibilityListener;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Models.FileInfo;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.FileComparator;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Util;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * �ļ�������д���ö����,���벻�ϵ�д,��ԭ��д��һ�������ھ���Ŀ�е��ļ��������޸���һ��
 * @author sharoncn
 *
 */
public class FileManager extends LinearLayout implements OnItemClickListener, OnSetSingleCheckBoxVisibilityListener {
	private static final String TAG = "FileManager";
	private ListView leftPanel;
	private ListView rightList;
	protected FileAdapter radp;
	protected SimpleAdapter ladp;
	protected ArrayList<FileInfo> rData;// �ұ�list��Դ�б�
	protected ArrayList<HashMap<String, Object>> lData; // ���list��Դ�б�
	protected Timer timer;
	protected String currentPath;
	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Log.v(TAG, "handleMessage");
			if (msg.arg1 == 100) {
				rData.clear();
				radp.notifyDataSetChanged();
				return;
			}
			FileInfo data = (FileInfo) msg.getData().getSerializable(FLAG_MSG_DATA);
			int icon;
			if (data.getType() == TYPE_DIR) {
				icon = R.drawable.folder;
			} else {
				icon = getIconByFilename(data.getName());
			}
			data.setIcon(icon);
			setRData(data);

			super.handleMessage(msg);
		}
	};
	
	
	private String saveFileByFullPath;

	public String getSaveFileByName() {
		return saveFileByFullPath;
	}

	public void setSaveFileByName(String saveFileByName) {
		this.saveFileByFullPath = saveFileByName;
	}

	public FileManager(Context context) {
		this(context, null);
	}

	public FileManager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * �ж����List�ǲ���ֻ��һ��item
	 * 
	 * @return
	 */
	public boolean isLeftSingleItem() {
		if (lData.size() <= 1) {
			return true;
		}
		return false;
	}

	/**
	 * �����ϼ�Ŀ¼
	 */
	public void backUpper() {
		String path = (String) lData.get(lData.size() - 2).get(FLAG_PATH);
		lData.remove(lData.size() - 1);
		lData.remove(lData.size() - 1);
		ladp.notifyDataSetChanged();
		setRoot(path);
	}

	/**
	 * Ϊ���list�������
	 * 
	 * @param data
	 */
	private void setRData(FileInfo data) {
		Log.v(TAG, "setData");
		Log.v(TAG, data.getName() + "  " + data.getIcon());
		rData.add(data);
		radp.notifyDataSetChanged();
	}

	/**
	 * Ϊ�ұ�list�������
	 * 
	 * @param name
	 * @param path
	 */
	protected void setLData(String name, String path) {
		for (int i = 0; i < lData.size(); i++) {
			if (lData.get(i).get(FLAG_PATH) == path) {
				Log.v(TAG, "����Ѿ����ڣ�" + path);
				return;
			}
		}
		HashMap<String, Object> lMap = new HashMap<String, Object>();
		int lIcon;
		if (name.equals("sdcard")) {
			lIcon = R.drawable.sdcard;
		} else {
			lIcon = R.drawable.folder;
		}
		lMap.put(FLAG_IMG, lIcon);
		if(path.equals("/")){
			lMap.put(FLAG_NAME, path);
		}else{
			lMap.put(FLAG_NAME, name.endsWith("/") ? name.substring(0, name.length() - 1) : name);
		}
		lMap.put(FLAG_PATH, path);
		lData.add(lMap);
		ladp.notifyDataSetChanged();
	}

	/**
	 * setRoot�������๦����ڵ㣬�������˸�Ŀ¼֮��,�࿪ʼ�����¼�Ŀ¼����Ϊ��ÿ���ļ�ϵͳ��ʹ�õ��಻ͬ������Ĺ����� ������Ҫ��д�˷�����
	 * 
	 * @param path
	 *            ��Ŀ¼
	 */
	public void setRoot(String path) {
		Log.v(TAG, "setRoot :" + path);
		File root;
		if (path == null) {
			root = Environment.getExternalStorageDirectory();
		} else {
			root = new File(path);
		}
		if (root.exists() && root.isDirectory()) {
			currentPath = root.getPath();
			setLData(root.getName(), currentPath);
			rData.clear();
			radp.notifyDataSetChanged();
			if (timer == null) {
				timer = new Timer();
			}
			timer.schedule(new MyTask(), 100);
		}
	}

	/**
	 * �õ�һ���ļ������ڱ����ļ�
	 * 
	 * @param coverOnExists
	 *            ΪTrueʱ�����ļ����ڸ����ļ������Ϊfalseʱ�᷵��null
	 * @return ������κ��쳣��������null
	 * @throws FileNotFoundException
	 */
	public FileOutputStream getFileOutputStream(boolean coverOnExists) {
		Date date = new Date();
		File f = new File(saveFileByFullPath);
		if (!f.getParentFile().exists()) {
			if (!f.getParentFile().mkdirs()) {
				Log.v(TAG, "����Ŀ¼�ṹʧ��");
				return null;
			}
		}

		if (f.exists()) {
			if (f.renameTo(new File(f.getPath() + "." + date.getTime() + ".OLD"))) {
				try {
					if (!f.createNewFile()) {
						Log.v(TAG, "�����ļ�ʧ��");
						return null;
					}
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			} else {
				Log.v(TAG, "������ʧ��");
				return null;
			}
		}
		try {
			return new FileOutputStream(saveFileByFullPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	// ����������������ѡ�����Ŀ
	public ArrayList<HashMap<String, String>> getCheckedItems() {
		return radp.getCheckedItems();
	}

	/**
	 * ���ڻ�����List��layout��Դ�����������д�˷����Եõ���ͬ��ʽ
	 * 
	 * @return ��ԴID
	 */
	public int GetLeftLayout() {
		return R.layout.list_item_left_sigleline;
	}

	/**
	 * ���ڻ���ұ�List��layout��Դ�����������д�˷����Եõ���ͬ��ʽ
	 * 
	 * @return ��ԴID
	 */
	public int GetRightLayout() {
		return R.layout.list_item_right_txtimgchksizedate;
	}

	// ��ʼ����Դ
	private void init() {
		leftPanel = new ListView(this.getContext());
		rightList = new ListView(this.getContext());

		LayoutParams lparams = new LayoutParams(100, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		lparams.topMargin = 2;
		lparams.rightMargin = 5;
		leftPanel.setLayoutParams(lparams);
		leftPanel.setId(0);
		leftPanel.setCacheColorHint(Color.TRANSPARENT);
		leftPanel.setPadding(5, 5, 5, 5);
		leftPanel.setBackgroundResource(R.drawable.filemgr_list_bg);
		leftPanel.setSelector(R.drawable.item_selector_v);

		LayoutParams rparams = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		rparams.topMargin = 2;
		rparams.rightMargin = 2;
		rightList.setLayoutParams(rparams);
		rightList.setId(1);
		rightList.setPadding(5, 5, 5, 5);
		rightList.setCacheColorHint(Color.TRANSPARENT);
		rightList.setBackgroundResource(R.drawable.filemgr_list_bg);
		rightList.setSelector(R.drawable.item_selector);

		this.addView(leftPanel);
		this.addView(rightList);

		// ��Ӽ�����
		leftPanel.setOnItemClickListener(new LeftListener());
		rightList.setOnItemClickListener(this);

		initLDataStructure();
		initRDataStructure();

		radp.setOnSetSingleCheckBoxVisibilityListener(this);
		leftPanel.setAdapter(ladp);
		rightList.setAdapter(radp);

		timer = new Timer();
	}

	private int getIconByFilename(String filename) {
		switch (Util.getFileType(filename)) {
		case Util.VEDIO:
			return R.drawable.moviefile;
		case Util.AUDIO:
			return R.drawable.musicfile;
		case Util.IMG:
			return R.drawable.picfile;
		case Util.TEXT:
			return R.drawable.txtfile;
		case Util.APP:
			return R.drawable.exefile;
		case Util.ZIP:
			return R.drawable.zipfile;
		}
		return R.drawable.unkownfile;
	}

	/**
	 * ���������д�˷����Ի�ö������ListView���ݽṹ������
	 */
	public void initLDataStructure() {
		lData = new ArrayList<HashMap<String, Object>>();

		ladp = new SimpleAdapter(this.getContext(), lData, GetLeftLayout(), new String[] { FLAG_NAME, FLAG_IMG },
				new int[] { R.id.leftitem_text, R.id.leftitem_img });
	}

	/**
	 * ���������д�˷����Ի�ö����ұ�ListView���ݽṹ������
	 */
	public void initRDataStructure() {
		rData = new ArrayList<FileInfo>();
		radp = new FileAdapter(this.getContext(), GetRightLayout(), rData);
	}

	@Override
	public void onItemClick(AdapterView<?> adp, View v, int position, long id) {
		setRoot(rData.get((int) id).getPath());
	}

	public void setCheckBoxVisibility(int visibility) {
		radp.setCheckBoxVisibility(visibility);
	}

	protected class LeftListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> adp, View v, int position, long id) {
			if (isLast(id)) {
				radp.checkedAll();
			} else if (!isFirst(id)) {
				removeAfterId((int) id);
			} else {
				if (hasMore(id)) {
					removeAfterId((int) id);
				} else {
					radp.checkedAll();
				}
			}
		}

		/**
		 * �Ƴ����list��item��IDΪid֮�������item������ǰ�ĵ�ǰ·������ΪID�����·����һ��������setRoot�ؽ�Ӱ���ұ�list
		 * 
		 * @param id
		 *            ���list�����itemID
		 */
		private void removeAfterId(int id) {
			String root = (String) lData.get(id).get("path");
			for (int i = lData.size() - 1; i >= id; i--) {
				lData.remove(i);
			}
			ladp.notifyDataSetChanged();
			setRoot(root);
		}

		/**
		 * �������list�����item֮���Ƿ�������item����
		 * 
		 * @param id
		 *            ���list�����itemID
		 * @return
		 */
		private boolean hasMore(long id) {
			if (lData.size() > id + 1) {
				return true;
			}
			return false;
		}

		/**
		 * �������list�����item�Ƿ�Ϊ���һ��item
		 * 
		 * @param id
		 *            ���list�����itemID
		 * @return
		 */
		private boolean isLast(long id) {
			if (lData.size() - 1 == id) {
				return true;
			}
			return false;
		}

		/**
		 * �������list�����item�Ƿ��ǵ�һ��
		 * 
		 * @param id
		 *            ���list�����itemID
		 * @return
		 */
		private boolean isFirst(long id) {
			if (id == 0) {
				return true;
			}
			return false;
		}
	}

	public static final String FLAG_NAME = "name";
	public static final String FLAG_PATH = "path";
	public static final String TYPE_DIR = "dir";
	public static final String TYPE_FILE = "file";
	public static final String FLAG_IMG = "img";
	public static final String FLAG_MSG_DATA = "data";

	// ��������д,����Ŀ¼�߼�,���ļ�ϵͳ���Ǳ����ļ�ϵͳʱ��Ҫ��д
	public class MyTask extends TimerTask {
		@Override
		public void run() {
			Log.v(TAG, "Task run");
			File rootFile = new File(currentPath);
			File[] files = rootFile.listFiles();
			if (files == null) {
				Log.v(TAG, "����ļ��б�ʧ��");
				return;
			}

			Arrays.sort(files, new FileComparator<File>());
			ArrayList<File> fs = new ArrayList<File>();
			for (File dir : files) {
				Message msg = new Message();
				Bundle data = new Bundle();
				FileInfo fInfo = new FileInfo();
				if (dir.isDirectory()) {
					fInfo.setName(dir.getName());
					fInfo.setPath(dir.getPath());
					fInfo.setType(TYPE_DIR);
					fInfo.setSize("");
					fInfo.setDate("");
				} else {
					fs.add(dir);
					continue;
				}
				data.putSerializable(FLAG_MSG_DATA, fInfo);
				msg.setData(data);
				handler.sendMessage(msg);
			}

			Iterator<File> it = fs.iterator();
			while (it.hasNext()) {
				final File file = it.next();
				final Message msg = new Message();
				final Bundle data = new Bundle();
				final FileInfo fInfo = new FileInfo();
				fInfo.setName(file.getName());
				fInfo.setPath(file.getPath());
				fInfo.setType(TYPE_FILE);
				
				long length = 0;
				long lastDate = 0;
				try {
					length = file.length();
					lastDate = file.lastModified();
				} catch (Exception e) {
					e.printStackTrace();
				}

				fInfo.setSize(Util.getFormatedSize(length));
				fInfo.setDate(Util.formatDate(new Date(lastDate)));
				data.putSerializable(FLAG_MSG_DATA, fInfo);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		}
	}

	/**
	 * ����˹����������д�Ի�õ�������CheckBox�ɼ��Ե�����,�˷���һֱ���ز��ɼ�
	 */
	@Override
	public int OnSetSingleCheckBoxVisibility(int position) {
		return View.GONE;
	}

	/**
	 * Ϊ�ļ��������ó���������
	 * 
	 * @param l
	 */
	public void setFileOnItemLongClickListener(OnItemLongClickListener l) {
		rightList.setOnItemLongClickListener(l);
	}

	/**
	 * ˢ�µ�ǰ�ļ��б�
	 */
	public void refresh() {
		if(currentPath != null)setRoot(currentPath);
	}
}
