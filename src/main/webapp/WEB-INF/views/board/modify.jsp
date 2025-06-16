<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../layout/header.jsp"></jsp:include>

	<div class="container-md">
	
		<h1>Board Modify Page</h1>
		<c:set value="${bdto.bvo }" var="bvo"></c:set>
		
		<form action="/board/update" method="post" enctype="multipart/form-data">
			<div class="mb-3">
				<label for="exampleFormControlInput1" class="form-label">Bno</label>
				<input type="text" class="form-control" name="bno" value="${bvo.bno }" readonly="readonly" id="exapleFormControlInput1">
			</div>
			<div class="mb-3">
				<label for="exampleFormControlTextarea1" class="form-label">title</label>
				<input type="text" class="form-control" name="title" value="${bvo.title }" id="exampleFormControlInput1">
			</div>
			<div class="mb-3">
				<label for="exampleFormControlTextarea1" class="form-label">Writer</label>
				<input type="text" class="form-control" name="writer" value="${bvo.writer }" readonly="readonly" id="exampleFormControlInput1">
			</div>
			<div class="mb-3">
				<label for="exampleFormControlTextarea1" class="form-label">Regdate</label>
				<input type="text" class="form-control" name="regDate" value="${bvo.regDate }" readonly="readonly" id="exampleFormControlInput1">
			</div>
			<div class="mb-3">
				<label for="exampleFormControlTextarea1" class="form-label">readCount</label>
				<input type="text" class="form-control" name="readCount" value="${bvo.readCount }" readonly="readonly" id="exampleFormControlInput1">
			</div>
			<div class="mb-3">
				<label for="exampleFormControlTextarea1" class="form-label">cmtQty</label>
				<input type="text" class="form-control" name="cmtQty" value="${bvo.cmtQty }" readonly="readonly" id="exampleFormControlInput1">
			</div>
			<div class="mb-3">
				<label for="exampleFormControlTextarea1" class="form-label">fileQty</label>
				<input type="text" class="form-control" name="fileQty" value="${bvo.fileQty }" readonly="readonly" id="exampleFormControlInput1">
			</div>
			<div class="mb-3">
				<label for="exampleFormControlTextarea1" class="form-label">content</label>
				<textarea class="form-control" name="content" id="exampleFormControlTextarea1" rows="5">${bvo.content }</textarea>
			</div>
			
			
			<!-- file upload print -->
			<div class="mb-3">
				<ul class="list-group list-group-flush">
				<!-- 파일 개수만큼 li 반복타입이 1이면 그림을 표시 아니면 그냥 파일모양으로 표시 -->
					<c:forEach items="${bdto.flist }" var="fvo">
						<li class="list-group-item">
						<c:choose>
							<c:when test="${fvo.fileType > 0 }">
								<!-- 그림파일 -->
								<div>
									<img alt="" src="/upload/${fvo.saveDir }/${fvo.uuid }_${fvo.fileName }">
								</div>
							</c:when>
							<c:otherwise>
								<!-- 일반파일 : 다운로드 가능 -->
								<a href="/upload/${fvo.saveDir }/${fvo.uuid}_${fvo.fileName}" download="${fvo.fileName }">
									<!-- 파일모양 아이콘 -->
									download
								</a>
							</c:otherwise>
						</c:choose>
						<div class="mb-3">
							<div class="fw-bold">${fvo.fileName }</div>
						</div>
						<span class="badge rounded-pill text-bg-info">${fvo.regDate } / ${fvo.fileSize } Bytes</span>
						<button
						type="button"
						class="btn btn-danger removeBtn"
						data-uuid="${fvo.uuid }"
						data-fileType="${fvo.fileType }"
						data-saveDir="${fvo.saveDir }"
						>remove</button>
						</li>
					</c:forEach>
				</ul>
			</div>
			
			<!-- 그림 추가 버튼 -->
			<!-- 파일 첨부 -->
			<div class="mb-3">
				<!-- <label for="file" class="form-label"></label> -->
				<input type="file" class="form-control" name="files" id="file" multiple="multiple" style="display:none">
				<button type="button" class="btn btn-success" id="trigger">file</button>
			</div>
			<!-- 파일 목록 -->
			<div class="mb-3" id="fileList">
			</div>
			<script type="text/javascript" src="/resources/js/boardRegisterFile.js"></script>
			<script type="text/javascript" src="/resources/js/boardModify.js"></script>
			 
			<a href="/board/list"><button type="button" class="btn btn-info">목록</button></a>
			<button type="submit" class="btn btn-primary" id="registerBtn">수정완료</button>
		</form>
		
	</div>
	
	
<jsp:include page="../layout/footer.jsp"></jsp:include>