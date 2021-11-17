package io.xicp.xplorist.model;

/**
 * 新模治具圖檔bean
 * @author C3005579
 * @since 2018年12月19日 下午5:28:48
 */
public class NewMouldMainBean {
	private String pkid;//主鍵id
	private String pre_ver_id;//上一版次id
	private String file_draw_id;//新模治具圖圖檔文件id
	private String file_pic_id;//新模治具圖圖片文件id
	private String file_dfm_id;//新模治具圖DFM文件id
	private String project_id;//專案id
	private String project_name;//專案名稱
	private String phase_id;//階段id
	private String phase_name;//階段名稱
	private String part_id;//零件id
	private String part_name;//零件名稱
	private String customer_part_no;//客戶料號
	private String process;//製程
	private String memo;//備註說明
	private String creator;//創建人
	private String create_date;//創建日期
	private String update_date;//修改日期
	private String version;//版本
	private String is_latest;//是否最新版本
	private String is_valid;//是否有效
	private String file_type;// 文件類型
	
	private String pic_file_path;// 圖片文件路徑
	
	public NewMouldMainBean() {
		super();
	}
	public String getPkid() {
		return pkid;
	}
	public void setPkid(String pkid) {
		this.pkid = pkid;
	}
	public String getPre_ver_id() {
		return pre_ver_id;
	}
	public void setPre_ver_id(String pre_ver_id) {
		this.pre_ver_id = pre_ver_id;
	}
	public String getFile_draw_id() {
		return file_draw_id;
	}
	public void setFile_draw_id(String file_draw_id) {
		this.file_draw_id = file_draw_id;
	}
	public String getFile_pic_id() {
		return file_pic_id;
	}
	public void setFile_pic_id(String file_pic_id) {
		this.file_pic_id = file_pic_id;
	}
	public String getFile_dfm_id() {
		return file_dfm_id;
	}
	public void setFile_dfm_id(String file_dfm_id) {
		this.file_dfm_id = file_dfm_id;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getIs_latest() {
		return is_latest;
	}
	public void setIs_latest(String is_latest) {
		this.is_latest = is_latest;
	}
	public String getIs_valid() {
		return is_valid;
	}
	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getPhase_id() {
		return phase_id;
	}
	public void setPhase_id(String phase_id) {
		this.phase_id = phase_id;
	}
	public String getPhase_name() {
		return phase_name;
	}
	public void setPhase_name(String phase_name) {
		this.phase_name = phase_name;
	}
	public String getPart_id() {
		return part_id;
	}
	public void setPart_id(String part_id) {
		this.part_id = part_id;
	}
	public String getPart_name() {
		return part_name;
	}
	public void setPart_name(String part_name) {
		this.part_name = part_name;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	public String getCustomer_part_no() {
		return customer_part_no;
	}
	public void setCustomer_part_no(String customer_part_no) {
		this.customer_part_no = customer_part_no;
	}
	public String getPic_file_path() {
		return pic_file_path;
	}
	public void setPic_file_path(String pic_file_path) {
		this.pic_file_path = pic_file_path;
	}
	
	
}
