package com.tcs.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "users_salt", catalog = "test")
public class User {
	
	private String fname;
	private String lname;
	private String username;
//	private String salt;
	private String password;
	private boolean enabled;
	private Set<UserRole> userRoles;
	
	public User() {
		super();
		this.enabled = true;
	}

	public User(String fname, String lname, String username, String password,
			boolean enabled, Set<UserRole> userRoles) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.username = username;
		//this.salt = salt;
		this.password = password;
		this.enabled = enabled;
		this.userRoles = userRoles;
	}
	
	@Column(name = "first_name", nullable = false)
	@NotEmpty(message = "Please enter your first name !")
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}
	
	@Column(name = "last_name", nullable = false)
	@NotEmpty(message = "Please enter your last name !")
	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}
	
	@Id
	@Column(name = "username", unique = true, nullable = false, length = 64)
	@NotEmpty(message = "Please enter your User Name !")
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
/*	@Column(name = "password_salt", nullable = false)
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}  */

	@Column(name = "password", nullable = false, length = 64)
	@NotEmpty(message = "Please enter your password !")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@Fetch(FetchMode.SELECT)
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	public String toString() {
		return "User [fname=" + fname + ", lname=" + lname + ", username="
				+ username + ", password=" + password + ", enabled=" + enabled
				+ ", userRoles=" + userRoles + "]";
	}

	
	
	
	
	
	
	

}
