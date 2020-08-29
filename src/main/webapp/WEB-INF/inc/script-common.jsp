<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!-- 공통 스크립트 영역 -->

   <!-- Bootstrap core JavaScript-->
  <script src="${pageContext.request.contextPath}/assets/vendor/jquery/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="${pageContext.request.contextPath}/assets/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Page level plugin JavaScript-->
  <script src="${pageContext.request.contextPath}/assets/vendor/chart.js/Chart.min.js"></script>
  <script src="${pageContext.request.contextPath}/assets/vendor/datatables/jquery.dataTables.js"></script>
  <script src="${pageContext.request.contextPath}/assets/vendor/datatables/dataTables.bootstrap4.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="${pageContext.request.contextPath}/assets/js/sb-admin-js/sb-admin.min.js"></script>

  <!-- Demo scripts for this page-->
  <script src="${pageContext.request.contextPath}/assets/js/sb-admin-js/demo/datatables-demo.js"></script>
  <script src="${pageContext.request.contextPath}/assets/js/sb-admin-js/demo/chart-area-demo.js"></script>
  <script src="${pageContext.request.contextPath}/assets/js/sb-admin-js/demo/chart-bar-demo.js"></script>
  <script src="${pageContext.request.contextPath}/assets/js/sb-admin-js/demo/chart-pie-demo.js"></script>

<script type="text/javascript">

function clearInput() {
	/*텍스트박스 지우기*/
	var el = document.getElementsByClassName("input-clear");

	for(var i=0; i<el.length; i++){
		el[i].value = '';
	}
	
	/*체크박스 지우기*/
	var el = document.getElementsByClassName("input-radio");
	for(var i=0; i<el.length; i++){
		el[i].checked = false;
	}
	
	$("#bookCover").attr("src", '');
}


</script>
<!--// 공통 스크립트 영역 -->