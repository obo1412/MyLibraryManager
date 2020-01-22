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
.con-left {
	float: left;
	width: 40%;
	border-right: 1px solid black;
}

.con-right {
	float: left;
	width: 40%;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/inc/topbar.jsp"%>
	<div id="wrapper">
		<%@ include file="/WEB-INF/inc/sidebar_left.jsp"%>
		<div id="content-wrapper">
			<div class="container-fluid">

				<h1>책 반납하기</h1>

				<form class="form-horizontal" name="search-mbr-form"
					id="search-mbr-form" method="post"
					action="${pageContext.request.contextPath}/book/return_book.do">
					<div class="form-group">
						<label for='search-name' class="col-md-4">책 검색</label>
						<div class="input-group col-md-5">
							<input type="text" name="bookCode" id="bookCode"
								class="form-control" placeholder="책코드를 입력해주세요"
								value="${bookCode}" /> <span class="input-group-btn">
								<button class="btn btn-warning" id="btn-search-mbr"
									type="submit">
									<i class="glyphicon glyphicon-search"></i>
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
				<form class="form-horizontal" name="myform" method="post"
					enctype="multipart/form-data"
					action="${pageContext.request.contextPath}/book/return.do">

					<div class="form-group">
						<label for='name' class="col-md-4">회원 이름</label>
						<div class="col-md-5">
							<input type="text" name="name" id="name" class="form-control"
								value="${brwItem.name}" />
						</div>
					</div>

					<div class="form-group">
						<label for='name' class="col-md-4">회원코드</label>
						<div class="col-md-5">
							<input type="tel" name="idCode" id="idCode" class="form-control"
								value="${brwItem.idCode}" />
						</div>
					</div>

					<div class="form-group">
						<label for='tel' class="col-md-4">연락처</label>
						<div class="col-md-5">
							<input type="tel" name="phone" id="phone" class="form-control"
								value="${brwItem.phone}" />
						</div>
					</div>

					<div class="form-group">
						<div class="col-md-offset-5 col-md-6">
							<button type="submit" class="btn btn-primary">반납하기</button>
							<button type="reset" class="btn btn-danger">다시작성</button>
						</div>
					</div>
				</form>
				<!-- 회원정보, 도서정보 끝 -->

			</div>
			<%@ include file="/WEB-INF/inc/footer.jsp"%>
		</div>
	</div>
	<div class="con-right">
		<table class="table">
			<thead>
				<tr>
					<th class="info text-center">대여번호</th>
					<th class="info text-center">도서코드</th>
					<th class="info text-center">도서제목</th>
					<th class="info text-center">대여일시</th>
					<th class="info text-center">반납일시</th>
					<th class="info text-center">선택</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${fn:length(brwList) > 0}">
						<c:forEach var="item" items="${brwList}" varStatus="status">
							<tr>
								<td class="text-center">${item.idBrw}</td>
								<td><c:url var="readUrl" value="/temp/temp.do">
										<c:param name="id" value="${item.idCodeBook}" />
									</c:url> <a href="${readUrl}">${item.idCodeBook}</a></td>
								<td class="text-center">${item.nameBook}</td>
								<td class="text-center"><a href="#">${item.startDateBrw}</a>
								</td>

								<td class="text-center" class="endDateBox"></td>

								<td class="test-center"><c:choose>
										<c:when test="${empty item.endDateBrw}">
											<button class="return-book btn" id="${status.index}">
												반납</button>
										</c:when>
										<c:otherwise>
											<p>반납됨</p>
										</c:otherwise>
									</c:choose></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="8" class="text-center" style="line-height: 100px;">
								조회된 데이터가 없습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<div id="test"></div>

	</div>

	<%@ include file="/WEB-INF/inc/script-common.jsp"%>
</body>
<script type="text/javascript">

$(function() {
	
	/* var CountMember = ${CountMember};
	console.log(CountMember);
	if (CountMember == 0) {
		$('#search-state').html("책 정보를 찾을 수 없습니다.");
	}*/
	
	$(".return-book").on("click", function(e){
	    var x = $(this).attr('id');
	    /* console.log(x); */
	    
	    var PIdCodeBookList = [];
	    var PIdBrwList = [];
	    var curEndDateBrw = [];

	    <c:forEach var="item" items='${brwList}'>
	    	PIdCodeBookList.push("${item.idCodeBook}");
	    	PIdBrwList.push("${item.idBrw}")
	    </c:forEach>
	    
	    var curIdCodeBook = PIdCodeBookList[x];
	    var curIdBrw = PIdBrwList[x];
	    
	    $.get("${pageContext.request.contextPath}/book/return_book_ok.do",{
	    	id_code_book : curIdCodeBook,
	    	id_brw : curIdBrw
	    }, function(json) {
	 		curEndDateBrw.push(json.rtndItem.endDateBrw); 
	 		console.log(curEndDateBrw[x]);
	    	$(".endDateBox").append("<p>"+curEndDateBrw[x]+"</p>");
	    });
	    
	    
	    /* console.log(PIdCodeBookList[x]) */
	    e.preventDefault();
	});
	
/* 	
	var name = null;
	var urlName = null;
	name = $("#search-name").val();
	urlName = '${pageContext.request.contextPath}/member/member_list.do?name='+name;
	if(CountMember > 1){
		window.open(urlName, 'window', 'width=300', 'height=300');
		
		$.ajax({
			url: "${pageContext.request.contextPath}/member/member_list.do?name="+name,
			method: 'get',
			data: {
			},
			dataType: 'html',
			sucess: function() {
				pop.location;
			}
		});
		
	} else if(CountMember == 0) {
		$('#search-state').html("회원 정보를 찾을 수 없습니다.");
	}
	console.log(name);
	 */
});
</script>
</html>