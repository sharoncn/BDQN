package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * ��ҳ,����,�Ƽ�,�����activity����Ϣ��ʾ�Ļ���
 * ��LinearLayout��װ��View������Ϊ���ʹ��
 * @author sharoncn
 *
 */
public abstract class BaseCanvas extends LinearLayout {
	private Context context;
	private ImageButton more;
	protected LinearLayout container;
	private TextView title;
	protected ListView list;
	private LinearLayout container_title;
	
	public BaseCanvas(Context context) {
		this(context,null);
	}

	public BaseCanvas(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initComponents();
	}

	private void initComponents(){
		//��ʼ��ͷ��
		initHeader();
		//��ʼ�����ݲ���
		initContent();
	}

	/**
	 * ��ʼ��ͷ��ģ��
	 */
	private void initHeader(){
		//ͷ������
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		container = new LinearLayout(this.context);
		container.setOrientation(LinearLayout.HORIZONTAL);
		container.setLayoutParams(params);
		container.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		container.setBackgroundResource(R.drawable.book_bg);
		
		//��������
		LayoutParams params_Title_Container = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params_Title_Container.weight = 2;
		container_title = new LinearLayout(this.context);
		container_title.setOrientation(LinearLayout.HORIZONTAL);
		container_title.setLayoutParams(params_Title_Container);
		container_title.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		container_title.setBackgroundResource(android.R.color.transparent);
		
		//����
		title = new TextView(context);
		LayoutParams params_title = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params_title.leftMargin = 20;
		title.setLayoutParams(params_title);
		title.setTextColor(Color.WHITE);
		title.setTextSize(15);
		title.setText("ȫ��");
		container_title.addView(title);
		title.setVisibility(GONE);
		
		//���ఴť
		more = new ImageButton(context);
		LayoutParams params_btn = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params_btn.rightMargin = 20;
		more.setLayoutParams(params_btn);
		more.setBackgroundResource(R.drawable.book_bg_more);
		more.setVisibility(VISIBLE);
		
		container.addView(container_title);
		container.addView(more);
		this.addView(container);
		
		//����ListView
		list = new ListView(context);
		LayoutParams params_list = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		params_list.setMargins(5, 5, 5, 5);
		list.setLayoutParams(params_list);
		list.setDivider(getResources().getDrawable(R.drawable.splitline));
		list.setPadding(0, 0, 0, 2);
		
		list.setCacheColorHint(Color.TRANSPARENT);
		this.addView(list);
	}
	
	/**
	 * Ϊ"����"��ť���ü�����
	 * @param l OnClickListener ������
	 */
	public void setHeaderButtonOnClickListener(View.OnClickListener l){
		more.setOnClickListener(l);
	}
	
	/**
	 * ���ø��ఴť�Ŀɼ���,Ĭ�Ͽɼ�
	 * @param visibility One of VISIBLE, INVISIBLE, or GONE. 
	 */
	public void setHeaderButtonVisibility(int visibility){
		more.setVisibility(visibility);
	}
	
	/**
	 * ����HeaderButton�ı���ͼƬ��Ĭ����"����"ͼƬ
	 * @param resid ͼƬID
	 */
	public void setHeaderButtonBackgoundResources(int resid){
		more.setBackgroundResource(resid);
	}
	
	/**
	 * ���ñ���
	 * @param resid int ����ͼƬID
	 */
	public void setBackgroundResource(int resid){
		container.setBackgroundResource(resid);
	}
	
	/**
	 * ���ñ���
	 * @param resId
	 */
	public void setTitleText(int resId){
		setTitleText(context.getString(resId));
	}
	
	/**
	 * ���ñ���
	 * @param text
	 */
	public void setTitleText(String text){
		title.setText(text);
	}
	
	/**
	 * ���ñ���ɼ��ԣ�Ĭ�ϲ��ɼ�
	 * @param visibility One of VISIBLE, INVISIBLE, or GONE. 
	 */
	public void setTitleVisibility(int visibility){
		title.setVisibility(visibility);
	}
	
	/**
	 * ��������Header�Ŀɼ���
	 * @param visibility
	 */
	public void setHeaderVisibility(int visibility){
		container.setVisibility(visibility);
	}
	
	/**
	 * �����������չʾ��ListView����
	 * @return ListView
	 */
	public ListView getContentListView(){
		return list;
	}
	
	public void hideDivider(){
		list.setDivider(context.getResources().getDrawable(android.R.color.transparent));
	}
	
	protected abstract void initContent();
}
