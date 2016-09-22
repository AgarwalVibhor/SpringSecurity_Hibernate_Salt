package com.tcs.dao;

import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.handler.MyCustomEncoder;
import com.tcs.model.User;
import com.tcs.model.UserRole;

@Repository
public class UserDaoImpl implements UserDaoInterface {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private MyCustomEncoder customEncoder;

	@Override
	@Transactional(readOnly = false)
	public String insert(User user) {
		
		Session session = sessionFactory.getCurrentSession();
		Transaction t = session.beginTransaction();
		
	/*	if(user != null)
		{	
			SecureRandom random = new SecureRandom();
			byte[] bytes = new byte[20];
			random.nextBytes(bytes);
			user.setSalt(bytes.toString());
			
			String password = bytes.toString() + user.getPassword();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String hashedPassword = encoder.encode(password);
			user.setPassword(hashedPassword);
			
		}
		else
		{
			System.out.println("User is null");
		}  */
		
		if(user != null)
		{
			String password = user.getPassword();
			String hashedPassword = customEncoder.encode(password);
			user.setPassword(hashedPassword);
		}
		
		String username = (String) session.save(user);
		t.commit();

		return username;
	}

	@Override
	@Transactional(readOnly = true)
	public User findUserByUsername(String username) {
		
		Session session = sessionFactory.getCurrentSession();
		Transaction t = session.beginTransaction();
		
		Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.ilike("username", username));
		System.out.println("First Query is fired.");
		User user = (User) cr.uniqueResult();
		System.out.println("First Name : " + user.getFname());
		System.out.println("Last Name : " + user.getLname());
		System.out.println("Username : " + user.getUsername());
	//	System.out.println("Salt : " + user.getSalt());
		System.out.println("Hashed Password : " + user.getPassword());
		System.out.println("Enabled : " + user.isEnabled());
		System.out.println("Second Query is fired.");
		Iterator<UserRole> itr = user.getUserRoles().iterator();
		while(itr.hasNext())
		{
			UserRole userRole = itr.next();
			System.out.println(userRole.getRole());
		}
		t.commit();
		
		return user;
	}

/*	@Override
	@Transactional(readOnly = true)
	public String getHashedPassword(String username, String password) {
		
		Session session = sessionFactory.getCurrentSession();
		Transaction t = session.beginTransaction();
		
		Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.ilike("username", username));
		Projection p = Projections.property("password_salt");
		cr.setProjection(p);
		String salt = (String) cr.uniqueResult();
		System.out.println("Salt in Dao : " + salt);
		t.commit();
		
		String newPassword = salt + password;
		System.out.println("Joint Password : " + newPassword);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hashedPassword = encoder.encode(newPassword);
		
		return hashedPassword;
	}  */

	@Override
	@Transactional(readOnly = false)
	public int insertUserRole(UserRole userRole) {
		
		Session session = sessionFactory.getCurrentSession();
		Transaction t = session.beginTransaction();
		
		int userRoleId = (Integer) session.save(userRole);
		t.commit();
		
		return userRoleId;
	}

}
