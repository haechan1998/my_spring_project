package com.koreaIT.www.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreaIT.www.domain.BoardDTO;
import com.koreaIT.www.domain.BoardVO;
import com.koreaIT.www.domain.FileVO;
import com.koreaIT.www.handler.FileHandler;
import com.koreaIT.www.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
public class BoardController {
	
	private final BoardService bsv;
	private final FileHandler fh;
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/insert")
	public String insert(BoardVO bvo, MultipartFile[] files) {
		
		List<FileVO> fileList = null;
		if(files[0].getSize() > 0){ // 업로드 하는 파일이 있는경우라면
			fileList = fh.uploadFile(files);
		}
		
		BoardDTO bdto = new BoardDTO(bvo, fileList);
		
		int isOk = bsv.insert(bdto, files.length);

		
		return "redirect:/"; // 등록 테스트 하면 메인으로 가기
		
	}
	
	
	
	@GetMapping({"/detail", "/modify"})
	public void detail
		(Model m,
		@RequestParam("bno")long bno,
		HttpServletRequest request
		) {
		String path = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1);
		
		if(path.equals("detail")) {
			int isOk = bsv.updateCount(bno, 1); // 조회수....
			
		}
		
		BoardDTO bdto = bsv.getDetail(bno);
		m.addAttribute("bdto", bdto);
		
	}
	
	@PostMapping("/update")
	public String update(
			BoardVO bvo,
			RedirectAttributes re,
			@RequestParam(value = "files", required = false) MultipartFile[] files
			) {
		// 기본값은 파일이 없는것으로
		List<FileVO> fileList = null;
		
		// 파일이 존재한다면
		if(files[0].getSize() > 0) {
			fileList = fh.uploadFile(files); // FileVO 에 있는 FileHandler 메서드 사용
		}
		
		int isOk = bsv.updateBoard(new BoardDTO(bvo, fileList));
		
		
		return "redirect:/";
		
	}
	

	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
