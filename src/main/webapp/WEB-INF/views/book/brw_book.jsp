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
</style>
</head>
<body>
	<%@ include file="/WEB-INF/inc/topbar.jsp"%>
	<div id='wrapper'>
		<%@ include file="/WEB-INF/inc/sidebar_left.jsp"%>

		<div id="content-wrapper">

			<div class="container-fluid">

				<h1>책 대여하기</h1>

				<form class="form-horizontal" name="search-mbr-form"
					id="search-mbr-form" method="post"
					action="${pageContext.request.contextPath}/book/brw_book.do">
					<div class="form-group">
						<label for='search-name' class="col-md-4">회원 검색</label>
						<div class="input-group col-md-5">
							<input type="text" name="search-name" id="search-name"
								class="form-control" placeholder="이름을 입력해주세요" value="${name}" />
							<span class="input-group-btn">
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
					action="${pageContext.request.contextPath}/book/brw_book_ok.do">

					<input type="hidden" name="memberId" id="memberId"
						value="${memberId}" />

					<div class="form-group">
						<label for='name' class="col-md-4">회원 이름</label>
						<div class="col-md-5">
							<input type="text" name="name" id="name" class="form-control"
								value="${name}" />
						</div>
					</div>

					<div class="form-group">
						<label for='name' class="col-md-4">회원코드</label>
						<div class="col-md-5">
							<input type="tel" name="idCode" id="idCode" class="form-control"
								value="${idCode}" />
						</div>
					</div>

					<div class="form-group">
						<label for='tel' class="col-md-4">연락처</label>
						<div class="col-md-5">
							<input type="tel" name="phone" id="phone" class="form-control"
								value="${phone}" />
						</div>
					</div>

					<div class="form-group">
						<label for='level' class="col-md-4">회원 등급</label>
						<div class="col-md-5">
							<input type="text" name="level" id="level" class="form-control"
								placeholder="1~5" />
						</div>
					</div>

					<div class="form-group">
						<label for='name' class="col-md-4">책 제목</label>
						<div class="col-md-5">
							<input type="text" name="bookName" id="bookName"
								class="form-control" />
						</div>
					</div>

					<div class="form-group">
						<label for='name' class="col-md-4">책 고유코드</label>
						<div class="col-md-5">
							<input type="text" name="bookCode" id="bookCode"
								class="form-control" />
						</div>
					</div>

					<div class="form-group">
						<label for='bookInfo' class="col-md-4">책 관련정보</label>
						<div class="col-md-5">
							<input type="text" name="bookInfo" id="bookInfo"
								class="form-control" placeholder="장르" />
						</div>
					</div>


					<div class="form-group">
						<div class="col-md-offset-5 col-md-6">
							<button type="submit" class="btn btn-primary">제출하기</button>
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
					<th class="info text-center">등록번호</th>
					<th class="info text-center">이름</th>
					<th class="info text-center">아이디코드</th>
					<th class="info text-center">연락처</th>
					<th class="info text-center">회원등급</th>
					<th class="info text-center">검색번호</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${fn:length(list) > 0}">
						<c:forEach var="item" items="${list}" varStatus="status">
							<tr>
								<td class="text-center">${item.id}</td>
								<td><c:url var="readUrl" value="/temp/temp.do">
										<c:param name="id" value="${item.id}" />
									</c:url> <a href="${readUrl}">${item.name}</a></td>
								<td class="text-center">${item.idCode}</td>
								<td class="text-center"><a href="#">${item.phone}</a></td>
								<td class="text-center">${item.level}</td>
								<td class="test-center">
									<button class="pick-user btn" id="${status.index}">선택
									</button>
								</td>
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
	</div>
	<%@ include file="/WEB-INF/inc/script-common.jsp"%>
</body>
<script type="text/javascript">

$(function() {
	
	var CountMember = ${CountMember};
	console.log(CountMember);
	if (CountMember == 0) {
		$('#search-state').html("회원 정보를 찾을 수 없습니다.");
	}
	
	$(".pick-user").on("click", function(e){
	    var x = $(this).attr('id');
	    console.log(x);
	    
	    var PIdList = [];
	    var PnameList = [];
	    var PidCodeList = [];
	    var PphoneList = [];
	    var PlevelList = [];

	    <c:forEach var="item" items='${list}'>
	    	PIdList.push("${item.id}");
	        PnameList.push("${item.name}");
	        PidCodeList.push("${item.idCode}");
	        PphoneList.push("${item.phone}");
	        PlevelList.push("${item.level}");
	    </c:forEach>
	    
	    $("#memberId").val(PIdList[x]);
	    $("#name").val(PnameList[x]);
	    $("#idCode").val(PidCodeList[x]);
	    $("#phone").val(PphoneList[x]);
	    $("#level").val(PlevelList[x]);
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