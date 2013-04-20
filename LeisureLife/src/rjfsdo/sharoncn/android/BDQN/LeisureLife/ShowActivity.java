package rjfsdo.sharoncn.android.BDQN.LeisureLife;


import rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter.DefaultAdapter;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.Show;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * ����Show��Activity�ĸ���,���ڵ��ô���onCreate����֮ǰ��ʼ��who,cmdValue
 * 
 * @author sharoncn
 * 
 */
public abstract class ShowActivity extends ListBaseActivity implements OnItemClickListener {
	protected int limit = 0;

	/**
	 * ��¼˭��ʹ�ô��࣬ӦΪ��DataManager.FLAG_?֮һ
	 */
	private int who;
	/**
	 * �����ߵ�URL�����ַ���,ӦΪ:URLProtocol.?_CMD_VALUE֮һ
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
	 * ����˭��ʹ�ô���
	 * 
	 * @param who
	 *            ӦΪ��DataManager.FLAG_?֮һ
	 */
	public void setWho(int who) {
		this.who = who;
	}

	public String getCmdValue() {
		return cmdValue;
	}

	/**
	 * ���õ����ߵ�URL�����ַ���
	 * 
	 * @param cmdValue
	 *            ӦΪ:URLProtocol.?_CMD_VALUE֮һ
	 */
	public void setCmdValue(String cmdValue) {
		this.cmdValue = cmdValue;
	}
}
