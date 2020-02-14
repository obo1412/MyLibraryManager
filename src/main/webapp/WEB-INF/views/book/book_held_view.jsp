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
<title>도서 상세 보기</title>

<%@ include file="/WEB-INF/inc/head.jsp"%>
</head>

<body>
	<div id="wrapper">
		<div id="content-wrapper">
			<div class="container-fluid">
				<h4 class='page-header'>도서 상세 보기</h4>

				<!-- 조회결과를 출력하기 위한 표 -->
				<table class="table table-sm table-bordered">
					<tbody>
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

				<!-- 버튼 -->
				<div class="text-center">
					<a
						href="${pageContext.request.contextPath}/book/book_held_edit.do?localIdBarcode=${bookHeldItem.localIdBarcode}"
						class="btn btn-warning">수정</a> <a
						href="${pageContext.request.contextPath}/book/book_held_delete.do?localIdBarcode=${bookHeldItem.localIdBarcode}"
						class="btn btn-danger">삭제</a> <input type="button"
						class="btn btn-primary" value="닫기" onclick="self.close()" />
				</div>
			</div>
			<!-- container-fluid 끝 -->
		</div>
		<!-- content-wrapper 끝 -->
	</div>
	<!-- wrapper 끝 -->
</body>
</html>