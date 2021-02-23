package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class CustomerDao {
	private CustomerDao(){}
	private static CustomerDao instance;
	public static CustomerDao getInstance(){
		if(instance == null){
			instance = new CustomerDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public Map<String, Object> selectUser(String cstmr_id, String cstmr_pw) {
		String sql = "SELECT * FROM CUSTOMER WHERE CSTMR_ID = ? AND CSTMR_PW = ?";
		List<Object> param = new ArrayList<>();
		param.add(cstmr_id);
		param.add(cstmr_pw);
		return jdbc.selectOne(sql, param);
	}
	
	public int insertUser(Map<String, Object> param){
		String sql = "INSERT INTO CUSTOMER VALUES (?,?,?,?,?,?,?,?,0)";
		
		List<Object> p = new ArrayList<>();
		p.add(param.get("CSTMR_ID"));
		p.add(param.get("CSTMR_PW"));
		p.add(param.get("CSTMR_NM"));
		p.add(param.get("CSTMR_HP"));
		p.add(param.get("CSTMR_BRTHDY"));
		p.add(param.get("CSTMR_ADRES1"));
		p.add(param.get("CSTMR_ADRES2"));

		return jdbc.update(sql,p);
	}


	public Map<String, Object> selectMyInfo(String cstmr_id, String cstmr_pw) {
		// 내 정보 조회
		String sql = "SELECT * FROM CUSTOMER WHERE CSTMR_ID = ? AND CSTMR_PW = ?";
		List<Object> param = new ArrayList<>();
		param.add(cstmr_id);
		param.add(cstmr_pw);
		return jdbc.selectOne(sql, param);
	}
	
	public List<Map<String, Object>> selectMyOrder(String cstmr_id) {
		// 주문내역 조회
		String sql = "SELECT * FROM ORDERLIST WHERE CSTMR_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(cstmr_id);
		return jdbc.selectList(sql, param);
	}
	
	public List<Map<String, Object>> selectMyReview(String cstmr_id) {
		// 리뷰내역 조회
		String sql = "SELECT * FROM REVIEW A, ORDERLIST B, CUSTOMER C WHERE A.ORDER_ID = B.ORDER_ID AND B.CSTMR_ID = C.CSTMR_ID AND C.CSTMR_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(cstmr_id);
		return jdbc.selectList(sql, param);
	}
	
	public int updateMyInfo(Map<String, Object> param){
		// 내 정보 수정
		String sql = "UPDATE CUSTOMER SET CSTMR_PW = ?, CSTMR_NM = ?,"
				+ " CSTMR_HP = ?, CSTMR_BRTHDY = ?,"
				+ "CSTMR_ADRES1 = ?, CSTMR_ADRES2 = ? WHERE CSTMR_ID = ?";
		
		List<Object> p = new ArrayList<>();
		p.add(param.get("CSTMR_PW"));
		p.add(param.get("CSTMR_NM"));
		p.add(param.get("CSTMR_HP"));
		p.add(param.get("CSTMR_BRTHDY"));
		p.add(param.get("CSTMR_ADRES1"));
		p.add(param.get("CSTMR_ADRES2"));
		p.add(param.get("CSTMR_ID"));

		return jdbc.update(sql,p);
	}
	
	public int updateMyCash(int cstmr_cash, String cstmr_id){
		// 사이버 머니 충전
		String sql = "UPDATE CUSTOMER SET CSTMR_CASH = CSTMR_CASH + ? WHERE CSTMR_ID = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(cstmr_cash);
		param.add(cstmr_id);

		return jdbc.update(sql,param);
	}
	
	public List<Map<String, Object>>  selectMyCart(String cstmr_id) {
		// 장바구니 조회
		String sql = "SELECT * FROM CART WHERE CSTMR_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(cstmr_id);
		return jdbc.selectList(sql, param);
	}
	
	public int updateMyCartItem(int cash_qty, int cash_id, String cstmr_id){
		// 장바구니 수정
		String sql = "UPDATE CART SET CART_QTY = ? WHERE CART_ID = ? AND CSTMR_ID = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(cash_qty);
		param.add(cash_id);
		param.add(cstmr_id);

		return jdbc.update(sql,param);
	}
	
	public int deleteMyCartItem(int cash_id, String cstmr_id){
		// 장바구니 삭제
		String sql = "DELETE FROM CART WHERE CART_ID = ? AND CSTMR_ID = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(cash_id);
		param.add(cstmr_id);

		return jdbc.update(sql,param);
	}
	
	public int updateMyOrder(String cstmr_id){
		// 주문
		String cashSql = "(SELECT SUM(MENU_PRICE) AS PRICESUM, RSTRNT_ID AS RSTRNTID FROM CART, MENU WHERE CART.CSTMR_ID = ? AND CART.MENU_ID = MENU.MENU_ID)";
		Map<String,Object> map = jdbc.selectOne(cashSql);
		String cstmr_cash = (String) map.get("PRICESUM");
		String rstrnt_id = (String) map.get("RSTRNTID");
		
		String sql = "INSERT INTO ORDERLIST VALUES ("
				+ "(SELECT MAX(NVL(ORDER_ID,0)) + 1 FROM ORDERLIST)"
				+ "?,?,'주문대기',?,"
				+ ",SYSDATE)";
		List<Object> param = new ArrayList<>();
		param.add(cstmr_id);
		param.add(rstrnt_id);
		param.add(cstmr_cash);
		jdbc.update(sql,param);
		
		String sql1 = "INSERT INTO CONTENT(ORDER_ID, MENU_ID, CONTENT_QTY)"
				+ "SELECT (SELECT MAX(NVL(ORDER_ID,0)) FROM ORDERLIST), MENU_ID, CART_QTY WHERE CSTMR_ID = ?";
		List<Object> param1 = new ArrayList<>();
		param1.add(cstmr_id);
		jdbc.update(sql1,param1);
		
		String sql2 = "UPDATE CUSTOMER SET CSTMR_CASH = CSTMR_CASH - ? WHERE CSTMR_ID = ?";
		List<Object> param2 = new ArrayList<>();
		param2.add(cstmr_cash);
		param2.add(cstmr_id);
		jdbc.update(sql2,param2);
		
		String sql3 = "DELETE FROM CART WHERE CSTMR_ID = ?";
		List<Object> param3 = new ArrayList<>();
		param3.add(cstmr_id);
		jdbc.update(sql3,param3);
		
		return jdbc.update(sql,param3);
	}
	
	
	
	public int insertReview(Map<String, Object> param){
		// 리뷰 작성
		String sql = "INSERT INTO REVIEW VALUES ("
				+ "(SELECT MAX(NVL(REVIEW_ID,0)) + 1 FROM REVIEW)"
				+ "?,?,?,?)";
		
		List<Object> p = new ArrayList<>();
		p.add(param.get("REVIEW_CONTENT"));
		p.add(param.get("RSTRNT_ID"));
		p.add(param.get("ORDER_ID"));
		p.add(param.get("REVIEW_SCORE"));

		return jdbc.update(sql,p);
	}

}
