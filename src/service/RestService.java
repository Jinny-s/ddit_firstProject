package service;

import controller.Controller;
import dao.RestDao;
import util.ScanUtil;
import util.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestService {

	private static RestService instance;
	public static RestService getInstance(){
		if(instance == null) {
			instance = new RestService();
		}
		return instance;
	}

	private RestDao restDao = RestDao.getInstance();
	static String restId, restPw; //변수 저장
	
	//식당회원가입
	public int restJoin() {
		System.out.println("────────────── 식당 회원가입 ──────────────");
		System.out.println("아이디를 입력해주세요 >");
		String restId = ScanUtil.nextLine();
		
		System.out.println("비밀번호를 입력해주세요 >");
		String restPw = ScanUtil.nextLine();
		
		System.out.println("식당명을 입력해주세요 >");
		String restNm = ScanUtil.nextLine();
		
		System.out.println("전화번호를 입력해주세요 >");
		String restTel = ScanUtil.nextLine();
		
		System.out.println("지역구를 선택해 주세요");
		System.out.println("1.동구 2.중구 3.서구 4.유성구 5.대덕구 >");
		int restGu = ScanUtil.nextInt();
		String restAdd1 = null;
		switch (restGu) {
			case 1: restAdd1 = "동구"; break;
			case 2: restAdd1 = "중구"; break;
			case 3: restAdd1 = "서구"; break;
			case 4: restAdd1 = "유성구"; break;
			case 5: restAdd1 = "대덕구"; break;
		}
		
		System.out.println("상세주소를 입력해주세요>");
		String restAdd2 = ScanUtil.nextLine();
		
		System.out.println("식당유형을 선택해주세요");
		System.out.println("1.한식 2.분식 3.카페/디저트 4.돈까스/회/일식 5.치킨");
		System.out.println("6.피자 7.아시안/양식 8.중식 9.족발/보쌈 10.찜/탕 11.패스트푸트");
		int fdtyGu = ScanUtil.nextInt();
		
		Map<String, Object> param = new HashMap<>();
		param.put("RSTRNT_ID", restId);
		param.put("RSTRNT_PW", restPw);
		param.put("RSTRNT_NM", restNm);
		param.put("RSTRNT_TELNO", restTel);
		param.put("RSTRNT_ADRES1", restAdd1);
		param.put("RSTRNT_ADRES2", restAdd2);
		param.put("FDTY_GU", fdtyGu);
		
		int result = restDao.insertRest(param);
		
		if(0 < result) {
			System.out.println("사장님, 회원가입이 성공하였습니다!");
		} else {
			System.out.println("사장님, 회원가입이 실패하였습니다ㅠㅠ");
		}
		
		return View.HOME;
	}
	
	//식당로그인
	public int restLogin() {
		System.out.println("────────────── 식당 로그인 ──────────────");
		System.out.println("아이디>");
		restId = ScanUtil.nextLine();
		System.out.println("비밀번호>");
		restPw = ScanUtil.nextLine();
		
		Map<String, Object> rest = restDao.selectRest(restId, restPw);
		
		if(rest == null) {
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
		} else {
			System.out.println("로그인 성공");
			System.out.println("사장님, 오늘도 좋은하루 되세요!");
			Controller.loginUser = rest;
			return View.REST_MAIN;
		}
		return View.HOME;
	}
	
	//식당메인화면
	public int restMain() {
		System.out.println("서비스를 선택해주세요 >");
		System.out.println("1.나의 식당 정보 조회");
		System.out.println("2.주문내역 조회");
		System.out.println("3.나의 식당 메뉴 조회");
		System.out.println("─────────────────────────────────────────");
		int input = ScanUtil.nextInt();
	
		
			while(true) {
				switch (input) {
				case 1: return View.REST_MYPAGE;
				case 2: return View.ORDER_LIST;
				case 3: return View.REST_MENU;
				}
			}
		
//		return View.REST_MAIN;
	}
		
	//식당정보조회
	public int restMypage(){
		Map<String, Object> selectRest = restDao.selectRest((String) Controller.loginUser.get("RSTRNT_ID"), (String) Controller.loginUser.get("RSTRNT_PW"));
		System.out.println("─────────────────────────────────────────");
		System.out.println("아이디 : " + selectRest.get("RSTRNT_ID"));
		System.out.println("비밀번호 : " + selectRest.get("RSTRNT_PW"));
		System.out.println("식당명 : " + selectRest.get("RSTRNT_NM"));
		System.out.println("전화번호 : " + selectRest.get("RSTRNT_TELNO"));
		System.out.println("지역구 : " + selectRest.get("RSTRNT_ADRES1"));
		System.out.println("상세주소 : " + selectRest.get("RSTRNT_ADRES2"));
		System.out.println("식당유형 : " + selectRest.get("FDTY_NM"));
		System.out.println("─────────────────────────────────────────");
		System.out.println("1.정보수정\t2.메인으로 >");
		
		int input = ScanUtil.nextInt();
		
		switch (input) {
		case 1: return View.EDIT_MYPAGE;
		case 2: return View.REST_MAIN;
		}
		
		return View.REST_MAIN;
	}
	
	//식당정보수정
	public int editMypage() {
		System.out.println("────────────── 식당 정보수정 ──────────────");
		System.out.println("수정할 비밀번호를 입력해주세요 >");
		String restPw = ScanUtil.nextLine();
		
		System.out.println("수정할 식당명을 입력해주세요 >");
		String restNm = ScanUtil.nextLine();
		
		System.out.println("수정할 전화번호를 입력해주세요 >");
		String restTel = ScanUtil.nextLine();
		
		System.out.println("수정할 지역구를 선택해 주세요");
		System.out.println("1.동구 2.중구 3.서구 4.유성구 5.대덕구 >");
		int restGu = ScanUtil.nextInt();
		String restAdd1 = null;
		switch (restGu) {
			case 1: restAdd1 = "동구"; break;
			case 2: restAdd1 = "중구"; break;
			case 3: restAdd1 = "서구"; break;
			case 4: restAdd1 = "유성구"; break;
			case 5: restAdd1 = "대덕구"; break;
		}
		
		System.out.println("수정할 상세주소를 입력해주세요>");
		String restAdd2 = ScanUtil.nextLine();
		
		System.out.println("수정할 식당유형을 선택해주세요");
		System.out.println("1.한식 2.분식 3.카페/디저트 4.돈까스/회/일식 5.치킨");
		System.out.println("6.피자 7.아시안/양식 8.중식 9.족발/보쌈 10.찜/탕 11.패스트푸트");
		int fdtyGu = ScanUtil.nextInt();
		
		Map<String, Object> param = new HashMap<>();
		param.put("RSTRNT_PW", restPw);
		param.put("RSTRNT_NM", restNm);
		param.put("RSTRNT_TELNO", restTel);
		param.put("RSTRNT_ADRES1", restAdd1);
		param.put("RSTRNT_ADRES2", restAdd2);
		param.put("FDTY_GU", fdtyGu);
		
		int result = restDao.updateRest((String) Controller.loginUser.get("RSTRNT_ID"), param);
		
		if(0 < result) {
			System.out.println("식당 정보 수정이 완료되었습니다.");
		} else {
			System.out.println("식당 정보 수정이 실패하였습니다.");
		}
		return View.REST_MAIN;
	}
			
	//메뉴 조회
	public int showMenu(){
		List<Map<String, Object>> selectMenus = restDao.selectMenu((String) Controller.loginUser.get("RSTRNT_ID"));
		System.out.println("──────────────── 메 뉴 판 ────────────────");
		
		for (Map<String, Object> selectMenu : selectMenus) {
			System.out.print("메뉴코드 : " + selectMenu.get("MENU_ID") + " / ");
			System.out.print(selectMenu.get("MENU_NM") + " / ");
			System.out.println(selectMenu.get("MENU_PRICE") + "원");
			
		}
		
		System.out.println("1.메뉴 추가\t2.메뉴 삭제\t3.메인으로");
		int input = ScanUtil.nextInt();
		
		switch(input) {
		case 1: return View.ADD_MENU;
		case 2: return View.DELETE_MENU;
		case 3: return View.REST_MAIN;
		}
		
		return View.REST_MENU;
	}
	
	//메뉴추가
	public int insertMenu() {
		System.out.println("메뉴명을 입력해주세요 >");		
		String menuName = ScanUtil.nextLine();
		System.out.println("가격을 입력해주세요 >");
		int price = ScanUtil.nextInt();
		
		Map<String, Object> p = new HashMap<>();
		p.put("MENU_NM", menuName);
		p.put("MENU_PRICE", price);
		
		int result = restDao.insertMenu(p);
		
		if(0 < result) {
			System.out.println("메뉴가 추가되었습니다.");
		} else {
			System.out.println("메뉴 추가가 실패하였습니다.");
		}
			
		return View.REST_MENU;
	}
	
	//메뉴삭제
	public int deleteMenu() {
		System.out.println("삭제할 메뉴의 코드를 입력해주세요 >");
		int menuId = ScanUtil.nextInt();
		
		Map<String, Object> p = new HashMap<>();
		p.put("MENU_ID", menuId);
		p.put("RSTRNT_ID", (String)Controller.loginUser.get("RSTRNT_ID"));
		
		int result = restDao.deleteMenu(menuId, (String)Controller.loginUser.get("RSTRNT_ID"));
		
		if(0 < result){
			System.out.println("삭제 성공!");
		}else{
			System.out.println("삭제 실패ㅠㅠ");
		}
		
		return View.REST_MENU;
		
	}
	
	//주문접수 (주문상태 업데이트)
//	public int up	dateStatus() {
//		System.out.println("────────────── 주문상태변경 ──────────────");
//		
//	}
	
	//리뷰 조회 ----- 체크
	public void showReview(){
		// 리뷰 조회
	}
	
	//식당 주문 리스트 조회 ----- 체크
	public int manageOrder(){
		List<Map<String, Object>> selectOrders = restDao.selectOrder((String) Controller.loginUser.get("RSTRNT_ID"));
		
		System.out.println("──────────────── 주문내역 ────────────────");
		for (Map<String, Object> selectOrder : selectOrders) {
			System.out.println("주문 번호 : " + selectOrder.get("ORDER_ID"));
			System.out.println("주문 날짜 : " + selectOrder.get("ORDER_DATE"));
			System.out.println("주문 가격 : " + selectOrder.get("ORDER_COST"));
			System.out.println("주문 상태 : " + selectOrder.get("ORDER_STATUS"));
			System.out.println("─────────────────────♥───────────────────");
		}
		System.out.println("1.주문조회\t2.메인으로");
		int input = ScanUtil.nextInt();
		
		switch(input) {
		case 1: return View.ORDER_DETAIL;
		case 2: return View.REST_MAIN;
		}
	
		// 주문 접수/취소 선택
//		System.out.println("1은 주문 접수 2는 주문 취소");
//		int orderNum = ScanUtil.nextInt();
//		if (orderNum == 1) {
//			restDao.updateStatus("Y", orderId);
//		} else {
//			restDao.updateStatus("N", orderId);
//		}

		// 고객에게 소요시간 전달
		return View.REST_MAIN;
	}
	
	
	public int detailOrder(){
		// 주문 상세 조회
		Map<String, Object> detailOrder = restDao.detailOrder("ORDER_ID", (String) Controller.loginUser.get("RSTRNT_ID"));
		System.out.println("조회할 주문 번호를 입력해주세요 >");
		String orderId = ScanUtil.nextLine();
		
		
		System.out.println("주문번호 : " + detailOrder.get("ORDER_ID"));
		System.out.println("메뉴명 : " + detailOrder.get("MENU_NM"));
		
		System.out.println();
		
		return View.ORDER_LIST;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//테스트
		public static void main(String[] args) {
			RestService restService = RestService.getInstance();
			restService.restMypage();
		}
}
