package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.KisService;

@WebServlet({"/doublecheck","/showstoredetail","/showstorechoice"})
public class KisAjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unused")
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		String cp = request.getContextPath();
		String url = uri.substring(cp.length());
		System.out.println("url="+url);
		HttpSession session;
		KisService ks = new KisService(request, response);
		String jsonObj=null;
		
		switch(url) {
		case "/doublecheck":
			jsonObj=ks.doublecheck();
			break;
		case "/showstoredetail":
			jsonObj=ks.showstoredetail();
			break;	
		case "/showstorechoice":
			jsonObj=ks.showstorechoice();
			break;	
	
		
		}
		
		
				
		if(jsonObj!=null) {
			
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
		}
		
		
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
