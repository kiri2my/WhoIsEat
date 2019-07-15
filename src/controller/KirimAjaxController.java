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






@WebServlet({"/menukind","/storesidebar","/membersidebar","/sendrequest","/requestcancel",
	"/accept","/reject","/acceptstore","/rejectstore","/confirmaccept","/confirmreject","/getprofile"})
public class KirimAjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		String cp = request.getContextPath();
		String url = uri.substring(cp.length());
		System.out.println("url="+url);
		
		String jsonObj=null;
		KirimService ks = new KirimService(request,response);
		
		switch(url) {
		case "/menukind":
			jsonObj = ks.menukind();
			break;
		case "/storesidebar":
			jsonObj=ks.storesidebar();
			break;
		case "/membersidebar":
			jsonObj=ks.membersidebar();
			break;	
		case "/sendrequest":
			jsonObj=ks.sendrequest();
			break;
		case "/requestcancel":	
			jsonObj=ks.requestcancel();
			break;
		case "/accept":
			jsonObj=ks.accept();
			ks.updateC_kindAccept();
			ks.updateM_msgstAccept();
			break;
		case "/reject":
			ks.updateM_msgstReject();
			break;
		case "/acceptstore":
			jsonObj = ks.acceptstore();
			ks.updateC_kindAcceptStore();
			ks.updateAc_eatAcceptStore();
			break;
		case "/rejectstore":	
			jsonObj = ks.rejectstore();
			ks.updateC_kindRejectStore();
			ks.updateAc_eatRejectStore();
			break;
		case "/confirmaccept":
			ks.confirmaccept();
			break;
		case "/confirmreject":
			ks.confirmreject();
			break;
		case "/getprofile":
			jsonObj = ks.getprofile();
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
