<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/inc/head.jsp"%>
	<style type="text/css">
		.con-left {
			float: left;
			width: 80%;
			border-right: 1px solid black;
		}
		
		.search-box {
			border-bottom : 1px solid black;
		}
		
		.info-section {
			padding-top: 10px;
		}
		
		.txt-box {
			margin-left: 50px;
		}
		
		.mgbox {
			margin: 5px 0 5px 0;
		}
		
		.barCode {
			/* barcode font free 3 of 9*/
			font-family: 'barcode font';
			font-size: 40pt;
			
		}
	</style>
</head>
<body>
	<!-- MeadCo ScriptX -->
	<!-- <object id=factory style="display:none"
		classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814"
		codebase="http://www.meadroid.com/scriptx/ScriptX.cab#Version=6,1,429,14">
	</object> -->

	<%@ include file="/WEB-INF/inc/topbar.jsp"%>
	<%@ include file="/WEB-INF/inc/sidebar_left.jsp" %>
<div class='container con-left'>
    <h1>인쇄 페이지</h1>
    <!-- <input type="button" name="print" value="Print This Page..." onClick="printWindow();"> -->
    
    <button class="btn btn-danger" id="btn-print">
		<i class="glyphicon glyphicon-question-sign">인쇄</i>
	</button>
    <form class="form-horizontal search-box" name="search-mbr-form" id="search-mbr-form" method="post"
    	action="${pageContext.request.contextPath}/book/search_book.do">
    	<div class="form-group">
	        <label for='search-book-info' class="col-md-2">도서 검색</label>
	        <div class="input-group col-md-4">
	            <input type="text" name="search-book-info" id="search-book-info"
	            	class="form-control" placeholder="ISBN 검색"/>
	            <span class="input-group-btn">
	            	<button class="btn btn-warning" id="btn-search-bookinfo" type="submit">
	           	 		<i class="glyphicon glyphicon-search"></i>
	            	</button>
	            	<button class="btn btn-info" id="searchEx">
	           	 		<i class="glyphicon glyphicon-question-sign"></i>
	            	</button>
	            </span>
	        </div>
    	</div>
    	
    	<div class="form-group">
    		<!-- <label for='search-state' class="col-md-4">검색 상태</label> -->
    		<p class="col-md-5 barCode print" id="search-state"></p>
    	</div>
    </form>
    	
    <div>
    	<input type="button" onclick="window.open('','window팝업','width=300, height=300, menubar=no, status=no, tollbar=no');">
    </div>
</div>


<%@ include file="/WEB-INF/inc/footer.jsp"%>
<%@ include file="/WEB-INF/inc/script-common.jsp" %>
</body>



<script type="text/javascript">
function printPage() {
	var initBody;
	window.onbeforeprint = function(){
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
	
	$( "#btn-print" ).click(function(){
        if( navigator.userAgent.indexOf("MSIE") > 0 ){
         printPage();
        } else if( navigator.userAgent.indexOf("Chrome") > 0){
            window.print();
        }
   });
	
	var sVal = null;
	$("#btn-search-bookinfo").click(function(e) {
		e.preventDefault();
		sVal = $("#search-book-info").val();
		$("#search-state").text(sVal);
	});
	
 	/* $("#btn-search-bookinfo").click(function(e) {
 		e.preventDefault();
		
		var bKeyword = $("#search-book-info").val();
		var Parms = '&ttbkey=${ttbKey}';
		var ttb;
		
		
		$.post("http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?itemIdType=ISBN13&output=js&Version=20131101"
				+"&ItemId="+bKeyword +'&ttbkey=ttbanfyanfy991303001',
			 function(json) {
			$("#isbn13").val(json.item[0].isbn13);
			$("#isbn10").val(json.item[0].isbn);
			$("#bookCateg").val(json.item[0].categoryName);
			$("#categCode").val(json.item[0].categoryId);
			$("#bookName").val(json.item[0].title);
			$("#author").val(json.item[0].author);
			$("#authorCode").val();
			$("#publisher").val(json.item[0].publisher);
			$("#pubDate").val(json.item[0].pubDate);
			$("#bookDesc").val(json.item[0].description);
			$("#bookCover").attr("src", json.item[0].cover);
			
		});
		
	}); */
	
/* 	var CountMember = ${CountMember};
	console.log(CountMember);
	if (CountMember == 0) {
		$('#search-state').html("회원 정보를 찾을 수 없습니다.");
	}
	
	$(".pick-user").on("click", function(e){
	    var x = $(this).attr('id');
	    console.log(x);
	    
	    var PIdList = [];
	    var PnameList = [];
	    var PidCodeList = [];
	    var PphoneList = [];
	    var PlevelList = [];

	    <c:forEach var="item" items='${list}'>
	    	PIdList.push("${item.id}");
	        PnameList.push("${item.name}");
	        PidCodeList.push("${item.idCode}");
	        PphoneList.push("${item.phone}");
	        PlevelList.push("${item.level}");
	    </c:forEach>
	    
	    $("#memberId").val(PIdList[x]);
	    $("#name").val(PnameList[x]);
	    $("#idCode").val(PidCodeList[x]);
	    $("#phone").val(PphoneList[x]);
	    $("#level").val(PlevelList[x]);
	    e.preventDefault();
	}); */
});
</script>
</html>