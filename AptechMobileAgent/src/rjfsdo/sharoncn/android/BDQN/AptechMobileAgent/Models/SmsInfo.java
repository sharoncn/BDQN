package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Models;

/**
 * 短信实体类
 * 
 * @author sharoncn
 * 
 */
public class SmsInfo {
	private int _id;// 表ID
	private int thread_id;// 会话ID
	private int association_id;
	private int person;// 联系人ID
	private String address;// 对方号码
	private long date;// 短信日期
	private int protocol;// 协议0：SMS_RPOTO, 1：MMS_PROTO
	private int read;// 是否读取 0：未读， 1：已读
	private int status;// 状态 default-1 -1：接收， 0：complete, 64： pending, 128：
							// failed
	private int type;// 短信类型 2：sent 3：draft56 4：outbox 5：failed 6：queued
	private String body;// 内容
	private String service_center;// 服务中心号码
	private String subject;// 主题
	private int reply_path_present;
	private int locked;
	private int error_code;// 错误码
	private int report_date;
	private int seen;
	private int port;
	private int sync_ver;
	private String uuid;
	private String group_msg_id;
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public int getThread_id() {
		return thread_id;
	}
	public void setThread_id(int thread_id) {
		this.thread_id = thread_id;
	}
	public int getAssociation_id() {
		return association_id;
	}
	public void setAssociation_id(int association_id) {
		this.association_id = association_id;
	}
	public int getPerson() {
		return person;
	}
	public void setPerson(int person) {
		this.person = person;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public int getProtocol() {
		return protocol;
	}
	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getService_center() {
		return service_center;
	}
	public void setService_center(String service_center) {
		this.service_center = service_center;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getReply_path_present() {
		return reply_path_present;
	}
	public void setReply_path_present(int reply_path_present) {
		this.reply_path_present = reply_path_present;
	}
	public int getLocked() {
		return locked;
	}
	public void setLocked(int locked) {
		this.locked = locked;
	}
	public int getError_code() {
		return error_code;
	}
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}
	public int getReport_date() {
		return report_date;
	}
	public void setReport_date(int report_date) {
		this.report_date = report_date;
	}
	public int getSeen() {
		return seen;
	}
	public void setSeen(int seen) {
		this.seen = seen;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getSync_ver() {
		return sync_ver;
	}
	public void setSync_ver(int sync_ver) {
		this.sync_ver = sync_ver;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getGroup_msg_id() {
		return group_msg_id;
	}
	public void setGroup_msg_id(String group_msg_id) {
		this.group_msg_id = group_msg_id;
	}
	
}
