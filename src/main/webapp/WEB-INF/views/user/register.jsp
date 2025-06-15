<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<jsp:include page="../layout/header.jsp"></jsp:include>
	
	<div class="inner">
	<form action="/user/register" method="post">
		ID<input type="text" name="userId" id="isDuplicateId">
		
		<button type="button" id="idDuplicateBtn">중복체크</button>
		
		<p id="checkId"></p>
		PW<input type="text" name="password" id="loginRegisterPassword">
		NAME<input type="text" name="name" id="loginRegisterName" > 
		EMAIL<input type="text" name="email" id="isDuplicateEmail">
		NICK<input type="text" name="nickName" id="isDuplicateNickName">
		PHONE<input type="text" name="phone" id="isDuplicatePhone">
		<button type="submit" id="registerSubBtn" disabled="disabled">가입</button>
	</form>
	</div>
	
	<script type="text/javascript" src="/resources/js/loginRegister.js">
	</script>
<jsp:include page="../layout/footer.jsp"></jsp:include>
