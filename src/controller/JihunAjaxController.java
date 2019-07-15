package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.YjhStoreMM;

@WebServlet({"/insertstinfo","/selectstoredetail"})
public class JihunAjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @SuppressWarnings("unused")
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		String cp = request.getContextPath();
		String url = uri.substring(cp.length());
		System.out.println("url="+url);
		HttpSession session;
		
		String jsonObj=null;
		YjhStoreMM smm=new YjhStoreMM(request, response);
		switch(url) 
		{
		
		case "/insertstinfo":
			smm.insertStoreInfo();
			break;
		case "/selectstoredetail":
			smm.selectstoredetail();
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
