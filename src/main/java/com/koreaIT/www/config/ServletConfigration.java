package com.koreaIT.www.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableScheduling
@EnableWebMvc
@ComponentScan(basePackages = {
		"com.koreaIT.www.controller",
		"com.koreaIT.www.service",
		"com.koreaIT.www.handler",
		"com.koreaIT.www.security"
		})
public class ServletConfigration implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// resources 경로설정 / 나중에 파일 업로드에 대한 경로설정 추가 
		// handler > location
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/");
		
		// 파일 업로드시 경로 추가 설정 폴더 만들고 꼭 확인할것
//		registry.addResourceHandler("/upload/**")
//		.addResourceLocations("file:///D:\\web_java_chc\\_myProject\\_java\\_fileUpload\\");
//		/* 
//		 * file:/// 뒤에 나오는 경로에 가서 파일을 찾는다.
//		 *  
//		 * */
		
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		// controller 에서 경로를 가져 올 때 파일명으로만 호출
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setViewClass(JstlView.class);
		
		registry.viewResolver(viewResolver);
		
	} // 웹 부분 값 처리
	
	// 파일 업로드 시 리졸버 추가
	@Bean(name = "multipartResolver")
	public MultipartResolver getMultipartResolver() {
		StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
		
		return multipartResolver;
	}
	
	
	

}
