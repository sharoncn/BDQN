package rjfsdo.sharoncn.android.CartoonReaderWeb.Data;

import java.util.List;

import android.database.Cursor;

import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookInfo;

public interface BookInfoDao {
	
	/**
	 * ������������б�д�����ݿ�
	 * @param book
	 */
	void saveBookInfo(List<BookInfo> book);
	/**
	 * ������д�����ݿ�
	 * @param book
	 * @return ����rowId ����д��󷵻�-1
	 */
	long saveBookInfo(BookInfo book);
	/**
	 * ��ѯbookId�������Ƿ����
	 * @param bookId
	 * @return ���ڷ���true�����򷵻�false
	 */
	boolean isRecordExists(String bookId);
	/**
	 * ɾ��bookId�ֶ�ΪbookId������
	 * @param bookId
	 */
	void deleteBookInfo(String bookId);
	/**
	 * ��ѯƥ��bookId������
	 * @param bookId
	 * @return BookInfo
	 */
	BookInfo getBookInfo(String bookId);
	/**
	 * �õ���������
	 * @return List<BookInfo> BookInfo���б�
	 */
	List<BookInfo> getAllBookInfo();
	/**
	 * ��������������α�
	 * @return
	 */
	Cursor getAllBookInfoCursor();
	/**
	 * �޸�������Ϣ
	 * @param book
	 * @return
	 */
	int modifyBookInfo(BookInfo book);
	/**
	 * �õ��ղص�����
	 * @return List<BookInfo> �ղص������б�
	 */
	List<BookInfo> getCollection();
	/**
	 * �õ��Ķ���������
	 * @return List<BookInfo> �Ѷ������б�
	 */
	List<BookInfo> getReadedBookInfo();
	/**
	 * �����ղ�
	 * @param bookId ����id
	 * @param isColleciton �Ƿ��ղأ�1��ʾ�ղأ�0��ʾ���ղ�
	 * @return boolean ���������true��ʾ�����ɹ���false��ʾ����ʧ��
	 */
	boolean setCollection(String bookId,String isColleciton);
	/**
	 * �����Ķ�
	 * @param bookId ����id
	 * @param isReaded �Ƿ��Ѷ���1��ʾ�Ѷ���0��ʾδ��
	 * @return boolean ���������true��ʾ�����ɹ���false��ʾ����ʧ��
	 */
	boolean setReaded(String bookId,String isReaded);
	/**
	 * �����Ķ�λ��
	 * @param bookId ����id
	 * @param position �Ķ�λ��
	 * @return boolean ���������true��ʾ�����ɹ���false��ʾ����ʧ��
	 */
	boolean setLastReadedPosition(String bookId,String position);
	/**
	 * ��������Ķ�ʱ��
	 * @param bookId ����id
	 * @param time ����Ķ�ʱ��
	 * @return boolean ���������true��ʾ�����ɹ���false��ʾ����ʧ��
	 */
	boolean setLastReadTime(String bookId,String time);
	/**
	 * �������Ķ�ʱ��
	 * @param bookId ����id
	 * @return �����ȡ�ɹ�����ʱ���ַ�������ȡʧ���򷵻�null
	 */
	String getLastReadTime(String bookId);
	/**
	 * �õ�һ������
	 * @param typeCode �������ʹ���
	 * @return List<BookInfo> �����б�
	 */
	List<BookInfo> getTypeBookInfo(String typeCode);
	/**
	 * �������Ķ����鼮
	 * @return BookInfo ���δ�ҵ��򷵻�null
	 */
	BookInfo getLastReadedBook();
	/**
	 * ��rating����д�����ݿ�
	 * @param bookId String ��ʶ������bookId
	 * @param value String Ҫд���ֵ
	 * @return boolean ���������true��ʾ�����ɹ���false��ʾ����ʧ��
	 */
	boolean saveRatingValue(String bookId,String value);
	/**
	 * �ر����ݿ�
	 */
	void close();
}
