package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;

public class RestDao {

	private RestDao(){}
	private static RestDao instance;
	public static RestDao getInstance(){
		if(instance == null) {
			instance = new RestDao();
		}
		return instance;
	}

	private JDBCUtil jdbc = JDBCUtil.getInstance();

	//식당 회원가입
	public int insertRest(Map<String, Object> param) {
		String sql = "insert into restaurant values (?, ?, ?, ?, ?, ?, ?)";

		List<Object> p = new ArrayList<>();
		p.add(param.get("RSTRNT_ID"));
		p.add(param.get("RSTRNT_PW"));
		p.add(param.get("RSTRNT_NM"));
		p.add(param.get("RSTRNT_TELNO"));
		p.add(param.get("RSTRNT_ADRES1"));
		p.add(param.get("RSTRNT_ADRES2"));
		p.add(param.get("FDTY_GU"));

		return jdbc.update(sql, p);
	}

	//식당 로그인 , 정보 조회
	public Map<String, Object> selectRest(String rstrntId, String rstrntPw) {
		String sql = "SELECT A.*, B.FDTY_NM FROM RESTAURANT A, FOODTYPE B"
				+ " WHERE RSTRNT_ID = ? AND RSTRNT_PW = ? AND A.FDTY_GU = B.FDTY_GU";
		List<Object> param = new ArrayList<>();
		param.add(rstrntId);
		param.add(rstrntPw);
		return jdbc.selectOne(sql, param);
	}

	//식당 정보 수정
	public int updateRest(String rstrntId, Map<String, Object> param) {
		String sql = "UPDATE RESTAURANT SET ";

		List<Object> p = new ArrayList<>();

		if (!param.get("RSTRNT_PW").equals("")) {
			sql += " RSTRNT_PW = ?,";
			p.add(param.get("RSTRNT_PW"));
		}

		if (!param.get("RSTRNT_NM").equals("")) {
			sql += " RSTRNT_NM = ?,";
			p.add(param.get("RSTRNT_NM"));
		}
		
		if (!param.get("RSTRNT_TELNO").equals("")) {
			sql += " RSTRNT_TELNO = ?,";
			p.add(param.get("RSTRNT_TELNO"));
		}

		if (!param.get("RSTRNT_ADRES1").equals("")) {
			sql += " RSTRNT_ADRES1 = ?,";
			p.add(param.get("RSTRNT_ADRES1"));
		}

		if (!param.get("RSTRNT_ADRES2").equals("")) {
			sql += " RSTRNT_ADRES2 = ?,";
			p.add(param.get("RSTRNT_ADRES2"));
		}
		
		if (!param.get("FDTY_GU").equals("")) {
			sql += " FDTY_GU = ?";
			p.add(param.get("FDTY_GU"));
		}


//		for (String key : param.keySet()) {
//			Object value = param.get(key);
//			if (value != null) {							//value != "" 인지 테스트 필요
//				p.add(value);
//				sql = sql + " " + key + "= ?,";
//			}
//		}
		
//		sql = sql.substring(0, sql.length() - 1);

		sql += " WHERE RSTRNT_ID = ?";
		p.add(rstrntId);

		return jdbc.update(sql, p);

	}

	//주문 내역 조회
	public List<Map<String, Object>> selectOrder(String rstrntId) {
		String sql =
				"SELECT *" +
				" FROM 	RESTAURANT A, ORDERLIST B" +
				" WHERE 	A.RSTRNT_ID = B.RSTRNT_ID AND A.RSTRNT_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(rstrntId);
		return jdbc.selectList(sql, param);
	}
	
	//주문 상세 조회
	public Map<String, Object> detailOrder(String orderId, String rstrntId) {
		String sql = "SELECT * FROM CONTENT A, MENU B WHERE A.MENU_ID = B.MENU_ID AND RSTRNT_ID = ? AND ORDER_ID = ?";
		List<Object> p = new ArrayList<>();
		p.add(rstrntId);
		p.add(orderId);
		return jdbc.selectOne(sql, p);
	}
	
	//주문 접수
	public int updateStatus(String orderStatus, String orderId) {
		String sql = "UPDATE ORDERLIST SET ORDER_STATUS = ? WHERE ORDER_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(orderStatus);
		param.add(orderId);
		return jdbc.update(sql, param);
	}
	
	//소요 시간 : 서비스에서 발송
	
	//메뉴 조회
	public List<Map<String, Object>> selectMenu(String rstrntId) {
		String sql = "SELECT * FROM MENU WHERE RSTRNT_ID = ?";
		List<Object> p = new ArrayList<>();
		p.add(rstrntId);
		return jdbc.selectList(sql, p);
	}
	
	//메뉴 추가
	public int insertMenu(Map<String, Object> param) {
		String sql = "insert into menu values (MENU_SEQ.NEXTVAL, ?, ?, ?)";
		
		List<Object> p = new ArrayList<>();
		p.add(param.get("MENU_NM"));
		p.add(param.get("MENU_PRICE"));
		p.add(Controller.loginUser.get("RSTRNT_ID"));
		
		return jdbc.update(sql, p);
	}
	
	//메뉴 삭제
	public int deleteMenu(int menuId, String rstrntId) {
		String sql = "DELETE FROM MENU WHERE MENU_ID = ? AND RSTRNT_ID = ?";
		List<Object> p = new ArrayList<>();
		p.add(menuId);
		p.add(rstrntId);
		return jdbc.update(sql, p);
		
	}
	
	//리뷰 조회
	public List<Map<String, Object>> selectMyReview(String rstrntId) {
		// 리뷰내역 조회
		String sql = "SELECT * FROM REVIEW WHERE RSTRNT_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(rstrntId);
		return jdbc.selectList(sql, param);
	}
	
	//리뷰 삭제
	public int deleteReview(int reviewId, String rstrntId) {
		String sql = "DELETE FROM REVIEW WHERE REVIEW_ID = ? AND RSTRNT_ID = ?";
		List<Object> p = new ArrayList<>();
		p.add(reviewId);
		p.add(rstrntId);
		return jdbc.update(sql, p);
	}
	
	
	
	
	//	 * 3-1 ~ 3-4 선택 화면
	//	 * 3-1. 내 식당 정보 조회 / 수정
	
	//	 * 3-2. 주문 리스트 조회
	//	 * 	3-2-1. 주문 접수 및 취소 선택
	//	 * 	3-2-2. 소요 시간 전달
	//	 * 3-3. 메뉴 추가 및 삭제
	//	 * 3-4. 리뷰 조회 및 삭제
	
	
}