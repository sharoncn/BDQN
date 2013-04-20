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
 * 20130123其实MainActivity和ViewActivity具有相同的Menu和Header布局。可以将这些提取出来放入BaseActivity中
 * Header可以提取一个父类提供共同的接口。如果这样的话应该就完美了。
 * 还是算了吧。一次性项目，又没有扩展的需求。
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
        //获得各个View
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
     * 将主界面风格未选中状态时风格
     */
    private void changeStyleToNormal(){
    	Log.v(TAG, "changeStyleToNormal");
    	main_separator.setVisibility(View.VISIBLE);
    	menu.setVisibility(View.GONE);
    	header.setBackButtonVisibility(View.GONE);
    	header.setDropdownButtonVisibility(View.GONE);
    	//设置标题
        header.setTitle(String.format(getString(R.string.title_part), imageAdapter.getCount()));
        header.setBackgroundResource(R.raw.main_bg);
        //设置分隔线 
        main_separator.setImageResource(R.raw.separator);
        main_separator.setScaleType(ScaleType.FIT_XY);
    }
    
    /**
     * 改变主界面风格为选中状态
     */
    private void changeStyleToSelected(){
    	Log.v(TAG, "changeStyleToSelected");
    	main_separator.setVisibility(View.GONE);
    	menu.setVisibility(View.VISIBLE);
    	//设置标题
    	header.setTitle(String.format(getString(R.string.title_select_part), imageAdapter.getAllCheckedItems().size()));//"" + getResources().getString(R.string.title_select_partone) + 
    			//selectedItem.size() + getResources().getString(R.string.title_select_parttwo));
    	header.setBackgroundResource(R.raw.head_bg);
    	header.setBackButtonVisibility(View.VISIBLE);
    	header.setDropdownButtonVisibility(View.VISIBLE);
    }

	/**
	 * 设置图片选中状态
	 * @param view     容纳图片的View
	 * @param position 图片位置
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
	 * 取消图片选中状态
	 * @param view     容纳图片的View
	 * @param position 图片位置
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
	 * 删除选中的图片
	 */
	private void deleteSelected(){
		imageAdapter.removeAllCheckedItem();
		imageAdapter.notifyDataSetChanged();
		changeStyleToNormal();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//当从图片查看Activity返回，清理所有选中状态
		if(requestCode == Constants.MAINACT_REQUEST){
			imageAdapter.removeAllChecked();
			imageAdapter.notifyDataSetChanged();
			changeStyleToNormal();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	//监听器s
	/**
     * 图片单击监听器
     */
	public void onItemClick(AdapterView<?> adpView, View view, int position, long id) {
		Log.v(TAG,"onItemClick");
		Intent intent = new Intent();
		intent.setClass(this, ViewActivity.class);
		intent.putExtra("position", position);
		this.startActivityForResult(intent,Constants.MAINACT_REQUEST);
	}
	
	/**
	 * 图片长按监听器
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
	
	//底部menu的监听器
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
	
	//更多按钮的dailog监听器
	private OnClickListener more_click = new OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch(which){
			case 0:
				Log.v(TAG,"返回被点击");
				prepareExit();
				break;
			case 1:
				Log.v(TAG,"更多被点击");
				break;
			}
		}
	};
	
	//删除dialog的取消监听器
	private OnClickListener dialog_cancel = new OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};
	
	//删除dialog的确定监听器
	private OnClickListener dialog_ok = new OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			deleteSelected();
		}
	};
	
	//返回监听器
	private View.OnClickListener back_Click = new View.OnClickListener() {
		public void onClick(View v) {
			prepareExit();
		}
	};
	
	//下拉监听器
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