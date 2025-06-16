<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
	
<jsp:include page="../layout/header.jsp"></jsp:include>
	
	<sec:authentication property="principal" var="pri"/>
	<c:set value="${bdto.bvo }" var="bvo"></c:set>
	<div class="inner">
		<sec:authorize access="isAuthenticated()">
			<form action="">
				<!-- 로그인 해야 나타나는 정보 -->
				<input type="hidden" value="${bvo.bno }" name="bno">
				<input type="hidden" value="${pri.uvo.userId }" name="userId">
				<button type="submit">신고하기</button>
			</form>
		</sec:authorize>
			<p>제목 : ${bvo.title }</p>
			<p>조회수 : ${bvo.readCount }</p>
			<p>작성자 : ${bvo.writer }</p>
			<p>내용 : ${bvo.content }</p>
			
		<a href="/board/list"><button type="button">목록</button></a>
		<sec:authorize access="isAuthenticated()">
			<c:set var="nickWithUser" value="${nick}(${user})" />
			
			<c:if test="${bvo.writer eq pri.uvo.nickName }">
				<a href="/board/modify?bno=${bvo.bno }"><button type="button">수정</button></a>
				<a href="/board/remove?bno=${bvo.bno }"><button type="button">삭제</button></a>
			</c:if>
		</sec:authorize>
		
		<!-- 댓글 비동기로 받기 -->
		<!-- comment line -->
		<!-- post -->
		<sec:authorize access="isAuthenticated()">
			<div class="input-group mb-3" style="margin-top:20px">
				<sec:authentication property="principal" var="pri"/>
				<span class="input-group-text" id="cmtWriter">${pri.uvo.userId }</span>
				<input type="text" class="form-control" id="cmtText" placeholder="comment..." aria-label="Username" aria-describedby="basic-addon1">
				<button type="button" id="cmtAddBtn" class="btn btn-secondary">post</button>
			</div>
		</sec:authorize>
		
		<!-- print -->
		<ul class="list-group list-group-flush" id="cmtListArea">
		</ul>
		
		<!-- 댓글 더보기 버튼 : 한 페이지에 5개 댓글 표시 더 있으면 더보기 버튼 활성화 / 없으면 사라짐-->
		<div class="mb-3">
			<button type="button" id="moreBtn" data-page="1" class="btn btn-secondary" style="visibility: hidden">더보기 +</button>
		</div>
		
		<!-- 수정 test -->
		
		<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h1 class="modal-title fs-5" id="cno-writer">Writer</h1>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body">
		        <input type="text" class="form-control" id="cmtTextMod">
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
		        <button type="button" class="btn btn-primary" id="cmtModBtn">수정</button>
		      </div>
		    </div>
		  </div>
		</div>
		
	</div>
				
	<script type="text/javascript">
		const bnoValue = `<c:out value="${bvo.bno}" />`;
		const priUsername = `<c:out value="${pri.uvo.userId}" />`;
		console.log(bnoValue);
	</script>
	<script type="text/javascript" src="/resources/js/boardDetailComment.js"></script>
	
	<!-- detail.jsp 열면 댓글 출력 -->
	<script type="text/javascript">
		spreadCommentList(bnoValue);
	</script>
	
<jsp:include page="../layout/footer.jsp"></jsp:include>
