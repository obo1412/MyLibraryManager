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
.card-body {
	font-size: 11pt;
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
						<h4>도서 등록하기</h4>
					</div>
					<div class="card-body">
						<div class="table-responsive row">

							<form class="form-horizontal search-box" name="search-mbr-form"
								id="search-mbr-form" method="get"
								action="${pageContext.request.contextPath}/book/search_book.do">
								<div class="form-group form-inline">
									<label for='search-book-info' class="col-md-3">도서 검색</label>
									<div class="input-group input-group-sm col-md-6">
										<input type="text" name="search-book-info"
											id="search-book-info" class="form-control input-clear"
											placeholder="ISBN 검색" value="${isbn}"/> <span
											class="input-group-append btn-group">
											<button class="btn btn-warning btn-sm" id="btn-search-bookinfo"
												type="submit">
												<i class="fas fa-search"></i>
											</button>
											<button class="btn btn-info btn-sm" id="searchEx">
												<i class="fas fa-question"></i>
											</button>
										</span>
									</div>
									<c:url var="nlUrl" value="/book/search_nl_book.do">
										<c:param name="search-book-info" value="${isbn}" />
									</c:url>
									<input type="button" value="국중검색" class="btn btn-sm btn-secondary" 
										onclick="openNlPopup()" />
									<input type='hidden' name="barcodeHead" value="${barcodeInit}" />
								</div>
							</form>

							<!-- 회원정보, 도서정보 수집 시작 -->
							<form class="form-horizontal info-section" name="myform"
								method="post">

								<div class="form-inline mb-1">
									<div class="form-group col-md-12">
										<c:set var="viewTitle" value="${jsonAladin.item[0].title}" />
										<c:if test="${not empty bookTitle}">
											<c:set var="viewTitle" value="${bookTitle}" />
										</c:if>
										<label for='bookTitle' class="col-md-2 control-label">도서명</label>
										<div class="col-md-10">
											<input type="text" name="bookTitle" id="bookTitle"
												class="form-control form-control-sm input-clear" style="width: 97.5%;"
												placeholder="도서 제목" value="${viewTitle}" />
										</div>
									</div>
								</div>

								<div class="form-inline mb-1">
									<div class="form-group col-md-12">
										<c:set var="viewAuthor" value="${jsonAladin.item[0].author}" />
										<c:if test="${not empty author}">
											<c:set var="viewAuthor" value="${author}" />
										</c:if>
										<label for='author' class="col-md-2 control-label">저자명</label>
										<div class="col-md-4">
											<input type="text" name="author" id="author"
												class="form-control form-control-sm input-clear" value="${viewAuthor}" />
										</div>

										<label for='authorCode' class="col-md-2 control-label">저자기호</label>
										<div class="col-md-4">
											<input type="text" name="authorCode" id="authorCode"
												class="form-control form-control-sm input-clear" value="${atcOut}" />
										</div>
									</div>
								</div>

								<div class="form-inline mb-1">
									<div class="form-group col-md-12">
										<label for='classificationCode' class="col-md-2 control-label">
											분류기호</label>
										<div class="col-md-4">
											<input type="text" name="classificationCode"
												id="classificationCode" class="form-control form-control-sm input-clear"
												value="${xmlClassNoArray[0]}" />
										</div>

										<label for='additionalCode' class="col-md-2 control-label">별치기호</label>
										<div class="col-md-4">
											<input type="text" name="additionalCode" id="additionalCode"
												class="form-control form-control-sm input-clear" value="" placeholder="유(유아), 아(아동)" />
										</div>
									</div>
								</div>

								<div class="form-inline mb-1">
									<div class="form-group col-md-12">
										<label for='volumeCode' class="col-md-2 control-label">
											권차기호</label>
										<div class="col-md-4">
											<input type="text" name="volumeCode" id="volumeCode"
												class="form-control form-control-sm input-clear" value="${jsonSeoji.docs[0].VOL}"
												placeholder="숫자만 기입하세요." />
										</div>
										
										<label for='copyCode' class="col-md-2 control-label">
											복본기호</label>
										<div class="col-md-4">
											<span style="">C</span>
											<input type="text" name="copyCode" id="copyCode"
												class="form-control form-control-sm input-clear" value="${copyCode}"
												placeholder="숫자만 기입해주세요." />
											<button class="btn btn-secondary btn-sm"
												formaction="${pageContext.request.contextPath}/book/book_held_check_copyCode.do">
												체크
											</button>
										</div>
									</div>
								</div>

								<div class="form-inline mb-1">
									<div class="form-group col-12">
										<div class="col-md-2 ">
											<label for='bookCateg' class="control-label">도서분류</label>
										</div>
										<div class="col-md-10">
											<input type="text" name="bookCateg" id="bookCateg"
												class="form-control form-control-sm input-clear" style='width: 97.5%;'
												placeholder="도서 분류"
												value="${jsonAladin.item[0].categoryName}" />
										</div>
									</div>
								</div>

								<div class="form-inline mb-1">
									<div class="form-group col-md-12">
										<label for='publisher' class="col-md-2 control-label">출판사</label>
										<div class="col-md-4">
											<input type="text" name="publisher" id="publisher"
												class="form-control form-control-sm input-clear" value="${jsonAladin.item[0].publisher}" />
										</div>

										<label for='pubDate'
											class="col-md-2 col-md-offset-1 control-label">출판일</label>
										<div class="col-md-4">
											<input type="text" name="pubDate" id="pubDate"
												class="form-control form-control-sm input-clear" value="${jsonAladin.item[0].pubDate}" />
										</div>
									</div>
								</div>

								<div class="form-inline mb-1">
									<div class="form-group col-md-12">
										<div class="col-md-2">
											<label for='page' class="control-label">페이지</label>
										</div>
										<div class="col-md-4">
											<input type="text" name="page" id="page" class="form-control form-control-sm input-clear"
												value="${jsonAladin.item[0].subInfo.itemPage}" />
										</div>
										<div class="col-md-2">
											<label for='price' class="control-label">가격</label>
										</div>
										<div class="col-md-4">
											<input type="text" name="price" id="price"
												class="form-control form-control-sm input-clear"
												value="${jsonAladin.item[0].priceStandard}" />
										</div>
									</div>
								</div>

								<div class="form-inline mb-1">
									<div class="form-group col-md-12">
										<label for='bookOrNot' class="col-md-2 control-label">도서/비도서</label>
										<div class="col-md-4">
											<select name="bookOrNot" class="form-control form-control-sm"
												style='width: 94.5%;'>
												<option value="BOOK" selected>국내도서</option>
												<option value="MUSIC">음반</option>
												<option value="DVD">DVD</option>
												<option value="FOREIGN">외국도서</option>
												<option value="EBOOK">전자책</option>
											</select>
										</div>

										<label for='purOrDon' class="col-md-2 control-label">수입구분</label>
										<div class="col-md-4">
											<select name="purOrDon" class="form-control form-control-sm"
												style='width: 94.5%'>
												<option value="1" selected>구입</option>
												<option value="0">기증</option>
											</select>
										</div>
									</div>
								</div>

								<div class="form-inline mb-1">
									<div class="form-group col-md-12">
										<div class="col-md-2">
											<label for='isbn13' class="control-label">ISBN13</label>
										</div>
										<div class="col-md-4">
											<input type="text" name="isbn13" id="isbn13"
												class="form-control form-control-sm input-clear" placeholder="ISBN 13자리"
												value="${jsonAladin.item[0].isbn13}" />
										</div>
										<div class="col-md-2">
											<label for='isbn10' class="control-label">ISBN10</label>
										</div>
										<div class="col-md-4">
											<input type="text" name="isbn10" id="isbn10"
												class="form-control form-control-sm input-clear" placeholder="ISBN 10자리"
												value="${jsonAladin.item[0].isbn}" />
										</div>
									</div>
								</div>

								<div class="form-inline mb-3">
									<div class="form-group col-md-12">
										<div class="col-md-6"></div>
										
										<label for='newBarcode' class="col-md-2 control-label">
											도서등록번호</label>
										<div class="col-md-4">
											<input type="text" name="newBarcode" id="newBarcode"
												class="form-control form-control-sm input-clear" value="${newBarcode}"
												placeholder="공란일 경우 숫자로만 작성됨." />
										</div>
									</div>
								</div>

								<div class='form-horizontal'>
									<div class="form-group offset-md-1 col-md-11">
										<label for='bookDesc' class="col-md-4">도서 설명</label>

										<div class="form-inline">
											<div class="form-control form-control-sm col-md-2 input-clear"
												style="border: 1px solid black; width: 100px; height: 130px;">
												<img id="bookCover"
													src="${jsonAladin.item[0].cover}" />
												<input type="hidden" name="bookCover" value="${jsonAladin.item[0].cover}" />
											</div>
											<textarea
												class="txt-box form-control form-control-sm custom-control col-md-8 input-clear"
												name='bookDesc' id="bookDesc"
												style="resize: none; height: 130px; width: 60%;">${jsonAladin.item[0].description}</textarea>
										</div>
									</div>
								</div>


								<div class="form-group">
									<div class="offset-md-5 col-md-6">
										<button type="submit" class="btn btn-primary" formaction="${pageContext.request.contextPath}/book/reg_book_ok.do">도서등록하기</button>
										<input type="button" class="btn btn-danger" onclick="clearInput()" value="다시작성" />
									</div>
								</div>
								<%-- <div class="col-md-6">${xmlClassNoArray[0]}/
									${xmlClassNoArray[1]}/ ${xmlClassNoArray[2]}/
									${jsonSeoji.docs[0].EA_ADD_CODE}</div> --%>
							</form>
							<!-- 회원정보, 도서정보 끝 -->
						</div>
						<!-- table responsive 끝 -->

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
	<!-- wrapp 끝 -->

	<%@ include file="/WEB-INF/inc/script-common.jsp"%>
</body>
<script type="text/javascript">
	$(function() {
		$("#searchEx").click(function(e) {
			e.preventDefault();
			$("#search-book-info").val("9788984314818");
		});
	});
	
	function openNlPopup() {
		var isbn = $("#search-book-info").val();
		var url = '';
		if(isbn != ''){
			url = '${pageContext.request.contextPath}/book/search_nl_book.do?search-book-info='+isbn;
		} else{
			url = '${pageContext.request.contextPath}/book/search_nl_book.do';
		}
		window.open(url, '_blank', 'width=800,height=600,scrollbars=yes');
	};
</script>
</html>