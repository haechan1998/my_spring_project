package com.koreaIT.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.koreaIT.www.security.CustomAuthUserService;
import com.koreaIT.www.security.CustomUserDetails;
import com.koreaIT.www.security.LoginFailureHandler;
import com.koreaIT.www.security.LoginSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	// 비밀번호를 암호화
	@Bean
	public PasswordEncoder bcPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//사용자 커스텀
	//------------------------
	// SuccessHandler 객체
	@Bean
	public AuthenticationSuccessHandler authSuccessHandler() {
		return new LoginSuccessHandler(); // 로그인 커스텀
		
	}
	
	@Bean
	public AuthenticationFailureHandler authFailureHandler() {
		return new LoginFailureHandler(); // 로그인 커스텀 
	}
	
	@Bean	
	public UserDetailsService customDetailsService() {
		return new CustomAuthUserService(); // 유저 디테일 서비스 커스텀
	}
	
//	@Bean
//	public UserDetails customUserDetails() {
//		return new CustomUserDetails();
//	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		
		provider.setHideUserNotFoundExceptions(false);
		
		return provider;
		
	}
	
	// authenticaionManagerBuilder, httpSecurity 오버라이드
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider(customDetailsService(), bcPasswordEncoder()));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CharacterEncodingFilter filter = new CharacterEncodingFilter("utf-8");
		filter.setForceEncoding(true);
		http.addFilterBefore(filter, CsrfFilter.class);
		
		// 월요일에 풀자~
		http.csrf().disable();
		
		// 권한에 따른 승인 요청
		//-------------------------------------
		// 다 만들고 나면 권한에 따른 경로 제한 확인 필요
		//-------------------------------------
		http.authorizeRequests()
			.antMatchers("/user/userList").hasRole("ADMIN") // ADMIN 만 접근 가능한 경로
			.antMatchers(
					"/","/user/login","/user/register",
					"/board/detail","/upload/**","/resources/**","/comment/**").permitAll() // 모두가 접근 가능한 경로
			.anyRequest().authenticated(); // 권한이 있어야 접근 가능한 경로
			
		// 로그인 페이지 구성
		http.formLogin()
			.usernameParameter("userId")
			.passwordParameter("password")
			.loginPage("/user/login")
			.successHandler(authSuccessHandler())
			.failureHandler(authFailureHandler());
		
		// 로그아웃
		http.logout()
			.logoutUrl("/user/logout")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.logoutSuccessUrl("/");
	}
	
	
}
