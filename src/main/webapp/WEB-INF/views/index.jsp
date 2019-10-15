<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/head.jsp" %>
<style type="text/css">
/** 메인페이지 > 최근게시물 > 제목 영역 */
.article-item .page-header {
	margin-bottom: 0px;
	border-bottom: 0px;
}

/** 메인페이지 > 최근게시물 > 제목 영역 > 제목 텍스트 */
.article-item h4 {
	font-weight: bold;
	margin-bottom: 0;
}

/** 메인페이지 > 최근게시물 > 제목 우측의 more 버튼 */
.article-item .btn {
	margin-bottom: -15px;
}

/** 메인페이지 > 최근게시물 > 글 목록 > 링크 */
.article-item .list-group-item a {
	display: block;
	color: #222;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

/** 케러셀 영역의 전체 높이 지정 */
#carousel {
	height: 80%;
}

/** 핸드폰 사이즈에서는 케러셀이 화면을 가득 채움 */
@media (max-width: 767px) {
	#carousel {
		height: 100%;
	}
}

/** 케러셀의 각 영역 높이가 케러셀 안에서 가득 차도록 구성 */
#carousel .item, #carousel .carousel-inner {
	height: 100%;
}

/** 케러셀 이미지 */
#carousel .img {
	height: 100%;
	background-size: cover;
	background-position: center center;
}
</style>

</head>
<body>
<%@ include file="/WEB-INF/inc/topbar.jsp" %>
<%@ include file="/WEB-INF/inc/sidebar_left.jsp" %>

<!-- 최신 게시물 목록 영역 -->
<div class="container content-box">
	로그인시 미납 책 목록
	현재 상태 표기 각종 정보 수집
</div>

<%@ include file="/WEB-INF/inc/footer.jsp" %>
<%@ include file="/WEB-INF/inc/script-common.jsp" %>
</body>
</html>