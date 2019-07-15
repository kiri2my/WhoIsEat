package service;
import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import bean.Forward;
import bean.Member;
import bean.StoreInfo;
import dao.YjhStoreDao;

public class YjhStoreMM 
{
	HttpServletRequest request;
	HttpServletResponse response;
	HttpSession session;
	YjhStoreDao sDao;
	Forward fw=null;
	StoreInfo sInfo=null;
	Member mb=null;
	
	public YjhStoreMM(HttpServletRequest request, HttpServletResponse response)
	{
		this.request=request;
		this.response=response;
	}

	public Forward insertStoreInfo()
	{
		String uploadPath=request.getSession().getServletContext().getRealPath("upload");
		System.out.println("path="+uploadPath);
		
		
		File dir=new File(uploadPath);
		if(!dir.isDirectory())
		{
			dir.mkdirs();
		}
		int size=10*1024*1024;
		
		fw=new Forward();
		sInfo=new StoreInfo();
		try 
		{
			MultipartRequest multi=
					new MultipartRequest(request,uploadPath,size,"UTF-8",
										new DefaultFileRenamePolicy());	
						
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
			String st_id=multi.getParameter("id");
			System.out.println("ST_PHOTO="+multi.getFilesystemName("st_photo"));
			
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
			
			sDao=new YjhStoreDao();
			String stnum=sDao.selstorenum(st_id);
			
			if(sDao.insertproduct(sInfo,stnum))
			{
				System.out.println("식당등록 성공");
			}
			else
			{
				System.out.println("식당등록 실패");
			}
			sDao.close();
			
			fw=new Forward();
			fw.setPath("loginform.jsp");	
			fw.setRedirect(true);
		} 
		catch (IOException e) 
		{
			System.out.println("식당 업로드 예외");
			e.printStackTrace();
		}	
		return fw;
	}

	public Forward selectstoredetail() 
	{
		String st_num=request.getParameter("st_num");
		System.out.println(st_num);
		sDao=new YjhStoreDao();
		sInfo=sDao.selectStoreInfo(st_num);
		
		Forward fw=new Forward();
		if(sInfo!=null)
		{
			Gson gsonP = new Gson();
			String strGsonP=gsonP.toJson(sInfo);
			request.setAttribute("gsonName", strGsonP);
			/*request.setAttribute("mList", makeMemInfoHtml(mList));*/
			fw.setPath("storedetail.jsp");
			fw.setRedirect(false);
		}
		else
		{
			fw.setPath("/");
			fw.setRedirect(true);
		}
		return fw;
	}

	/*public Forward selectstoreinfo()
	{
		
	}*/
}