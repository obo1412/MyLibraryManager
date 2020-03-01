<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 오늘날짜 만들고, 날짜패턴 아래와같이 바꾸기 -->
<jsp:useBean id="currDate" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${currDate}" pattern="yyyy-MM-dd HH:mm:ss" />
<!-- 오늘날짜 관련 끝 -->
<!doctype html>
<html>
<head>
<meta charset='utf-8' />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>대출된 도서 목록</title>

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
						<h6 class='float-left'>대출된 도서 목록</h6>
					</div>
					<div class="card-body">
						<!-- 검색폼 + 추가버튼 -->
						<div style='margin-top: 30px;' class="pull-right">
							<form method='get'
								action='${pageContext.request.contextPath}/book/book_list_brwd.do'
								style="width: 300px;">
								<div class="input-group input-group-sm">
									<span class="input-group-prepend">
										<select name="searchOpt" class="form-control form-control-sm">
												<option value="1"
													<c:if test="${searchOpt == 1}">selected</c:if>>대출회원</option>
												<option value="2"
													<c:if test="${searchOpt == 2}">selected</c:if>>도서제목</option>
												<option value="3"
													<c:if test="${searchOpt == 3}">selected</c:if>>바코드</option>
										</select>
									</span>
									<input type="text" name='keyword' class="form-control form-control-sm"
										placeholder="검색" value="${keyword}" /> <span
										class="input-group-append">
										<button class="btn btn-success btn-sm" type="submit">
											<i class='fas fa-search'></i>
										</button>
									</span>
								</div>
							</form>
						</div>

						<!-- 조회결과를 출력하기 위한 표 -->
						<table class="table table-sm">
							<thead>
								<tr>
									<th class="info text-center">대여번호</th>
									<th class="info text-center">대출회원</th>
									<th class="info text-center">회원연락처</th>
									<th class="info text-center">도서제목</th>
									<th class="info text-center">도서바코드</th>
									<th class="info text-center">대여 일시</th>
									<th class="info text-center">반납 예정일</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(brwList) > 0}">
										<c:forEach var="item" items="${brwList}">
											<tr>
												<td class="text-center">${item.idBrw}</td>
												<td class="text-center">${item.name}</td>
												<td class="text-center">${item.phone}</td>
												<td class="text-center">${item.titleBook}</td>
												<td class="text-center">${item.localIdBarcode}</td>
												<td class="text-center">${item.startDateBrw}</td>
													<c:if test="${item.dueDateBrw < currentDate}">
														<c:set var="delay" value="text-danger" />
													</c:if>
												<td class="text-center ${delay}">${item.dueDateBrw}</td>
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
			<%@ include file="/WEB-INF/inc/footer.jsp"%>
		</div>
	</div>


	<%@ include file="/WEB-INF/inc/script-common.jsp"%>
</body>
</html>



