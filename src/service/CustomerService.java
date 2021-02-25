package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.View;
import dao.CustomerDao;

public class CustomerService {
	
	private CustomerService(){}
	private static CustomerService instance;
	public static CustomerService getInstance(){
		if(instance == null){
			instance = new CustomerService();
		}
		return instance;
	}
	
	String cid, cpw;
		
	private CustomerDao customerDao = CustomerDao.getInstance();
	
	public int login(){
		System.out.println("───────────────── 회　원  로그인 ─────────────────");
		System.out.println("아이디를 입력해주세요 >");
		String cstmr_id = ScanUtil.nextLine();
		System.out.println("비밀번호를 입력해주세요 >");
		String cstmr_pw = ScanUtil.nextLine();	
	
		Map<String, Object> user = customerDao.selectUser(cstmr_id, cstmr_pw);
		if(user == null){
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
		}else{
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("　　　　　　　　　　　　회원 로그인 성공!");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			cid = cstmr_id;
			cpw = cstmr_pw;
			Controller.loginUser = user;
			return View.CUSTOM_MAIN;
		}
		return View.LOGIN;
		
	}
	
	public int afterLogin(){
		System.out.println("1.마이페이지  2.식당리스트 조회  3.장바구니 조회");
		System.out.println("4.캐시 충전  5.리뷰 작성  6.로그아웃");
		int actionKind = ScanUtil.nextInt();	
		switch (actionKind) {
		case 1:
			return View.CUSTOM_MYPAGE;
		case 2:
			return View.MATCH_LOCATION;
		case 3:
			return View.CART;
		case 4:
			return View.CHARGE_CASH;
		case 5:
			return View.MY_REVIEW;
		case 6: 
			return View.HOME;
		}
		return View.HOME;
	}
	
	public int join(){
		System.out.println("────────────────── 일반 회원가입 ─────────────────");
		System.out.println("아이디를 입력해주세요 >");
		String cstmr_id = ScanUtil.nextLine();
		
		System.out.println("비밀번호를 입력해주세요 >");
		String cstmr_pw = ScanUtil.nextLine();
		
		System.out.print("이름을 입력해주세요 > ");
		String name = ScanUtil.nextLine();	
		
		System.out.println("전화번호를 입력해주세요 >");
		String hp = ScanUtil.nextLine();	
		
		System.out.print("생년월일을 입력해주세요 (ex: 00/00/00) > ");
		String birthdy = ScanUtil.nextLine();	
		
		System.out.println("지역구를 선택해 주세요");
		System.out.println("1.동구 2.중구 3.서구 4.유성구 5.대덕구 >");
		int restGu = ScanUtil.nextInt();
		String add1 = null;
		switch (restGu) {
			case 1: add1 = "동구"; break;
			case 2: add1 = "중구"; break;
			case 3: add1 = "서구"; break;
			case 4: add1 = "유성구"; break;
			case 5: add1 = "대덕구"; break;
		}
		
		System.out.println("상세주소를 입력해주세요>");
		String add2 = ScanUtil.nextLine();	
		
		Map<String, Object> param = new HashMap<>();
		param.put("CSTMR_ID", cstmr_id);
		param.put("CSTMR_PW", cstmr_pw);
		param.put("CSTMR_NM", name);
		param.put("CSTMR_HP", hp);
		param.put("CSTMR_BRTHDY", birthdy);
		param.put("CSTMR_ADRES1", add1);
		param.put("CSTMR_ADRES2", add2);
		
		int result = customerDao.insertUser(param);
		return View.HOME;
	}
	
	public int showInfo(){
		System.out.println("<<<<<<<<<<<<<<<<<<< 마이페이지 >>>>>>>>>>>>>>>>>>>");
		Map<String, Object> myInfo = customerDao.selectMyInfo(cid, cpw);
		
		System.out.println("■■■■■■■■■■■■■■■■■■　내 　정 　보　■■■■■■■■■■■■■■■■■■");
		System.out.println("아이디　　: " + myInfo.get("CSTMR_ID"));
		System.out.println("비밀번호　: " + myInfo.get("CSTMR_PW"));
		System.out.println("이름　　　: " + myInfo.get("CSTMR_NM"));
		System.out.println("전화번호　: " + myInfo.get("CSTMR_HP"));
		System.out.println("생년월일　: " + myInfo.get("CSTMR_BRTDY"));
		System.out.println("지역구　　: " + myInfo.get("CSTMR_ADRES1"));
		System.out.println("상세주소　: " + myInfo.get("CSTMR_ADRES2"));
		System.out.println("캐시　　　: " + myInfo.get("CSTMR_CASH"));
		System.out.println();
		
		// 주문내역 조회
		List<Map<String, Object>> myOrderList = customerDao.selectMyOrder(cid);

		System.out.println("■■■■■■■■■■■■■■■■■　주문 내역 조회　■■■■■■■■■■■■■■■■■");
		for(Map<String, Object> myOrder : myOrderList){
		System.out.println("주문아이디 : " + myOrder.get("ORDER_ID"));
		System.out.println("식당명　　　: " + myOrder.get("RSTRNT_ID"));
		System.out.println("주문상태　　: " + myOrder.get("ORDER_STATUS"));
		System.out.println("주문가격　　: " + myOrder.get("ORDER_COST"));
		System.out.println("주문일　　　: " + myOrder.get("ORDER_DATE"));
		System.out.println("───────────────────────────────────────────────");
		}
		
		
		// 리뷰내역 조회
		List<Map<String, Object>> myReviewList = customerDao.selectMyReview(cid);
		System.out.println("■■■■■■■■■■■■■■■■■　리뷰 내역 조회　■■■■■■■■■■■■■■■■■");
		for(Map<String, Object> myReview : myReviewList){
		System.out.println("리뷰내용 : " + myReview.get("REVIEW_CONTENT"));
		System.out.println("식당명 　: " + myReview.get("RSTRNT_ID"));
		System.out.println("별점　　 : " + myReview.get("REVIEW_SCORE"));
		System.out.println();
		}

		while(true){
			
		System.out.println("1.내 정보 수정  2.로그아웃  3.메인으로");
		int actionKind = ScanUtil.nextInt();
		
		switch (actionKind) {
		case 1:
			editInfo(); 
			break;
		case 2:
			return View.HOME;
		case 3: 
			return View.CUSTOM_MAIN;
		}
		}
	}
	
