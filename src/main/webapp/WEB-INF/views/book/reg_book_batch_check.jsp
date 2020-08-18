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
						<h4>도서 일괄 등록 검증 단계</h4>
					</div>
					<div class="card-body">
						<div class="table-responsive row">

							<!-- 회원정보, 도서정보 수집 시작 -->
							<form class="form-horizontal info-section" name="batchForm"
								method="post">

								<div>
									<c:forEach var="jsonArray" items="${bookInfoArray}" varStatus="status">
										<div class="card card-body p-2 m-2">
											<div id="div${status.index}" style="clear:both;">
												
												<div class="form-inline">
												
												<div class="form-group col-2">
													<label for="bookCover" class="col-12 control-label">도서표지</label>
													<input type="hidden" name="bookCover" value="${jsonArray.item[0].cover}" />
													<img id="bookCover" src="${jsonArray.item[0].cover}" />
												</div>
												
												<div class="col-10">
													<div class="col-12 form-group form-inline mb-1">
														<input name="isbn" id="isbn${status.index}" value="${callIsbn[status.index]}"
															class="form-control form-control-sm col-3 mr-2" />
														<input name="title" id="title${status.index}" value="${titleArr[status.index]}"
															class="makeAtc form-control form-control-sm col-3 mr-2" sort-idx="${status.index}" />
														<input name="author" id="author${status.index}" value="${authorArr[status.index]}"
															class="makeAtc form-control form-control-sm col-3 mr-2" sort-idx="${status.index}" />
														<div class="col-2 form-inline">
															<input type="hidden" name="regException" id="regExcept${status.index}" value="0" />
															<input type="checkbox" class="regExceptCls" id="regExcPin${status.index}" sort-idx="${status.index}" />
															<!-- value없는 checkbox에 name을 똑같이 넣어주면 value 값이 on으로
																컨트롤러로 넘어감. -->
															<label for="regExcPin${status.index}" style="font-size:11px;">등록제외</label>
														</div>
													</div>
													
													<div class="col-12 form-group form-inline mb-0">
														<input name="copyCode" id="cp${status.index}" value="${copyCodeArray[status.index]}"
															class="form-control form-control-sm col-3 mr-2" />
														<a class="pick-clscode col-3 p-0 mr-2"
															this-isbn="${callIsbn[status.index]}"
															this-clscode-idx="cls${status.index}"
															sort-idx="${status.index}" >
															<c:choose>
																<c:when test="${not empty clsCodeArray[status.index]}">
																	<input name="classCode" id="cls${status.index}" value="${clsCodeArray[status.index]}"
																	class="form-control form-control-sm col-12" />
																</c:when>
																<c:otherwise>
																	<input name="classCode" id="cls${status.index}" value="${clsCodeArray[status.index]}"
																	class="form-control form-control-sm col-12 is-invalid cls-class" sort-idx="${status.index}"/>
																</c:otherwise>
															</c:choose>
															
														</a>
														<input name="authorCode" id="atc${status.index}" value="${atcOutArray[status.index]}"
															class="form-control form-control-sm col-3 mr-2" />
														<button class="btn btn-success" type="button"
															data-toggle="collapse" data-target="#collapseDetail${status.index}">상세보기</button>
													</div>
												</div>
												
												</div>
												
												<div class="collapse mt-1" id="collapseDetail${status.index}">
													<div class="card card-body" style="width:100%;">
														<div class="form-group form-inline">
															<div class="form-group form-inline col-6 pl-0">
																<label for="additionalCode" class="col-4 control-label">별치기호</label>
																<input type="text" name="additionalCode" id="additionalCode"
																	class="form-control form-control-sm col-8" value="" placeholder="유(유아), 아(아동)"/>
															</div>
															<div class="form-group form-inline col-6">
																<label for="volumeCode" class="col-4 control-label">권차기호</label>
																<input type="text" name="volumeCode" id="volumeCode"
																	class="form-control form-control-sm col-8" value="${volumeCodeArray[status.index]}" placeholder="권차기호"/>
															</div>
														</div>
														<div class="form-group form-inline">
															<label for="bookCateg" class="col-2 control-label">도서분류</label>
															<input type="text" name="bookCateg" id="bookCateg"
																class="form-control form-control-sm col-10" value="${jsonArray.item[0].categoryName}" placeholder="도서분류"/>
														</div>
														<div class="form-group form-inline">
															<div class="form-group form-inline col-6 pl-0">
																<label for="publisher" class="col-4 control-label">출판사</label>
																<input type="text" name="publisher" id="publisher"
																	class="form-control form-control-sm col-8" value="${jsonArray.item[0].publisher}" placeholder="출판사"/>
															</div>
															<div class="form-group form-inline col-6">
																<label for="pubDate" class="col-4 control-label">출판일</label>
																<input type="text" name="pubDate" id="pubDate"
																	class="form-control form-control-sm col-8" value="${jsonArray.item[0].pubDate}" placeholder="출판일"/>
															</div>
														</div>
														<div class="form-group form-inline">
															<div class="form-group form-inline col-6 pl-0">
																<label for="page" class="col-4 control-label">페이지</label>
																<input type="text" name="page" id="page"
																	class="form-control form-control-sm col-8" value="${jsonArray.item[0].subInfo.itemPage}" placeholder="페이지"/>
															</div>
															<div class="form-group form-inline col-6">
																<label for="price" class="col-4 control-label">가격</label>
																<input type="text" name="price" id="price"
																	class="form-control form-control-sm col-8" value="${jsonArray.item[0].priceStandard}" placeholder="가격"/>
															</div>
														</div>
														<div class="form-group form-inline">
															<div class="form-group form-inline col-6 pl-0">
																<label for="bookOrNot" class="col-4 control-label p-0">도서/비도서</label>
																<div class="col-8">
																	<select name="bookOrNot" class="form-control form-control-sm"
																		style=''>
																		<option value="BOOK" selected>국내도서</option>
																		<option value="MUSIC">음반</option>
																		<option value="DVD">DVD</option>
																		<option value="FOREIGN">외국도서</option>
																	<option value="EBOOK">전자책</option>
																	</select>
																</div>
															</div>
															<div class="form-group form-inline col-6">
																<label for="purOrDon" class="col-4 control-label">수입구분</label>
																<div class="col-8">
																	<select name="purOrDon" class="form-control form-control-sm"
																		style=''>
																		<option value="1" selected>구입</option>
																		<option value="0">기증</option>
																	</select>
																</div>
															</div>
														</div>
														<div class="form-group form-inline">
															<label for="isbn10" class="col-md-2 control-label">ISBN10</label>
															<input type="text" name="isbn10" id="isbn10"
																class="form-control form-control-sm" value="${jsonArray.item[0].isbn}" placeholder="ISBN 10"/>
														</div>
														<div class="form-group form-inline">
															
															<div class="form-group col-9">
																<label for="bookDesc" class="col-12 control-label float-left">도서설명</label>
																<textarea
																	class="txt-box form-control form-control-sm custom-control col-12"
																	name='bookDesc' id="bookDesc"
																	style="resize: none; height: 130px; width: 100%;">${jsonArray.item[0].description}</textarea>
															</div>
														</div>
														
													</div>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>

								<div class="form-group" style="position:fixed; top:85px; left:1100px;">
									<button type="submit" class="btn btn-primary btn-sm" formaction="${pageContext.request.contextPath}/book/reg_book_batch_ok.do">일괄 등록하기</button>
								</div>
								
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
	
	$(".cls-class").change(function() {
		var sortidx = $(this).attr('sort-idx');
		var thisClsCode = document.getElementById('cls'+sortidx);
		
		if(!isNaN(thisClsCode.value)&&(thisClsCode.value!='')){
			thisClsCode.classList.remove('is-invalid');
		} else {
			thisClsCode.classList.add('is-invalid');
		}
	});

</script>
</html>