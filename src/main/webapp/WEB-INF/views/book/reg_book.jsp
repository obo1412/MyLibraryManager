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
	</style>
</head>
<body>
	<%@ include file="/WEB-INF/inc/topbar.jsp"%>
	<%@ include file="/WEB-INF/inc/sidebar_left.jsp" %>
<div class='container con-left'>
    <h1>책 등록하기</h1>
    
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
    		<label for='search-state' class="col-md-4">검색 상태</label>
    		<p class="col-md-5" id="search-state"></p>
    	</div>
    </form>
    	
    
    <!-- 회원정보, 도서정보 수집 시작 -->
    <form class="form-horizontal info-section" name="myform" method="post"  enctype="multipart/form-data"
        action="${pageContext.request.contextPath}/book/reg_book_ok.do">
		
		<div class="form-inline mgbox">
	        <div class="form-group">
	            <label for='regDate' class="col-md-4 control-label">입력 일자</label>
	            <div class="col-md-4">
	                <input type="text" name="regDate" id="regDate" class="form-control" value="${regDate}"/>
	            </div>
	        </div>
	        <div class="form-group">
	            <label for='buyDonate' class="col-md-4 col-md-offset-1 control-label">구입/기증</label>
	            <div class="col-md-4">
	                <input type="text" name="buyDonate" id="buyDonate" class="form-control" value="${buyDonate}"/>
	            </div>
	        </div>
        </div>
       	<div class="form-inline mgbox">
            <div class="form-group">
	            <label for='isbn13' class="col-md-4 control-label">ISBN 13</label>
	            <div class="col-md-5">
	                <input type="text" name="isbn13" id="isbn13" class="form-control" placeholder="ISBN 13자리"
	                	value="${jsonAladin.item[0].isbn13}"/>
	            </div>
	        </div>
	        
	        <div class="form-group">
	            <label for='isbn10' class="col-md-4 col-md-offset-1 control-label">ISBN 10</label>
	            <div class="col-md-5">
	                <input type="text" name="isbn10" id="isbn10" class="form-control" placeholder="ISBN 10자리"
	                	value="${jsonAladin.item[0].isbn}"/>
	            </div>
	        </div>
	    </div>
        
        <div class="form-inline mgbox">
	        <div class="form-group">
	            <label for='bookCateg' class="col-md-4 control-label">도서 분류</label>
	            <div class="col-md-5">
	                <input type="text" name="bookCateg" id="bookCateg" class="form-control"
	                	value="${jsonAladin.item[0].categoryName}"/>
	            </div>
	        </div>
	        
	        <div class="form-group">
	            <label for='categCode' class="col-md-4 col-md-offset-1 control-label">분류 기호</label>
	            <div class="col-md-5">
	                <input type="text" name="categCode" id="categCode" class="form-control"
	                	value="${xmlClassNoArray[0]}"/>
	            </div>
	        </div>
	    </div>

        <div class="form-inline mgbox">
	        <div class="form-group">
	            <label for='bookName' class="col-md-4 control-label">도서명</label>
	            <div class="col-md-3">
	                <input type="text" name="bookName" id="bookName" class="form-control"
	                	placeholder="도서이름" value="${jsonAladin.item[0].title}"/>
	            </div>
	        </div>
        </div>
        
        <div class="form-inline mgbox">
	        <div class="form-group">
	            <label for='author' class="col-md-4 control-label">저자명</label>
	            <div class="col-md-5">
	                <input type="text" name="author" id="author" class="form-control"
	                	value="${jsonAladin.item[0].author}"/>
	            </div>
	        </div>
	        
	        <div class="form-group">
	            <label for='authorCode' class="col-md-4 col-md-offset-1 control-label">저자 기호</label>
	            <div class="col-md-5">
	                <input type="text" name="authorCode" id="authorCode" class="form-control"
	                	value=""/>
	            </div>
	        </div>
	    </div>
	    
	    <div class="form-inline mgbox">
	        <div class="form-group">
	            <label for='publisher' class="col-md-4 control-label">출판사</label>
	            <div class="col-md-5">
	                <input type="text" name="publisher" id="publisher" class="form-control"
	                	value="${jsonAladin.item[0].publisher}"/>
	            </div>
	        </div>
	        
	        <div class="form-group">
	            <label for='pubDate' class="col-md-4 col-md-offset-1 control-label">출판일</label>
	            <div class="col-md-5">
	                <input type="text" name="pubDate" id="pubDate" class="form-control"
	                	value="${jsonAladin.item[0].pubDate}"/>
	            </div>
	        </div>
	    </div>
	    
	    <div class='form-horizontal mgbox'>
			<div class="form-group">
				<label for='bookDesc' class="col-md-4">도서 설명</label>
			</div>
			<div class="form-inline">
				<div class="form-control" style="border: 1px solid black; width:100px; height:130px;">
					<img id="bookCover" src="${jsonAladin.item[0].cover}"/>
				</div>
				<textarea class="txt-box form-control custom-control col-md-7 col-md-offset-1" name='bookDesc' 
					id="bookDesc" style="resize:none; height: 130px; width:60%;">${jsonAladin.item[0].description}
				</textarea>
			</div>
		</div>

       
        <div class="form-group mgbox">
            <div class="col-md-offset-5 col-md-6">
                <button type="submit" class="btn btn-primary">도서등록하기</button>
                <button type="reset" class="btn btn-danger">다시작성</button>
            </div>
        </div>
        ${xmlClassNoArray[0]}/
        ${xmlClassNoArray[1]}/
        ${xmlClassNoArray[2]}/
        ${jsonSeoji.docs[0].EA_ADD_CODE}
        
    </form>
    <!-- 회원정보, 도서정보 끝 -->
</div>

<%@ include file="/WEB-INF/inc/footer.jsp"%>
<%@ include file="/WEB-INF/inc/script-common.jsp" %>
</body>
<script type="text/javascript">

$(function() {
	$("#searchEx").click(function(e) {
		e.preventDefault();
		$("#search-book-info").val("9788984314818");
		
	});
	
 	/* $("#btn-search-bookinfo").click(function(e) {
 		e.preventDefault();
		
		var bKeyword = $("#search-book-info").val();
		var Parms = '&ttbkey=${ttbKey}';
		var ttb;
		
		
		$.get("http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?itemIdType=ISBN13&output=js&Version=20131101"
				+"&ItemId="+bKeyword +'&ttbkey=[승인key]',
			 function(json) {
			$("#isbn13").val(jsonAladin.item[0].isbn13);
			$("#isbn10").val(jsonAladin.item[0].isbn);
			$("#bookCateg").val(jsonAladin.item[0].categoryName);
			$("#categCode").val(jsonAladin.item[0].categoryId);
			$("#bookName").val(jsonAladin.item[0].title);
			$("#author").val(jsonAladin.item[0].author);
			$("#authorCode").val();
			$("#publisher").val(jsonAladin.item[0].publisher);
			$("#pubDate").val(jsonAladin.item[0].pubDate);
			$("#bookDesc").val(jsonAladin.item[0].description);
			$("#bookCover").attr("src", jsonAladin.item[0].cover);
			
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