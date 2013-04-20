package rjfsdo.sharoncn.android.CartoonReaderWeb.Utils;

import android.os.Environment;

public class Constants {
	public static final String URL = "http://192.168.1.223/cartoonReader";
	public static final String URL_BOOK = URL + "/cartoon/book/book.php";
	public static final String URL_TYPE = URL + "/cartoon/book/type.php";
	public static final String DATABASE_NAME = "CartoonReaderWeb.db";
	public static final String DATATABLE_NAME = "cartoon_book";
	public static final String DATATABLE_READED = "cartoon_readed";
	public static final String DATATABLE_FEEDBACK = "cartoon_feedback";
	public static final String SDCARD = Environment.getExternalStorageDirectory().getPath();
	public static final String XMLTEMP = SDCARD + "/CartoonReaderWeb/tmp";
	public static final String PICTEMP = SDCARD + "/CartoonReaderWeb/pic";
	public static final String ZIPTEMP = SDCARD + "/CartoonReaderWeb/zip";
	public static final String RECOMMEND_FILENAME = "recommend_index.xml";
	public static final String ORDER_FILENAME = "hitnum_index.xml";
	public static final String ALL_FILENAME = "all_index.xml";
	public static final String BOOKTYPE_FILENAME = "booktype_index.xml";
}
