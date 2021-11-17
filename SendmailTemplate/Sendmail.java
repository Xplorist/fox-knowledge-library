package com.foxconn.shzbg.epdm.email;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import org.mozilla.universalchardet.UniversalDetector;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jboss.util.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxconn.shzbg.epdm.file.method.fileCustomMethod;
import com.foxconn.shzbg.epdm.file.model.customModel;
import com.foxconn.shzbg.epdm.file.model.file_manage_totals.FileDTO;
import com.foxconn.shzbg.epdm.flowchart.method.PrcsOrderMethod;
import com.foxconn.shzbg.epdm.flowchart.model.MailReceiverModel;
import com.foxconn.shzbg.epdm.flowchart.model.ManutechModel;
import com.foxconn.shzbg.epdm.flowchart.model.PartModel;
import com.foxconn.shzbg.epdm.packspe.model.PackageSpeBasic;
import com.opensymphony.xwork2.ActionSupport;

public class Sendmail extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private InputStream inputStream;
	private File file;
	private String fileFileName;
	private String to;
	private String subject;
	private String mailcontent;

	public InputStream getInputStream() {
		return this.inputStream;
	}

	public String sendJsonWithApache(String json) {
		String result = "";
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			// String url = "http://localhost:8080/SmtpSend1/sendmail";
			String url = "http://10.244.231.65:8080/Smtp/sendmail";
			HttpPost postRequest = new HttpPost(url);
			String encodeCharset = "utf-8";
			StringEntity input = new StringEntity(json, encodeCharset);
			// System.out.println(json);
			input.setContentType("application/json;charset:" + encodeCharset);
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent(), encodeCharset));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				result += output;
				System.out.println(output);
			}

			httpClient.close();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return result;
	}

	public String sendJson(String json) throws IOException {
		// URL url = new URL("http://10.248.48.146:8080/SmtpSend1/sendmail");
		URL url = new URL("http://10.244.231.65:8080/Smtp/sendmail");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);

		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json; charset:UTF-8"); // ;charset:big5

		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());// "UTF-8"
		out.write(json);
		out.flush();
		out.close();

		int res = connection.getResponseCode();

		System.out.println(res);

		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		String returnLine = null;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
			returnLine += line;
		}
		connection.disconnect();
		return returnLine;
	}

	public String execute() throws Exception {
		MailContent mc = new MailContent();
		String contentt = "<div style='font-size:14px'>TO：各製程窗口<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;請更新：<font style='color:blue'>test</font> Flowchart資料<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;發行原因：<font style='color:blue'>test</font>"
				+ "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;請進行審核。(注：目前沒有實現郵件直接鏈接審核，故敬請按如下步驟進行。後續會改進爲直接鏈接審核)"
				+ "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作步驟：複製系統地址(http://10.244.168.66:8080/smartFactory )到Google濟覽器->登陸--->複製http://10.244.168.152/iFlowchart/Model/Index?sysmno=sys012M02010000到Google瀏覽器；"
				+ "<br/>"
				+ "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;溫馨提示：因Flowchart作業頁面複雜，我們採用了Google濟覽器作爲用戶接口，而supernote隻能默認打開IE瀏覽器，故請先打開Google濟覽器再複製地址到地址欄。"
				+ "<br/>如沒有Google瀏覽器，可按如下步驟安裝綠色免安裝版Google瀏覽器："
				+ "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、下載Google瀏覽器，Google下載地址：10.244.168.152/file/Chrome.zip；"
				+ "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、解壓縮到一任意的位置即可；"
				+ "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、雙擊GoogleChromePortable.exe即可Surf  Internet；"
				+ "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、爲方便後續使用，可設置GoogleChromePortable.exe快捷方式到桌面。</div>";
		setTo("hzcd-mis-sys1@mail.foxconn.com");
		setSubject("測試郵件");
		mc.setTo(this.to);
		mc.setSubject(this.subject);
		mc.setSubtype("text/html");
		mc.setBody(contentt);// this.mailcontent
		mc.setFilename(this.fileFileName);
		mc.setUserid("T0D507");
		mc.setPassword("TYYKjtXMnZyn");
		// System.out.println(this.getMailcontent());
		if (this.file != null) {
			FileInputStream fileInputStreamReader = new FileInputStream(this.file);
			// 將文件轉化爲base64string
			byte[] bytes = new byte[(int) this.file.length()];
			fileInputStreamReader.read(bytes);
			fileInputStreamReader.close();
			mc.setFileAttach(Base64.encodeBytes(bytes));// .encodeBase64String(bytes)
		}
		// 將對象轉化爲json
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(mc);
		// 發送json到郵件api
		// String sj = sendJson(jsonInString);
		//String sj = sendJsonWithApache(jsonInString);

		//this.inputStream = new ByteArrayInputStream(sj.getBytes());
		return "success";
	}

	// 審核通過郵件發送
	public String passmail(String adress) throws Exception {
		MailContent mc = new MailContent();
		String content = "";
		if (adress.equals("")) {// 審核完成，發送給提交人
			content = "";
			setSubject("【工管系統-FlowChart】您提交的flowchart已經審核完成,請登錄系統查看。（系統自動發送,請勿回復！）");
		} else {
			content = "【工管系統-FlowChart】您有一筆單需要審核，請登錄系統進行審核作業。（系統自動發送,請勿回復！）";
			content += "<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
			setSubject("【工管系統-FlowChart】您有一筆單需要審核，請登錄系統進行審核作業。（系統自動發送,請勿回復！）");
		}
		if (adress==null||adress.equals("")) {
			adress = "hzcd-mis-sys1@mail.foxconn.com";
			content+="<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<<未查到收件人郵箱,轉發到本郵箱>>";
		}
		setTo(adress);//"hzcd-mis-sys1@mail.foxconn.com"
		mc.setTo(this.to);
		mc.setSubject(this.subject);
		mc.setSubtype("text/html");
		mc.setBody(content);// this.mailcontent
		mc.setFilename(this.fileFileName);
		mc.setUserid("T0D507");
		mc.setPassword("TYYKjtXMnZyn");
		// System.out.println(this.getMailcontent());
		// 將對象轉化爲json
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(mc);
		// 發送json到郵件api
		// String sj = sendJson(jsonInString);
		String sj = sendJsonWithApache(jsonInString);

		this.inputStream = new ByteArrayInputStream(sj.getBytes());
		System.out.println("郵件已經發送成功\n主題:"+mc.getSubject());
		return "0";
	}
	
	// 上一級審核通過給下一級發送郵件
	public String passmailNext(String address,String projectName,String phaseName,String partName,String auditType) throws Exception {
		MailContent mc = new MailContent();
		String content = "";
		if (address.equals("")) {// 審核完成，發送給提交人
			content = "";
			setSubject("工管系統Flowchart查找下一級審核人失敗");
		} else {
			content ="【工管系統-FlowChart】專案:<b style='color:red;'>"+projectName+"</b>階段:<b style='color:red;'>"
					+phaseName+"</b>零件:<b style='color:red;'>"+partName+"</b>的[<b style='color:red;'>"+auditType
					+"</b>]需要您審核，請登錄系統進行審核作業。（系統自動發送,請勿回復！）";
			content += "<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
			setSubject("【工管系統-FlowChart】專案:"+projectName+"階段:"+phaseName+"零件:"+partName+"的["+auditType+"]需要您審核，請登錄系統進行審核作業。（系統自動發送,請勿回復！）");
		}
		if (address==null||address.equals("")) {
			address = "hzcd-mis-sys1@mail.foxconn.com";
			content+="<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<<未查到收件人郵箱,轉發到本郵箱>>";
		}
		setTo(address);//"hzcd-mis-sys1@mail.foxconn.com"
		mc.setTo(this.to);
		mc.setSubject(this.subject);
		mc.setSubtype("text/html");
		mc.setBody(content);// this.mailcontent
		mc.setFilename(this.fileFileName);
		mc.setUserid("T0D507");
		mc.setPassword("TYYKjtXMnZyn");
		// System.out.println(this.getMailcontent());
		// 將對象轉化爲json
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(mc);
		// 發送json到郵件api
		// String sj = sendJson(jsonInString);
		String sj = sendJsonWithApache(jsonInString);
		
		this.inputStream = new ByteArrayInputStream(sj.getBytes());
		System.out.println("郵件已經發送成功\n主題:"+mc.getSubject());
		return "0";
	}
	
	// 全製程順位提交后，發送郵件給審核人，提交人為主製程提交人(參數：提交人，審核人)
	public String sendMainAfterTotalProcessSubmit(MailReceiverModel auditManModel,ManutechModel submitMan) throws Exception {
		MailContent mc = new MailContent();
		String address=auditManModel.getMailAddress();
		String content = "";
		if (address.equals("")) {// 審核完成，發送給提交人
			content = "";
			setSubject("【工管系統-FlowChart】您提交的flowchart已經審核完成,請登錄系統查看。（系統自動發送,請勿回復！）");
		} else {
			content += auditManModel.getName()+"("+auditManModel.getAccount()+"),你好！<br>";
			content += "【工管系統-FlowChart】<b style='color:red;'>"+submitMan.getManutechName()+"</b>(<b style='color:red;'>"+submitMan.getManutechId()+"</b>)提交的[專案:<b style='color:red;'>"+submitMan.getProjectName()
					+"</b>階段:<b style='color:red;'>"+submitMan.getPhaseName()+"</b>零件:<b style='color:red;'>"
					+submitMan.getPartName()+"</b>]的[<b style='color:red;'>全製程審核</b>]需要審核，請登錄系統進行該審核。（系統自動發送,請勿回復！）";
			content += "<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
			setSubject("【工管系統-FlowChart】"+submitMan.getManutechName()+"("+submitMan.getManutechId()+")提交的[專案:"+submitMan.getProjectName()+"階段:"+submitMan.getPhaseName()+"零件:"+submitMan.getPartName()+"]的[全製程審核]需要審核，請登錄系統進行審核。（系統自動發送,請勿回復！）");
		}
		if (address==null||address.equals("")) {
			address = "hzcd-mis-sys1@mail.foxconn.com";
			content+="<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<<未查到收件人郵箱,轉發到本郵箱>>";
		}
		setTo(address);//"hzcd-mis-sys1@mail.foxconn.com"
		mc.setTo(this.to);
		mc.setSubject(this.subject);
		mc.setSubtype("text/html");
		mc.setBody(content);// this.mailcontent
		mc.setFilename(this.fileFileName);
		mc.setUserid("T0D507");
		mc.setPassword("TYYKjtXMnZyn");
		// System.out.println(this.getMailcontent());
		// 將對象轉化爲json
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(mc);
		// 發送json到郵件api
		// String sj = sendJson(jsonInString);
		String sj = sendJsonWithApache(jsonInString);

		this.inputStream = new ByteArrayInputStream(sj.getBytes());
		System.out.println("郵件已經發送成功\n主題:"+mc.getSubject());
		return "0";
	}
	
	// 零件下所有主製程的細製程都審核完成，發送郵件給主製程提交人
	public String sendMainAfterAllThinProcessAudit(ManutechModel auditManModel) throws Exception {
		MailContent mc = new MailContent();
		String address=auditManModel.getManutechEmail();
		String content = "";
		if (address.equals("")) {// 審核完成，發送給提交人
			content = "";
			setSubject("【工管系統-FlowChart】您提交的flowchart已經審核完成,請登錄系統查看。（系統自動發送,請勿回復！）");
		} else {
			content += auditManModel.getManutechName()+"("+auditManModel.getManutechId()+"),你好！<br>";
			content += "【工管系統-FlowChart】[專案:<b style='color:red;'>"+auditManModel.getProjectName()
					+"</b>階段:<b style='color:red;'>"+auditManModel.getPhaseName()+"</b>零件:<b style='color:red;'>"
					+auditManModel.getPartName()+"</b>]的[<b style='color:red;'>細製程審核</b>]已經完成，請登錄系統進行該零件Flowchart的全製程順位作業。（系統自動發送,請勿回復！）";
			content += "<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
			setSubject("【工管系統-FlowChart】[專案:"+auditManModel.getProjectName()+"階段:"+auditManModel.getPhaseName()+"零件:"+auditManModel.getPartName()+"]的[細製程審核]已經完成，請登錄系統進行該零件Flowchart的全製程順位作業。（系統自動發送,請勿回復！）");
		}
		if (address==null||address.equals("")) {
			address = "hzcd-mis-sys1@mail.foxconn.com";
			content+="<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<<未查到收件人郵箱,轉發到本郵箱>>";
		}
		setTo(address);//"hzcd-mis-sys1@mail.foxconn.com"
		mc.setTo(this.to);
		mc.setSubject(this.subject);
		mc.setSubtype("text/html");
		mc.setBody(content);// this.mailcontent
		mc.setFilename(this.fileFileName);
		mc.setUserid("T0D507");
		mc.setPassword("TYYKjtXMnZyn");
		// System.out.println(this.getMailcontent());
		// 將對象轉化爲json
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(mc);
		// 發送json到郵件api
		// String sj = sendJson(jsonInString);
		String sj = sendJsonWithApache(jsonInString);

		this.inputStream = new ByteArrayInputStream(sj.getBytes());
		System.out.println("郵件已經發送成功\n主題:"+mc.getSubject());
		return "0";
	}
	
	// 零件下所有主製程的細製程都審核完成，發送郵件給主製程提交人
	public String sendMainAfterTotalprocessAudit(ManutechModel auditManModel) throws Exception {
		MailContent mc = new MailContent();
		String address=auditManModel.getManutechEmail();
		String content = "";
		if (address.equals("")) {// 審核完成，發送給提交人
			content = "";
			setSubject("【工管系統-FlowChart】您提交的flowchart已經審核完成,請登錄系統查看。（系統自動發送,請勿回復！）");
		} else {
			content += auditManModel.getManutechName()+"("+auditManModel.getManutechId()+"),你好！<br>";
			content += "【工管系統-FlowChart】[專案:<b style='color:red;'>"+auditManModel.getProjectName()
					+"</b>階段:<b style='color:red;'>"+auditManModel.getPhaseName()+"</b>零件:<b style='color:red;'>"
					+auditManModel.getPartName()+"</b>]的[<b style='color:red;'>全製程審核</b>]已經完成，請登錄系統進行該零件Flowchart的全製程發行。（系統自動發送,請勿回復！）";
			content += "<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
			setSubject("【工管系統-FlowChart】[專案:"+auditManModel.getProjectName()+"階段:"+auditManModel.getPhaseName()+"零件:"+auditManModel.getPartName()+"]的[全製程審核]已經完成，請登錄系統進行該零件Flowchart的全製程發行。（系統自動發送,請勿回復！）");
		}
		if (address==null||address.equals("")) {
			address = "hzcd-mis-sys1@mail.foxconn.com";
			content+="<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<<未查到收件人郵箱,轉發到本郵箱>>";
		}
		setTo(address);//"hzcd-mis-sys1@mail.foxconn.com"
		mc.setTo(this.to);
		mc.setSubject(this.subject);
		mc.setSubtype("text/html");
		mc.setBody(content);// this.mailcontent
		mc.setFilename(this.fileFileName);
		mc.setUserid("T0D507");
		mc.setPassword("TYYKjtXMnZyn");
		// System.out.println(this.getMailcontent());
		// 將對象轉化爲json
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(mc);
		// 發送json到郵件api
		// String sj = sendJson(jsonInString);
		String sj = sendJsonWithApache(jsonInString);

		this.inputStream = new ByteArrayInputStream(sj.getBytes());
		System.out.println("郵件已經發送成功\n主題:"+mc.getSubject());
		return "0";
	}
	
	// 細製程提交后通知細製程審核（參數：提交人姓名工號，審核人相關信息）
	public String sendMailAfterThinProcessSubmit(String submitMan,ManutechModel auditManModel) throws Exception {
		MailContent mc = new MailContent();
		String address=auditManModel.getManutechEmail();
		String content = "";
		if (address.equals("")) {// 審核完成，發送給提交人
			content = "";
			setSubject("【工管系統-FlowChart】您提交的flowchart已經審核完成,請登錄系統查看。（系統自動發送,請勿回復！）");
		} else {
			content += auditManModel.getManutechName()+"("+auditManModel.getManutechId()+"),你好！<br>";
			content += "【工管系統-FlowChart】<b style='color:red;'>"+submitMan+"</b>提交了[專案:<b style='color:red;'>"+auditManModel.getProjectName()
			+"</b>階段:<b style='color:red;'>"+auditManModel.getPhaseName()+"</b>零件:<b style='color:red;'>"+auditManModel.getPartName()+"</b>主製程:<b style='color:red;'>"+auditManModel.getLordprcsName()
			+"</b>]的[<b style='color:red;'>細製程審核</b>]需要審核，請登錄系統進行審核作業。（系統自動發送,請勿回復！）";
			content += "<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
			
			setSubject("【工管系統-FlowChart】"+submitMan+"提交了[專案:"+auditManModel.getProjectName()
			+"階段:"+auditManModel.getPhaseName()+"零件:"+auditManModel.getPartName()+"主製程:"+auditManModel.getLordprcsName()
			+"]的[細製程審核]需要審核，請登錄系統進行審核作業。（系統自動發送,請勿回復！）");
		}
		if (address==null||address.equals("")) {
			address = "hzcd-mis-sys1@mail.foxconn.com";
			content+="<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<<未查到收件人郵箱,轉發到本郵箱>>";
		}
		setTo(address);//"hzcd-mis-sys1@mail.foxconn.com"
		mc.setTo(this.to);
		mc.setSubject(this.subject);
		mc.setSubtype("text/html");
		mc.setBody(content);// this.mailcontent
		mc.setFilename(this.fileFileName);
		mc.setUserid("T0D507");
		mc.setPassword("TYYKjtXMnZyn");
		// System.out.println(this.getMailcontent());
		// 將對象轉化爲json
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(mc);
		// 發送json到郵件api
		// String sj = sendJson(jsonInString);
		String sj = sendJsonWithApache(jsonInString);

		this.inputStream = new ByteArrayInputStream(sj.getBytes());
		System.out.println("郵件已經發送成功\n主題:"+mc.getSubject());
		return "0";
	}
	
	//主製程設定提交完成后發送郵件通知主製程審核
	public String sendMailAfterMainProcessSubmitEnd(MailReceiverModel receiver,String submitManId,String submitManName,PartModel partModel) throws Exception {
		MailContent mc = new MailContent();
		String content = "";
		String address=receiver.getMailAddress();
		if (address.equals("")) {
			content = "";
			setSubject("【工管系統-FlowChart】您提交的flowchart已經審核完成,請登錄系統查看。（系統自動發送,請勿回復！）");
		} else {
			content += receiver.getName()+"，你好!<br>";
			content += "【工管系統-FlowChart】<b style='color:red;'>"+submitManName+"</b>"
					+"提交的[專案:<b style='color:red;'>"+partModel.getProjectName()+"</b> 階段:<b style='color:red;'>"+partModel.getPhaseName()+"</b>"
					+ " 零件:<b style='color:red;'>"+partModel.getPart_name()+"</b>]的[<b style='color:red;'>主製程審核</b>]需要審核，請登錄系統進行審核作業。（系統自動發送,請勿回復！）";
			content += "<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
			setSubject("【工管系統-FlowChart】"+submitManName+"提交的[專案:"+partModel.getProjectName()+" 階段:"+partModel.getPhaseName()+" 零件:"+partModel.getPart_name()+"]的[主製程審核]需要審核，請登錄系統進行審核作業。（系統自動發送,請勿回復！）");
		}
		if (address==null||address.equals("")) {
			address = "hzcd-mis-sys1@mail.foxconn.com";
			content+="<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<<未查到收件人["+receiver.getName()+"("+receiver.getAccount()+")]郵箱,轉發到本郵箱>>";
		}
		setTo(address);//"hzcd-mis-sys1@mail.foxconn.com"
		mc.setTo(this.to);
		mc.setSubject(this.subject);
		mc.setSubtype("text/html");
		mc.setBody(content);// this.mailcontent
		mc.setFilename(this.fileFileName);
		mc.setUserid("T0D507");
		mc.setPassword("TYYKjtXMnZyn");
		// System.out.println(this.getMailcontent());
		// 將對象轉化爲json
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(mc);
		// 發送json到郵件api
		// String sj = sendJson(jsonInString);
		String sj = sendJsonWithApache(jsonInString);

		this.inputStream = new ByteArrayInputStream(sj.getBytes());
		System.out.println("郵件已經發送成功\n主題:"+mc.getSubject());
		return "0";
	}
	
	// 主製程審核結束，通知細製程負責人進行細製程作業
	public String sendMailAfterMainProcessAuditEnd(String submanId,String submanName,Map<String, List<ManutechModel>> map) throws Exception {
		MailContent mc = new MailContent();
		String content = "";
		
		Set<String> keySet = map.keySet();
		for(String key:keySet){
			List<ManutechModel> list = map.get(key);
			ManutechModel model=list.get(0);
			String address = model.getManutechEmail();
			String manutechId=model.getManutechId();
			String manutechName=model.getManutechName();
			String projectName=model.getProjectName();
			String phaseName=model.getPhaseName();
			String partName=model.getPartName();
			String lordprocesName="[";
			StringBuffer sb=new StringBuffer();
			for(ManutechModel m:list){
				sb.append(m.getLordprcsName()+",");
			}
			String sbStr=sb.toString();
			lordprocesName+=sbStr.substring(0, sbStr.lastIndexOf(","));
			lordprocesName+="]";
			
			if (address.equals("")) {//未查到地址，發送給資訊中心
				content = "【工管系統-FlowChart】"+submanName+"("+submanId+")"+"提交的主製程已經審核完成,給制工"+manutechName+"("+manutechId+")發送郵件失敗，原因：未在工管系統中給此制工設置郵箱地址。（系統自動發送,請勿回復！）";
				setSubject("【工管系統-FlowChart】"+submanName+"("+submanId+")"+"提交的主製程已經審核完成,給制工"+manutechName+"("+manutechId+")發送郵件失敗，原因：未在工管系統中給此制工設置郵箱地址。（系統自動發送,請勿回復！）");
				address="hzcd-mis-sys1@mail.foxconn.com";
			} else {
				content = manutechName+"("+manutechId+")，你好！<br>";
				content += "【工管系統-FlowChart】<b style='color:red;'>"+submanName+"</b>("+submanId+")派發的[專案:<b style='color:red;'>"
						+projectName+"</b>階段:<b style='color:red;'>"+phaseName+"</b>零件:<b style='color:red;'>"
						+partName+"</b>]的主製程:<b style='color:red;'>"+lordprocesName
						+"</b>的<b style='color:red;'>細製程作業</b>需要操作，請登錄系統進行細製程相關作業。（系統自動發送,請勿回復！）";
				content += "<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
				setSubject("【工管系統-FlowChart】"+submanName+"("+submanId+")派發的[專案:"+projectName+"階段:"+phaseName+"零件:"
						+partName+"]的主製程:"+lordprocesName+"的細製程作業需要操作，請登錄系統進行細製程相關作業。（系統自動發送,請勿回復！）");
			}
			if (address==null||address.equals("")) {
				address = "hzcd-mis-sys1@mail.foxconn.com";
				content+="<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<<未查到收件人郵箱,轉發到本郵箱>>";
			}
			setTo(address);//"hzcd-mis-sys1@mail.foxconn.com"
			mc.setTo(this.to);
			mc.setSubject(this.subject);
			mc.setSubtype("text/html");
			mc.setBody(content);// this.mailcontent
			mc.setFilename(this.fileFileName);
			mc.setUserid("T0D507");
			mc.setPassword("TYYKjtXMnZyn");
			// System.out.println(this.getMailcontent());
			// 將對象轉化爲json
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(mc);
			// 發送json到郵件api
			// String sj = sendJson(jsonInString);
			String sj = sendJsonWithApache(jsonInString);
			
			this.inputStream = new ByteArrayInputStream(sj.getBytes());
			System.out.println("郵件已經發送成功\n主題:"+mc.getSubject());
		}
		
		return "0";
	}
	
	//客戶圖檔上傳成功后給相關人員發送通知郵件
	public String sendEmailAfterCusFileUp(String address,customModel cm){
		String result="0";
		MailContent mc = new MailContent();
		String content = "";
		if (address.equals("")) {// 審核完成，發送給提交人
			content = "";
			setSubject("【工管系統-客戶圖檔】工管系統中上傳了新的客戶圖檔，請登錄系統進行相關作業。（系統自動發送,請勿回復！）");
		} else {
			content = "【工管系統-客戶圖檔】專案<font color='red'>"+new fileCustomMethod().getProjectNameById(cm.getProject())+"</font>中上傳了新的客戶圖檔，請登錄系統進行相關作業。（系統自動發送,請勿回復！）<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
			content +="新上傳的客戶圖檔信息：<br>";
			//content+="【專案】：<font color='red'>"+new fileCustomMethod().getProjectNameById(cm.getProject())+"</font><br>";
			content+="【客戶料號】：<font color='red'>"+cm.getCustomno()+"</font><br>";
			//content+="【圖檔文件名稱】：<font color='red'>"+cm.getCustomfile()+"</font><br>";
			content+="【圖檔文件類型】：<font color='red'>"+cm.getCustomtype()+"</font><br>";
			content+="【備註】：<font color='red'>"+cm.getMemo()+"</font><br>";
			setSubject("【工管系統-客戶圖檔】專案["+new fileCustomMethod().getProjectNameById(cm.getProject())+"]中上傳了新的客戶圖檔，請登錄系統進行相關作業。（系統自動發送,請勿回復！）");
		}
		if (address==null||address.equals("")) {
			address = "hzcd-mis-sys1@mail.foxconn.com";
			content+="<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<<未查到收件人郵箱,轉發到本郵箱>>";
		}
		setTo(address);//"hzcd-mis-sys1@mail.foxconn.com"
		mc.setTo(this.to);
		mc.setSubject(this.subject);
		mc.setSubtype("text/html");
		mc.setBody(content);// this.mailcontent
		mc.setFilename(this.fileFileName);
		mc.setUserid("T0D507");
		mc.setPassword("TYYKjtXMnZyn");
		// System.out.println(this.getMailcontent());
		// 將對象轉化爲json
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString="";
		try {
			jsonInString = mapper.writeValueAsString(mc);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		// 發送json到郵件api
		// String sj = sendJson(jsonInString);
		String sj = sendJsonWithApache(jsonInString);

		this.inputStream = new ByteArrayInputStream(sj.getBytes());
		
		return result;
	}
	
	//多個圖檔上傳之後發送通知郵件
	public String sendMailAfterMulitPicFileUpload(String address,List<customModel> customModelList){
		String result="0";
		MailContent mc = new MailContent();
		String content = "";
		String projectName=new fileCustomMethod().getProjectNameById(customModelList.get(0).getProject());
		
		if (address.equals("")) {// 審核完成，發送給提交人
			content = "";
			setSubject("【工管系統-Apple圖檔下載    通知】"+new SimpleDateFormat("MM/dd HH:mm").format(new Date())+"  downloaded "+projectName+" dwgs（系統自動發送,請勿回復！）");
		} else {
			content = "<b style='color:red;'>"+projectName+"</b> dwgs were downloaded, pls review them.（系統自動發送,請勿回復！）<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
			content +="新上傳的客戶圖檔信息：<br>";
			int count=0;//記錄文件個數
			
			for (customModel cm : customModelList) {
				count++;
				content +="<font color='blue'>"+count+"</font>：<br>";
				content+="【專案】：<font color='red'>"+projectName+"</font><br>";
				content+="【客戶料號】：<font color='red'>"+cm.getCustomno()+"</font><br>";
				content+="【圖檔文件名稱】：<font color='red'>"+cm.getCustomfile()+"</font><br>";
				content+="【圖檔文件類型】：<font color='red'>"+cm.getCustomtype()+"</font><br>";
				content+="【備註】：<font color='red'>"+cm.getMemo()+"</font><br>";
				content+="【上傳人】：<font color='red'>"+cm.getCreaterName()+"("+cm.getCreater()+")</font><br>";
				content+="【上傳時間】：<font color='red'>"+cm.getCreatedate()+"</font><br><hr>";
			}
			
			setSubject("【工管系統-Apple圖檔下載    通知】"+new SimpleDateFormat("MM/dd HH:mm").format(new Date())+"  downloaded "+projectName+" dwgs（系統自動發送,請勿回復！）");
		}
		if (address==null||address.equals("")) {
			address = "hzcd-mis-sys1@mail.foxconn.com";
			content+="<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<<未查到收件人郵箱,轉發到本郵箱>>";
		}
		setTo(address);//"hzcd-mis-sys1@mail.foxconn.com"
		mc.setTo(this.to);
		mc.setSubject(this.subject);
		mc.setSubtype("text/html");
		mc.setBody(content);// this.mailcontent
		mc.setFilename(this.fileFileName);
		mc.setUserid("T0D507");
		mc.setPassword("TYYKjtXMnZyn");
		// System.out.println(this.getMailcontent());
		// 將對象轉化爲json
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString="";
		try {
			jsonInString = mapper.writeValueAsString(mc);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		// 發送json到郵件api
		// String sj = sendJson(jsonInString);
		String sj = sendJsonWithApache(jsonInString);

		this.inputStream = new ByteArrayInputStream(sj.getBytes());
		System.out.println("郵件已經發送成功\n主題:"+mc.getSubject());
		return result;
	}
	
	//多個文件上傳之後發送通知郵件
	public String sendMailAfterMulitFileUpload(String address,List<FileDTO> fileList){
		String result="0";
		String fileType=fileList.get(0).getFileType();
		MailContent mc = new MailContent();
		String content = "";
		if (address.equals("")) {// 審核完成，發送給提交人
			content = "";
			setSubject("【工管系統-"+fileType+"】工管系統中上傳了新的"+fileType+"，請登錄系統進行相關作業。（系統自動發送,請勿回復！）");
		} else {
			content = "【工管系統-"+fileType+"】上傳了新的"+fileType+"，請登錄系統進行相關作業。（系統自動發送,請勿回復！）<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
			content +="新上傳的"+fileType+"信息：<br>";
			int count=0;//記錄文件個數
			
			for (FileDTO dto : fileList) {
				count++;
				content +="<font color='blue'>"+count+"</font>：<br>";
				content+="【專案】：<font color='red'>"+dto.getProject()+"</font><br>";
				content+="【階段】：<font color='red'>"+dto.getPhase()+"</font><br>";
				content+="【文件名稱】：<font color='red'>"+dto.getOriginalFileName()+"</font><br>";
				content+="【文件類型】：<font color='red'>"+dto.getFileType()+"</font><br>";
				content+="【備註】：<font color='red'>"+dto.getMemo()+"</font><br>";
				content+="【上傳人】：<font color='red'>"+dto.getCreator()+"("+dto.getCreatorAccount()+")</font><br>";
				content+="【上傳時間】：<font color='red'>"+dto.getCreateDate()+"</font><br><hr>";
			}
			
			setSubject("【工管系統-"+fileType+"】上傳了新的"+fileType+"，請登錄系統進行相關作業。（系統自動發送,請勿回復！）");
		}
		if (address==null||address.equals("")) {
			address = "hzcd-mis-sys1@mail.foxconn.com";
			content+="<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<<未查到收件人郵箱,轉發到本郵箱>>";
		}
		setTo(address);//"hzcd-mis-sys1@mail.foxconn.com"
		mc.setTo(this.to);
		mc.setSubject(this.subject);
		mc.setSubtype("text/html");
		mc.setBody(content);// this.mailcontent
		mc.setFilename(this.fileFileName);
		mc.setUserid("T0D507");
		mc.setPassword("TYYKjtXMnZyn");
		// System.out.println(this.getMailcontent());
		// 將對象轉化爲json
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString="";
		try {
			jsonInString = mapper.writeValueAsString(mc);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		// 發送json到郵件api
		// String sj = sendJson(jsonInString);
		String sj = sendJsonWithApache(jsonInString);
		
		this.inputStream = new ByteArrayInputStream(sj.getBytes());
		System.out.println("郵件已經發送成功\n主題:"+mc.getSubject());
		return result;
	}
	
	// 駁回郵件發送
	public String backmail(String adress, String empno, String name) throws JsonProcessingException {
		MailContent mc = new MailContent();
		String content = name
				+ "：<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;見信好！<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "您提交的flowchart被主管  " + empno + " 駁回，請登錄系統修改后再提交。<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
		setSubject("【FlowChart】您有一筆單被駁回。（系統自動發送,請勿回復！）");
		if (adress==null||adress.equals("")) {
			adress = "hzcd-mis-sys1@mail.foxconn.com";
			content+="<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<<未查到收件人郵箱,轉發到本郵箱>>";
		}
		setTo(adress);//"hzcd-mis-sys1@mail.foxconn.com"
		mc.setTo(this.to);
		mc.setSubject(this.subject);
		mc.setSubtype("text/html");
		mc.setBody(content);// this.mailcontent
		mc.setFilename(this.fileFileName);
		mc.setUserid("T0D507");
		mc.setPassword("TYYKjtXMnZyn");
		// System.out.println(this.getMailcontent());
		// 將對象轉化爲json
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(mc);
		// 發送json到郵件api
		// String sj = sendJson(jsonInString);
		String sj = sendJsonWithApache(jsonInString);

		this.inputStream = new ByteArrayInputStream(sj.getBytes());
		System.out.println("郵件已經發送成功\n主題:"+mc.getSubject());
		return "0";
	}
	
	// 駁回主製程審核郵件發送(提交人郵箱，駁回主管工號姓名，提交人姓名，專案階段零件信息，審核類型)
	public String rejectMainProcessAuditMail(String adress, String empno, String name,String[] message,String auditType) throws JsonProcessingException {
		MailContent mc = new MailContent();
		String content = name
				+ "：<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;見信好！<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "【工管系統-FlowChart】您提交的[專案:<b style='color:red;'>"+message[0]+"</b>階段:<b style='color:red;'>"+message[1]+"</b>零件:<b style='color:red;'>"+message[2]+"</b>]的[<b style='color:red;'>"+auditType+"</b>]被主管  " + empno + " 駁回，請登錄系統修改后再提交。（系統自動發送,請勿回復！）"
				+"<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
		setSubject("【工管系統-FlowChart】您提交的[專案:"+message[0]+"階段:"+message[1]+"零件:"+message[2]+"]的["+auditType+"]被主管  " + empno + " 駁回，請登錄系統修改后再提交。（系統自動發送,請勿回復！）");
		if (adress==null||adress.equals("")) {
			adress = "hzcd-mis-sys1@mail.foxconn.com";
			content+=name+" 未收到郵件<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<<未查到收件人郵箱,轉發到本郵箱>>";
		}
		setTo(adress);//"hzcd-mis-sys1@mail.foxconn.com"
		mc.setTo(this.to);
		mc.setSubject(this.subject);
		mc.setSubtype("text/html");
		mc.setBody(content);// this.mailcontent
		mc.setFilename(this.fileFileName);
		mc.setUserid("T0D507");
		mc.setPassword("TYYKjtXMnZyn");
		// System.out.println(this.getMailcontent());
		// 將對象轉化爲json
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(mc);
		// 發送json到郵件api
		// String sj = sendJson(jsonInString);
		String sj = sendJsonWithApache(jsonInString);

		this.inputStream = new ByteArrayInputStream(sj.getBytes());
		System.out.println("郵件已經發送成功\n主題:"+mc.getSubject());
		return "0";
	}

	// 發行零件郵件發送
	public String publishPart(String address, String part, String version, String memo, String hhno,
			String partid, String typename, String projectId, String p_id, String projectName, String phaseName ) throws JsonProcessingException, SQLException {
		PrcsOrderMethod po = new PrcsOrderMethod();
		
		if (po.fxupdate(hhno, version, partid)) {
			if (!po.autono(projectId, typename, p_id, part).equals("1")) {
				MailContent mc = new MailContent();
				String title = "【工管系統-Flowchart】 專案:"+projectName+"階段:"+phaseName+ "零件:" + part + "的Flowchart已發行！（系統自動發送,請勿回復！）";
				String content = "【工管系統-Flowchart】 專案:<b style='color:red;'>"+projectName+"</b>階段:<b style='color:red;'>"+phaseName+ "</b>零件:<b style='color:red;'>" 
						+ part + "</b>的Flowchart已發行！（系統自動發送,請勿回復！）"
						+ "<br/>Flowchart信息：" 
						+ "<br/>專案：<b style='color:red;'>" + projectName+"</b>"
						+ "<br/>階段：<b style='color:red;'>" + phaseName+"</b>"
						+ "<br/>零件：<b style='color:red;'>" + part+"</b>"
						+ "<br/>發行版次：<b style='color:red;'>" + version+"</b>"
						+ "<br/>發行原因：<b style='color:red;'>" + memo+"</b>"
						+ "<br/>請登錄系統進行預覽。<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
				setSubject("" + title);
				setTo(address);// "hzcd-mis-sys1@mail.foxconn.com"
				mc.setTo(this.to);
				mc.setSubject(this.subject);
				mc.setSubtype("text/html");
				mc.setBody(content);// this.mailcontent
				mc.setFilename(this.fileFileName);
				mc.setUserid("T0D507");
				mc.setPassword("TYYKjtXMnZyn");
				// System.out.println(this.getMailcontent());
				// 將對象轉化爲json
				ObjectMapper mapper = new ObjectMapper();
				String jsonInString = mapper.writeValueAsString(mc);
				// 發送json到郵件api
				// String sj = sendJson(jsonInString);
				String sj = sendJsonWithApache(jsonInString);
				this.inputStream = new ByteArrayInputStream(sj.getBytes());
				System.out.println("郵件已經發送成功\n主題:"+mc.getSubject());
			}
		}
		return "0";
	}
	
	// 包裝規範審核任務郵件通知
	public String packageSpeAuditMail(String address,PackageSpeBasic basic,String nextType) throws JsonProcessingException, SQLException {
		String projectName=basic.getProject_name();
		String phaseName=basic.getPhase_name();
		String partName=basic.getPart_name();
		String psTitle=basic.getTitle();
		
		MailContent mc = new MailContent();
		String title = "【工管系統-包裝規範】 專案:"+projectName+"階段:"+phaseName+ "零件:" + partName + "的["+psTitle+"]組立包裝作業規範需要["+nextType+"]審核！（系統自動發送,請勿回復！）";
		String content = "【工管系統-包裝規範】 專案:<b style='color:red;'>"+projectName+"</b>階段:<b style='color:red;'>"+phaseName+ "</b>零件:<b style='color:red;'>" 
				+ partName + "</b>的<b style='color:red;'>"+psTitle+"</b>組立包裝作業規範需要<b style='color:red;'>"+nextType+"</b>審核！（系統自動發送,請勿回復！）"
				+ "<br/>包裝規範信息：" 
				+ "<br/>專案：<b style='color:red;'>" + projectName+"</b>"
				+ "<br/>階段：<b style='color:red;'>" + phaseName+"</b>"
				+ "<br/>零件：<b style='color:red;'>" + partName+"</b>"
				+ "<br/>請登錄系統進行預覽。<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
		setSubject("" + title);
		setTo(address);// "hzcd-mis-sys1@mail.foxconn.com"
		mc.setTo(this.to);
		mc.setSubject(this.subject);
		mc.setSubtype("text/html");
		mc.setBody(content);// this.mailcontent
		mc.setFilename(this.fileFileName);
		mc.setUserid("T0D507");
		mc.setPassword("TYYKjtXMnZyn");
		// System.out.println(this.getMailcontent());
		// 將對象轉化爲json
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(mc);
		// 發送json到郵件api
		// String sj = sendJson(jsonInString);
		String sj = sendJsonWithApache(jsonInString);
		this.inputStream = new ByteArrayInputStream(sj.getBytes());
		System.out.println("郵件已經發送成功\n主題:"+mc.getSubject());
		
		return "0";
	}
	
	// 包裝規範駁回任務郵件通知
	public String packageSpeOverruleMail(String address,PackageSpeBasic basic,String nowType) throws JsonProcessingException, SQLException {
		String projectName=basic.getProject_name();
		String phaseName=basic.getPhase_name();
		String partName=basic.getPart_name();
		String psTitle=basic.getTitle();
		
		MailContent mc = new MailContent();
		String title = "【工管系統-包裝規範】 專案:"+projectName+"階段:"+phaseName+ "零件:" + partName + "的["+psTitle+"]組立包裝作業規範的["+nowType+"]審核被駁回，請修改后重新提交！（系統自動發送,請勿回復！）";
		String content = "【工管系統-包裝規範】 專案:<b style='color:red;'>"+projectName+"</b>階段:<b style='color:red;'>"+phaseName+ "</b>零件:<b style='color:red;'>" 
				+ partName + "</b>的<b style='color:red;'>"+psTitle+"</b>組立包裝作業規範的<b style='color:red;'>"+nowType+"</b>審核被駁回，請修改后重新提交！（系統自動發送,請勿回復！）"
				+ "<br/>包裝規範信息：" 
				+ "<br/>專案：<b style='color:red;'>" + projectName+"</b>"
				+ "<br/>階段：<b style='color:red;'>" + phaseName+"</b>"
				+ "<br/>零件：<b style='color:red;'>" + partName+"</b>"
				+ "<br/>請登錄系統進行預覽。<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
		setSubject("" + title);
		setTo(address);// "hzcd-mis-sys1@mail.foxconn.com"
		mc.setTo(this.to);
		mc.setSubject(this.subject);
		mc.setSubtype("text/html");
		mc.setBody(content);// this.mailcontent
		mc.setFilename(this.fileFileName);
		mc.setUserid("T0D507");
		mc.setPassword("TYYKjtXMnZyn");
		// System.out.println(this.getMailcontent());
		// 將對象轉化爲json
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(mc);
		// 發送json到郵件api
		// String sj = sendJson(jsonInString);
		String sj = sendJsonWithApache(jsonInString);
		this.inputStream = new ByteArrayInputStream(sj.getBytes());
		System.out.println("郵件已經發送成功\n主題:"+mc.getSubject());
		
		return "0";
	}

	// 包裝規範發行任務郵件通知
	public String packageSpePublishMail(String address,PackageSpeBasic basic) throws JsonProcessingException, SQLException {
		String projectName=basic.getProject_name();
		String phaseName=basic.getPhase_name();
		String partName=basic.getPart_name();
		String psTitle=basic.getTitle();
		
		MailContent mc = new MailContent();
		String title = "【工管系統-包裝規範】 專案:"+projectName+"階段:"+phaseName+ "零件:" + partName + "的["+psTitle+"]組立包裝作業規範需要發行！（系統自動發送,請勿回復！）";
		String content = "【工管系統-包裝規範】 專案:<b style='color:red;'>"+projectName+"</b>階段:<b style='color:red;'>"+phaseName+ "</b>零件:<b style='color:red;'>" 
				+ partName + "</b>的<b style='color:red;'>"+psTitle+"</b>組立包裝作業規範需要發行！（系統自動發送,請勿回復！）"
				+ "<br/>包裝規範信息：" 
				+ "<br/>專案：<b style='color:red;'>" + projectName+"</b>"
				+ "<br/>階段：<b style='color:red;'>" + phaseName+"</b>"
				+ "<br/>零件：<b style='color:red;'>" + partName+"</b>"
				+ "<br/>請登錄系統進行預覽。<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
		setSubject("" + title);
		setTo(address);// "hzcd-mis-sys1@mail.foxconn.com"
		mc.setTo(this.to);
		mc.setSubject(this.subject);
		mc.setSubtype("text/html");
		mc.setBody(content);// this.mailcontent
		mc.setFilename(this.fileFileName);
		mc.setUserid("T0D507");
		mc.setPassword("TYYKjtXMnZyn");
		// System.out.println(this.getMailcontent());
		// 將對象轉化爲json
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(mc);
		// 發送json到郵件api
		// String sj = sendJson(jsonInString);
		String sj = sendJsonWithApache(jsonInString);
		this.inputStream = new ByteArrayInputStream(sj.getBytes());
		System.out.println("郵件已經發送成功\n主題:"+mc.getSubject());
		
		return "0";
	}
	
	// 包裝規範發行完成郵件通知
	public String packageSpePublishFinishedInfo(String address,PackageSpeBasic basic) throws JsonProcessingException, SQLException {
		String projectName=basic.getProject_name();
		String phaseName=basic.getPhase_name();
		String partName=basic.getPart_name();
		String psTitle=basic.getTitle();
		
		MailContent mc = new MailContent();
		String title = "【工管系統-包裝規範】 專案:"+projectName+"階段:"+phaseName+ "零件:" + partName + "的["+psTitle+"]組立包裝作業規範已經發行，請登錄系統查看！（系統自動發送,請勿回復！）";
		String content = "【工管系統-包裝規範】 專案:<b style='color:red;'>"+projectName+"</b>階段:<b style='color:red;'>"+phaseName+ "</b>零件:<b style='color:red;'>" 
				+ partName + "</b>的<b style='color:red;'>"+psTitle+"</b>組立包裝作業規範已經發行，請登錄系統查看！（系統自動發送,請勿回復！）"
				+ "<br/>包裝規範信息：" 
				+ "<br/>專案：<b style='color:red;'>" + projectName+"</b>"
				+ "<br/>階段：<b style='color:red;'>" + phaseName+"</b>"
				+ "<br/>零件：<b style='color:red;'>" + partName+"</b>"
				+ "<br/>請登錄系統進行預覽。<br>工管系統網址： http://10.244.231.103/ePDMWeb<br>";
		setSubject("" + title);
		setTo(address);// "hzcd-mis-sys1@mail.foxconn.com"
		mc.setTo(this.to);
		mc.setSubject(this.subject);
		mc.setSubtype("text/html");
		mc.setBody(content);// this.mailcontent
		mc.setFilename(this.fileFileName);
		mc.setUserid("T0D507");
		mc.setPassword("TYYKjtXMnZyn");
		// System.out.println(this.getMailcontent());
		// 將對象轉化爲json
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(mc);
		// 發送json到郵件api
		// String sj = sendJson(jsonInString);
		String sj = sendJsonWithApache(jsonInString);
		this.inputStream = new ByteArrayInputStream(sj.getBytes());
		System.out.println("郵件已經發送成功\n主題:"+mc.getSubject());
		
		return "0";
	}
	
	public File getFile() {
		return this.file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return this.fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getTo() {
		return this.to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMailcontent() {
		return this.mailcontent;
	}

	public void setMailcontent(String mailcontent) {
		this.mailcontent = mailcontent;
	}
}
