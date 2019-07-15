package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import bean.Forward;
import bean.Member;
import bean.Msg;
import bean.Reservation;
import bean.StoreInfo;
import dao.KirimDao;

public class KirimService {
	HttpServletRequest request;
	HttpServletResponse response;
	HttpSession session;
	KirimDao kDao;
	
	public KirimService(HttpServletRequest request, HttpServletResponse response) {
		this.request=request;
		this.response=response;
		
	}
	public Forward login() {
		String memberId = request.getParameter("memberid");
		String storeId = request.getParameter("storeid");
		String memberPw = request.getParameter("pw1");
		String storePw = request.getParameter("pw2");
		System.out.println(memberId+storeId+memberPw+storePw);
		Member mb = new Member();
		
		if(memberId!="" && storeId=="") {
			mb.setId(memberId);
			mb.setKind("N");
			mb.setPw(memberPw);
		}else if(storeId!="" && memberId=="") {
			mb.setId(storeId);
			mb.setKind("S");
			mb.setPw(storePw);
		}		
		kDao = new KirimDao();
		Member mb2=kDao.login(mb);
		kDao.close();
		Forward fw = new Forward();
		
		if(mb2.getId()!=null) {
			request.getSession().setAttribute("id",mb2.getId());
			request.getSession().setAttribute("kind",mb2.getKind());
					
			String mb3 = new Gson().toJson(mb2);
			request.setAttribute("mb", mb3);
			fw.setPath("main.jsp");
			fw.setRedirect(false);
			
			if(mb2.getKind().contains("N")) {
				request.setAttribute("url", "reservetime");
			}else if(mb2.getKind().contains("S")) {
				request.setAttribute("url", "showchoicelist");
			}
		}else {
			fw.setPath("loginform.jsp");
			fw.setRedirect(true);
		}
		return fw;
	}
	
	public String menukind() {
		String sId = (String) request.getSession().getAttribute("id");
		kDao = new KirimDao();
		String kind = kDao.getkind(sId);
		kDao.close();
		String jsonObj=null;
		if(kind!=null) {
			jsonObj = new Gson().toJson(kind);
		}
		return jsonObj;
	}
		
	public String storesidebar() {
		String sId = (String) request.getSession().getAttribute("id");
		
		kDao = new KirimDao();
		String storeNum = kDao.getStoreNum(sId);
		kDao.close();
				
		kDao = new KirimDao();
		List<Reservation> resvList = kDao.storesidebar(storeNum);
		kDao.close();
		
		//닉네임 가져올까?
		
		if(resvList!=null) {
			String jsonResvList = new Gson().toJson(resvList);
			return jsonResvList;

		}
		return null;
	}	
	public String membersidebar() {
		String sId = (String) request.getSession().getAttribute("id");
		System.out.println("sId="+sId);
		kDao = new KirimDao();
		List<Msg> msgList = kDao.membersidebar(sId);
		kDao.close();
		String jsonMsgList=null;
		if(msgList!=null) {
			jsonMsgList = new Gson().toJson(msgList);
		}
		return jsonMsgList;
	}
	
	public Forward reservetimetoselectlocation() {
		String time = request.getParameter("time");
		String hour = request.getParameter("hour");
		String minute = request.getParameter("minute");
		
		SimpleDateFormat df = new SimpleDateFormat("MM/dd");// a hh시 mm분 ss초
		Calendar c = Calendar.getInstance();
        String today = df.format(c.getTime());
        String reserveTime = today+" "+time+" "+hour+"시 "+minute+"분";
        System.out.println(reserveTime);
        
        request.getSession().setAttribute("time", reserveTime);
        request.setAttribute("url", "selectlocation");
        
        Forward fw = new Forward();
        fw.setPath("main.jsp");
        fw.setRedirect(false);
        return fw;
	}
	public void insertchoicelist() {
		String st_num = request.getParameter("st_num");
		String st_oneline = request.getParameter("st_oneline");
		session = request.getSession();
		String reservationtime = (String) session.getAttribute("time");
		String sId = (String) session.getAttribute("id");
		System.out.println("num="+st_num);
		System.out.println("oneline="+st_oneline);
		System.out.println("sId="+sId);
		System.out.println("reservationtime="+reservationtime);
		
				
		HashMap<String,String> choicer = new HashMap<>();
		choicer.put("sId", sId);
		choicer.put("st_num", st_num);
		choicer.put("reservationtime", reservationtime);
		if(st_oneline=="" || st_oneline==null) {
			choicer.put("st_oneline", "저의 밥친구가 되어주세요.");
		}else {
			choicer.put("st_oneline", st_oneline);
		}
		
		request.getSession().setAttribute("storenum",st_num);//세션에 storenum 저장 : st_num
		kDao = new KirimDao();
		int result = kDao.insertchoicelist(choicer);
		kDao.close();
		
		if(result==1) {
			System.out.println("식당선택자리스트에 한명 추가 완료");
		}else {
			System.out.println("식당선택자리스트에 한명 추가 실패");
		}
	}
	
