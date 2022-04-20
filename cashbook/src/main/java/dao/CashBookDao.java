package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import vo.CashBook;

public class CashBookDao {
	
	// -입력
	public void insertCashBook(CashBook cashbook, List<String> hashtag) {
		// -데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// -데이터베이스 드라이버 연결
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234");
			conn.setAutoCommit(false); // 자동 커밋을 해제
			
			String sql = "INSERT INTO cashbook(cash_date, kind, cash, memo, update_date, create_date) "
					   + "VALUES (?, ?, ?, ?, NOW(), NOW())";
			
			// insert + select 방금 생성된 행의 키 값 ex) select 방금 입력한 cashbook_no from cashbook; (-입력하자마자 select)
			stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cashbook.getCashDate());
			stmt.setString(2, cashbook.getKind());
			stmt.setInt(3, cashbook.getCash());
			stmt.setString(4, cashbook.getMemo());
			stmt.executeUpdate(); // insert
			rs = stmt.getGeneratedKeys(); // select 방금 입력한 cashbook_no from cashbook;
			int cashbookNo = 0;
			if(rs.next()) {
				cashbookNo = rs.getInt(1);
			}
			
			// hashtag를 저장하는 코드 (-여러 개의 hashtag가 있을 수 있기에 for 사용)
			PreparedStatement stmt2 = null;
			for(String h : hashtag) {
				String sql2 = "INSERT INTO hashtag(cashbook_no, tag, create_date) VALUES(?, ?, NOW())";
				
				stmt2 = conn.prepareStatement(sql2);
				stmt2.setInt(1, cashbookNo);
				stmt2.setString(2, h);
				stmt2.executeUpdate();	
			}
			
			conn.commit();			
		} catch (Exception e) {
			try { 
				conn.rollback(); // -예외가 발생하면 rollback 시킨다.
			} catch (SQLException e1) {
				e1.printStackTrace();
			} 
			e.printStackTrace();
		} finally {
			try {
				// -데이터베이스 자원 반환
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// -삭제
	public void deleteCashBook(int cashbookNo) {
		// -데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int row = 0;
		
		try {
			// -데이터베이스 드라이버 연결
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234");
			conn.setAutoCommit(false); // 자동 커밋 해제
			
			// -(cashbook 테이블과 fk키 관계 때문에 hashtag 테이블의 데이터가 먼저 삭제 되어야 cashbook의 데이터가 삭제된다.)
			// -hashtag 테이블 데이터를 삭제하는 코드
			String sql = "DELETE FROM hashtag WHERE cashbook_no = ?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cashbookNo);
			stmt.executeUpdate();	
			
			// -cashbook 테이블 데이터를 삭제하는 코드
			PreparedStatement stmt2 = null;
			String sql2 = "DELETE FROM cashbook WHERE cashbook_no = ?";

			stmt2 = conn.prepareStatement(sql2);
			stmt2.setInt(1, cashbookNo);
			row = stmt2.executeUpdate();
			
			conn.commit(); // -커밋
		} catch (Exception e) {
			try { 
				conn.rollback(); // -예외가 발생하면 rollback
			} catch (SQLException e1) {
				e1.printStackTrace();
			} 
			e.printStackTrace();
		} finally {
			try {
				// -데이터베이스 자원 반환
				conn.close();
				
				// -디버깅 코드
				if(row == 1) {
					System.out.println("[deleteCashBook] hashtag 삭제 성공");
				} else {
					System.out.println("[deleteCashBook] hashtag 삭제 실패");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// -상세보기
	public CashBook selectCashBookOne(int cashbookNo) {
		CashBook cashbook = new CashBook();
		
		// -데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT"
				+ "				cashbook_no cashbookNo"
				+ "				,cash_date cashDate"
				+ "				,kind"
				+ "				,cash"
				+ "				,memo"
				+ "				,update_date updateDate"
				+ "				,create_date createDate"
				+ "			FROM cashbook"
				+ "			WHERE cashbook_no = ?";
		
		try {
			// -데이터베이스 드라이버 연결
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234");
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cashbookNo);
			rs = stmt.executeQuery();
			while(rs.next()) {
				cashbook.setCashbookNo(rs.getInt("cashbookNo"));
				cashbook.setCashDate(rs.getString("cashDate"));
				cashbook.setKind(rs.getString("kind"));
				cashbook.setCash(rs.getInt("cash"));
				cashbook.setMemo(rs.getString("memo"));
				cashbook.setUpdateDate(rs.getString("updateDate"));
				cashbook.setCreatDate(rs.getString("createDate"));
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// -데이터베이스 자원 반환
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cashbook;
	}
	
	// -월별 가계부 리스트 
	public List<Map<String, Object>> selectCashBookListByMonth(int y, int m) { // -y -> year, m -> month
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		/*
			SELECT 
				cashbook_no cashbookNo
				,DAY(cash_date) day
				,kind
				,cash
				,LEFT(memo, 5) memo
			FROM cashbook
			WHERE YEAR(cash_date) = ? AND MONTH(cash_date) = ?
			ORDER BY DAY(cash_date) ASC, kind ASC;
			
			-kind ASC로 지출보다 수입이 먼저 나오도록
		*/
		
		// -데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT"
				+ "				cashbook_no cashbookNo"
				+ "				,DAY(cash_date) day"
				+ "				,kind"
				+ "				,cash"
				+ "				,LEFT(memo, 5) memo"
				+ "			FROM cashbook"
				+ "			WHERE YEAR(cash_date) = ? AND MONTH(cash_date) = ?"
				+ "			ORDER BY DAY(cash_date) ASC, kind ASC";
		
		try {
			// -데이터베이스 드라이버 연결
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234");
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, y);
			stmt.setInt(2, m);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cashbookNo", rs.getInt("cashbookNo"));
				map.put("day", rs.getInt("day"));
				map.put("kind", rs.getString("kind"));
				map.put("cash", rs.getInt("cash"));
				map.put("memo", rs.getString("memo"));
				list.add(map);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// -데이터베이스 자원 반환
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
