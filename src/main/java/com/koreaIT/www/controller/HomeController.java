package com.koreaIT.www.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.koreaIT.www.domain.BoardVO;
import com.koreaIT.www.domain.PagingVO;
import com.koreaIT.www.handler.PagingHandler;
import com.koreaIT.www.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

	private final BoardService bsv;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(PagingVO pgvo, Model m) {
		int totalCount = bsv.getTotalCount(pgvo);
		PagingHandler ph = new PagingHandler(totalCount, pgvo);
		
		List<BoardVO> boardList = bsv.getBoardList(pgvo);
		log.info(">> qty > {}", pgvo.getQty());
		log.info(">> boardList > {}", boardList);
		
		m.addAttribute("boardList", boardList);
		m.addAttribute("ph", ph);
		
		return "main";
	}
	
	
	
}
