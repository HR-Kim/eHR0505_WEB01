package kr.co.ehr.user.service;

import java.util.List;

import kr.co.ehr.cmn.DTO;

public interface UserDao {
	
	public int deleteAll();
	public int update(User user);
	public List<User> retrieve(Search vo);
	public List<User> getAll();
	public int count(String uId);
	public int deleteUser(User user);
	public int add(User user);
	public User get(String id);
}
