<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/head.jsp"%>

<style type="text/css">
.red {
	color: red;
}

#background {
	width: 100%;
	height: 100%;
	background-image:
		url('${pageContext.request.contextPath}/assets/img/background_books.jpg');
	background-repeat: no-repeat;
	background-size: cover;
	background-position: center;
}

nav a:hover {
	text-decoration: none;
}
</style>

</head>
<body>


	<!-- 최신 게시물 목록 영역 -->
	<div>

		<div id="background" style="width: 100%;">
			<nav style="background-color: black; opacity: 0.7;">
				<div style="padding-left: 10pt;">
					<div style="line-height: 60pt; font-size: 30pt; color: white;">
						<a href="${pageContext.request.contextPath}/index.do">
							FineBook <sup style="vertical-align: middle; font-size: 14pt;">Ver
								4.0</sup>
						</a>
					</div>
					<div
						style="line-height: 15px; color: white; font-size: 15px; padding-left: 80px; padding-bottom: 10px;">
						LibShop 도서관리시스템</div>
				</div>
			</nav>
			
				<!-- login form -->
				<div style="padding-top: 100px; padding-bottom: 100px;">
					<div style="margin: auto; text-align: center;">
						<form
							class="col-md-3 offset-md-1 col-sm-4 offset-sm-1 col-xm-12 navbar-form navbar-right"
							method="post">
							<div class="form-group">
								<input type="text" name="user_id" placeholder="User Id"
									class="form-control" autofocus>
							</div>
							<div class="form-group">
								<input type="password" name="user_pw" placeholder="Password"
									class="form-control">
							</div>
							<div class="dropdown-divider">
							</div>
							<div>
								<button type="submit" class="btn btn-success btn-block mr-2"
									formaction="${pageContext.request.contextPath}/manager/login_ok.do">
									관리자 로그인</button>
								<button type="submit" class="btn btn-secondary btn-block mr-2"
									formaction="${pageContext.request.contextPath}/member/login_ok.do">
									일반회원 로그인</button>
							</div>
						</form>
					</div>
				</div>
				<!-- log in form 종료 -->
		</div>
		<!-- 상단 컨텐츠 -->
		
		<!-- 하단 게시판 -->
		<div style="width: 100%; float:left;">
		<div id="bottom-left" class="col-xm-12 col-md-6 mt-3"
			style="float: left;">

			<!-- 공지사항 -->
			<div class="col-md-12 article-item">
				<div class="page-header clearfix">
					<h4 class="float-left">공지사항</h4>
					<div class="float-right">
						<a
							href="${pageContext.request.contextPath}/bbs/document_list.do?category=notice"
							class="btn btn-warning btn-sm">more</a>
					</div>
				</div>

				<ul class="list-group">
					<c:forEach var="document" items="${noticeList}">
						<li class="list-group-item list-group-item-action"><a
							style="display: inline-block;"
							href="${pageContext.request.contextPath}/bbs/document_read.do?category=${document.category}&document_id=${document.id}">
								${document.subject} </a></li>
					</c:forEach>
				</ul>
			</div>

		</div>
		<div id="bottom-right" class="col-sm-12 col-md-6 mt-3"
			style="float: right;">
			<!-- 질문답변 -->
			<div class="col-md-12 article-item">
				<div class="page-header clearfix">
					<h4 class="float-left">질문답변</h4>
					<div class="float-right">
						<a
							href="${pageContext.request.contextPath}/bbs/document_list.do?category=qna"
							class="btn btn-warning btn-sm">more</a>
					</div>
				</div>

				<ul class="list-group">
					<c:forEach var="document" items="${qnaList}">
						<li class="list-group-item list-group-item-action"
							style="display: inline-block;"><a
							style="display: inline-block;"
							href="${pageContext.request.contextPath}/bbs/document_read.do?category=${document.category}&document_id=${document.id}">${document.subject}</a>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	</div>
	
	<!-- wrapper 끝 -->



	<%@ include file="/WEB-INF/inc/script-common.jsp"%>
</body>
</html>