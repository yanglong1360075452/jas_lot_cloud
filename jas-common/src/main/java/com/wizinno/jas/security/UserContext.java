package com.wizinno.jas.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContext {

	/**
	 * 得到当前session中的用户，如果没有返回null
	 * 
	 * @return UserDetails
	 */
	public static SecurityUserDetails getUserDetails() {
		SecurityUserDetails userDetails = null;
		SecurityContext sc = SecurityContextHolder.getContext();
		Authentication ac = sc.getAuthentication();
		if (ac != null) {
			userDetails = (SecurityUserDetails) ac.getPrincipal();
		}
		return userDetails;
	}

	/**
	 * 得到当前登录用户，如果没有返回null
	 * 
	 * @return loginId
	 */
	public static String getLoginUserName() {
		String loginId = null;
		SecurityUserDetails userDetails = getUserDetails();
		if (userDetails != null) {
			loginId = userDetails.getUsername();
		}
		return loginId;
	}

	public static Long getLoginUserID() {
		Long userId = null;
		SecurityUserDetails userDetails = getUserDetails();
		if (userDetails != null) {
			userId = userDetails.getId();
		}
		return userId;
	}
	
	public static long getSystemUserID() {
		return 0;
	}

	/**
	 * 判断用户是否登陆
	 *
	 */
	public static boolean isLogined() {
		boolean flag = false;
		if(getLoginUserName() != null) {
			flag = true;
		}
		return flag;
	}

}
