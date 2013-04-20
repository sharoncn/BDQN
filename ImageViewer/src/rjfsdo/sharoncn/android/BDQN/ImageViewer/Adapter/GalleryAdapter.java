package rjfsdo.sharoncn.android.BDQN.ImageViewer.Adapter;

import java.util.List;

import rjfsdo.sharoncn.android.BDQN.ImageViewer.Models.ImageInfo;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Utils.Util;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryAdapter extends BaseAdapter {
	private List<ImageInfo> data;
	private Context context;
	private int width, height;

	public GalleryAdapter(Context context, int width, int height) {
		super();
		this.context = context;
		this.width = width;
		this.height = height;
		data = DataProvider.getAllImages();
	}

	public int getCount() {
		if (data != null) {
			return data.size();
		}
		return 0;
	}

	public Object getItem(int position) {
		if (data != null) {
			return data.get(position);
		}
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new ImageView(context);
		}
		ImageView image = (ImageView) convertView;
		image.setLayoutParams(new Gallery.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		Bitmap bmp = data.get(position).getImage(width, height);
		if(bmp != null){
			image.setImageBitmap(bmp);
		}else{
			image.setImageResource(android.R.drawable.ic_delete);
		}
		return convertView;
	}

	/**
	 * É¾³ýÒ»¸öÍ¼Æ¬
	 * @param position
	 * @return
	 */
	public boolean deleteImage(int position){
		if(position < 0 || position > data.size() - 1){
			return false;
		}
		ImageInfo image = data.get(position);
		data.remove(position);
		return Util.deleteFile(image.getPath());
	}
}
