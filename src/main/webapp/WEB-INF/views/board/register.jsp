<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
    
<jsp:include page="../layout/header.jsp"></jsp:include>
	
	<div class="inner registerInner">
		<form action="/board/insert" method="post" enctype="multipart/form-data" class="registerForm">
			<ul>
			<sec:authentication property="principal" var="pri"/>
				<li>
					title<input type="text" name="title">
				</li>
				
				<li>
					writer<input type="text" name="writer" value="${pri.uvo.nickName }" readonly="readonly">
				</li>
				
				<li>
					content<textarea rows="5" cols="20" name="content"></textarea>
				</li>
			</ul>
			<input type="file" name="files" id="file" multiple="multiple">
			<button type="button" id="trigger">첨부파일 업로드</button>
			<button type="submit">저장</button>
		</form>
	</div>
	
	
	
	<script type="text/javascript" src="/resources/js/boardRegisterFile.js">
	</script>
	
<jsp:include page="../layout/footer.jsp"></jsp:include>