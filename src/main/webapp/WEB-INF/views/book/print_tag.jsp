<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/head.jsp"%>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/barcode/jquery-barcode.js"></script>
<style type="text/css">
@font-face{
	font-family: 'Free 3 of 9';
	src:url(${pageContext.request.contextPath}/assets/fonts/free3of9.ttf);
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

				<h1 class="prtNone">인쇄 페이지</h1>
				<!-- <input type="button" name="print" value="Print This Page..." onClick="printWindow();"> -->

				<form class="form-horizontal search-box prtNone"
					name="search-mbr-form" id="search-mbr-form" method="post"
					action="${pageContext.request.contextPath}/book/search_book.do">
					<div class="form-group form-inline">
						<button class="btn btn-danger" id="btn-print">인쇄</button>
						<label for='search-book-info' class="col-md-2">도서 검색</label>
						<div class="input-group col-md-4">
							<input type="text" name="search-book-info" id="search-book-info"
								class="form-control" placeholder="ISBN 검색" /> <span
								class="input-group-append">
								<button class="btn btn-warning" id="btn-search-bookinfo"
									type="submit">
									<i class="fas fa-search"></i>
								</button>
								<button class="btn btn-info" id="searchEx">
									<i class="fas fa-question"></i>
								</button>
							</span>
						</div>
					</div>
				</form>

				<!-- <div>
					<input type="button" value="출력"
						onclick="window.open('','window팝업','width=300, height=300, menubar=no, status=no, tollbar=no');">
				</div> -->

				<table class="table table-sm prtNone">
					<thead>
						<tr>
							<th class="info text-center">도서관</th>
							<th class="info text-center">도서제목</th>
							<th class="info text-center">바코드</th>
							<th class="info text-center">청구기호</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="text-center">리브도서관</td>
							<td class="text-center">표백</td>
							<td class="text-center">BZ000001</td>
							<td class="text-center">813.7장12ㅍC3</td>
						</tr>
					</tbody>
				</table>
					
					<div id="bcTarget">
					</div>
					
				<div id="wholeBox"
					style="border: 1px solid black; width: 490px; height: 140px;">
					<div id="leftBox"
						style="padding-left: 50px; float: left; width: 50%; height: 100%;">
						<div style="width: 100%; height: 10px;"></div>
						<div style="font-family:'Free 3 of 9'; font-size: 50px;">
							*000126*</div>
						<div>0000006</div>
					</div>
					<div id="qrBox"
						style="float: left; width: 16.6%; height: 100%;">
						<div style="margin-top: 10px; margin: 25px auto; transform: rotate(90deg);">
							<!-- <div>000006</div>
							<div>qr</div> -->
						</div>
					</div>
					<div id="callNoBox" style="float: left; width: 16%; height: 100%;">
						<div
							style="font-size: 14px; margin-top: 10px; margin: 25px auto; transform: rotate(90deg);">
							<div>813.7</div>
							<div>장12ㅍ</div>
							<div>C3</div>
						</div>
					</div>
					<div id="rightBox"
						style="display: table; float: right; width: 16%; height: 100%; background-color: #3399ff; text-align: center;">
						<div
							style="display: table-cell; font-size: 40px; color: white; transform-origin: 50%; transform: rotate(90deg); vertical-align: middle;">900</div>
					</div>
				</div>
				
				<div id="wholeBox" 
					style="margin-top: 10px; border: 1px solid black; width: 490px; height: 140px;">
					<div id="leftBox"
						style="padding-left: 50px; float: left; width: 50%; height: 100%;">
						<div style="width: 100%; height: 10px;"></div>
						<div style="font-family:'Free 3 of 9'; font-size: 50px;">
							*000126*</div>
						<div>0000006</div>
					</div>
					<div id="qrBox"
						style="float: left; width: 16.6%; height: 100%;">
						<div style="margin-top: 10px; margin: 25px auto; transform: rotate(90deg);">
							<!-- <div>000006</div>
							<div>qr</div> -->
						</div>
					</div>
					<div id="callNoBox" style="float: left; width: 16%; height: 100%;">
						<div
							style="font-size: 14px; margin-top: 10px; margin: 25px auto; transform: rotate(90deg);">
							<div>813.7</div>
							<div>장12ㅍ</div>
							<div>C3</div>
						</div>
					</div>
					<div id="rightBox"
						style="display: table; float: right; width: 16%; height: 100%; background-color: #3399ff; text-align: center;">
						<div
							style="display: table-cell; font-size: 40px; color: white; transform-origin: 50%; transform: rotate(90deg); vertical-align: middle;">900</div>
					</div>
				</div>
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
		
		$("#bctarget").barcode("000006","codabar");

	});
</script>
</html>