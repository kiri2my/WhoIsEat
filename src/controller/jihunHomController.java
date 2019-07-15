package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Forward;
import service.YjhStoreMM;




@WebServlet({"/insertstoreinfo"})
public class jihunHomController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    	
	@SuppressWarnings("unused")
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		String cp = request.getContextPath();
		String url = uri.substring(cp.length());
		String path=null;
		System.out.println("url="+url);
		HttpSession session;
		YjhStoreMM sMM=new YjhStoreMM(request, response);
		
		Forward fw=null;
		
		
		switch(url) {			
			
		case "/insertstoreinfo":
			fw=sMM.insertStoreInfo();
			break;
			
		}
		
		if(fw!=null) 
		{
			if(fw.isRedirect()) 
			{
				response.sendRedirect(fw.getPath());
			}
			else 
			{
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
