<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html>
<head>
<meta charset='utf-8' />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>회원 상세 보기</title>

<%@ include file="/WEB-INF/inc/head.jsp"%>
</head>

<body>
	<div id="wrapper">
		<div id="content-wrapper">
			<div class="container-fluid">
				<h4 class='page-header'>회원 상세 보기</h4>
				
				<!-- 버튼 -->
				<div class="text-right">
					<a
						href="${pageContext.request.contextPath}/member/member_edit.do?memberId=${memberItem.id}"
						class="btn btn-warning">수정</a> <a
						href="${pageContext.request.contextPath}/member/member_delete.do?memberId=${memberItem.id}"
						class="btn btn-danger" data-toggle="modal" data-target="#inactive_member_modal">삭제</a> <input type="button"
						class="btn btn-primary closeRefresh" value="닫기" onclick="self.close()" />
				</div>
				
				<!-- 조회결과를 출력하기 위한 표 -->
				<table class="table table-sm table-bordered mt-2">
					<tbody>
						<tr>
							<th class="table-info text-center" width="130">번호</th>
							<td>${memberItem.id}</td>
						</tr>
						<tr>
							<th class="table-info text-center">이름</th>
							<td>${memberItem.name}</td>
						</tr>
						<tr>
							<th class="table-info text-center">연락처</th>
							<td>${memberItem.phone}</td>
						</tr>
						<tr>
							<th class="table-info text-center">생년월일</th>
							<td>${memberItem.birthdate}</td>
						</tr>
						<tr>
							<th class="table-info text-center">이메일</th>
							<td>${memberItem.email}</td>
						</tr>
						<tr>
							<th class="table-info text-center">우편번호</th>
							<td>${memberItem.postcode}</td>
						</tr>
						<tr>
							<th class="table-info text-center">주소</th>
							<td>${memberItem.addr1}</td>
						</tr>
						<tr>
							<th class="table-info text-center">상세주소</th>
							<td>${memberItem.addr2}</td>
						</tr>
						<tr>
							<th class="table-info text-center">가입일</th>
							<td>${memberItem.regDate}</td>
						</tr>
						<tr>
							<th class="table-info text-center">바코드</th>
							<td>${memberItem.barcodeMbr}</td>
						</tr>
						<tr>
							<th class="table-info text-center">프로필이미지</th>
								<td>
									<c:choose>
										<c:when test="${memberItem.profileImg ne null}">
											<img style="width:100px; height:133px;" 
												src="http://nuts.i234.me:7070/files/upload/${profileName}" alt="profileImg"/>
										</c:when>
										<c:otherwise>
											프로필 사진이 없습니다.
										</c:otherwise>
									</c:choose>
								</td>
						</tr>
						<tr>
							<th class="table-info text-center">RF-UID</th>
							<td>C${memberItem.rfUid}</td>
						</tr>
						<tr>
							<th class="table-info text-center">등급이름</th>
							<td>${memberItem.gradeName}</td>
						</tr>
						<tr>
							<th class="table-info text-center">대출가능권수</th>
							<td>${memberItem.brwLimit}</td>
						</tr>
						<tr>
							<th class="table-info text-center">대여기한</th>
							<td>${memberItem.dateLimit}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- container-fluid 끝 -->
		</div>
		<!-- content-wrapper 끝 -->
	</div>
	<!-- wrapper 끝 -->
	<div class="modal fade" id="inactive_member_modal" tabindex="-1" role="dialog"
					aria-labelledby="exampleModalLabel" aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">회원 정보 삭제</h5>
								<button class="close" type="button" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
							</div>
							<form name="member_inactive" method="post">
							<input type="hidden" name="memberId" value="${memberItem.id}"/>
							<div class="modal-body">
								<div>정말로 회원정보를 삭제하시겠습니까?</div>
							</div>
							<div class="modal-footer">
								<button class="btn btn-secondary" type="button"
									data-dismiss="modal">취소</button>
								<input type="submit" class="btn btn-warning closeRefresh" value="삭제" formaction="${pageContext.request.contextPath}/member/member_inactive_ok.do"/>
							</div>
							</form>
						</div>
					</div>
				</div>
<%@ include file="/WEB-INF/inc/script-common.jsp"%>
<script type="text/javascript">
		$(function() {
			$(".closeRefresh").click(function(){
				opener.location.href=opener.document.URL;
				window.close();
			});
		});
	</script>
</body>
</html>