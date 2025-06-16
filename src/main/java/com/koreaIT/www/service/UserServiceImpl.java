package com.koreaIT.www.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koreaIT.www.domain.BanVO;
import com.koreaIT.www.domain.UserVO;
import com.koreaIT.www.repository.BanDAO;
import com.koreaIT.www.repository.UserDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserDAO udao;
	private final BanDAO bdao;

	@Transactional
	@Override
	public int insert(UserVO uvo) {
		int isOk = udao.insert(uvo);
		return udao.insertAuthInit(uvo.getUserId());
	}

	@Override
	public UserVO checkId(UserVO uvo) {
		// TODO Auto-generated method stub
		return udao.checkId(uvo);
	}

	@Override
	public UserVO checkNick(UserVO uvo) {
		// TODO Auto-generated method stub
		return udao.checkNick(uvo);
	}
	
	@Transactional
	@Override
	public List<UserVO> getUserList() {
		List<UserVO> uvoList = udao.getUserList();
		for(UserVO list : uvoList) {
			list.setAuthList(udao.getAuthList(list.getUserId()));
		}
		
		return uvoList;
	}
	
	@Transactional
	@Override
	public int userRestriction(String selected, UserVO uvo) {
		log.info(">>> isBan > {}",uvo.getIsBan());
		if(uvo.getIsBan().equals("N")) {
			bdao.insertBan(uvo.getUserId(), uvo.getEmail(), selected);
			
		}
		
		return udao.userRestriction(uvo);
		
		
	}
	
	@Transactional
	@Override
	public void userUnban(UserVO uvo) {
		if(uvo.getIsBan().equals("Y")) {
			udao.userUnban(uvo.getUserId(), uvo.getEmail());
		}
		bdao.deleteBan(uvo.getUserId());
		
		
		
	}

	@Override
	public void userUnRock(UserVO uvo) {
		udao.userUnRock(uvo.getUserId());
		
	}

}
