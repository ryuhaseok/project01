package manager;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DepartmentDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/member_db";
	private String id = "member";
	private String pw = "member";

	private void getConnection() {
		try {
			Scanner s = new Scanner(System.in);
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
		// 5. 자원정리
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
	

	// 부서리스트
	public List<DepartmentVo> departmentMemberInfo() {
		List<DepartmentVo> departmentList = new ArrayList<DepartmentVo>();
		this.getConnection();
		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// -SQL문 준비
			String query = "select d.department_id, ";
				  query += " d.department_name, ";
				  query += " d.manager_id,";
				  query += " m.name, ";
				  query += " ifnull(dc.cnt, 0) cnt ";
				  query += " from departments d ";
				  query += " left join members m ";
				  query += " on d.manager_id = m.member_id";
				  query += " left join (select department_id, count(*) cnt\r\n"
				  		+ "		   from members\r\n"
				  		+ "		   group by department_id) dc\r\n"
				  		+ "	on d.department_id = dc.department_id\r\n";

			// -바인딩
			pstmt = conn.prepareStatement(query);

			// -실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int departmentId = rs.getInt("d.department_id");
				String departmentName = rs.getString("d.department_name");
				String managerName = rs.getString("m.name");
				int count = rs.getInt("cnt");

				DepartmentVo departmentVo = new DepartmentVo(departmentId, departmentName, managerName,count);

				departmentList.add(departmentVo);

			}

			System.out.println(departmentList.size());
			for (DepartmentVo vo : departmentList) {
				
				System.out.print(vo.getDepartmentId());
				System.out.print("		" + vo.getDepartmentName());
				System.out.print("		" + vo.getName());
				System.out.print("		" + vo.getCountPosition());
				System.out.println();
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return departmentList;
	}

//	// 상태별 리스트
//	public void memberStatus() {
//		this.getConnection();
//
//		try {
//			// SQL query
//			String query = "select state,count(*)\r\n"
//					+ "from history\r\n"
//					+ "group by state\r\n"
//					+ "having state in ('근무', '휴가', '출장', '병가');";
//
//			pstmt = conn.prepareStatement(query);
//			rs = pstmt.executeQuery();
//
//			// Process the results
//			while (rs.next()) {
//				int countCount=rs.getInt("count(*)");
//				String status = rs.getString("state");
//				System.out.println(status);
//			}
//
//		} catch (SQLException e) {
//			System.out.println("Error: " + e);
//		} finally {
//			this.close();
//		}
//	}

	// 부서명 리스트
	public void departmentNameInfo() {

		int count = -1;

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// -SQL문 준비
			String query = "select department_id, department_name from departments ";

			// -바인딩
			pstmt = conn.prepareStatement(query);

			// -실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			System.out.println("******************부서명리스트**********************");
			while (rs.next()) {
				int departmentrId = rs.getInt("department_id");
				String departmentName = rs.getString("department_name");

				System.out.print(departmentrId + "." + departmentName + " ");
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
//
	}

	// 부서추가
	public int departmentInsert(DepartmentVo departmentVo1) {

		int count = -1;

		this.getConnection();
		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// -SQL문 준비
			String query = "insert into departments values (null, ?,null)";

			// -바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, departmentVo1.getDepartmentName());

			// -실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("-------------------------------------------------");
			System.out.println("              부서가 추가되었습니다.                  ");
			System.out.println("-------------------------------------------------");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return count;

	}// departmentInsert()

	// 부서장 리스트

	public void managerInfo() {

		int count = -1;

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// -SQL문 준비
			String query = "select member_id, name from members where position='부서장'";

			// -바인딩
			pstmt = conn.prepareStatement(query);

			// -실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			System.out.println("******************부서장리스트**********************");
			while (rs.next()) {
				int memberId = rs.getInt("member_id");
				String managerName = rs.getString("name");

				System.out.println("부서장 " + memberId + "." + managerName);
				System.out.println();
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
	}

	// 부서장 수정
	public int newManagerUpdate(int managerUpdate, int no01) {
		int count = -1;

		this.getConnection();

		try {

			String query = "update members set position=? where manager_id=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no01);
			pstmt.setInt(2, managerUpdate);

			pstmt.executeUpdate();

			System.out.println("-------------------------------------------------");
			System.out.println("              부서장이 수정되었습니다..              ");
			System.out.println("-------------------------------------------------");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return count;

	}

	// 부서명 수정
	public int departmentUpdate(String newDepartmentName, int no02) {
		int count = -1;

		this.getConnection();
		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// -SQL문 준비
			String query = "update departments set department_name=? where department_id=?";

			// -바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newDepartmentName);
			pstmt.setInt(2, no02);

			// -실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("-------------------------------------------------");
			System.out.println("              부서명이 수정되었습니다..              ");
			System.out.println("-------------------------------------------------");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return count;

	}

	// 선택한 부서에 속한 멤버리스트
	public void departmentMemberInfo(int no01) {
		int count = -1;

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// -SQL문 준비
			String query = "select member_id, name, position from members where department_id=?";

			// -바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no01);
			// -실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			System.out.println("*********선택한 부서에 대한 리스트***********");
			while (rs.next()) {
				int memberId = rs.getInt("member_id");
				String managerName = rs.getString("name");
				String managerPosition = rs.getString("position");

				System.out.println(memberId + "." + managerName + " 직급 :" + managerPosition);
				System.out.println();
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
	}

	public void departmentDelete(String num) {
		this.getConnection();
		try {
			String query = "";
			query += " delete from departments ";
			query += " where department_id=? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, num);

			pstmt.executeUpdate();

			System.out.println("-------------------------------------------------");
			System.out.println("              부서명이 삭제되었습니다..              ");
			System.out.println("-------------------------------------------------");
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
	}// departmentDelete()


}