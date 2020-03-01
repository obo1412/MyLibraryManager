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
<title>도서 정보 수정하기</title>

<%@ include file="/WEB-INF/inc/head.jsp"%>

<style type="text/css">
	label {
		font-size: 14px;
	}
	
	p {
		margin: 5px 0px;
	}
</style>
</head>

<body>
	<div id="wrapper">
		<div id="content-wrapper">
			<div class="container-fluid">
				<h4 class='page-header'>도서 정보 수정</h4>
				
				<form class="horizontal" name="edit_book_held" method="post" action="${pageContext.request.contextPath}/book/book_held_edit_ok.do">
					
					<input type="hidden" name="id" id="id" value="${bookHeldItem.id}"/>
					
					<div class="form-inline">
						<label for="titleBook" class="col-2">도서 제목</label>
						<div class="col-10">
							<p class="form-control-static form-control-sm">${bookHeldItem.titleBook}</p>
						</div>
					</div>
					
					<div class="form-inline mb-2">
						<label for="bookShelf" class="col-2">서가</label>
						<div class="col-10">
							<input name="bookShelf" id="bookShelf" class="form-control form-control-sm"
								value="${bookHeldItem.bookShelf}"/>
						</div>
					</div>
					
					<div class="form-inline mb-2">
						<label for="localIdBarcode" class="col-2">바코드</label>
						<div class="col-10">
							<input name="localIdBarcode" id="localIdBarcode" class="form-control form-control-sm"
								value="${bookHeldItem.localIdBarcode}"/>
						</div>
					</div>
					
					<div class="form-inline mb-2">
						<label for="purchasedOrDonated" class="col-2">구입/기증</label>
						<div class="col-10">
							<select name="purchasedOrDonated" id="purchasedOrDonated"
								class="form-control form-control-sm">
								<option value="1" <c:if test="${bookHeldItem.purchasedOrDonated == 1}">selected</c:if>>구입</option>
								<option value="0"<c:if test="${bookHeldItem.purchasedOrDonated == 0}">selected</c:if>>기증</option>
							</select>
						</div>
					</div>
					
					<div class="form-inline mb-2">
						<label for="additionalCode" class="col-2">별치기호</label>
						<div class="col-10">
							<input name="additionalCode" id="additionalCode" class="form-control form-control-sm"
								value="${bookHeldItem.additionalCode}"/>
						</div>
					</div>
					
					<div class="form-inline mb-2">
						<label for="copyCode" class="col-2">복본기호</label>
						<div class="col-10">
							<input name="copyCode" id="copyCode" class="form-control form-control-sm"
								value="${bookHeldItem.copyCode}"/>
						</div>
					</div>
					
					<div class="form-group mt-2">
						<div class="col-md-offset-2 col-md-6">
							<button type="submit" class="btn btn-primary btn-sm Refresh">수정하기</button>
							<button type="reset" class="btn btn-danger btn-sm">다시작성</button>
						</div>
					</div>
				</form>
				
				<!-- 조회결과를 출력하기 위한 표 -->
				<table class="table table-sm table-bordered mt-2">
					<tbody>
						<tr>
							<th class="table-info text-center">도서상태</th>
							<c:choose>
								<c:when test="${bookHeldItem.available < 2 }">
									<td>이용가능</td>
								</c:when>
								<c:otherwise>
									<td style="color: red;">폐기됨</td>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<th class="table-info text-center" width="130">번호</th>
							<td>${bookHeldItem.id}</td>
						</tr>
						<tr>
							<th class="table-info text-center">카테고리</th>
							<td>${bookHeldItem.categoryBook}</td>
						</tr>
						<tr>
							<th class="table-info text-center">도서제목</th>
							<td>${bookHeldItem.titleBook}</td>
						</tr>
						<tr>
							<th class="table-info text-center">저자</th>
							<td>${bookHeldItem.writerBook}</td>
						</tr>
						<tr>
							<th class="table-info text-center">출판사</th>
							<td>${bookHeldItem.publisherBook}</td>
						</tr>
						<tr>
							<th class="table-info text-center">출판일</th>
							<td>${bookHeldItem.pubDateBook}</td>
						</tr>
						<tr>
							<th class="table-info text-center">별치기호</th>
							<td>${bookHeldItem.additionalCode}</td>
						</tr>
						<tr>
							<th class="table-info text-center">분류기호</th>
							<td>${bookHeldItem.classificationCode}</td>
						</tr>
						<tr>
							<th class="table-info text-center">저자기호</th>
							<td>${bookHeldItem.authorCode}</td>
						</tr>
						<tr>
							<th class="table-info text-center">권차기호</th>
							<td>${bookHeldItem.volumeCode}</td>
						</tr>
						<tr>
							<th class="table-info text-center">복본기호</th>
							<td>C${bookHeldItem.copyCode}</td>
						</tr>
						<tr>
							<th class="table-info text-center">가격</th>
							<td>${bookHeldItem.priceBook}</td>
						</tr>
						<tr>
							<th class="table-info text-center">ISBN 10</th>
							<td>${bookHeldItem.isbn10Book}</td>
						</tr>
						<tr>
							<th class="table-info text-center">ISBN 13</th>
							<td>${bookHeldItem.isbn13Book}</td>
						</tr>
						<tr>
							<th class="table-info text-center">페이지</th>
							<td>${bookHeldItem.page}</td>
						</tr>
						<tr>
							<th class="table-info text-center">등록일</th>
							<td>${bookHeldItem.regDate}</td>
						</tr>
						<tr>
							<th class="table-info text-center">수정일</th>
							<td>${bookHeldItem.editDate}</td>
						</tr>
						<tr>
							<th class="table-info text-center">바코드번호</th>
							<td>${bookHeldItem.localIdBarcode}</td>
						</tr>
						<tr>
							<th class="table-info text-center">구매/기증</th>
							<td>${bookHeldItem.purchasedOrDonated}</td>
						</tr>
						<tr>
							<th class="table-info text-center">표지</th>
							<td><img name="bookCover" src="${bookHeldItem.imageLink}" /></td>
						</tr>
						<tr>
							<th class="table-info text-center">도서 설명</th>
							<td>${bookHeldItem.descriptionBook}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- container-fluid 끝 -->
		</div>
		<!-- content-wrapper 끝 -->
	</div>
	<!-- wrapper 끝 -->
	<div class="modal fade" id="delete_book_modal" tabindex="-1" role="dialog"
					aria-labelledby="exampleModalLabel" aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">도서 정보를 삭제 또는 폐기하시겠습니까?</h5>
								<button class="close" type="button" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
							</div>
							<form name="book_held_delete" method="post">
							<input type="hidden" name="localIdBarcode" value="${bookHeldItem.localIdBarcode}"/>
							<div class="modal-body">
								<div>기록삭제는 도서정보를 모두 삭제합니다.(복구불가능)</div>
								<div>폐기는 해당 도서의 바코드번호, 복본기호 등만을 삭제하여 기록은 남기지만 이용가능한 도서에서 제외됩니다.</div>
							</div>
							<div class="modal-footer">
								<button class="btn btn-secondary" type="button"
									data-dismiss="modal">취소</button>
								<c:if test="${bookHeldItem.available < 2 }">
								<input type="submit" class="btn btn-warning closeRefresh" value="폐기" formaction="${pageContext.request.contextPath}/book/book_held_discard_ok.do"/>
								</c:if>
								<input type="submit" class="btn btn-danger closeRefresh" value="기록삭제" formaction="${pageContext.request.contextPath}/book/book_held_delete_ok.do"/>
							</div>
							</form>
						</div>
					</div>
				</div>
<%@ include file="/WEB-INF/inc/script-common.jsp"%>
<script type="text/javascript">
		$(function() {
			$(".closeRefresh").click(function(){
				opener.location.href=opener.document.URL;
				window.close();
			});
			
			$(".Refresh").click(function(){
				opener.location.href=opener.document.URL;
			});
		});
	</script>
</body>
</html>