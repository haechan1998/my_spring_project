package com.koreaIT.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BanVO {
	private String userId;
	private String email;
	private String regDate;
	private int day5; // 5일 정지
	private int day10; // 10일 정지
	private int day15; // 15일 정지
	private int permanent; // 영구정지
}
