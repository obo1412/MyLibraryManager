<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/head.jsp" %>

<style type="text/css">

</style>

</head>
<body>
<%@ include file="/WEB-INF/inc/topbar.jsp" %>

<!-- 최신 게시물 목록 영역 -->
<div id="wrapper">
<%@ include file="/WEB-INF/inc/sidebar_left.jsp" %>
	<div id="content-wrapper">
		<div class="container-fluid">
		<p>게시판 활성화 완료, ui 수정 필요</p>
		<p>member, manager DB테이블 수정 중</p>
		<p>도서 등록하기/ 도서테이블 별도, 도서관 별 도서테이블 참조 형식</p>
		<p>댓글 기능 수정 필요</p>
		
		
		<p>로그인시 미납 책 목록</p>
		<p>현재 상태 표기 각종 정보 수집</p>
		</div> <!-- container-fluid 종료 -->
		<%@ include file="/WEB-INF/inc/footer.jsp" %>	
	</div>
</div>

<%@ include file="/WEB-INF/inc/script-common.jsp" %>
</body>
</html>