package com.wizinno.jas.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class SecurityUserDetails implements UserDetails, Serializable {

	private static final long serialVersionUID = -8242940190960961504L;
	
	private String username;//登录名
	private String password;	
	private boolean userEnabled;
	private Collection<GrantedAuthority> authorities;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	
	//额外增加的属性
	private long id;
	private List<Integer> permission;

	public SecurityUserDetails(long userId, String username, String password,
			boolean userEnabled, Collection<GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.userEnabled = userEnabled;		
		this.authorities = authorities;
		
		//这里先初始都为true，如果需要深度控制，可完善
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;

		this.id = userId;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}
	
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}
	
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.userEnabled;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isUserEnabled() {
		return userEnabled;
	}

	public void setUserEnabled(boolean userEnabled) {
		this.userEnabled = userEnabled;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Integer> getPermission() {
		return permission;
	}

	public void setPermission(List<Integer> permission) {
		this.permission = permission;
	}

}
