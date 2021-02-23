package service;

public class UserService {
	
	private UserService() {}
	private static UserService instance;
	public static UserService getInstance() {
		if(instance == null) {
			instance = new UserService();
		}
		return instance;
	}
	
	/*
	 * private UserDao = UserDao.getInstance();
	 * 
	 * public void join(){} // 회원가입
	 */	 
	public void login(){} // 로그인
}
