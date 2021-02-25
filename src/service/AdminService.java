package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;
import util.ScanUtil;
import util.View;
import dao.AdminDao;

public class AdminService {

	
	private AdminService(){}
	private static AdminService instance;
	public static AdminService getInstance(){
		if(instance == null){
			instance = new AdminService();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	private AdminDao adminDao = AdminDao.getInstance();
	
	//관리자 로그인
	public int login(){
		System.out.println("───────────────── 관리자  로그인 ─────────────────");
		System.out.println("아이디를 입력해주세요 >");
		String userId = ScanUtil.nextLine();
		System.out.println("비밀번호를 입력해주세요 >");
		String password = ScanUtil.nextLine();
		
		
		
		Map<String, Object> user = adminDao.loginAdmin(userId, password);
		if(user == null){
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
		}else{
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("　　　　　　　　　　최고관리자 로그인 성공!");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			return View.ADMIN_MAIN;
		}
		return View.LOGIN;
		
	}
	
	//관리자 첫 화면
	public int adminMain(){
		System.out.println("┌─────────────────────────────────────────────┐");
		System.out.println("│ 　　　　　　　　　시스템　관리자　페이지 　　　　　　　　　　│");
		System.out.println("│　　　　　　　조회할　데이터를　선택해주세요　> 　　　　　　 │");
		System.out.println("└─────────────────────────────────────────────┘");
		System.out.println("1.라이더스  2.일반회원  3.식당");
		System.out.println("4.배달대행업체  5.로그아웃 >");
		int input = ScanUtil.nextInt();
		
			
			switch(input){
			case 1: updateRiders(); 	break;
			case 2: updateCustomer(); 	break;
			case 3:	updateRestaurant();	break;
			case 4:	updateRider_MNG(); 	break;
			case 5: return View.HOME;
			}
			return View.HOME;
		}
	
	//라이더스 정보 제공 및 수정, 삭제
	public int updateRiders(){
		System.out.println("■■■■■■■■■■■■■■■■■■ 라이더스 조회 ■■■■■■■■■■■■■■■■■");
		List<Map<String, Object>> ridersList = adminDao.selectAllRiders();
		for(Map<String, Object> map : ridersList){
			System.out.println(map.get("RD_ID") + " / " + map.get("RD_PW") + " / " + map.get("RD_NM") + " / "
					+ map.get("RD_TELNO") + " / " + map.get("RD_ADRES1") + " / " + map.get("RD_ADRES2"));
		}
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("1.조회\t2.메인으로");
		int num = ScanUtil.nextInt();
		switch(num) {
		
		case 1:
		System.out.println("┌─────────────────────────────────────────────┐");
		System.out.println("│　　　　　　　　조회하실 아이디를 입력해주세요　　　　　　　 │");
		System.out.println("└─────────────────────────────────────────────┘");
		String riders_id = ScanUtil.nextLine();
		
		System.out.println("┌─────────────────────────────────────────────┐");
		System.out.println("│　　　　　　　　수행하실 선택지를 입력해주세요　　　　　　　 │");
		System.out.println("└─────────────────────────────────────────────┘");
		System.out.println("1.수정\t2.삭제\t0.목록 >");
		int input = ScanUtil.nextInt();
		
		switch(input) {
		case 1:
			update(riders_id);
			break;
			
		case 2:
			int ridersdelete = adminDao.deleteRiders(riders_id);
			if(ridersdelete > 0){
				System.out.println("┌─────────────────────────────────────────────┐");
				System.out.println("│　　　　　　　　라이더스 삭제가 완료되었습니다　　　　　　　 │");
				System.out.println("└─────────────────────────────────────────────┘");

			}else{
				System.out.println("┌─────────────────────────────────────────────┐");
				System.out.println("│　　　　　　　　라이더스 삭제가 실패하였습니다　　　　　　　 │");
				System.out.println("└─────────────────────────────────────────────┘");
			}
			break;
		case 0:
			break;
		}
		case 2: return adminMain();
		}
		return adminMain();
	}
	
	//라이더스 수정
	private int update(String riders_id){
		System.out.println("──────────────── 라이더스 정보 수정 ───────────────");
		System.out.print("수정할 비밀번호 입력> ");	String RD_PW = ScanUtil.nextLine();
		System.out.print("수정할 이름 입력> ");		String RD_NM = ScanUtil.nextLine();
		System.out.print("수정할 전화번호 입력> ");	String RD_TELNO = ScanUtil.nextLine();
		System.out.print("수정할 주소1 입력> ");	String RD_ADRES1 = ScanUtil.nextLine();
		System.out.print("수정할 주소2 입력> ");	String RD_ADRES2 = ScanUtil.nextLine();
		
		
		Map<String, Object> ridersParam = new HashMap<>();
		ridersParam.put("RD_PW", RD_PW);
		ridersParam.put("RD_NM", RD_NM);
		ridersParam.put("RD_ADRES1", RD_ADRES1);
		ridersParam.put("RD_ADRES2", RD_ADRES2);		
		ridersParam.put("RD_TELNO", RD_TELNO);		
		ridersParam.put("RD_ID", riders_id);	
		
		int updateCnt = adminDao.updateRiders(ridersParam);
		
		if(updateCnt > 0){
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　라이더스 정보가 수정되었습니다　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
		}else{
			System.out.println("!!!!!!!!!!!!!! 라이더스 정보수정 실패 !!!!!!!!!!!!!!");
		}
		
	
		return View.ADMIN_MAIN;
		}
	
	//일반회원 정보 제공 및 수정, 삭제
	public int updateCustomer(){
		System.out.println("■■■■■■■■■■■■■■■■■■ 일반회원 조회 ■■■■■■■■■■■■■■■■■");
		List<Map<String, Object>> customerList = adminDao.selectAllCustomer();
		for(Map<String, Object> map : customerList){
			System.out.println(map.get("CSTMR_ID") + " / " + map.get("CSTMR_PW") + " / "
					+ map.get("CSTMR_NM") + " / " + map.get("CSTMR_HP") + " / "
					+ map.get("CSTMR_BRTHDY") + " / " + map.get("CSTMR_ADRES1") + " / " + map.get("CSTMR_ADRES2"));
		}
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("1.조회\t2.메인으로");
		int num = ScanUtil.nextInt();
		switch(num) {
		case 1: 
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　조회하실 아이디를 입력해주세요　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
			String customer_id = ScanUtil.nextLine();
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　수행하실 선택지를 입력해주세요　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
			System.out.println("1.수정\t2.삭제\t0.목록 >");
			int input = ScanUtil.nextInt();
			
			
			switch(input) {
			case 1:
				update_cus(customer_id);
				break;
				
			case 2:
				int customerdelete = adminDao.deleteCustomer(customer_id);
				if(customerdelete > 0){
					System.out.println("┌─────────────────────────────────────────────┐");
					System.out.println("│　　　　　　　　일반회원 삭제가 완료되었습니다　　　　　　　 │");
					System.out.println("└─────────────────────────────────────────────┘");
				}else{
					System.out.println("┌─────────────────────────────────────────────┐");
					System.out.println("│　　　　　　　　일반회원 삭제가 실패하였습니다　　　　　　　 │");
					System.out.println("└─────────────────────────────────────────────┘");
				}
				break;
			case 0:
				break;
			}
			
		case 2: return adminMain();
		}
		return adminMain();
	}
	
	//일반 회원 수정
	private int update_cus(String customer_id){
		System.out.println("──────────────── 일반회원 정보 수정 ───────────────");
		System.out.print("수정할 비밀번호 입력 > ");		String CSTMR_PW = ScanUtil.nextLine();
		System.out.print("수정할 이름 입력 > ");		String CSTMR_NM = ScanUtil.nextLine();
		System.out.print("수정할 전화번호 입력 > ");		String CSTMR_HP = ScanUtil.nextLine();
		System.out.print("수정할 생년월일 입력 > ");		String CSTMR_BRTHDY = ScanUtil.nextLine();
		System.out.print("수정할 주소1 입력 > ");		String CSTMR_ADRES1 = ScanUtil.nextLine();
		System.out.print("수정할 주소2 입력 > ");		String CSTMR_ADRES2 = ScanUtil.nextLine();
		
		
		Map<String, Object> customerParam = new HashMap<>();
		customerParam.put("CSTMR_PW", CSTMR_PW);
		customerParam.put("CSTMR_NM", CSTMR_NM);
		customerParam.put("CSTMR_HP", CSTMR_HP);
		customerParam.put("CSTMR_BRTHDY", CSTMR_BRTHDY);		
		customerParam.put("CSTMR_ADRES1", CSTMR_ADRES1);	
		customerParam.put("CSTMR_ADRES2", CSTMR_ADRES2);
		customerParam.put("CSTMR_ID", customer_id);
		
		int updateCnt = adminDao.updateCustomer(customerParam);
		
		if(updateCnt > 0){
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　일반회원 정보가 수정되었습니다　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
		}else{
			System.out.println("!!!!!!!!!!!!!! 일반회원 정보수정 실패 !!!!!!!!!!!!!!");
		}
		return View.ADMIN_MAIN;
		}
	
	//식당 정보 제공 및 수정, 삭제
	public int updateRestaurant(){
		System.out.println("■■■■■■■■■■■■■■■■■■ 식당회원 조회 ■■■■■■■■■■■■■■■■■");
		List<Map<String, Object>> restaurantList = adminDao.selectAllRestaurant();
		for(Map<String, Object> map : restaurantList){
			System.out.println(map.get("RSTRNT_ID") + " / " + map.get("RSTRNT_PW") + " / " + map.get("RSTRNT_NM") + " / "
					+ map.get("RSTRNT_TELNO") + " / " + map.get("RSTRNT_ADRES1") + " / " + map.get("RSTRNT_ADRES2"));
		}
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("1.조회\t2.메인으로");
		int num = ScanUtil.nextInt();
		switch(num) {
		
		case 1:
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　조회하실 아이디를 입력해주세요　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
			String restaurant_id = ScanUtil.nextLine();
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　수행하실 선택지를 입력해주세요　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
			System.out.println("1.수정\t2.삭제\t0.목록 >");
			int input = ScanUtil.nextInt();
		
		switch(input) {
		case 1:
			update_res(restaurant_id);
			break;
			
		case 2:
			int restaunrantdelete = adminDao.deleteRestaurant(restaurant_id);
			if(restaunrantdelete > 0){
				System.out.println("┌─────────────────────────────────────────────┐");
				System.out.println("│　　　　　　　　식당회원 삭제가 완료되었습니다　　　　　　　 │");
				System.out.println("└─────────────────────────────────────────────┘");
			}else{
				System.out.println("┌─────────────────────────────────────────────┐");
				System.out.println("│　　　　　　　　식당회원 삭제가 실패하였습니다　　　　　　　 │");
				System.out.println("└─────────────────────────────────────────────┘");
			}
			break;
		case 0:
			break;
		}
		case 2: return adminMain();
		}
		return adminMain();
	}
	
	//식당 수정
	private int update_res(String restaurant_id){
		System.out.println("──────────────── 식당회원 정보 수정 ───────────────");
		System.out.print("수정할 비밀번호 입력 > ");		String RSTRNT_PW = ScanUtil.nextLine();
		System.out.print("수정할 이름 입력 > ");		String RSTRNT_NM = ScanUtil.nextLine();
		System.out.print("수정할 전화번호 입력 > ");		String RSTRNT_TELNO = ScanUtil.nextLine();
		System.out.print("수정할 주소1 입력 > ");		String RSTRNT_ADRES1 = ScanUtil.nextLine();
		System.out.print("수정할 주소2 입력 > ");		String RSTRNT_ADRES2 = ScanUtil.nextLine();
		
		
		Map<String, Object> restaurntParam = new HashMap<>();
		restaurntParam.put("RSTRNT_PW", RSTRNT_PW);
		restaurntParam.put("RSTRNT_NM", RSTRNT_NM);
		restaurntParam.put("RSTRNT_TELNO", RSTRNT_TELNO);
		restaurntParam.put("RSTRNT_ADRES1", RSTRNT_ADRES1);		
		restaurntParam.put("RSTRNT_ADRES2", RSTRNT_ADRES2);	
		restaurntParam.put("RSTRNT_ID", restaurant_id);	
		
		int updateCnt = adminDao.updateRestaurant(restaurntParam);
		
		if(updateCnt > 0){
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　식당회원 정보가 수정되었습니다　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
		}else{
			System.out.println("!!!!!!!!!!!!!! 식당회원 정보수정 실패 !!!!!!!!!!!!!!");
		}
		return View.ADMIN_MAIN;
		}
	
	//배달대행업체 정보 제공 및 수정, 삭제
	public int updateRider_MNG(){
		System.out.println("■■■■■■■■■■■■■■■■ 배달대행업체 조회 ■■■■■■■■■■■■■■■■");
		List<Map<String, Object>> rider_mngList = adminDao.selectAllRiderMng();
		for(Map<String, Object> map : rider_mngList){
			System.out.println(map.get("MNG_ID") + " / " + map.get("MNG_PW") + " / " + map.get("MNG_NM")
					+ " / " + map.get("MNG_TELNO"));
		}
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("1.조회\t2.메인으로");
		int num = ScanUtil.nextInt();
		switch(num) {
		
		case 1:
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　조회하실 아이디를 입력해주세요　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
			String ridermng_id = ScanUtil.nextLine();
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　수행하실 선택지를 입력해주세요　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
			System.out.println("1.수정\t2.삭제\t0.목록 >");
			int input = ScanUtil.nextInt();
		
		switch(input) {
		case 1:
			update_mng(ridermng_id);
			break;
			
		case 2:
			int ridermngdelete = adminDao.deleteRider_MNG(ridermng_id);
			if(ridermngdelete > 0){
				System.out.println("┌─────────────────────────────────────────────┐");
				System.out.println("│　　　　　　　　배달업체 삭제가 완료되었습니다　　　　　　　 │");
				System.out.println("└─────────────────────────────────────────────┘");
			}else{
				System.out.println("┌─────────────────────────────────────────────┐");
				System.out.println("│　　　　　　　　배달업체 삭제가 실패하였습니다　　　　　　　 │");
				System.out.println("└─────────────────────────────────────────────┘");
			}
			break;
		case 0:
			break;
		}
		case 2: return adminMain();
		}
		return adminMain();
	}
	
	//배달대행업체 수정
	private int update_mng(String mng_id){
		System.out.println("──────────────── 배달업체 정보 수정 ───────────────");
		System.out.print("수정할 비밀번호 입력 > ");		String MNG_PW = ScanUtil.nextLine();
		System.out.print("수정할 이름 입력 > ");		String MNG_NM = ScanUtil.nextLine();
		System.out.print("수정할 전화번호 입력 > ");		String MNG_TELNO = ScanUtil.nextLine();
		
		Map<String, Object> riders_mngParam = new HashMap<>();
		riders_mngParam.put("MNG_PW", MNG_PW);
		riders_mngParam.put("MNG_NM", MNG_NM);
		riders_mngParam.put("MNG_TELNO", MNG_TELNO);
		riders_mngParam.put("MNG_ID",  mng_id);	
		
		int updateCnt = adminDao.updateRiderMng(riders_mngParam);
		
		if(updateCnt > 0){
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　배달업체 정보가 수정되었습니다　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
		}else{
			System.out.println("!!!!!!!!!!!!!! 배달업체 정보수정 실패 !!!!!!!!!!!!!!");
		}
		return View.ADMIN_MAIN;
		}
	
	

	
	
	
//	public static void main(String[] args) {
//		AdminService adminServce= new AdminService();
//		adminServce.updateRiders();
//		
//	}
	
	
}
