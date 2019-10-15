<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>
<style type="text/css">
	.sidebar {
		width: 150px;
		height: 100%;
		padding-left: 20px;
		float:left;
		background-color: #282828;
	}
</style>
<!-- 메뉴바 -->
<div class="sidebar">
	<div class="container">
		<!-- 로고 영역 -->
		
		<!-- //로고 영역 -->
		<!-- 메뉴 영역 -->
		<div class="navbar-collapse collapse">
			<!-- 사이트 메뉴 -->
			<ul class="nav navbar-nav">
				<li>
					<a href="${pageContext.request.contextPath}/member/member_list.do">회원 목록</a>
				</li>
				<br>
				<li>
					<a href="${pageContext.request.contextPath}/book/book_list.do">책 목록</a>
				</li>
			</ul>
			<!-- //사이트 메뉴 -->
			
		</div>
		<!-- //메뉴 영역 -->
	</div>
</div>
<!-- //메뉴바 -->