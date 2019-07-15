package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.StoreInfo;

public class YjhStoreDao 
{
	Connection con;			
	PreparedStatement pstmt;
	ResultSet rs;
	
	public YjhStoreDao()
	{
		con=JdbcUtil.getConnection();
	}
	
	public void close()
	{
		JdbcUtil.close(rs);		//(rs,pstmt,con) 컴마로 한줄에 다 사용할 수 있음.
		JdbcUtil.close(pstmt);
		JdbcUtil.close(con);
	}
	
	
	public boolean insertproduct(StoreInfo sInfo, String stnum) 
	{
		String sql="INSERT INTO STOREINFO VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	try 
	{
		pstmt=con.prepareStatement(sql);
		pstmt.setNString(1, stnum);
		pstmt.setNString(2, sInfo.getSt_photo());
		pstmt.setNString(3, sInfo.getSt_name());
		pstmt.setNString(4, sInfo.getSt_cate());
		pstmt.setNString(5, sInfo.getSt_info());
		pstmt.setNString(6, sInfo.getSt_menu());
		pstmt.setNString(7, sInfo.getSt_phone());
		pstmt.setNString(8, sInfo.getSt_event());
		pstmt.setNString(9, sInfo.getSt_comment());
		pstmt.setNString(10, sInfo.getSt_saleinfo());
		pstmt.setNString(11, sInfo.getSt_intro());
		pstmt.setNString(12, sInfo.getSt_sido());
		pstmt.setNString(13, sInfo.getSt_gu());
		pstmt.setNString(14, sInfo.getSt_dong());
		pstmt.setNString(15, sInfo.getSt_address());
		
		int result=pstmt.executeUpdate();
		System.out.println("result"+result);
		if(result!=0)
		{
			return true;
		}
	} 
	catch (SQLException e) 
	{
		
		e.printStackTrace();
	}
	return false;
	}

	public StoreInfo selectStoreInfo(String st_num) 
	{
		String sql="SELECT * FROM STOREINFO WHERE ST_STORENUM=?";
		try 
		{
			pstmt=con.prepareStatement(sql);
			pstmt.setNString(1, st_num);
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				StoreInfo si=new StoreInfo();
				si.setSt_photo(rs.getNString("st_photo"));
				si.setSt_name(rs.getNString("st_name"));
				si.setSt_cate(rs.getNString("st_cate"));
				si.setSt_info(rs.getNString("st_info"));
				si.setSt_menu(rs.getNString("st_menu"));
				si.setSt_phone(rs.getNString("st_phone"));
				si.setSt_event(rs.getNString("st_event"));
				si.setSt_comment(rs.getNString("st_comment"));
				si.setSt_saleinfo(rs.getNString("st_saleinfo"));
				si.setSt_intro(rs.getNString("st_intro"));
				si.setSt_sido(rs.getNString("st_sido"));
				si.setSt_gu(rs.getNString("st_gu"));
				si.setSt_dong(rs.getNString("st_dong"));
				
				System.out.println(si);
				return si;
			}
		} 
		
		catch (SQLException e) 
		{
			System.out.println("조회하실 데이터가 없습니다.");
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String selstorenum(String id) {
		String sql = "SELECT STORENUM FROM STOREMEMBER S JOIN MEMBER M\r\n" + 
				"ON S.S_ID=M.ID\r\n" + 
				"WHERE S_ID=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, id);			
			rs = pstmt.executeQuery();
			StoreInfo st=null;
			if (rs.next()) {
				return rs.getNString("STORENUM");
			}
		} catch (SQLException e) {
			System.out.println("yjDao_selstorenum 오류");
			e.printStackTrace();
		}
		return null;
	}
	
}
