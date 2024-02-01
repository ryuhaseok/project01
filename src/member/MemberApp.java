package member;


import java.util.Scanner;

public class MemberApp {

	public static void main(String[] args) {
		Scanner s=new Scanner(System.in);

		MemberDao memberDao=new MemberDao();


		String ch;
		String id, pw, name, hp, birth;

		outerLoop:
		while(true) {
			System.out.println("		   <시작화면>");
			System.out.println("-------------------------------------------------");
			System.out.println("  1. 회원가입\r\n"
					+ "  2. 로그인\r\n"
					+ "  /q 입력 시 프로그램 종료");
			System.out.println("-------------------------------------------------");
			System.out.print(" > ");
			ch=s.next();

			if(ch.equals("/q")) {
				System.out.println("******** 프로그램 종료 ********");
				break outerLoop;
			}

			switch(ch){
			case "1":
				System.out.println("	<회원가입>");
				System.out.print(" 아이디 > ");
				id=s.next();
				System.out.print(" 비밀번호 > ");
				pw=s.next();
				System.out.print(" 이름 > ");
				name=s.next();
				System.out.print(" 전화번호 > ");
				hp=s.next();
				System.out.print(" 생년월일 > ");
				birth=s.next();
				MemberVo memberVo=new MemberVo(id, pw, name, hp, birth, null);
				memberDao.memberInsert(memberVo);
				break;
			case "2":
				System.out.println("		   <로그인>");
				System.out.print(" 아이디: ");
				id=s.next();
				System.out.print(" 비밀번호: ");
				pw=s.next();
				MemberVo memberVo01 = new MemberVo(id, pw);
				boolean loginSuccess = memberDao.login(memberVo01);	    
				if (loginSuccess) {
					while(true) {
						System.out.println("        	     <메뉴화면>");
						System.out.println("-------------------------------------------------");
						System.out.println(" 1. 내 정보 보기\r\n"
								+ " 2. 내 정보 수정\r\n"
								+ " 3. 근태 히스토리 확인\r\n"
								+ " 4. 현재 상태 등록\r\n"
								+ " /q 입력 시 프로그램 종료");
						System.out.println("-------------------------------------------------");
						System.out.print(" 원하는 메뉴 번호 입력 > ");
						ch=s.next();

						if(ch.equals("/q")) {
							System.out.println(" 프로그램 종료");
							break outerLoop;
						}
						
						MemberVo memberVo02 = new MemberVo();
						memberVo02.setId(memberVo01.getId());
						switch(ch) {
						case "1":
							memberDao.myInfo(memberVo02.getId());
							break;
						case "2":
							System.out.println("	           <내 정보 수정> ");
							System.out.println("-------------------------------------------------");
							memberDao.updateInfo(memberVo02.getId(), s);							
							break;
						case "3":
							System.out.println("	       <자신의 근태 히스토리>");
							System.out.println("-------------------------------------------------");
							memberDao.history(memberVo02.getId());
							System.out.println("-------------------------------------------------");
							memberDao.historyCount(memberVo02.getId());
							System.out.println("");
							break;
						case "4":
							System.out.println("	             <상태 등록>");
							System.out.println("-------------------------------------------------");
							System.out.println("  	[휴가 / 병가 / 근무 / 출장]");
							System.out.println("-------------------------------------------------");
							System.out.print(" 입력> ");
							String state=s.next();

							int count = memberDao.selectState(memberVo02.getId());
							if(count==1) {
							}else {
								memberDao.insertState(state, memberVo02.getId());	
							}
							break;
						}

					}
				}//로그인 끝
				break;
			}
		}
		s.close();
	}

}