	public Forward showchoicelist() {
		String skind = request.getSession().getAttribute("kind").toString();
		System.out.println("skind="+skind);
		String id = request.getSession().getAttribute("id").toString();
		System.out.println("id="+id);
		String st_num=null;
		String st_name=null;
		kDao = new KirimDao();
		List<HashMap<String,String>> choiceList=null;
		if(skind.contains("N")) {
			st_num = request.getParameter("st_num");
			st_name = request.getParameter("st_name");
			choiceList = kDao.showchoicelist(st_num);
		}else if(skind.contains("S")){
			choiceList = kDao.showchoiceliststore(id);
		}
		kDao.close();
		Forward fw = null;
		if(choiceList!=null) {
			request.setAttribute("choiceList", new Gson().toJson(choiceList));
			request.setAttribute("url","toshowchoicelist");
			request.setAttribute("st_name",st_name);
			fw = new Forward();
			fw.setPath("main.jsp");
			fw.setRedirect(false);
		}else {
			fw = new Forward();
			fw.setPath("main.jsp");
			fw.setRedirect(true);
		}
		return fw;
	}
		
	public Forward toshowchoicelist() {
		String list = request.getParameter("list");
		String st_name = request.getParameter("dong");
		
		List<HashMap<String,String>> choiceList = 
				new Gson().fromJson(list, new TypeToken<List<HashMap<String,String>>>(){}.getType());
		
		request.setAttribute("choiceList", choiceList);
		request.setAttribute("choiceListHtml", makeHtmlChoiceList(choiceList));
		request.setAttribute("st_name", st_name);
		
		Forward fw = new Forward();
		fw.setPath("storechoicelist.jsp");
		fw.setRedirect(false);
		return fw;
	}
	private Object makeHtmlChoiceList(List<HashMap<String, String>> choiceList) {
		StringBuilder sb = new StringBuilder();
		//"c_listnum"
		//"c_storenum"
		//"c_kind"
		String kind="처리완료";
		sb.append("<table id='htmltable'>");
		for(int i=0;i<choiceList.size();i++) {
			sb.append("<tr class='choicemember'><th class='profileId'>"+choiceList.get(i).get("c_id")+"</th>");
			sb.append("<th>"+choiceList.get(i).get("c_reservationtime")+"</th>");
			sb.append("<th>"+choiceList.get(i).get("c_oneline")+"</th>");			
			sb.append("<th><input type='hidden' class='c_id' value='"+choiceList.get(i).get("c_id")+"'/></th>");
			if(choiceList.get(i).get("c_kind").equals("0")) {
				kind="선택";
			}else if(choiceList.get(i).get("c_kind").equals("1")) {
				kind="수락";
			}else if(choiceList.get(i).get("c_kind").equals("2")) {
				kind="식당에 요청중";
			}else if(choiceList.get(i).get("c_kind").equals("4")) {
				kind="처리완료";
			}
			sb.append("<th><input type='hidden' class='showkind' value='"+kind+"'></th>");			  
			sb.append("<th><input type='button' class='requestbtn' value='요청'></th>");			  
			sb.append("<th><input type='button' class='cancelbtn' value='취소' style='display: none;'></th></tr>");			  
		}
		sb.append("</table>");
		return sb.toString();
	}
	public String sendrequest() {
		String sendid = (String) request.getSession().getAttribute("id");
		String receiveid = request.getParameter("receiveid");
		String reqmsg = request.getParameter("reqmsg");
		
		System.out.println("*******************************");
		System.out.println("sendid="+sendid);
		System.out.println("receiveid="+receiveid);
		System.out.println("reqmsg="+reqmsg);
		System.out.println("*******************************");
		
		if(reqmsg==null || reqmsg=="") {
			reqmsg = "저의 밥친구가 되어주세요";
		}
		Msg msg = new Msg();
		msg.setM_msg(reqmsg);
		msg.setM_sendid(sendid);
		msg.setM_receiveid(receiveid);		
		kDao = new KirimDao();
		String doublemsg=kDao.doublemsg(msg);
		System.out.println("*******************************");
		System.out.println("db1"+doublemsg);
		String jsonObj=null;
		if(doublemsg==null) {
			System.out.println("*******************************");
			System.out.println("db2"+doublemsg);
			int result = kDao.sendrequest(msg);
			kDao.close();
			if(result==1) {
				jsonObj = new Gson().toJson("요청메세지가 전송되었습니다");
			}else {
				jsonObj = new Gson().toJson("요청메세지 전송이 실패하였습니다");
			}
		}else {
			jsonObj = new Gson().toJson("중복 요청하였습니다. 취소 후, 다시 요청해주세요");
		}
		return jsonObj;
	}
	
	
	public String requestcancel() {
		String sId = (String) request.getSession().getAttribute("id");
		String targetId = request.getParameter("targetid");
		System.out.println("targetId="+targetId);
		kDao = new KirimDao();
		int result = kDao.requestcancel(sId,targetId);
		kDao.close();
		System.out.println("result="+result);
		String jsonObj=null;
		if(result==1) {
			jsonObj = new Gson().toJson("요청메세지가 취소되었습니다");
		}else {
			jsonObj = new Gson().toJson("취소할 요청메세지가 존재하지 않습니다");
		}
		return jsonObj;
	}
	public String accept() {
		String requestTime = request.getParameter("requesttime");
		String requestId = request.getParameter("requestid");
		String acceptId = (String) request.getSession().getAttribute("id");
		String storeNum = (String) request.getSession().getAttribute("storenum");
		
		System.out.println("requestTime="+requestTime);
		System.out.println("requestId="+requestId);
		System.out.println("acceptId="+acceptId);
		System.out.println("storeNum="+storeNum);
		
		kDao = new KirimDao();
		String st_id=kDao.getst_id(storeNum);
		kDao.close();
		System.out.println("st_id="+st_id);//n
		HashMap<String,String> singleAcpt=new HashMap<>();
		singleAcpt.put("reserveTime", requestTime); //
		singleAcpt.put("storeNum", storeNum);
		singleAcpt.put("acceptId", acceptId);		//
		singleAcpt.put("requestId", requestId);
		singleAcpt.put("st_id", st_id);				//
		//List<HashMap<String,String>> acptList=null;
		kDao = new KirimDao();
		int result = kDao.sendReserve(singleAcpt);
		kDao.close();
		
		kDao = new KirimDao();
		int result2 = kDao.addAccept(singleAcpt);
		kDao.close();
		String jsonObj=null;
		if(result==1 && result2==1) {//성공시
			jsonObj = new Gson().toJson(singleAcpt);
		}		
		return jsonObj;
	}
	
