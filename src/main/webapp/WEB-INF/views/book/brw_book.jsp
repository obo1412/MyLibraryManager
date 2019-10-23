<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/head.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/inc/topbar.jsp"%>
<%@ include file="/WEB-INF/inc/sidebar_left.jsp" %>
<div class='container content-box'>
  
        <h1>책 대여하기</h1>
    <form class="form-horizontal" name="search-mbr-form" method="post"
    	action="${pageContext.request.contextPath}/book/brw_book.do">
    	<div class="form-group">
	        <label for='search-name' class="col-md-2">회원 검색</label>
	        <div class="input-group col-md-4">
	            <input type="text" name="search-name" id="searh-name"
	            	class="form-control" placeholder="이름을 입력해주세요" value="${name}"/>
	            <span class="input-group-btn">
	            	<button class="btn btn-warning" type="submit">
	           	 	<i class="glyphicon glyphicon-search"></i>
	            	</button>
	            </span>
	        </div>
    	</div>
    </form> 	
    
    <!-- 회원정보, 도서정보 수집 시작 -->
    <form class="form-horizontal" name="myform" method="post"  enctype="multipart/form-data"
        action="${pageContext.request.contextPath}/book/brw_book_ok.do">
		
        <div class="form-group">
            <label for='name' class="col-md-2">회원 이름</label>
            <div class="input-group col-md-4">
                <input type="text" name="name" id="name" class="form-control" value="${name}"/>
            </div>
        </div>
        
        <div class="form-group">
            <label for='name' class="col-md-2">회원코드</label>
            <div class="col-md-4">
                <input type="tel" name="idCode" id="idCode" class="form-control" value="${idCode}"/>
            </div>
        </div>
        
        <div class="form-group">
            <label for='tel' class="col-md-2">연락처*</label>
            <div class="col-md-4">
                <input type="tel" name="phone" id="phone" class="form-control" value="${phone}"/>
            </div>
        </div>
        
        <div class="form-group">
            <label for='name' class="col-md-2">책 제목(책 고유코드)</label>
            <div class="col-md-4">
                <input type="text" name="name" id="name" class="form-control"/>
            </div>
        </div>

        <div class="form-group">
            <label for='tel' class="col-md-2">연락처*</label>
            <div class="col-md-4">
                <input type="tel" name="phone" id="phone" class="form-control" placeholder="'-'없이 입력"/>
            </div>
        </div>

        <div class="form-group">
            <label for='level' class="col-md-2">등급*</label>
            <div class="col-md-4">
                <input type="text" name="level" id="level" class="form-control" placeholder="1~5"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-offset-2 col-md-4">
                <button type="submit" class="btn btn-primary">제출하기</button>
                <button type="reset" class="btn btn-danger">다시작성</button>
            </div>
        </div>
    </form>
    <!-- 회원정보, 도서정보 끝 -->
</div>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
<%@ include file="/WEB-INF/inc/script-common.jsp" %>
</body>
</html>