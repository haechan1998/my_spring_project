package com.koreaIT.www.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.koreaIT.www.domain.UserVO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
//	@Autowired
	@Getter
	private UserVO uvo;
	
//	@Autowired
	@Getter
	private AuthUser authUser;
	
	public CustomUserDetails(UserVO uvo) {
		this.uvo = uvo;
		this.authUser = new AuthUser(uvo);
	}
	
	@Override
	public boolean isEnabled() {
		
		// is_account_rock 인 상황도 체크
		return uvo.getIsBan().equals("N") && !uvo.isAccountRock(); // true 라면 DisabledException 예외 리턴
	}
	
	// isEnabled 테스트용 오버라이드 (기본값)
	@Override public Collection<? extends GrantedAuthority> getAuthorities() { return null; }
    @Override public String getPassword() { return uvo.getPassword(); }
    @Override public String getUsername() { return uvo.getUserId(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }


}
