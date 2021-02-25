package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;
import util.ScanUtil;

public class AdminDao {

	/*
	 * 로그인, 회원 조회 및 삭제, 식당조회 및 삭제
	 * 라이더스 조회 및 삭제, 배달업체 조회 및 삭제
	 */
	
	private AdminDao(){}
	private static AdminDao instance;
	public static AdminDao getInstance(){
		if(instance == null){
			instance = new AdminDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	
	public Map<String, Object> loginAdmin(String userId, String password){
		String sql = "select * from ADMINISTRATOR where ADM_ID = ? and ADM_PW = ?";
		List<Object> param = new ArrayList<>();
		param.add(userId);
		param.add(password);
		
		return jdbc.selectOne(sql, param);
	}
	
	public List<Map<String, Object>> selectAllAdmin(){
		String sql = "select * from ADMINISTRATOR";
		return jdbc.selectList(sql); 
	}
	
	public List<Map<String, Object>> selectAllCustomer(){
		String sql = "SELECT * FROM CUSTOMER";		
		return jdbc.selectList(sql);
	}
	
	public List<Map<String, Object>> selectAllRestaurant(){
		String sql = "SELECT * FROM RESTAURANT";
		return jdbc.selectList(sql);
	}
	
	public List<Map<String, Object>> selectAllRiderMng(){
		String sql = "SELECT * FROM RIDER_MNG";		
		return jdbc.selectList(sql);
	}
	
	public List<Map<String, Object>> selectAllRiders() {
		String sql = "SELECT * FROM RIDERS";
		return jdbc.selectList(sql);
	}
	
	public int updateRiders(Map<String, Object> param){
//		String sql = "UPDATE RIDERS SET RD_PW =?, RD_NM = ?, RD_ADRES1 = ?, RD_ADRES2 = ?, RD_TELNO = ? WHERE RD_ID = ? ";
//		
//
//		List<Object> p = new ArrayList<>();
//		p.add(param.get("RD_PW"));
//		p.add(param.get("RD_NM"));
//		p.add(param.get("RD_ADRES1"));
//		p.add(param.get("RD_ADRES2"));
//		p.add(param.get("RD_TELNO"));
//		p.add(param.get("RD_ID"));
//		
//		return jdbc.update(sql, p);
		
		String sql = "UPDATE RIDERS SET ";

		List<Object> p = new ArrayList<>();

		for (String key : param.keySet()) {
			Object value = param.get(key);
			if (!value.equals("")) {
				p.add(value);
				sql = sql + " " + key + "= ?,";
			}
		}
		
		sql = sql.substring(0, sql.length() - 1);

		sql += " WHERE RD_ID = ?";
		p.add(param.get("RD_ID"));

		return jdbc.update(sql, p);
		
	}
	
	public int updateRestaurant(Map<String, Object> param){
//		String sql = "UPDATE RESTAURANT SET RSTRNT_PW =?, RSTRNT_NM = ?, RSTRNT_TELNO = ?, RSTRNT_ADRES1 = ?, RSTRNT_ADRES2 = ? WHERE RSTRNT_ID = ? ";
//		
//		List<Object> p = new ArrayList<>();
//		p.add(param.get("RSTRNT_PW"));
//		p.add(param.get("RSTRNT_NM"));
//		p.add(param.get("RSTRNT_TELNO"));
//		p.add(param.get("RSTRNT_ADRES1"));
//		p.add(param.get("RSTRNT_ADRES2"));
//		p.add(param.get("RSTRNT_ID"));
//		
//		return jdbc.update(sql, p);
		
		String sql = "UPDATE RESTAURANT SET ";

		List<Object> p = new ArrayList<>();

		for (String key : param.keySet()) {
			Object value = param.get(key);
			if (!value.equals("")) {
				p.add(value);
				sql = sql + " " + key + "= ?,";
			}
		}
		
		sql = sql.substring(0, sql.length() - 1);

		sql += " WHERE RSTRNT_ID = ?";
		p.add(param.get("RSTRNT_ID"));

		return jdbc.update(sql, p);
		
	}
	
	public int updateRiderMng(Map<String, Object> param){
//		String sql = "UPDATE RIDER_MNG SET MNG_PW =?, MNG_NM = ?, MNG_TELNO = ? WHERE MNG_ID = ? ";
//		
//		List<Object> p = new ArrayList<>();
//		p.add(param.get("MNG_PW"));
//		p.add(param.get("MNG_NM"));
//		p.add(param.get("MNG_TELNO"));
//		p.add(param.get("MNG_ID"));
//		
//		return jdbc.update(sql, p);
		
		String sql = "UPDATE RIDER_MNG SET ";

		List<Object> p = new ArrayList<>();

		for (String key : param.keySet()) {
			Object value = param.get(key);
			if (!value.equals("")) {
				p.add(value);
				sql = sql + " " + key + "= ?,";
			}
		}
		
		sql = sql.substring(0, sql.length() - 1);

		sql += " WHERE MNG_ID = ?";
		p.add(param.get("MNG_ID"));

		return jdbc.update(sql, p);
	}
	
	public int updateCustomer(Map<String, Object> param){
//		String sql = "UPDATE CUSTOMER SET CSTMR_PW = ?, CSTMR_NM = ?, CSTMR_HP = ?, CSTMR_BRTHDY = ?, CSTMR_ADRES1 = ?,"
//				+ " CSTMR_ADRES2 = ?, CSTMR_CASH = ? WHERE CSTMR_ID = ? ";
//		
//		List<Object> p = new ArrayList<>();
//		p.add(param.get("CSTMR_PW"));
//		p.add(param.get("CSTMR_NM"));
//		p.add(param.get("CSTMR_HP"));
//		p.add(param.get("CSTMR_BRTHDY"));
//		p.add(param.get("CSTMR_ADRES1"));
//		p.add(param.get("CSTMR_ADRES2"));
//		p.add(param.get("CSTMR_CASH"));
//		p.add(param.get("CSTMR_ID"));
//		
//		return jdbc.update(sql, p);
		
		String sql = "UPDATE CUSTOMER SET ";

		List<Object> p = new ArrayList<>();

		for (String key : param.keySet()) {
			Object value = param.get(key);
			if (!value.equals("")) {
				p.add(value);
				sql = sql + " " + key + "= ?,";
			}
		}
		
		sql = sql.substring(0, sql.length() - 1);

		sql += " WHERE CSTMR_ID = ?";
		p.add(param.get("CSTMR_ID"));

		return jdbc.update(sql, p);
	}
	
	public int deleteRiders(String rid){
		String sql = "DELETE FROM RIDERS WHERE RD_ID = ?";
		String sql2 = "DELETE FROM MATCHIN WHERE RD_ID = ?";
		
		List<Object> p = new ArrayList<>();
		p.add(rid);
		jdbc.update(sql2, p);
		
		return jdbc.update(sql, p);
	}
	
	public int deleteCustomer(String cid){
		String sql = "DELETE FROM CUSTOMER WHERE CSTMR_ID = ?";
		
		List<Object> p = new ArrayList<>();
		p.add(cid);
		
		
		return jdbc.update(sql, p);
	}
	
	public int deleteRestaurant(String resid){
		String sql = "DELETE FROM RESTAURANT WHERE RSTRNT_ID = ?";
		
		List<Object> p = new ArrayList<>();
		p.add(resid);
		
		
		return jdbc.update(sql, p);
	}
	
	public int deleteRider_MNG(String mngid){
		String sql = "DELETE FROM RIDER_MNG WHERE MNG_ID = ?";
		
		List<Object> p = new ArrayList<>();
		p.add(mngid);
		
		
		return jdbc.update(sql, p);
	}
	
	public static void main(String[] args) {
		
		
		AdminDao adminDao = new AdminDao();
		
		System.out.println("AdminList :" + adminDao.selectAllAdmin());
		System.out.println("CustomerList :" + adminDao.selectAllCustomer());
		System.out.println("RestaurantList :" + adminDao.selectAllRestaurant());
		System.out.println("RiderMngtList :" + adminDao.selectAllRiderMng());
		System.out.println("RiderstList :" + adminDao.selectAllRiders());
		
		Map<String, Object> ridersParam = new HashMap<>();
		ridersParam.put("RD_PW", "변경비밀번호");
		ridersParam.put("RD_NM", "변경이름");
		ridersParam.put("RD_ADRES1", "변경주소1");
		ridersParam.put("RD_ADRES2", "변경주소2");
		ridersParam.put("RD_ID", "khs");		
		
		
		System.out.println("updateRidersCount : " + adminDao.updateRiders(ridersParam));
		
		
		Map<String, Object> restaurantParam = new HashMap<>();
		restaurantParam.put("RSTRNT_PW", "변경비밀번호");
		restaurantParam.put("RSTRNT_NM", "변경 이름");
		restaurantParam.put("RSTRNT_TELNO", "변경 전화번호");
		restaurantParam.put("RSTRNT_ADRES1", "변경 주소 1");
		restaurantParam.put("RSTRNT_ADRES2", "변경 주소 2");
		restaurantParam.put("RSTRNT_ID", "AAA");
			
		System.out.println("updateRestaurantCount : " + adminDao.updateRestaurant(restaurantParam));
		 
		Map<String, Object> customerParam = new HashMap<>();
		customerParam.put("CSTMR_PW", "변경비밀번호");
		customerParam.put("CSTMR_NM", "변경 이름");
		customerParam.put("CSTMR_HP", "변경 전화번호");
		customerParam.put("CSTMR_BRTHDY", "변경 생일");
		customerParam.put("CSTMR_ADRES1", "변경 주소 1");
		customerParam.put("CSTMR_ADRES2", "변경 주소 2");
		customerParam.put("CSTMR_CASH", 20);
		customerParam.put("CSTMR_ID", "AAA");
			
		System.out.println("updateCustomerCount : " + adminDao.updateCustomer(customerParam));
		
		Map<String, Object> ridermngParam = new HashMap<>();
		ridermngParam.put("MNG_PW", "변경비밀번호");
		ridermngParam.put("MNG_NM", "변경 이름");
		ridermngParam.put("MNG_TELNO", "변경 전화번호");
		ridermngParam.put("MNG_ID", "AAA");
	
			
		System.out.println("updateRidermngCount : " + adminDao.updateRiderMng(ridermngParam));
		
	}

	
}
