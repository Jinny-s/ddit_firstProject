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

		String sql = "insert into riders values (?, ?, ?, ?, ?, ?)";

		List<Object> p = new ArrayList<>();
		p.add(param.get("RD_ID"));
		p.add(param.get("RD_PW"));
		p.add(param.get("RD_NM"));
		p.add(param.get("RD_ADRES1"));
		p.add(param.get("RD_ADRES2"));
		p.add(param.get("RD_TELNO"));

		return jdbc.update(sql, p);
	}
	
	//라이더 로그인, 정보 조회
	public Map<String, Object> selectRider(String loginId, String loginPw) {

		String sql = "select * from riders where rd_id = ? and rd_pw = ?";
		List<Object> param = new ArrayList<>();
		param.add(loginId);
		param.add(loginPw);
		return jdbc.selectOne(sql, param);
	}
	
	//라이더 정보 수정
	public int riderUpdate(String riderId, Map<String, Object> param) {
		String sql = "UPDATE RIDERS SET ";

		List<Object> p = new ArrayList<>();
			if (!param.get("RD_PW").equals("")) {
			sql += " RD_PW = ?,";
			p.add(param.get("RD_PW"));
		}
			if (!param.get("RD_NM").equals("")) {
			sql += " RD_NM = ?,";
			p.add(param.get("RD_NM"));
		}
	
		if (!param.get("RD_ADRES1").equals("")) {
			sql += " RD_ADRES1 = ?,";
			p.add(param.get("RD_ADRES1"));
		}

		if (!param.get("RD_ADRES2").equals("")) {
			sql += " RD_ADRES2 = ?,";
			p.add(param.get("RD_ADRES2"));
			}

		if (!param.get("RD_TELNO").equals("")) {
			sql += " RD_TELNO = ?,";
			p.add(param.get("RD_TELNO"));
		}
		
		sql = sql.substring(0, sql.length() - 1);

			
		sql += " WHERE RD_ID = ?";
		p.add(riderId);
			
		return jdbc.update(sql, p);
	}
	
	//식당 오더 리스트 조회
	public List<Map<String, Object>> viewOrderList(String status, String gu) {
		String sql = "SELECT A.ORDER_ID, A.CSTMR_ID, A.RSTRNT_ID, A.ORDER_STATUS, A.ORDER_COST, A.ORDER_DATE, B.CSTMR_ADRES1, B.CSTMR_ADRES2"   
					  + " FROM ORDERLIST A, CUSTOMER B"
					  + " WHERE A.CSTMR_ID = B.CSTMR_ID"
					  + " AND A.ORDER_STATUS = ?"
					  + " AND B.CSTMR_ADRES1 = ?";
		List<Object> param = new ArrayList<>();
		param.add(status);
		param.add(gu);
		return jdbc.selectList(sql, param);
	}
	
	//매칭 Insert / orderStatus "배달중" 으로 변경
	public int matchinInsert(Map<String, Object> param) {
		
		String sql = "INSERT INTO MATCHIN(ORDER_ID, RD_ID)" 
					+ " SELECT A.ORDER_ID, B.RD_ID"
					+ " FROM ORDERLIST A, RIDERS B"
					+ " WHERE A.ORDER_ID = ?"
					+ " AND B.RD_ID = ?";

		List<Object> p = new ArrayList<>();
		p.add(param.get("A.ORDER_ID"));
		p.add(param.get("B.RD_ID"));

		return jdbc.update(sql, p);
	}
	
	//오더리스트 STATUS "배달중" 으로 변경
	public int statusUpdateIng(Map<String, Object> param) {
		
		String sql = "UPDATE ORDERLIST SET ORDER_STATUS = ? WHERE ORDER_ID = ?";

		List<Object> p = new ArrayList<>();
		p.add(param.get("ORDER_STATUS"));
		p.add(param.get("ORDER_ID"));

		return jdbc.update(sql, p);
	}
	
	//오더리스트 STATUS "배달완료" 로 변경
	public int statusUpdateDone(Map<String, Object> param) {
		
		String sql = "UPDATE ORDERLIST SET ORDER_STATUS = ? WHERE ORDER_ID = ?";

		List<Object> p = new ArrayList<>();
		p.add(param.get("ORDER_STATUS"));
		p.add(param.get("ORDER_ID"));

		return jdbc.update(sql, p);
	}
	
	//매칭 삭제
	public int matchingDelete(Map<String, Object> param) {
		String sql = "DELETE FROM MATCHIN WHERE ORDER_ID = ?";

		List<Object> p = new ArrayList<>();
		p.add(param.get("ORDER_ID"));
			
		return jdbc.update(sql, p);
	}
	
}
