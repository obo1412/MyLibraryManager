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

<style type="text/css">

</style>
</head>
<body>
	<form class="" name="tag-position-setting-form" method="get"
		action="${pageContext.request.contextPath}/book/print_position_setting_ok.do">
		<div style="width:100mm; height:40mm;">
			<input name="margin-left" value="${tag.marginLeft}"/>
			
			<input name="tag-width" value="${tag.tagWidth}" />
			
			<input name="tag-height" value="${tag.tagHeight}" />
			
			<div>
				<label>태그사이 간격</label>
				<input name="tag-gap" value="${tag.tagGap}" />
			</div>
			
			<div>
				<label>제목&태그 사이 간격</label>
				<input name="title-tag-gap" value="${tag.titleTagGap}" />
			</div>
		</div>
		
		<div class="form-group">
			<button type="submit" class="btn btn-primary">설정저장</button>
			<button class="btn btn-warning" onclick="window.close();">창닫기</button>
		</div>
	</form>
	<%@ include file="/WEB-INF/inc/script-common.jsp"%>
</body>



<script type="text/javascript">

</script>
</html>