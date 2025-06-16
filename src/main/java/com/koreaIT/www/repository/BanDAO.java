package com.koreaIT.www.repository;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.koreaIT.www.domain.BanVO;


public interface BanDAO {

	int insertBan(@Param("userId") String userId, @Param("email") String email, @Param("selected") String selected);

	void deleteBan(String userId);

	List<BanVO> getExpiredBanUsers();

	int deleteBanByUserId(String userId);




}
