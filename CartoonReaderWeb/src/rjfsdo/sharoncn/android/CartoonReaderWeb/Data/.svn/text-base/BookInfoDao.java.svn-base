package rjfsdo.sharoncn.android.CartoonReaderWeb.Data;

import java.util.List;

import android.database.Cursor;

import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookInfo;

public interface BookInfoDao {
	
	/**
	 * 将传入的漫画列表写入数据库
	 * @param book
	 */
	void saveBookInfo(List<BookInfo> book);
	/**
	 * 将漫画写入数据库
	 * @param book
	 * @return 返回rowId 如果有错误返回-1
	 */
	long saveBookInfo(BookInfo book);
	/**
	 * 查询bookId的漫画是否存在
	 * @param bookId
	 * @return 存在返回true，否则返回false
	 */
	boolean isRecordExists(String bookId);
	/**
	 * 删除bookId字段为bookId的漫画
	 * @param bookId
	 */
	void deleteBookInfo(String bookId);
	/**
	 * 查询匹配bookId的漫画
	 * @param bookId
	 * @return BookInfo
	 */
	BookInfo getBookInfo(String bookId);
	/**
	 * 得到所有漫画
	 * @return List<BookInfo> BookInfo的列表
	 */
	List<BookInfo> getAllBookInfo();
	/**
	 * 获得所有漫画的游标
	 * @return
	 */
	Cursor getAllBookInfoCursor();
	/**
	 * 修改漫画信息
	 * @param book
	 * @return
	 */
	int modifyBookInfo(BookInfo book);
	/**
	 * 得到收藏的漫画
	 * @return List<BookInfo> 收藏的漫画列表
	 */
	List<BookInfo> getCollection();
	/**
	 * 得到阅读过的漫画
	 * @return List<BookInfo> 已读漫画列表
	 */
	List<BookInfo> getReadedBookInfo();
	/**
	 * 设置收藏
	 * @param bookId 漫画id
	 * @param isColleciton 是否收藏，1表示收藏，0表示不收藏
	 * @return boolean 操作结果，true表示操作成功，false表示操作失败
	 */
	boolean setCollection(String bookId,String isColleciton);
	/**
	 * 设置阅读
	 * @param bookId 漫画id
	 * @param isReaded 是否已读，1表示已读，0表示未读
	 * @return boolean 操作结果，true表示操作成功，false表示操作失败
	 */
	boolean setReaded(String bookId,String isReaded);
	/**
	 * 设置阅读位置
	 * @param bookId 漫画id
	 * @param position 阅读位置
	 * @return boolean 操作结果，true表示操作成功，false表示操作失败
	 */
	boolean setLastReadedPosition(String bookId,String position);
	/**
	 * 设置最后阅读时间
	 * @param bookId 漫画id
	 * @param time 最后阅读时间
	 * @return boolean 操作结果，true表示操作成功，false表示操作失败
	 */
	boolean setLastReadTime(String bookId,String time);
	/**
	 * 获得最后阅读时间
	 * @param bookId 漫画id
	 * @return 如果获取成功返回时间字符串，获取失败则返回null
	 */
	String getLastReadTime(String bookId);
	/**
	 * 得到一类漫画
	 * @param typeCode 漫画类型代码
	 * @return List<BookInfo> 漫画列表
	 */
	List<BookInfo> getTypeBookInfo(String typeCode);
	/**
	 * 获得最后阅读的书籍
	 * @return BookInfo 如果未找到则返回null
	 */
	BookInfo getLastReadedBook();
	/**
	 * 将rating数据写入数据库
	 * @param bookId String 标识漫画的bookId
	 * @param value String 要写入的值
	 * @return boolean 操作结果，true表示操作成功，false表示操作失败
	 */
	boolean saveRatingValue(String bookId,String value);
	/**
	 * 关闭数据库
	 */
	void close();
}
