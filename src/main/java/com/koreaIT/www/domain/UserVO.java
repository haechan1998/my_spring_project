package com.koreaIT.www.domain;

import java.util.List;

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
public class UserVO {
	
	private String userId;
	private String password;
	private String name;
	private String nickName;
	private String email;
	private String phone;
	private String regDate;
	private String lastLogin;
	private String isBan;
	private List<AuthVO> authList;
	private boolean isAccountRock;
	private int failedAttempts;
	
}
