package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.RmngDao;
import util.ScanUtil;
import util.View;

public class RmngService {
	
	private static RmngService instance;
	
	public static RmngService getInstance(){
		if(instance == null){
			instance = new RmngService();
		}
		return instance;
	}
	 
	private RmngDao rmngDao = RmngDao.getInstance();
	
	static String riderMngId, riderMngPw;

	//배달 대행 업체 회원가입
	public int rmngServiceJoin(){

		System.out.println("┌──────────────────────────────────────────────┐");
		System.out.println("│				배달 대행 업체 회원가입			   │");
		System.out.println("└──────────────────────────────────────────────┘");
		System.out.println("아이디를 입력해주세요 >");
		String userId = ScanUtil.nextLine();
		System.out.println("비밀번호를 입력해주세요 >");
		String password = ScanUtil.nextLine();
		System.out.println("대행업체명을 입력해주세요 >");
		String userName = ScanUtil.nextLine();
		System.out.println("전화번호를 입력해주세요 >");
		String telNo = ScanUtil.nextLine();
		
		Map<String, Object> param = new HashMap<>();
		param.put("MNG_ID", userId);
		param.put("MNG_PW", password);
		param.put("MNG_NM", userName);
		param.put("MNG_TELNO", telNo);

		int result = rmngDao.insertRmng(param);
		
		if(0 < result){
			System.out.println("회원가입에 성공하셨습니다.");
		}else{
			System.out.println("회원가입에 실패하셨습니다.");
		}
	
		return View.HOME;
	}

	//배달 대행 업체 로그인
	public int rmngServicelogin() {

		System.out.println("┌──────────────────────────────────────────────┐");
		System.out.println("│               배달 대행 업체 로그인               │");
		System.out.println("└──────────────────────────────────────────────┘");
		System.out.println("아이디를 입력해주세요 >");
		riderMngId = ScanUtil.nextLine();
		System.out.println("비밀번호를 입력해주세요 >");
		riderMngPw = ScanUtil.nextLine();
		
		Map<String, Object> rmng = rmngDao.selectRmng(riderMngId, riderMngPw);
		
		if(rmng == null){
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
		}else{
			System.out.println("로그인 성공");
			Controller.loginUser = rmng;
			return View.RMNG_MAIN;
		}
		
		return View.LOGIN;
	}
	
	//대행업체 정보 수정
	public int rmngUpdate() {
		System.out.println("┌──────────────────────────────────────────────┐");
		System.out.println("│              배달 대행 업체 정보수정              │");
		System.out.println("└──────────────────────────────────────────────┘");
		System.out.println("수정하실 비밀번호를 입력해주세요 >");
		String mngPw = ScanUtil.nextLine();
		
		System.out.println("수정하실 업체명을 입력해주세요 >");
		String mngNm = ScanUtil.nextLine();
		
		System.out.println("수정하실 전화번호를 입력해주세요 >");
		String mngTel = ScanUtil.nextLine();
	
		
		Map<String, Object> param = new HashMap<>();
		param.put("MNG_PW", mngPw);
		param.put("MNG_NM", mngNm);
		param.put("MNG_TELNO", mngTel);
				
		int result = rmngDao.rmngUpdate((String) Controller.loginUser.get("MNG_ID"), param);
		
		if(0 < result){
			System.out.println("────────────────────────────────────────────────");
			System.out.println("대행업체 정보 수정이 완료되었습니다.");
		}else{
			System.out.println("────────────────────────────────────────────────");
			System.out.println("대행업체 정보 수정이 실패하였습니다.");
		}	
		return View.RMNG_MAIN;
	}
	
	//대행업체 메인 화면
	public int RmngMain() {
		System.out.println("┌──────────────────────────────────────────────┐");
		System.out.println("│               서비스를 선택해주세요               │");
		System.out.println("└──────────────────────────────────────────────┘");
		System.out.println("───────────────────────────────────────────────");
		System.out.println("1.내 업체 조회"); //정보 조회 안에 정보 수정
		System.out.println("2.라이더 목록 조회"); //목록 조회 안에 삭제 넣기
		System.out.println("3.로그아웃");
		System.out.println("────────────────────────────────────────────────");
		System.out.println("입력 >");
		int input = ScanUtil.nextInt();

		while(true) {
			switch(input) {
			case 1: return View.RMNG_MYPAGE;
			case 2: return View.MY_RIDERS;
			case 3: return View.LOGIN;
			}
		}
	}
	
	//대행업체 정보 조회	
	public int rmngInfo() {

		System.out.println("┌──────────────────────────────────────────────┐");
		System.out.println("│                대행업체 정보 조회                │");
		System.out.println("└──────────────────────────────────────────────┘");
		Map<String, Object> selectRider = rmngDao.selectRmng((String) Controller.loginUser.get("MNG_ID"), (String) Controller.loginUser.get("MNG_PW"));
		System.out.println("아이디 : " + selectRider.get("MNG_ID"));
		System.out.println("비밀번호 : " + selectRider.get("MNG_PW"));
		System.out.println("라이더명 : " + selectRider.get("MNG_NM"));
		System.out.println("전화번호 : " + selectRider.get("MNG_TELNO"));
		System.out.println("───────────────────────────────────────────────");
		System.out.println("1.정보수정");
		System.out.println("2.메인으로");
		System.out.println("───────────────────────────────────────────────");
		System.out.println("입력 >");
		
		int input = ScanUtil.nextInt();
		
		switch(input) {
		case 1: return View.EDIT_RMNG;
		case 2: return View.RMNG_MAIN;
		}
		
		return View.RMNG_MAIN;
	}
	
	//라이더 목록 조회
	public int riderList(){
		List<Map<String, Object>> riders = rmngDao.ridersView();
		
		System.out.println("┌──────────────────────────────────────────────┐");
		System.out.println("│                  라이더스 목록                  │");
		System.out.println("└──────────────────────────────────────────────┘");
		
		for (Map<String, Object> rider : riders){
			System.out.println("───────────────────────────────────────────────");
			System.out.println("라이더 ID :" + rider.get("RD_ID"));
			System.out.println("라이더 pw :" + rider.get("RD_PW"));
			System.out.println("라이더 이름 :" + rider.get("RD_NM"));
			System.out.println("라이더 주소 :" + rider.get("RD_ADRES1"));
			System.out.println("라이더 상세주소 :" + rider.get("RD_ADRES2"));
		}
		System.out.println("───────────────────────────────────────────────");
		System.out.println("1.라이더 삭제");
		System.out.println("2.메인으로");
		System.out.println("───────────────────────────────────────────────");
		int input = ScanUtil.nextInt();
		
		switch(input) {
		case 1: return View.RIDER_DELETE;
		case 2: return View.RMNG_MAIN;
		}
		
		return View.RMNG_MAIN;
	}
	
	//라이더 삭제
	public int riderDelete(){
		System.out.println("┌──────────────────────────────────────────────┐");
		System.out.println("│                  라이더스 삭제                  │");
		System.out.println("└──────────────────────────────────────────────┘");
		System.out.println("삭제하실 라이더의 이름을 입력해주세요 >");
		String name = ScanUtil.nextLine();
		
		Map<String, Object> param = new HashMap<>();
		param.put("RD_NM", name);
		
		int result = rmngDao.deleteRiders(param);
		
		if(0 < result){ 
			System.out.println("───────────────────────────────────────────────");
			System.out.println("삭제 성공");
		}else{
			System.out.println("───────────────────────────────────────────────");
			System.out.println("삭제 실패");
		}
		
		return View.RMNG_MAIN;
	}
	
}
