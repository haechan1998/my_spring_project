package com.koreaIT.www.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koreaIT.www.domain.BoardDTO;
import com.koreaIT.www.domain.BoardVO;
import com.koreaIT.www.domain.FileVO;
import com.koreaIT.www.domain.PagingVO;
import com.koreaIT.www.repository.BoardDAO;
import com.koreaIT.www.repository.FileDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private final BoardDAO bdao;
	private final FileDAO fdao;
	
	@Transactional
	@Override
	public int insert(BoardDTO bdto, int length) {
		// BoardDTO => BoardVO + fileList
		int isOk = bdao.insert(bdto.getBvo()); // bvo 가 등록되어봐야 아는 번호
		if(bdto.getFlist() == null) {
			return isOk;
		}
		// FileDAO 생성 => fileMapper 를 생성하여 fvo 값을 DB 로 등록
		// fileVO.setBno
		if(isOk > 0) {
			long bno = bdao.getBno();
			for(FileVO fvo : bdto.getFlist()) {
				fvo.setBno(bno);
				// 저장
				
				isOk *= fdao.fileInsert(fvo);
			}
			isOk *= bdao.updateFileQty(bno, length);
			
		}
		
		return isOk;
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		return bdao.getTotalCount(pgvo);
	}

	@Override
	public List<BoardVO> getBoardList(PagingVO pgvo) {
		return bdao.getBoardList(pgvo);
	}

	@Override
	public int updateCount(long bno, int i) {
		return bdao.updateCount(bno, i);
	}
	
	@Transactional
	@Override
	public BoardDTO getDetail(long bno) {
		
		BoardVO bvo = bdao.getDetail(bno);
		List<FileVO> fileList = fdao.getFileList(bno);
		
		BoardDTO bdto = new BoardDTO(bvo, fileList);
		
		return bdto;
	}
	
	@Transactional	
	@Override
	public int updateBoard(BoardDTO boardDTO) {
		
		int isOk = bdao.updateCount(boardDTO.getBvo().getBno(), -1);
		long bno = boardDTO.getBvo().getBno();
		
		if(isOk > 0) {
			isOk *= bdao.updateBoard(boardDTO.getBvo());
		}
		if(boardDTO.getFlist() == null) {
			return isOk;
		}
		int length = 0;
		if(isOk > 0) {
			for(FileVO fvo : boardDTO.getFlist()) {
				fvo.setBno(boardDTO.getBvo().getBno());
				isOk *= fdao.fileInsert(fvo);
				length++;
			}
			isOk *= bdao.updateFileQty(bno, length);
		}
		return isOk;
		
	}

	@Override
	public int remove(long bno) {
		// TODO Auto-generated method stub
		return bdao.remove(bno);
	}

	@Transactional
	@Override
	public int removeFile(String uuid) {
		// TODO Auto-generated method stub
		long bno = fdao.getBno(uuid);
		int isOk = fdao.removeFile(uuid);
		if(isOk > 0) {
			isOk *= bdao.removeFileQtyUpdate(bno);
		}
		return isOk;
	}

	

}
