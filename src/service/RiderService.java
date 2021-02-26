package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.RiderDao;
import util.ScanUtil;
import util.View;

public class RiderService {
	
//	private RiderService(){}
	
	private static RiderService instance;
	public static RiderService getInstance(){
		if(instance == null){
			instance = new RiderService();
		}
		return instance;
	}
	
	private RiderDao riderDao = RiderDao.getInstance();
	static String riderId, riderPw; //변수 저장

	//라이더 회원가입
	public int riderJoin(){
		System.out.println("───────────────── 라이더스　회원가입 ─────────────────");

		System.out.println("아이디를 입력해주세요 >");
		String riderId = ScanUtil.nextLine();
		
		System.out.println("비밀번호를 입력해주세요 >");
		String riderPw = ScanUtil.nextLine();
		
		System.out.println("라이더님 성명을 입력해주세요 >");
		String riderNm = ScanUtil.nextLine();
		
		System.out.println("지역구를 선택해 주세요");
		System.out.println("1.동구 2.중구 3.서구 4.유성구 5.대덕구 >");
		int riderGu = ScanUtil.nextInt();
		String riderAdd1 = null;
		switch (riderGu) {
			case 1: riderAdd1 = "동구"; break;
			case 2: riderAdd1 = "중구"; break;
			case 3: riderAdd1 = "서구"; break;
			case 4: riderAdd1 = "유성구"; break;
			case 5: riderAdd1 = "대덕구"; break;
		}
		System.out.println("상세주소를 입력해주세요>");
		String riderAdd2 = ScanUtil.nextLine();
		
		System.out.println("전화번호를 입력해주세요 >");
		String riderTel = ScanUtil.nextLine();
		
		Map<String, Object> param = new HashMap<>();
		param.put("RD_ID", riderId);
		param.put("RD_PW", riderPw);
		param.put("RD_NM", riderNm);
		param.put("RD_ADRES1", riderAdd1);
		param.put("RD_ADRES2", riderAdd2);
		param.put("RD_TELNO", riderTel);
		
		int result = riderDao.insertRider(param);
		
		if(0 < result){
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　라이더스 가입이 완료되었습니다　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
		}else{
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　라이더스 가입이 실패하였습니다　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
		}
		
		return View.HOME;
	}
	
	//라이더 로그인
	public int riderLogin() {
		System.out.println("───────────────── 라이더스　로그인 ─────────────────");
		System.out.print("아이디>");
		riderId = ScanUtil.nextLine();
		System.out.print("비밀번호>");
		riderPw = ScanUtil.nextLine();
		
		Map<String, Object> rider = riderDao.selectRider(riderId, riderPw);
		
		if(rider == null){
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
		}else{
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("　　　　　　　　　　라이더스  로그인 성공!");
			System.out.println("　　　　　　　라이더님, 오늘도 안전운전 하세요!");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			Controller.loginUser = rider;
			return View.RIDER_MAIN;
		}
	
		return View.HOME;
	}
	
	//라이더 메인화면
	public int riderMain() {
		System.out.println("┌──────────────────────────────────────────────┐");
		System.out.println("│               서비스를 선택해주세요               │");
		System.out.println("└──────────────────────────────────────────────┘");
		System.out.println("1.라이더 정보 조회"); //정보 조회 안에 정보 수정
		System.out.println("2.식당 주문 리스트 조회"); //리스트 조회 안에 매칭 넣기
		System.out.println("3.배달완료 보내기");
		System.out.println("4.로그아웃");
		System.out.println("─────────────────────────────────────────");
		System.out.println("입력 >");
		int input = ScanUtil.nextInt();

		while(true) {
			switch(input) {
			case 1: return View.RIDER_MYPAGE;
			case 2: return View.DELIVERY_ORDER;
			case 3: return View.DELIVERY_CONFIRM;
			case 4: return View.HOME;
			}
		}
	}
	
