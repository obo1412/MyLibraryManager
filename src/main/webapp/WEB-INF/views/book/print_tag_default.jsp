<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/head.jsp"%>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/barcode/jquery-barcode.js"></script>
<style type="text/css">
@font-face {
	font-family: 'Free 3 of 9';
	src: url(${pageContext.request.contextPath}/assets/fonts/free3of9.ttf);
}

@media print {
	@page {
		size: 262mm 371mm; /* A4 */
		margin: 0;
	}
	html, body {
		border: 0;
		margin: 0;
		padding: 0;
		font-family: '굴림'
	}
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
	<%-- <%@ include file="/WEB-INF/inc/topbar.jsp"%> --%>
	<div id='wrapper'>
		<%-- <%@ include file="/WEB-INF/inc/sidebar_left.jsp"%> --%>
		<div id="content-wrapper">

			<div class="container-fluid">

				<h1 class="prtNone">인쇄 페이지</h1>
				<!-- <input type="button" name="print" value="Print This Page..." onClick="printWindow();"> -->


				<button class="btn btn-danger prtNone" id="btn-print">인쇄</button>


				<!-- <div>
					<input type="button" value="출력"
						onclick="window.open('','window팝업','width=300, height=300, menubar=no, status=no, tollbar=no');">
				</div> -->

				<c:forEach var="item" items="${bookHeldList}">
					<div style="width: 131mm; float: left;"><!-- float left를 사용해서 나란히 놓기 -->
						<div id="titleRmk"
							style="padding-left: 8pt; font-size: 10pt; font-weight: bold; width:100%; overflow:hidden">${item.titleBook}</div>
						<div id="wholeBox"
							style="border:1px solid black; width: 117mm; height: 35mm; float: left;">
							<div id="leftBox"
								style="text-align: center; float: left; width: 56mm; height: 100%;">
								<div
									style="width: 100%; height: 35%; font-weight: 900; font-size: 20pt; padding-top: 20pt;">
									${item.nameLib}</div>
								<div
									style="height: 35%; font-family: 'Free 3 of 9'; font-size: 35pt;">
									*${item.localIdBarcode}*</div>
								<div style="font-weight: bold;">${item.localIdBarcode}</div>
							</div>
							
							<div id="centerBox"
								style="float: left; width: 40mm; height: 100%;">
								<div id="qrBox" style="float: left; width: 19mm; height: 100%;">
									<div
										style="float:left; line-height:12pt; margin-top: 30pt; margin-left: 20pt; transform: rotate(90deg);">
										<div style="font-weight:bold;">${item.localIdBarcode}</div>
										<div>${item.localIdBarcode}</div>
									</div>
								</div>
								<div id="callNoBox"
									style="float: left; width: 21mm; height: 100%;">
									<div
										style="float:left; font-size: 10pt; line-height:12pt; margin: 10px auto; transform: rotate(90deg);">
										<div>${item.classificationCode}</div>
										<div>${item.authorCode}</div>
										<div>C${item.copyCode}</div>
									</div>
								</div>
							</div>
							<c:set var="classCode" value="${item.classCodeHead}" />
							<c:choose>
								<c:when test="${classCode lt 0}">
									<c:set var="classCode" value="" />
									<div id="rightBox"
										style="display: table; background-color:black; float: right; width: 18mm; height: 100%; text-align: center;">
									<div
										style="width:100%; display: table-cell; font-size: 35pt; color: white; transform-origin: 50%; transform: rotate(90deg); vertical-align: middle;"><%-- ${classCode} --%></div>
									</div>
								</c:when>
								<c:when test="${classCode eq 0}">
									<c:set var="classCode" value="000" />
									<div id="rightBox"
										style="display: table; background-color:brown; float: right; width: 18mm; height: 100%; text-align: center;">
									<div
										style="width:100%; display: table-cell; font-size: 35pt; color: white; transform-origin: 50%; transform: rotate(90deg); vertical-align: middle;"><%-- ${classCode} --%></div>
										</div>
								</c:when>
								<c:when test="${classCode eq 100}">
									<c:set var="classSectionColor" value="red" />
								</c:when>
								<c:when test="${classCode eq 200}">
									<c:set var="classSectionColor" value="orange" />
								</c:when>
								<c:when test="${classCode eq 300}">
									<c:set var="classSectionColor" value="yellow" />
								</c:when>
								<c:when test="${classCode eq 400}">
									<c:set var="classSectionColor" value="green" />
								</c:when>
								<c:when test="${classCode eq 500}">
									<c:set var="classSectionColor" value="blue" />
								</c:when>
								<c:when test="${classCode eq 600}">
									<c:set var="classSectionColor" value="navy" />
								</c:when>
								<c:when test="${classCode eq 700}">
									<c:set var="classSectionColor" value="purple" />
								</c:when>
								<c:when test="${classCode eq 800}">
									<c:set var="classSectionColor" value="pink" />
									<div id="rightBox"
											style="display: table; background-color:pink; float: right; width: 18mm; height: 100%; text-align: center;">
										<div style="width:100%; display: table-cell; font-size: 35pt; color: white; transform-origin: 50%; transform: rotate(90deg); vertical-align: middle;"></div>
									</div>
								</c:when>
								<c:when test="${classCode eq 900}">
									<c:set var="classSectionColor" value="pink" />
								</c:when>
							</c:choose>
						</div><!-- whole box 끝 -->
					</div>
				</c:forEach>

			</div>
			<!-- container-fluid 끝 -->
		</div>
		<!-- content-wrapper 끝 -->
	</div>
	<!-- wrapper 끝 -->

	<%@ include file="/WEB-INF/inc/script-common.jsp"%>
</body>



<script type="text/javascript">
	function printPage() {
		var initBody;
		window.onbeforeprint = function() {
			initBody = document.body.innerHTML;
			document.body.innerHTML = document.getElementByClassName('print').innerHTML;
		};
		window.onafterprint = function() {
			document.body.innerHTML = initBody;
		};
		window.print();
		return false;
	};

	$(function() {

		/* function PrintElem(elem) {
			Popup($(elem).html());
		}
		
		function Popup(data) {
			var mywindow = window.open('', 'my div', 'height=400,width=600');
			mywindow.document.write('<html><head><title>my div</title>');
			mywindow.document.write('</head><body >');
			mywindow.document.write(data);
			mywindow.document.write('</body></html>');
			mywindow.document.close(); // IE >= 10에 필요
			mywindow.focus(); // necessary for IE >= 10
			mywindow.print();
			mywindow.close();
			return true;	
		} */

		$("#searchEx").click(function(e) {
			e.preventDefault();
			$("#search-book-info").val("9788984314818");

		});

		$("#btn-print").click(function() {
			if (navigator.userAgent.indexOf("MSIE") > 0) {
				printPage();
			} else if (navigator.userAgent.indexOf("Chrome") > 0) {
				window.print();
			}
		});

		var sVal = null;
		$("#btn-search-bookinfo").click(function(e) {
			e.preventDefault();
			sVal = $("#search-book-info").val();
			$("#search-state").text(sVal);
		});

		$("#bctarget").barcode("000006", "codabar");
		

	});
</script>
</html>