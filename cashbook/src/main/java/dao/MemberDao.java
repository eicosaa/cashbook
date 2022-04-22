package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vo.Member;

public class MemberDao {
	// 회원 가입
	// 회원 수정
	// 회원 탈퇴
	// 회원 정보
	
	// 로그인
	public String selectMemberByIdPw(Member member) {
		String memberId = null;
		
		// -데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT member_id memberId FROM member WHERE member_id = ? AND member_pw = PASSWORD(?)";
		
		try {
			// -데이터베이스 드라이버 연결
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234");
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberId());
			stmt.setString(2, member.getMemberPw());
			rs = stmt.executeQuery();
			if(rs.next()) {
				memberId = rs.getString("memberId");
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
		
		return memberId;
	}
}
