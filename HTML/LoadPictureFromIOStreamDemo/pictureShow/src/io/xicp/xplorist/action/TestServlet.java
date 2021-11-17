package io.xicp.xplorist.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.xicp.xplorist.model.NewMouldMainBean;
import net.sf.json.JSONObject;
import util.download.GenerateDownloadURL_Util;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JSONObject jsonObject = new JSONObject();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<NewMouldMainBean> newMouldList = new ArrayList<NewMouldMainBean>();
		for(int i = 0; i < 10; i++) {
			NewMouldMainBean bean = new NewMouldMainBean();
			newMouldList.add(bean);
		}
		for(int i = 0; i < newMouldList.size(); i++){
			NewMouldMainBean mainBean = newMouldList.get(i);
			String file_save_path = "D:\\file\\NewMouldDraw\\pic\\2018\\12\\26";
			String file_save_name = "(093207645)login-bg-8.jpg";
			String pic_file_path = file_save_path.substring(file_save_path.indexOf(":\\")+1) + File.separator + file_save_name;
			mainBean.setPic_file_path(pic_file_path);
		}
		newMouldList = (List<NewMouldMainBean>)GenerateDownloadURL_Util.setDtoUrlField((List<NewMouldMainBean>)newMouldList, request.getSession());
		
		String contextPath = request.getContextPath();
		jsonObject.put("newMouldList", newMouldList);
		jsonObject.put("contextPath", contextPath);
		sendJson(jsonObject, response);
	}
	
	// 返回json數據
	public void sendJson(JSONObject jsonObject, HttpServletResponse response) {
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}

}
