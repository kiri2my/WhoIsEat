package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil {

	public static Connection getConnection() {
		Connection con=null;		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");			 
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "MN", "1111");
			System.out.println("Connection Success!");
		} catch (SQLException e) {
			System.out.println("DB연결 실패");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void close(ResultSet rs) {
		if(rs!=null ) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(PreparedStatement pstmt) {
		if(pstmt!=null ) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public static void close(Connection con) {
		if(con!=null) {
			try {
				con.close();				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	

}
