package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.test;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.SpnProvider;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.SpnProvider.SpnInfo;
import android.content.Context;
import android.net.TrafficStats;
import android.telephony.TelephonyManager;
import android.test.AndroidTestCase;
import android.util.Log;

public class Test extends AndroidTestCase {

	private static final String TAG = "Test";

	public void testTrafficStats() throws Exception {
		// static long getMobileRxBytes() //��ȡͨ��Mobile�����յ����ֽ�������������WiFi
		// static long getMobileRxPackets() //��ȡMobile�����յ������ݰ�����
		// static long getMobileTxBytes() //Mobile���͵����ֽ���
		// static long getMobileTxPackets() //Mobile���͵������ݰ���
		// static long getTotalRxBytes() //��ȡ�ܵĽ����ֽ���������Mobile��WiFi��
		// static long getTotalRxPackets() //�ܵĽ������ݰ���������Mobile��WiFi��
		// static long getTotalTxBytes() //�ܵķ����ֽ���������Mobile��WiFi��
		// static long getTotalTxPackets() //���͵������ݰ���������Mobile��WiFi��
		//
		// static long getUidRxBytes(int uid) //��ȡĳ������UID�Ľ����ֽ���
		// static long getUidTxBytes(int uid) //��ȡĳ������UID�ķ����ֽ���
		Method[] methods = TrafficStats.class.getMethods();
		for (Method method : methods) {
			try {
				Log.v(TAG,
						"method name:"
								+ method.getName()
								+ "  "
								+ method.invoke(Class
										.forName("android.net.TrafficStats"),
										new Object[] {}));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// TelephonyManager tm = (TelephonyManager)
	// getSystemService(Context.TELEPHONY_SERVICE);
	// /**
	// * ���ص绰״̬
	// *
	// * CALL_STATE_IDLE ���κ�״̬ʱ
	// * CALL_STATE_OFFHOOK ����绰ʱ
	// * CALL_STATE_RINGING �绰����ʱ
	// */
	// tm.getCallState();
	// //���ص�ǰ�ƶ��ն˵�λ��
	// CellLocation location=tm.getCellLocation();
	// //����λ�ø��£�������½������㲥�����ն���Ϊע��LISTEN_CELL_LOCATION�Ķ�����Ҫ��permission����ΪACCESS_COARSE_LOCATION��
	// location.requestLocationUpdate();
	// /**
	// * ��ȡ���ݻ״̬
	// *
	// * DATA_ACTIVITY_IN ��������״̬��������ڽ�������
	// * DATA_ACTIVITY_OUT ��������״̬��������ڷ�������
	// * DATA_ACTIVITY_INOUT ��������״̬��������ڽ��ܺͷ�������
	// * DATA_ACTIVITY_NONE ��������״̬������������ݷ��ͺͽ���
	// */
	// tm.getDataActivity();
	// /**
	// * ��ȡ��������״̬
	// *
	// * DATA_CONNECTED ��������״̬��������
	// * DATA_CONNECTING ��������״̬����������
	// * DATA_DISCONNECTED ��������״̬���Ͽ�
	// * DATA_SUSPENDED ��������״̬����ͣ
	// */
	// tm.getDataState();
	// /**
	// * ���ص�ǰ�ƶ��ն˵�Ψһ��ʶ
	// *
	// * �����GSM���磬����IMEI�������CDMA���磬����MEID
	// */
	// tm.getDeviceId();
	// //�����ƶ��ն˵�����汾�����磺GSM�ֻ���IMEI/SV�롣
	// tm.getDeviceSoftwareVersion();
	// //�����ֻ����룬����GSM������˵��MSISDN
	// tm.getLine1Number();
	// //���ص�ǰ�ƶ��ն˸����ƶ��ն˵���Ϣ
	// List infos=tm.getNeighboringCellInfo();
	// for(NeighboringCellInfo info:infos){
	// //��ȡ�ھ�С����
	// int cid=info.getCid();
	// //��ȡ�ھ�С��LAC��LAC:
	// λ�������롣Ϊ��ȷ���ƶ�̨��λ�ã�ÿ��GSM/PLMN�ĸ������������ֳ����λ������LAC�����ڱ�ʶ��ͬ��λ������
	// info.getLac();
	// info.getNetworkType();
	// info.getPsc();
	// //��ȡ�ھ�С���ź�ǿ��
	// info.getRssi();
	// }
	// //����ISO��׼�Ĺ����룬�����ʳ�;����
	// tm.getNetworkCountryIso();
	// //����MCC+MNC���� (SIM����Ӫ�̹��Ҵ������Ӫ���������)(IMSI)
	// tm.getNetworkOperator();
	// //�����ƶ�������Ӫ�̵�����(SPN)
	// tm.getNetworkOperatorName();
	// /**
	// * ��ȡ��������
	// *
	// * NETWORK_TYPE_CDMA ��������ΪCDMA
	// * NETWORK_TYPE_EDGE ��������ΪEDGE
	// * NETWORK_TYPE_EVDO_0 ��������ΪEVDO0
	// * NETWORK_TYPE_EVDO_A ��������ΪEVDOA
	// * NETWORK_TYPE_GPRS ��������ΪGPRS
	// * NETWORK_TYPE_HSDPA ��������ΪHSDPA
	// * NETWORK_TYPE_HSPA ��������ΪHSPA
	// * NETWORK_TYPE_HSUPA ��������ΪHSUPA
	// * NETWORK_TYPE_UMTS ��������ΪUMTS
	// *
	// * ���й�����ͨ��3GΪUMTS��HSDPA���ƶ�����ͨ��2GΪGPRS��EGDE�����ŵ�2GΪCDMA�����ŵ�3GΪEVDO
	// */
	// tm.getNetworkType();
	// /**
	// * �����ƶ��ն˵�����
	// *
	// * PHONE_TYPE_CDMA �ֻ���ʽΪCDMA������
	// * PHONE_TYPE_GSM �ֻ���ʽΪGSM���ƶ�����ͨ
	// * PHONE_TYPE_NONE �ֻ���ʽδ֪
	// */
	// tm.getPhoneType();
	// //����SIM���ṩ�̵Ĺ��Ҵ���
	// tm.getSimCountryIso();
	// //����MCC+MNC���� (SIM����Ӫ�̹��Ҵ������Ӫ���������)(IMSI)
	// tm.getSimOperator();
	// tm.getSimOperatorName();
	// //����SIM�������к�(IMEI)
	// tm.getSimSerialNumber();
	// /**
	// * �����ƶ��ն�
	// *
	// * SIM_STATE_ABSENT SIM��δ�ҵ�
	// * SIM_STATE_NETWORK_LOCKED SIM�����类��������ҪNetwork PIN����
	// * SIM_STATE_PIN_REQUIRED SIM��PIN����������ҪUser PIN����
	// * SIM_STATE_PUK_REQUIRED SIM��PUK����������ҪUser PUK����
	// * SIM_STATE_READY SIM������
	// * SIM_STATE_UNKNOWN SIM��δ֪
	// */
	// tm.getSimState();
	// //�����û�Ψһ��ʶ������GSM�����IMSI���
	// tm.getSubscriberId();
	// //��ȡ������������������ĸ��ʶ��
	// tm.getVoiceMailAlphaTag();
	// //���������ʼ�����
	// tm.getVoiceMailNumber();
	// tm.hasIccCard();
	// //�����ֻ��Ƿ�������״̬
	// tm.isNetworkRoaming();
	// // tm.listen(PhoneStateListener listener, int events) ;
	// //���ͣ�
	// //IMSI�ǹ����ƶ��û�ʶ����ļ��(International Mobile Subscriber Identity)
	// //IMSI����15λ����ṹ���£�
	// //MCC+MNC+MIN
	// //MCC��Mobile Country Code���ƶ������룬��3λ���й�Ϊ460;
	// //MNC:Mobile NetworkCode���ƶ������룬��2λ
	// //���й����ƶ��Ĵ���Ϊ��00��02����ͨ�Ĵ���Ϊ01�����ŵĴ���Ϊ03
	// //���������ǣ�Ҳ��Android�ֻ���APN�����ļ��еĴ��룩��
	// //�й��ƶ���46000 46002
	// //�й���ͨ��46001
	// //�й����ţ�46003
	// //������һ�����͵�IMSI����Ϊ460030912121001
	// //IMEI��International Mobile Equipment Identity �������ƶ��豸��ʶ���ļ��
	// //IMEI��15λ������ɵġ����Ӵ��š�������ÿ̨�ֻ�һһ��Ӧ�����Ҹ�����ȫ����Ψһ��
	// //�����Ϊ��
	// //1. ǰ6λ��(TAC)�ǡ��ͺź�׼���롱��һ��������
	// //2. ���ŵ�2λ��(FAC)�ǡ����װ��š���һ��������
	// //3. ֮���6λ��(SNR)�ǡ����š���һ���������˳���
	// //4. ���1λ��(SP)ͨ���ǡ�0�壬Ϊ�����룬Ŀǰ�ݱ���

	public void testTelephonyManager() throws Exception {
		final TelephonyManager tm = (TelephonyManager) getContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		Log.i(TAG, "getSimOperator:" + tm.getSimOperator());// ����MCC+MNC����
															// (SIM����Ӫ�̹��Ҵ������Ӫ���������)getNetworkOperator
		Log.i(TAG, "getNetworkOperator:" + tm.getNetworkOperator());
	}

	public void testSpnProvider() throws Exception {
		final SpnProvider spnProvider = SpnProvider.getInstance(mContext);
		final List<SpnInfo> spns = spnProvider.getAll();
		for (SpnInfo spn : spns) {
			Log.v(TAG,"SpnInfo Start");
			Log.v(TAG,"SpnInfo:" + spn.getCode());
			Log.v(TAG,"SpnInfo:" + spn.getName());
			Log.v(TAG,"SpnInfo:" + spn.getNumber());
			Log.v(TAG,"SpnInfo:" + spn.getText());
			Log.v(TAG,"SpnInfo End");
		}
	}
	
	public void testDate(){
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		System.out.println("DAY_OF_MONTH:" + calendar.get(Calendar.DAY_OF_MONTH));
		System.out.println("HOUR_OF_DAY:" + calendar.get(Calendar.HOUR_OF_DAY));
		System.out.println("MINUTE:" + calendar.get(Calendar.MINUTE));
		System.out.println("SECOND:" + calendar.get(Calendar.SECOND));
	}
}
