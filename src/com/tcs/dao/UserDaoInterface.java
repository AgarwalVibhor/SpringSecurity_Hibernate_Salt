package com.tcs.dao;

import com.tcs.model.User;
import com.tcs.model.UserRole;

public interface UserDaoInterface {
	
	public String insert(User user);
	
	public int insertUserRole(UserRole userRole);
	
	public User findUserByUsername(String username);
	

}
