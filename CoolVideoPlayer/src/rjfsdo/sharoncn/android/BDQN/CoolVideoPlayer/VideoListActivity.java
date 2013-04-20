package rjfsdo.sharoncn.android.BDQN.CoolVideoPlayer;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.CoolVideoPlayer.Models.VideoInfo;
import rjfsdo.sharoncn.android.BDQN.CoolVideoPlayer.Utils.Utils;
import rjfsdo.sharoncn.android.BDQN.CoolVideoPlayer.adapter.VideoListAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;
import cn.com.jbit.coolvideoplayer.R;

public class VideoListActivity extends ListActivity {
	private VideoListAdapter videoListAdapter;
	private List<VideoInfo> videos = new ArrayList<VideoInfo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.layout_videolist);
		this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.inc_activitys_title);
		
		videoListAdapter = new VideoListAdapter(this, getVideoList(Utils.getSdCardPath() + "/Video"));
		this.setListAdapter(videoListAdapter);
	}
	
	private List<VideoInfo> getVideoList(String path){
		Log.v("getVideoList",path);
		File root = new File(path);
		File[] files;
		FileFilter filter = Utils.getFilter(); 
		files = root.listFiles(filter);
		VideoInfo video;
		for(File file:files){
			if(file.isDirectory() && file.canRead()){
				getVideoList(file.getPath());
			}else{
				video = new VideoInfo();
				video.setName(file.getName());
				video.setSize(Utils.toMB(file.length()));
				video.setThumbnail(ThumbnailUtils.createVideoThumbnail(file.getPath(), Thumbnails.MINI_KIND));
				video.setPath(file.getPath());
				videos.add(video);
			}
		}
		return videos;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		VideoInfo video = videos.get(position);
		if(video.getPath() != null && !"".equals(video.getPath())){
			Intent intent = new Intent(this,MainActivity.class);
			intent.putExtra("path", video.getPath());
			this.startActivity(intent);
		}else{
			Toast.makeText(this, R.string.video_path_noexist, Toast.LENGTH_LONG);
		}
		
	}
	
	
}
