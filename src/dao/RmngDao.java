package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import util.JDBCUtil;

public class RmngDao {
	
	private RmngDao(){}
	
	private static RmngDao instance;
	
	public static RmngDao getInstance(){
		if(instance == null){
			instance = new RmngDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	//배달 대행 업체 회원가입
	public int insertRmng(Map<String, Object> param){

		String sql = "insert into rider_mng values (?, ?, ?, ?)";

		List<Object> p = new ArrayList<>();
		p.add(param.get("MNG_ID"));
		p.add(param.get("MNG_PW"));
		p.add(param.get("MNG_NM"));
		p.add(param.get("MNG_TELNO"));

		return jdbc.update(sql, p);
	}

	//배달 대행 업체 로그인
	public Map<String, Object> selectRmng(String userId, String password) {

		String sql = "select * from rider_mng where mng_id = ? and mng_pw = ?";
		List<Object> param = new ArrayList<>();
		param.add(userId);
		param.add(password);
		
		return jdbc.selectOne(sql, param);
	}
	
	//라이더 목록 조회
	public List<Map<String, Object>> ridersView(){

		String sql = "select * from riders";
		return jdbc.selectList(sql);
	}
	
	//라이더 삭제
	public int deleteRiders(Map<String, Object> param){

		String sql = "delete from riders where rd_nm = ?";

		List<Object> p = new ArrayList<>();
		p.add(param.get("RD_NM"));

		return jdbc.update(sql, p);
	}
	
}
