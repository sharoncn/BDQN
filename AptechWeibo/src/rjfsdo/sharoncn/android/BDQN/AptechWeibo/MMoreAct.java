package rjfsdo.sharoncn.android.BDQN.AptechWeibo;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.ClickableOneColumnPanel;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.DefaultHeader;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.MorePanel;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * �������
 * @author sharoncn
 *
 */
public class MMoreAct extends BaseActivity implements OnClickListener {
	private static final String TAG = "MMoreAct";
	private DefaultHeader header;
	private MorePanel panel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_act_more);
		initViews();
	}

	private void initViews() {
		header = (DefaultHeader) findViewById(R.id.more_header);
		header.setHeaderTitle(R.string.more);

		// Panel
		panel = (MorePanel) findViewById(R.id.more_panel);
		panel.setItemOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case ClickableOneColumnPanel.ID_ONE:
			Log.v(TAG, "���ñ����");
			gotoSetup();
			break;
		case ClickableOneColumnPanel.ID_ONE + 1:
			Log.v(TAG, "�˺Ź������");
			// "�˺Ź���"
			break;
		case ClickableOneColumnPanel.ID_ONE + 2:
			Log.v(TAG, "���ģʽ�����");
			// "���ģʽ"
			break;
		case ClickableOneColumnPanel.ID_ONE + 3:
			Log.v(TAG, "�ٷ�΢�������");
			// "�ٷ�΢��"
			break;
		case ClickableOneColumnPanel.ID_ONE + 4:
			Log.v(TAG, "������������");
			// "�������"
			break;
		case ClickableOneColumnPanel.ID_ONE + 5:
			Log.v(TAG, "���ڱ����");
			// "����"
			break;
		}
	}

	private void gotoSetup() {
		Intent intent = new Intent(this, SetupAct.class);
		startActivity(intent);
	}

}
