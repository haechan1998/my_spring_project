package com.koreaIT.www.service;

import java.util.List;

import com.koreaIT.www.domain.CommentVO;
import com.koreaIT.www.domain.PagingVO;
import com.koreaIT.www.handler.PagingHandler;

public interface CommentService {

	int post(CommentVO cvo);

	PagingHandler getList(long bno, PagingVO pgvo);

	int delete(long cno, long bno);

	int update(CommentVO cvo);

}
