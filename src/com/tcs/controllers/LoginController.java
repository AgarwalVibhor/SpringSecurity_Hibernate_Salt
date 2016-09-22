package com.tcs.controllers;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tcs.business.UserBusinessInterface;
import com.tcs.model.User;
import com.tcs.model.UserRole;

@Controller
public class LoginController {
	
	@Autowired
	@Qualifier("userService")
	private UserBusinessInterface business;
	
	
	
	@RequestMapping(value = "/")
	public ModelAndView start(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("redirect:/hello");
		return model;
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView admin(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("admin");
		model.addObject("title", "Spring Security - Salt Generation");
		model.addObject("message", "This page is only for the Admin guys !!");
		return model;
	}
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public ModelAndView hello(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("hello");
		model.addObject("title", "Spring Security - Salt Generation");
		model.addObject("message", "This page is both for the Admin and the Users !!");
		return model;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginGet(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout)
	{
		ModelAndView model = new ModelAndView("login");
		if(error != null)
		{
			model.addObject("error_message", "Invalid Username and Password !");
		}
		if(logout != null)
		{
			model.addObject("logout_message", "You have successfully logged out !");
		}
		return model;
	}
	
/*	@RequestMapping(value = "/login_check", method = RequestMethod.POST)
	public ModelAndView loginPost(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password)
	{
		ModelAndView model = null;
		UserDetails user = customService.loadUserByUsername(username);
		System.out.println("------------------------------");
		System.out.println("Inside Controller");
		System.out.println("User : " + user);
		String hashedPassword = customService.getHashedPassword(username, password);
		System.out.println("Hashed : " + hashedPassword);
		if(user.getPassword().equals(hashedPassword))
		{
			model = new ModelAndView("redirect:/hello");
		}
		else
		{
			model = new ModelAndView("redirect:/login");
		}
		return model;
	}  */
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView error(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("error");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String username = user.getUsername();
		model.addObject("username", username);
		return model;
	}
	
	@RequestMapping(value = "/registerGet", method = RequestMethod.GET)
	public ModelAndView registerGet(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("register");
		User user = new User();
		model.addObject("employeeForm", user);
		return model;
	}
	
	@RequestMapping(value = "/registerPost", method = RequestMethod.POST)
	public ModelAndView registerPost(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute("employeeForm") User user, BindingResult result,
			@RequestParam(value = "userRole", required = false) String userRole,
			@RequestParam(value = "adminRole", required = false) String adminRole,
			@RequestParam(value = "managerRole", required = false) String managerRole)
	{
		Set<UserRole> userRoles = new HashSet<UserRole>();
		System.out.println("Inside Register Post");
		
		String username  = null;
		ModelAndView model = null;
		
		if(result.hasErrors())
		{
			model = new ModelAndView("register");
			return model;
		}
		else if(userRole == null)
		{
			model = new ModelAndView("register");
			model.addObject("employeeForm", user);
			model.addObject("error_message", "You need to select at least one option !");
			return model;
		}
		else if(userRole.equals("No"))
		{
			if(adminRole == null && managerRole == null)
			{
				model = new ModelAndView("register");
				model.addObject("employeeForm", user);
				model.addObject("message", "You need to select at least one option out of Admin and Manager !");
				return model;
			}
			else if(adminRole.equals("No") && managerRole.equals("No"))
			{
				model = new ModelAndView("register");
				model.addObject("employeeForm", user);
				model.addObject("message", "You need to select at least one option out of Admin and Manager. Both cannot be denied !!");
				return model;
			}
			else if(adminRole.equals("Yes") && managerRole.equals("Yes"))
			{
				UserRole userRole1 = new UserRole();
				userRole1.setUser(user);
				userRole1.setRole("ROLE_ADMIN");
				UserRole userRole2 = new UserRole();
				userRole2.setUser(user);
				userRole2.setRole("ROLE_MANAGER");
				userRoles.add(userRole1);
				userRoles.add(userRole2);
			}
			else if(adminRole.equals("Yes") && managerRole.equals("No"))
			{
				UserRole userRole1 = new UserRole();
				userRole1.setUser(user);
				userRole1.setRole("ROLE_ADMIN");
				userRoles.add(userRole1);
			}
			else
			{
				UserRole userRole1 = new UserRole();
				userRole1.setUser(user);
				userRole1.setRole("ROLE_MANAGER");
				userRoles.add(userRole1);
			}
		}
		else if(userRole.equals("Yes"))
		{
			UserRole userRole1 = new UserRole();
			userRole1.setUser(user);
			userRole1.setRole("ROLE_USER");
			userRoles.add(userRole1);
		}
		
			user.setUserRoles(userRoles);
			System.out.println("First Query for insert is fired.");
			username = business.insert(user);
			if(username != null)
			{
				System.out.println("Second Query for insert is fired.");
				Iterator<UserRole> itr = user.getUserRoles().iterator();
				while(itr.hasNext())
				{
					UserRole user_role = itr.next();
					System.out.println("Corresponding Query for insert is fired.");
					business.insertUserRole(user_role);
				}
				System.out.println("Second Query for insert is finished.");
				System.out.println("User saved successfully.");
				model = new ModelAndView("registerSuccess");
				model.addObject("title", "Spring Security - Salt Generation");
				model.addObject("username", username);
			}
			else
			{
				System.out.println("User could not be saved successfully.");
				model = new ModelAndView("register");
				model.addObject("employeeForm", user);
				model.addObject("message", "Registration Unsuccessful ! Please try again !!");
			}
			
		return model;
	}

}
