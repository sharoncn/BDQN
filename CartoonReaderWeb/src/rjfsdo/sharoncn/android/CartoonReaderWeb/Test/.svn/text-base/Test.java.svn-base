package rjfsdo.sharoncn.android.CartoonReaderWeb.Test;

import java.util.ArrayList;
import java.util.List;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import rjfsdo.sharoncn.android.CartoonReaderWeb.Data.BookInfoDao;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Data.BookInfoDaoImpl;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookInfo;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookType;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Net.Client;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Constants;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Utils;
import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

public class Test extends AndroidTestCase{
	private static final String TAG = "Test";
	
	//测试获得所有阅读过的漫画
	public void testGetReadedBook(){
		BookInfoDao dao = BookInfoDaoImpl.getInstance(getContext());
		List<BookInfo> books = dao.getReadedBookInfo();
		for(BookInfo book:books){
			Log.v(TAG, "阅读过的漫画:" + book);
		}
		testGetLastReadedBook();
	}
	
	//测试获得最后阅读过的漫画
	public void testGetLastReadedBook(){
		BookInfoDao dao = BookInfoDaoImpl.getInstance(getContext());
		BookInfo book = dao.getLastReadedBook();
		Log.v(TAG, "最后阅读的漫画:" + book);
	}
	
	//测试写入数据库是否有重复
	public void testUpdate(){
		testGetAllBookInfo();
		BookInfoDao dao = BookInfoDaoImpl.getInstance(getContext());
		assertTrue(!dao.isRecordExists("" + 20));
		dao.setCollection("" + 5, "" + 1);
		dao.setReaded("" + 5, "" + 1);
		testGetAllBookInfo();
	}
	
	//测试从数据库获取所有书籍
	public void testGetAllBookInfo(){
		
		BookInfoDao dao = BookInfoDaoImpl.getInstance(getContext());
		Cursor cursor = dao.getAllBookInfoCursor();
		String[] columns = cursor.getColumnNames();
		int i = 0;
		while(cursor.moveToNext()){
			i += 1;
			Log.w(TAG, "第" + i + "条数据开始");
			for(String column:columns){
				Log.i(TAG, column + ":" + cursor.getString(cursor.getColumnIndex(column)));
			}
			Log.w(TAG, "第" + i + "条数据结束");
		}
	}
	
