<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/fonts/fonts.css" />
<%@ include file="/WEB-INF/inc/head.jsp"%>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/barcode/jquery-barcode.js"></script>
<style type="text/css">
@font-face {
	font-family: 'Free 3 of 9';
	src: url(${pageContext.request.contextPath}/assets/fonts/free3of9.ttf) format('truetype');
}

.barCode {
	/* barcode font free 3 of 9*/
	font-family: 'barcode font';
	font-size: 40pt;
}

.divPrintType {
	font-weight:bold;
}

@media print {
	.sidebar {
		display: none;
	}
	.sticky-footer {
		display: none;
	}
	.prtNone {
		display: none;
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

				<h1 class="prtNone">인쇄 설정 페이지</h1>
					<a class="btn btn-secondary"
						href="${pageContext.request.contextPath}/assets/fonts/free3of9.ttf" download>폰트다운로드</a>
					<a class="btn btn-warning"
						href="${pageContext.request.contextPath}/book/print_position_setting.do"
						onclick="window.open(this.href, '_black', 'width=600,height=800,scrollbars=yes'); return false;" >태그치수
					</a>

				<form class="form-horizontal search-box prtNone"
					name="search-mbr-form" id="search-mbr-form" method="get"
					<%-- action="${pageContext.request.contextPath}/book/print_tag_page.do" --%>>
				
				<div class="col-md-12">
					<div>
						<div class="divPrintType">A4용지 타입</div>
						<label>
							<img class="tagImg" src="${pageContext.request.contextPath}/assets/img/tagDefault.PNG"
								style="display:block; width:300px;"/>
								<span style="padding-left:100px;">
									<input type="radio" name="tagType"
										id="tag_default" value="0" />기본형
								</span>
						</label>
						<label>
							<img class="tagImg" src="${pageContext.request.contextPath}/assets/img/tagOpt1.PNG"
								style="display:block; width:300px;"/>
							<span style="padding-left:100px;">
								<input type="radio" name="tagType"
									id="tag_opt1" value="1"/>OPTION1
							</span>
						</label>
					</div>
					<div class="mt-1">
						<div class="divPrintType">Roll용지 타입</div>
						<label>
							<img class="tagImg" src="${pageContext.request.contextPath}/assets/img/tagRollDefault.PNG"
								style="display:block; width:300px;"/>
							<span style="padding-left:100px;">
								<input type="radio" name="tagType"
									id="tag_roll_default" value="10" />Roll_Printer
							</span>
						</label>
						<label>
							<img class="tagImg" src="${pageContext.request.contextPath}/assets/img/tagRollOpt1.PNG"
								style="display:block; width:300px;"/>
							<span style="padding-left:100px;">
								<input type="radio" name="tagType"
									id="tag_roll_default" value="11" checked />Roll_opt1
							</span>
						</label>
					</div>
					<%-- <label>
						<img class="tagImg" src="${pageContext.request.contextPath}/assets/img/tagC.jpg"
							style="display:block;"/>
						<input type="radio" name="tagType" id="tag_opt2" value="2"/>바코드(중)
					</label> --%>
				</div>
				
				<div class="form-group mb-3">
					<label for="rangeSorting">범위 출력(등록번호)</label>
					<div class="col-sm-12">
						<div class="">
							<select name="rangeStart" id="rangeStart" class="form-control">
								<c:forEach var="start" items="${bookHeldList}" varStatus="startStatus">
									<option value="${startStatus.count}">
										${start.localIdBarcode} / (${start.title})
									</option>
								</c:forEach>
							</select>
						</div>
						
						<label for="rangeSorting"></label>
						<div class="input-group col-sm-12">
							<select name="rangeEnd" id="rangeEnd" class="form-control">
								<c:forEach var="end" items="${bookHeldList}" varStatus="endStatus">
									<option value="${endStatus.count}">
										${end.localIdBarcode} / (${end.title})
									</option>
								</c:forEach>
							</select>
							<span class="input-group-append">
								<input type="button" class="btn btn-secondary" value="출력" id="sbmBtn"
									<%-- formaction="${pageContext.request.contextPath}/book/print_tag_page.do" --%>
									onclick="openPopup()" />
							</span>
						</div>
					</div>
				</div>
				
				<div class="form-group">
					<label for="sheetSorting">자동 묶음 출력</label>
					<div class="form-inline">
						<input type="text" class="form-control col-1"
							name="printingEa" id="printingEa" value="${tag.printingEa}" />권씩
						<input type="text" class="form-control col-1"
							name="printingSheetCount" id="printingSheetCount" value="${tag.printingSheetCount}" />번째
						<input type="button" class="btn btn-secondary" value="출력" id="btnPrintSheetSorting"
							onclick="autoSheetSortingPopup()" />
					</div>
				</div>
				
				<div class="form-group">
					<label for="dateSorting">날짜별 출력</label>
					<div class="col-sm-12 col-md-4 mb-3 input-group">
						<input type="date" class="form-control" max="9999-12-31"
							name="dateSorting" id="dateSorting" value="" placeholder="날짜별 출력"/>
							<span class="input-group-append">
								<input type="button" class="btn btn-secondary" value="출력" id="sbmBtn"
									<%-- formaction="${pageContext.request.contextPath}/book/print_tag_page.do" --%>
									onclick="openPopup()" />
							</span>
					</div>
				</div>
				
				<div class="form-group">
					<label for="titleSorting">단일 출력(도서명)</label>
					<div class="col-sm-12 col-md-4 mb-3 input-group">
						<input type="text" class="form-control"
							name="titleSorting" id="titleSorting" value="" placeholder="도서명으로 출력"/>
						<span class="input-group-append">
								<input type="button" class="btn btn-secondary" value="출력" id="sbmBtn"
									<%-- formaction="${pageContext.request.contextPath}/book/print_tag_page.do" --%>
									onclick="openPopup()" />
						</span>
					</div>
				</div>
				
				<div class="form-group">
					<label for="targetSorting">단일 출력(등록번호)</label>
					<div class="col-sm-12 col-md-4 mb-3 input-group">
						<input type="text" class="form-control"
							name="targetSorting" id="targetSorting" value="" placeholder="도서등록번호로 출력"/>
						<span class="input-group-append">
								<input type="button" class="btn btn-secondary" value="출력" id="sbmBtn"
									<%-- formaction="${pageContext.request.contextPath}/book/print_tag_page.do" --%>
									onclick="openPopup()" />
						</span>
					</div>
				</div>
				
				</form>
				
			</div>
			<!-- container-fluid 끝 -->
		</div>
		<!-- content-wrapper 끝 -->
	</div>
	<!-- wrapper 끝 -->

	<%@ include file="/WEB-INF/inc/script-common.jsp"%>
</body>


<script type="text/javascript">
	$(function() {
		//시작시 실행
	});
	
	function autoSheetSortingPopup() {
		var tagT = 0;
		tagT = $('input:radio[name=tagType]:checked').val();
		
		var printingEa = document.getElementById('printingEa').value;
		var printingSheetCount = document.getElementById('printingSheetCount').value;
		
		var rangeS = ((printingSheetCount - 1) * printingEa) + 1;
		var rangeE = printingEa * printingSheetCount ;
		
		var url = '${pageContext.request.contextPath}/book/print_tag_page.do?tagType='+tagT;
		url = url + '&rangeStart='+rangeS +'&rangeEnd='+rangeE;
		window.open(url, '_blank', 'width=1080,height=800,scrollbars=yes');
		
		$.ajax({
			url: "${pageContext.request.contextPath}/book/autoSheetCountUp.do",
			type: 'POST',
			data: {
				printingEa,
				printingSheetCount
			},
			/* dataType: "json", */
			success: function(data) {
				document.getElementById('printingSheetCount').value = data.result;
			}
			,error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	};
	
	function openPopup() {
		var tagT = 0;
		tagT = $('input:radio[name=tagType]:checked').val();
		
		var titleBook = $("#titleSorting").val();
		var targetBarcode = $("#targetSorting").val();
		var dateBarcode = $("#dateSorting").val();
		var rangeS = $("#rangeStart").val();
		var rangeE = $("#rangeEnd").val();
		
		var url = '${pageContext.request.contextPath}/book/print_tag_page.do?tagType='+tagT;
		if(targetBarcode != '' || dateBarcode != ''){
			url = url + '&targetSorting='+targetBarcode +'&dateSorting='+dateBarcode;
		} else if(rangeE != 0){
			url = url + '&rangeStart='+rangeS +'&rangeEnd='+rangeE;
		} else if (titleBook != '') {
			url = url + '&titleSorting='+titleBook;
		} else {
			url = '${pageContext.request.contextPath}/book/print_tag_page.do';
		}
		window.open(url, '_blank', 'width=1080,height=800,scrollbars=yes');
	};
</script>
</html>