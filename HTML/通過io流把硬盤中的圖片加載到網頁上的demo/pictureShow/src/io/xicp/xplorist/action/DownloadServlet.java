package io.xicp.xplorist.action;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.config.ConfigReader;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
    }
    
    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
    	super.destroy();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstRootPath = ConfigReader.getPropertyByPath("config/file", "fileRootPath");
		String key = request.getParameter("key");
		String fileName = request.getParameter("fileName");
		String module = request.getParameter("module");
		if(module!=null){
			module=new String(module.getBytes("iso8859-1"),"utf-8");
		}
		String url = firstRootPath + request.getSession().getAttribute(key);
	
			File file = new File(url);
			if (file.exists()) {
				response.setContentType("application/x-download");
				int fileLength = (int) file.length();
				response.setContentLength(fileLength);
				//如果傳文件名了，則用所傳名，否則自動生成
				if(fileName!=null){
					fileName=new String(fileName.getBytes("iso8859-1"),"utf-8");
				}else{
					fileName = url;
				}
			    response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

			if (fileLength != 0) {
				  java.io.OutputStream os = response.getOutputStream();
				   java.io.FileInputStream fis = new java.io.FileInputStream(url);
				   byte[] b = new byte[1024];
				   int i = 0;
				   while ((i = fis.read(b)) > 0) {
				    os.write(b, 0, i);
				   }
				   fis.close();
				   os.flush();
				   os.close();
			}
	
		} else {//文件不存在時跳轉到錯誤頁面   F1648300  2012/11/15
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

}
