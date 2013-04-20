package rjfsdo.sharoncn.android.BDQN.ImageViewer;

import rjfsdo.sharoncn.android.BDQN.ImageViewer.Adapter.GalleryAdapter;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Components.DefaultMenu;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Components.MyGallery;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Components.ViewHeader;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Dialog.DeleteDialog;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Dialog.InfoDialog;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Dialog.MoreDialog;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Models.ImageInfo;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class ViewActivity extends Activity {
	private static final String TAG = "ViewActivity";
	private ViewHeader header;
	private DefaultMenu menu;
	private MyGallery gallery;
	private Handler handler;
	private GalleryAdapter adapter;
	private int position = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.layout_view);
		
		handler = new Handler();
		
		menu = (DefaultMenu) findViewById(R.id.view_menu);
		gallery = (MyGallery) findViewById(R.id.view_gallery);
		header = (ViewHeader) findViewById(R.id.view_header);
		
		header.setOnPreviousClickListener(previous_click);
		header.setOnNextClickListener(next_click);
		header.setBackgroundResource(android.R.color.transparent);
		menu.setOnMenuButtonClickListenter(menu_click);
		menu.setSeparatorVisibility(View.VISIBLE);
		menu.setBackgroundResource(android.R.color.transparent);
		
		Bundle bundle = getIntent().getExtras();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int position = bundle.getInt("position");
		adapter = new GalleryAdapter(this,dm.widthPixels/2,dm.heightPixels/2);
		gallery.setAdapter(adapter);
		gallery.setSelection(position);
		gallery.setOnItemClickListener(gallery_item_click);
		gallery.setOnItemSelectedListener(gallery_select);
	}
	
	
	//监听器
	//gallery监听器
	private OnItemSelectedListener gallery_select = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> adpView, View view, int position,
				long id) {
			ViewActivity.this.position = position;
			header.setTitle(String.format(getString(R.string.view_title_info), position + 1,adpView.getAdapter().getCount()));
		}

		public void onNothingSelected(AdapterView<?> adpView) {}
	};
	
	//gallery点击监听器
	private OnItemClickListener gallery_item_click = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> adpView, View view, int position,
				long id) {
			header.setVisibility(View.VISIBLE);
			menu.setVisibility(View.VISIBLE);
			handler.removeCallbacksAndMessages(null);
			handler.postDelayed(new DisplayRunnable(), 5000);
			
		}
	};
	
	//向前按钮监听器
	private OnClickListener previous_click = new OnClickListener() {
		public void onClick(View v) {
			Log.v(TAG,"previous_click");
			if(position < 0){
				return;
			}
			if(position == 0){
				gallery.setSelection(adapter.getCount() - 1);
			}else{
				gallery.setSelection(position - 1);
			}
		}
	};
	
	//向后按钮监听器
	private OnClickListener next_click = new OnClickListener() {
		public void onClick(View v) {
			Log.v(TAG,"next_click");
			if(position < 0){
				return;
			}
			if(position == adapter.getCount()){
				gallery.setSelection(0);
			}else{
				gallery.setSelection(position + 1);
			}
		}
	};
	
	//菜单项监听器
	private OnClickListener menu_click = new OnClickListener() {
		public void onClick(View v) {
			switch(v.getId()){
			case DefaultMenu.ID_DELETE://删除按钮被点击
				DeleteDialog delete_dlg = new DeleteDialog(ViewActivity.this);
				delete_dlg.setTitle(R.string.delete)
				.setCancelable(false)
				.setMessage(R.string.delete_message)
				.setPositiveButton(R.string.cancel, dialog_cancel)
				.setNegativeButton(R.string.ok, dialog_ok)
				.show();
				
				break;
			case DefaultMenu.ID_INFO://文件信息按钮被点击
				if(position < -1){
					position = 0;
				}
				ImageInfo image = (ImageInfo) adapter.getItem(position);
				InfoDialog dlg = new InfoDialog(ViewActivity.this);
				dlg.setTitle(getString(R.string.dialog_ditail));
				dlg.setName(String.format(getString(R.string.dialog_nameis),image.getName()))
				.setPath(String.format(getString(R.string.dialog_pathis),image.getPath()))
				.setTime(String.format(getString(R.string.dialog_timeis),image.getFormatedTime()))
				.setSize(String.format(getString(R.string.dialog_sizeis),image.getFormatedSize()))
				.show();
				break;
			case DefaultMenu.ID_MORE://更多按钮被点击
				MoreDialog more_dlg = new MoreDialog(ViewActivity.this);
				more_dlg.setItems(new String[]{getString(R.string.back),getString(R.string.more)}, more_click)
				.show();
				break;
			}
		}
	};
	
	//dialog的取消监听器
	private DialogInterface.OnClickListener dialog_cancel = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};
	
	//dialog的确定监听器
	private DialogInterface.OnClickListener dialog_ok = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			adapter.deleteImage(gallery.getSelectedItemPosition());
			adapter.notifyDataSetChanged();
		}
	};
	
	//更多按钮弹出的dialog监听器
	private DialogInterface.OnClickListener more_click = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch(which){
			case 0:
				Log.v(TAG,"返回被点击");
				ViewActivity.this.setResult(Activity.RESULT_OK);
				ViewActivity.this.finish();
				break;
			case 1:
				Log.v(TAG,"更多被点击");//更多是干什么的不知道，视频上也没介绍，需求文档也没说
				break;
			}
		}
	};
	
	//隐藏menu和header的runnable
	class DisplayRunnable implements Runnable{
		public void run() {
			header.setVisibility(View.GONE);
			menu.setVisibility(View.GONE);
		}
	}
}
