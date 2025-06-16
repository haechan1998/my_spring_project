<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/resources/css/reset.css" rel="stylesheet">
<link href="/resources/css/default.css" rel="stylesheet">
<link href="/resources/css/login.css" rel="stylesheet">

</head>
<body>
<!-- 여기부터 header 작성 -->
	<header>
		<div class="inner">
			<sec:authentication property="principal" var="pri"/>
			<ul class="navBox">
				<li>
					<a href="/">home</a> 
				</li>
				<li>
					<a href="/board/register">게시물 등록</a>
				</li>
				<li>
					<a href="/user/login">로그인</a>
				</li>
				<li>
					<a href="/user/register">회원가입</a>
				</li>
				<li>
				
				<form action="/user/logout" method="post" id="looutForm">
					<a href="" id="logoutLick">로그아웃</a>
				</form>
				</li>
				<li>
					<a href="/user/userDetail">내정보</a>
				</li>
				<!-- ADMIN 일 경우 나타내는 정보 -->
				<li>
					<a>신고내역</a>
				</li>
				<!-- 가입 일자 비교해서 신규 유저 위 -->
				<!-- 가입 일자 비교해서 올드 유저 아래-->
				<li>
					<a href="/user/userList">유저리스트</a>
				</li>
				
			</ul>			
		</div>
		
		<script type="text/javascript">
			document.getElementById('logoutLick').addEventListener('click', (e) => {
		    e.preventDefault
		    document.getElementById('looutForm').submit();
		})
		</script>
	</header>
