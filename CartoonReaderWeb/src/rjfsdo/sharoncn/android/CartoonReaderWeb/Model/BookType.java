package rjfsdo.sharoncn.android.CartoonReaderWeb.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String typeId;//id
	private String typeCode;//���ͱ��(���ȵ���3�Ǵ���𣬴���3����С���С�����ǰ3Ϊ�Ǵ����ı��)
	private String typeName;//��������
	private String bookNum;//������������
	private List<BookType> childType = new ArrayList<BookType>();//�����ϼ�
	
	public BookType(){}
	
	public BookType(String typeId, String typeCode, String typeName,
			String bookNum) {
		super();
		this.typeId = typeId;
		this.typeCode = typeCode;
		this.typeName = typeName;
		this.bookNum = bookNum;
	}

	@Override
	public String toString() {
		return "typeId:" + typeId + "/" + "typeCode:" + typeCode + "/" + "typeName:" + typeName + "/" + "bookNum:" + 
	bookNum + "/childType.size()" + childType.size();
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getBookNum() {
		return bookNum;
	}

	public void setBookNum(String bookNum) {
		this.bookNum = bookNum;
	}

	public List<BookType> getChildType() {
		return childType;
	}

	public void setChildType(List<BookType> childType) {
		this.childType = childType;
	}

}
