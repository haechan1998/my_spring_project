<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<jsp:include page="layout/header.jsp"></jsp:include>
	
	<div class="inner">
		<p>메인페이지 표시용~</p>
		
		<form class="d-flex justify-content-center" role="search" action="/">
			<select class="form-select w-25" name="type" aria-label="Default select example">
				<option ${ph.pgvo.type eq null ? 'selected' : ''}>select</option>
				<option ${ph.pgvo.type eq 't' ? 'selected' : ''} value="t">Title</option>
				<option ${ph.pgvo.type eq 'w' ? 'selected' : ''} value="w">Writer</option>
				<option ${ph.pgvo.type eq 'c' ? 'selected' : ''} value="c">Content</option>
				<option ${ph.pgvo.type eq 'tw' ? 'selected' : ''} value="tw">Title + Writer</option>
				<option ${ph.pgvo.type eq 'tc' ? 'selected' : ''} value="tc">Title + Content</option>
				<option ${ph.pgvo.type eq 'wc' ? 'selected' : ''} value="wc">Writer + content</option>
				<option ${ph.pgvo.type eq 'twc' ? 'selected' : ''} value="twc">All</option>
			</select>
			
			<input class="form-control me-2" name="keyword" type="search" placeholder="Search"
				aria-label="Search" />
			<input type="hidden" name="pageNo" value="1">
			<input type="hidden" name="qty" value="${ph.pgvo.qty }">
			
			<button type="submit" class="btn btn-success btn-sm position-relative">
				 Search  
				<span class="position-absolute top-0 start-100 translate-middle badge text-bg-primary">${ph.totalCount }</span>
				<span class="visually-hidden">unread messages</span>
			</button>
		</form>
		
		<c:forEach items="${boardList }" var="bvo">
			<div>${bvo.bno } / 제목 : <a href="/board/detail?bno=${bvo.bno }">${bvo.title }</a> / 작성자 : ${bvo.writer} </div>
		</c:forEach>
		
		
		
		<%-- paging line --%>
		<nav aria-label="Page navigation example">
			<ul class="pagination justify-content-center">
	
				<%-- 이전  --%>
				<li class="page-item ${ph.prev eq false ? 'disabled' : '' }"><a
					class="page-link"
					href="/?pageNo=${ph.startPage - 1 }&qty=${ph.pgvo.qty}"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
	
	
				<c:forEach begin="${ph.startPage }" end="${ph.endPage }" var="i">
					<li class="page-item ${ph.pgvo.pageNo eq i ? 'active' : ''}"><a
						class="page-link"
						href="/?pageNo=${i }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}">${i}</a></li>
				</c:forEach>
	
				<%-- 다음  --%>
				<li class="page-item ${ph.next eq false ? 'disabled' : '' }"><a
					class="page-link"
					href="/?pageNo=${ph.endPage + 1 }&qty=${ph.pgvo.qty}"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
	
			</ul>
		</nav>	
	</div>
	
<jsp:include page="layout/footer.jsp"></jsp:include>
