package com.koreaIT.www.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koreaIT.www.domain.CommentVO;
import com.koreaIT.www.domain.PagingVO;
import com.koreaIT.www.handler.PagingHandler;
import com.koreaIT.www.repository.BoardDAO;
import com.koreaIT.www.repository.CommentDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

	private final CommentDAO cdao;
	private final BoardDAO bdao;

	@Transactional
	@Override
	public int post(CommentVO cvo) {
		// TODO Auo-generated method stub
		// 댓글을 새로 작성 할 경우 cmtQty 1 증가
		int i = 1;
		int isOk = cdao.post(cvo);
		
		if(isOk > 0) {
			isOk *= bdao.updateCmtQty(cvo.getBno(), i);
		}
		
		return isOk;
	}

//	--------------페이지가 없을경우-------------
//	@Override
//	public List<CommentVO> getList(long bno) {
//		// TODO Auto-generated method stub
//		return cdao.getList(bno);
//	}

	@Override
	public PagingHandler getList(long bno, PagingVO pgvo) {
		// TODO Auto-generated method stub
		List<CommentVO> list = cdao.getList(bno, pgvo);
		int totalCount = cdao.getTotal(bno);
		PagingHandler ph = new PagingHandler(totalCount, pgvo, list);
		
		return ph;
	}
	
	@Transactional
	@Override
	public int delete(long cno, long bno) {
		// TODO Auto-generated method stub
		int i = -1; // 삭제 할 경우 cmtQty 1 감소
		long bno1 = cdao.getBno(cno);
		
		log.info(">>> bno1 > {}", bno1);
		int isOk = cdao.delete(cno);
		if(isOk > 0) {
			isOk *= bdao.updateCmtQty(bno, i);
		}
		
		return isOk;
	}

	@Override
	public int update(CommentVO cvo) {
		// TODO Auto-generated method stub
		return cdao.update(cvo);
	}
	
}
