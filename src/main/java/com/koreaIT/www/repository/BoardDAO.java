package com.koreaIT.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.koreaIT.www.domain.BoardVO;
import com.koreaIT.www.domain.PagingVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

	long getBno();

	int updateFileQty(@Param("bno") long bno, @Param("length") int length);

	int getTotalCount(PagingVO pgvo);

	List<BoardVO> getBoardList(PagingVO pgvo);

	int updateCount(@Param("bno") long bno, @Param("i")int i);

	BoardVO getDetail(long bno);

	int updateBoard(BoardVO bvo);

}
