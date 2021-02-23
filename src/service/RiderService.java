package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.RiderDao;
import util.ScanUtil;
import util.View;

public class RiderService {
	
	private RiderService(){}
	
	private static RiderService instance;
	
	public static RiderService getInstance(){
		if(instance == null){
			instance = new RiderService();
		}
		return instance;
	}
	
	private RiderDao riderDao = RiderDao.getInstance();
	
	//임시 테스트
	public static void main(String[] args) {
		new RiderService().start();
	}
	private void start() {
		statusUpdate();
	}
	String LoginId = "kdh";
	String LoginPw = "kdh2";
	//test

	//라이더 회원가입
	public int riderServiceJoin(){

		System.out.print("아이디>");
		String userId = ScanUtil.nextLine();
		System.out.print("비밀번호>");
		String password = ScanUtil.nextLine();
		System.out.print("이름>");
		String userName = ScanUtil.nextLine();
		System.out.print("구까지 입력해주세요>");
		String adres1 = ScanUtil.nextLine();
		System.out.print("상세주소를 입력해주세요>");
		String adres2 = ScanUtil.nextLine();
		
		Map<String, Object> param = new HashMap<>();
		param.put("RD_ID", userId);
		param.put("RD_PW", password);
		param.put("RD_NM", userName);
		param.put("RD_ADRES1", adres1);
		param.put("RD_ADRES2", adres2);

		int result = riderDao.insertRider(param);
		
		if(0 < result){
			System.out.println("회원가입 성공");
		}else{
			System.out.println("회원가입 실패");
		}
		
		return View.RIDER_MAIN;
	}
	
	//라이더 로그인
	public int riderServicelogin() {
		System.out.println("========== 라이더 로그인 =============");
		
		System.out.print("아이디>");
		LoginId = ScanUtil.nextLine();
		
		System.out.print("비밀번호>");
		LoginPw = ScanUtil.nextLine();
		
		Map<String, Object> user = riderDao.loginRider(LoginId, LoginPw);
		
		if(user == null){
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
		}else{
			System.out.println("로그인 성공");
			return View.RIDER_MAIN;
		}
	
		return View.RIDER_MAIN;
	}
	
	//라이더 정보 조회	
	public int viewRiderInfo() {

		System.out.println("라이더 정보 조회");
		List<Map<String, Object>> user = riderDao.viewRiderInfo(LoginId);
		System.out.println(user);
		return View.RIDER_MAIN;
	}

	//라이더 정보 수정
	public int RiderUpdate() {

		System.out.println("수정하실 내용을 입력해주세요");
		System.out.println("이름>");
		String riderNm = ScanUtil.nextLine();
		System.out.println("주소1>");
		String adres1 = ScanUtil.nextLine();
		System.out.println("주소2>");
		String adres2 = ScanUtil.nextLine();
		
		String rdId = LoginId; // 얘내 지워야됨
		String rdPw = LoginPw;
		
		Map<String, Object> param = new HashMap<>();
		param.put("RD_NM", riderNm);
		param.put("RD_ADRES1", adres1);
		param.put("RD_ADRES2", adres2);
		param.put("RD_ID", rdId);
		param.put("RD_PW", rdPw);
		
		int result = riderDao.RiderUpdate(param);
		
		if(0 < result){
			System.out.println("수정 성공");
		}else{
			System.out.println("수정 실패");
		}	
		
		return View.RIDER_MAIN;
	}
	
	//식당 오더 리스트 조회
	public int viewOrderList() {
		String status = null;
		
		System.out.println("배달 대기 목록 조회입니다");
		System.out.println("1.배달대기중 2.배달 3.배달완료");
		System.out.println("입력>");
		
		int input = ScanUtil.nextInt();
		
		switch(input){
		case 1:
			status = "배달대기중";
			break;
		case 2:
			status = "배달중";
			break;
		case 3:
			status = "배달완료";
			break;
		}
		
		List<Map<String, Object>> orderListStatus = riderDao.viewOrderList(status);
		
		for (Map<String, Object> orderList : orderListStatus){
			System.out.println();
			System.out.println("주문번호 : " + orderList.get("ORDER_ID"));
			System.out.println("주문자 ID : " + orderList.get("CSTMR_ID"));
			System.out.println("식당 ID : " + orderList.get("RSTRNT_ID"));
			System.out.println("주문 상태 : " + orderList.get("ORDER_STATUS"));
			System.out.println("주소 : " + orderList.get("CSTMR_ADRES1"));
			System.out.println("상세주소 : " + orderList.get("CSTMR_ADRES2"));
			System.out.println();
			System.out.println("-----------------------");	
		}
		return View.RIDER_MAIN;
	}
	
	//오더리스트 STATUS 변경하기
	public int statusUpdate(){

		String statusUpdate = null;
		
		System.out.println("배달 상태 변경입니다.");
		System.out.println("1.배달완료 2.배달중 3.배달대기중");
		System.out.println("입력>");
		
		int input = ScanUtil.nextInt();
		
		switch(input) {
		case 1:
			statusUpdate = "배달완료";
			break;
		case 2:
			statusUpdate = "배달중";
			break;
		case 3:
			statusUpdate = "배달대기중";
			break;
		}
		
		System.out.println("주문번호를 입력해주세요.");
		System.out.println("입력>");
		
		int orderNo = ScanUtil.nextInt();
		
		Map<String, Object> param = new HashMap<>();
		param.put("ORDER_STATUS", statusUpdate);
		param.put("ORDER_ID", orderNo);
		
		int result = riderDao.statusUpdate(param);
		
		if(0 < result){
			System.out.println("성공적으로 업데이트 되었습니다.");
		}else{
			System.out.println("업데이트 실패");
		}
	
		return View.RIDER_MAIN;
	}
	
	// 주문 선택

	
	
}
