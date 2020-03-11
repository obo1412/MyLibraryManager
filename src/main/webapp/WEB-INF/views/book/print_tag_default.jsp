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

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/fonts/free3of9.ttf" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/fonts/fre3of9x.ttf" />

<style type="text/css">

@font-face {
	font-family: 'Free 3 of 9';
	src: url(${pageContext.request.contextPath}/assets/fonts/fre3of9x.ttf) format('ttf');
	src: url(${pageContext.request.contextPath}/assets/fonts/free3of9.ttf) format('ttf');
	
}

@font-face {
	font-family: NanumSquareWeb;
	src: url(NanumSquareR.woff) format('woff');
	
}

@media print {
	@page {
		size: 262.5mm 371.25mm; /* A4 */
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

			<div class="container-fluid" style="margin-left:0mm; margin-top:1.85mm;">
				
				<h1 class="prtNone" style="font-family: 'Free 3 of 9'">123456나눔고딕코딩lI바코드</h1>
				<h1 class="prtNone" style="font-family:'나눔고딕코딩'">123456나눔고딕코딩lI</h1>
				<h1 class="prtNone" style="font-family:'굴림'">123456나눔고딕코딩lI굴림</h1>
				<h1 class="prtNone" style="font-family:'NanumSquareWeb'">123456나눔고딕코딩lI나눔스퀘어</h1>
				<h1 class="prtNone" >123456나눔고딕코딩lI일반</h1>
				<h1 class="prtNone" style="font-family:'꾸잉뽕'">123456나눔고딕코딩lI잘못된폰트</h1>
				<!-- <input type="button" name="print" value="Print This Page..." onClick="printWindow();"> -->

				<div class="prtNone">
					<button class="btn btn-danger prtNone" id="btn-print">인쇄</button>
				</div>

				<!-- <div>
					<input type="button" value="출력"
						onclick="window.open('','window팝업','width=300, height=300, menubar=no, status=no, tollbar=no');">
				</div> -->

				<c:forEach var="item" items="${bookHeldList}" varStatus="status">
					<c:choose>
						<c:when test="${(status.count ne 1) and((status.count mod 18) eq 1)}">
							<div style="/* border:1px dashed red; */ width: 100%; height:22.125mm; float: left; ">
							<!-- 18로 나눈 나머지 간격 조정 -->
							</div>
						</c:when>
						<c:otherwise>
							<!-- 아무것도 만들지 않는다ㅇ -->
						</c:otherwise>
					</c:choose>
					<div style="/* border:1px dashed red; */ width: 125mm; float: left;"><!-- float left를 사용해서 나란히 놓기 -->
						<div id="titleRmk"
							style="line-height:3.75mm; padding-left: 6.25mm; font-size: 6pt; font-weight: bold; width:100%; overflow:hidden">${item.titleBook}</div>
						<div id="wholeBox"
							style="/* border:1px solid black; */ margin-left:6.2mm; width: 117.5mm; height: 35mm; float: left;">
							<div id="leftBox"
								style="padding-left:2.5mm; text-align: center; float: left; width: 58.75mm; height: 100%;">
								<div
									style="width: 100%; height: 35%; font-weight: 900; font-size: 18pt; padding-top: 20pt;
									line-height:20pt;">
									${item.nameLib}</div>
								<div
									style="height: 35%; font-family: 'Free 3 of 9', 'barcode39', '바코드39'; font-size: 35pt;">
									*${item.localIdBarcode}*</div>
								<div style="font-weight: bold; margin-top:1mm; ">${item.localIdBarcode}</div>
							</div>
							
							<div id="rightWhole" style="float:left; width:58.75mm; height:100%;">
							<div id="centerBox"
								style="float: left; width: 40mm; height: 100%;">
								<div id="qrBox" style="float: left; width: 20mm; height: 100%; /* border-right:1px solid red; border-left:1px solid red; */">
									<div
										style="float:left; line-height:12pt; margin-top: 10mm; margin-left: 2.5mm; transform: rotate(90deg);">
										<div style="font-weight:bold;">${item.localIdBarcode}</div>
										<div>${item.localIdBarcode}</div>
									</div>
								</div>
								<div id="callNoBox"
									style="float: left; width: 20mm; height: 100%;">
									<div
										style="float:left; font-size: 10pt; line-height:12pt; margin-top: 3.4mm; margin-left: 5.2mm; transform: rotate(90deg);">
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
										style="display: table; background-color:black; float: right; width: 18.75mm; height: 100%; text-align: center;">
									<div
										style="width:100%; display: table-cell; font-size: 30pt; color: white; transform-origin: 50%; transform: rotate(90deg); vertical-align: middle;">${classCode}</div>
									</div>
								</c:when>
								<c:when test="${classCode eq 0}">
									<c:set var="classCode" value="000" />
									<div id="rightBox"
										style="display: table; background-color:brown; float: right; width: 18.75mm; height: 100%; text-align: center;">
									<div
										style="width:100%; display: table-cell; font-size: 30pt; color: white; transform-origin: 50%; transform: rotate(90deg); vertical-align: middle;">${classCode}</div>
										</div>
								</c:when>
								<c:when test="${classCode eq 100}">
									<div id="rightBox"
										style="display: table; background-color:red; float: right; width: 18.75mm; height: 100%; text-align: center;">
									<div
										style="width:100%; display: table-cell; font-size: 30pt; color: white; transform-origin: 50%; transform: rotate(90deg); vertical-align: middle;">${classCode}</div>
										</div>
								</c:when>
								<c:when test="${classCode eq 200}">
									<c:set var="classSectionColor" value="orange" />
									<div id="rightBox"
											style="display: table; background-color:orange; float: right; width: 18.75mm; height: 100%; text-align: center;">
										<div style="width:100%; display: table-cell; font-size: 30pt; color: white; transform-origin: 50%; transform: rotate(90deg); vertical-align: middle;">${classCode}</div>
									</div>
								</c:when>
								<c:when test="${classCode eq 300}">
									<c:set var="classSectionColor" value="yellow" />
									<div id="rightBox"
											style="display: table; background-color:yellow; float: right; width: 18.75mm; height: 100%; text-align: center;">
										<div style="width:100%; display: table-cell; font-size: 30pt; color: white; transform-origin: 50%; transform: rotate(90deg); vertical-align: middle;">${classCode}</div>
									</div>
								</c:when>
								<c:when test="${classCode eq 400}">
									<c:set var="classSectionColor" value="green" />
									<div id="rightBox"
											style="display: table; background-color:green; float: right; width: 18.75mm; height: 100%; text-align: center;">
										<div style="width:100%; display: table-cell; font-size: 30pt; color: white; transform-origin: 50%; transform: rotate(90deg); vertical-align: middle;">${classCode}</div>
									</div>
								</c:when>
								<c:when test="${classCode eq 500}">
									<c:set var="classSectionColor" value="pink" />
									<div id="rightBox"
											style="display: table; background-color:pink; float: right; width: 18.75mm; height: 100%; text-align: center;">
										<div style="width:100%; display: table-cell; font-size: 30pt; color: white; transform-origin: 50%; transform: rotate(90deg); vertical-align: middle;">${classCode}</div>
									</div>
								</c:when>
								<c:when test="${classCode eq 600}">
									<c:set var="classSectionColor" value="blue" />
									<div id="rightBox"
											style="display: table; background-color:blue; float: right; width: 18.75mm; height: 100%; text-align: center;">
										<div style="width:100%; display: table-cell; font-size: 30pt; color: white; transform-origin: 50%; transform: rotate(90deg); vertical-align: middle;">${classCode}</div>
									</div>
								</c:when>
								<c:when test="${classCode eq 700}">
									<c:set var="classSectionColor" value="purple" />
									<div id="rightBox"
											style="display: table; background-color:purple; float: right; width: 18.75mm; height: 100%; text-align: center;">
										<div style="width:100%; display: table-cell; font-size: 30pt; color: white; transform-origin: 50%; transform: rotate(90deg); vertical-align: middle;">${classCode}</div>
									</div>
								</c:when>
								<c:when test="${classCode eq 800}">
									<c:set var="classSectionColor" value="navy" />
									<div id="rightBox"
											style="display: table; background-color:navy; float: right; width: 18.75mm; height: 100%; text-align: center;">
										<div style="width:100%; display: table-cell; font-size: 30pt; color: white; transform-origin: 50%; transform: rotate(90deg); vertical-align: middle;">${classCode}</div>
									</div>
								</c:when>
								<c:when test="${classCode eq 900}">
									<c:set var="classSectionColor" value="green" />
									<div id="rightBox"
											style="display: table; background-color:green; float: right; width: 18.75mm; height: 100%; text-align: center;">
										<div style="width:100%; display: table-cell; font-size: 30pt; color: white; transform-origin: 50%; transform: rotate(90deg); vertical-align: middle;">${classCode}</div>
									</div>
								</c:when>
							</c:choose>
							</div><!-- rightWhole -->
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

	$(function() {

		$("#btn-print").click(function() {
			if (navigator.userAgent.indexOf("MSIE") > 0) {
				printPage();
			} else if (navigator.userAgent.indexOf("Chrome") > 0) {
				window.print();
			}
		});

	});
</script>
</html>