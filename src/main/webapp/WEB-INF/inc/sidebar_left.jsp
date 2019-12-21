<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page trimDirectiveWhitespaces="true"%>

<head>

<style type="text/css">
.notyet {
	color: red;
}
</style>

</head>

<!-- Sidebar -->
<ul class="sidebar navbar-nav">
	<li class="nav-item dropdown"><a class="nav-link dropdown-toggle"
		href="#" id="bookDropdown" role="button" data-toggle="dropdown"
		aria-haspopup="true" aria-expanded="false"> <i
			class="fas fa-fw fa-book"></i> <span>도서 관리</span>
	</a>
		<div class="dropdown-menu" aria-labelledby="bookDropdown">
			<h6 class="dropdown-header">도서</h6>
			<a class="dropdown-item"
				href="${pageContext.request.contextPath}/book/book_list.do">도서
				목록</a> <a class="dropdown-item notyet" href="#">도서 현황</a> <a
				class="dropdown-item"
				href="${pageContext.request.contextPath}/book/reg_book.do">도서
				등록하기</a>
			<div class="dropdown-divider"></div>
			<h6 class="dropdown-header">점검</h6>
			<a class="dropdown-item notyet" href="#">장서 점검</a> <a
				class="dropdown-item notyet" href="#">보고서 출력</a> <a
				class="dropdown-item"
				href="${pageContext.request.contextPath}/book/print_tag.do">태그
				인쇄하기</a>
		</div></li>
	<li class="nav-item dropdown"><a class="nav-link dropdown-toggle"
		href="#" id="memberDropdown" role="button" data-toggle="dropdown"
		aria-haspopup="true" aria-expanded="false"> <i
			class="fas fa-fw fa-address-book"></i> <span>회원 관리</span>
	</a>
		<div class="dropdown-menu" aria-labelledby="memberDropdown">
			<h6 class="dropdown-header">회원</h6>
			<a class="dropdown-item"
				href="${pageContext.request.contextPath}/member/member_list.do">회원
				목록</a> <a class="dropdown-item"
				href="${pageContext.request.contextPath}/member/join.do">회원 등록하기</a>
			<a class="dropdown-item notyet" href="#">회원 찾기</a>
			<!-- <div class="dropdown-divider"></div>
          <h6 class="dropdown-header">Other Pages:</h6>
          <a class="dropdown-item" href="#">404 Page</a>
          <a class="dropdown-item" href="#">Blank Page</a> -->
		</div></li>
	<li class="nav-item dropdown"><a class="nav-link dropdown-toggle"
		href="#" id="brwRtnDropdown" role="button" data-toggle="dropdown"
		aria-haspopup="true" aria-expanded="false"> <i
			class="fas fa-fw fa-book-reader"></i> <span>도서 대출/반납</span>
	</a>
		<div class="dropdown-menu" aria-labelledby="brwRtnDropdown">
			<h6 class="dropdown-header">도서 대출</h6>
			<a class="dropdown-item"
				href="${pageContext.request.contextPath}/book/brw_book.do">도서
				대출하기</a> <a class="dropdown-item"
				href="${pageContext.request.contextPath}/book/book_list_brwd.do">대출된
				도서 목록</a>
			<div class="dropdown-divider"></div>
			<h6 class="dropdown-header">도서 반납</h6>
			<a class="dropdown-item"
				href="${pageContext.request.contextPath}/book/return_book.do">도서
				반납하기</a>
		</div></li>
	<li class="nav-item"><a class="nav-link notyet" href="#"> <i
			class="fas fa-fw fa-cogs"></i> <span>환경 설정</span></a></li>
</ul>



<%-- <style type="text/css">
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
		<!-- 로고 영역 -->
		
		<!-- //로고 영역 -->
		<!-- 메뉴 영역 -->
		<div class="navbar-collapse collapse">
			<!-- 사이트 메뉴 -->
			<ul class="nav nav-pills nav-stacked">
				<li>
					<a href="${pageContext.request.contextPath}/member/member_list.do">회원 목록</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/book/book_list.do">전체<br>도서목록</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/book/brw_book.do">도서<br>대출하기</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/book/book_list_brwd.do">대출된<br>도서목록</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/book/return_book.do">도서<br>반납하기</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/book/reg_book.do">도서<br>등록하기</a>
				</li>
				<li>
					<a href="">태그<br>인쇄하기</a>
				</li>
			</ul>
			<!-- //사이트 메뉴 -->
			
		</div>
		<!-- //메뉴 영역 -->
</div>
<!-- //메뉴바 --> --%>


