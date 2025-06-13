<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<jsp:include page="../layout/header.jsp"></jsp:include>
	
	<div class="inner">
	<form action="/user/register" method="post">
		ID<input type="text" name="userId">
		PW<input type="text" name="password">
		NAME<input type="text" name="name"> 
		EMAIL<input type="text" name="email">
		NICK<input type="text" name="nickName">
		PHONE<input type="text" name="phone">
		<button type="submit">가입</button>
	</form>
	</div>
	
<jsp:include page="../layout/footer.jsp"></jsp:include>
