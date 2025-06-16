package com.koreaIT.www.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		
		re.addFlashAttribute("userId", request.getAttribute("userId"));
		re.addFlashAttribute("errorMessage", request.getAttribute("errorMessage"));
//		re.addFlashAttribute("attemptsMessage", request.getAttribute("attemptsMessage"));
		
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
	
	@PostMapping("isDuplicateId")
	public ResponseEntity<String> isDuplicateId(@RequestBody UserVO uvo){
		
		// 
		UserVO checkUvo = usv.checkId(uvo);
		log.info(">>>> uvo > {}", uvo);
		log.info(">>>> userId > {}", uvo.getUserId());
		log.info(">>>> checkUvo > {}", checkUvo);
		
		return checkUvo == null ? new ResponseEntity<String>("ok_id", HttpStatus.OK) : // 200
			new ResponseEntity<String>("fail_id", HttpStatus.INTERNAL_SERVER_ERROR); // 500
	}
	
	@PostMapping("isDuplicateNick")
	public ResponseEntity<String> isDuplicateNick(@RequestBody UserVO uvo){
		
		UserVO checkUvo = usv.checkNick(uvo);
		return checkUvo == null ? new ResponseEntity<String>("ok_nick", HttpStatus.OK) : // 200
			new ResponseEntity<String>("fail_nick", HttpStatus.INTERNAL_SERVER_ERROR); // 500
		
	}
	
	@GetMapping("/userList")
	public void userList(Model m) {
		
		List<UserVO> uvoList = usv.getUserList();
		log.info(">> uvoList > {}",uvoList);
		log.info(">> authList > {}", uvoList.get(0).getAuthList());
		
		
		m.addAttribute("uvoList", uvoList); 
	}
	
	// 밴처리
	@PostMapping("/ban")
	public String ban(@RequestParam("selected") String selected, UserVO uvo) {
		log.info(">> selected > {}",selected);
		
		// 밴할때 userId 를 담은 uvo 와 제재 내용을 들고간다.
		// 이후 user의 isBan 을 'Y'로 변경
		int isOk = usv.userRestriction(selected, uvo);
		
		return "redirect:/user/userList";
		
	}
	
	// 밴 해제 수동 처리
	@PostMapping("/unban")
	public String unban(UserVO uvo) {
		
		usv.userUnban(uvo);
		
		return "redirect:/user/userList";
	}
	
	// 사용자 계정 락 해제
	@PostMapping("/unRock")
	public String unRock(UserVO uvo) {
		
		usv.userUnRock(uvo);
		
		return "redirect:/user/userList";
	}
	
	@GetMapping("/userDetail")
	public void userDetail() {}
	
	
	
	
	
	
}
