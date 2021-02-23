package dao;

import util.JDBCUtil;

public class UserDao {
	
	private UserDao() {}
	private static UserDao instance;
	public static UserDao getInstance() {
		if(instance == null) {
			instance = new UserDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
}
