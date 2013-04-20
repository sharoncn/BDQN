package rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.RatingActivity;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Adapter.GestureListenerAdapter;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Dialog.DefaultDialog;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Extension.GetMoreSetup;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.SDCard.TabMainActivity;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Utils.Constants;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Utils.Utils;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Data.BookInfoDaoImpl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * ���ݹ�����Intent����keyΪ��picPath ��ͼƬ·����ע����ͼƬ��·��������ͼƬĿ¼·��
 * ����Ҫ����keyΪ��bookId,�Ի�õ�ǰ�Ķ���������Ϣ
 * @author sharoncn
 *
 */
public class MainActivity extends BaseActivity {
	private static final String TAG = "MainActivity";
	RelativeLayout layout2;//�˵�����
	RelativeLayout layout3;//�˵��в�
	RelativeLayout layout4;//�˵��ײ�
	ImageView imageView;//����ͼƬ
	TextView imageName;//��������
	TextView pagePosition;//����ͼƬ��ǰλ��
	ImageButton openSDcard;//��SDcard��
	ImageButton page;//��ҳ
	ImageButton zoomSmall;//���� ��С
	ImageButton zoomBig;//���� �Ŵ�
	ImageButton bottom_menu_home;//��ǩ
	ImageButton logout;//�˳�
	ImageButton setup;//����
	protected ImageButton lastPage;//��һҳ
	ImageButton nextPage;//��һҳ
	String bookMarks;
	String[] imageArray;//ͼƬ����
	Map<String,String> startPosition;//ͼƬ��ʼλ��
	int imageIndex;//ͼƬ����
	int disWidth;//��
	int disHeight;//��
	Handler handler;
	protected GestureDetector gestureScanner;
	int picIndex;
	GetMoreSetup gms;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main_cartoonreader);
		allActivity.add(this);
		//��ø������
		layout2 = (RelativeLayout) findViewById(R.id.layout2);
		layout3 = (RelativeLayout) findViewById(R.id.layout3);
		layout4 = (RelativeLayout) findViewById(R.id.layout4);
		openSDcard = (ImageButton) findViewById(R.id.openSDcard);
		page = (ImageButton) findViewById(R.id.page);
		zoomSmall = (ImageButton) findViewById(R.id.zoomSmall);
		zoomBig = (ImageButton) findViewById(R.id.zoomBig);
		bottom_menu_home = (ImageButton) findViewById(R.id.bottom_menu_home);
		logout = (ImageButton) findViewById(R.id.logout);
		setup = (ImageButton) findViewById(R.id.setup);
		imageView = (ImageView) findViewById(R.id.imageView);
		lastPage = (ImageButton) findViewById(R.id.lastPage);
		nextPage = (ImageButton) findViewById(R.id.nextPage);
		imageName = (TextView) this.findViewById(R.id.imageName);
		pagePosition = (TextView) findViewById(R.id.pagePosition);
		//�����¼�
		gestureScanner = new GestureDetector(gestureListener_adpter);
		//��ȡͼƬ�б�
		imageList = loadImages();
		if(imageList != null && imageList.size() > 0){
			imageArray = imageList.toArray(new String[]{});
		}else{
			//û������ͼƬʱ�����еĲ˵�����ʾ����
			layout2.setVisibility(View.VISIBLE);
			layout3.setVisibility(View.VISIBLE);
			layout4.setVisibility(View.VISIBLE);
			Utils.showMsg(this, R.string.noPic);
		}
		//����ֻ��ֱ���
		DisplayMetrics dm = this.getResources().getDisplayMetrics();
		disWidth = dm.widthPixels;
		disHeight = dm.heightPixels;
		//���ü����¼�
		imageView.setOnClickListener(hiddenMenu);
		imageView.setLongClickable(true);
		openSDcard.setOnClickListener(openSDcardButton);
		page.setOnClickListener(pageButton);
		zoomSmall.setOnClickListener(zoomButton);
		zoomBig.setOnClickListener(zoomButton);
		bottom_menu_home.setOnClickListener(homeButton);
		logout.setOnClickListener(logoutButton);
		setup.setOnClickListener(setupButton);
		lastPage.setOnClickListener(pageTurnButton);
		nextPage.setOnClickListener(pageTurnButton);
		//ʵ����Handler
		handler = new Handler();
		//��ʼ������
		initLoadImages();
		//��������
		gms = new GetMoreSetup(this,imageView,imagePosition,imageList,handler);
	}
	
	/**
	 * ���ص�������ͼƬ���ļ����µ�����ͼƬ�б�
	 * @return LinkedList
	 */
	public LinkedList<String> loadImages(){
		LinkedList<String> list = new LinkedList<String>();
		//����ļ�·��
		String filePath = getPicPath();
		if(filePath != null){
			//���filePath��·���������ļ���������File[] files������
			File[] files = Utils.getPicOrder((new File(filePath)).getParent());
			if(files != null){
				for(File file:files){
					if(Utils.isPic(file.getPath())){
						list.add(file.getPath());
					}
				}
				int i = 0;
				for(String file:list){
					if(file.equals(filePath)){
						picIndex = i;
						break;
					}
					i += 1;
				}
			}
		}
		
		return list;
	}
	
	/**
	 * ��ʼ��ͼƬ
	 */
	public void initLoadImages(){
		//���ͼƬ·��
		String picPath = getPicPath();
		Log.v(TAG,"initLoadImages:" + picPath);
		if(picPath != null){
			if(imageArray != null){
				//��ȡ��ǰͼƬλ��
				int i = getPicPosition(picPath,imageArray);
				//������ʾ��ͼƬ
				setImageView(i);
			}
		}
	}
	
	//�����¼�
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		gestureScanner.onTouchEvent(ev);
		imageView.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * ����Intent��õ�����ͼƬ��·��
	 * @return String
	 */
	public String getPicPath(){
		Bundle bundle = this.getIntent().getExtras();
		if(bundle != null){
			Log.v(TAG,"getPicPath --- picPath:" + bundle.getString("picPath"));
			if(bundle.containsKey("bookId")){
				bookId = bundle.getString("bookId");
				dao = BookInfoDaoImpl.getInstance(this);
				dao.setLastReadTime(bookId, rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Utils.formatDate(new Date()));
			}else{
				bookId = null;
			}
			return bundle.getString("picPath");
		}else{
			return Utils.getFileRead(Constants.HISTORY);
		}
	}
	
	/**
	 * �ѷŴ������С��λͼ��ֵ��ImageView
	 * @param index ������ͼƬ���±�
	 */
	public void setImageView(int index){
		imagePosition.put("positionId", "" + index);
		imageView.getImageMatrix().reset();
		imageView.setImageBitmap(BitmapFactory.decodeFile(imageArray[index]));
		setPageInfo();
	}
	
	public void setImageView(Bitmap target){
		//imagePosition.put("positionId", "" + index);
		//setPageInfo();
		imageView.setImageBitmap(target);
	}
	
	/**
	 * ��ȡ��ǰͼƬ��λ��
	 * @param picPath
	 * @param imagesArray
	 * @return
	 */
	public int getPicPosition(String picPath,String[] imagesArray){
		int position = 0;
		for(int i= 0;i < imagesArray.length;i++){
			Log.v(TAG,"ͼƬ�б��е�ǰΪ:" + imagesArray[i]);
			if(picPath.equals(imagesArray[i])){
				Log.v(TAG,"����������Ϊ:" + i);
				position = i;
				break;
			}
		}
		Log.v(TAG,"��������������Ϊ:" + position);
		return position;
	}
	
	public OnClickListener hiddenMenu = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//����������ͼƬʱ�����ػ���ʾ�˵�
			if(imageList != null){
				//��ʾ�����²˵�
				layout2.setVisibility(View.VISIBLE);
				layout3.setVisibility(View.VISIBLE);
				layout4.setVisibility(View.VISIBLE);
				//5s֮���Զ����ز˵�
				handler.postDelayed(hideMenu, 5000);
			}
		}
	};

	//Come on����û��GestureListenerAdapter
	/**
	 * �����¼� ���Լ�������һ�� Ŀǰû�з������ﶨ���˴���
	 */
	public GestureListenerAdapter gestureListener_adpter = new GestureListenerAdapter(){
		@Override
		public void doOnFling(String action) {
			if(imageList != null && imageList.size() > 0){
				if(action.equals("right")){
					//��һҳ
					if(picIndex > 0){
						picIndex -= 1;
						setImageView(picIndex);
					}
				}else if(action.equals("left")){
					//��һҳ
					if(picIndex < imageList.size() - 1){
						picIndex += 1;
						setImageView(picIndex);
					}else{
						if(bookId != null){
							Intent intent = new Intent(MainActivity.this,RatingActivity.class);
							intent.putExtra("bookId", bookId);
							startActivity(intent);
						}else{
							Utils.showMsg(MainActivity.this, R.string.pageEnd);
						}
					}
				}
			}
		}
	};
	
	/**
	 * ͨ��Handler���ز˵�
	 */
	private Runnable hideMenu = new Runnable(){
		@Override
		public void run() {
			layout2.setVisibility(View.GONE);
			layout3.setVisibility(View.GONE);
			layout4.setVisibility(View.GONE);
			handler.removeCallbacks(hideMenu);
		}
	};
	
	/**
	 * ����ͼƬ��Ϣ
	 */
	
	public void setPageInfo(){
		if(imageList != null){
			if(imagePosition != null){
				//��չ
				String picPath = Utils.getImagePath(imagePosition, imageList);
				if(picPath != null){
					File file = new File(picPath);
					picPath = file.getParent();
					Log.v(TAG,"setPageInfo:" + picPath);
					imageName.setText((new File(picPath)).getName());
				}
				String page = getImagePagePosition(imagePosition,imageList);
				pagePosition.setText(page);
			}
		}else{
			Utils.showNoPicMsg(this);
		}
	}
	
	/**
	 * ��ȡ��ǰͼƬ��λ��
	 * @param imagePosition
	 * @param imageList
	 * @return String
	 */
	public String getImagePagePosition(Map<String,String> imagePosition,LinkedList<String> imageList){
		String pagePosition = null;
		if(imagePosition != null){
			StringBuffer sbf = new StringBuffer();
			//��ǰҳ��������ϵ�� page = index + 1
			int page = Integer.parseInt(imagePosition.get("positionId")) + 1;
			int totalPages = imageList.size();
			sbf.append(String.valueOf(page));
			sbf.append("/");
			sbf.append(String.valueOf(totalPages));
			pagePosition = sbf.toString();
			sbf = null;
		}
		return pagePosition;
	}
	/**
	 * ��ȡ�ֻ��ֱ���
	 */
	public void getDisplayMetrics(){
		/* ȡ�÷ֱ��ʵĴ�С*/
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		disWidth = dm.widthPixels;
		disHeight = dm.heightPixels;
	}
	
	/**
	 * ���浱ǰ�Ķ���������Ϣ
	 */
	@Override
	protected void onPause() {
		Log.v(TAG,"MainActivity-onPause");
		try{
			//��ȡ��ǰͼƬ·��
			//�Ի�ȡ��ǰͼƬ·��������չ
			String picPath = Utils.getImagePath(imagePosition, imageList);
			if(picPath != null){
				//false��ʾ����Դ�ļ�������׷�ӣ����½���һ�����ļ�
				Utils.saveFile(Constants.HISTORY, picPath, false);
			}
		}catch(Exception e){
			Log.i(TAG,e.getMessage() + "");
		}
		super.onPause();
	}
	
	/**
	 * ����ָ�����ҳ����ת��leftΪ��һҳ��rightΪ��һҳ
	 * @param String left��right
	 */
	public void getArrayAtBitmap(String str){
		if(imageList != null){
			//��ʼ��imagePosition��������Һܶ�ط�����ʼ����Ϊʲô����baseActivity����ֱ�ӳ�ʼ���أ�
			int len = imageArray.length;
			//��һҳ����
			if(str.equals("left")){
				if(picIndex >= 1){
					int i = picIndex - 1;
					imagePosition.put("positionId", "" + i);
					setImageView(i);
					picIndex = i;
				}else{
					Utils.showMsg(this,R.string.pageFirst);
				}
			}
			//��һҳ����
			if(str.equals("right")){
				if(picIndex < len -1){
					int i = picIndex + 1;
					imagePosition.put("positionId", "" + i);
					setImageView(i);
					picIndex = i;
				}else{
					Utils.showMsg(this,R.string.pageEnd);
				}
			}
		}else{
			Utils.showNoPicMsg(this);
		}
		//���õ�ǰ������ͼƬ��Ϣ
		setPageInfo();
	}
	
	
	//���м�����
	private OnClickListener openSDcardButton = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(bookId != null && dao != null){
				Log.i(TAG,"��������Ķ�λ��:" + imagePosition.get("positionId"));
				dao.setLastReadedPosition(bookId, imagePosition.get("positionId"));
			}
			Intent intent = new Intent(MainActivity.this,TabMainActivity.class);
			//��õ�ǰͼƬ·��
			if(imageList != null && imageList.size() > 0){
				String picPath = Utils.getImagePath(imagePosition,imageList);
				if(picPath != null && picPath.length() > 0){
					Bundle bundle = new Bundle();
					bundle.putString("picPath", picPath);
					intent.putExtras(bundle);
				}
			}
			startActivity(intent);
		}
	};
	
	/**
	 * ��ҳ����
	 */
	private OnClickListener pageButton = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//������������ʾ��һҳ����һҳ����һҳ�����һҳ
			String[] pageArray = new String[]{getString(R.string.firstPage),getString(R.string.beforePage),
					getString(R.string.nextPage),getString(R.string.lastPage)};
			//�����Զ���Ի���falseΪ����ʾȷ����ť����֮��ʾ
			//�Զ���Ի���
			DefaultDialog pageDialog = new DefaultDialog(MainActivity.this,pageArray,false){
				@Override
				protected void doItems(int which){
					int position = Integer.parseInt(imagePosition.get("positionId"));
					switch(which){
					case 0:
						if(position > 0){
							imageIndex = 0;
							picIndex = 0;
							setImageView(imageIndex);
						}else{
							Utils.showMsg(MainActivity.this, R.string.pageFirst);
						}
						break;
					case 1:
						if(position >= 1){
							imageIndex = position - 1;
							picIndex = position - 1;
							setImageView(imageIndex);
						}else{
							Utils.showMsg(MainActivity.this, R.string.pageFirst);
						}
						break;
					case 2:
						if(position < imageArray.length - 1){
							imageIndex = position + 1;
							picIndex = position + 1;
							setImageView(imageIndex);
						}else{
							Utils.showMsg(MainActivity.this, R.string.pageEnd);
						}
						break;
					case 3:
						if(position != imageArray.length - 1){
							imageIndex = imageArray.length - 1;
							picIndex = imageArray.length -1;
							setImageView(imageIndex);
						}else{
							Utils.showMsg(MainActivity.this, R.string.pageEnd);
						}
					}
				}
				@Override
				protected void doPositive(){}
			};
			pageDialog.setTitle(R.string.pageTitle);
			pageDialog.show();
		}
	};
	
	private OnClickListener zoomButton = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(imageList != null && imageList.size() > 0){
				//��ȡ��ǰͼƬ·��
				String picPath = Utils.getImagePath(imagePosition, imageList);
				String action = "";
				if(v == zoomSmall){
					action = "small";
				}else{
					action = "big";
				}
				Log.v(TAG,"�������ͼƬ��ť ����Ļ�߿�{" + disWidth + ":" + disHeight + "}");
				//ʵ�ֶ�ͼƬ���ŷ���
				Bitmap bitmap = Utils.imageZoom(picPath,disWidth,disHeight,action);
				if(bitmap != null){
					setImageView(bitmap);
					bitmap = null;
				}
			}
		}
	};
	
	private OnClickListener homeButton = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this,rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.TabMainActivity.class);
			startActivity(intent);
			//promptExit(MainActivity.this);
			exitApp(MainActivity.this);
		}
	};
	
	private OnClickListener logoutButton = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//����BaseActivity�е��˳�
			promptExit(MainActivity.this);
			//ʵ�ֶ��˳��Ի��������չ��3.10��
		}
	};

	private OnClickListener setupButton = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//���ø������öԻ���
			gms.showDialog();
			//ʵ�ֶԸ������õĶԻ��������չ��3.11��
		}
	};
	
	public OnClickListener page_slideButton = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(v == lastPage){
				getArrayAtBitmap("left");
			}else{
				getArrayAtBitmap("right");
			}
		}
	};
	
	public OnClickListener pageTurnButton = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int position = Integer.parseInt(imagePosition.get("positionId"));
			switch(v.getId()){
			case R.id.lastPage:
				if(position >= 1){
					imageIndex = position - 1;
					picIndex = position - 1;
					setImageView(imageIndex);
				}else{
					Utils.showMsg(MainActivity.this, R.string.pageFirst);
				}
				break;
			case R.id.nextPage:
				if(position < imageArray.length - 1){
					imageIndex = position + 1;
					picIndex = position + 1;
					setImageView(imageIndex);
				}else{
					if(bookId != null){
						Intent intent = new Intent(MainActivity.this,RatingActivity.class);
						intent.putExtra("bookId", bookId);
						startActivity(intent);
					}else{
						Utils.showMsg(MainActivity.this, R.string.pageEnd);
					}
				}
			}
		}
	};
}