	public String acceptstore() {
		String sendid = (String) request.getSession().getAttribute("id");
		String receiveid1 = request.getParameter("resvId1");
		String receiveid2 = request.getParameter("resvId2");
		String reserveTime = request.getParameter("resvTime");
		String waitmin = request.getParameter("waitmin"); 
		String waitmin2 = request.getParameter("waitmin2");
		
		System.out.println("waitmin="+waitmin);
		System.out.println("waitmin2="+waitmin2);
		
		if(waitmin2==null || waitmin2=="") {
			waitmin2 = waitmin;
		}
		Msg msg = new Msg();
		msg.setM_msg("예약시간:"+reserveTime+"식당 대기시간:"+waitmin2);
		msg.setM_sendid(sendid);
		msg.setM_receiveid(receiveid1);
		Msg msg2 = new Msg();
		msg2.setM_msg("예약시간:"+reserveTime+"식당 대기시간:"+waitmin2);
		msg2.setM_sendid(sendid);
		msg2.setM_receiveid(receiveid2);
		
		kDao = new KirimDao();
		int result = kDao.acceptstore(msg);
		kDao.close();
		
		kDao = new KirimDao();
		int result2 = kDao.acceptstore(msg2);
		kDao.close();
		String jsonObj=null;
		
		if(result==1 && result2==1) {
			jsonObj = new Gson().toJson("손님 두명에게 식당수락 메세지가 전송되었습니다");
		}else {
			jsonObj = new Gson().toJson("손님 두명에게 식당수락 메세지 전송이 실패하였습니다");
		}
		return jsonObj;
	}
	public String rejectstore() {
		String sendid = (String) request.getSession().getAttribute("id");
		String receiveid1 = request.getParameter("resvId1");
		String receiveid2 = request.getParameter("resvId2");
		String reserveTime = request.getParameter("resvTime");
		String rejReason = request.getParameter("rejectreason"); 
				
		Msg msg = new Msg();
		msg.setM_msg("예약시간:"+reserveTime+"거절사유:"+rejReason);
		msg.setM_sendid(sendid);
		msg.setM_receiveid(receiveid1);
		Msg msg2 = new Msg();
		msg2.setM_msg("예약시간:"+reserveTime+"거절사유:"+rejReason);
		msg2.setM_sendid(sendid);
		msg2.setM_receiveid(receiveid2);
		
		kDao = new KirimDao();
		int result = kDao.rejectstore(msg);
		kDao.close();
		
		kDao = new KirimDao();
		int result2 = kDao.rejectstore(msg2);
		kDao.close();
		String jsonObj=null;
		
		if(result==1 && result2==1) {
			jsonObj = new Gson().toJson("손님 두명에게 식당거절 메세지가 전송되었습니다");
		}else {
			jsonObj = new Gson().toJson("손님 두명에게 식당거절 메세지 전송이 실패하였습니다");
		}
		return jsonObj;
	}
	
