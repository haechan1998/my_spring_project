package com.koreaIT.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO {
	
	private long bno;
	private String title;
	private String writer;
	private String content;
	private boolean isDel;
	private String regDate;
	private int readCount;
	private int reportCount;
	private int fileQty;
	
	// 기본적인 테스트를 완료하고
	// 사용자 id를 board 의 작성자로 빼자

}
