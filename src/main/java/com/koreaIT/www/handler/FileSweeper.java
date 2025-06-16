package com.koreaIT.www.handler;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.koreaIT.www.domain.FileVO;
import com.koreaIT.www.repository.FileDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@RequiredArgsConstructor
@Component
public class FileSweeper {
	
	
	// 매일 등록된 시간에 파일 정리 => 스케줄러가 실행
	// 화면상에서(DB에서) 삭제되었지만, 실제 파일로 남아있는 파일을 제거
	// FileRemoveHandler 를 사용해도 됨.
	// 스케줄 기록 cron 방식 : 초 분 시 일 월 요일 년도(생략가능)
	
	// cron  = "59 59 23 * * *" => 매일 23시 59분 59초에 실행
	
	// DB 에 존재하지 않는 파일이 폴더에 존재한다면 삭제
	
	// 직접 DB에 접속해서 파일 리스트를 가져와서 폴더의 파일고 비교
	private final FileDAO fdao;
	private final String DIR = "D:\\web_java_chc\\_myProject\\_java\\_fileUpload\\"; // 파일 저장 경로
	
	@Scheduled(cron="59 59 23 * * *")
	public void fileSweeper() {
		log.info(">>>> FileSweeper Running Start : {}", LocalDateTime.now());
		
		// DB에 등록된 모든 파일 리스트 가져오기
		List<FileVO> dbList = fdao.getAllFileList();
		
		// 실제 폴더에 저장되어있는 파일리스트
		// D:\\web_java_chc\\_myProject\\_java\\_fileUpload\\2025\\06\\09\\uuid_fileName
		// D:\\web_java_chc\\_myProject\\_java\\_fileUpload\\2025\\06\\09\\uuid_th_fileName
		// (실재하는 파일 리스트)
		List<String> currentFiles = new ArrayList<>();
		for(FileVO fvo : dbList) {
			String filePath = fvo.getSaveDir() + File.separator + fvo.getUuid();
			String fileName = fvo.getFileName();
			currentFiles.add(DIR + filePath + "_" + fileName);
			
			// 이미지라면 썸네일도 추가
			if(fvo.getFileType() == 1) {
				currentFiles.add(DIR + fileName + "_th_" + fileName);
			}
		}
		log.info(">>>> currentFiles > {}", currentFiles);
		
		// tlf vkdlf rudfh tjfwjd
		
		LocalDate now = LocalDate.now();
		String today = now.toString();
		today = today.replace("-", File.separator); // 2025/06/09
		
		// 경로를 기반으로 저장되어 있는 파일 검색
		File dir = Paths.get(DIR + today).toFile();
		// listFiles() : 경로안에 있는 모든 파일을 배열로 리턴
		
		File[] allFileObject = dir.listFiles();
		log.info(">>>> allFileObject > {}", allFileObject.toString());
		
		// 실제 정장되어 있는 파일 목록과, DB 에 존재하는 파일을 비교하여 DB 에 없는 파일을 삭제 진행
		
		for(File file : allFileObject) {
			String storedFileName = file.toPath().toString();
			
			if(!currentFiles.contains(storedFileName)) {
				// 파일이 존재하지 않는다면 삭제
				file.delete();
				log.info(">>>> delete file >> {}", storedFileName);
			}
		}

		log.info(">>>> FileSweeper Running End : {}", LocalDateTime.now());
	}
	
	
	
	
}
