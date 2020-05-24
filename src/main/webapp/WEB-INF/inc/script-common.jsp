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



	/* var windowWidth = $(window).width();
	var sidebarWidth = $(".sidebar").outerWidth(); */
	//위 20과 1의 숫자는 border와 margin값으로 계산에 포함하여야 한다.
	/* var ViewWidth = $(".top-first-line").outerWidth(); */
	// $(".jqtest").html(containerWidth-sidebarWidth);
	// 위 주석 코드는 테스트용도로 jqtest 클래스 div 박스에 사이즈
	// 제대로 주입하는지 확인한 것.
	/* var contentWidth = windowWidth - sidebarWidth;
	$(".content-box").outerWidth(contentWidth); */
	
	
	//nav 밑으로 content가 안겹쳐서 안슴
	/* topbar 높이 자동계산하여 div 박스 밑으로 밀기 */
	/* var topbarHeight = $(".topbar").outerHeight();
	$(".content-box").css("padding-top", topbarHeight);
	$(".sidebar").css("top", topbarHeight); */
	// 모바일웹 사이드바(메뉴바) 띄울시 탑바 만큼 아래로 밀어주는 역할
	/* topbar 높이 자동계산하여 div 박스 밑으로 밀기 끝 */
	
	/* 마지막 메뉴 알아서 바텀바 위로 마진 넣기 */
	/* var bottombarHeight = $(".bottombar").outerHeight();
	$(".container-footer").css("margin-bottom", bottombarHeight); */
	/* 마지막 메뉴 알아서 바텀바 위로 마진 넣기 끝 */

	/* 부모 박스 높이 자동 계산해서 중간 맞춰주는 함수 */
	/* var imgVerticalMiddleH = $('.imgVerticalMiddle').children().outerHeight();
	var imgVerticalMiddleParentH = $('.imgVerticalMiddle').parent().outerHeight();
	var imgHeight = ((imgVerticalMiddleParentH/2)-(imgVerticalMiddleH/2));
	$(".imgVerticalMiddle").css("padding-top", imgHeight);
	$('.testbox').html(imgVerticalMiddleH); */
	/* 부모 박스 높이 자동 계산해서 중간 맞춰주는 함수 끝 */

	/* 페이지 새로고침 기능
	반응형 웹에서 화면크기변경시 버튼 상태 변경이 안되서 강제 새로고침 필요 */
	/* function resize_page() {
		var windowWidth = $(window).width();
		if(windowWidth > 768) {
			window.location.reload();
		}
	}

	$(function() {
		$(window).resize(function(e){
			resize_page();
		});
	});
	
	$(".mb-nav-btn").click(function(){
		$(".sidebar").toggle();
	}); */
	/* 페이지 새로고침 기능 끝 */
</script>
<!--// 공통 스크립트 영역 -->