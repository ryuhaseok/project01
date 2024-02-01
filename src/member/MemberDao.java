package member;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MemberDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver" ;
	private String url = "jdbc:mysql://localhost:3306/member_db";
	private String id = "member";
	private String pw = "member";

	private void getConnection() {
		try {
			Scanner s=new Scanner(System.in);
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	private void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public int memberInsert(MemberVo memberVo) {
		int count = -1; 

		this.getConnection();

		try {

			if (isId(memberVo.getId())) {
	            System.out.println("*************************************************");
	            System.out.println(" 	 이미 존재하는 아이디입니다. 회원 가입 실패");
	            System.out.println("*************************************************");
	            return count; // 중복된 아이디이므로 실패를 반환
	        }
			// 3. SQL문 준비 / 바인딩 / 실행
			// -SQL문 준비
			String query = "insert into members(id, pw, name, hp, birth, hire_date) "
					+ "values(?, ?, ?, ?, ?, now());";

			// -바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberVo.getId());
			pstmt.setString(2, memberVo.getPw());
			pstmt.setString(3, memberVo.getName());
			pstmt.setString(4, memberVo.getHp());
			pstmt.setString(5, memberVo.getBirth());


			// -실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("-------------------------------------------------");
			System.out.println("		  회원 가입 성공 	");
			System.out.println("-------------------------------------------------");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		this.close();
		return count;
	}

	private boolean isId(String id) {
	    try {
	        String query = "select count(*) from members where id = ?";
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, id);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            int rowCount = rs.getInt(1);
	            return rowCount > 0; // 결과가 1보다 크면 이미 존재하는 아이디
	        }
	    } catch (SQLException e) {
	        System.out.println("error:" + e);
	    }
	    return false;
	}
	
	public boolean login(MemberVo memberVo) {

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// -SQL문 준비
			String query = "select name "
					+ "from members "
					+ "where id=? and pw=?";

			// -바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberVo.getId());
			pstmt.setString(2, memberVo.getPw());

			// -실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			if(rs.next()==true) {
				String name=rs.getString("name");
				System.out.println("-------------------------------------------------");
				System.out.println("		"+ name+" 님 로그인 성공 	");
				System.out.println("-------------------------------------------------");
			}else {
				System.out.println("-------------------------------------------------");
				System.out.println("		로그인 실패 	");
				System.out.println("-------------------------------------------------");
				return false;
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		this.close();
		return true;
	}

	public void myInfo(String loginId) {
		int count = -1; 

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// -SQL문 준비
			String query = "select m.name, m.id, m.pw, m.hp, m.birth, d.department_name, m.position\r\n"
					+ "from members m\r\n"
					+ "left join departments d on m.department_id = d.department_id\r\n"
					+ "where m.id = ?";

			// -바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, loginId);

			// -실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while(rs.next()) {
				String name=rs.getString("m.name");
				String id=rs.getString("m.id");
				String pw=rs.getString("m.pw");
				String hp=rs.getString("m.hp");
				String birth=rs.getString("m.birth");
				String departmentName=rs.getString("d.department_name");
				String position=rs.getString("m.position");

				System.out.println("이름: "+name);
				System.out.println("아이디: "+id);
				System.out.println("비밀번호: "+pw);
				System.out.println("전화번호: "+hp);
				System.out.println("생년월일: "+birth);
				System.out.println("부서: "+departmentName);
				System.out.println("직급: "+position);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		this.close();
	}

	public void updateInfo(String id, Scanner sc) {

		this.getConnection();

		//Scanner sc = new Scanner(System.in);
		int menuNo;
		String str = "";

		System.out.println(" 1.비밀번호 2.이름 3.전화번호 4.생년월일");
		System.out.print(" 변경할 항목 선택 > ");
		menuNo = sc.nextInt();
		sc.nextLine();
		switch (menuNo) {

		//		case 1: // 아이디
		//			try {
		//				System.out.print(" 변경할 아이디 입력>");
		//				str = sc.nextLine();
		//				String query = "";
		//				query += " update members ";
		//				query += " set id = ? ";
		//				query += " where id = ? ";
		//
		//				pstmt = conn.prepareStatement(query);
		//				pstmt.setString(1, str);
		//				pstmt.setString(2, id);
		//				pstmt.executeUpdate();
		//
		//			} catch (SQLException e) {
		//				System.out.println("error:" + e);
		//			}
		//			
		//			id = str;
		//			break;

		case 1: // 비밀번호
			try {
				System.out.print(" 변경할 비밀번호 입력>");
				str = sc.nextLine();
				String query = "";
				query += " update members ";
				query += " set pw = ? ";
				query += " where id = ? ";

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, str);
				pstmt.setString(2, id);
				pstmt.executeUpdate();

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			break;

		case 2: // 이름
			try {
				System.out.print(" 변경할 이름 입력>");
				str = sc.nextLine();
				String query = "";
				query += " update members ";
				query += " set name = ? ";
				query += " where id = ? ";

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, str);
				pstmt.setString(2, id);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			break;

		case 3: // 전화번호
			try {
				System.out.print(" 변경할 전화번호 입력>");
				str = sc.nextLine();
				String query = "";
				query += " update members ";
				query += " set hp = ? ";
				query += " where id = ? ";

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, str);
				pstmt.setString(2, id);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			break;

		case 4: // 생년월일
			try {
				System.out.print(" 변경할 생년월일 입력>");
				str = sc.nextLine();
				String query = "";
				query += " update members ";
				query += " set birth = ? ";
				query += " where id = ? ";

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, str);
				pstmt.setString(2, id);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			break;         
		}// switch case
		System.out.println("-------------------------------------------------");
		System.out.println("		 변경 완료");
		System.out.println("-------------------------------------------------");
		//sc.close();
		this.close();
	}

	public void history(String id) {
		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// -SQL문 준비
			String query = "select h.date, h.state "
					+ "from history h, members m "
					+ "where h.member_id=m.member_id and m.id=?"
					+ " order by h.date desc limit 5";

			// -바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);


			// -실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while(rs.next()) {
				String date = rs.getString("h.date");
				String state = rs.getString("h.state");

				System.out.println(date + " 	" + state);
			} 
		}catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		this.close();
	}

	public void historyCount(String id) {
		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// -SQL문 준비
			 String query = "select state, count(*) count"
		               + "   from history h, members m"
		               + "   where m.id=? and h.member_id=m.member_id"
		               + "   group by state"
		               + "   having state in ('근무', '휴가', '출장', '병가')";

			// -바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);


			// -실행
			rs = pstmt.executeQuery();
			System.out.print("  ");
			// 4.결과처리
			while(rs.next()) {
				String state = rs.getString("state");
				int count = rs.getInt("count");
				System.out.print(state + " : " + count + "회");
				System.out.print(" | ");
			} 
			System.out.println("\n-------------------------------------------------");
			System.out.println(" ");
		}catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		this.close();
	}

	public void insertState(String state, String id) {
		int count = -1;
		this.getConnection();

		try {

			String memberIdQuery = " select member_id "
					 			 + " from members "
					 			 + " where id=?";
			
			PreparedStatement memberIdPstmt = conn.prepareStatement(memberIdQuery);
			
			memberIdPstmt.setString(1, id);
			
			ResultSet memberIdRs = memberIdPstmt.executeQuery();

			// 3. SQL문 준비 / 바인딩 / 실행
			if (memberIdRs.next()) {
				int memberId = memberIdRs.getInt("member_id");
				// -SQL문 준비
				
				String query = "insert into history(member_id, date, state) "
						     + "values(?, curdate(), ?)";

				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, memberId);
				pstmt.setString(2, state);

				count = pstmt.executeUpdate();

				if (count > 0) {
					System.out.println("-------------------------------------------------");
					System.out.println("		상태 등록 완료");
					System.out.println("-------------------------------------------------");
				} else {
					System.out.println("-------------------------------------------------");
					System.out.println("	상태 등록 실패: 이미 존재하는 근태 정보입니다.");
					System.out.println("-------------------------------------------------");
				}
				
			}
			
		} catch (SQLException e) {
			System.out.println("-------------------------------------------------");
			System.out.println("	상태 등록 실패: 이미 존재하는 근태 정보입니다.");
			System.out.println("-------------------------------------------------");
		} 
		this.close();
	}

	public int selectState(String id) {
		int count=-1;
		this.getConnection();

		try {
			if (isMemberId(id)) {
				String memberIdQuery = "SELECT member_id FROM members WHERE id=?";
				PreparedStatement memberIdPstmt = conn.prepareStatement(memberIdQuery);
				memberIdPstmt.setString(1, id);
				ResultSet memberIdRs = memberIdPstmt.executeQuery();

				if (memberIdRs.next()) {
					int memberId = memberIdRs.getInt("member_id");

					String query = "select count(member_id) "
							+ "from history "
							+ "where member_id=? and date=curdate()";

					// -바인딩
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1,  memberId);

					// -실행
					rs = pstmt.executeQuery();

					// 4.결과처리
				} else {
					System.out.println("-------------------------------------------------");
					System.out.println("     존재하지 않는 member_id입니다.");
					System.out.println("-------------------------------------------------");
				}

			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		this.close();

		return count;
	}

	private boolean isMemberId(String id) {
		try {
			String query = "select count(*) from members where id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0;
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return false;
	}
}