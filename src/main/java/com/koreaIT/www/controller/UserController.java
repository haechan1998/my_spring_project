package com.koreaIT.www.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	//logout 메서드 구현
	private void logout(HttpServletRequest request, HttpServletResponse response) {
		// 내가 로그인한 시큐리티의 authentication 객체
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		new SecurityContextLogoutHandler().logout(request, response, authentication);
		
	}
	
	@GetMapping("/modify")
	public void modify() {}
	
	@PostMapping("/modify")
	public String modify(
			UserVO uvo, RedirectAttributes re,
			HttpServletRequest request, HttpServletResponse response
			) {
		
		int isOk = 0;
		
		if(uvo.getPassword().isEmpty() || uvo.getPassword().length() == 0) {
			isOk = usv.userModify(uvo);
		}else {
			uvo.setPassword(bcEncoder.encode(uvo.getPassword()));
			isOk = usv.userPwdModify(uvo);
		}
		// pwd 가 공백이면 nick_name 만 수정
		// pwd 가 있다면 pwd를 암호화 하여 다시 저장
		re.addFlashAttribute("modify_msg", isOk > 0 ? "ok" : "fail");
		// flash 는 주소표시줄에 띄우지 않는다. 그리고 데이터를 옮기고 나서 바로 삭제.
		
		log.info(">>>> modify > {}",isOk > 0 ? "성공" : "실패");
		
		// 로그아웃을 하고, 수정이 완료되었다는 메세지
		// 수정이 완료되면 로그인 페이지로 이동
		logout(request, response);
		return "redirect:/";
	}
	
	@GetMapping("/withdrawMembership")
	public String withdrawMembership(Model m, RedirectAttributes re, Principal pri) {
		
		String e = pri.getName(); // Principal 객체는 인증사용자의 username 정보만 가지고 있음.
		 
		// Authentication 객체는 인증사용자의 username, authorities 를 가지고 있다.
		// : credentials 는 암호화로 포장되어있기 때문에 암호화되어있는 값만 가져올 수 있다.
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		String userId = userDetails.getUsername();
		log.info(">>> userId >{}",userId);
		int isOk = usv.withdrawMembership(userId);
		re.addFlashAttribute("remove_msg", isOk > 0 ? "ok" : "fail");
		
		return "redirect:/";
		
	}
	
	
	
	
	
	
}
