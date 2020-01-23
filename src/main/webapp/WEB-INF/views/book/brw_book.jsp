<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/head.jsp"%>
<style type="text/css">
@media ( min-width : 768px) {
	.upsideCard {
		max-width: 1000px;
		float: left;
		width: 49%;
		margin-right: 0.2em;
	}
	.bottomCard {
		float: left;
		width: 100%;
	}
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/inc/topbar.jsp"%>
	<div id='wrapper'>
		<%@ include file="/WEB-INF/inc/sidebar_left.jsp"%>

		<div id="content-wrapper">

			<div class="container-fluid">
				<div class="card mb-3 upsideCard">
					<div class="card-header">
						<h6>도서 대여하기</h6>
					</div>

					<div class="card-body">
						<form class="form-horizontal" name="search-mbr-form"
							id="search-mbr-form" method="post"
							action="${pageContext.request.contextPath}/book/brw_book.do">
							<div class="form-group">
								<label for='search-name' class="col-md-12">회원 검색</label>
								<div class="input-group col-md-12">
									<input type="text" name="search-name" id="search-name"
										class="form-control" placeholder="이름을 입력해주세요" value="${name}" />
									<span class="input-group-btn">
										<button class="btn btn-warning" id="btn-search-mbr"
											type="submit">
											<i class="fas fa-search"></i>
										</button>
									</span>
								</div>
							</div>

							<!-- 							<div class="form-group">
								<label for='search-state' class="col-md-12">검색 상태</label>
								<p class="col-md-12" id="search-state"></p>
							</div> -->
						</form>
						<div class="table-responsive">
							<table class="table">
								<tbody>
									<c:choose>
										<c:when test="${fn:length(list) > 0}">
											<thead>
												<tr>
													<th class="info text-center">이름</th>
													<th class="info text-center">회원코드</th>
													<th class="info text-center">연락처</th>
													<th class="info text-center">회원등급</th>
													<th class="info text-center">대여권수</th>
													<th class="info text-center">대여기한</th>
													<th class="info text-center">선택</th>
												</tr>
											</thead>
											<c:forEach var="item" items="${list}" varStatus="status">
												<tr>
													<td><c:url var="readUrl" value="/temp/temp.do">
															<c:param name="id" value="${item.id}" />
														</c:url> <a href="${readUrl}">${item.name}</a></td>
													<td class="text-center"></td>
													<td class="text-center"><a href="#">${item.phone}</a></td>
													<td class="text-center">${item.gradeName}</td>
													<td class="text-center">${item.brwLimit}</td>
													<td class="text-center">${item.dateLimit}</td>
													<td class="test-center">
														<button class="pick-user btn" id="${status.index}">선택
														</button>
													</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:when test="${name ne null}">
											<thead>
												<tr>
													<th class="info text-center">이름</th>
													<th class="info text-center">회원코드</th>
													<th class="info text-center">연락처</th>
													<th class="info text-center">회원등급</th>
													<th class="info text-center">대여권수</th>
													<th class="info text-center">대여기한</th>
													<th class="info text-center">선택</th>
												</tr>
											</thead>
												<tr>
													<td><c:url var="readUrl" value="/temp/temp.do">
															<c:param name="id" value="${memberId}" />
														</c:url> <a href="${readUrl}">${name}</a></td>
													<td class="text-center"></td>
													<td class="text-center"><a href="#">${phone}</a></td>
													<td class="text-center">${grade}</td>
													<td class="text-center">${brwLimit}</td>
													<td class="text-center">${dateLimit}</td>
													<td class="test-center">
														<button class="pick-user btn" id="${status.index}">선택
														</button>
													</td>
												</tr>
										</c:when>
										<c:otherwise>
											<tr>
												<td colspan="8" class="text-center"
													style="line-height: 30px;">조회된 회원정보가 없습니다.</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>

						<!-- 회원정보, 도서정보 수집 시작 -->
						<form class="form-horizontal" name="myform" method="post"
							action="${pageContext.request.contextPath}/book/brw_book_ok.do">

							<input type="hidden" name="memberId" id="memberId"
								value="${memberId}" />

							<div class="form-group">
								<label for='name' class="col-md-12">회원 이름</label>
								<div class="col-md-12">
									<input type="text" name="name" id="name" class="form-control"
										value="${name}" />
								</div>
							</div>

							<div class="form-group">
								<label for='tel' class="col-md-12">연락처</label>
								<div class="col-md-12">
									<input type="tel" name="phone" id="phone" class="form-control"
										value="${phone}" />
								</div>
							</div>

							<div class="form-group">
							<!-- 	<label for='grade' class="col-md-12">회원 등급</label> -->
								<div class="col-md-12">
									<input type="hidden" name="grade" id="grade" class="form-control"
										value="${grade}" />
								</div>
							</div>

							<div class="form-group">
								<!-- <label for='brwLimit' class="col-md-12">대여가능</label> -->
								<div class="col-md-12">
									<input type="hidden" name="brwLimit" id="brwLimit"
										class="form-control" value="${brwLimit}" />
								</div>
							</div>

							<div class="form-group">
								<!-- <label for='name' class="col-md-12">대여중</label> -->
								<div class="col-md-12">
									<input type="hidden" name="borrowed" id="borrowed"
										class="form-control" />
								</div>
							</div>

							<div class="form-group">
								<!-- <label for='dateLimit' class="col-md-12">대여기한</label> -->
								<div class="col-md-12">
									<input type="hidden" name="dateLimit" id="dateLimit"
										class="form-control" value="${dateLimit}" />
								</div>
							</div>

							<div class="form-group">
								<label for='barcodeBook' class="col-md-12">도서바코드</label>
								<div class="col-md-12">
									<input type="text" name="barcodeBook" id="barcodeBook"
										class="form-control" />
								</div>
							</div>


							<div class="form-group">
								<div class="offset-md-6 col-md-6">
									<button type="submit" class="btn btn-primary">제출</button>
									<button type="reset" class="btn btn-danger">취소</button>
								</div>
							</div>
						</form>
						<!-- 회원정보, 도서정보 끝 -->
					</div>
					<!-- card body 끝 -->
				</div>
				<!-- card 끝 -->

				<div class="card mb-3 upsideCard">
					<div class="card-header">
						<h6>반납하기</h6>
					</div>
					<div class="card-body"></div>
					<!-- card body2 끝 -->
				</div>
				<!-- card2 끝 -->

				<div class="card mb-3 bottomCard">
					<div class="card-header">
						<h4 class='pull-left'>대여 목록</h4>
					</div>
					<div class="card-body">
						<!-- 조회결과를 출력하기 위한 표 -->
						<table class="table">
							<thead>
								<tr>
									<th class="info text-center">상태</th>
									<th class="info text-center">이름</th>
									<th class="info text-center">연락처</th>
									<th class="info text-center">회원등급</th>
									<th class="info text-center">도서명</th>
									<th class="info text-center">대여일시</th>
									<th class="info text-center">반납일시</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(brwList) > 0}">
										<c:forEach var="item" items="${brwList}">
											<tr>
											<c:choose>
												<c:when test="${item.endDateBrw eq null}">
													<c:set var="state" value="대여" />
												</c:when>
												<c:otherwise>
													<c:set var="state" value="반납" />
												</c:otherwise>
											</c:choose>
												<td class="text-center">${state}</td>
												<td class="text-center">${item.name}</td>
												<td class="text-center">${item.phone}</td>
												<td class="text-center">${item.gradeId}</td>
												<td class="text-center">${item.titleBook}</td>
												<td class="text-center">${item.startDateBrw}</td>
												<td class="text-center">${item.endDateBrw}</td>
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

					</div>
					<!-- card3 body 끝 -->
				</div>
				<!-- card3 끝 -->
			</div>
			<!-- container-fluid 끝 -->
			<%@ include file="/WEB-INF/inc/footer.jsp"%>
		</div>
		<!-- content wrapper 끝 -->
	</div>
	<!-- wrapper 끝 -->

	<%@ include file="/WEB-INF/inc/script-common.jsp"%>
