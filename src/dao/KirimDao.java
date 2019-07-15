package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.Member;
import bean.Msg;
import bean.Reservation;

public class KirimDao {
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public KirimDao() {
		con = JdbcUtil.getConnection();
	}
	
	public void close() {
		JdbcUtil.close(rs);
		JdbcUtil.close(pstmt);
		JdbcUtil.close(con);
	}
	
	public Member login(Member mb) {
		String sql = "SELECT ID, PW, KIND FROM MEMBER WHERE ID=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, mb.getId());		
			rs = pstmt.executeQuery();
			Member mb2 = new Member();
			while(rs.next()) {
				if(mb.getPw().equals(rs.getNString("PW"))) {
					mb2.setId(rs.getNString("ID"));
					mb2.setKind(rs.getNString("KIND"));
					return mb2;
					
				}
			}
		} catch (SQLException e) {
			System.out.println("login DAO error");
			e.printStackTrace();
		}
		return null;
		
		
		
	}

	public String getkind(String sId) {
		String sql = "SELECT KIND FROM MEMBER WHERE ID=?";
		String result=null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, sId);		
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result = rs.getNString("kind");
			}
			return result;	
		} catch (SQLException e) {
			System.out.println("getkind DAO error");
			e.printStackTrace();
		}
		return null;
	}
	

	
	public List<Reservation> storesidebar(String storeNum) {
		String sql = "SELECT * FROM USERACCEPT WHERE AC_ST_NUM=? AND AC_EAT=0 ";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, storeNum);		
			rs = pstmt.executeQuery();
			List<Reservation> resvList = new ArrayList<>();
			while(rs.next()) {
				Reservation resv = new Reservation();
				resv.setAc_reservationtime(rs.getNString("AC_RESERVATIONTIME"));
				resv.setAc_st_num(rs.getNString("AC_ST_NUM"));
				resv.setAc_acceptId(rs.getNString("AC_ACCEPTID"));
				resv.setAc_requestId(rs.getNString("AC_REQUESTID"));
				resv.setAc_eat(rs.getInt("AC_EAT"));
				resvList.add(resv);
			}
			return resvList;
		} catch (SQLException e) {
			System.out.println("storesidebar DAO error");
			e.printStackTrace();
		}
		return null;
	}

	public List<Msg> membersidebar(String sId) {
		//보낸사람 닉네임,kind1에 시간대 추가해야함(STORECHOICELIST 조인 C_ID, C_NICKNAME 필요함 )
		
		String sql = "SELECT * FROM MESSAGE M JOIN STORECHOICELIST S " + 
				"ON M.M_RECEIVEID = S.C_ID " + 
				"WHERE M_RECEIVEID=? AND (M_MSGST=0 or M_MSGST=3 or M_MSGST=4) AND (C_KIND=0 or C_KIND=2 or C_KIND=3)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, sId);		
			rs = pstmt.executeQuery();
			List<Msg> msgList = new ArrayList<>();
			while(rs.next()) {
				Msg msg = new Msg();
				msg.setM_msgnum(rs.getNString("M_MSGNUM"));
				msg.setM_msg(rs.getNString("M_MSG"));
				msg.setM_sendid(rs.getNString("M_SENDID"));
				msg.setM_receiveid(rs.getNString("M_RECEIVEID"));
				msg.setM_msgst(rs.getInt("M_MSGST"));
				msg.setC_reservationtime(rs.getNString("C_RESERVATIONTIME"));
				msg.setC_oneline(rs.getNString("C_ONELINE"));
				msgList.add(msg);
			}
			return msgList;
		} catch (SQLException e) {
			System.out.println("membersidebar DAO error");
			e.printStackTrace();
		}
		return null;
	}
	public List<HashMap<String, String>> showchoicelist(String st_num) {
		String sql = "SELECT * FROM STORECHOICELIST WHERE C_STORENUM=? AND C_KIND=0";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, st_num);		
			rs = pstmt.executeQuery();
			List<HashMap<String,String>> choiceList = new ArrayList<>();
			while(rs.next()) {
				HashMap<String,String> hMap = new HashMap<>();
				hMap.put("c_id",rs.getNString("C_ID"));
				hMap.put("c_storenum",rs.getNString("C_STORENUM"));
				hMap.put("c_reservationtime",rs.getNString("C_RESERVATIONTIME"));
				hMap.put("c_oneline",rs.getNString("C_ONELINE"));
				hMap.put("c_kind",rs.getInt("C_KIND")+"");
				choiceList.add(hMap);
			}
			return choiceList;
		} catch (SQLException e) {
			System.out.println("showchoicelist DAO error");
			e.printStackTrace();
		}
		return null;
	}

	public int insertchoicelist(HashMap<String, String> choicer) {
		String sql = "INSERT INTO STORECHOICELIST VALUES(?,?,?,?,0)";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, choicer.get("sId"));		
			pstmt.setNString(2, choicer.get("st_num"));
			pstmt.setNString(3, choicer.get("reservationtime"));
			pstmt.setNString(4, choicer.get("st_oneline"));
			result = pstmt.executeUpdate();
			if(result!=0) {//성공시
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("insertchoicelist DAO error");
			e.printStackTrace();
		}
		return result;
	}

	public int sendrequest(Msg msg) {
		String sql = "INSERT INTO MESSAGE VALUES(LPAD(SEQ_MSG.NEXTVAL,7,0),?,?,?,0)";
		
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, msg.getM_msg());		
			pstmt.setNString(2, msg.getM_sendid());		
			pstmt.setNString(3, msg.getM_receiveid());		
			result = pstmt.executeUpdate();
			if(result!=0) {//성공시
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("sendrequest DAO error");
			e.printStackTrace();
		}
		return result;
	}
	public int sendReserve(HashMap<String, String> singleAcpt) {
		String sql = "INSERT INTO MESSAGE VALUES(LPAD(SEQ_MSG.NEXTVAL,7,0),?,?,?,2)";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, singleAcpt.get("reserveTime"));		
			pstmt.setNString(2, singleAcpt.get("acceptId"));
			pstmt.setNString(3, singleAcpt.get("st_id"));		
			result = pstmt.executeUpdate();
			if(result!=0) {//성공시
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("sendReserve DAO error");
			e.printStackTrace();
		}
		return result;
	}
	
	public int requestcancel(String sId, String targetId) {
		String sql = "UPDATE MESSAGE SET M_MSGST=5 WHERE M_SENDID=? AND M_RECEIVEID=? AND M_MSGST=0";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, sId);		
			pstmt.setNString(2, targetId);	
			result = pstmt.executeUpdate();
			if(result!=0) {//성공시
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("requestcancel DAO error");
			e.printStackTrace();
		}
		return result;
	}
	
	public int acceptstore(Msg msg) {
		String sql = "INSERT INTO MESSAGE VALUES(LPAD(SEQ_MSG.NEXTVAL,7,0),?,?,?,3)";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, msg.getM_msg());		
			pstmt.setNString(2, msg.getM_sendid());		
			pstmt.setNString(3, msg.getM_receiveid());		
			result = pstmt.executeUpdate();
			if(result!=0) {//성공시
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("acceptstore DAO error");
			e.printStackTrace();
		}
		return result;
	}
	public int rejectstore(Msg msg) {
		String sql = "INSERT INTO MESSAGE VALUES(LPAD(SEQ_MSG.NEXTVAL,7,0),?,?,?,4)";
		
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, msg.getM_msg());		
			pstmt.setNString(2, msg.getM_sendid());		
			pstmt.setNString(3, msg.getM_receiveid());		
			result = pstmt.executeUpdate();
			if(result!=0) {//성공시
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("rejectstore DAO error");
			e.printStackTrace();
		}
		return result;
	}

	public List<HashMap<String, String>> showchoiceliststore(String id) {
		String sql = "SELECT * FROM STORECHOICELIST C JOIN STOREMEMBER S\r\n" + 
				"ON C.C_STORENUM=S.STORENUM\r\n" + 
				"WHERE S.S_ID=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, id);		
			rs = pstmt.executeQuery();
			List<HashMap<String,String>> choiceList = new ArrayList<>();
			while(rs.next()) {
				HashMap<String,String> hMap = new HashMap<>();
				hMap.put("c_id",rs.getNString("C_ID"));
				hMap.put("c_storenum",rs.getNString("C_STORENUM"));
				hMap.put("c_reservationtime",rs.getNString("C_RESERVATIONTIME"));
				hMap.put("c_oneline",rs.getNString("C_ONELINE"));
				hMap.put("c_kind",rs.getInt("C_KIND")+"");
				choiceList.add(hMap);
			}
			return choiceList;
		} catch (SQLException e) {
			System.out.println("showchoiceliststore DAO error");
			e.printStackTrace();
		}
		return null;
	}

	public String getst_id(String storeNum) {
		String sql = "SELECT S_ID FROM STOREMEMBER WHERE STORENUM=? ";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, storeNum);		
			rs = pstmt.executeQuery();
			String st_id=null;
			if(rs.next()) {
				st_id = rs.getNString("S_ID");
			}
			return st_id;
		} catch (SQLException e) {
			System.out.println("getst_id DAO error");
			e.printStackTrace();
		}
		return null;
		
	}
	public String getStoreNum(String sId) {
		String sql = "SELECT STORENUM FROM STOREMEMBER WHERE S_ID=? ";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, sId);		
			rs = pstmt.executeQuery();
			String storeNum=null;
			if(rs.next()) {
				storeNum = rs.getNString("STORENUM");
			}
			return storeNum;
		} catch (SQLException e) {
			System.out.println("getStoreNum DAO error");
			e.printStackTrace();
		}
		return null;
	}
	

	public int addAccept(HashMap<String, String> singleAcpt) {
		String sql = "INSERT INTO USERACCEPT VALUES(?,?,?,?,0)";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, singleAcpt.get("reserveTime"));		
			pstmt.setNString(2, singleAcpt.get("storeNum"));		
			pstmt.setNString(3, singleAcpt.get("acceptId"));		
			pstmt.setNString(4, singleAcpt.get("requestId"));		
			result = pstmt.executeUpdate();
			if(result!=0) {//성공시
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("addAccept DAO error");
			e.printStackTrace();
		}
		return result;
	}

	public int updateC_kindAccept(String id, String storeNum) {
		String sql = "UPDATE STORECHOICELIST SET C_KIND=1 WHERE C_ID=? AND C_STORENUM=? AND C_KIND=0";
		
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, id);		
			pstmt.setNString(2, storeNum);		
			result = pstmt.executeUpdate();
			if(result!=0) {//성공시
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("updateC_kindAccept DAO error");
			e.printStackTrace();
		}
		return result;
	}
	public int updateC_kindAcceptStore(String receiveId, String storeNum) {
		String sql = "UPDATE STORECHOICELIST SET C_KIND=2 WHERE C_ID=? AND C_STORENUM=? AND C_KIND=1";
		
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, receiveId);		
			pstmt.setNString(2, storeNum);		
			result = pstmt.executeUpdate();
			if(result!=0) {//성공시
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("updateC_kindAcceptStore DAO error");
			e.printStackTrace();
		}
		return result;
	}
	public int updateC_kindRejectStore(String receiveId, String storeNum) {
		String sql = "UPDATE STORECHOICELIST SET C_KIND=3 WHERE C_ID=? AND C_STORENUM=? AND C_KIND=1";
		
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, receiveId);		
			pstmt.setNString(2, storeNum);		
			result = pstmt.executeUpdate();
			if(result!=0) {//성공시
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("updateC_kindRejectStore DAO error");
			e.printStackTrace();
		}
		return result;
	}

	public int updateM_msgstAccept(String m_msgnum) {
		String sql = "UPDATE MESSAGE SET M_MSGST=1 WHERE M_MSGNUM=? AND M_MSGST=0";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, m_msgnum);		
			result = pstmt.executeUpdate();
			if(result!=0) {//성공시
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("updateM_msgstAccept DAO error");
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateM_msgstReject(String r_m_msgnum) {
		String sql = "UPDATE MESSAGE SET M_MSGST=8 WHERE M_MSGNUM=? AND M_MSGST=0";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, r_m_msgnum);		
			result = pstmt.executeUpdate();
			if(result!=0) {//성공시
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("updateM_msgstReject DAO error");
			e.printStackTrace();
		}
		return result;
	}
	
	public int confirmaccept(String saMsgNum) {
		String sql = "UPDATE MESSAGE SET M_MSGST=6 WHERE M_MSGNUM=? AND M_MSGST=3";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, saMsgNum);		
			result = pstmt.executeUpdate();
			if(result!=0) {//성공시
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("confirmaccept DAO error");
			e.printStackTrace();
		}
		return result;
	}
	public int confirmreject(String srMsgNum) {
		String sql = "UPDATE MESSAGE SET M_MSGST=7 WHERE M_MSGNUM=? AND M_MSGST=4";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, srMsgNum);		
			result = pstmt.executeUpdate();
			if(result!=0) {//성공시
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("confirmreject DAO error");
			e.printStackTrace();
		}
		return result;
	}


	public int updateAc_eatAcceptStore(String storeNum, String reserveTime, String acceptId, String requestId) {
		String sql = "UPDATE USERACCEPT SET AC_EAT=1 WHERE AC_RESERVATIONTIME=? AND AC_ST_NUM=? AND AC_ACCEPTID=? AND AC_REQUESTID=?";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, reserveTime);		
			pstmt.setNString(2, storeNum);		
			pstmt.setNString(3, acceptId);		
			pstmt.setNString(4, requestId);		
			result = pstmt.executeUpdate();
			if(result!=0) {//성공시
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("updateAc_eatAcceptStore DAO error");
			e.printStackTrace();
		}
		return result;
	}
	public int updateAc_eatRejectStore(String storeNum, String reserveTime, String acceptId, String requestId) {
		String sql = "UPDATE USERACCEPT SET AC_EAT=2 WHERE AC_RESERVATIONTIME=? AND AC_ST_NUM=? AND AC_ACCEPTID=? AND AC_REQUESTID=?";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, reserveTime);		
			pstmt.setNString(2, storeNum);		
			pstmt.setNString(3, acceptId);		
			pstmt.setNString(4, requestId);		
			result = pstmt.executeUpdate();
			if(result!=0) {//성공시
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("updateAc_eatRejectStore DAO error");
			e.printStackTrace();
		}
		return result;
	}

	public String doublemsg(Msg msg) {
		String sql = "SELECT * FROM MESSAGE WHERE M_SENDID=? AND M_RECEIVEID=? AND M_MSGST='0'";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, msg.getM_sendid());		
			pstmt.setNString(2, msg.getM_receiveid());		
			rs = pstmt.executeQuery();
			if(rs.next()) 
			return "중복메세지가 있음";
		} catch (SQLException e) {
			System.out.println("getst_id DAO error");
			e.printStackTrace();
		}
		return null;
	}

	public String selectreid(String saMsgNum) {
		String sql = "SELECT M_RECEIVEID FROM MESSAGE M JOIN STORECHOICELIST S \r\n" + 
				"ON M.M_RECEIVEID = S.C_ID\r\n" + 
				"WHERE M_MSGNUM=? AND (C_KIND=2 OR C_KIND=3)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, saMsgNum);		
			rs = pstmt.executeQuery();
			if(rs.next()) 
			return rs.getNString("M_RECEIVEID");
		} catch (SQLException e) {
			System.out.println("selectreid DAO error");
			e.printStackTrace();
		}
		return null;
	}

	public int updatechoicekind(String reid) {
		String sql = "UPDATE STORECHOICELIST SET C_KIND=4 WHERE C_ID=?";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, reid);		
			result = pstmt.executeUpdate();
			if(result!=0) {//성공시
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("updateAc_eatRejectStore DAO error");
			e.printStackTrace();
		}
		return result;		
		
	}

	public Member getprofile(String proId) {
		String sql = "SELECT * FROM MEMBER JOIN NORMALMEMBER " + 
				"ON MEMBER.ID=NORMALMEMBER.N_ID WHERE ID=? ";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, proId);
			rs = pstmt.executeQuery();
			Member mb = null;
			if(rs.next()) {
				mb = new Member();
				mb.setId(rs.getString("ID"));
				mb.setPhone(rs.getNString("PHONE"));
				mb.setKind(rs.getString("KIND"));
				mb.setNickname(rs.getNString("NICKNAME"));
				mb.setProfilephoto(rs.getNString("PROFILEPHOTO"));
				mb.setOneline(rs.getNString("ONELINE"));
			}
			return mb;
		} catch (SQLException e) {
			System.out.println("getprofile DAO error");
			e.printStackTrace();
		}
		return null;
	}

	

	

	

	

	
	

}
