package com.koreaIT.www.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.koreaIT.www.domain.UserVO;
import com.koreaIT.www.repository.UserDAO;

@Component
public class CustomAuthUserService implements UserDetailsService {
	

	@Autowired
	private UserDAO udao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// username, password 인증용 토큰 생성 => DB 에서 확인
				// 리턴타입 UserDetails : 인증된 객체 리턴 <id, password, auth>
				
				// DB user 테이블에서 username 이 일치하는 객체를 리턴
				// username : 로그인을 시도하는 id => email
				
				UserVO uvo = udao.getUser(username);
				
				if(uvo == null) {
					throw new UsernameNotFoundException(username);
				}
				
				uvo.setAuthList(udao.getAuthList(username));
				
				return new CustomUserDetails(uvo);
	}
	
	
	
	

}
