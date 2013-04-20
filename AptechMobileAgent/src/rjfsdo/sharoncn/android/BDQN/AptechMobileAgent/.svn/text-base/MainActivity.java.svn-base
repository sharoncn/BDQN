package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent;

import java.util.LinkedList;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Adapter.CustomGridAdapter;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Adapter.CustomGridAdapter.GridDataInfo;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.AppsManager.AppsManagerActivity;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.CommunicationManager.CommunicationActivity;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Components.CustomGrid;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.FilesManager.FileManagerActivity;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.PowerManager.PowerActivity;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.PrivacyManager.PrivacyActivity;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.TrafficManager.TrafficActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnItemClickListener, OnClickListener {
    private CustomGrid grid;
    private CustomGridAdapter adapter;
    private static Resources res;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_act_main);
        
        if(res == null){
        	res = getResources();
        }
        
        grid = (CustomGrid) findViewById(R.id.main_grid);
        adapter = new CustomGridAdapter(this);
        adapter.prepareData(prepareData());
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(this);
    }

    private LinkedList<GridDataInfo> prepareData(){
    	final LinkedList<GridDataInfo> data = new LinkedList<GridDataInfo>();
    	data.add(new GridDataInfo(this.getString(R.string.privacy_mgr), res.getDrawable(R.drawable.list_2_normal),
				res.getDrawable(R.drawable.list_2_pressed), PrivacyActivity.class));
		data.add(new GridDataInfo(this.getString(R.string.file_mgr), res.getDrawable(R.drawable.list_3_normal), res
				.getDrawable(R.drawable.list_3_pressed), FileManagerActivity.class));
		data.add(new GridDataInfo(this.getString(R.string.power_mgr), res.getDrawable(R.drawable.list_4_normal), res
				.getDrawable(R.drawable.list_4_pressed), PowerActivity.class));
		data.add(new GridDataInfo(this.getString(R.string.communication_mgr), res
				.getDrawable(R.drawable.list_0_normal), res.getDrawable(R.drawable.list_0_pressed),
				CommunicationActivity.class));
		data.add(new GridDataInfo(this.getString(R.string.apps_mgr), res.getDrawable(R.drawable.list_1_normal), res
				.getDrawable(R.drawable.list_1_pressed), AppsManagerActivity.class));
		data.add(new GridDataInfo(this.getString(R.string.traffic_mgr), res.getDrawable(R.drawable.list_5_normal),
				res.getDrawable(R.drawable.list_5_pressed), TrafficActivity.class));
		return data;
    }
    
	@Override
	public void onItemClick(AdapterView<?> adpView, View view, int position, long id) {
		final GridDataInfo data = (GridDataInfo) adapter.getItem(position);
		if(data != null){
			final Intent intent = new Intent(this,data.getClazz());
			startActivity(intent);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle(R.string.exit)
			.setMessage(String.format(getString(R.string.exit_confirm), getString(R.string.app_name)))
			.setPositiveButton(R.string.ok, this)
			.setNegativeButton(R.string.cancel, this)
			.show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch(which){
		case DialogInterface.BUTTON_POSITIVE:
			finish();
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			dialog.dismiss();
			break;
		}
		
	}
}