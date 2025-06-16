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
import org.springframework.security.core.userdetails.User;
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
//		String attemptsMessage = null;
		
		// 아이디가 null 일 경우 처리
//		if (authUserId == null || authUserId.trim().isEmpty() || authUserId.length() == 0) {
//		    request.setAttribute("errorMessage", "아이디를 입력해주세요.");
//		    request.getRequestDispatcher("/user/login").forward(request, response);
//		    return;  // 아래 로직 진행 방지
//		}
		
		// 여기서 실패 값 비교
		
		
		// BadCredentalException : 비밀번호가 일치하지 않음.
		// InternalAuthenticationServiceException : 인증 프로세스 내부에서 오류가 발생했을 때
		// UsernameNotFoundException : 존재하지 않는 아이디
		// exception 발생 시 메시지 저장
		if(exception instanceof UsernameNotFoundException) {
			errorMessage = "아이디가 일치하지 않습니다.";
			
		}else if(exception instanceof BadCredentialsException) {
			// 여기서 실패횟수처리
			udao.failedLogin(authUserId);
			
			int failedAttemps = udao.getFailedAttempts(authUserId);
			log.info(">> failAttemps > {}",failedAttemps);
			// 아이디는 일치하지만 비밀번호가 다른경우 로그인 실패횟수 1회 증가
			
			// 로그인 실패 횟수가 5회 미만일경우
			if(5 > failedAttemps){
				errorMessage = failedAttemps + "회 실패했습니다.(5회 실패시 계정이 잠기게됩니다.)";
			}
//			errorMessage = "비밀번호가 일치하지 않습니다.";
			
			// 로그인 실패 횟수가 5회 이상 일 경우
			if(failedAttemps >= 5) {
				// 해당 유저의 계정을 rock 처리
				udao.accountRock(authUserId);
				errorMessage = "정지된 계정입니다. 관리자에게 문의하세요.(koreaIT@gmail.com)";
//			errorMessage = "잠긴 계정입니다. 관리자에게 문의하세요.";
			}
			
		}else if(exception instanceof InternalAuthenticationServiceException) {
			errorMessage = "관리자에게 문의하세요.(koreaIT@gmail.com)";
			
		}else if(exception instanceof DisabledException) {
			errorMessage = "정지된 계정입니다. 관리자에게 문의하세요.(koreaIT@gmail.com)";
			
		}else {
			errorMessage = "관리자에게 문의하세요.(koreaIT@gmail.com)";
			
		}
		
		
		
		
		log.info(">> userId > {}",authUserId);
		
		
		request.setAttribute("userId", authUserId);
		request.setAttribute("errorMessage", errorMessage);
//		request.setAttribute("attemptsMessage", attemptsMessage);
		
		request.getRequestDispatcher("/user/login").forward(request, response);
		
	}

}
