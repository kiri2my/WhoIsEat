package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Forward;
import service.KirimService;




@WebServlet({"/login","/reservetime","/reservetimetoselectlocation","/selectlocation","/showchoicelist"
	,"/toshowchoicelist"})
public class KirimFwController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		String cp = request.getContextPath();
		String url = uri.substring(cp.length());
		String path=null;
		System.out.println("url="+url);
		HttpSession session;
		
		Forward fw=null;
		KirimService ks = new KirimService(request,response);
		
		switch(url) {
		
		case "/login":
			fw=ks.login(); 
			break;
		case "/reservetime":
			fw=new Forward();
			fw.setPath("reservetime.jsp");
			fw.setRedirect(true);
			break;
		case "/reservetimetoselectlocation":
			fw=ks.reservetimetoselectlocation();
			break;
		case "/selectlocation":
			fw=new Forward();
			fw.setPath("selectlocation.jsp");
			fw.setRedirect(true);
			break;
		case "/showchoicelist":	
			String skind = request.getSession().getAttribute("kind").toString();
			System.out.println("skind="+skind);
			if(skind.contains("N")) {
				ks.insertchoicelist();
				fw=ks.showchoicelist();
			}else if(skind.contains("S")){
				fw=ks.showchoicelist();
			}			
			break;
		case "/toshowchoicelist":
			fw=ks.toshowchoicelist();
			break;
		
			
		}
		
		if(fw!=null) {
			if(fw.isRedirect()) {
				response.sendRedirect(fw.getPath());
			}else {
				request.getRequestDispatcher(fw.getPath()).forward(request, response);
			}
		}
		
		
	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
