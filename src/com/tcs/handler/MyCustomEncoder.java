package com.tcs.handler;

import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MyCustomEncoder extends BCryptPasswordEncoder{
	
	
	public MyCustomEncoder(int strength, SecureRandom random) {
		
		super(strength, random);
	}
	
	@Override
	public String encode(CharSequence rawPassword) {
		System.out.println("Inside encode......");
		return super.encode(rawPassword);
	}
	
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		System.out.println("Inside matches.......");
		return super.matches(rawPassword, encodedPassword);
	}
	
	

}
