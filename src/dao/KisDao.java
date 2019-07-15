package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Member;
import bean.StoreInfo;

public class KisDao {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	public KisDao() {
		con = JdbcUtil.getConnection();
	}

	public void close() {
		JdbcUtil.close(rs);
		JdbcUtil.close(pstmt);
		JdbcUtil.close(con);
	}

	public String doublecheck(String id) {
		String sql = "SELECT * FROM MEMBER WHERE ID=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return "이미 가입된 사용자입니다";
			}
		} catch (SQLException e) {
			System.out.println("ksDao_doublecheck 오류");
			e.printStackTrace();
		}
		return "사용 가능합니다";

	}

	public boolean join(Member mb) {
		String sql = "INSERT INTO MEMBER VALUES(?,?,?,?)";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, mb.getId());
			pstmt.setNString(2, mb.getPw());
			pstmt.setNString(3, mb.getPhone());
			pstmt.setNString(4, mb.getKind());
			result = pstmt.executeUpdate();
			if (result != 0) {
				return true; // insert 성공
			}
		} catch (SQLException e) {
			System.out.println("KisDao_join 오류");
			e.printStackTrace();
		}
		return false;
	}

	public boolean memberJoin(Member mb) {
		String sql = "INSERT INTO NORMALMEMBER VALUES(?,?,?,?)";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, mb.getId());
			pstmt.setNString(2, mb.getNickname());
			pstmt.setNString(3, mb.getProfilephoto());
			pstmt.setNString(4, mb.getOneline());
			result = pstmt.executeUpdate();
			if (result != 0) {
				return true; // insert 성공
			}
		} catch (SQLException e) {
			System.out.println("KisDao_memberJoin 오류");
			e.printStackTrace();
		}
		return false;
	}

	public boolean storeJoin(Member mb) {
		String sql = "INSERT INTO STOREMEMBER VALUES(?,?)";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, mb.getId());
			pstmt.setNString(2, mb.getStorenum());
			result = pstmt.executeUpdate();
			if (result != 0) {
				return true; // insert 성공
			}
		} catch (SQLException e) {
			System.out.println("KisDao_storeJoin 오류");
			e.printStackTrace();
		}
		return false;
	}

	public List<StoreInfo> showstorelist(String si_do, String gu, String dong) {
		List<StoreInfo> strList = null;
		String sql = "SELECT * FROM STOREINFO WHERE ST_SIDO=? AND ST_GU=? AND ST_DONG=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, si_do);
			pstmt.setNString(2, gu);
			pstmt.setNString(3, dong);
			rs = pstmt.executeQuery();
			StoreInfo st = null;
			strList = new ArrayList<StoreInfo>();
			while (rs.next()) {
				st = new StoreInfo();
				st.setSt_storenum(rs.getNString("ST_STORENUM"));
				st.setSt_photo(rs.getNString("ST_PHOTO"));
				st.setSt_name(rs.getNString("ST_NAME"));
				st.setSt_cate(rs.getNString("ST_CATE"));
				st.setSt_intro(rs.getNString("ST_INTRO"));
				st.setSt_address(rs.getNString("ST_ADDRESS"));
				strList.add(st);
			}
			return strList;
		} catch (SQLException e) {
			System.out.println("KisDao_showstorelist 실패");
			e.printStackTrace();
		}
		return null;
	}

	public StoreInfo showstoredetail(String storenum) {
		StoreInfo st=null;
		String sql = "SELECT * FROM STOREINFO WHERE ST_STORENUM=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, storenum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				st = new StoreInfo();
				st.setSt_photo(rs.getNString("ST_PHOTO"));
				st.setSt_name(rs.getNString("ST_NAME"));
				st.setSt_cate(rs.getNString("ST_CATE"));
				st.setSt_intro(rs.getNString("ST_INTRO"));
				st.setSt_menu(rs.getNString("ST_MENU"));
				st.setSt_phone(rs.getNString("ST_PHONE"));
				st.setSt_event(rs.getNString("ST_EVENT"));
				st.setSt_comment(rs.getNString("ST_COMMENT"));
				st.setSt_saleinfo(rs.getNString("ST_SALEINFO"));
				st.setSt_address(rs.getNString("ST_ADDRESS"));
				st.setSt_storenum(storenum);	
				return st;
			}	

		} catch (SQLException e) {
			System.out.println("KisDao_showstoredetail 오류");
			e.printStackTrace();
		}
		return null;
	}

	public String showstorechoicenum(String storenum) {
		String sql = "SELECT MAX(ROWNUM) FROM STOREINFO S JOIN STORECHOICELIST C\r\n" + 
				"ON S.ST_STORENUM=C.C_STORENUM\r\n" + 
				"WHERE C.C_STORENUM=? AND C.C_KIND='0'";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, storenum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getNString("MAX(ROWNUM)");
			}	

		} catch (SQLException e) {
			System.out.println("KisDao_showstorechoicenum 오류");
			e.printStackTrace();
		}
		return null;
		
		
		
	}

	public List<StoreInfo> serchshowstorelist(String selmode, String sertext) {
		System.out.println(selmode+sertext);
		List<StoreInfo> strList = null;
		String sql=null;
		if(selmode.equals("ST_NAME")) {
			sql = "SELECT * FROM STOREINFO WHERE ST_NAME LIKE '%'||?||'%' ";
		}else {
			sql = "SELECT * FROM STOREINFO WHERE ST_CATE LIKE '%'||?||'%' ";
		}
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, sertext);
			rs = pstmt.executeQuery();
			StoreInfo st = null;
			strList = new ArrayList<StoreInfo>();
			while (rs.next()) {
				st = new StoreInfo();
				st.setSt_storenum(rs.getNString("ST_STORENUM"));
				st.setSt_photo(rs.getNString("ST_PHOTO"));
				st.setSt_name(rs.getNString("ST_NAME"));
				st.setSt_cate(rs.getNString("ST_CATE"));
				st.setSt_intro(rs.getNString("ST_INTRO"));
				st.setSt_address(rs.getNString("ST_ADDRESS"));
				strList.add(st);
			}
			return strList;
		} catch (SQLException e) {
			System.out.println("KisDao_serchshowstorelist 실패");
			e.printStackTrace();
		}
		return null;
	}

	public boolean updatemember(Member mb) {
		String sql = "UPDATE NORMALMEMBER SET NICKNAME=?,PROFILEPHOTO=?,ONELINE=? WHERE N_ID=?";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, mb.getNickname());
			pstmt.setNString(2, mb.getProfilephoto());
			pstmt.setNString(3, mb.getOneline());
			pstmt.setNString(4, mb.getId());
			result = pstmt.executeUpdate();
			if (result != 0) {
				return true; // insert 성공
			}
		} catch (SQLException e) {
			System.out.println("KisDao_updatemember 오류");
			e.printStackTrace();
		}
		return false;
	}

	public boolean member(Member mb) {
		String sql = "UPDATE MEMBER SET PW=?,PHONE=? WHERE ID=?";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, mb.getPw());
			pstmt.setNString(2, mb.getPhone());
			pstmt.setNString(3, mb.getId());
			result = pstmt.executeUpdate();
			if (result != 0) {
				return true; // insert 성공
			}
		} catch (SQLException e) {
			System.out.println("KisDao_member 오류");
			e.printStackTrace();
		}
		return false;
	}

	public boolean updatestoredetail(StoreInfo st,String stnum) {
		String sql = "UPDATE STOREINFO SET ST_PHOTO=?,ST_NAME=?,ST_CATE=?,ST_INFO=?,ST_MENU=?,ST_PHONE=?,ST_EVENT=?,ST_COMMENT=?,ST_SALEINFO=?\r\n" + 
				",ST_INTRO=?,ST_SIDO=?,ST_GU=?,ST_DONG=?,ST_ADDRESS=? WHERE ST_STORENUM=?";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, st.getSt_photo());
			pstmt.setNString(2, st.getSt_name());
			pstmt.setNString(3, st.getSt_cate());
			pstmt.setNString(4, st.getSt_info());
			pstmt.setNString(5, st.getSt_menu());
			pstmt.setNString(6, st.getSt_phone());
			pstmt.setNString(7, st.getSt_event());
			pstmt.setNString(8, st.getSt_comment());
			pstmt.setNString(9, st.getSt_saleinfo());
			pstmt.setNString(10, st.getSt_intro());
			pstmt.setNString(11, st.getSt_sido());
			pstmt.setNString(12, st.getSt_gu());
			pstmt.setNString(13, st.getSt_dong());
			pstmt.setNString(14, st.getSt_address());
			pstmt.setNString(15, stnum);
			result = pstmt.executeUpdate();
			if (result != 0) {
				return true; // insert 성공
			}
		} catch (SQLException e) {
			System.out.println("KisDao_updatestoredetail 오류");
			e.printStackTrace();
		}
		return false;
	}

	public String selstorenum(String id) {
		String sql = "SELECT * FROM STOREMEMBER S JOIN STOREINFO I\r\n" + 
				"ON S.STORENUM=I.ST_STORENUM\r\n" + 
				"WHERE S_ID=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, id);			
			rs = pstmt.executeQuery();
			StoreInfo st=null;
			if (rs.next()) {
				return rs.getNString("ST_STORENUM");
			}
		} catch (SQLException e) {
			System.out.println("ksDao_selstorenum 오류");
			e.printStackTrace();
		}
		return null;
		
	}

	public boolean cancelstorechoice(String id) {
		String sql = "DELETE FROM STORECHOICELIST WHERE C_ID=? AND C_KIND=0";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, id);
			result = pstmt.executeUpdate();
			if (result != 0) {
				return true; // insert 성공
			}
		} catch (SQLException e) {
			System.out.println("KisDao_cancelstorechoice 오류");
			e.printStackTrace();
		}
		return false;
	}

}