	public int editInfo(){
		// 내 정보 수정
		System.out.println("────────────────── 내 정보 수정 ──────────────────");
		System.out.println("수정하실 비밀번호를 입력해주세요 >");
		String cstmr_pw = ScanUtil.nextLine();
		
		System.out.println("수정하실 이름을 입력해주세요 >");
		String name = ScanUtil.nextLine();	
		
		System.out.println("수정하실 전화번호를 입력해주세요 >");
		String hp = ScanUtil.nextLine();	
		
		System.out.println("수정하실 생년월일 입력해주세요 >");
		String birthdy = ScanUtil.nextLine();	
		
		System.out.println("수정하실 지역구를 선택해주세요(필수) >");
		System.out.println("1.동구 2.중구 3.서구 4.유성구 5.대덕구 >");
		int cGu = ScanUtil.nextInt();
		String add1 = null;
		switch (cGu) {
			case 1: add1 = "동구"; break;
			case 2: add1 = "중구"; break;
			case 3: add1 = "서구"; break;
			case 4: add1 = "유성구"; break;
			case 5: add1 = "대덕구"; break;
		}
		
		System.out.println("수정하실 상세주소를 입력해주세요 >");
		String add2 = ScanUtil.nextLine();	
		
		Map<String, Object> param = new HashMap<>();
		param.put("CSTMR_ID", cid);
		param.put("CSTMR_PW", cstmr_pw);
		param.put("CSTMR_NM", name);
		param.put("CSTMR_HP", hp);
		param.put("CSTMR_BRTHDY", birthdy);
		param.put("CSTMR_ADRES1", add1);
		param.put("CSTMR_ADRES2", add2);
		
		int myInfo = customerDao.updateMyInfo(param);
		if(0 < myInfo){
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　일반회원 정보가 수정되었습니다　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
		}else{
			System.out.println("!!!!!!!!!!!!!! 일반회원 정보수정 실패 !!!!!!!!!!!!!!");
		}
		return View.CUSTOM_MYPAGE;
	}
	
