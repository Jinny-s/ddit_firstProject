package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ScanUtil;
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
	
	public void login(){
		System.out.println("============= 회원 로그인 ===============");
		System.out.print("아이디 > ");
		String cstmr_id = ScanUtil.nextLine();
		System.out.print("비밀번호 > ");
		String cstmr_pw = ScanUtil.nextLine();	
	
		Map<String, Object> user = customerDao.selectUser(cstmr_id, cstmr_pw);
		if(user == null){
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
		}else{
			System.out.println("로그인 성공");
			cid = cstmr_id;
			cpw = cstmr_pw;
		}
		
	}
	
	public void join(){
		System.out.println("============= 회원 회원가입 ===============");
		System.out.print("아이디 > ");
		String cstmr_id = ScanUtil.nextLine();
		System.out.print("비밀번호 > ");
		String cstmr_pw = ScanUtil.nextLine();
		System.out.print("이름 > ");
		String name = ScanUtil.nextLine();	
		System.out.print("휴대전화 > ");
		String hp = ScanUtil.nextLine();	
		System.out.print("생년월일 > ");
		String birthdy = ScanUtil.nextLine();	
		System.out.print("주소1 > (동구/중구/서구/유성구/대덕구)");
		String add1 = ScanUtil.nextLine();	
		System.out.print("주소2 > ");
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
	}
	
	public void showInfo(){
		System.out.println("============= 마이페이지 ===============");
		// 내 정보 조회
		Map<String, Object> myInfo = customerDao.selectMyInfo(cid, cpw);
		System.out.println(myInfo);
		// 주문내역 조회
		List<Map<String, Object>> myOrder = customerDao.selectMyOrder(cid);
		System.out.println(myOrder);
		// 리뷰내역 조회
		List<Map<String, Object>> myReview = customerDao.selectMyReview(cid);	
		System.out.println(myReview);
		
		System.out.println("1.내 정보 수정\t2.장바구니 조회\t3.캐시 충전\t4.리뷰 작성\t");
		int actionKind = ScanUtil.nextInt();
		switch (actionKind) {
		case 1:
			editInfo(); break;
		case 2:
			editCart(); break;
		case 3:
			chargeMoney(); break;
		case 4:
			System.out.print("1.작성할 주문 아이디 입력  > ");
			int orderItem = ScanUtil.nextInt();
			writeReview(orderItem); break;
		default:
			break;
		}
	}
	
	public void editInfo(){
		// 내 정보 수정
		System.out.println("============= 내 정보 수정 ===============");
		System.out.print("아이디 > ");
		String cstmr_id = ScanUtil.nextLine();
		System.out.print("비밀번호 > ");
		String cstmr_pw = ScanUtil.nextLine();
		System.out.print("이름 > ");
		String name = ScanUtil.nextLine();	
		System.out.print("휴대전화 > ");
		String hp = ScanUtil.nextLine();	
		System.out.print("생년월일 > ");
		String birthdy = ScanUtil.nextLine();	
		System.out.print("주소1 > (동구/중구/서구/유성구/대덕구)");
		String add1 = ScanUtil.nextLine();	
		System.out.print("주소2 > ");
		String add2 = ScanUtil.nextLine();	
		
		Map<String, Object> param = new HashMap<>();
		param.put("CSTMR_ID", cstmr_id);
		param.put("CSTMR_PW", cstmr_pw);
		param.put("CSTMR_NM", name);
		param.put("CSTMR_HP", hp);
		param.put("CSTMR_BRTHDY", birthdy);
		param.put("CSTMR_ADRES1", add1);
		param.put("CSTMR_ADRES2", add2);
		
		int myInfo = customerDao.updateMyInfo(param);
		
	}
	
	public void editCart(){
		// 장바구니 조회, 수정, 삭제
		System.out.println("============= 장바구니 ===============");
		List<Map<String, Object>> myCart = customerDao.selectMyCart(cid);
		
		System.out.print("1.조회할 상품 아이디 입력  > ");
		int cartItem = ScanUtil.nextInt();
		System.out.println("1.수정\t2.삭제");
		int cartKind = ScanUtil.nextInt();
		switch (cartKind) {
		case 1:
			System.out.print("수량  > "); int qty = ScanUtil.nextInt();
			int result = customerDao.updateMyCartItem(qty,cartItem, cid);
			if(0 < result){
				System.out.println("수량 변경 완료");
			}else{
				System.out.println("수량 변경 실패");
			}
			break;
		case 2:
			int result2 = customerDao.deleteMyCartItem(cartItem, cid);
			if(0 < result2){
				System.out.println("삭제 완료");
			}else{
				System.out.println("삭제 실패");
			}
		default:
			break;
		}
		System.out.println("1.주문하기\t2.목록");
		int orderKind = ScanUtil.nextInt();
		switch (orderKind) {
		case 1:
			order();
			break;
		case 2:
			showInfo();
		default:
			break;
		}
	}
	
	public void order(){
		// 결제 -> 주문완료
		int result = customerDao.updateMyOrder(cid);
		
		if(0 < result){
			System.out.println("배달이 도착하였습니다.");
		}else{
			System.out.println("주문과정에 오류가 생겼습니다.");
		}

	}
	
	public void writeReview(int orderId){
		// 리뷰 작성
		System.out.println("============= 리뷰 작성 ===============");
		System.out.print("리뷰내용 > ");
		String content = ScanUtil.nextLine();
		System.out.print("별점 > (1~5)");
		int score = ScanUtil.nextInt();
		
		Map<String, Object> param = new HashMap<>();
		param.put("REVIEW_CONTENT", content);
		param.put("RSTRNT_ID", cid);
		param.put("ORDER_ID", orderId);
		param.put("REVIEW_SCORE", score);

		
		int result = customerDao.insertReview(param);
		
		if(0 < result){
			System.out.println("리뷰작성 완료");
		}else{
			System.out.println("리뷰작성 실패");
		}

	}

	public void chargeMoney(){
		// 사이버 머니 충전
		System.out.print("충전금액을 입력해주세요 ex)10000 > ");
		int cash = ScanUtil.nextInt();
		int result = customerDao.updateMyCash(cash, cid);
		
		if(0 < result){
			System.out.println("충전 완료");
		}else{
			System.out.println("충전 실패");
		}
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
