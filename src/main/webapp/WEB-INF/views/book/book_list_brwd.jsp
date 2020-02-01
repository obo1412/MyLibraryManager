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
<title>대출된 도서 목록</title>

<%@ include file="/WEB-INF/inc/head.jsp"%>
</head>

<body>
	<%@ include file="/WEB-INF/inc/topbar.jsp"%>

	<div id="wrapper">
		<%@ include file="/WEB-INF/inc/sidebar_left.jsp"%>
		<div id="content-wrapper">
			<div class="container-fluid">

				<h1 class='pull-left'>대출 도서 목록</h1>
				<!-- 검색폼 + 추가버튼 -->
				<div style='margin-top: 30px;' class="pull-right">
					<form method='get'
						action='${pageContext.request.contextPath}/player/player_list.do'
						style="width: 300px;">
						<div class="input-group">
							<input type="text" name='keyword' class="form-control"
								placeholder="도서 검색" value="${keyword}" /> <span
								class="input-group-btn">
								<button class="btn btn-success" type="submit">
									<i class='glyphicon glyphicon-search'></i>
								</button> <a href="${pageContext.request.contextPath}/book/add_book.do"
								class="btn btn-primary">도서 추가</a>
							</span>
						</div>
					</form>
				</div>

				<!-- 조회결과를 출력하기 위한 표 -->
				<table class="table">
					<thead>
						<tr>
							<th class="info text-center">대여번호</th>
							<th class="info text-center">책 코드</th>
							<th class="info text-center">책 이름</th>
							<th class="info text-center">회원 번호</th>
							<th class="info text-center">회원 이름</th>
							<th class="info text-center">대여 일시</th>
							<th class="info text-center">반납 일시</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${fn:length(brwList) > 0}">
								<c:forEach var="item" items="${brwList}">
									<tr>
										<td class="text-center">${item.idBrw}</td>
										<td class="text-center">${item.idCodeBook}</td>
										<td class="text-center">${item.nameBook}</td>
										<td class="text-center">${item.idCode}</td>
										<td class="text-center">${item.name}</td>
										<td class="text-center">${item.startDateBrw}</td>
										<c:choose>
											<c:when test="${not empty item.endDateBrw}">
												<td class="text-center">${item.endDateBrw}</td>
											</c:when>
											<c:otherwise>
												<td class="text-center">미반납</td>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="8" class="text-center" style="line-height: 100px;">
										조회된 데이터가 없습니다.</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>

				<!-- 페이지 번호 -->
				<%@ include file="/WEB-INF/inc/common_pagination_bottom.jsp" %>
			</div>
			<%@ include file="/WEB-INF/inc/footer.jsp"%>
		</div>
	</div>


	<%@ include file="/WEB-INF/inc/script-common.jsp"%>
</body>
</html>