	public void updateC_kindAccept() {
		String storeNum = (String) request.getSession().getAttribute("storenum");
		String requestId = request.getParameter("requestid");
		String acceptId = (String) request.getSession().getAttribute("id");
				
		kDao = new KirimDao();
		int result=kDao.updateC_kindAccept(requestId,storeNum);
		kDao.close();
		
		kDao = new KirimDao();
		int result2=kDao.updateC_kindAccept(acceptId,storeNum);
		kDao.close();
		
		if(result==1 && result2==1) {
			System.out.println("updateC_kindAccept 성공");
		}else {
			System.out.println("updateC_kindAccept 실패");
		}
	}
	
	public void updateM_msgstAccept() {
		String m_msgnum =request.getParameter("m_msgnum");
		System.out.println("m_msgnum="+m_msgnum);
		
		kDao = new KirimDao();
		int result=kDao.updateM_msgstAccept(m_msgnum);
		kDao.close();
		
		if(result==1) {
			System.out.println("updateM_msgstAccept 성공");
		}else {
			System.out.println("updateM_msgstAccept 실패");
		}
	}
	public void updateM_msgstReject() {
		String r_m_msgnum =request.getParameter("r_m_msgnum");
		System.out.println("r_m_msgnum="+r_m_msgnum);
		
		kDao = new KirimDao();
		int result=kDao.updateM_msgstReject(r_m_msgnum);
		kDao.close();
		
		if(result==1) {
			System.out.println("updateM_msgstReject 성공");
		}else {
			System.out.println("updateM_msgstReject 실패");
		}
		
	}
	
	
	public void confirmaccept() {
		String saMsgNum =request.getParameter("saMsgNum");
		System.out.println("saMsgNum="+saMsgNum);		
		kDao = new KirimDao();
		String reid=kDao.selectreid(saMsgNum);
		int scc=kDao.updatechoicekind(reid);
		if(scc==1) {			
			int result=kDao.confirmaccept(saMsgNum);
			kDao.close();
			if(result==1) {
				System.out.println("confirmaccept 성공");
			}else {
				System.out.println("confirmaccept 실패");
			}
		}
	}
		
	
	public void confirmreject() {
		String srMsgNum =request.getParameter("srMsgNum");
		System.out.println("srMsgNum="+srMsgNum);		
		kDao = new KirimDao();		
		String reid=kDao.selectreid(srMsgNum);
		int scc=kDao.updatechoicekind(reid);		
		if(scc==1) {			
			int result=kDao.confirmreject(srMsgNum);
			kDao.close();
			if(result==1) {
				System.out.println("confirmaccept 성공");
			}else {
				System.out.println("confirmaccept 실패");
			}
		}
	}
	
