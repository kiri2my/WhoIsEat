package service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import bean.Forward;
import bean.Member;
import bean.StoreInfo;
import dao.KisDao;
import dao.YjhStoreDao;

public class KisService {
	HttpServletRequest request;
	HttpServletResponse response;
	
	private KisDao ksDao; // 회원 DB서비스
	private HttpSession session;


	public KisService(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public String doublecheck() {
		String id= request.getParameter("id");
		System.out.println("id="+id);
		ksDao=new KisDao();
		String re=ksDao.doublecheck(id);
		ksDao.close();
		String jsonObj=null;
		if(re!=null) {
			System.out.println(re);
			jsonObj=new Gson().toJson(re);
		}
		return jsonObj;		
	}

	public Forward memberjoin() {
		ksDao = new KisDao();
		Forward fw = new Forward();
		Member mb = new Member();
		String uploadPath=request.getSession().getServletContext().getRealPath("upload"); //물리적 주소
		int size=10*1024*1024; //업로드 파일 크기 10MB까지 제한
		
		//clear 후 upload폴더가 부재하다면 생성핳 것.
		File dir=new File(uploadPath);
		if(!dir.isDirectory()) {
			dir.mkdirs(); //폴더 생성
		}
		
		try {
			MultipartRequest multi=new MultipartRequest(request, uploadPath, size, "utf-8", 
									new DefaultFileRenamePolicy());
		 
		mb.setId(multi.getParameter("id"));
		mb.setPw(multi.getParameter("pw"));
		mb.setPhone(multi.getParameter("phone"));
		mb.setKind("N");
		mb.setNickname(multi.getParameter("nickname"));
		mb.setProfilephoto(multi.getFilesystemName("profilephoto"));
		mb.setOneline(multi.getParameter("oneline"));
		boolean result = ksDao.join(mb);		
		if (result) {
			boolean result2 = ksDao.memberJoin(mb);
			ksDao.close();
			if(result2) {
			fw.setPath("loginform.jsp");
			fw.setRedirect(true);
			}
		} else {
			request.setAttribute("msg", "회원가입에 실패하였습니다.");
			fw.setPath("memberjoin.jsp");
			fw.setRedirect(false);
		}
		
		} catch (IOException e) {
			System.out.println("memberjoin 예외");
			e.printStackTrace();
		}
		
		return fw;
	}
	
	
	
	public Forward storejoin() {
		ksDao = new KisDao(); 
		Member mb = new Member();
		String id=request.getParameter("id");
		mb.setStorenum(request.getParameter("storenum"));
		mb.setId(request.getParameter("id"));
		mb.setPw(request.getParameter("pw"));
		mb.setPhone(request.getParameter("phone"));
		mb.setKind("S");
		boolean result = ksDao.join(mb);		
		Forward fw = new Forward();
		if (result) {
			boolean result2 = ksDao.storeJoin(mb);
			ksDao.close();
			if(result2) {
			fw.setPath("storeupload.jsp");
			request.setAttribute("id", id);
			fw.setRedirect(false);
			}
		} else {
			request.setAttribute("msg", "회원가입에 실패하였습니다.");
			fw.setPath("storejoin.jsp");
			fw.setRedirect(false);
		}
		return fw;
	}

	public Forward showstorelist() {
		String si_do=request.getParameter("si_do");
		String gu=request.getParameter("gu");
		String dong=request.getParameter("dong");		
		System.out.println(si_do+gu+dong);		
		ksDao=new KisDao();
		List<StoreInfo> strList=ksDao.showstorelist(si_do,gu,dong);
		ksDao.close();
		Forward fw=new Forward();
		if(strList!=null && strList.size()!=0) {
			request.setAttribute("strList", new Gson().toJson(strList));
			request.setAttribute("dong", dong);
			fw.setPath("main.jsp");
		}else {
			request.setAttribute("none", "검색하신 지역에 예약가능한 식당이 없습니다.");
			fw.setPath("main.jsp");
		}
		request.setAttribute("url","toshowstorelist");
		fw.setRedirect(false);
		return fw;
		
	}
	public Forward toshowstorelist() {
		String dong = request.getParameter("dong");
		String none = request.getParameter("none");
		
		String list = request.getParameter("list");
		System.out.println("dong="+dong);
		System.out.println("none="+none);
		System.out.println("list="+list);
		
		
		
		Gson gsonObj = new Gson();
		List<StoreInfo> strList = 
				gsonObj.fromJson(list, new TypeToken<List<StoreInfo>>(){}.getType()); 
		//System.out.println(strList.get(0).getSt_info());
		if(strList!=null&& strList.size()!=0) {
			request.setAttribute("dong",dong);
			request.setAttribute("showstoreList", showstoreList(strList));
		}else {
			request.setAttribute("none",none);
		}		
		Forward fw = new Forward();
		fw.setPath("showstorelist.jsp");
		fw.setRedirect(false);
		return fw;
	}

	private Object showstoreList(List<StoreInfo> strList) {
		StringBuilder sb=new StringBuilder();
		 sb.append("<table border=1px solid black id='htmltable'>");
		  for(int i=0; i<strList.size();i++) { 
			  StoreInfo st=strList.get(i);			  
			  sb.append("<tr><th><img src='upload/"+st.getSt_photo()+"' width='100' height='50'></th>");
			  sb.append("<th>"+st.getSt_name()+"</th>");
			  sb.append("<th>"+st.getSt_cate()+"</th>");			  
			  sb.append("<th>"+st.getSt_address()+"</th>");			  
			  sb.append("<th class='showdetail'><input type='button' id='showdetail' onclick='detail("+st.getSt_storenum()+")' value='상세보기'></th>");			  
			  sb.append("<th class='select'><a href='showchoicelist?st_num="+st.getSt_storenum()+"&st_name="+st.getSt_name()+"'>선택</a></th></tr>");			  
		  }
		  sb.append("</table>");
		return sb.toString();
	}

	public String showstoredetail() {
		String storenum= request.getParameter("storenum");
		System.out.println("storenum="+storenum);
		ksDao=new KisDao();
		StoreInfo re=ksDao.showstoredetail(storenum);		
		ksDao.close();
		String jsonObj=null;
		if(re!=null) {			
			jsonObj=new Gson().toJson(re);
		}
		return jsonObj;		
	}

	public String showstorechoice() {
		String storenum= request.getParameter("storenum");
		System.out.println("storenum2="+storenum);
		ksDao=new KisDao();
		String choicenum=ksDao.showstorechoicenum(storenum);		
		ksDao.close();		
		String jsonObj=null;
		if(choicenum!=null) {			
			jsonObj=new Gson().toJson(choicenum);
			System.out.println("ch="+choicenum);
		}
		return jsonObj;	
		
		
	}

	public Forward storeserch() {
		String selmode=request.getParameter("selectmode");
		String sertext=request.getParameter("serchtext");
		System.out.println(selmode+sertext);		
		ksDao=new KisDao();
		List<StoreInfo> strList=ksDao.serchshowstorelist(selmode,sertext);
		ksDao.close();
		System.out.println(strList);
		Forward fw=new Forward();
		if(strList!=null && strList.size()!=0) {
			
			request.setAttribute("strList", new Gson().toJson(strList));
			request.setAttribute("serchtext", sertext);
			fw.setPath("main.jsp");
		}else {
			request.setAttribute("none", "검색하신 지역에 예약가능한 식당이 없습니다.");
			fw.setPath("main.jsp");
		}
		request.setAttribute("url","toshowstorelist");
		fw.setRedirect(false);
		return fw;
	}
	
	
	public Forward logout() {
		Forward fw = new Forward();
		session = request.getSession();
		session.invalidate(); // 세션 무효화
		fw.setPath("loginform.jsp"); //shoppingIndex.jsp
		fw.setRedirect(true);
		return fw;
	}

	public Forward updatemember() {
		ksDao = new KisDao();
		Forward fw = new Forward();
		Member mb = new Member();
		String uploadPath=request.getSession().getServletContext().getRealPath("upload"); //물리적 주소
		int size=10*1024*1024; //업로드 파일 크기 10MB까지 제한
		
		//clear 후 upload폴더가 부재하다면 생성핳 것.
		File dir=new File(uploadPath);
		if(!dir.isDirectory()) {
			dir.mkdirs(); //폴더 생성
		}
		
		try {
			MultipartRequest multi=new MultipartRequest(request, uploadPath, size, "utf-8", 
									new DefaultFileRenamePolicy());
		 
		mb.setId(multi.getParameter("id"));
		mb.setPw(multi.getParameter("pw"));
		mb.setPhone(multi.getParameter("phone"));
		mb.setKind("N");
		mb.setNickname(multi.getParameter("nickname"));
		mb.setProfilephoto(multi.getFilesystemName("profilephoto"));
		mb.setOneline(multi.getParameter("oneline"));
		boolean result = ksDao.member(mb);		
		if (result) {			
			boolean result2 = ksDao.updatemember(mb);
			ksDao.close();
			if(result2) {
			fw.setPath("toreservetime");
			fw.setRedirect(false);
			}
		} else {
			request.setAttribute("msg", "내정보수정에 실패하였습니다.");
			fw.setPath("updatemember.jsp");
			fw.setRedirect(false);
		}
		
		} catch (IOException e) {
			System.out.println("updatemember 예외");
			e.printStackTrace();
		}
		
		return fw;
	}

	public Forward updatestore() {
		ksDao = new KisDao(); 
		Member mb = new Member(); 
		mb.setId(request.getParameter("id"));
		mb.setPw(request.getParameter("pw"));
		mb.setPhone(request.getParameter("phone"));
		mb.setKind("S");
		boolean result = ksDao.member(mb);
		ksDao.close();
		Forward fw = new Forward();
		if (result) {			
			fw.setPath("main.jsp");
			fw.setRedirect(false);
		} else {
			request.setAttribute("msg", "업체 정보 수정에 실패하였습니다.");
			fw.setPath("updatestore.jsp");
			fw.setRedirect(false);
		}
		return fw;
	}

	public Forward updatestoredetail() {
		String uploadPath=request.getSession().getServletContext().getRealPath("upload");
		System.out.println("path="+uploadPath);
		
		
		File dir=new File(uploadPath);
		if(!dir.isDirectory())
		{
			dir.mkdirs();
		}
		int size=10*1024*1024;
		
		Forward fw=new Forward();
		StoreInfo sInfo=new StoreInfo();
		try 
		{
			MultipartRequest multi=
					new MultipartRequest(request,uploadPath,size,"UTF-8",
										new DefaultFileRenamePolicy());	
			session=request.getSession();
			String id=session.getAttribute("id").toString();
			String st_photo=multi.getFilesystemName("st_photo");
			String st_name=multi.getParameter("st_name");
			String st_cate=multi.getParameter("st_cate");
			String st_info=multi.getParameter("st_info");
			String st_menu=multi.getParameter("st_menu");
			String st_phone=multi.getParameter("st_phone");
			String st_event=multi.getParameter("st_event");
			String st_comment=multi.getParameter("st_comment");
			String st_saleinfo=multi.getParameter("st_saleinfo");
			String st_intro=multi.getParameter("st_intro");
			String st_sido=multi.getParameter("sido");
			String st_gu=multi.getParameter("sigungu");
			String st_dong=multi.getParameter("bname");
			String st_address=multi.getParameter("st_address");
			
			System.out.println(st_sido);
			System.out.println(st_gu);
			System.out.println(st_dong);
			System.out.println(st_address);
			
			sInfo.setSt_photo(st_photo);
			sInfo.setSt_name(st_name);
			sInfo.setSt_cate(st_cate);
			sInfo.setSt_info(st_info);
			sInfo.setSt_menu(st_menu);
			sInfo.setSt_phone(st_phone);
			sInfo.setSt_event(st_event);
			sInfo.setSt_comment(st_comment);
			sInfo.setSt_saleinfo(st_saleinfo);
			sInfo.setSt_intro(st_intro);
			sInfo.setSt_sido(st_sido);
			sInfo.setSt_gu(st_gu);
			sInfo.setSt_dong(st_dong);
			sInfo.setSt_address(st_address);
			
			ksDao=new KisDao();
			String stnum=ksDao.selstorenum(id);
			
			if(stnum!=null)
			{
				ksDao.updatestoredetail(sInfo,stnum);
				ksDao.close();
			}
			else
			{
				System.out.println("실패ㅠ");
			}
			
			fw=new Forward();
			fw.setPath("showchoicelist");	
			fw.setRedirect(true);
		} 
		catch (IOException e) 
		{
			System.out.println("KisService_상세페이지 수정 예외");
			e.printStackTrace();
		}	
		return fw;
	
	}

	public Forward cancelstorechoice() {
		ksDao = new KisDao(); 
		session=request.getSession();
		String id=session.getAttribute("id").toString();
		boolean result = ksDao.cancelstorechoice(id);
		ksDao.close();
		Forward fw = new Forward();
		if (result) {			
			fw.setPath("toreservetime");
			fw.setRedirect(false);
		} 
		return fw;
		
	}

			
		

}
