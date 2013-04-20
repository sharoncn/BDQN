package rjfsdo.sharoncn.android.BDQN.ImageViewer;

import rjfsdo.sharoncn.android.BDQN.ImageViewer.Adapter.DataProvider;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Adapter.ImageAdapter;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Components.BaseMenu;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Components.DefaultMenu;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Components.Header;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Dialog.DeleteDialog;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Dialog.InfoDialog;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Dialog.MoreDialog;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Models.ImageInfo;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Utils.Constants;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 20130123��ʵMainActivity��ViewActivity������ͬ��Menu��Header���֡����Խ���Щ��ȡ��������BaseActivity��
 * Header������ȡһ�������ṩ��ͬ�Ľӿڡ���������Ļ�Ӧ�þ������ˡ�
 * �������˰ɡ�һ������Ŀ����û����չ������
 * @author sharoncn
 *
 */
public class MainActivity extends Activity implements OnItemClickListener{
	private static final String TAG = "MainActivity";
	private ImageView main_separator;
	private Header header;
	private GridView main_gview;
	private static ImageAdapter imageAdapter;
	private BaseMenu menu;
	private ProgressDialog loading_pd;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			imageAdapter.notifyDataSetChanged();
			imageAdapter.prepareData();
			changeStyleToNormal();
			loading_pd.dismiss();
			super.handleMessage(msg);
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_main);
        //��ø���View
        main_separator = (ImageView) findViewById(R.id.main_separator);
        header = (Header) findViewById(R.id.main_header);
        main_gview = (GridView) findViewById(R.id.main_gview);
        
        main_gview.setOnItemClickListener(this);
        main_gview.setCacheColorHint(Color.TRANSPARENT);
        main_gview.setOnItemLongClickListener(longClick);
        
        header.setOnBackClickListener(back_Click);
        header.setOnDropdownClickListener(dropdown_Clck);
        header.setBackButtonVisibility(View.GONE);
        header.setDropdownButtonVisibility(View.GONE);
        
        menu = (BaseMenu) findViewById(R.id.bottom_menu);
        menu.setOnMenuButtonClickListenter(menu_click);
        menu.setVisibility(View.GONE);
        
        loading_pd = new ProgressDialog(this);
		loading_pd.setMessage(getString(R.string.searching));
		loading_pd.setCancelable(false);
		loading_pd.setMax(100);
		loading_pd.show();
		
        new SearchImagesTask().start();
        imageAdapter = new ImageAdapter(this);
        main_gview.setAdapter(imageAdapter);
    }
    
    class SearchImagesTask extends Thread{
		public void run() {
			DataProvider.initData();
			handler.sendEmptyMessage(0);
		}
    }
    
    /**
     * ����������δѡ��״̬ʱ���
     */
    private void changeStyleToNormal(){
    	Log.v(TAG, "changeStyleToNormal");
    	main_separator.setVisibility(View.VISIBLE);
    	menu.setVisibility(View.GONE);
    	header.setBackButtonVisibility(View.GONE);
    	header.setDropdownButtonVisibility(View.GONE);
    	//���ñ���
        header.setTitle(String.format(getString(R.string.title_part), imageAdapter.getCount()));
        header.setBackgroundResource(R.raw.main_bg);
        //���÷ָ��� 
        main_separator.setImageResource(R.raw.separator);
        main_separator.setScaleType(ScaleType.FIT_XY);
    }
    
    /**
     * �ı���������Ϊѡ��״̬
     */
    private void changeStyleToSelected(){
    	Log.v(TAG, "changeStyleToSelected");
    	main_separator.setVisibility(View.GONE);
    	menu.setVisibility(View.VISIBLE);
    	//���ñ���
    	header.setTitle(String.format(getString(R.string.title_select_part), imageAdapter.getAllCheckedItems().size()));//"" + getResources().getString(R.string.title_select_partone) + 
    			//selectedItem.size() + getResources().getString(R.string.title_select_parttwo));
    	header.setBackgroundResource(R.raw.head_bg);
    	header.setBackButtonVisibility(View.VISIBLE);
    	header.setDropdownButtonVisibility(View.VISIBLE);
    }

	/**
	 * ����ͼƬѡ��״̬
	 * @param view     ����ͼƬ��View
	 * @param position ͼƬλ��
	 */
	private void setChecked(View view,int position){
		imageAdapter.setChecked(position);
		ImageView checked = (ImageView) view.findViewById(R.id.item_checked);
		checked.setVisibility(View.VISIBLE);
		if(imageAdapter.getAllCheckedItems().size() > 1){
			Log.v(TAG,"selectedItem.size():" + imageAdapter.getAllCheckedItems().size());
			menu.setViewVisibility(DefaultMenu.INDEX_INFO, View.GONE);
		}else{
			menu.setViewVisibility(DefaultMenu.INDEX_INFO, View.VISIBLE);
		}
		changeStyleToSelected();
	}
	
	/**
	 * ȡ��ͼƬѡ��״̬
	 * @param view     ����ͼƬ��View
	 * @param position ͼƬλ��
	 */
	private void setUnChecked(View view,int position){
		if(imageAdapter.getAllCheckedItems().contains("" + position)){
			imageAdapter.removeChecked("" + position);
		}
		ImageView checked = (ImageView) view.findViewById(R.id.item_checked);
		checked.setVisibility(View.GONE);
		Log.v(TAG,"selectedItem.size():" + imageAdapter.getAllCheckedItems().size());
		if(imageAdapter.getAllCheckedItems().size() == 0){
			changeStyleToNormal();
		}else if(imageAdapter.getAllCheckedItems().size() == 1){
			menu.setViewVisibility(1, View.VISIBLE);
		}
		if(imageAdapter.getAllCheckedItems().size() > 0){
			changeStyleToSelected();
		}
	}
	
	/**
	 * ɾ��ѡ�е�ͼƬ
	 */
	private void deleteSelected(){
		imageAdapter.removeAllCheckedItem();
		imageAdapter.notifyDataSetChanged();
		changeStyleToNormal();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//����ͼƬ�鿴Activity���أ���������ѡ��״̬
		if(requestCode == Constants.MAINACT_REQUEST){
			imageAdapter.removeAllChecked();
			imageAdapter.notifyDataSetChanged();
			changeStyleToNormal();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	//������s
	/**
     * ͼƬ����������
     */
	public void onItemClick(AdapterView<?> adpView, View view, int position, long id) {
		Log.v(TAG,"onItemClick");
		Intent intent = new Intent();
		intent.setClass(this, ViewActivity.class);
		intent.putExtra("position", position);
		this.startActivityForResult(intent,Constants.MAINACT_REQUEST);
	}
	
	/**
	 * ͼƬ����������
	 */
	private OnItemLongClickListener longClick = new OnItemLongClickListener() {

		public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
			Log.v(TAG,"onItemLongClick");
			if(!imageAdapter.getAllCheckedItems().contains("" + position)){
				setChecked(view,position);
			}else{
				setUnChecked(view,position);
			}
			return true;
		}
	};
	
	//�ײ�menu�ļ�����
	private View.OnClickListener menu_click = new View.OnClickListener() {
		public void onClick(View v) {
			switch(v.getId()){
			case DefaultMenu.ID_DELETE:
				DeleteDialog delete_dlg = new DeleteDialog(MainActivity.this);
				delete_dlg.setTitle(R.string.delete)
				.setCancelable(false)
				.setMessage(R.string.delete_message)
				.setPositiveButton(R.string.cancel, dialog_cancel)
				.setNegativeButton(R.string.ok, dialog_ok)
				.show();
				break;
			case DefaultMenu.ID_INFO:
				int position = Integer.parseInt(imageAdapter.getAllCheckedItems().get(0));
				ImageInfo image = (ImageInfo) imageAdapter.getItem(position);
				InfoDialog dlg = new InfoDialog(MainActivity.this);
				dlg.setTitle(getString(R.string.dialog_ditail));
				dlg.setName(String.format(getString(R.string.dialog_nameis),image.getName()))
				.setPath(String.format(getString(R.string.dialog_pathis),image.getPath()))
				.setTime(String.format(getString(R.string.dialog_timeis),image.getFormatedTime()))
				.setSize(String.format(getString(R.string.dialog_sizeis),image.getFormatedSize()))
				.show();
				break;
			case DefaultMenu.ID_MORE:
				MoreDialog more_dlg = new MoreDialog(MainActivity.this);
				more_dlg.setItems(new String[]{getString(R.string.back),getString(R.string.more)}, more_click)
				.show();
				break;
			}
		}
	};
	
	//���ఴť��dailog������
	private OnClickListener more_click = new OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch(which){
			case 0:
				Log.v(TAG,"���ر����");
				prepareExit();
				break;
			case 1:
				Log.v(TAG,"���౻���");
				break;
			}
		}
	};
	
	//ɾ��dialog��ȡ��������
	private OnClickListener dialog_cancel = new OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};
	
	//ɾ��dialog��ȷ��������
	private OnClickListener dialog_ok = new OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			deleteSelected();
		}
	};
	
	//���ؼ�����
	private View.OnClickListener back_Click = new View.OnClickListener() {
		public void onClick(View v) {
			prepareExit();
		}
	};
	
	//����������
	private View.OnClickListener dropdown_Clck = new View.OnClickListener() {
		public void onClick(View v) {
			
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			prepareExit();
		}
		return true;
	}
	
	private void prepareExit(){
		if(imageAdapter.getAllCheckedItems().size() > 0){
			imageAdapter.removeAllChecked();
			imageAdapter.notifyDataSetChanged();
			changeStyleToNormal();
		}else{
			finish();
		}
	}
}