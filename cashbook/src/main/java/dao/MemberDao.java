package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vo.Member;

public class MemberDao {
	
	// 회원 가입 (INSERT)
	public void insertMember(Member member) {
		// -데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int row = 0;
		
		try {
			// -데이터베이스 드라이버 연결
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234");
			
			String sql = "INSERT INTO member(member_id, member_pw, member_name, member_phone, member_gender, member_email, member_birth, member_addr, create_date) "
					   + "VALUES (?, PASSWORD(?), ?, ?, ?, ?, ?, ?, NOW())";
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberId());
			stmt.setString(2, member.getMemberPw());
			stmt.setString(3, member.getMemberName());
			stmt.setString(4, member.getMemberPhone());
			stmt.setString(5, member.getMemberGender());
			stmt.setString(6, member.getMemberEmail());
			stmt.setString(7, member.getMemberBirth());
			stmt.setString(8, member.getMemberAddr());
			row = stmt.executeUpdate();	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// -데이터베이스 자원 반환
				conn.close();
				
				// -디버깅 코드
				if(row == 1) {
					System.out.println("[insertMember] member(회원가입) 입력 성공");
				} else {
					System.out.println("[insertMember] member(회원가입) 입력 실패");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 회원 수정 (UPDATE)
	public void updateMember(Member member, String originalMemberPw) {
		// -데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int row = 0;
		
		try {
			// -데이터베이스 드라이버 연결
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234");
	
			String sql = "UPDATE member"
					+ " SET member_name = ?"
					+ "	, member_phone = ?"
					+ "	, member_gender = ?"
					+ "	, member_email = ?"
					+ "	, member_birth = ?"
					+ "	, member_addr = ?"
					+ "	, member_pw = PASSWORD(?)"
					+ " WHERE member_id = ? AND member_pw = PASSWORD(?)";
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberName());
			stmt.setString(2, member.getMemberPhone());
			stmt.setString(3, member.getMemberGender());
			stmt.setString(4, member.getMemberEmail());
			stmt.setString(5, member.getMemberBirth());
			stmt.setString(6, member.getMemberAddr());
			stmt.setString(7, member.getMemberPw());
			stmt.setString(8, member.getMemberId());
			stmt.setString(9, originalMemberPw);
			stmt.executeUpdate();	
			
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
	}
	
	// 회원 탈퇴 (DELETE)
	public void deleteMember(String memberId, String memberPw) {
		// -데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int row = 0;
		
		try {
			// -데이터베이스 드라이버 연결
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234");

			String sql = "DELETE FROM member WHERE member_id = ? AND member_pw = PASSWORD(?)";
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setString(2, memberPw);
			row = stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// -데이터베이스 자원 반환
				conn.close();
				
				// -디버깅 코드
				if(row == 1) {
					System.out.println("[deleteMember] Member 삭제 성공");
				} else {
					System.out.println("[deleteMember] Member 삭제 실패");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 회원 정보 (SELECT)
	public Member selectMemberOne(String sessionMemberId) {
		Member member = new Member();
		
		// -데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT member_id memberId, member_name memberName, member_phone memberPhone, member_gender memberGender, member_email memberEmail, member_birth memberBirth, member_addr memberAddr, create_date createDate FROM member WHERE member_id = ?";
		
		try {
			// -데이터베이스 드라이버 연결
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook", "root", "java1234");
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, sessionMemberId);
			rs = stmt.executeQuery();
			while(rs.next()) {
				member.setMemberId(rs.getString("memberId"));
				member.setMemberName(rs.getString("memberName"));
				member.setMemberPhone(rs.getString("memberPhone"));
				member.setMemberGender(rs.getString("memberGender"));
				member.setMemberEmail(rs.getString("memberEmail"));
				member.setMemberBirth(rs.getString("memberBirth"));
				member.setMemberAddr(rs.getString("memberAddr"));
				member.setCreateDate(rs.getString("createDate"));
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
				
		return member;
	}
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
