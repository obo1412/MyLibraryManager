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
		href="#" id="brwRtnDropdown" role="button" data-toggle="dropdown"
		aria-haspopup="true" aria-expanded="false"> <i
			class="fas fa-fw fa-book-reader"></i> <span>도서 대출/반납</span>
	</a>
		<div class="dropdown-menu" aria-labelledby="brwRtnDropdown">
			<a class="dropdown-item"
				href="${pageContext.request.contextPath}/book/brw_book.do">도서
				대출/반납</a> <a class="dropdown-item"
				href="${pageContext.request.contextPath}/book/book_held_list_brwd.do">대출된
				도서 목록</a>
		</div></li>
	<li class="nav-item dropdown"><a class="nav-link dropdown-toggle"
		href="#" id="bookDropdown" role="button" data-toggle="dropdown"
		aria-haspopup="true" aria-expanded="false"> <i
			class="fas fa-fw fa-book"></i> <span>도서 관리</span>
	</a>
		<div class="dropdown-menu" aria-labelledby="bookDropdown">
			<a class="dropdown-item" href="${pageContext.request.contextPath}/book/book_held_list.do">도서 목록</a>
			<a class="dropdown-item" href="${pageContext.request.contextPath}/book/reg_book.do">도서 등록하기</a>
			<a class="dropdown-item" href="${pageContext.request.contextPath}/book/reg_book_batch.do">도서 일괄 등록하기</a>
			
			<div class="dropdown-divider"></div>
			<a class="dropdown-item" href="${pageContext.request.contextPath}/book/book_held_discard_list.do">폐기도서 목록</a>
			<a class="dropdown-item" href="${pageContext.request.contextPath}/book/print_tag_setup.do">라벨 출력</a>
		</div></li>
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#"
			id="memberDropdown" role="button" data-toggle="dropdown"
			aria-haspopup="true" aria-expanded="false">
			<i class="fas fa-fw fa-address-book"></i> <span>회원 관리</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="memberDropdown">
			<a class="dropdown-item"
				href="${pageContext.request.contextPath}/member/member_list.do">회원
				목록</a> <a class="dropdown-item"
				href="${pageContext.request.contextPath}/member/join.do">회원 등록하기</a>
			<a class="dropdown-item"
				href="${pageContext.request.contextPath}/member/grade_list.do">회원등급
				관리</a>
			<!-- <div class="dropdown-divider"></div>
          <h6 class="dropdown-header">Other Pages:</h6>
          <a class="dropdown-item" href="#">404 Page</a>
          <a class="dropdown-item" href="#">Blank Page</a> -->
		</div></li>

	<li class="nav-item dropdown">
		<a class="nav-link notyet" href="#"
			id="settingDropdown" role="button" data-toggle="dropdown"
			aria-haspopup="true" aria-expanded="false">
			<i class="fas fa-fw fa-cogs"></i> <span>환경 설정</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="settingDropdown">
			<a class="dropdown-item"
				href="${pageContext.request.contextPath}/book/db_transfer.do">
				데이터베이스 이관
			</a>
			<a class="dropdown-item" id="btn_export_excel">
				Export Excel
			</a>
		</div>
	</li>
</ul>

<script>
	const btnExportExcel = document.getElementById('btn_export_excel');
	btnExportExcel.addEventListener("click", exportExcelOk);
	
	function exportExcelOk() {
		$.ajax({
			url: "${pageContext.request.contextPath}/export_bookheld_excel_ok.do",
			type: 'POST',
			dataType: "text",
			data: {},
			success: function(data) {
				/* location.href=document.URL; */
				alert("도서등록정보를 엑셀 파일로 저장하였습니다.");
			}
		});
	};
</script>
