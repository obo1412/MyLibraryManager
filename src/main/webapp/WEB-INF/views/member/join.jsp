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
  
        <h1>회원 추가</h1>
    
    <!-- 가입폼 시작 -->
    <form class="form-horizontal" name="myform" method="post"  enctype="multipart/form-data"
        action="${pageContext.request.contextPath}/member/join_ok.do">
	
		<input type="hidden" name="lastId" value="${lastId}" />
		<input type="hidden" name="idLib" value="${idLib}" />
		
        <div class="form-group">
            <label for='name' class="col-md-2">이름*</label>
            <div class="col-md-10">
                <input type="text" name="name" id="name" class="form-control"/>
            </div>
        </div>

        <div class="form-group">
            <label for='tel' class="col-md-2">연락처*</label>
            <div class="col-md-10">
                <input type="tel" name="phone" id="phone" class="form-control" placeholder="'-'없이 입력"/>
            </div>
        </div>

        <div class="form-group">
            <label for='birthdate' class="col-md-2">등급*</label>
            <div class="col-md-10">
                <input type="text" name="level" id="level" class="form-control" placeholder="1~5"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-offset-2 col-md-10">
                <button type="submit" class="btn btn-primary">추가하기</button>
                <button type="reset" class="btn btn-danger">다시작성</button>
            </div>
        </div>
    </form>
    <!-- 가입폼 끝 -->
</div>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
<%@ include file="/WEB-INF/inc/script-common.jsp" %>
</body>
</html>