package rjfsdo.sharoncn.android.BDQN.LeisureLife;


import rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter.DefaultAdapter;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter.MovieAdapter;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.BaseMovie;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * ��Ӱ�б�ͼ�����ӳ�б�Ļ���
 * @author sharoncn
 *
 */
public abstract class MovieListBaseActivity extends ListBaseActivity implements OnItemClickListener{
	protected int limit;
	/**
	 * ��¼˭��ʹ�ô��࣬ӦΪ��DataManager.FLAG_*֮һ
	 */
	private int who;
	/**
	 * �����ߵ�URL�����ַ���,ӦΪ:URLProtocol.*_CMD_VALUE֮һ
	 */
	private String cmdValue;
	
	@Override
	protected void setProjection(DefaultAdapter adapter){
		adapter.addProjection(DefaultAdapter.VIEW_IMG, "getImage");
		adapter.addProjection(DefaultAdapter.VIEW_TXTONE, "getName");
		adapter.addProjection(DefaultAdapter.VIEW_TXTTWO, "getType");
		adapter.addProjection(DefaultAdapter.VIEW_TXTTHREE, "getPlayer");
		adapter.addProjection(DefaultAdapter.VIEW_TXTFOUR, "getTime");
	}
	
	@Override
	protected void addTextViewHeader(DefaultAdapter adapter){
		adapter.addTextHeader(0,"");
		adapter.addTextHeader(1,getString(R.string.typeis));
		adapter.addTextHeader(2,getString(R.string.playeris));
		adapter.addTextHeader(3,getString(R.string.playtimeis));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_movie);
		
		setAdapterClazz(MovieAdapter.class);
		
		list = (ListView) findViewById(R.id.movie_list);
		list.setOnItemClickListener(this);
		
		initData();
	}

	public int getWho() {
		return who;
	}

	/**
	 * ����˭��ʹ�ô���
	 * @param who ӦΪ��DataManager.FLAG_?֮һ
	 */
	public void setWho(int who) {
		this.who = who;
	}
	
	public String getCmdValue() {
		return cmdValue;
	}

	/**
	 * ���õ����ߵ�URL�����ַ���
	 * @param cmdValue ӦΪ:URLProtocol.?_CMD_VALUE֮һ
	 */
	public void setCmdValue(String cmdValue) {
		this.cmdValue = cmdValue;
	}

	protected void initData() {
		super.initData(this, BaseMovie.class, cmdValue, who, limit);
	}
	
}
