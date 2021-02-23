package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dao.RmngDao;
import util.ScanUtil;
import util.View;

public class RmngService {
	
	private RmngService(){}
	
	private static RmngService instance;
	
	public static RmngService getInstance(){
		if(instance == null){
			instance = new RmngService();
		}
		return instance;
	}
	
	private RmngDao rmngDao = RmngDao.getInstance();
	
	//배달 대행 업체 회원가입
	public int rmngServiceJoin(){

		System.out.println("--------배달 대행 업체 회원가입---------");
		System.out.print("아이디>");
		String userId = ScanUtil.nextLine();
		System.out.print("비밀번호>");
		String password = ScanUtil.nextLine();
		System.out.print("이름>");
		String userName = ScanUtil.nextLine();
		System.out.print("전화번호>");
		String telNo = ScanUtil.nextLine();
		
		Map<String, Object> param = new HashMap<>();
		param.put("MNG_ID", userId);
		param.put("MNG_PW", password);
		param.put("MNG_NM", userName);
		param.put("MNG_TELNO", telNo);

		int result = rmngDao.insertRmng(param);
		
		if(0 < result){
			System.out.println("회원가입 성공");
		}else{
			System.out.println("회원가입 실패");
		}
	
		return View.HOME;
	}

	//배달 대행 업체 로그인
	public int rmngServicelogin() {

		System.out.println("========== 배달 대행 업체 로그인 =============");
		System.out.print("아이디>");
		String userId = ScanUtil.nextLine();
		System.out.print("비밀번호>");
		String password = ScanUtil.nextLine();
		
		Map<String, Object> user = rmngDao.selectRmng(userId, password);
		
		if(user == null){
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
		}else{
			System.out.println("로그인 성공");
		
			return View.RMNG_MAIN;
		}
		
		return View.HOME;
	}
	
	//라이더 목록 조회
	public int riderList(){
		List<Map<String, Object>> riders = rmngDao.ridersView();
		
		System.out.println(riders);
		System.out.println("============라이더 조회===========");
		
		for (Map<String, Object> rider : riders){
			System.out.println();
			System.out.println("라이더 ID :" + rider.get("RD_ID"));
			System.out.println("라이더 pw :" + rider.get("RD_PW"));
			System.out.println("라이더 이름 :" + rider.get("RD_NM"));
			System.out.println("라이더 주소 :" + rider.get("RD_ADRES1"));
			System.out.println("라이더 상세주소 :" + rider.get("RD_ADRES2"));
			System.out.println();
			System.out.println("-----------------------");	
		}
		
		return View.HOME;
	}
	
	//라이더 삭제
	public int riderDelete(){
		System.out.println("삭제하실 라이더의 이름을 기입해주세요.");
		System.out.print("이름>");
		String name = ScanUtil.nextLine();
		
		Map<String, Object> param = new HashMap<>();
		param.put("RD_NM", name);
		
		int result = rmngDao.deleteRiders(param);
		
		if(0 < result){
			System.out.println("삭제 성공");
		}else{
			System.out.println("삭제 실패");
		}
		
		return View.HOME;
	}
	
}
