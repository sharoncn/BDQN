package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Models.AppPackageInfo;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Models.FileInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Util {
	public static final int TEXT = 2001;
	public static final int VEDIO = 2002;
	public static final int AUDIO = 2003;
	public static final int APP = 2004;
	public static final int ZIP = 2005;
	public static final int IMG = 2006;
	public static final int UNKOWN = 2100;

	private static ArrayList<String> text = new ArrayList<String>();
	private static ArrayList<String> vedio = new ArrayList<String>();
	private static ArrayList<String> img = new ArrayList<String>();
	private static ArrayList<String> audio = new ArrayList<String>();
	private static ArrayList<String> app = new ArrayList<String>();
	private static ArrayList<String> zip = new ArrayList<String>();

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final DecimalFormat df = new DecimalFormat("0.00");
	private static final String TAG = "Util";

	static {
		final String[] simg = new String[] { ".png", ".jpg", ".jpeg", ".gif", ".bmp", ".tiff" };
		for (String item : simg) {
			img.add(item);
		}
		final String[] stext = new String[] { ".txt", ".doc", ".java", ".xml" };
		for (String item : stext) {
			text.add(item);
		}
		final String[] svedio = new String[] { ".rmvb", ".rm", ".mp4", ".3gp", ".wmv", ".avi", ".mkv", ".flv", ".f4v",
				".asf" };
		for (String item : svedio) {
			vedio.add(item);
		}
		final String[] saudio = new String[] { ".mp3", ".wma", ".flac", ".aac", ".mmf", ".amr", ".m4a", ".m4r", ".ogg",
				".mp2", ".wma" };
		for (String item : saudio) {
			audio.add(item);
		}
		final String[] sapp = new String[] { ".apk", ".exe", ".class" };
		for (String item : sapp) {
			app.add(item);
		}
		final String[] szip = new String[] { ".zip", ".rar", ".7z", ".cab", ".jar", ".uue", ".z", ".ace", ".lzh",
				".gzip", ".bz2", ".arj" };
		for (String item : szip) {
			zip.add(item);
		}
	}

	/**
	 * �����ļ�������ļ���׺��
	 * 
	 * @param fileName
	 *            �ļ���
	 * @return �ļ���׺��
	 */
	public static String getFileExtName(String fileName) {
		Log.v(TAG, "fileName:" + fileName);
		final int index = fileName.lastIndexOf(".");
		if (index == -1) {
			return "";
		}
		return fileName.substring(index);
	}

	/**
	 * �����ļ�������ļ�����
	 * 
	 * @param fileName
	 *            �ļ���
	 * @return ����
	 */
	public static int getFileType(String fileName) {
		String ext = getFileExtName(fileName);
		Log.v("Util", ext);
		if (text.contains(ext.toLowerCase())) {
			return TEXT;
		}
		if (vedio.contains(ext.toLowerCase())) {
			return VEDIO;
		}
		if (audio.contains(ext.toLowerCase())) {
			return AUDIO;
		}
		if (app.contains(ext.toLowerCase())) {
			return APP;
		}
		if (zip.contains(ext.toLowerCase())) {
			return ZIP;
		}
		if (img.contains(ext.toLowerCase())) {
			return IMG;
		} else {
			return UNKOWN;
		}
	}

	/**
	 * ��ʽ������
	 * 
	 * @param date
	 *            ��Ҫ��ʽ��������
	 * @return ��ʽ������ַ���
	 */
	public static String formatDate(Date date) {
		return sdf.format(date);
	}
	
	/**
	 * �����ڸ�ʽ��Ϊyyyy-MM-dd����ʽ
	 * @param date
	 * @return
	 */
	public static String formatSimpleDate(Date date){
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	private static final int GB = 1024 * 1024 * 1024;
	private static final int MB = 1024 * 1024;
	private static final int KB = 1024;

	/**
	 * ��ʽ���ļ���С�����ظ�ʽ����ɵ��ַ���
	 * 
	 * @param size
	 *            �ļ���С
	 * @return ��ʽ����ɵ��ַ���
	 */
	public static String getFormatedSize(long size) {
		String measurement = "Byte";
		double s = 0D;

		if (size >= GB) {
			measurement = "GB";
			s = (double) size / (GB);
		} else if (size >= MB) {
			measurement = "MB";
			s = (double) size / (MB);
		} else if (size >= KB) {
			measurement = "KB";
			s = (double) size / KB;
		} else {
			return size + measurement;
		}
		final String strFloat = formatDecimal(s);
		return strFloat + measurement;
	}
	
	/**
	 * ��ʽ���ļ���С�����ظ�ʽ����ɵ��ַ���
	 * 
	 * @param size
	 *            �ļ���С
	 * @return ��ʽ����ɵ��ַ���
	 */
	public static String getFormatedSize(double size) {
		String measurement = "Byte";
		double s = 0D;

		if (size >= GB) {
			measurement = "GB";
			s = size / (GB);
		} else if (size >= MB) {
			measurement = "MB";
			s = size / (MB);
		} else if (size >= KB) {
			measurement = "KB";
			s = size / KB;
		} else {
			return size + measurement;
		}
		final String strFloat = formatDecimal(s);
		return strFloat + measurement;
	}

	/**
	 * ��ʽ��С����С��λ��Ϊ2λ
	 * 
	 * @param value
	 * @return
	 */
	public static String formatDecimal(Object value) {
		return df.format(value);
	}

	public static List<PackageInfo> getApkList(Context context) {
		final PackageManager mPackageManager = context.getPackageManager();
		return mPackageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
	}

	/**
	 * ���Ӧ���б�
	 * 
	 * @param context
	 * @return
	 */
	public static List<AppPackageInfo> getAppsList(Context context) {
		final PackageManager mPackageManager = context.getPackageManager();
		final ArrayList<AppPackageInfo> appInfos = new ArrayList<AppPackageInfo>();
		final List<PackageInfo> packages = getApkList(context);
		for (PackageInfo pkg : packages) {
			final ApplicationInfo app = pkg.applicationInfo;
			final AppPackageInfo appInfo = new AppPackageInfo();
			final String appName = mPackageManager.getApplicationLabel(app).toString();
			appInfo.setName(appName);
			appInfo.setAppId(app.uid);
			appInfo.setIcon(context.getPackageManager().getApplicationIcon(app));
			appInfo.setVersion(pkg.versionName);
			appInfo.setVersionCode(pkg.versionCode);
			appInfo.setPackageName(pkg.packageName);
			boolean isSys = false;
			if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
				isSys = true;
			} else if ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				isSys = true;
			} else {
				isSys = false;
			}

			appInfo.setSysFlag(isSys);
			appInfos.add(appInfo);
		}
		return appInfos;
	}

	/**
	 * ɾ��Ӧ��
	 * 
	 * @param context
	 * @param pkgName
	 * @param requestCode
	 */
	public static void uninstallApp(Activity context, String pkgName) {
		final Uri packageURI = Uri.parse("package:" + pkgName);
		final Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
		context.startActivity(intent);
	}

	public static long getInternalAvailSize() {

		return 0;
	}

	/**
	 * �����ļ�
	 * 
	 * @param context
	 * @param fileInfo
	 */
	public static void shareFile(Context context, FileInfo fileInfo) {
		final Intent intent = new Intent(Intent.ACTION_SEND);
		final File file = new File(fileInfo.getPath());
		intent.setDataAndType(Uri.fromFile(file), MIMETypeUtil.getMIMEType(fileInfo.getName()));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * ��ָ�����ļ�
	 * 
	 * @param context
	 * @param fileInfo
	 */
	public static void openFile(Context context, FileInfo fileInfo) {
		final Intent intent = new Intent(Intent.ACTION_VIEW);
		final File file = new File(fileInfo.getPath());
		intent.setDataAndType(Uri.fromFile(file), MIMETypeUtil.getMIMEType(fileInfo.getName()));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * ���SD���ĸ�·��
	 * 
	 * @return
	 */
	public static String getSdCardPath() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File sdCard = Environment.getExternalStorageDirectory();
			return sdCard.getPath();
		}
		return null;
	}

	/**
	 * �ļ������Ի���
	 * 
	 * @param context
	 * @param arrayRes
	 * @param listener
	 * @return
	 */
	public static Dialog showFileOperationDialog(Context context, int arrayRes, DialogInterface.OnClickListener listener) {
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setItems(arrayRes, listener).setCancelable(false)
				.setPositiveButton(R.string.cancel, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		final Dialog dialog = alert.show();
		return dialog;
	}

	/**
	 * �������Ի���
	 * 
	 * @param context
	 * @param fileInfo
	 * @param layoutRes
	 * @param listener
	 * @return
	 */
	public static EditText showRenameToDialog(Context context, FileInfo fileInfo, int layoutRes,
			DialogInterface.OnClickListener listener) {
		final View view = LayoutInflater.from(context).inflate(layoutRes, null);
		final TextView tv = (TextView) view.findViewById(R.id.tv_current_name);
		final TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		final EditText et_rename = (EditText) view.findViewById(R.id.et_rename);

		tv_title.setText(context.getString(R.string.currentname));
		tv.setText(fileInfo.getName());

		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle(R.string.rename).setView(view).setIcon(android.R.drawable.ic_menu_more)
				.setPositiveButton(R.string.ok, listener).setNegativeButton(R.string.cancel, listener).show();
		return et_rename;
	}

	/**
	 * ɾ���Ի���
	 * 
	 * @param context
	 * @param fileInfo
	 * @param listener
	 */
	public static void showDeleteDialog(Context context, FileInfo fileInfo, DialogInterface.OnClickListener listener) {
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle(R.string.rename).setIcon(android.R.drawable.ic_menu_more)
				.setPositiveButton(R.string.ok, listener)
				.setMessage(fileInfo.getName() + " " + context.getString(R.string.delete_sure))
				.setNegativeButton(R.string.cancel, listener).show();
	}

	/**
	 * ��ϸ��Ϣ�Ի���
	 * 
	 * @param context
	 * @param fileInfo
	 */
	public static void showDetailDialog(Context context, FileInfo fileInfo) {
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);

		final View view = LayoutInflater.from(context).inflate(R.layout.dialog_detail, null);
		final TextView tv_name = (TextView) view.findViewById(R.id.detail_filename);
		final TextView tv_path = (TextView) view.findViewById(R.id.detail_filepath);
		final TextView tv_size = (TextView) view.findViewById(R.id.detail_filesize);
		final TextView tv_lastmodity = (TextView) view.findViewById(R.id.detail_lastmodify);

		tv_name.setText(fileInfo.getName());
		tv_path.setText(fileInfo.getPath());
		tv_size.setText(fileInfo.getSize());
		tv_lastmodity.setText(fileInfo.getDate());

		alert.setTitle(R.string.file_detail).setIcon(android.R.drawable.ic_menu_more).setView(view)
				.setPositiveButton(R.string.ok, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
	}

	/**
	 * Toast��ʾ��ʾ��Ϣ
	 * 
	 * @param context
	 * @param resId
	 */
	public static void showMsg(Context context, int resId) {
		showMsg(context, context.getString(resId));
	}

	/**
	 * Toast��ʾ��ʾ��Ϣ
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showMsg(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * �ַ�����Padding����
	 * 
	 * @param value
	 *            ��Ҫ������ַ���
	 * @param len
	 *            ����ĳ���
	 * @param str
	 *            ����������ַ���
	 * @return ���֮����ַ���
	 */
	public static String stringLeftPadding(String value, int len, String padString) {
		final StringBuffer sb = new StringBuffer();
		final int count = value.length();
		for (int i = len; i > count; i--) {
			sb.append(padString);
		}
		sb.append(value);
		return sb.toString();
	}

	/**
	 * ��ʾProgressDialog
	 * 
	 * @param context
	 *            Context
	 * @param msg
	 *            �ı�
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context, String msg) {
		final ProgressDialog pd = new ProgressDialog(context);
		pd.setMessage(msg);
		pd.show();
		return pd;
	}

	/**
	 * ��ʾProgressDialog
	 * 
	 * @param context
	 *            Context
	 * @param resId
	 *            �ı�ID
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context, int resId) {
		return showProgressDialog(context, context.getString(resId));
	}

	/**
	 * �����ļ������Ҫ�������ļ���·�������ڻᴴ����·��
	 * 
	 * @param filePath
	 *            Ҫ�������ļ�·��
	 * @return ����ļ�����ֱ�ӷ���true����������쳣�����ߴ���ʧ�ܣ��򷵻�false
	 */
	public static boolean createFile(String filePath) {
		Log.v(TAG, "arg:" + filePath);
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		}
		File parent = file.getParentFile();
		try {
			if (!parent.exists()) {
				parent.mkdirs();
			}
			return file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * ������
	 * 
	 * @param instance
	 *            Ҫ���Ķ���
	 * @param name
	 *            �����ֶ���(������set����֧��)
	 * @param value
	 *            Ҫ����ֵ
	 */
	public static void constructObject(Object instance, String name, Object value) {
		Method[] methods = instance.getClass().getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			// Log.v(TAG, "Method Name:" + methodName);
			if (methodName.equalsIgnoreCase("set" + name)) {
				// Log.v(TAG, "ƥ�䵽����:" + methodName);
				try {
					method.invoke(instance, new Object[] { value });
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
