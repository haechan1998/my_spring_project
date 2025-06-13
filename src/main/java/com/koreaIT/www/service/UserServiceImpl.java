package com.koreaIT.www.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koreaIT.www.domain.UserVO;
import com.koreaIT.www.repository.UserDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserDAO udao;

	@Transactional
	@Override
	public int insert(UserVO uvo) {
		int isOk = udao.insert(uvo);
		return udao.insertAuthInit(uvo.getUserId());
	}

}
