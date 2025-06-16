package com.koreaIT.www.service;

import java.util.List;

import com.koreaIT.www.domain.BoardDTO;
import com.koreaIT.www.domain.BoardVO;
import com.koreaIT.www.domain.PagingVO;

public interface BoardService {


	int insert(BoardDTO bdto, int length);

	int getTotalCount(PagingVO pgvo);

	List<BoardVO> getBoardList(PagingVO pgvo);

	int updateCount(long bno, int i);

	BoardDTO getDetail(long bno);

	int updateBoard(BoardDTO boardDTO);

	int remove(long bno);

	int removeFile(String uuid);

}
