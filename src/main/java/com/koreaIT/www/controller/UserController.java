package com.koreaIT.www.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreaIT.www.domain.UserVO;
import com.koreaIT.www.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user/*")
@Controller
public class UserController {
	
	private final UserService usv;
	private final BCryptPasswordEncoder bcEncoder;
	
	@GetMapping("/login")
	public void login() {}
	
	
	@PostMapping("/login")
	public String join(HttpServletRequest request, RedirectAttributes re) {
		
		re.addFlashAttribute("email", request.getAttribute("email"));
		re.addFlashAttribute("errorMessage", request.getAttribute("errorMessage"));
		
		return "redirect:/user/login";
	}
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(UserVO uvo) {
		
		// 비동기로 로그인 중복 검사 실행 후 회원가입 처리 완료 시키기
		
		// 회원가입 처리
		// password 암호화
		log.info(">>>> uvo > {}", uvo);
		uvo.setPassword(bcEncoder.encode(uvo.getPassword()));
		int isOk = usv.insert(uvo);
		log.info(">>>> uvo > {}",uvo);
		
		return "redirect:/";
	}
	
	@PostMapping("isDuplicate")
	public ResponseEntity<String> isDuplicate(@RequestBody UserVO uvo){
		
		// 
		UserVO checkUvo = usv.checkId(uvo);
		log.info(">>>> uvo > {}", uvo);
		log.info(">>>> userId > {}", uvo.getUserId());
		log.info(">>>> checkUvo > {}", checkUvo);
		
		return checkUvo == null ? new ResponseEntity<String>("ok", HttpStatus.OK) : // 200
			new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR); // 500
	}
	
	
	
	
	
	
	
	
	
	
	
}
