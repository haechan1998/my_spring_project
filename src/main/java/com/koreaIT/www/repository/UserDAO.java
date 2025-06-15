package com.koreaIT.www.repository;

import java.util.List;

import com.koreaIT.www.domain.AuthVO;
import com.koreaIT.www.domain.UserVO;

public interface UserDAO {

	void failedLogin(String authUserId);

	int getFailedAttemps(String authUserId);

	void accountRock(String authUserId);

	void updateLastLogin(String authUserId);

	void resetFailedAttempts(String authUserId);

	UserVO getUser(String username);

	List<AuthVO> getAuthList(String username);

	int insert(UserVO uvo);

	int insertAuthInit(String userId);

	UserVO checkId(UserVO uvo);

}