	public void updateC_kindAcceptStore() {
		String st_id = (String) request.getSession().getAttribute("id");
		String receiveId1 = request.getParameter("resvId1");
		String receiveId2 = request.getParameter("resvId2");
		
		System.out.println("st_id="+st_id);
		System.out.println("receiveId1="+receiveId1);
		System.out.println("receiveId2="+receiveId2);
		
		kDao = new KirimDao();
		String storeNum=kDao.getStoreNum(st_id);
		kDao.close();
		
		kDao = new KirimDao();
		int result=kDao.updateC_kindAcceptStore(receiveId1,storeNum);
		kDao.close();
		
		kDao = new KirimDao();
		int result2=kDao.updateC_kindAcceptStore(receiveId2,storeNum);
		kDao.close();
		
		if(result==1 && result2==1) {
			System.out.println("updateC_kindAcceptStore 성공");
		}else {
			System.out.println("updateC_kindAcceptStore 실패");
		}
		
	}
	public void updateC_kindRejectStore() {
		String st_id = (String) request.getSession().getAttribute("id");
		String receiveId1 = request.getParameter("resvId1");
		String receiveId2 = request.getParameter("resvId2");
		
		System.out.println("st_id="+st_id);
		System.out.println("receiveId1="+receiveId1);
		System.out.println("receiveId2="+receiveId2);
		
		kDao = new KirimDao();
		String storeNum=kDao.getStoreNum(st_id);
		kDao.close();
		
		kDao = new KirimDao();
		int result=kDao.updateC_kindRejectStore(receiveId1,storeNum);
		kDao.close();
		
		kDao = new KirimDao();
		int result2=kDao.updateC_kindRejectStore(receiveId2,storeNum);
		kDao.close();
		
		if(result==1 && result2==1) {
			System.out.println("updateC_kindRejectStore 성공");
		}else {
			System.out.println("updateC_kindRejectStore 실패");
		}
		
		
	}
	public void updateAc_eatAcceptStore() {
		String st_id = (String) request.getSession().getAttribute("id");
		String acceptId = request.getParameter("resvId1");
		String requestId = request.getParameter("resvId2");
		String reserveTime = request.getParameter("resvTime");
		
		System.out.println("st_id="+st_id);
		System.out.println("acceptId="+acceptId);
		System.out.println("requestId="+requestId);
		System.out.println("reserveTime"+reserveTime);
		
		kDao = new KirimDao();
		String storeNum=kDao.getStoreNum(st_id);
		kDao.close();
		
		kDao = new KirimDao();
		int result=kDao.updateAc_eatAcceptStore(storeNum,reserveTime,acceptId,requestId);
		kDao.close();
		
		if(result==1) {
			System.out.println("updateAc_eatAcceptStore 성공");
		}else {
			System.out.println("updateAc_eatAcceptStore 실패");
		}
		
	}
	
	
	public void updateAc_eatRejectStore() {
		String st_id = (String) request.getSession().getAttribute("id");
		String acceptId = request.getParameter("resvId1");
		String requestId = request.getParameter("resvId2");
		String reserveTime = request.getParameter("resvTime");
		kDao = new KirimDao();
		String storeNum=kDao.getStoreNum(st_id);
		kDao.close();
		
		kDao = new KirimDao();
		int result=kDao.updateAc_eatRejectStore(storeNum,reserveTime,acceptId,requestId);
		kDao.close();
		
		if(result==1) {
			System.out.println("updateAc_eatRejectStore 성공");
		}else {
			System.out.println("updateAc_eatRejectStore 실패");
		}
	}
	public String getprofile() {
		String proId=request.getParameter("profileid");
		kDao=new KirimDao();
		Member mb=kDao.getprofile(proId);
		kDao.close();
		String jsonObj=null;
		if(mb!=null) {
			jsonObj=new Gson().toJson(mb);
		}		
		return jsonObj;
	}
	
	
	
	
	
	
	
	

	
	
	
	
	

}
