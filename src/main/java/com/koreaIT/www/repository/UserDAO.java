package com.koreaIT.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.koreaIT.www.domain.AuthVO;
import com.koreaIT.www.domain.UserVO;

public interface UserDAO {

	void failedLogin(String authUserId);

	int getFailedAttempts(String authUserId);

	void accountRock(String authUserId);

	void updateLastLogin(String authUserId);

	void resetFailedAttempts(String authUserId);

	UserVO getUser(String username);

	List<AuthVO> getAuthList(String username);

	int insert(UserVO uvo);

	int insertAuthInit(String userId);

	UserVO checkId(UserVO uvo);

	UserVO checkNick(UserVO uvo);

	List<UserVO> getUserList();

	int userRestriction(UserVO uvo);

	void userUnban(@Param("userId") String userId, @Param("email") String email);

	void userUnRock(String userId);


}
