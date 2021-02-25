package controller;

import java.util.Map;

import service.AdminService;
import service.CustomerService;
import service.RestService;
import service.RiderService;
import service.RmngService;
import service.ShoppingService;
import util.ScanUtil;
import util.View;

public class Controller {

	public static Map<String, Object> loginUser;
	
	private AdminService adminService = AdminService.getInstance();
	private CustomerService customerService = CustomerService.getInstance();
	private ShoppingService shoppingService = ShoppingService.getInstance();
	private RestService restService = RestService.getInstance();
	private RiderService riderService = RiderService.getInstance();
	private RmngService rmngService = RmngService.getInstance();

	public static void main(String[] args) {
		
		new Controller().start();
		
	}
		
	
	private void start() {
		int view = View.HOME;
		
		while(true) {
			switch (view) {
			case View.HOME: view = home(); break;
			case View.LOGIN: view = login(); break;
			case View.JOIN: view = join(); break;
			
			//관리자
			case View.ADMIN_LOGIN: view = adminService.login(); break;
			case View.ADMIN_MAIN: view = adminService.adminMain(); break;
			
			//일반회원
			case View.CUSTOM_LOGIN: view = customerService.login(); break;
			case View.CUSTOM_MAIN: view = customerService.afterLogin(); break;
			case View.CHARGE_CASH: view = customerService.chargeMoney(); break;
			case View.MY_REVIEW: view = customerService.writeReview(); break;
			case View.CART : view = customerService.editCart(); break;
			case View.CUSTOM_MYPAGE: view = customerService.showInfo(); break;
			case View.MATCH_LOCATION: view = shoppingService.viewRstList(); break;
			case View.VIEW_REST: view = shoppingService.restInfo(); break;
			
			//식당
			case View.REST_LOGIN: view = restService.restLogin(); break;
			case View.REST_MAIN : view = restService.restMain(); break;
			case View.REST_MYPAGE : view = restService.restMypage(); break;
			case View.EDIT_REST : view = restService.editMypage(); break;
			case View.REST_MENU : view = restService.showMenu(); break;
			case View.ADD_MENU : view = restService.insertMenu(); break;
			case View.DELETE_MENU : view = restService.deleteMenu(); break;
			case View.ORDER_LIST : view = restService.manageOrder(); break;
			case View.ORDER_DETAIL : view = restService.detailOrder(); break;
			case View.REST_REVIEW : view = restService.reviewList(); break;
			
			//라이더
			case View.RIDER_JOIN: view = riderService.riderJoin(); break;
	        case View.RIDER_LOGIN: view = riderService.riderLogin(); break;
	        case View.RIDER_MAIN: view = riderService.riderMain(); break;
	        case View.RIDER_MYPAGE: view = riderService.riderInfo(); break;
	        case View.EDIT_RIDER: view = riderService.riderUpdate(); break;
	        case View.DELIVERY_ORDER: view = riderService.viewOrderList(); break;
	        case View.MATCHING_ORDER: view = riderService.matchinInsert(); break;
	        case View.DELIVERY_CONFIRM: view = riderService.statusUpdateDone(); break;
			
	        //배달대행업체
	        case View.RMNG_LOGIN: view = rmngService.rmngServicelogin(); break;
	        case View.RMNG_JOIN: view = rmngService.rmngServiceJoin(); break;
	        case View.RMNG_MAIN: view = rmngService.RmngMain(); break;
	        case View.RMNG_MYPAGE: view = rmngService.rmngInfo(); break;
	        case View.MY_RIDERS: view = rmngService.riderList(); break;
	        case View.EDIT_RMNG: view = rmngService.rmngUpdate(); break;
	        case View.RIDER_DELETE: view = rmngService.riderDelete(); break;
			
			}
		}
	}
	
	//로그인 유형 선택
	private int login() {
		try {
			System.out.println("───────────────────────────────────────────────");
			System.out.println("로그인 유형을 선택하세요 >");
			System.out.println("1.관리자 2.일반회원 3.식당 4.배달업체 5.라이더스 6.메인");
			System.out.println("───────────────────────────────────────────────");

			int input = ScanUtil.nextInt();
			
			switch(input) {
			case 0: return View.HOME;
			case 1: return View.ADMIN_LOGIN;
			case 2: return View.CUSTOM_LOGIN;
			case 3: return View.REST_LOGIN;
			case 4: return View.RMNG_LOGIN;
			case 5: return View.RIDER_LOGIN;
			}

		} catch (Exception e) {
			System.out.println("올바른 숫자를 입력해주세요 >");
			return View.LOGIN;
		}
		return View.HOME;
	}
	
	//회원가입 유형 선택
	private int join() {
		try {
			System.out.println("───────────────────────────────────────────────");
			System.out.println("회원가입 유형을 선택하세요 >");
			System.out.println("1.일반회원 2.식당사장님 3.배달업체 4.라이더스 0.메인화면");
			System.out.println("───────────────────────────────────────────────");
			int input = ScanUtil.nextInt();
			
			switch(input) {
			case 0: return View.HOME;
			case 1: customerService.join(); break;
			case 2: restService.restJoin(); break;
			case 3: rmngService.rmngServiceJoin(); break;
			case 4: riderService.riderJoin(); break;
			}
		} catch (Exception e) {
			System.out.println("올바른 숫자를 입력해주세요 >");
			return View.JOIN;
		}
		return View.HOME;
	}
	
