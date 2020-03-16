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

				<form class="form-horizontal search-box prtNone"
					name="search-mbr-form" id="search-mbr-form" method="get"
					<%-- action="${pageContext.request.contextPath}/book/print_tag_page.do" --%>>
				
				<div class="col-md-12">
					<label>
						<img class="tagImg" src="${pageContext.request.contextPath}/assets/img/tagA.jpg"
							style="display:block;"/>
						<input style="margin:auto;" type="radio" name="tagType"
							id="tag_default" value="0" checked />기본형
					</label>
					<label>
						<img class="tagImg" src="${pageContext.request.contextPath}/assets/img/tagB.jpg"
							style="display:block;"/>
						<input type="radio" name="tagType" id="tag_opt1" value="1"/>바코드(우)
					</label>
					<label>
						<img class="tagImg" src="${pageContext.request.contextPath}/assets/img/tagC.jpg"
							style="display:block;"/>
						<input type="radio" name="tagType" id="tag_opt2" value="2"/>바코드(중)
					</label>
				</div>
				
				<div class="form-group">
					<label for="dateSorting">날짜별 인쇄</label>
					<div class="col-sm-12 col-md-6 mb-3 input-group">
						<input type="date" class="form-control" max="9999-12-31"
							name="dateSorting" id="dateSorting" value="" placeholder="날짜별 인쇄"/>
							<span class="input-group-append">
								<input type="submit" class="btn btn-secondary" value="인쇄" id="sbmBtn"
									<%-- formaction="${pageContext.request.contextPath}/book/print_tag_page.do" --%>
									onclick="openPopup()" />
							</span>
					</div>
				</div>
				
				<div class="form-group">
					<label for="targetSorting">단일 인쇄</label>
					<div class="col-sm-12 col-md-6 mb-3 input-group">
						<input type="text" class="form-control"
							name="targetSorting" id="targetSorting" value="" placeholder="도서sorting 조건"/>
						<span class="input-group-append">
								<input type="submit" class="btn btn-secondary" value="인쇄" id="sbmBtn"
									<%-- formaction="${pageContext.request.contextPath}/book/print_tag_page.do" --%>
									onclick="openPopup()" />
						</span>
					</div>
				</div>
				
				<div class="form-group col-sm-12 col-md-6 mb-3">
					<label for="rangeSorting">바코드 범위 인쇄</label>
					<div class="col-sm-12 col-md-9">
						<div class="">
							<select name="rangeStart" id="rangeStart" class="form-control">
								<c:forEach var="start" items="${bookHeldList}" varStatus="startStatus">
								<option value="${startStatus.index}">
									${start.localIdBarcode} / (${start.titleBook})
								</option>
								</c:forEach>
							</select>
						</div>
						
						<label for="rangeSorting"></label>
						<div class="input-group">
							<select name="rangeEnd" id="rangeEnd" class="form-control">
								<c:forEach var="end" items="${bookHeldList}" varStatus="endStatus">
								<option value="${endStatus.index}">
									${end.localIdBarcode} / (${end.titleBook})
								</option>
								</c:forEach>
							</select>
							<span class="input-group-append">
								<input type="submit" class="btn btn-secondary" value="인쇄" id="sbmBtn"
									<%-- formaction="${pageContext.request.contextPath}/book/print_tag_page.do" --%>
									onclick="openPopup()" />
							</span>
						</div>
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
	
	function openPopup() {
		var tagT = 0;
		
		$('#sbmBtn').click(function (){
			tagT = $('input:radio[name=tagType]:checked').val();
			alert(tagT);
		});
		
		var targetBarcode = $("#targetSorting").val();
		var dateBarcode = $("#dateSorting").val();
		var rangeS = $("#rangeStart").val();
		var rangeE = $("#rangeEnd").val();
		
		var url = '${pageContext.request.contextPath}/book/print_tag_page.do?tagType='+tagT;
		if(targetBarcode != '' || dateBarcode != ''){
			url = url + '?targetSorting='+targetBarcode +'?dateSorting='+dateBarcode;
		} else if(rangeE != 0){
			url = url + '?rangeStart='+rangeS +'?rangeEnd='+rangeE;
		} else {
			url = '${pageContext.request.contextPath}/book/print_tag_page.do';
		}
		window.open(url, '_blank', 'width=1080,height=800,scrollbars=yes');
	};
</script>
</html>