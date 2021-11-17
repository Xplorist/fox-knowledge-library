package com.foxconn.shzbg.epdm.email;

public class MailContent {
	private String to;
	private String from;
	private String subject;
	private String body;
	private String subtype;
	private String fileAttach; //base64string
	private String filename;
	private String userid;
	private String password;
	public MailContent() {}
    
    public MailContent(String to, String from, String subject,String body,String subtype,String filename,String fileAttach) {
        super();
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.body = body;
        this.subtype =subtype;
        this.filename = filename;
        this.fileAttach = fileAttach;
       
    }
	public boolean hasAttachment(){
		if(fileAttach != null && fileAttach.length() > 0)
			return true;
		else
			return false;
	}
	public void setTo(String _to){
		this.to = _to;
	}
	public String getTo(){
		return this.to;
	}
	public void setFrom(String _from){
		this.from = _from;
	}
	public String getFrom(){
		return this.from;
	}
	public void setSubject(String _subject){
		this.subject = _subject;
	}
	public String getSubject(){
		return this.subject;
	}
	public void setBody(String _body){
		this.body = _body;
	}
	public String getBody(){
		return this.body;
	}
	public void setSubtype(String _subtype){
		this.subtype = _subtype;
	}
	public String getSubtype(){
		return this.subtype;
	}
	public void setFileAttach(String _fileAttach){
		this.fileAttach = _fileAttach;
	}
	public String getFileAttach(){
		return this.fileAttach;
	}
	public void setFilename(String _filename){
		filename = _filename;
	}
	public String getFilename(){
		return this.filename;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
