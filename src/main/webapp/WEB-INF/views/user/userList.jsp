<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<jsp:include page="../layout/header.jsp"></jsp:include>
	
	<div class="inner">
	
		<h4>유저 목록</h4>
		<br>
		
		<!-- 리스트를 출력할 때 어드민을 제외한 모든 계정 출력으로 바꾸자 -->
		<c:forEach items="${uvoList }" var="uvo">
			<c:if test="${uvo.userId ne 'admin'and uvo.isBan eq 'N'}">
			     
				<p>
				
					id : ${uvo.userId } / 닉네임 : ${uvo.nickName} / 이메일 : ${uvo.email } / 마지막접속일자 : ${uvo.lastLogin } / 권한 :
					<c:forEach items="${uvo.authList }" var="avo">
						${avo.auth }
					</c:forEach>
					 / isban : ${uvo.isBan }
					
				</p>
				
				<form action="/user/ban" method="post">
					<input type="hidden" name="userId" value="${uvo.userId }">
					<input type="hidden" name="email" value="${uvo.email }">
					<input type="hidden" name="isBan" value="${uvo.isBan }">
					
					
					<select name="selected">
						<optgroup label="사용자 제재">
							<option value="5day">5일</option>
							<option value="10day">10일</option>
							<option value="15day">15일</option>
							<option value="permanent">영구</option>
						</optgroup>
					</select>
					<button type="submit">확인</button>
				</form>
				
			</c:if>
			
			
		</c:forEach>
		
		<br>
		<h4>제재 유저 목록</h4>
		<br>
		
		<!-- 여기서 밴 해제 -->
		<c:forEach items="${uvoList }" var="uvo">
		
			<form action="/user/unban" method="post">
				<c:if test="${uvo.isBan eq 'Y' }">
				
					<p>
					
						id : ${uvo.userId } / 닉네임 : ${uvo.nickName} / 이메일 : ${uvo.email } / 마지막접속일자 : ${uvo.lastLogin } / 권한 :
						<c:forEach items="${uvo.authList }" var="avo">
							${avo.auth }
						</c:forEach>
						 / isban : ${uvo.isBan }
						
					</p>
					<input type="hidden" value="${uvo.userId }" name="userId">
					<input type="hidden" value="${uvo.isBan }" name="isBan">
					<button type="submit">사용자 제재 해제</button>
				
				</c:if>
			</form>
			
		</c:forEach>
		
		<!-- 여기서 락걸린 계정 해제 -->
		
		<br>
		<h4>계정 락 유저 목록</h4>
		<br>
		
		<c:forEach items="${uvoList }" var="uvo">
		
			<form action="/user/unRock" method="post">
				<c:if test="${uvo.accountRock}">
				
					<p>
					
						id : ${uvo.userId } / 닉네임 : ${uvo.nickName} / 이메일 : ${uvo.email } / 마지막접속일자 : ${uvo.lastLogin } / 권한 :
						<c:forEach items="${uvo.authList }" var="avo">
							${avo.auth }
						</c:forEach>
						 / accountRock : ${uvo.accountRock }
						
					</p>
					<input type="hidden" value="${uvo.userId }" name="userId">
					<button type="submit">계정 락 해제</button>
				
				</c:if>
			</form>
			
		</c:forEach>
	
	</div>
	
<jsp:include page="../layout/footer.jsp"></jsp:include>
