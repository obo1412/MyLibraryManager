<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="currDate" class="java.util.Date" />
<!doctype html>
<html>
<head>
<meta charset='utf-8' />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>보유도서 목록</title>

<%@ include file="/WEB-INF/inc/head.jsp"%>
	<style>
		table { 
			table-layout: fixed;
		}
		
		tr > td {
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
		}
	</style>
</head>

<body>
	<%@ include file="/WEB-INF/inc/topbar.jsp"%>

	<div id="wrapper">
		<%@ include file="/WEB-INF/inc/sidebar_left.jsp"%>
		<div id="content-wrapper">
			<div class="container-fluid">

				<div class="card mb-3">
					<div class="card-header">
						<h6 class='float-left'>도서 목록</h6>
					</div>
					<div class="card-body">
						<!-- 검색폼 + 추가버튼 -->
						<div style='margin: 10px auto;'>
							<div class="float-left">
								<form method='get'
									action='${pageContext.request.contextPath}/book/book_held_list.do'
									style="width: 300px;">
									<div class="input-group input-group-sm">
										<span class="input-group-prepend">
											<select name="searchOpt" class="form-control form-control-sm">
													<option value="1"
														<c:if test="${searchOpt == 1}">selected</c:if>>제목</option>
													<option value="2"
														<c:if test="${searchOpt == 2}">selected</c:if>>저자</option>
													<option value="3"
														<c:if test="${searchOpt == 3}">selected</c:if>>출판사</option>
											</select>
										</span> <input type="text" name='keyword'
											class="form-control form-control-sm" placeholder="도서 검색"
											value="${keyword}" autofocus/> <span class="input-group-append">
											<button class="btn btn-success btn-sm" type="submit">
												<i class='fas fa-search'></i>
											</button> <a href="${pageContext.request.contextPath}/book/reg_book.do"
											class="btn btn-primary btn-sm">도서 추가</a>
										</span>
									</div>
								</form>
							</div>
							
							<div class="float-right form-inline">
								<select name="yearOptionBookHeldList" id="yearOptionBookHeldList" class="form-control form-control-sm">
									<option value="">전체목록</option>
									<fmt:formatDate value="${currDate}" pattern="yyyy" var="yearStart" />
									<c:forEach begin="0" end="10" var="pastYear" step="1">
										<option value="<c:out value='${yearStart-pastYear}'/>">
											<c:out value='${yearStart-pastYear}'/>
										</option>
									</c:forEach>
								</select>
								<button class="btn btn-sm btn-secondary" onclick="clickedBookHeldListToExcel()">도서목록 엑셀변환</button>
							</div>
						</div>

						<!-- 조회결과를 출력하기 위한 표 -->
						<table class="table table-sm">
							<thead>
								<tr>
									<th class="info text-center" style="width:50px;">번호</th>
									<th class="info text-center" style="width:120px;">도서명</th>
									<th class="info text-center" style="width:70px;">저자명</th>
									<th class="info text-center" style="width:70px;">출판사</th>
									<th class="info text-center" style="width:70px;">출판일</th>
									<th class="info text-center" style="width:90px;">ISBN13</th>
									<th class="info text-center" style="width:80px;">청구기호</th>
									<th class="info text-center" style="width:80px;">등록일</th>
									<th class="info text-center" style="width:60px;">등록번호</th>
									<th class="info text-center" style="width:60px;">권차기호</th>
									<th class="info text-center" style="width:60px;">복본기호</th>
									<th class="info text-center" style="width:30px;">색상</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(bookHeldList) > 0}">
										<c:forEach var="item" items="${bookHeldList}" varStatus="status">
											<tr>
												<td class="text-center">${page.indexLast - status.index}</td>
												<td class="text-center" style="white-space:nowrap; text-overflow:ellipsis;">
													<c:url var="viewUrl"
														value="/book/book_held_edit.do">
														<c:param name="localIdBarcode"
															value="${item.localIdBarcode}" />
															<c:param name="bookHeldId"
															value="${item.id}" />
													</c:url> <a href="${viewUrl}"
													onclick="window.open(this.href, '_blank','width=550,height=800,scrollbars=yes');return false;">${item.title}</a>
												</td>
												<td class="text-center">${item.writer}</td>
												<td class="text-center">${item.publisher}</td>
												<fmt:parseDate var="parsePubDate" value="${item.pubDate}" pattern="yyyy-MM-dd"/>
												<fmt:formatDate var="pubDate" value="${parsePubDate}" pattern="yyyy-MM-dd" />
												<td class="text-center">${pubDate}</td>
												<td class="text-center">${item.isbn13}</td>
												<td class="text-center">
													<div>
														<c:if test="${not empty item.additionalCode}">${item.additionalCode} </c:if>
													</div>
													<div>
														<c:if test="${not empty item.classificationCode}">${item.classificationCode} </c:if>
													</div>
													<div>
														<c:if test="${not empty item.authorCode}">${item.authorCode} </c:if>
													</div>
													<div>
														<c:if test="${not empty item.volumeCode}">V${item.volumeCode} </c:if>
													</div>
													<div>
														<c:if test="${item.copyCode ne '0'}">C${item.copyCode} </c:if>
													</div>
												</td>
												<td class="text-center">${item.regDate}</td>
												<td class="text-center">${item.localIdBarcode}</td>
												<td class="text-center">
													<c:choose>
														<c:when test="${not empty item.volumeCode}">
															V${item.volumeCode}
														</c:when>
														<c:otherwise>
															-
														</c:otherwise>
													</c:choose>
												</td>
												<c:choose>
													<c:when test="${item.copyCode eq 0}">
														<td class="text-center">-</td>
													</c:when>
													<c:otherwise>
														<td class="text-center">C${item.copyCode}</td>
													</c:otherwise>
												</c:choose>
												<td class="text-center">
													<div style="margin:auto; width:30px; min-height:15px; background-color:${item.classCodeColor}"></div>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="8" class="text-center"
												style="line-height: 100px;">조회된 데이터가 없습니다.</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>

						<!-- 페이지 번호 -->
						<%@ include file="/WEB-INF/inc/common_pagination_bottom.jsp"%>
					</div>
					<!-- card body 끝 -->
				</div>
				<!-- card 끝 -->
			</div>
			<!-- container-fluid 끝 -->
			<%@ include file="/WEB-INF/inc/footer.jsp"%>
		</div>
	</div>

	<%@ include file="/WEB-INF/inc/script-common.jsp"%>
	
	<script type="text/javascript">
	
		function clickedBookHeldListToExcel() {
			var targetYear = document.getElementById('yearOptionBookHeldList').value;
			
			$.ajax({
				url: "${pageContext.request.contextPath}/book/book_held_list_to_excel.do",
				type: 'POST',
				data: {
					targetYear
				},
				/* dataType: "json", */
				success: function(data) {
					console.log('도서목록 엑셀 변환: '+data.rt);
					console.log('파일 경로: '+data.filePath);
					
					var uploadFolderPath = data.filePath.substring(data.filePath.lastIndexOf("upload"));
					console.log('업로드폴더까지 경로:'+uploadFolderPath);
					var downloadPath = "/files/"+uploadFolderPath;
					var fileName = data.filePath.substring(data.filePath.lastIndexOf("/")+1).split("?")[0];
					var xhr = new XMLHttpRequest();
						xhr.responseType = 'blob';
						xhr.onload = function() {
							var link = document.createElement('a');
							link.href = window.URL.createObjectURL(xhr.response); //xhr.response is a blob
							link.download = fileName;
							link.style.display = 'none';
							document.body.appendChild(link);
							link.click();
							delete link;
						};
						xhr.open('POST', downloadPath);
						xhr.setRequestHeader('Content-type', 'application/json');
						xhr.send();
				}
				,error:function(request,status,error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});
		};
	</script>
</body>
</html>



