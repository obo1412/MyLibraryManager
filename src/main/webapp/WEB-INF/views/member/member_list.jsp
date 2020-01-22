<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!doctype html>
<html>
<head>
<meta charset='utf-8' />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>회원 목록</title>

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
						<h4 class='pull-left'>회원 목록</h4>
					</div>
					<div class="card-body">
						<!-- 검색폼 + 추가버튼 -->
						<div style='margin-top: 30px;' class="pull-right">
							<form method='get'
								action='${pageContext.request.contextPath}/player/player_list.do'
								style="width: 300px;">
								<div class="input-group">
									<input type="text" name='keyword' class="form-control"
										placeholder="회원 이름 검색" value="${keyword}" /> <span
										class="input-group-append">
											<button class="btn btn-success" type="submit">
												<i class='fas fa-search'></i>
											</button>
										<a href="${pageContext.request.contextPath}/member/join.do"
										class="btn btn-primary">회원 추가</a>
									</span>
								</div>
							</form>
						</div>

						<!-- 조회결과를 출력하기 위한 표 -->

						<table class="table">
							<thead>
								<tr>
									<th class="info text-center">등록번호</th>
									<th class="info text-center">이름</th>
									<th class="info text-center">생년월일</th>
									<th class="info text-center">연락처</th>
									<th class="info text-center">이메일</th>
									<th class="info text-center">회원등급</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(list) > 0}">
										<c:forEach var="item" items="${list}">
											<tr>
												<td class="text-center">${item.id}</td>
												<td><c:url var="readUrl" value="/player/player_view.do">
														<c:param name="id" value="${item.id}" />
													</c:url> <a href="${readUrl}">${item.name}</a></td>
												<td class="text-center" id="userCode"><fmt:parseDate
														var="birthdateStr" value="${item.birthdate}"
														pattern="yyyy-MM-dd" /> <!-- DB에서 가져온 String형 데이터를 Date로 변환
											var 변수명, pattern 날짜형식, value 컨트롤러에서 받은값 var에 저장 --> <fmt:formatDate
														var="birthdateFmt" value="${birthdateStr}"
														pattern="yyyy-MM-dd" /> <!-- Date형으로 저장된 값을 String으로 변환 var변수에 넣고 아래처럼 변수를 사용 -->
													${birthdateFmt}</td>
												<td class="text-center">${item.phone}</td>
												<td class="text-center">${item.email}</td>
												<td class="text-center">${item.gradeId}</td>
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
						<nav class="text-center">
							<ul class="pagination">
								<!-- 이전 그룹으로 이동 -->
								<c:choose>
									<c:when test="${page.prevPage > 0}">
										<!-- 이전 그룹에 대한 페이지 번호가 존재한다면? -->
										<!-- 이전 그룹으로 이동하기 위한 URL을 생성해서 "prevUrl"에 저장 -->
										<c:url var="prevUrl" value="/player/player_list.do">
											<c:param name="keyword" value="${keyword}"></c:param>
											<c:param name="page" value="${page.prevPage}"></c:param>
										</c:url>

										<li><a href="${prevUrl}">&laquo;</a></li>
									</c:when>

									<c:otherwise>
										<!-- 이전 그룹에 대한 페이지 번호가 존재하지 않는다면? -->
										<li class='disabled'><a href="#">&laquo;</a></li>
									</c:otherwise>
								</c:choose>

								<!-- 페이지 번호 -->
								<!-- 현재 그룹의 시작페이지~끝페이지 사이를 1씩 증가하면서 반복 -->
								<c:forEach var="i" begin="${page.startPage}"
									end="${page.endPage}" step="1">
									<!-- 각 페이지 번호로 이동할 수 있는 URL을 생성하여 page_url에 저장 -->
									<c:url var="pageUrl" value="/player/player_list.do">
										<c:param name="keyword" value="${keyword}"></c:param>
										<c:param name="page" value="${i}"></c:param>
									</c:url>

									<!-- 반복중의 페이지 번호와 현재 위치한 페이지 번호가 같은 경우에 대한 분기 -->
									<c:choose>
										<c:when test="${page.page == i}">
											<li class='active'><a href="#">${i}</a></li>
										</c:when>
										<c:otherwise>
											<li><a href="${pageUrl}">${i}</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>

								<!-- 다음 그룹으로 이동 -->
								<c:choose>
									<c:when test="${page.nextPage > 0}">
										<!-- 다음 그룹에 대한 페이지 번호가 존재한다면? -->
										<!-- 다음 그룹으로 이동하기 위한 URL을 생성해서 "nextUrl"에 저장 -->
										<c:url var="nextUrl" value="/player/player_list.do">
											<c:param name="keyword" value="${keyword}"></c:param>
											<c:param name="page" value="${page.nextPage}"></c:param>
										</c:url>

										<li><a href="${nextUrl}">&raquo;</a></li>
									</c:when>

									<c:otherwise>
										<!-- 이전 그룹에 대한 페이지 번호가 존재하지 않는다면? -->
										<li class='disabled'><a href="#">&raquo;</a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</nav>
					</div>
					<!-- card body 끝 -->
				</div>
				<!-- card 끝 -->
			</div>
			<!-- container fluid 끝 -->
			<%@ include file="/WEB-INF/inc/footer.jsp"%>
		</div>
		<!-- content wrapper 끝 -->
	</div>
	<!-- wrapper 끝 -->


	<%@ include file="/WEB-INF/inc/script-common.jsp"%>
</body>
</html>



