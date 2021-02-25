package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.CustomerDao;
import dao.ShoppingDao;
import util.ScanUtil;
import util.View;

public class ShoppingService {

	private static ShoppingService instance;
	public static ShoppingService getInstance() {
		if(instance == null) {
			instance = new ShoppingService();
		}
		return instance;
	}
	
	private ShoppingDao shoppingDao = ShoppingDao.getInstance();
	private CustomerDao customerDao = CustomerDao.getInstance();
	static String customId, customPw;
	static int restNum;
	
	   //회원 지역구 식당 출력
	   public int viewRstList() {

	      Map<String, Object> selectCustomer = customerDao.selectMyInfo((String) Controller.loginUser.get("CSTMR_ID"), (String) Controller.loginUser.get("CSTMR_PW"));
	      customId = (String)selectCustomer.get("CSTMR_ID");
	      String gu = (String)selectCustomer.get("CSTMR_ADRES1");
	      
	      String location = gu;
	      
	      List<Map<String, Object>> rstList = shoppingDao.matchLocaiton(location);
	      
	      System.out.println("■■■■■■■■■■■■■■ ▶ " + gu + " 식당 리스트 ◀ ■■■■■■■■■■■■■■");
	      for (Map<String, Object> rstlists : rstList){
	      System.out.println("식당 번호 : " + rstlists.get("RSTRNT_NO"));
	      System.out.println("식당명 : " + rstlists.get("RSTRNT_NM"));
	      System.out.println("전화번호 : " + rstlists.get("RSTRNT_TELNO"));
	      System.out.println("주소 : " + rstlists.get("RSTRNT_ADRES1"));
	      System.out.println("주소2 : " + rstlists.get("RSTRNT_ADRES2"));
	      System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");

	      }
	      System.out.println("1.식당 조회  2.메인으로 >");
	      
	      int input = ScanUtil.nextInt();
	      
	      switch(input) {
	      case 1: return View.VIEW_REST; 
	      case 2: return View.CUSTOM_MAIN;
	      }
	      
	      return View.CUSTOM_MAIN;
	   }
	
	//식당 기본 정보 출력
	public int restInfo() {
		System.out.println("┌─────────────────────────────────────────────┐");
		System.out.println("│　　　　　　　　조회할 식당번호를 입력해주세요　　　　　　　 │");
		System.out.println("└─────────────────────────────────────────────┘");
		int restNo = ScanUtil.nextInt();

		Map<String, Object> restInfo = shoppingDao.selectRest(restNo);
		System.out.println("                << " + restInfo.get("RSTRNT_NM") + " >>");
		System.out.println("식당유형 : " + restInfo.get("FDTY_NM"));
		System.out.println("전화번호 : " + restInfo.get("RSTRNT_TELNO"));
		System.out.println("주소 : " + restInfo.get("RSTRNT_ADRES1") + " " + restInfo.get("RSTRNT_ADRES2"));
		System.out.println("───────────────────────────────────────────────");
		while(true) {
			System.out.println("1.메뉴보기  2.리뷰조회 3.식당 리스트로 돌아가기");
			int input = ScanUtil.nextInt();
			
			switch(input) {
			case 1 : List<Map<String, Object>> selectMenus = shoppingDao.selectMenu(restNo);
						for (Map<String, Object> selectMenu : selectMenus) {
							System.out.print("메뉴코드 : " + selectMenu.get("MENU_ID") + " / ");
							System.out.print(selectMenu.get("MENU_NM") + " / ");
							System.out.println(selectMenu.get("MENU_PRICE") + "원");
							System.out.println("───────────────────────────────────────────────");
						}
						System.out.println("1.장바구니에 담기\t2.식당 정보로 돌아가기");
						int num = ScanUtil.nextInt();
						switch(num) {
						case 1: addCart();
						case 2: break;
						}
						break;
						
			case 2 : List<Map<String, Object>> reviewLists = shoppingDao.shopReview(restNo);
						for (Map<String, Object> reviewList : reviewLists) {
							System.out.println("리뷰번호 : " + reviewList.get("REVIEW_ID") + " / 별점 : " + reviewList.get("REVIEW_SCORE"));
							System.out.println("리뷰내용 : " + reviewList.get("REVIEW_CONTENT"));
							System.out.println("───────────────────────────────────────────────");
						}
						System.out.println("1.식당 정보로 돌아가기");
						int num2 = ScanUtil.nextInt();
						switch(num2) {case 1: break;}
						break;
			case 3: return View.MATCH_LOCATION;
			}
		}
	}
	
	//장바구니 추가
	public void addCart() {
		System.out.print("장바구니에 추가할 메뉴의 코드를 입력해주세요 > ");
		String menuNo = ScanUtil.nextLine();
		System.out.print("수량을 입력해주세요. > ");
		int qty = ScanUtil.nextInt();
		
		Map<String, Object> param = new HashMap<>();
		param.put("CART_QTY", qty);
		param.put("CSTMR_ID", customId);
		param.put("MENU_ID", menuNo);

		
		int result = shoppingDao.addMyCart(param);
		
		if(0 < result){
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　장바구니 추가가 완료되었습니다　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
		}else{
			System.out.println("┌─────────────────────────────────────────────┐");
			System.out.println("│　　　　　　　　장바구니 삭제가 완료되었습니다　　　　　　　 │");
			System.out.println("└─────────────────────────────────────────────┘");
		}
	}
	//식당 메뉴 조회
//	public int showMenu(){
//		List<Map<String, Object>> selectMenus = shoppingDao.selectMenu(restNum);
//		System.out.println("──────────────── 메 뉴 판 ────────────────");
//		
//		for (Map<String, Object> selectMenu : selectMenus) {
//			System.out.print("메뉴코드 : " + selectMenu.get("MENU_ID") + " / ");
//			System.out.print(selectMenu.get("MENU_NM") + " / ");
//			System.out.println(selectMenu.get("MENU_PRICE") + "원");
//		}
	//식당 리뷰 조회
	
	//장바구니에 메뉴 추가
	
//	public static void main(String[] args) {
//		
//		ShoppingService s = ShoppingService.getInstance();
//		s.restInfo();
//	}
	
}
