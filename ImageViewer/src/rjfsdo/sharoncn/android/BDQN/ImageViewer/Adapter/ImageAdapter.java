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
 * 20130120ÿ��getView��Ҫ��SD����ͼƬ����ĺܿ����ҵ�M9����һ��һ�ٵġ��Ҿ�����һ��List���������Ѿ����ع���ͼƬ����������
 * �ٴ�getView��ʱ�򲻿������Ӧ�ò�������������û�����顣��ռ��Դ�Ͷ�ռ��Դ�ɣ�����Ҳ����100*100��λͼ��Ӧ��Ҳ
 * ռ���˶����ڴ�ɡ������ڴ�Ҳ�Ƚϴ�
 * 20130122�����ֻ�����5700����ͼƬ�����������žͿ��ˡ�����Ǻã�
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
	 * �õ�Adapter����������
	 * @return List<ImageInfo> ��������
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
		
		//���ѡ��״̬����ʾchecked���ImageView
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
	 * �������ѡ�е�����
	 * @return List<String> һ��String�б��б��д��ѡ�е�Item��position
	 */
	public List<String> getAllCheckedItems(){
		return checkedItem;
	}
	
	/**
	 * ����һ��ѡ�б�ʶ
	 * @param position ��ʶλ��
	 */
	public void setChecked(int position){
		if(!checkedItem.contains(position)){
			checkedItem.add("" + position);
		}
	}
	
	/**
	 * ɾ��ѡ�б�ʶ
	 * @param position ��ʶλ��
	 */
	public void removeChecked(String position){
		if(checkedItem.contains(position)){
			checkedItem.remove(position);
		}
	}
	
	/**
	 * ɾ������ѡ�б�ʶ
	 */
	public void removeAllChecked(){
		checkedItem.clear();
	}
	
	/**
	 * ɾ�������Ѿ�ѡ�е�Item
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
	 * ׼�����ݣ�����new����Adapter֮�����
	 */
	public void prepareData(){
		data = DataProvider.getAllImages();
	}
	
	/**
	 * ���Ŷ�����Runnable
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
	 * �Ƿ񲥷Ŷ���
	 * @return 
	 */
	public boolean isPlayAnimation() {
		return isPlayAnimation;
	}

	/**
	 * �����Ƿ񲥷Ŷ���
	 * @param isPlayAnimation
	 */
	public void setPlayAnimation(boolean isPlayAnimation) {
		this.isPlayAnimation = isPlayAnimation;
	}
}
