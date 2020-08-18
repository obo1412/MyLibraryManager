<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!-- 오늘날짜 만들고, 날짜패턴 아래와같이 바꾸기 -->
<jsp:useBean id="currDate" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${currDate}" pattern="yyyy-MM-dd HH:mm:ss" />
<!-- 오늘날짜 관련 끝 -->
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/inc/head.jsp"%>
<style type="text/css">
@media ( min-width : 768px) {
	.upsideCard {
		max-width: 500px;
		float: left;
		width: 49%;
		margin-right: 0.2em;
		min-height: 480px;
	}
	.bottomCard {
		float: left;
		width: 100%;
		/* 페이지가 모니터 최대치일 경우, 아래칸이 위로 올라옴 */
		clear: left;
		min-height: 300px;
	}
	.table-sm {
		font-size: 12px;
	}
	label {
		margin-bottom: 0;
		font-size: 14px;
	}
	.btn-sm {
		font-size: 10px;
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
						<h6>
							<label for='search-name'>도서 대출하기</label>
						</h6>
					</div>

					<div class="card-body">
						<form class="form-horizontal" name="search-mbr-form"
							id="search-mbr-form" method="post"
							action="${pageContext.request.contextPath}/book/brw_book.do">
							<div class="form-group form-inline">
								<label for='search-name' class="col-md-3 float-right">회원
									검색</label>
								<div class="input-group input-group-sm col-md-9">
									<input type="text" name="search-name" id="search-name"
										class="form-control korean-first" placeholder="이름을 입력해주세요" value="${name}" />
									<span class="input-group-append">
										<button class="btn btn-sm btn-warning" id="btn-search-mbr"
											type="submit">
											<i class="fas fa-search"></i>
										</button>
									</span>
								</div>
							</div>
						</form>

						<div class="table-responsive">
							<table class="table table-sm">
								<tbody>
									<c:choose>
										<c:when test="${fn:length(list) > 0}">
											<thead>
												<tr>
													<th class="table-info text-center">이름</th>
													<!-- <th class="info text-center">회원코드</th> -->
													<th class="table-info text-center">연락처</th>
													<th class="table-info text-center">회원등급</th>
													<th class="table-info text-center">대출권수</th>
													<th class="table-info text-center">대출기한</th>
													<th class="table-info text-center">선택</th>
												</tr>
											</thead>
											<c:forEach var="item" items="${list}" varStatus="status">
												<tr>
													<td style="display: none; position: absolute;">${item.id}</td>
													<td class="text-center"><c:url var="readUrl"
															value="/temp/temp.do">
															<c:param name="id" value="${item.id}" />
														</c:url> <a href="${readUrl}">${item.name}</a></td>
													<%-- <td class="text-center">${barcodeMbr}</td> --%>
													<td class="text-center">${item.phone}</td>
													<td class="text-center">${item.gradeName}</td>
													<td class="text-center">${item.brwLimit}</td>
													<td class="text-center">${item.dateLimit}</td>
													<td class="text-center"><c:url var="mbrIdUrl"
															value="/book/brw_book.do">
															<c:param name="memberId" value="${item.id}" />
															<c:param name="name" value="${item.name}" />
															<c:param name="phone" value="${item.phone}" />
															<c:param name="brwLimit" value="${item.brwLimit}" />
														</c:url>
														<button class="pick-user btn btn-secondary btn-sm"
															id="${status.index}"
															onclick="location.href='${mbrIdUrl}'">선택</button></td>
												</tr>
											</c:forEach>
										</c:when>
										<%-- <c:when test="${name ne null}"> --%>
										<c:when test="${fn:length(list)==1}">
											<thead>
												<tr>
													<th class="table-info text-center">이름</th>
													<!-- <th class="info text-center">회원코드</th> -->
													<th class="table-info text-center">연락처</th>
													<th class="table-info text-center">회원등급</th>
													<th class="table-info text-center">대출권수</th>
													<th class="table-info text-center">대출기한</th>
													<th class="table-info text-center">선택</th>
												</tr>
											</thead>
											<tr>
												<td class="text-center"><c:url var="readUrl"
														value="/temp/temp.do">
														<c:param name="id" value="${memberId}" />
													</c:url> <a href="${readUrl}">${name}</a></td>
												<%-- <td class="text-center">${barcodeMbr}</td> --%>
												<td class="text-center">${phone}</td>
												<td class="text-center">${grade}</td>
												<td class="text-center">${brwLimit}</td>
												<td class="text-center">${dateLimit}</td>
												<td class="text-center">
													<button class="pick-user btn btn-secondary btn-sm"
														id="${status.index}">-</button>
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

							<div class="form-inline mb-2">
								<div class="form-group col-md-12">
									<label for='name' class="col-md-4">회원이름</label>
									<div class="input-group input-group-sm col-md-7">
										<input type="text" name="name" id="name" class="form-control"
											value="${name}" readonly />
									</div>
								</div>
							</div>

							<div class="form-inline mb-2">
								<div class="form-group col-md-12">
									<label for='phone' class="col-md-4">연락처</label>
									<div class="input-group input-group-sm col-md-7">
										<input type="tel" name="phone" id="phone" class="form-control"
											value="${phone}" readonly />
									</div>
								</div>
							</div>
							
							<div class="form-inline mb-3">
								<div class="form-group col-md-4">
									<label for='brwLimit' class="col-md-12">대출한도</label>
									<div class="col-md-12 text-center">
										<input type="text" name="brwLimit" id="brwLimit"
											class="form-control form-control-sm" value="${brwLimit}"
											style="width:50%" readonly/>
									</div>
								</div>

								<div class="form-group col-md-4">
									<label for='brwNow' class="col-md-12">대출중</label>
									<div class="col-md-12 text-center">
										<input type="text" name="brwNow" id="brwNow"
											class="form-control form-control-sm" value="${brwNow}"
											style="width:50%" readonly />
									</div>
								</div>

								<div class="form-group col-md-4">
									<label for='brwPsb' class="col-md-12">대출가능</label>
									<div class="col-md-12 text-center">
										<c:set var="brwWarning" value="" />
									<c:if test="${brwLimit != 0 and (brwPsb < 1)}">
										<c:set var="brwWarning" value="bg-danger text-white" />
									</c:if>
										<input type="text" name="brwPsb" id="brwPsb"
											class="form-control form-control-sm ${brwWarning}"
											value="${brwPsb}" style="width:50%" readonly />
									</div>
								</div>
							</div>
							
							<div class="col-md-12 mb-3" style="font-size:14px;">
								<c:choose>
								<c:when test="${overDueCount > 0}">
									<p class="bg-danger text-white">${overDueCount}권의 연체중인 도서가 존재합니다.</p>
								</c:when>
								<c:when test="${restrictDate ne null}">
									<fmt:parseDate var="rstDate" value="${restrictDate}" pattern="yyyy-MM-dd" />
									<fmt:formatDate var="viewRstDate" value="${rstDate}" pattern="yyyy-MM-dd" />
									<p class="bg-danger text-white">${viewRstDate}까지 도서 대출이 제한됩니다.</p>
								</c:when>
								<c:when test="${(name eq null)||(name eq '')}">
									<p> </p>
								</c:when>
								<c:otherwise>
									<p class="bg-success text-white">대출 가능 회원입니다.</p>
								</c:otherwise>
								</c:choose>
							</div>

							<div class="form-inline mb-2">
								<div class="form-group col-md-12">
									<label for='barcodeBook' class="col-md-4">도서등록번호</label>
									<div class="input-group input-group-sm col-md-7">
										<span class="input-group-prepend"> <input type="button"
											value="검색" class="btn btn-sm btn-warning fas fa-search english-first"
											onclick="window.open('${pageContext.request.contextPath}/book/book_held_list_popup.do', '_blank', 'width=750,height=700,scrollbars=yes')" />
										</span> <input type="text" name="barcodeBook" id="barcodeBook"
											class="form-control" placeholder="or 직접 입력" />
									</div>
								</div>
							</div>

							<div class="form-group">
								<!-- 	<label for='grade' class="col-md-12">회원 등급</label> -->
								<div class="col-md-12">
									<input type="hidden" name="grade" id="grade"
										class="form-control" value="${grade}" />
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
								<div class="offset-md-7 col-md-5">
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

				<div class="card mb-3 upsideCard clear">
					<div class="card-header">
						<h6>
							<label for='barcodeBookRtn'>반납하기</label>
						</h6>
					</div>
					<div class="card-body">
						<form class="form-horizontal" name="search-mbr-form"
							id="search-mbr-form" method="post"
							action="${pageContext.request.contextPath}/book/return_book_ok.do">
							<div class="form-group form-inline">
								<label for='barcodeBookRtn' class="col-md-3">도서 검색</label>
								<div class="input-group input-group-sm col-md-9">
									<input type="text" name="barcodeBookRtn" id="barcodeBookRtn"
										class="form-control" placeholder="도서바코드를 입력해주세요"
										value="${barcodeBook}" /> <span class="input-group-append">
										<button class="btn btn-warning btn-sm" id="btn-search-mbr"
											type="submit">반납</button>
									</span>
								</div>
							</div>
						</form>
						<!-- 도서 검색폼 -->

						<div class="table-responsive">
							<table class="table table-sm">
								<tbody>
									<c:choose>
										<c:when test="${fn:length(brwRmnList) > 0}">
											<thead>
												<tr>
													<th class="table-info text-center">도서제목</th>
													<th class="table-info text-center">등록번호</th>
													<th class="table-info text-center">대출일</th>
													<th class="table-info text-center">반납일</th>
													<th class="table-info text-center">상태</th>
												</tr>
											</thead>
											<c:forEach var="item" items="${brwRmnList}"
												varStatus="status">
												<tr>
													<td class="text-center">${item.titleBook}</td>
													<td class="text-center">${item.localIdBarcode}</td>
													<td class="text-center">
													<fmt:parseDate var="formDate"
															value="${item.startDateBrw}"
															pattern="yyyy-MM-dd HH:mm:ss" /> <fmt:formatDate
															var="viewDate" value="${formDate}"
															pattern="yyyy-MM-dd HH:mm:ss" /> ${viewDate}</td>
													<td class="text-center">${item.endDateBrw}</td>
													<td class="text-center"><c:choose>
															<c:when
																test="${item.endDateBrw eq null || item.endDateBrw eq ''}">
																<c:set var="rtnBtnState" value="반납" />
																<c:set var="classRtnBtn" value="btn btn-warning btn-sm" />
																<c:if test="${currentDate > item.dueDateBrw}">
																	<c:set var="rtnBtnState" value="연체" />
																	<c:set var="classRtnBtn" value="btn btn-danger btn-sm text-white" />
																</c:if>
																<c:url var="rtnUrl" value="/book/return_book_ok.do">
																	<c:param name="barcodeBookRtn"
																		value="${item.localIdBarcode}" />
																	<c:param name="idBrw" value="${item.idBrw}" />
																	<c:param name="idMemberBrw" value="${item.idMemberBrw}" />
																	<c:param name="name" value="${item.name}" />
																	<c:param name="phone" value="${item.phone}" />
																	<c:param name="brwLimit" value="${item.brwLimit}" />
																</c:url>
															</c:when>
															<c:otherwise>
																<c:set var="rtnBtnState" value="취소" />
																<c:set var="classRtnBtn" value="btn btn-primary btn-sm" />
																<c:url var="rtnUrl"
																	value="/book/return_cancel_book_ok.do">
																	<c:param name="barcodeBookRtnCancle"
																		value="${item.localIdBarcode}" />
																	<c:param name="idBrw" value="${item.idBrw}" />
																	<c:param name="idMemberBrw" value="${item.idMemberBrw}" />
																	<c:param name="name" value="${item.name}" />
																	<c:param name="phone" value="${item.phone}" />
																	<c:param name="brwLimit" value="${item.brwLimit}" />
																</c:url>
															</c:otherwise>
														</c:choose>
														<button class="return-book ${classRtnBtn}"
															onclick="location.href='${rtnUrl}'">
															${rtnBtnState}</button></td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td colspan="8" class="text-center"
													style="line-height: 30px;">조회된 도서 및 회원 정보가 없습니다.</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
						<!-- 도서 검색시, 그 도서를 빌린 사람의 대여현황을 보여주기 위함. -->
					</div>
					<!-- card body2 끝 -->
				</div>
				<!-- card2 끝 -->

				<div class="card mb-3 bottomCard">
					<div class="card-header">
						<h4 class='pull-left'>오늘의 대출/반납 현황</h4>
					</div>
					<div class="card-body">
						<!-- 조회결과를 출력하기 위한 표 -->
						<div class="table-responsive">
							<table class="table table-sm">
								<thead>
									<tr>
										<th class="info text-center">번호</th>
										<th class="info text-center">상태</th>
										<th class="info text-center">이름</th>
										<th class="info text-center">연락처</th>
										<th class="info text-center">회원등급</th>
										<th class="info text-center">도서명</th>
										<th class="info text-center">도서등록번호</th>
										<th class="info text-center">반납예정일</th>
										<th class="info text-center">대출일</th>
										<th class="info text-center">반납일</th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(brwListToday) > 0}">
											<c:forEach var="item" items="${brwListToday}" varStatus="status">
												<tr>
													<td class="text-center">${status.count}</td>
													<c:choose>
														<c:when
															test="${item.endDateBrw eq null || item.endDateBrw eq ''}">
															<c:set var="state" value="대출" />
															<c:set var="classBtn" value="btn btn-warning" />
														</c:when>
														<c:otherwise>
															<c:set var="state" value="반납" />
															<c:set var="classBtn" value="btn btn-primary" />
														</c:otherwise>
													</c:choose>
													<td class="text-center ${classBtn}"
														style="font-size: 12px;">${state}</td>
													<td class="text-center">${item.name}</td>
													<td class="text-center">${item.phone}</td>
													<td class="text-center">${item.gradeName}</td>
													<td class="text-center">${item.titleBook}</td>
													<td class="text-center">${item.localIdBarcode}</td>
													<td class="text-center"><fmt:parseDate var="dueDate" value="${item.dueDateBrw}"
															pattern="yyyy-MM-dd" /> <fmt:formatDate
															var="dViewDate" value="${dueDate}"
															pattern="yyyy-MM-dd" /> ${dViewDate}</td>
													<td class="text-center"><fmt:parseDate var="formDate"
															value="${item.startDateBrw}"
															pattern="yyyy-MM-dd HH:mm:ss" /> <fmt:formatDate
															var="viewDate" value="${formDate}"
															pattern="yyyy-MM-dd HH:mm:ss" /> ${viewDate}</td>
														<c:set var="delay" value="" />
													<c:if test="${item.dueDateBrw < item.endDateBrw}">
														<c:set var="delay" value="text-danger" />
													</c:if>
													<td class="text-center ${delay}">
														${item.endDateBrw}
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
						</div>

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



	<script type="text/javascript">
		$(function() {
			/* 페이지 호출시 회원검색에 포커싱 */
			document.getElementById('search-name').focus();

			/* 멤버id와 이름, 전화번호가 채워졌을시, 도서바코드로 focusing 함수*/
			var chkMemberId = document.getElementById('name').value;
			if (chkMemberId) {
				document.getElementById('barcodeBook').focus();
			}

		});
	</script>
</body>
</html>