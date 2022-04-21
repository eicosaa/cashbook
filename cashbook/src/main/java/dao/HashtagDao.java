package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HashtagDao {
	
	// -hashtag별 순위 목록
	public List<Map<String, Object>> selectTagRankList() {
		List<Map<String, Object>> list = new ArrayList<>();
		
		// -데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// -데이터베이스 드라이버 연결
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234");
			
	       /*
	           SELECT t.tag, t.cnt, RANK() over(ORDER BY t.cnt DESC) ranking
	           FROM 
	           (SELECT tag, COUNT(*) cnt
	           FROM hashtag
	           GROUP BY tag) t
           */

			String sql = "SELECT t.tag, t.cnt, RANK() over(ORDER BY t.cnt DESC) rank"
		               + "            FROM"
		               + "            (SELECT tag, COUNT(*) cnt"
		               + "            FROM hashtag"
		               + "            GROUP BY tag) t";

			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("tag", rs.getString("tag"));
				map.put("cnt", rs.getInt("t.cnt"));
				map.put("rank", rs.getInt("rank"));
				list.add(map);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// -데이터베이스 자원 반환
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// -수입 / 지출별 검색 목록
	public List<Map<String, Object>> selectSearchIncomeExpendList(String kind) {
		System.out.println("[HashtagDao.selectSearchIncomeExpendList] kind : " + kind);
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		// -데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// -데이터베이스 드라이버 연결
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234");

			String sql = "SELECT kind, t.tag, t.cnt, RANK() over(ORDER BY t.cnt DESC) rank"
					+ " FROM"
					+ " (SELECT c.kind kind, tag, COUNT(*) cnt"
					+ " FROM hashtag t INNER JOIN cashbook c"
					+ " ON t.cashbook_no = c.cashbook_no"
					+ " WHERE c.kind = ?"
					+ " GROUP BY t.tag) t";

			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, kind);
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("kind", rs.getString("kind"));
				map.put("tag", rs.getString("t.tag"));
				map.put("cnt", rs.getInt("t.cnt"));
				map.put("rank", rs.getInt("rank"));
				list.add(map);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// -데이터베이스 자원 반환
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// -해당 태그의 목록
	public List<Map<String, Object>> selectThisTagList(String tag) {
		System.out.println("[HashtagDao.selectThisTagList] tag : " + tag);
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		// -데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// -데이터베이스 드라이버 연결
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234");

			String sql = "SELECT t.tag, c.cashbook_no, c.cash_date, c.kind, c.memo, c.update_date, c.create_date"
					+ " FROM hashtag t INNER JOIN cashbook c"
					+ " ON t.cashbook_no = c.cashbook_no"
					+ " WHERE t.tag = ?";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, tag);
				
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("tag", rs.getString("t.tag"));
				map.put("cashbookNo", rs.getInt("c.cashbook_no"));
				map.put("cashDate", rs.getString("c.cash_date"));
				map.put("kind", rs.getString("c.kind"));
				map.put("memo", rs.getString("c.memo"));
				map.put("updateDate", rs.getString("c.update_date"));
				map.put("createDate", rs.getString("c.create_date"));
				list.add(map);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// -데이터베이스 자원 반환
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// -날짜별 검색 목록
	public List<Map<String, Object>> selectSearchDateList(String beginCashDate, String lastCashDate) {
		System.out.println("[HashtagDao.selectSearchDateList] beginCashDate : " + beginCashDate);
		System.out.println("[HashtagDao.selectSearchDateList] lastCashDate : " + lastCashDate);
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		// -데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// -데이터베이스 드라이버 연결
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234");

			String sql = "SELECT c.cashbook_no, c.cash_date, c.kind, c.memo, c.update_date, c.create_date, t.tag"
					+ " FROM hashtag t INNER JOIN cashbook c"
					+ " ON t.cashbook_no = c.cashbook_no"
					+ " WHERE c.cash_date BETWEEN ? AND ?";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, beginCashDate);
			stmt.setString(2, lastCashDate);
				
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("cashbookNo", rs.getInt("c.cashbook_no"));
				map.put("cashDate", rs.getString("c.cash_date"));
				map.put("kind", rs.getString("c.kind"));
				map.put("memo", rs.getString("c.memo"));
				map.put("updateDate", rs.getString("c.update_date"));
				map.put("createDate", rs.getString("c.create_date"));
				map.put("tag", rs.getString("t.tag"));
				list.add(map);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// -데이터베이스 자원 반환
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
