package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class ShoppingDao {

	private ShoppingDao() {}
	private static ShoppingDao instance;
	public static ShoppingDao getInstance() {
		if(instance == null) {
			instance = new ShoppingDao();
		}
		return instance;
	}

	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	 //회원 지역에 맞는 식당 리스트 조회
	 public List<Map<String, Object>> matchLocaiton(String location) {

		 String sql = "SELECT A.*"
	               + " FROM RESTAURANT A"
	               + " WHERE A.RSTRNT_ADRES1 = ?";

	      List<Object> param = new ArrayList<>();
	      param.add(location);
	      
	      return jdbc.selectList(sql, param);
	   }
	
	//1개의 식당 조회 (식당 기본 정보 출력) 1.메뉴조회 2.리뷰조회
	public Map<String, Object> selectRest(int rstrntNo) {
		String sql = "SELECT A.*, B.FDTY_NM FROM RESTAURANT A, FOODTYPE B"
				+ " WHERE RSTRNT_NO = ? AND A.FDTY_GU = B.FDTY_GU";
		List<Object> p = new ArrayList<>();
		p.add(rstrntNo);
		return jdbc.selectOne(sql, p);
	}
	
	//메뉴조회
	public List<Map<String, Object>> selectMenu(int rstrntNo) {
		String sql = "SELECT A.* FROM MENU A, RESTAURANT B WHERE A.RSTRNT_ID = B.RSTRNT_ID AND B.RSTRNT_NO = ?";
		List<Object> p = new ArrayList<>();
		p.add(rstrntNo);
		return jdbc.selectList(sql, p);
	}
	
	//리뷰조회
	public List<Map<String, Object>> shopReview(int rstrntNo) {
		String sql = "SELECT A.* FROM REVIEW A, RESTAURANT B WHERE A.RSTRNT_ID = B.RSTRNT_ID AND RSTRNT_NO = ?";
		List<Object> p = new ArrayList<>();
		p.add(rstrntNo);
		return jdbc.selectList(sql, p);
	}	
		
	//장바구니에 추가
	public int addMyCart(Map<String, Object> param){
		// 장바구니 수정
		
		String sql = "INSERT INTO CART VALUES ("
				+ "CART_SEQ.NEXTVAL"
				+ ",?,?,?)";
		
		List<Object> p = new ArrayList<>();
		p.add(param.get("CART_QTY"));
		p.add(param.get("CSTMR_ID"));
		p.add(param.get("MENU_ID"));

		return jdbc.update(sql,p);
	}
}
