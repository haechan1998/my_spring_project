<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<jsp:include page="../layout/header.jsp"></jsp:include>
	
	<div class="inner">
	<form action="/user/register" method="post">
		ID<input type="text" name="userId" id="isDuplicateId">
		<button type="button" id="isIdDuplicateBtn">중복체크</button>
		<p id="checkId"></p>
		
		NICK<input type="text" name="nickName" id="isDuplicateNickName">
		<button type="button" id="isNickDuplicateBtn">중복체크</button>
		<p id="checkNick"></p>
		
		PW<input type="text" name="password" id="loginRegisterPassword">
		EMAIL<input type="text" name="email" id="isDuplicateEmail">
		NAME<input type="text" name="name" id="loginRegisterName" > 
		PHONE<input type="text" name="phone" id="isDuplicatePhone">
		
		<button type="submit" id="registerSubBtn" disabled="disabled">가입</button>
	</form>
	</div>
	
	<script type="text/javascript" src="/resources/js/loginRegister.js">
	</script>
<jsp:include page="../layout/footer.jsp"></jsp:include>
