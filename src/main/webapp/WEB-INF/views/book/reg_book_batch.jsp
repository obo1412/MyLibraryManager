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
.card-body {
	font-size: 11pt;
}
</style>

</head>
<body>
	<%@ include file="/WEB-INF/inc/topbar.jsp"%>
	<div id="wrapper">
		<%@ include file="/WEB-INF/inc/sidebar_left.jsp"%>
		<div id="content-wrapper">
			<div class="container-fluid">
				<div class="card mb-3">
					<div class="card-header">
						<h4>도서 일괄 등록하기</h4>
					</div>
					<div class="card-body">
						<div class="table-responsive row">

							<!-- 회원정보, 도서정보 수집 시작 -->
							<form class="form-horizontal info-section" name="batchForm"
								enctype="multipart/form-data"
								method="post">

								<div class="form-inline mb-1">
									<div class="form-group col-md-12">
										<label for='batchFile' class="col-md-2 control-label">도서명</label>
										<div class="col-md-10">
											<input type="file" name="batchFile" id="batchFile"
												class="form-control form-control-sm input-clear"
												placeholder="파일 로드" />
										</div>
									</div>
								</div>

								

								<div class="form-group">
									<div class="offset-md-5 col-md-6">
										<button type="submit" class="btn btn-primary" formaction="${pageContext.request.contextPath}/book/reg_book_batch_ok.do">일괄 등록하기</button>
										<input type="button" class="btn btn-danger" onclick="clearInput()" value="다시작성" />
									</div>
								</div>
								
							</form>
							<!-- 회원정보, 도서정보 끝 -->
						</div>
						<!-- table responsive 끝 -->

					</div>
					<!-- card body 끝 -->
				</div>
				<!-- card 끝 -->
			</div>
			<!-- container fluid 끝 -->
			<%@ include file="/WEB-INF/inc/footer.jsp"%>
		</div>
		<!-- content wrapper 끝 -->
	</div>
	<!-- wrapp 끝 -->

	<%@ include file="/WEB-INF/inc/script-common.jsp"%>
</body>
<script type="text/javascript">

</script>
</html>