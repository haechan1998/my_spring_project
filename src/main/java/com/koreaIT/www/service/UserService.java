package com.koreaIT.www.service;

import java.util.List;

import com.koreaIT.www.domain.UserVO;

public interface UserService {

	int insert(UserVO uvo);

	UserVO checkId(UserVO uvo);

	UserVO checkNick(UserVO uvo);

	List<UserVO> getUserList();

	int userRestriction(String selected, UserVO uvo);

	void userUnban(UserVO uvo);

	void userUnRock(UserVO uvo);

}
