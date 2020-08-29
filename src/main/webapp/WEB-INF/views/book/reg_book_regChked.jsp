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
	
	table { 
		table-layout: fixed;
	}
	
	tr > td {
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	
	.cheongGuCode {
		height:40px;
		overflow:auto;
		-ms-overflow-style: none;
	}
	
	.cheongGuCode::-webkit-scrollbar {
		display:none;
	}
</style>

</head>
<body>
	<%@ include file="/WEB-INF/inc/topbar.jsp"%>
	<div id="wrapper">
		<%@ include file="/WEB-INF/inc/sidebar_left.jsp"%>
		<div id="content-wrapper">
			<div class="container-fluid">
				<div class="card mb-3" style="float:left; min-width:800px; max-width:1000px; height:750px;">
					<div class="card-header" style="height:50px;">
						<h4>도서 등록하기</h4>
					</div>
					<div class="card-body">
						<div class="table-responsive row">

							<form class="form-horizontal search-box" name="search-mbr-form"
								id="search-mbr-form" method="get"
								action="${pageContext.request.contextPath}/book/search_book.do">
								<div class="form-group form-inline">
									<div class="col-md-3">
										<label for='search-book-info'>도서 검색</label>
										<div class="form-group form-inline ml-5 classStraightReg">
											<input type="checkbox" class="classStraightReg form-control"
												id="chkBoxStraightReg" name="straightReg" value="reg" checked/>
											<label for="chkBoxStraightReg">바로등록</label>
										</div>
									</div>
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
												class="makeAtc form-control form-control-sm input-clear" style="width: 97.5%;"
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
												class="makeAtc form-control form-control-sm input-clear" value="${viewAuthor}" />
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
											<c:set var="viewClassNo" value="${clsCode}" />
												<c:if test="${not empty classCode}">
													<c:set var="viewClassNo" value="${classCode}" />
												</c:if>
												<input type="text" name="classificationCode"
													id="classificationCode" class="form-control form-control-sm input-clear"
													value="${viewClassNo}" />
										</div>

										<label for='additionalCode' class="col-md-2 control-label">별치기호</label>
										<div class="col-md-4">
											<c:set var="viewAddCode" value="" />
											<c:if test="${not empty additionalCode}">
												<c:set var="viewAddCode" value="${additionalCode}" />
											</c:if>
											<input type="text" name="additionalCode" id="additionalCode"
												class="form-control form-control-sm input-clear" value="${viewAddCode}" placeholder="유(유아), 아(아동)" />
										</div>
									</div>
								</div>

								<div class="form-inline mb-1">
									<div class="form-group col-md-12">
										<label for='volumeCode' class="col-md-2 control-label">
											권차기호</label>
										<div class="col-md-4">
											<c:set var="viewVolumeCode" value="${jsonSeoji.docs[0].VOL}" />
											<c:if test="${not empty volumeCode}">
												<c:set var="viewVolumeCode" value="${volumeCode}" />
											</c:if>
											<input type="text" name="volumeCode" id="volumeCode"
												class="form-control form-control-sm input-clear" value="${viewVolumeCode}"
												placeholder="숫자만 기입하세요." />
										</div>
										
										<label for='copyCode' class="col-md-2 control-label">
											복본기호</label>
										<div class="col-md-4">
											<!-- <span style="">C</span> -->
											<input type="text" name="copyCode" id="copyCode"
												class="form-control form-control-sm input-clear" value="${copyCode}"
												placeholder="숫자만 기입해주세요." />
											<%-- <button class="btn btn-secondary btn-sm"
												formaction="${pageContext.request.contextPath}/book/book_held_check_copyCode.do">
												체크
											</button> --%>
										</div>
									</div>
								</div>

								<div class="form-inline mb-1">
									<div class="form-group col-12">
										<div class="col-md-2 ">
											<label for='bookCateg' class="control-label">도서분류</label>
										</div>
										<div class="col-md-10">
											<c:set var="viewBookCateg" value="${jsonAladin.item[0].categoryName}" />
											<c:if test="${not empty bookCateg}">
												<c:set var="viewBookCateg" value="${bookCateg}" />
											</c:if>
											<input type="text" name="bookCateg" id="bookCateg"
												class="form-control form-control-sm input-clear" style='width: 97.5%;'
												placeholder="도서 분류"
												value="${viewBookCateg}" />
										</div>
									</div>
								</div>

								<div class="form-inline mb-1">
									<div class="form-group col-md-12">
										<c:set var="viewPublisher" value="${jsonAladin.item[0].publisher}" />
										<c:if test="${not empty publisher}">
											<c:set var="viewPublisher" value="${publisher}" />
										</c:if>
										<label for='publisher' class="col-md-2 control-label">출판사</label>
										<div class="col-md-4">
											<input type="text" name="publisher" id="publisher"
												class="form-control form-control-sm input-clear" value="${viewPublisher}" />
										</div>

										<label for='pubDate'
											class="col-md-2 col-md-offset-1 control-label">출판일</label>
										<div class="col-md-4">
											<c:set var="viewPubDate" value="${jsonAladin.item[0].pubDate}" />
											<c:if test="${not empty pubDate}">
												<c:set var="viewPubDate" value="${pubDate}" />
											</c:if>
											<input type="text" name="pubDate" id="pubDate"
												class="form-control form-control-sm input-clear" value="${viewPubDate}" />
										</div>
									</div>
								</div>

								<div class="form-inline mb-1">
									<div class="form-group col-md-12">
										<div class="col-md-2">
											<label for='page' class="control-label">페이지</label>
										</div>
										<div class="col-md-4">
											<c:set var="viewPage" value="${jsonAladin.item[0].subInfo.itemPage}" />
											<c:if test="${not empty itemPage}">
												<c:set var="viewPage" value="${itemPage}" />
											</c:if>
											<input type="text" name="page" id="page" class="form-control form-control-sm input-clear"
												value="${viewPage}" />
										</div>
										<div class="col-md-2">
											<label for='price' class="control-label">가격</label>
										</div>
										<div class="col-md-4">
											<c:set var="viewPrice" value="${jsonAladin.item[0].priceStandard}" />
											<c:if test="${not empty price}">
												<c:set var="viewPrice" value="${price}" />
											</c:if>
											<input type="text" name="price" id="price"
												class="form-control form-control-sm input-clear"
												value="${viewPrice}" />
										</div>
									</div>
								</div>

								<div class="form-inline mb-1">
									<div class="form-group col-md-12">
										<label for='bookOrNot' class="col-md-2 control-label">도서/비도서</label>
										<div class="col-md-4">
											<select name="bookOrNot" class="form-control form-control-sm">
												<option value="BOOK" selected>국내도서</option>
												<option value="MUSIC">음반</option>
												<option value="DVD">DVD</option>
												<option value="FOREIGN">외국도서</option>
												<option value="EBOOK">전자책</option>
											</select>
										</div>

										<label for='purOrDon' class="col-md-2 control-label">수입구분</label>
										<div class="col-md-4">
											<select name="purOrDon" class="form-control form-control-sm">
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
											<c:set var="viewIsbn13" value="${jsonAladin.item[0].isbn13}" />
											<c:if test="${not empty isbn13}">
												<c:set var="viewIsbn13" value="${isbn13}" />
											</c:if>
											<input type="text" name="isbn13" id="isbn13"
												class="form-control form-control-sm input-clear" placeholder="ISBN 13자리"
												value="${viewIsbn13}" />
										</div>
										<div class="col-md-2">
											<label for='isbn10' class="control-label">ISBN10</label>
										</div>
										<div class="col-md-4">
											<c:set var="viewIsbn10" value="${jsonAladin.item[0].isbn}" />
											<c:if test="${not empty isbn10}">
												<c:set var="viewIsbn10" value="${isbn10}" />
											</c:if>
											<input type="text" name="isbn10" id="isbn10"
												class="form-control form-control-sm input-clear" placeholder="ISBN 10자리"
												value="${viewIsbn10}" />
										</div>
									</div>
								</div>
								
								<div class="form-inline mb-1">
									<div class="form-group col-md-12">
										<div class="col-md-2">
											<label for='rfId' class="control-label">RF ID</label>
										</div>
										<div class="col-md-4">
											<input type="text" name="rfId" id="rfId"
												class="form-control form-control-sm input-clear" placeholder="RF ID"
												value="" />
										</div>
										<div class="col-md-2">
											<label for='bookSize' class="control-label">도서크기</label>
										</div>
										<div class="col-md-4">
											<input type="text" name="bookSize" id="bookSize"
												class="form-control form-control-sm input-clear" placeholder="Book Size"
												value="${bookSize}" />
										</div>
									</div>
								</div>

								<div class="form-inline mb-3">
									<div class="form-group col-md-12">
										<label for='idCountry' class="col-md-2 control-label">
											도서 국가</label>
										<div class="col-md-4">
											<select name="idCountry" class="form-control form-control-sm">
												<c:forEach var="country" items="${countryList}">
													<c:set var="choice_country" value="" />
													<c:if test="${country.nameCountry == '대한민국'}">
														<c:set var="choice_country" value="selected" />
													</c:if>
													<option value="${country.idCountry}" ${choice_country}>
														${country.nameCountry}
													</option>
												</c:forEach>
											</select>
										</div>
										
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
									<div class="form-group col-md-11">
										<label for='bookDesc' class="col-md-4">도서 설명</label>

										<div class="form-inline">
											<c:set var="viewBookCover" value="${jsonAladin.item[0].cover}" />
											<c:if test="${not empty bookCover}">
												<c:set var="viewBookCover" value="${bookCover}" />
											</c:if>
											<div class="form-control form-control-sm col-md-2 input-clear"
												style="border: 1px solid black; width: 100px; height: 130px;">
												<img id="bookCover"
													src="${viewBookCover}" />
												<input type="hidden" name="bookCover" value="${viewBookCover}" />
											</div>
											<c:set var="viewBookDesc" value="${jsonAladin.item[0].description}" />
											<c:if test="${not empty bookDesc}">
												<c:set var="viewBookDesc" value="${bookDesc}" />
											</c:if>
											<textarea
												class="txt-box form-control form-control-sm custom-control col-md-7 input-clear"
												name='bookDesc' id="bookDesc"
												style="resize: none; height: 130px; width: 60%;">${viewBookDesc}</textarea>
												
											<div class="ml-3 float-right">
												<ul class="list-group">
													<li class="py-0 list-group-item">
														<div id="summaryClassHeadCode">
															<c:choose>
																<c:when test="${not empty classCodeHead}">
																	<div>
																		${classCodeHead}
																	</div>
																</c:when>
																<c:otherwise>
																	<div style="color:#D3D3D3;">
																		대분류
																	</div>
																</c:otherwise>
															</c:choose>
														</div>
													</li>
													<li class="py-0 list-group-item">
														<div id="summaryAdditionalCode">
															<c:choose>
																<c:when test="${not empty additionalCode}">
																	<div>
																		${additionalCode}
																	</div>
																</c:when>
																<c:otherwise>
																	<div style="color:#D3D3D3;">
																		별치기호
																	</div>
																</c:otherwise>
															</c:choose>
														</div>
													</li>
													<li class="py-0 list-group-item">
														<div id="summaryClassCode">
															<c:choose>
																<c:when test="${not empty clsCode}">
																	<div>
																		${clsCode}
																	</div>
																</c:when>
																<c:otherwise>
																	<div style="color:#D3D3D3;">
																		십진분류
																	</div>
																</c:otherwise>
															</c:choose>
														</div>
													</li>
													<li class="py-0 list-group-item">
														<div id="summaryAuthorCode">
															<c:choose>
																<c:when test="${not empty atcOut}">
																	<div>
																		${atcOut}
																	</div>
																</c:when>
																<c:otherwise>
																	<div style="color:#D3D3D3;">
																		저자기호
																	</div>
																</c:otherwise>
															</c:choose>
														</div>
													</li>
													<li class="py-0 list-group-item">
														<div id="summaryVolumeCode">
															<c:choose>
																<c:when test="${not empty jsonSeoji.docs[0].VOL}">
																	<div>
																		${jsonSeoji.docs[0].VOL}
																	</div>
																</c:when>
																<c:otherwise>
																	<div style="color:#D3D3D3;">
																		권차기호
																	</div>
																</c:otherwise>
															</c:choose>
														</div>
													</li>
													<li class="py-0 list-group-item">
														<div id="summaryCopyCode">
															<c:choose>
																<c:when test="${not empty copyCode}">
																	<div>
																		C${copyCode}
																	</div>
																</c:when>
																<c:otherwise>
																	<div style="color:#D3D3D3;">
																		복본기호
																	</div>
																</c:otherwise>
															</c:choose>
														</div>
													</li>
												</ul>
											</div>
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
				<!-- 등록된 도서 card -->
				<div class="card-body" style="float:left; max-width:600px; max-height:700px; overflow-y:auto;">
					<table class="table table-sm">
							<thead>
								<tr>
									<th class="table-secondary text-center" style="width:30px;">번호</th>
									<th class="table-secondary text-center" style="width:100px;">도서명</th>
									<th class="table-secondary text-center" style="width:80px;">저자명</th>
									<th class="table-secondary text-center" style="width:40px;">출판사</th>
									<th class="table-secondary text-center" style="width:50px;">청구기호</th>
									<th class="table-secondary text-center" style="width:60px;">등록번호</th>
									<th class="table-secondary text-center" style="width:40px;">권차</th>
									<th class="table-secondary text-center" style="width:40px;">복본</th>
									<th class="table-secondary text-center" style="width:30px;">색상</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(regTodayList) > 0}">
										<c:forEach var="item" items="${regTodayList}" varStatus="status">
											<tr>
												<td class="text-center">${item.sortingIndex}</td>
												<td class="text-center"><c:url var="viewUrl"
														value="/book/book_held_edit.do">
														<c:param name="localIdBarcode"
															value="${item.localIdBarcode}" />
															<c:param name="bookHeldId"
															value="${item.id}" />
													</c:url> <a href="${viewUrl}"
													onclick="window.open(this.href, '_blank','width=550,height=800,scrollbars=yes');return false;">${item.title}</a>
												</td>
												<td class="text-center">${item.writer}</td>
												<td class="text-center">${item.publisher}</td>
												<td class="text-center">
													<div class="cheongGuCode">
														<div>
															<c:if test="${not empty item.additionalCode}">${item.additionalCode} </c:if>
														</div>
														<div>
															<c:if test="${not empty item.classificationCode}">${item.classificationCode} </c:if>
														</div>
														<div>
															<c:if test="${not empty item.authorCode}">${item.authorCode} </c:if>
														</div>
														<div>
															<c:if test="${not empty item.volumeCode}">V${item.volumeCode} </c:if>
														</div>
														<div>
															<c:if test="${item.copyCode ne '0'}">C${item.copyCode} </c:if>
														</div>
													</div>
												</td>
												<td class="text-center">${item.localIdBarcode}</td>
												<td class="text-center">
													<c:choose>
														<c:when test="${not empty item.volumeCode}">
															V${item.volumeCode}
														</c:when>
														<c:otherwise>
															-
														</c:otherwise>
													</c:choose>
												</td>
												<c:choose>
													<c:when test="${item.copyCode eq 0}">
														<td class="text-center">-</td>
													</c:when>
													<c:otherwise>
														<td class="text-center">C${item.copyCode}</td>
													</c:otherwise>
												</c:choose>
												<td class="text-center">
													<div style="margin:auto; width:30px; min-height:15px; background-color:${item.classCodeColor}"></div>
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
					<%@ include file="/WEB-INF/inc/common_pagination_bottom.jsp"%>
				</div>
				<!-- 등록된 도서 card 끝-->
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
		/* 페이지로딩시 바로 실행 구문 */
	});
	
	$("#searchEx").click(function(e) {
		e.preventDefault();
		$("#search-book-info").val("9788984314818");
	});
	
	function openNlPopup() {
		var isbn = $("#search-book-info").val();
		var url = '';
		if(isbn != ''){
			url = '${pageContext.request.contextPath}/book/search_nl_book.do?searchOpt=1&search-book-info='+isbn;
		} else{
			url = '${pageContext.request.contextPath}/book/search_nl_book.do';
		}
		window.open(url, '_blank', 'width=800,height=600,scrollbars=yes');
	};
	
	
	$("#additionalCode").change(function() {
		var thisCode = document.getElementById('additionalCode').value;
		document.getElementById('summaryAdditionalCode').innerHTML = thisCode;
	});
	
	$("#classificationCode").change(function() {
		var thisCode = document.getElementById('classificationCode').value;
		document.getElementById('summaryClassCode').innerHTML = thisCode;
	});
	
	$("#authorCode").change(function() {
		var thisCode = document.getElementById('authorCode').value;
		document.getElementById('summaryAuthorCode').innerHTML = thisCode;
	});
	
	$("#volumeCode").change(function() {
		var thisCode = document.getElementById('volumeCode').value;
		document.getElementById('summaryVolumeCode').innerHTML = thisCode;
	});
	
	$("#copyCode").change(function() {
		var thisCode = document.getElementById('copyCode').value;
		document.getElementById('summaryCopyCode').innerHTML = thisCode;
	});
	
	$("#classificationCode").change(function() {
		var thisClsInput = document.getElementById('classificationCode');
		if(!isNaN(thisClsInput.value)&&(thisClsInput.value!='')){
			thisClsInput.classList.remove('is-invalid');
		} else {
			thisClsInput.classList.add('is-invalid');
		}
	});
	
	$(".makeAtc").on("propertychange change keyup paste input", function() {
		var thisIsbn = document.getElementById('search-book-info').value;
		var thisTitle = document.getElementById('bookTitle').value;
		var thisAuthor = document.getElementById('author').value;
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
				document.getElementById('authorCode').value = data.result;
				document.getElementById('copyCode').value = data.copyCode;
			}
			,error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	});
	
	$(".classStraightReg").on("click", function(e) {
		document.getElementById('search-book-info').focus();
	});
	
	window.onload = function() {
		var thisClsInput = document.getElementById('classificationCode');
		if(!isNaN(thisClsInput.value)&&(thisClsInput.value!='')){
			thisClsInput.classList.remove('is-invalid');
		} else {
			thisClsInput.classList.add('is-invalid');
		}
		
		document.getElementById('search-book-info').focus();
	};
</script>
</html>