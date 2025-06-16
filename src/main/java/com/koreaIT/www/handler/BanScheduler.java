package com.koreaIT.www.handler;

import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.koreaIT.www.domain.BanVO;
import com.koreaIT.www.repository.BanDAO;
import com.koreaIT.www.repository.UserDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@RequiredArgsConstructor
@Component
public class BanScheduler {
	
	private final BanDAO banDao;
	private final UserDAO udao;
	
	@Scheduled(cron="59 59 23 * * *") // 매일 bandao 에 들어가서 밴 기간이 지난 사용자를 해제 처리 
	public void releaseExpiredBans() {
		log.info("=== 밴 해제 스케줄러 실행 ===");
        
        List<BanVO> expiredUsers = banDao.getExpiredBanUsers();

        for (BanVO banVo : expiredUsers) {
            int isOk = banDao.deleteBanByUserId(banVo.getUserId());
            udao.userUnban(banVo.getUserId(), banVo.getEmail());
            
            log.info("밴 해제됨: {} / 성공여부: {}", banVo.getUserId(), isOk > 0);
        }
    }
		
}

