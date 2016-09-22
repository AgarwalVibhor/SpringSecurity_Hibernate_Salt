package com.tcs.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.dao.UserDaoInterface;
import com.tcs.model.User;
import com.tcs.model.UserRole;

@Service("customService")
public class MyCustomService implements UserDetailsService {
	
	@Autowired
	private UserDaoInterface dao;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		User user = dao.findUserByUsername(username);
		Set<UserRole> userRoles = user.getUserRoles();
		List<GrantedAuthority> authorities = buildUserAuthority(userRoles);
		UserDetails userDetails = buildUserForAuthentication(user, authorities);

		return userDetails;
	}
	
	public List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles)
	{
		Set<GrantedAuthority> roles = new HashSet<>();
		Iterator<UserRole> itr = userRoles.iterator();
		while(itr.hasNext())
		{
			UserRole userRole = itr.next();
			roles.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(roles);
		return authorities;
	}
	
	public UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities)
	{
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), 
				user.isEnabled(), true, true, true, authorities);
	}
	
	/*@Transactional(readOnly = true)
	public String getHashedPassword(String username, String password)
	{
		String hashedPassword = dao.getHashedPassword(username, password);
		return hashedPassword;
	} */

}