	public int editCart(){
		// 장바구니 조회, 수정, 삭제
		System.out.println("■■■■■■■■■■■■■■■■■■　장 바 구 니　■■■■■■■■■■■■■■■■■■");
		List<Map<String, Object>> myCartList = customerDao.selectMyCart(cid);
		for(Map<String, Object> myCart : myCartList){
		System.out.println("식당명 : " + myCart.get("RSTRNT_NM"));
		System.out.println("수량 : " + myCart.get("CART_QTY"));
		System.out.println("메뉴명 : " + myCart.get("MENU_NM"));
		System.out.println("합계 : " + myCart.get("SUM_PRICE"));
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		}
		
		System.out.println("1.주문하기  2.조회하기  3.돌아가기");
		int orderKind = ScanUtil.nextInt();
		switch (orderKind) {
		case 1:
			order();
			break;
		case 2:
			
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("　　　　　　　　　조회할 상품번호를 입력해주세요 　　　　　　 ");
			System.out.println("└─────────────────────────────────────────────┘");
			int cartItem = ScanUtil.nextInt();
			System.out.println("1.수정  2.삭제  3.목록");
			int cartKind = ScanUtil.nextInt();
			switch (cartKind) {
			case 1:
				System.out.print("수량변경 > "); int qty = ScanUtil.nextInt();
				int result = customerDao.updateMyCartItem(qty,cartItem, cid);
				if(0 < result){
					System.out.println("┌─────────────────────────────────────────────┐");
					System.out.println("│　　　　　　　　수량정보 변경이 완료되었습니다　　　　　　　 │");
					System.out.println("└─────────────────────────────────────────────┘");
				}else{
					System.out.println("┌─────────────────────────────────────────────┐");
					System.out.println("│　　　　　　　　수량정보 변경이 실패하였습니다　　　　　　　 │");
					System.out.println("└─────────────────────────────────────────────┘");
				}
				break;
			case 2:
				int result2 = customerDao.deleteMyCartItem(cartItem, cid);
				if(0 < result2){
					System.out.println("┌─────────────────────────────────────────────┐");
					System.out.println("│　　　　　　　　주문정보 삭제가 완료되었습니다　　　　　　　 │");
					System.out.println("└─────────────────────────────────────────────┘");
				}else{
					System.out.println("┌─────────────────────────────────────────────┐");
					System.out.println("│　　　　　　　　주문정보 삭제가 실패하였습니다　　　　　　　 │");
					System.out.println("└─────────────────────────────────────────────┘");
				}
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		return View.CUSTOM_MAIN;
	}
	
	public int order(){
		// 결제 -> 주문완료
		int result = customerDao.updateMyOrder(cid);
		
		if(0 < result){
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　고객님의 주문이 접수되었습니다　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
		}else{
			System.out.println("주문과정에 오류가 생겼습니다.");
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　주문접수 오류가 발생하였습니다　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
		}
		return View.CUSTOM_MYPAGE;
	}
	
	public int writeReview(){
		// 리뷰 작성
		System.out.println("■■■■■■■■■■■■■■■■■■　리 뷰 작 성　■■■■■■■■■■■■■■■■■■");
		System.out.print("작성할 주문 아이디 입력 > ");
		int orderId = ScanUtil.nextInt();
		System.out.print("리뷰내용 > ");
		String content = ScanUtil.nextLine();
		System.out.print("별점(1 ~ 5) > ");
		int score = ScanUtil.nextInt();
		
		Map<String, Object> param = new HashMap<>();
		param.put("REVIEW_CONTENT", content);
		param.put("ORDER_ID", orderId);
		param.put("REVIEW_SCORE", score);

		
		int result = customerDao.insertReview(param);
		
		if(0 < result){
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　고객님의 리뷰가 등록되었습니다　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
		}else{
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("　　　　　　　　　　리뷰 작성이　실패하였습니다");
			System.out.println("└─────────────────────────────────────────────┘");
		}
		return View.CUSTOM_MAIN;
	}

	public int chargeMoney(){
		// 사이버 머니 충전
		System.out.println("┌─────────────────────────────────────────────┐");
		System.out.println("│　　　　　　　　충전하실 금액을 입력하여주세요　　　　　　　 │");
		System.out.println("│　　　　　　　　　　　　　ex) 10000　　　　　　　　　　　　 │");
		System.out.println("└─────────────────────────────────────────────┘");
		int cash = ScanUtil.nextInt();
		int result = customerDao.updateMyCash(cash, cid);
		
		if(0 < result){
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　고객님의 캐시가 충전되었습니다　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
		}else{
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　캐시충전 오류가 발생하였습니다　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
		}
		return View.CUSTOM_MAIN;
	}

}

	//회원가입 (관리자, 고객, 식당, 라이더, 배달 대행 업체)
	// 로그인(관리자, 고객, 식당, 라이더, 배달 대행 업체)
	// 1) 관리자 일때 
	// System.out.println("1.회원 정보 조회 \t 2.식당 정보 조회 \t 3. 배달 대행 업체 정보 조회");
	//	1. 회원 정보 조회
	//	1-1. 회원 정보 삭제
	//	2. 식당 정보 조회 
	//	2-1. 식당 정보 삭제
	//	3. 배달 대행 업체 정보 조회
	//	3-1. 배달 대행 업체 정보 삭제
	
	// 2) 고객 일때
	// System.out.println("1. 마이페이지 \t 2. 장바구니 변경 \t 3. 리뷰 작성 \t 4. 사이버 머니 충전");
	//	1. 마이페이지
	//	1-1. 내 정보 조회
	//	1-2. 주문내역 조회
	//	1-3. 리뷰내역 조회
	//	2. 장바구니 변경
	//	2-1. 장바구니 조회
	//	2-2. 장바구니 수정
	//	2-3. 장바구니 삭제
	//	3. 리뷰 작성	
	//	4. 사이버 머니 충전
	
	// 3) 식당 일때
	// System.out.println("1.식당 정보 조회 \t 2. 리뷰 조회  \t 3. 식당 주문 리스트 조회");
	//	1. 식당 정보 조회
	//	1-1. 식당 정보 수정
	//	2. 리뷰 조회  
	//	3. 식당 주문 리스트 조회
	//	3-1. 주문 접수/취소 선택
	//	3-2. 고객에게 소요시간 전달
	
	// 4) 라이더 일때
	// System.out.println("1.라이더 정보 조회 \t 2. 주문 리스트 조회");
	//	1. 라이더 정보 조회   
	//	1-1. 수정
	//  2. 주문 리스트 조회
	//	2-1. 주문 승인/거절 선택
	//	2-2. 고객과 식당에게 배달 완료 알림
	
	// 5) 배달 대행 업체
	//	1. 라이더 리스트 조회
	//	1-1. 라이더 조회
	//	1-2. 라이더 삭제
