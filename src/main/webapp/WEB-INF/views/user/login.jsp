<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<jsp:include page="../layout/header.jsp"></jsp:include>
	
	<div>
	    <div class="login-wrapper">
	        <h2>Login</h2>
	        <form method="post" action="/user/login" id="login-form">
	            <input type="text" name="userId" placeholder="id" id="loginInputId">
	            <p id="message-tit" style="color: red; font-size: 10px;"></p>
	            <br>
	            
	            <input type="password" name="password" placeholder="Password" id="loginInputPassword">
	            <label for="remember-check">
	                <input type="checkbox" id="remember-check">아이디 저장하기
	            </label>
	            <input type="submit" value="Login" id="loginSubBtn">
	        </form>
	    </div>
	</div>

	
<script type="text/javascript">
	const errorMessage = `<c:out value="${errorMessage}" />`
	// const attemptsMessage = `<c:out value="${attemptsMessage}" />`
	const userId = `<c:out value="${userId}" />`
</script>
<script type="text/javascript" src="/resources/js/login.js">
</script>
	
<jsp:include page="../layout/footer.jsp"></jsp:include>
