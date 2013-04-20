package rjfsdo.sharoncn.android.BDQN.ImageViewer.Adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import rjfsdo.sharoncn.android.BDQN.ImageViewer.R;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Models.ImageInfo;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Utils.Util;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 20130120每次getView都要从SD卡拿图片，真的很卡，我的M9都是一顿一顿的。我决定用一个List保存所有已经加载过的图片。这样起码
 * 再次getView的时候不卡，如果应用不流畅，根本就没有体验。多占资源就多占资源吧，反正也就是100*100的位图，应该也
 * 占不了多少内存吧。现在内存也比较大。
 * 20130122可我手机中有5700多张图片，程序运行着就垮了。如何是好！
 * @author sharoncn
 *
 */
public class ImageAdapter extends BaseAdapter {
	private List<ImageInfo> data = null;
	private LayoutInflater inflater;
	//private static Map<String,Bitmap> images = new HashMap<String, Bitmap>();
	private Animation anim;
	private List<String> checkedItem = new ArrayList<String>();
	private Context context;
	private Handler handler;
	private Random ran = new Random();
	private boolean isPlayAnimation = false;
	
	public ImageAdapter(Context context){
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		handler = new Handler();
	}
	
	/**
	 * 得到Adapter的所有数据
	 * @return List<ImageInfo> 所有数据
	 */
	public List<ImageInfo> getAllData(){
		return data;
	}
	
	@Override
	public int getCount() {
		if(data != null){
			return data.size();
		}else{
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		if(data != null){
			return data.get(position);
		}else{
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if(convertView == null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.item_gridview, null);
			holder.img = (ImageView) convertView.findViewById(R.id.item_image);
			holder.text = (TextView) convertView.findViewById(R.id.item_name);
			holder.checked = (ImageView) convertView.findViewById(R.id.item_checked);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		//ImageInfo image = data.get(position); 
		Bitmap bmp;
		//if(images.containsKey(image.getPath())){
		//	bmp = images.get(image.getPath());
		//}else{
		bmp = data.get(position).getImage();
		//	images.put(image.getPath(), bmp);
		//}
		if(bmp != null){
			holder.img.setImageBitmap(bmp);
		}else{
			holder.img.setImageResource(android.R.drawable.ic_delete);
		}
		holder.checked.setImageResource(R.raw.btn_check_on);
		holder.text.setText(data.get(position).getName());
		
		//如果选中状态，显示checked这个ImageView
		if(checkedItem.contains("" + position)){
			holder.checked.setVisibility(View.VISIBLE);
		}else{
			holder.checked.setVisibility(View.GONE);
		}
		holder.text.setVisibility(View.GONE);
		
		if(isPlayAnimation){
			anim = AnimationUtils.loadAnimation(context, R.anim.imageanim);
			Animation a = holder.img.getAnimation();
			int time = ran.nextInt(1000);
			if(a != null){
				if(a.hasEnded()){
					handler.postDelayed(new AnimationRunnable(holder.img, anim), time);
				}
			}else{
				handler.postDelayed(new AnimationRunnable(holder.img, anim), time);
			}
		}
		return convertView;
	}

	public class Holder{
		public ImageView img;
		public ImageView checked;
		public TextView text;
	}
	
	/**
	 * 获得所有选中的数据
	 * @return List<String> 一个String列表，列表中存放选中的Item的position
	 */
	public List<String> getAllCheckedItems(){
		return checkedItem;
	}
	
	/**
	 * 设置一个选中标识
	 * @param position 标识位置
	 */
	public void setChecked(int position){
		if(!checkedItem.contains(position)){
			checkedItem.add("" + position);
		}
	}
	
	/**
	 * 删除选中标识
	 * @param position 标识位置
	 */
	public void removeChecked(String position){
		if(checkedItem.contains(position)){
			checkedItem.remove(position);
		}
	}
	
	/**
	 * 删除所有选中标识
	 */
	public void removeAllChecked(){
		checkedItem.clear();
	}
	
	/**
	 * 删除所有已经选中的Item
	 */
	public void removeAllCheckedItem(){
		Iterator<ImageInfo> it = data.iterator();
		List<ImageInfo> iamges = new ArrayList<ImageInfo>();
		for(String position:checkedItem){
			iamges.add(data.get(Integer.parseInt(position)));
		}
		while(it.hasNext()){
			ImageInfo image = it.next();
			for(ImageInfo temp:iamges){
				if(image.equals(temp)){
					Util.deleteFile(image.getPath());
					it.remove();
				}
			}
		}
		checkedItem.clear();
	}
	
	/**
	 * 准备数据，请在new出此Adapter之后调用
	 */
	public void prepareData(){
		data = DataProvider.getAllImages();
	}
	
	/**
	 * 播放动画的Runnable
	 * @author sharoncn
	 *
	 */
	class AnimationRunnable implements Runnable{
		private Animation anim = null;
		private View view;
		public AnimationRunnable(View v,Animation anim){
			this.anim = anim;
			this.view = v;
		}
		
		public void run() {
			int i = ran.nextInt(5);
			anim.setDuration(anim.getDuration() * i);
			view.startAnimation(anim);
		}
	}
	
	/**
	 * 是否播放动画
	 * @return 
	 */
	public boolean isPlayAnimation() {
		return isPlayAnimation;
	}

	/**
	 * 设置是否播放动画
	 * @param isPlayAnimation
	 */
	public void setPlayAnimation(boolean isPlayAnimation) {
		this.isPlayAnimation = isPlayAnimation;
	}
}
