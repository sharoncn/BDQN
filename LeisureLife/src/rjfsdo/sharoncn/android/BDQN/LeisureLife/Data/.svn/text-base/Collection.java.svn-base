package rjfsdo.sharoncn.android.BDQN.LeisureLife.Data;

/**
 * 收藏实体类
 * @author sharoncn
 *
 */
public class Collection {
	private String type; //类型
	private String content;//内容
	private String comment;//评论
	
	public Collection(String type, String content, String comment) {
		super();
		this.type = type;
		this.content = content;
		this.comment = comment;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getImageId(){
		String[] parts = this.content.split(",");
		final int count = parts.length;
		for(int i = 0;i < count;i ++){
			if(parts[i].startsWith("image:")){
				return parts[i].replace("image:", "");
			}
		}
		return null;
	}
	
	public String getName(){
		String[] parts = this.content.split(",");
		final int count = parts.length;
		for(int i = 0;i < count;i ++){
			if(parts[i].startsWith("name:")){
				return parts[i].replace("name:", "");
			}
		}
		return null;
	}
}
