package rjfsdo.sharoncn.android.BDQN.LeisureLife;


import rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter.DefaultAdapter;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.Show;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 所有Show类Activity的父类,请在调用此类onCreate方法之前初始化who,cmdValue
 * 
 * @author sharoncn
 * 
 */
public abstract class ShowActivity extends ListBaseActivity implements OnItemClickListener {
	protected int limit = 0;

	/**
	 * 记录谁在使用此类，应为：DataManager.FLAG_?之一
	 */
	private int who;
	/**
	 * 调用者的URL命令字符串,应为:URLProtocol.?_CMD_VALUE之一
	 */
	private String cmdValue;

	@Override
	protected void setProjection(DefaultAdapter adapter) {
		adapter.addProjection(DefaultAdapter.VIEW_IMG, "getImage");
		adapter.addProjection(DefaultAdapter.VIEW_TXTONE, "getName");
		adapter.addProjection(DefaultAdapter.VIEW_TXTTWO, "getAddr");
		adapter.addProjection(DefaultAdapter.VIEW_TXTTHREE, "getTime");
	}

	@Override
	protected void addTextViewHeader(DefaultAdapter adapter) {
		adapter.addTextHeader(0, getString(R.string.nameis));
		adapter.addTextHeader(1, getString(R.string.addris));
		adapter.addTextHeader(2, getString(R.string.timeis));
		adapter.addTextHeader(3, "");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_show);

		list = (ListView) findViewById(R.id.show_list);
		list.setOnItemClickListener(this);

		initData();
	}

	protected void initData() {
		super.initData(this, Show.class, cmdValue, who, limit);
	}

	public int getWho() {
		return who;
	}

	/**
	 * 设置谁在使用此类
	 * 
	 * @param who
	 *            应为：DataManager.FLAG_?之一
	 */
	public void setWho(int who) {
		this.who = who;
	}

	public String getCmdValue() {
		return cmdValue;
	}

	/**
	 * 设置调用者的URL命令字符串
	 * 
	 * @param cmdValue
	 *            应为:URLProtocol.?_CMD_VALUE之一
	 */
	public void setCmdValue(String cmdValue) {
		this.cmdValue = cmdValue;
	}
}
