package com.koreaIT.www.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.koreaIT.www.repository.UserDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
	
	@Autowired
	private UserDAO udao;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		String authUserId = request.getParameter("userId");
		String errorMessage = null;
		String attempsMessage = null;
		
		
		
		// BadCredentalException : 비밀번호가 일치하지 않음.
		// InternalAuthenticationServiceException : 인증 프로세스 내부에서 오류가 발생했을 때
		// UsernameNotFoundException : 존재하지 않는 아이디
		// exception 발생 시 메시지 저장
		if(exception instanceof UsernameNotFoundException) {
			errorMessage = "아이디가 일치하지 않습니다.";
			
		}else if(exception instanceof BadCredentialsException) {
			errorMessage = "비밀번호가 일치하지 않습니다.";
			// 여기서 실패횟수처리
			udao.failedLogin(authUserId); 
			// 아이디는 일치하지만 비밀번호가 다른경우 로그인 실패횟수 1회 증가
			
		}else if(exception instanceof InternalAuthenticationServiceException) {
			errorMessage = "관리자에게 문의하세요.(koreaIT@gmail.com)";
			
		}else if(exception instanceof DisabledException) {
			errorMessage = "정지된 계정입니다. 관리자에게 문의하세요.(koreaIT@gmail.com)";
			
		}else {
			errorMessage = "관리자에게 문의하세요.(koreaIT@gmail.com)";
			
		}
		
		// 여기서 실패 값 비교
		int failedAttemps = udao.getFailedAttemps(authUserId);
		
		// 로그인 실패 횟수가 5회 이상 일 경우
		if(failedAttemps >= 5) {
			// 해당 유저의 계정을 rock 처리
			udao.accountRock(authUserId);
			errorMessage = "잠긴 계정입니다. 관리자에게 문의하세요.";
		}
		
		// 로그인 실패 횟수가 5회 미만일경우
		if(5 > failedAttemps){
			attempsMessage = (5-failedAttemps)+ "회 실패했습니다.(5회 실패시 계정이 잠기게됩니다.)";
		}
		
		
		request.setAttribute("userId", authUserId);
		request.setAttribute("errorMessage", errorMessage);
		request.setAttribute("attemptsMessag", attempsMessage);
		
		request.getRequestDispatcher("/user/login").forward(request, response);
		
	}

}
