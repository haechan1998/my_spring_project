package com.koreaIT.www.handler;

import java.io.File;

import com.koreaIT.www.domain.FileVO;

public class FileRemoveHandler {
private final String UP_DIR = "D:\\web_java_chc\\_myProject\\_java\\_fileUpload";
	
	public int deleteFile(FileVO fvo) {
		// D:\\web_java_chc\\_myProject\\_java\\_fileUpload\\2025\\06\\05\\uuid_fileName
		// D:\\web_java_chc\\_myProject\\_java\\_fileUpload\\2025\\06\\05\\uuid_th_fileName
		// file.delete() // 파일삭제
		// image file (=> fileType) 같이 삭제 / 아니면 파일만 삭제
		
		// 기존 파일 객체, 썸네일 파일 객체
		boolean isDel = false;
		
		File fileDir = new File(UP_DIR, fvo.getSaveDir());
		String removeFile = fvo.getUuid()+"_"+fvo.getFileName();
		File deleteFile = new File(fileDir, removeFile) ;
		String removeThFile = fvo.getUuid()+"_th_"+fvo.getFileName();
		File deleteThFile = new File(fileDir, removeThFile) ;
		
		try {
			
			// 파일이 존재해야 삭제
			if(deleteFile.exists()) {
				isDel = deleteFile.delete(); // 같은 이름의 파일을 삭제
				
				// 이미지 파일 일경우 fileType 은 1
				// 즉 이미지 파일이 아닐경우 thumbnail 은 삭제 하지 않아도 됨.
				if(fvo.getFileType()  == 1 && deleteThFile.exists()) {
					isDel = deleteThFile.delete();
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return isDel ? 1 : 0;
	}
	
}
