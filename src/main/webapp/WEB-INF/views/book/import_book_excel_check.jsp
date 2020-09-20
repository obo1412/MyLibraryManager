<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/head.jsp"%>
<style type="text/css">
.card {
	float:left;
}

.card-body {
	font-size: 11pt;
}

label {
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
				
				<div class="card mb-3 mr-2" style="width:1000px;">
					<div class="card-header">
						<h4>도서 데이터 엑셀 등록</h4>
					</div>
					<div class="card-body">
						<div class="table-responsive row">

							<!-- 회원정보, 도서정보 수집 시작 -->
							<form class="form-horizontal info-section" name="batchForm"
								method="post">
								
								<div class="form-group float-right">
									<div class="col-12">
										<button type="submit" class="btn btn-primary btn-sm" formaction="${pageContext.request.contextPath}/book/import_book_excel_ok.do">등록하기</button>
										<input type="button" class="btn btn-danger btn-sm" onclick="clearInput()" value="다시작성" />
									</div>
								</div>

								<table class="table table-sm">

									<thead>
										<c:forEach var="row" items="${theArr}" varStatus="firStat">
											<c:if test="${firStat.index eq 0}">
												<tr>
													<c:forEach var="col" items="${row}" varStatus="seStat">
														<th class="text-center">
															<c:set var="opt" value="" />
															<c:if test="">
																<c:set var="opt" value="selected" />
															</c:if>
															<select name="colH${seStat.index}">
																<option value="">목록제외</option>
																<option value="barcode">등록번호</option>
																<option value="title">도서명</option>
																<option value="author">저자명</option>
																<option value="publisher">출판사</option>
																<option value="pubDateYear">발행연도</option>
																<option value="regDate">등록일</option>
																<option value="callNumber">청구기호</option>
																<option value="price">가격</option>
																<option value="rfId">RFID</option>
															</select>
														</th>
													</c:forEach>
												</tr>
												<tr>
													<c:forEach var="col" items="${row}" varStatus="seStat">
														<th class="table-info text-center">${col}</th>
														<c:if test="${(firStat.index eq 0) and (seStat.last eq true)}" >
															<!-- 마지막 col값을 주기 위한 변수로 -->
															<input type="hidden" name="colLast" value="${seStat.index}" />
														</c:if>
													</c:forEach>
												</tr>
											</c:if>
										</c:forEach>
									</thead>

									<tbody>
										<c:forEach var="row" items="${theArr}" varStatus="firStat">
											<c:if test="${firStat.index gt 0}">
											<tr>
												<c:forEach var="col" items="${row}" varStatus="seStat">
													<td name="col${seStat.index}" class="text-center">
														${col}
														<input type="hidden" name="col${seStat.index}" value="${col}" />
													</td>
												</c:forEach>
											</tr>
											</c:if>
										</c:forEach>
									</tbody>

								</table>
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
	/* $(function() {
		/* 페이지 호출시 회원검색에 포커싱 *//*
		document.getElementById('txtBox').focus();
	}); */
	
	 //등록제외처리를 위한 테스트
	$(".regExceptCls").on("click", function(e){
		var sortidx = $(this).attr('sort-idx');
		var thisRegExcept = document.getElementById('regExcept'+sortidx);
		if(document.getElementById('regExcPin'+sortidx).checked == true){
			thisRegExcept.value = 1;
		} else {
			thisRegExcept.value = 0;
		}
		
	});
	
	
	
	$(".pick-clscode").on("dblclick", function(e){
		var isbn = $(this).attr('this-isbn');
		var clscode = $(this).attr('this-clscode-idx');
		var sortidx = $(this).attr('sort-idx');
		
		var url = '${pageContext.request.contextPath}/book/search_nl_book.do';
		if(isbn != ''){
			url = url+'?searchOpt=1&search-book-info='+isbn+'&sort-idx='+sortidx+'&this-clscode-idx='+clscode;
		}
		window.open(url, '_blank', 'width=800,height=600,scrollbars=yes');
	});
	
	$(".makeAtc").change(function(){
		var sortidx = $(this).attr('sort-idx');
		var thisIsbn = document.getElementById('isbn'+sortidx).value;
		var thisTitle = document.getElementById('title'+sortidx).value;
		var thisAuthor = document.getElementById('author'+sortidx).value;
		var atcout = null;
		
		$.ajax({
			url: "${pageContext.request.contextPath}/book/author_code.do",
			type: 'POST',
			data: {
				thisIsbn,
				thisTitle,
				thisAuthor
			},
			/* dataType: "json", */
			success: function(data) {
				document.getElementById('atc'+sortidx).value = data.result;
				document.getElementById('cp'+sortidx).value = data.copyCode;
			}
			,error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	});

</script>
</html>