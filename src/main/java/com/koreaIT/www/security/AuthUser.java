package com.koreaIT.www.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.koreaIT.www.domain.UserVO;

import lombok.Getter;

@Getter
public class AuthUser extends User{

	private static final long serialVersionUID = 1L;
	private UserVO uvo;

	public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		// TODO Auto-generated constructor stub
	}
	
	public AuthUser(UserVO uvo) {
		super(uvo.getUserId(), uvo.getPassword(),
				uvo.getAuthList().stream()
				.map(authVO -> new SimpleGrantedAuthority(authVO.getAuth()))
				.collect(Collectors.toList())
				);
		this.uvo = uvo;
	}
}
