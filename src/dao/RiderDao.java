package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class RiderDao {
	
	private RiderDao(){}
	
	private static RiderDao instance;
	
	public static RiderDao getInstance(){
		if(instance == null){
			instance = new RiderDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	//라이더 회원가입
	public int insertRider(Map<String, Object> param){

		String sql = "insert into riders values (?, ?, ?, ?, ?)";

		List<Object> p = new ArrayList<>();
		p.add(param.get("RD_ID"));
		p.add(param.get("RD_PW"));
		p.add(param.get("RD_NM"));
		p.add(param.get("RD_ADRES1"));
		p.add(param.get("RD_ADRES2"));

		return jdbc.update(sql, p);
	}
	
	//라이더 로그인
	public Map<String, Object> loginRider(String LoginId, String LoginPw) {

		String sql = "select * from riders where rd_id = ? and rd_pw = ?";
		List<Object> param = new ArrayList<>();
		param.add(LoginId);
		param.add(LoginPw);
		
		return jdbc.selectOne(sql, param);
	}
	
	//라이더 정보 조회
	public List<Map<String, Object>> viewRiderInfo(String rd_id) {
		String sql = "SELECT * FROM RIDERS WHERE RD_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(rd_id);
		return jdbc.selectList(sql, param);
	}
	
	//라이더 정보 수정
	public int RiderUpdate(Map<String, Object> param) {
		
		String sql = "UPDATE RIDERS SET RD_NM = ?, RD_ADRES1 = ?, RD_ADRES2 = ? WHERE RD_ID = ? and RD_PW = ?";

		List<Object> p = new ArrayList<>();
		p.add(param.get("RD_NM"));
		p.add(param.get("RD_ADRES1"));
		p.add(param.get("RD_ADRES2"));
		p.add(param.get("RD_ID"));
		p.add(param.get("RD_PW"));

		return jdbc.update(sql, p);
	}
	
	//식당 오더 리스트 조회
	public List<Map<String, Object>> viewOrderList(String status) {
		String sql = "SELECT A.ORDER_ID, A.CSTMR_ID, A.RSTRNT_ID, A.ORDER_STATUS, A.ORDER_COST, A.ORDER_DATE, B.CSTMR_ADRES1, B.CSTMR_ADRES2"   
					  + " FROM ORDERLIST A, CUSTOMER B, RESTAURANT C"
					  + " WHERE A.CSTMR_ID = B.CSTMR_ID"
					  + " AND A.RSTRNT_ID = C.RSTRNT_ID"
					  + " AND A.ORDER_STATUS = ?";
		List<Object> param = new ArrayList<>();
		param.add(status);
		return jdbc.selectList(sql, param);
	}
	
	//오더리스트 STATUS 변경하기
	public int statusUpdate(Map<String, Object> param) {
		
		String sql = "UPDATE ORDERLIST SET ORDER_STATUS = ? WHERE ORDER_ID = ?";

		List<Object> p = new ArrayList<>();
		p.add(param.get("ORDER_STATUS"));
		p.add(param.get("ORDER_ID"));

		return jdbc.update(sql, p);
	}
	
	//주문 선택
	

	
	
}