	private int home() {
		try {
			System.out.println("■■■■■■■■■■■■■■■■　자 바 의 민 족　■■■■■■■■■■■■■■■■");
			System.out.println();
			System.out.println("                     .;;;                ");
			System.out.println("                    !@@@@@               ");
			System.out.println("                    @@@@@@=              ");
			System.out.println("                    ;;;@@@@              ");
			System.out.println("                    @# @@@@              ");
			System.out.println("                    .  @@@!              ");
			System.out.println("                        ;;    ;;;;;;;;;;.");
			System.out.println("                         @@! .@@@@@@@@@@;");
			System.out.println("                        @@@@@,@@@@@@@@@@;");
			System.out.println("                       @@@@@@@@@@@@@@@@@;");
			System.out.println("             ;  ;:   :@@@@@@@@@@@@@@@@@@;");
			System.out.println("             ;  ;:   :@@@@@@@@@@@@@@@@@@;");
			System.out.println("             @@@@@;@@;    @@@@@@@@@@@@@@;");
			System.out.println("             @@@@@@;      @@@@@@@@@@@@@@;");
			System.out.println("             #@@@-:     ;@@@@@@@@@@@@@@@;");
			System.out.println("             ,@@@    :;@@@@@@@@@@@@@@@@@;");
			System.out.println("             *@@$  ,@@@@@@@@@@@@@@@@@@@@~");
			System.out.println("            ,@@@   @@@@@@@@@@@@@@@$      ");
			System.out.println("            *@@#   @@@@@@@@@@@@@@@@      ");
			System.out.println("           ,@@@=   @@@@@@@@@@@@@@@@      ");
			System.out.println("           *@@@    @@@; @@@@@@@@@@*      ");
			System.out.println("         -;@@@@~   @@@@!@@@@@@@@@@@,     ");
			System.out.println("       ,@@@@@@@#-  :@@@@@@@@@@@@@@@~     ");
			System.out.println("      ,@@@@@@@@@@; .@@@@@@@@@@@@@@@@     ");
			System.out.println("      ;@@@@@@@@@@@@@@@@@@@@@@@@@@@@@     ");
			System.out.println("      -@@@@@@@@@@@@@@@@@@@@@@@@@@@@@-    ");
			System.out.println("      ;@@@@@@@@@@@@@@@@@@@@@@@@@@@@@~    ");
			System.out.println("      ;@@@@@@*@@@@@@@@@@@@@@@@@@@@@!     ");
			System.out.println("      ,@@@@@$             #@@@@@*        ");
			System.out.println("       ,@@@$.              =@@@*         ");
			System.out.println("         -.                  -      ");
			System.out.println();
			System.out.println("　　　　　　1.로그인　2.회원가입　0.프로그램 종료　　　　　　");
			System.out.println("");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.print("번호 입력>");
			int input = ScanUtil.nextInt();	
			
			switch (input) {
				case 1: return login(); 
				//로그인 시 회원 유형 선택
				
				case 2: return join(); 
				//회원가입 시 회원 유형 선택
				
				case 0:
					System.out.println("프로그램이 종료되었습니다.");
					System.exit(0);
			}
		} catch (Exception e) {
			System.out.println("올바른 숫자를 입력해주세요 >");
			return View.HOME;
		}

		
		return View.HOME;
	}
}


/*
* <로그인 화면>
* 
* 1. 관리자 화면
* 	  1-1 ~ 1-4 선택 화면
* 1-1. 회원 조회 화면 with 삭제 기능
* 1-2. 식당 조회 화면 with 삭제 기능
* 1-3. 배달 대행 업체 조회 화면 with 삭제 기능
* 1-4. 라이더 조회 화면 with 삭제 기능
* 
* 2. 고객 화면
* 2-1 ~ 2-5 선택 화면
* 2-1. 내 정보 조회화면
*  2-1-1. 내 정보 수정
* 2-2. 식당 목록 조회
* 	2-2-1. 식당 선택 후 메뉴 조회
* 	2-2-2. 장바구니에 추가 및 삭제
*  2-2-3. 사이버 머니 결제 화면
*  2-2-4. 리뷰 작성
* 2-3. 사이버 머니 충전
* 2-4. 나의 주문 내역 조회
* 2-5. 리뷰 조회
* 
* 3. 식당 화면
* 3-1 ~ 3-4 선택 화면
* 3-1. 내 식당 정보 조회 / 수정
* 3-2. 주문 리스트 조회
* 	3-2-1. 주문 접수 및 취소 선택
* 	3-2-2. 소요 시간 전달
* 3-3. 메뉴 추가 및 삭제
* 3-4. 리뷰 조회 및 삭제
*  
* 4. 배달 대행 업체 화면
* 4-1. 라이더 리스트 조회 및 삭제
* 
* 5. 라이더스 화면
* 5-1 ~ 5-2 선택 화면
* 5-1. 내 정보 조회 및 수정
* 5-2. 내 지역 주문 현황 조회
* 	5-2-1. 주문 승인 및 거절
*/

