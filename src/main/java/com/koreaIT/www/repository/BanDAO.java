package com.koreaIT.www.repository;


import org.apache.ibatis.annotations.Param;


public interface BanDAO {

	int insertBan(@Param("userId") String userId, @Param("email") String email, @Param("selected") String selected);

	void deleteBan(String userId);




}
