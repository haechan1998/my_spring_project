package com.koreaIT.www.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		
		// 클래스의 배열 형태로 담아 리턴한다.
		return new Class[] {RootConfig.class, SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		
		return new Class[] {ServletConfigration.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	// encoding 설정
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encoding = new CharacterEncodingFilter();
		encoding.setEncoding("UTF-8");
		encoding.setForceEncoding(true); 
		return new Filter[] {encoding};
		
	}
	
	
	// 파일 업로드 할때 수정
	@Override
	protected void customizeRegistration(Dynamic registration) {
		// 기타 사용자 설정 => customizeRegistration
		// 사용자 지정 익셉션 처리 설정
		registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
		// 파일 업로드 경로
		String uploadLocation = "D:\\web_java_chc\\_myProject\\_java\\_fileUpload";
		int maxFileSize = 1024*1024*20; // 20MB
		int maxReqSize = maxFileSize * 3; // 요청 파일 최대 사이즈
		int fileSizeThreshold = maxFileSize; // 파일의 임시공간
		// 위 요소는 파일 업로드 시 필수
		
		MultipartConfigElement multipartElement =
				new MultipartConfigElement(uploadLocation, maxFileSize, maxReqSize, fileSizeThreshold);
		
		registration.setMultipartConfig(multipartElement);
	}
	
	
	
	

}
