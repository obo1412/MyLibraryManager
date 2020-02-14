<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8' />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>보유도서 목록</title>

<%@ include file="/WEB-INF/inc/head.jsp"%>
<style type="text/css">
.table-sm {
	font-size: 12px;
}
</style>
</head>

<body>

	<div id="wrapper">

		<div id="content-wrapper">
			<div class="container-fluid">

				<div class="card mb-3">
					<div class="card-header">
						<h6 class='pull-left'>국립중앙도서관 정보</h6>
					</div>
					<div class="card-body">
						<!-- 검색폼 + 추가버튼 -->
						<div style='margin-top: 30px;' class="pull-right">
							<form method='get'
								action='${pageContext.request.contextPath}/book/search_nl_book.do'
								style="width: 300px;">
								<div class="input-group input-group-sm">
									<span class="input-group-prepend">
										<select name="searchOpt" class="form-control form-control-sm">
											<option value="1" <c:if test="${searchOpt == 1}">selected</c:if>>
												제목</option>
											<option value="2" <c:if test="${searchOpt == 2}">selected</c:if>>
												저자</option>
											<option value="3" <c:if test="${searchOpt == 3}">selected</c:if>>
												출판사</option>
										</select>
									</span>
									<input type="text" name='keyword' id='keyword'
										class="form-control form-control-sm" placeholder="검색" value="${isbn}" />
									<span class="input-group-append">
										<button class="btn btn-sm btn-success" type="submit">
											<i class='fas fa-search'></i>
										</button>
									</span>
								</div>
							</form>

						</div>

<div>
${xmlArray}
</div>
						<!-- 조회결과를 출력하기 위한 표 -->
						<div class="table-responsive">
							<table class="table table-sm">
								<thead>
									<tr>
										<th class="info text-center">도서명</th>
										<th class="info text-center">도서저자</th>
										<th class="info text-center">출판사</th>
										<th class="info text-center">분류기호</th>
										<th class="info text-center">선택</th>
									</tr>
								</thead>
								<tbody>
									<%-- <c:choose>
										<c:when test="${fn:length(xmlArray) > 0}">
											<c:forEach var="item" items="${xmlArray}">
												<tr>
													<td class="text-center">${item.titleArray}</td>
													<td class="text-center">${item.authorArray}</td>
													<td class="text-center">${item.pubArray}</td>
													<td class="text-center">${item.classNoArray}</td>
													<td class="text-center"><c:set var="bar"
															value="" />
														<button class="pick-book btn btn-primary btn-sm" value="${bar}">
															선택</button></td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td colspan="8" class="text-center"
													style="line-height: 100px;">조회된 데이터가 없습니다.</td>
											</tr>
										</c:otherwise>
									</c:choose> --%>
								</tbody>
							</table>
						</div>

						<!-- 페이지 번호 -->
						<%@ include file="/WEB-INF/inc/common_pagination_bottom.jsp"%>
					</div>
					<!-- card body 끝 -->
				</div>
				<!-- card 끝 -->
			</div>
			<!-- container-fluid 끝 -->
			<%-- <%@ include file="/WEB-INF/inc/footer.jsp"%> --%>
		</div>
	</div>

	<%@ include file="/WEB-INF/inc/script-common.jsp"%>
	<script type="text/javascript">
		window.onload = function() {
			document.getElementById('keyword').focus();
		}

		$(document).on('click', '.pick-book', function() {
			$(opener.document).find("#barcodeBook").val($(this).val());
			window.close();
		});
	</script>
</body>
</html>



