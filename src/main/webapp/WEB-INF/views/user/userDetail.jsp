<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<jsp:include page="../layout/header.jsp"></jsp:include>
	
	<!-- 권한이 있는 경우에 접근이 가능한 페이지 -->
	
	<sec:authentication property="principal" var="pri"/>
		
	<div class="inner">
		id : <p>${pri.uvo.userId }</p>
		email : <p>${pri.uvo.email }</p>
		nickName : <p>${pri.uvo.nickName }</p>
		phone : <p>${pri.uvo.phone }</p>
		lastLogin : <p>${pri.uvo.lastLogin }</p>
	</div>
	
<jsp:include page="../layout/footer.jsp"></jsp:include>
