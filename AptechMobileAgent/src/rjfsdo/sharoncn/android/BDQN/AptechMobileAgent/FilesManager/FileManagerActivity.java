package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.FilesManager;

import java.io.File;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Models.FileInfo;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Util;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;

public class FileManagerActivity extends Activity implements OnItemLongClickListener {
	// private static final String TAG = "FileManagerActivity";
	private FileManager fileMgr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_filemanager);
		initViews();
	}

	private void initViews() {
		fileMgr = (FileManager) findViewById(R.id.file_mgr);
		fileMgr.setFileOnItemLongClickListener(this);
		String sdCardPath = "/";// Util.getSdCardPath();
		if (sdCardPath != null) {
			fileMgr.setRoot(sdCardPath);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adpView, View view, int position, long id) {
		FileInfo file = (FileInfo) adpView.getAdapter().getItem(position);
		if (file.getType().equals(FileManager.TYPE_FILE)) {
			currentFile = file;
			Util.showFileOperationDialog(this, R.array.file_op, listener);
			return true;
		}
		return false;
	}

	private FileInfo currentFile = null;
	private EditText et_rename = null;
	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (currentFile == null) {
				return;
			}

			switch (which) {
			case 0:
				// 查看
				Util.openFile(FileManagerActivity.this, currentFile);
				break;
			case 1:
				// 分享
				Util.shareFile(FileManagerActivity.this, currentFile);
				break;
			case 2:
				// 重命名
				et_rename = Util.showRenameToDialog(FileManagerActivity.this, currentFile, R.layout.dialog_renameto,
						rename);
				break;
			case 3:
				// 删除
				Util.showDeleteDialog(FileManagerActivity.this, currentFile, delete);
				break;
			case 4:
				// 详细
				Util.showDetailDialog(FileManagerActivity.this, currentFile);
				break;
			}
		}
	};

	private OnClickListener delete = new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				File file = new File(currentFile.getPath());
				if (file.delete()){
					Util.showMsg(FileManagerActivity.this, R.string.delete_success);
					fileMgr.refresh();
				}else{
					Util.showMsg(FileManagerActivity.this, R.string.delete_fail);
				}
				dialog.dismiss();
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				dialog.dismiss();
				break;
			}
		}
	};

	private OnClickListener rename = new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				if (et_rename != null) {
					final String newName = et_rename.getText().toString();
					if (newName != null && !newName.equals(currentFile.getName())) {
						if (renameTo(currentFile, newName)){
							Util.showMsg(FileManagerActivity.this, R.string.rename_success);
							fileMgr.refresh();
						}else{
							Util.showMsg(FileManagerActivity.this, R.string.rename_fail);
						}
					}
				}
				dialog.dismiss();
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				dialog.dismiss();
				break;
			}
		}
	};

	private boolean renameTo(FileInfo file, String newName) {
		final String newPath = file.getPath().replace(file.getName(), newName);
		final File oldFile = new File(file.getPath());
		final File newFile = new File(newPath);

		return oldFile.renameTo(newFile);
	}
}
