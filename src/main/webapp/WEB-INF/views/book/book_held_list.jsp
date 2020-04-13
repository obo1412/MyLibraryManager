<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html>
<head>
<meta charset='utf-8' />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>보유도서 목록</title>

<%@ include file="/WEB-INF/inc/head.jsp"%>
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
						<div style='margin-top: 30px;' class="float-left">
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

						<!-- 조회결과를 출력하기 위한 표 -->
						<table class="table table-sm">
							<thead>
								<tr>
									<th class="info text-center">번호</th>
									<th class="info text-center">도서명</th>
									<th class="info text-center">저자명</th>
									<th class="info text-center">출판사</th>
									<th class="info text-center">출판일</th>
									<th class="info text-center">ISBN13</th>
									<th class="info text-center">청구기호</th>
									<th class="info text-center">등록일</th>
									<th class="info text-center">등록번호</th>
									<th class="info text-center">복본기호</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(bookHeldList) > 0}">
										<c:forEach var="item" items="${bookHeldList}" varStatus="status">
											<tr>
												<td class="text-center">${page.indexLast - status.index}</td>
												<td class="text-center"><c:url var="viewUrl"
														value="/book/book_held_view.do">
														<c:param name="localIdBarcode"
															value="${item.localIdBarcode}" />
															<c:param name="bookHeldId"
															value="${item.id}" />
													</c:url> <a href="${viewUrl}"
													onclick="window.open(this.href, '_blank','width=600,height=800,scrollbars=yes');return false;">${item.titleBook}</a>
												</td>
												<td class="text-center">${item.writerBook}</td>
												<td class="text-center">${item.publisherBook}</td>
												<td class="text-center">${item.pubDateBook}</td>
												<td class="text-center">${item.isbn13Book}</td>
												<td class="text-center"><c:if
														test="${not empty item.additionalCode}">${item.additionalCode}</c:if>
													<c:if test="${not empty item.classificationCode}">${item.classificationCode}</c:if>
													<c:if test="${not empty item.authorCode}">${item.authorCode}</c:if>
													<c:if test="${item.volumeCode ne '0' and (not empty item.volumeCode)}">v${item.volumeCode}</c:if>
													<c:if test="${item.copyCode ne '0'}">C${item.copyCode}</c:if>
												</td>
												<td class="text-center">${item.regDate}</td>
												<td class="text-center">${item.localIdBarcode}</td>
												<c:choose>
													<c:when test="${item.copyCode eq 0}">
														<td class="text-center">-</td>
													</c:when>
													<c:otherwise>
														<td class="text-center">C${item.copyCode}</td>
													</c:otherwise>
												</c:choose>
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
</body>
</html>