	//라이더 정보 조회	
	public int riderInfo() {

		System.out.println("┌──────────────────────────────────────────────┐");
		System.out.println("│　　　　　　　　　　　　　라이더 정보조회 　　　　　　　　　　　│");
		System.out.println("└──────────────────────────────────────────────┘");
		Map<String, Object> selectRider = riderDao.selectRider((String) Controller.loginUser.get("RD_ID"), (String) Controller.loginUser.get("RD_PW"));
		System.out.println("─────────────────────────────────────────");
		System.out.println("아이디 : " + selectRider.get("RD_ID"));
		System.out.println("비밀번호 : " + selectRider.get("RD_PW"));
		System.out.println("라이더명 : " + selectRider.get("RD_NM"));
		System.out.println("전화번호 : " + selectRider.get("RD_TELNO"));
		System.out.println("지역구 : " + selectRider.get("RD_ADRES1"));
		System.out.println("상세주소 : " + selectRider.get("RD_ADRES2"));
		System.out.println("─────────────────────────────────────────");
		System.out.println("1.정보수정\t2.메인으로 >");
		
		int input = ScanUtil.nextInt();
		
		switch(input) {
		case 1: return View.EDIT_RIDER;
		case 2: return View.RIDER_MAIN;
		}
		
		return View.RIDER_MAIN;
	}

	//라이더 정보 수정
	public int riderUpdate() {
		System.out.println("┌──────────────────────────────────────────────┐");
		System.out.println("│                  라이더 정보 정                 │");
		System.out.println("└──────────────────────────────────────────────┘");
		System.out.println("수정하실 비밀번호를 입력해주세요 >");
		String riderPw = ScanUtil.nextLine();
		
		System.out.println("수정하실 라이더명을 입력해주세요 >");
		String riderNm = ScanUtil.nextLine();
		
		System.out.println("수정하실 지역구를 선택해주세요(필수) >");
		System.out.println("1.동구 2.중구 3.서구 4.유성구 5.대덕구 >");
		int riderGu = ScanUtil.nextInt();
		String riderAdd1 = null;
		switch (riderGu) {
			case 1: riderAdd1 = "동구"; break;
			case 2: riderAdd1 = "중구"; break;
			case 3: riderAdd1 = "서구"; break;
			case 4: riderAdd1 = "유성구"; break;
			case 5: riderAdd1 = "대덕구"; break;
		}
				
		System.out.println("수정하실 상세주소를 입력해주세요 >");
		String riderAdd2 = ScanUtil.nextLine();
		
		System.out.println("수정하실 전화번호를 입력해주세요 >");
		String riderTel = ScanUtil.nextLine();
	
		
		Map<String, Object> param = new HashMap<>();
		param.put("RD_PW", riderPw);
		param.put("RD_NM", riderNm);
		param.put("RD_ADRES1", riderAdd1);
		param.put("RD_ADRES2", riderAdd2);
		param.put("RD_TELNO", riderTel);
				
		int result = riderDao.riderUpdate((String) Controller.loginUser.get("RD_ID"), param);
		
		if(0 < result){
			System.out.println("라이더 정보 수정이 완료되었습니다.");
		}else{
			System.out.println("라이더 정보 수정이 실패하였습니다.");
		}	
		return View.RIDER_MAIN;
	}
	