</body>
<script type="text/javascript">
/**
 * 아래 기능확인해서 사용하기!!!!
 */
/* 	$(function() {

		var CountMember = $
		{
			CountMember
		}
		;
		console.log(CountMember);
		if (CountMember == 0) {
			$('#search-state').html("회원 정보를 찾을 수 없습니다.");
		}

		$(".pick-user").on("click", function(e) {
			var x = $(this).attr('id');
			console.log(x);

			var PIdList = [];
			var PnameList = [];
			var PidCodeList = [];
			var PphoneList = [];
			var PlevelList = [];

			<c:forEach var="item" items='${list}'>
			PIdList.push("${item.id}");
			PnameList.push("${item.name}");
			PidCodeList.push("${item.idCode}");
			PphoneList.push("${item.phone}");
			PlevelList.push("${item.level}");
			</c:forEach>

			$("#memberId").val(PIdList[x]);
			$("#name").val(PnameList[x]);
			$("#idCode").val(PidCodeList[x]);
			$("#phone").val(PphoneList[x]);
			$("#level").val(PlevelList[x]);
			e.preventDefault();
		}); */

		/* 	
		 var name = null;
		 var urlName = null;
		 name = $("#search-name").val();
		 urlName = '${pageContext.request.contextPath}/member/member_list.do?name='+name;
		 if(CountMember > 1){
		 window.open(urlName, 'window', 'width=300', 'height=300');
		
		 $.ajax({
		 url: "${pageContext.request.contextPath}/member/member_list.do?name="+name,
		 method: 'get',
		 data: {
		 },
		 dataType: 'html',
		 sucess: function() {
		 pop.location;
		 }
		 });
		
		 } else if(CountMember == 0) {
		 $('#search-state').html("회원 정보를 찾을 수 없습니다.");
		 }
		 console.log(name);
		 */
	});
</script>
</html>