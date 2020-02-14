<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>

<!-- 아래기능은, ajax 기본 호출-->
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



<!-- ajax를 이용하여 openapi로 데이터를 보내고 json 형태로 값을 받아옴 -->
		$("#btn-search-bookinfo").click(function(e) {
			e.preventDefault();
		
		var bKeyword = $("#search-book-info").val();
		var Parms = '&ttbkey=${ttbKey}';
		var ttb;
		
		
		$.get("http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?itemIdType=ISBN13&output=js&Version=20131101"
				+"&ItemId="+bKeyword +'&ttbkey=[승인key]',
			 function(json) {
			$("#isbn13").val(json.item[0].isbn13);
			$("#isbn10").val(json.item[0].isbn);
			$("#bookCateg").val(json.item[0].categoryName);
			$("#categCode").val(json.item[0].categoryId);
			$("#bookName").val(json.item[0].title);
			$("#author").val(json.item[0].author);
			$("#authorCode").val();
			$("#publisher").val(json.item[0].publisher);
			$("#pubDate").val(json.item[0].pubDate);
			$("#bookDesc").val(json.item[0].description);
			$("#bookCover").attr("src", jsonAladin.item[0].cover);
			
		});
		
		});
		
		
		<!-- list로 받은 데이터가 순차적으로 등록되는데,
					이 데이터 값 하나하나를 우리가 인덱싱을 못해서 사용을 못함.
					그래서 강제로 하나하나에 인덱스를 주는 역할. -->
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
		 
		 <!-- 위와 동 -->
		 /*
		 * 아래기능은 회원검색했을때, 회원선택버튼 누르면, input칸에 채우기
		 */
		$(".pick-user").on("click", function(e) {
			var x = $(this).attr('id');
			console.log(x);

			var PIdList = [];
			var PnameList = [];
			var PphoneList = [];
			var PbrwLimitList = [];
			var PdateLimitList = [];

			<c:forEach var="item" items='${list}'>
			PIdList.push("${item.id}");
			PnameList.push("${item.name}");
			PphoneList.push("${item.phone}");
			PbrwLimitList.push("${item.brwLimit}");
			PdateLimitList.push("${item.dateLimit}");
			</c:forEach>

			$("#memberId").val(PIdList[x]);
			$("#name").val(PnameList[x]);
			$("#phone").val(PphoneList[x]);
			$("#brwLimit").val(PbrwLimitList[x]);
			$("#dateLimit").val(PdateLimitList[x]);
			e.preventDefault();
			
			/* pick-user 버튼을 누르면 도서바코드로 포커싱 */
			document.getElementById('barcodeBook').focus();
		});
		
		<!-- 버튼 클릭시, 항목에 인덱싱해주고, 그 입력된 버튼에 자리를
					찾아가게 하는 ajax -->
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
		
		
				<!-- return-book 버튼이 입력되면, 해당 barcode의 도서가 반납처리
							ajax로 구현 시도 아직 이해 못함. -->
		$(document).on('click', '.return-book', function(){
			var rtnBarcode = $(this).val();
			$.post("${pageContext.request.contextPath}/book/return_book_ok.do",
					{barcodeBookRtn: rtnBarcode},
					function(req){
						$("#calltest").val(req.a);
					});
		});
		
		<!-- 반납결과 내용 출력 템플릿 -->
	<script id="return_book_tmpl" type="text/x-handlebars-template">
	</script>
	
	<!-- ajax 호출하여 handlebars 템플릿에 넣는 대략적인 과정 실행해보지는 않았음. -->
	$(document).on('click', '.testbtn', function(){
			var rtnBarcode = 3;
			$.post("${pageContext.request.contextPath}/book/return_book_ok_ajax.do",
					{
						barcodeBookRtn: 'BZ000002',
					},
					function(req){
						$('#calltest').html(req.jsonBrwRmnList);
						<!-- return_book_tmpl을 로드하여 변수 template에 주입 -->
						var template = Handlebars.compile($("#return_book_tmpl").html());
						<!-- 결과값 jsonBrwRmnList와 template 매칭,변수 html에 주입. -->
						var html = template(req.jsonBrwRmnList);
						<!-- 결합된 결과를 #result 박스나 뭐 어딘가의 공간에 주입. -->
						$("#result").append(html);
					});
		});




