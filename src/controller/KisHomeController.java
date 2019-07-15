package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Forward;
import service.KisService;

@WebServlet({"/memberjoin", "/storejoin","/showstorelist","/toshowstorelist","/storeserch","/logout","/toreservetime"
	,"/updatemember","/updatestore","/updatestoredetail","/cancelstorechoice"})
public class KisHomeController extends HttpServlet {
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
		KisService ks=new KisService(request, response);
		Forward fw=null;
		
		
		switch(url) {			
		case "/memberjoin":
			fw=ks.memberjoin();
			break;
		case "/storejoin":
			fw=ks.storejoin();
			break;
		case "/showstorelist":
			fw=ks.showstorelist();
			break;
		case "/toshowstorelist":
			fw=ks.toshowstorelist();
			break;
		case "/storeserch":
			fw=ks.storeserch();
			break;
		case "/toreservetime":
			fw=new Forward();
			request.setAttribute("url", "reservetime");
			fw.setPath("main.jsp");
			fw.setRedirect(false);
			break;
		case "/logout":
			fw=ks.logout();
			break;
		case "/updatemember":
			fw=ks.updatemember();
			break;
		case "/updatestore":
			fw=ks.updatestore();
			break;
		case "/updatestoredetail":
			fw=ks.updatestoredetail();
			break;
		case "/cancelstorechoice":
			fw=ks.cancelstorechoice();
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
