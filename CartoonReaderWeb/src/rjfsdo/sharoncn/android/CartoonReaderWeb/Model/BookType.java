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
	private String typeCode;//类型编号(长度等于3是大类别，大于3的是小类别，小类别编号前3为是大类别的编号)
	private String typeName;//类型名称
	private String bookNum;//包含漫画数量
	private List<BookType> childType = new ArrayList<BookType>();//子类别合集
	
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
