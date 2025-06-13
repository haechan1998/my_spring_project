package com.koreaIT.www.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@EnableTransactionManagement
@MapperScan(basePackages = {"com.koreaIT.www.repository"})
@Configuration
public class RootConfig {
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Bean
	public DataSource dataSource() {
		
		HikariConfig hikariConfig = new HikariConfig();
		
		// 여기에서 DB 설정을 한다.
		// jdbcDriver, url, username, password
		// hikari 에 설정 set
		hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		// sql db 이름
		hikariConfig.setJdbcUrl("jdbc:log4jdbc:mysql://localhost:3306/my_springdb");
		// sql 유저
		hikariConfig.setUsername("springuser");
		// sql 패스워트
		hikariConfig.setPassword("mysql");
		
		
		// ------------------ 여기부터 hikari 필수 추가설정
		// 최대 커넥션 개수
		hikariConfig.setMaximumPoolSize(5);
		// 최소 유효 커넥션 개수 (max 와 같은 개수로 설정)
		hikariConfig.setMinimumIdle(5);
		
		hikariConfig.setConnectionTestQuery("SELECT now()"); // test query
		
		hikariConfig.setPoolName("springHikariCP");
		
		// ------------------ 여기부터 hikari 기타 추가설정
		// cachePrepStmts : cache 사용 여부 설정
		hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
		
		// mysql 드라이버가 연결당 cache size : 250~500 사이 권장
		hikariConfig.addDataSourceProperty("dataSource.prepStmtsCacheSize", "250");
		
		// connection 당 캐싱 할 preparedStatement 의 개수 지정 옵션 : default 256 (2의8승)
		hikariConfig.addDataSourceProperty("dataSource.prepStmtsCahceSqlLimit", "true");
		
		// mysql 서버에서 최신 이슈가 있을 경우 지원 받을 것인지 설정
		hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
		
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		
		return hikariDataSource;

		
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		
		SqlSessionFactoryBean sqlFactoryBean = new SqlSessionFactoryBean();
		// 위에서 설정한 hikari를 set 해준다.
		sqlFactoryBean.setDataSource(dataSource());
		
		// 맵퍼 경로 설정
		sqlFactoryBean
		.setMapperLocations(applicationContext.getResources("classpath:/mapper/*.xml"));
		
		// mybatisConfig.xml 인지
		sqlFactoryBean
		.setConfigLocation(applicationContext.getResource("classpath:/mybatisConfig.xml"));
		
		return sqlFactoryBean.getObject();
		
	}
	
	// 트랜젝션 매니저 설정
	@Bean
	public DataSourceTransactionManager transationManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