	//식당 오더 리스트 조회
	public int viewOrderList() {
		String status = null;
		
		Map<String, Object> selectRider = riderDao.selectRider((String) Controller.loginUser.get("RD_ID"), (String) Controller.loginUser.get("RD_PW"));
		
		String gu = (String) selectRider.get("RD_ADRES1");
		
		System.out.println("┌──────────────────────────────────────────────┐");
		System.out.println("│                배달 대기 목록조회                │");
		System.out.println("└──────────────────────────────────────────────┘");
		System.out.println("조회 하실 목록을 선택해 주세요.");
		System.out.println("1.접수완료(배달대기) 2.배달중 3.배달완료");
		
		int input = ScanUtil.nextInt();
		
		switch(input){
		case 1:
			status = "접수완료";
			System.out.println(status + "로 검색합니다.");
			break;
		case 2:
			status = "배달중";
			System.out.println(status + "으로 검색합니다.");
			break;
		case 3:
			status = "배달완료";
			System.out.println(status + "로 검색합니다.");
			break;
		}
			
		List<Map<String, Object>> orderListStatus = riderDao.viewOrderList(status, gu);
			
		for (Map<String, Object> orderList : orderListStatus){
			System.out.println("─────────────────────────────────────────");
			System.out.println("주문번호 : " + orderList.get("ORDER_ID"));
			System.out.println("주문자 ID : " + orderList.get("CSTMR_ID"));
			System.out.println("식당 ID : " + orderList.get("RSTRNT_ID"));
			System.out.println("주문 상태 : " + orderList.get("ORDER_STATUS"));
			System.out.println("주소 : " + orderList.get("CSTMR_ADRES1"));
			System.out.println("상세주소 : " + orderList.get("CSTMR_ADRES2"));
			System.out.println();
		}
		System.out.println("─────────────────────────────────────────");
		System.out.println("1.배달 시작하기");
		System.out.println("2.메인으로 돌아가기");
		int input1 = ScanUtil.nextInt();

		while(true) {
			switch(input1) {
			case 1: return View.MATCHING_ORDER;
			case 2: return View.RIDER_MAIN;
			}
		}
	}

	//매칭 Insert / orderStatus "배달중" 으로 변경
	int myOrderNo = 0;
	public int matchinInsert() {
		System.out.println("배달하실 주문번호를 입력해주세요");
		System.out.println("입력>");
		myOrderNo = ScanUtil.nextInt();
			
		String riderId = (String) Controller.loginUser.get("RD_ID");
		Map<String, Object> param = new HashMap<>();
		param.put("A.ORDER_ID", myOrderNo);
		param.put("B.RD_ID", riderId);
		
		int result = riderDao.matchinInsert(param);
		
		if(0 < result){
			System.out.println("배달이 매칭되었습니다.");
			statusUpdate(); //Status업데이트
		}else{
			System.out.println("매칭 실패");
		}	
		
		return View.RIDER_MAIN;
	}
	
	//오더리스트 STATUS "배달중" 으로 변경
	public int statusUpdate(){

		String statusUpdate = "배달중";
		
		Map<String, Object> param = new HashMap<>();
		param.put("ORDER_STATUS", statusUpdate);
		param.put("ORDER_ID", myOrderNo);
		
		int result = riderDao.statusUpdateIng(param);
		
		if(0 < result){
			System.out.println("성공적으로 업데이트 되었습니다.");
		}else{
			System.out.println("업데이트 실패");
		}
	
		return View.RIDER_MAIN;
	}
	
	//오더리스트 STATUS "배달완료" 로 변경, 매칭삭제
	public int statusUpdateDone(){
		System.out.println("배달 맡으신 주문의 번호는 " + myOrderNo + " 번 입니다");
		System.out.println("배달 완료 처리하시겠습니까?");
		System.out.println("1.YES 2.NO");
		int input = ScanUtil.nextInt();
		switch(input){
		case 1:
			String statusUpdate = "배달완료";
			
			Map<String, Object> param = new HashMap<>();
			param.put("ORDER_STATUS", statusUpdate);
			param.put("ORDER_ID", myOrderNo);
			
			Map<String, Object> param1 = new HashMap<>();
			param1.put("ORDER_ID", myOrderNo);
			
			int delete = riderDao.matchingDelete(param1);
			
			if(0 < delete){
				System.out.println("매칭이 성공적으로 삭제 되었습니다.");
			}else{
				System.out.println("삭제 실패");
			}
			
			int result = riderDao.statusUpdateIng(param);
			
			if(0 < result){
				System.out.println("성공적으로 업데이트 되었습니다.");
			}else{
				System.out.println("업데이트 실패");
			}
		
			return View.RIDER_MAIN;
		case 2:
			return View.RIDER_MAIN;
		}
		return View.RIDER_MAIN;
	}
}
