package com.koreaIT.www.handler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.koreaIT.www.domain.FileVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@Component
public class FileHandler {
//  저장경로
	private final String UP_DIR = "D:\\web_java_chc\\_myProject\\_java\\_fileUpload";
	
	// MutipartFile[] 파리미터로 받고, List<FileVO> 로 리턴 (+ 저장)
	public List<FileVO> uploadFile(MultipartFile[] files){
		
		
		List<FileVO> fileList = new ArrayList<FileVO>();
		
		// FileVO 생성 + 파일 저장 + 그림일 경우 썸네일 저장...
		// 일반적으로 파일을 저장 시 날짜별로 폴더화 하여 업로드된 파일을 관리.
		LocalDate date = LocalDate.now(); // 오늘 날짜 리턴 2025-06-05
		log.info(">>> date > {}", date);
		String today = date.toString();
		today = today.replace("-", File.separator); // 2025/06/05 window(\), mac(/)
		
		// D:\\web_java_chc\\_myProject\\_java\\_fileUpload\\2025\\06\\05
		File folders = new File(UP_DIR, today);
		// mkdir : 폴더 생성 명령어(1개만 생성) / mkdirs (하위폴더까지 다 생성)
		
		// exists() 있는지 없는지 t/f 로 리턴
		if(!folders.exists()) { // 폴더가 없다면
			folders.mkdirs();
		}
		
		// files 를 가지고 FileVO 생성
		for(MultipartFile file : files) {
			
			FileVO fvo = new FileVO();
			// uuid, saveDir, fileName, fileType(썸네일 작업할때 넣기), fileSize
			fvo.setSaveDir(today); // 2025\\06\\05
			fvo.setFileSize(file.getSize());
			
			log.info(">>> getName > {}", file.getName()); // 객체 이름
			log.info(">>> getName > {}", file.getOriginalFilename()); // 실제 파일 명
			String fileName = file.getOriginalFilename();
			fvo.setFileName(fileName);
			UUID uuid = UUID.randomUUID();
			String uuidStr = uuid.toString();
			fvo.setUuid(uuidStr);
			
			// ------- fvo 생성 완료 // fileType / bno
			// 디스크의 저장
			String fullFileName = uuidStr + "_" + fileName;
			File storeFile = new File(folders, fullFileName);
					
			// 저장
			try {
				file.transferTo(storeFile); // 저장
				// 썸네일 저장 (이미지만 가능)
				// 이미지인지 확인 => tika
				if(isImageFile(storeFile)) {
					fvo.setFileType(1);
					// 썸네일 생성
					File thumbnail = new File(folders, uuidStr + "_th_" + fileName);
					Thumbnails.of(storeFile).size(100, 100).toFile(thumbnail);
				}
				
			} catch (Exception e) {
				log.info("file store error");
				e.printStackTrace();
			}
			// for 문 끝나기 전에 list 추가
			fileList.add(fvo);
		}
		
		return fileList;
	}
	
	private boolean isImageFile(File storeFile) throws IOException {
		// type "image/png"
		String mimeType = new Tika().detect(storeFile);
		return mimeType.startsWith("image") ? true : false;
	}
}
