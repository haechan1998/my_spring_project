package com.koreaIT.www.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.koreaIT.www.domain.CommentVO;
import com.koreaIT.www.domain.PagingVO;
import com.koreaIT.www.handler.PagingHandler;
import com.koreaIT.www.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RequestMapping("/comment/*")
@Slf4j
@RestController
public class CommentController {
	
	private final CommentService csv;
	
	// ResponseEntity<T> : T => response 객체의 body 에 보낼 값의 타입... 
	
	@PostMapping("/post")
	public ResponseEntity<String> post(@RequestBody CommentVO cvo){
		log.info(">>>>>> cvo > {}", cvo);
		
		// cvo 를 DB 로 전달.
		int isOk = csv.post(cvo);
		
		return isOk > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) : // 200
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR); // 500
		
	}

//	----------------------------------------------------------------------------
//									페이지가 없을경우
//	@GetMapping(value = "/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<CommentVO>> list(@PathVariable("bno") long bno){
//		
//		List<CommentVO> list = csv.getList(bno);
//		
//		return new ResponseEntity<List<CommentVO>>(list, HttpStatus.OK);
//	}
//	----------------------------------------------------------------------------
	
	@GetMapping(value = "/{bno}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagingHandler> list(
			@PathVariable("bno") long bno,
			@PathVariable("page") int page){
		
		// DB 의 limit 에서 쓸 값을 설정.
		PagingVO pgvo = new PagingVO(page, 5);
		
//		List<CommentVO> list = csv.getList(bno, pgvo);
//		int totalCount = csv.getTotal(pgvo);
//		PagingHandler ph = new PagingHandler(totalCount, pgvo, list);
		
		PagingHandler ph = csv.getList(bno, pgvo);
		
		
		
		return new ResponseEntity<PagingHandler>(ph, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{cno}/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(
			@PathVariable("cno") long cno,
			@PathVariable("bno") long bno) {
		
		int isOk = csv.delete(cno, bno);
		
		return isOk > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) : // 성공
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR); // 실패
		
		
	}
	// 수정
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody CommentVO cvo){
		
		int isOk = csv.update(cvo);
		
		return isOk > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) :
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
}
