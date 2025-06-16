<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
	
<jsp:include page="../layout/header.jsp"></jsp:include>
	
	<sec:authentication property="principal" var="pri"/>
	<c:set value="${bdto.bvo }" var="bvo"></c:set>
	<div class="inner">
	<form action="">
		<!-- 로그인 해야 나타나는 정보 -->
		<input type="hidden" value="${bvo.bno }" name="bno">
		<input type="hidden" value="${pri.uvo.userId }" name="userId">
		<button type="submit">신고하기</button>
	</form>
		<p>제목 : ${bvo.title }</p>
		<p>조회수 : ${bvo.readCount }</p>
		<p>작성자 : ${bvo.writer }</p>
		<p>내용 : ${bvo.content }</p>
				
	
		<!-- 댓글 비동기로 받기 -->
		
	</div>
	
<jsp:include page="../layout/footer.jsp"></jsp:include>
