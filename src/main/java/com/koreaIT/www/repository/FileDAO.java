package com.koreaIT.www.repository;

import java.util.List;

import com.koreaIT.www.domain.FileVO;

public interface FileDAO {

	int fileInsert(FileVO fvo);

	List<FileVO> getFileList(long bno);

	long getBno(String uuid);

	int removeFile(String uuid);

	List<FileVO> getAllFileList();

}
