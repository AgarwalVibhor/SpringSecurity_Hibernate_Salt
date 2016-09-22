package com.tcs.business;

import com.tcs.model.User;
import com.tcs.model.UserRole;

public interface UserBusinessInterface {
	
	public String insert(User user);
	
	public int insertUserRole(UserRole userRole);

}