	//测试从服务器获取所有书籍
	public void testGetAllBookFromNet(){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "book"));
		params.add(new BasicNameValuePair("type", "all"));
		List<BookInfo> all_data = prepareRecommendData(Constants.URL_BOOK,params);
		for(BookInfo book:all_data){
			Log.i(TAG,book.toString());
		}
	}
	
	//测试从服务器获取所有类型
	public void testGetAllBookTypeFromNet(){
		List<BookType> all_data = new ArrayList<BookType>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "type"));
		try {
			String result_all = Client.requestStringContent(Constants.URL_TYPE, params);
			Log.w(TAG, result_all);
			if(result_all != null && result_all != ""){
				all_data = Utils.formatBookType(result_all);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(BookType book:all_data){
			Log.i(TAG,book.toString());
			for(BookType type:book.getChildType()){
				Log.i(TAG,type.toString());
			}
		}
	}
	
	private List<BookInfo> prepareRecommendData(String url,List<NameValuePair> params) {
		List<BookInfo> all_data = null;
		try {
			String result_all = Client.requestStringContent(url, params);
			Log.w(TAG, result_all);
			if(result_all != null && result_all != ""){
				all_data = Utils.formatBookInfo(result_all);
				return all_data;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//网络包中的Client类，获取type列表
	public void testGetType(){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "type"));
		try {
			String result = Client.requestStringContent(Constants.URL_TYPE, params);
			Log.w(TAG, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//网络包中的Client类，获取book列表
	public void testGetBook(){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "book"));
		params.add(new BasicNameValuePair("type", "index"));
		try {
			String result = Client.requestStringContent(Constants.URL_BOOK, params);
			Log.w(TAG, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//测试文件下载
	public void testDownLoadFile(){
		
	}
	
	//测试Utils.formatBookInfo()方法
	public void testBookInfo(){
		String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><books><book><bookId>9</bookId><bookName>女儿国传奇</bookName>" +
				"<bookContent>女儿国传奇</bookContent><writer>沙沙</writer><countryName>日本</countryName><coverPath>/Upfiles/images/201" +
				"10621105656v3yln.png</coverPath><zipPath>/Upfiles/files/20110621105700w58uo.zip</zipPath><className>动作格斗" +
				"</className><classId>001006</classId><createDate>2011/07/26</createDate><hitnum>0</hitnum><recommend>1" +
				"</recommend><grade>5</grade><page>1</page><pageTotal>20</pageTotal><gradeNums>5</gradeNums></book><book>" +
				"<bookId>8</bookId><bookName>斗罗大陆</bookName><bookContent>斗罗大陆</bookContent><writer>劳劳</writer>" +
				"<countryName>日本</countryName><coverPath>/Upfiles/images/20110623002916gepgg.png</coverPath><zipPath>/Upfiles/files" +
				"/20110621105528c3sue.zip</zipPath><className>动作格斗</className><classId>001006</classId><createDate>2011/07/26<" +
				"/createDate><hitnum>4000</hitnum><recommend>1</recommend><grade>3</grade><page>1</page><pageTotal>20</pageTotal>" +
				"<gradeNums>3</gradeNums></book><book><bookId>1</bookId><bookName>火影忍者</bookName><bookContent>火影忍者</bookContent>" +
				"<writer>james</writer><countryName>日本</countryName><coverPath>/Upfiles/images/20110621095141rt4t3.png</coverPath><" +
				"zipPath>/Upfiles/files/20110621095151tpot4.zip</zipPath><className>原画美图</className><classId>001003</classId><cr" +
				"eateDate>2011/06/22</createDate><hitnum>0</hitnum><recommend>1</recommend><grade>7</grade><page>1</page><pageTotal>20" +
				"</pageTotal><gradeNums>7</gradeNums></book><book><bookId>5</bookId><bookName>鬼吹灯</bookName><bookContent>鬼吹灯" +
				"</bookContent><writer>常范</writer><countryName>日本</countryName><coverPath>/Upfiles/images/20110621102225m86da.png" +
				"</coverPath><zipPath>/Upfiles/files/2011062110223295yfe.zip</zipPath><className></className><classId>001002</classId>" +
				"<createDate>2011/06/22</createDate><hitnum>8000</hitnum><recommend>1</recommend><grade>6</grade><page>1</page><pageTotal>20" +
				"</pageTotal><gradeNums>6</gradeNums></book><book><bookId>4</bookId><bookName>诛仙</bookName><bookContent>诛仙</bookContent><writer>囷囷" +
				"</writer><countryName>日本</countryName><coverPath>/Upfiles/images/20110621102132cpju3.png</coverPath><zipPath>/Upfiles/files/20110621102138g39pb.zip" +
				"</zipPath><className>纯情恋爱</className><classId>001004</classId><createDate>2011/06/22</createDate><hitnum>9999</hitnum><recommend>1</recommend>" +
				"<grade>5</grade><page>1</page><pageTotal>20</pageTotal><gradeNums>5</gradeNums></book><book><bookId>3</bookId><bookName>鲁滨逊漂流记" +
				"</bookName><bookContent>鲁滨逊漂流记</bookContent><writer>鸿飞</writer><countryName>日本</countryName><coverPath>/Upfiles/images/201106211013573pdq5.png" +
				"</coverPath><zipPath>/Upfiles/files/20110621101404csnzg.zip</zipPath><className>游戏同人</className><classId>001005</classId><createDate>2011/06/22" +
				"</createDate><hitnum>11250</hitnum><recommend>1</recommend><grade>4</grade><page>1</page><pageTotal>20</pageTotal><gradeNums>4</gradeNums></book>" +
				"<book><bookId>2</bookId><bookName>古今大战秦俑情</bookName><bookContent>古今大战秦俑情</bookContent><writer>中坜</writer><countryName>日本" +
				"</countryName><coverPath>/Upfiles/images/20110621101044p3xjh.png</coverPath><zipPath>/Upfiles/files/20110621101055jv5tz.zip</zipPath>" +
				"<className></className><classId>001002</classId><createDate>2011/06/22</createDate><hitnum>0</hitnum><recommend>1</recommend><grade>1" +
				"</grade><page>1</page><pageTotal>20</pageTotal><gradeNums>1</gradeNums></book></books>";
	
		try {
			List<BookInfo> books = Utils.formatBookInfo(content);
			Log.w(TAG, "Book size is:" + books.size());
			for(BookInfo book:books){
				Log.i(TAG, book.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//测试Utils.formatBookType()方法
	public void testBookType(){
		String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><books><type><typeId>22</typeId><typeCode>001016</typeCode><typeName>奇幻冒险</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>23</typeId><typeCode>001017</typeCode><typeName>其他类别</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>26</typeId><typeCode>001003</typeCode><typeName>原画美图</typeName><" +
				"bookNum>1</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>27</typeId><typeCode>001004</typeCode><typeName>纯情恋爱</typeName>" +
				"<bookNum>1</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>28</typeId><typeCode>001005</typeCode><typeName>游戏同人</typeName>" +
				"<bookNum>1</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>29</typeId><typeCode>001006</typeCode><typeName>动作格斗</typeName>" +
				"<bookNum>7</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>30</typeId><typeCode>001007</typeCode><typeName>轻松搞笑</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>31</typeId><typeCode>001008</typeCode><typeName>恐怖惊悚</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>32</typeId><typeCode>001009</typeCode><typeName>武侠历史</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>33</typeId><typeCode>001010</typeCode><typeName>耽美人生</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>34</typeId><typeCode>001011</typeCode><typeName>体育竞技</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>35</typeId><typeCode>001012</typeCode><typeName>科幻未来</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>36</typeId><typeCode>001013</typeCode><typeName>推理悬念</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>37</typeId><typeCode>001014</typeCode><typeName>励志激励</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>38</typeId><typeCode>001015</typeCode><typeName>青春校园</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>39</typeId><typeCode>003001</typeCode><typeName>单行本</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>40</typeId><typeCode>003002</typeCode><typeName>连载中</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>41</typeId><typeCode>003003</typeCode><typeName>完结篇</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>42</typeId><typeCode>004001</typeCode><typeName>男生喜欢</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>43</typeId><typeCode>004002</typeCode><typeName>女生喜欢</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>45</typeId><typeCode>001</typeCode><typeName>按题材分类</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type><type><typeId>46</typeId><typeCode>002</typeCode><typeName>按地区分类</typeName>" +
				"<bookNum>0</bookNum><page>1</page><pageTotal>29</pageTotal></type></books>";
	
		try {
			List<BookType> books = Utils.formatBookType(content);
			Log.w(TAG, "Book size is:" + books.size());
			for(BookType book:books){
				Log.i(TAG, book.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
