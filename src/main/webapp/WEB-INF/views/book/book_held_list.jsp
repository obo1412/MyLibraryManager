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
						<h6 class='pull-left'>도서 목록</h6>
					</div>
					<div class="card-body">
						<!-- 검색폼 + 추가버튼 -->
						<div style='margin-top: 30px;' class="pull-right">
							<form method='get'
								action='${pageContext.request.contextPath}/player/player_list.do'
								style="width: 300px;">
								<div class="input-group">
									<input type="text" name='keyword' class="form-control"
										placeholder="도서 검색" value="${keyword}" /> <span
										class="input-group-append">
										<button class="btn btn-success" type="submit">
											<i class='fas fa-search'></i>
										</button> <a href="${pageContext.request.contextPath}/book/reg_book.do"
										class="btn btn-primary">도서 추가</a>
									</span>
								</div>
							</form>
						</div>

						<!-- 조회결과를 출력하기 위한 표 -->
						<table class="table">
							<thead>
								<tr>
									<th class="info text-center">도서번호</th>
									<th class="info text-center">도서명</th>
									<th class="info text-center">도서저자</th>
									<th class="info text-center">등록일</th>
									<th class="info text-center">로컬바코드</th>
									<th class="info text-center">복본기호</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(bookHeldList) > 0}">
										<c:forEach var="item" items="${bookHeldList}">
											<tr>
												<td class="text-center">${item.id}</td>
												<td class="text-center">${item.titleBook}</td>
												<td class="text-center">${item.writerBook}</td>
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
						<%@ include file="/WEB-INF/inc/common_pagination_bottom.jsp" %>
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



