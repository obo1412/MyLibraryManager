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
						<h4 class='pull-left'>회원 목록</h4>
					</div>
					<div class="card-body">
						<!-- 검색폼 + 추가버튼 -->
						<div style='margin-top: 30px;' class="pull-right">
							<form method='get'
								action='${pageContext.request.contextPath}/member/member_list.do'
								style="width: 300px;">
								<div class="input-group input-group-sm">
									<input type="text" name='keyword' class="form-control"
										placeholder="회원 이름 검색" value="${keyword}" autofocus/> <span
										class="input-group-append">
											<button class="btn btn-success btn-sm" type="submit">
												<i class='fas fa-search'></i>
											</button>
										<a href="${pageContext.request.contextPath}/member/join.do"
										class="btn btn-primary btn-sm">회원 추가</a>
									</span>
								</div>
							</form>
						</div>

						<!-- 조회결과를 출력하기 위한 표 -->

						<table class="table table-sm">
							<thead>
								<tr>
									<th class="table-info text-center" style="width:30px;">번호</th>
									<th class="table-info text-center" style="width:50px;">이름</th>
									<th class="table-info text-center" style="width:60px;">생년월일</th>
									<th class="table-info text-center" style="width:70px;">연락처</th>
									<th class="table-info text-center" style="width:100px;">이메일</th>
									<th class="table-info text-center" style="width:50px;">회원등급</th>
									<th class="table-info text-center" style="width:50px;">대출제한(권)</th>
									<th class="table-info text-center" style="width:50px;">대출기한(일)</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(list) > 0}">
										<c:forEach var="item" items="${list}" varStatus="status">
											<tr>
												<td class="text-center">${page.indexLast - status.index}</td>
												<td class="text-center"><c:url var="readUrl" value="/member/member_view.do">
														<c:param name="memberId" value="${item.id}" />
													</c:url>
													<a href="${readUrl}" onclick="window.open(this.href, '_blank','width=400,height=650,scrollbars=yes');return false;">
														${item.name}
													</a>
												</td>
												<td class="text-center" id="userCode"><fmt:parseDate
														var="birthdateStr" value="${item.birthdate}"
														pattern="yyyy-MM-dd" /> <!-- DB에서 가져온 String형 데이터를 Date로 변환
											var 변수명, pattern 날짜형식, value 컨트롤러에서 받은값 var에 저장 --> <fmt:formatDate
														var="birthdateFmt" value="${birthdateStr}"
														pattern="yyyy-MM-dd" /> <!-- Date형으로 저장된 값을 String으로 변환 var변수에 넣고 아래처럼 변수를 사용 -->
													${birthdateFmt}</td>
												<td class="text-center">${item.phone}</td>
												<td class="text-center">${item.email}</td>
												<td class="text-center">${item.gradeName}</td>
												<td class="text-center">${item.brwLimit}</td>
												<td class="text-center">${item.dateLimit}</td>
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
			<!-- container fluid 끝 -->
			<%@ include file="/WEB-INF/inc/footer.jsp"%>
		</div>
		<!-- content wrapper 끝 -->
	</div>
	<!-- wrapper 끝 -->


	<%@ include file="/WEB-INF/inc/script-common.jsp"%>
</body>
</html>



