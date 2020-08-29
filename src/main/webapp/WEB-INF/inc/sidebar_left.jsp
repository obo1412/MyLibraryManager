<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page trimDirectiveWhitespaces="true"%>

<head>

<style type="text/css">
.notyet {
	color: red;
}

	td {
		width: 10px;
		height: 10px;
		text-align: center;
		font-size: 15px;
		font-family: 나눔고딕;
		border: 1px border-color:black;
		border-radius: 4px; /*모서리 둥글게*/
	}
	
	td.arrow:hover {
		background-color: orange;
	}
	
	.pickDay:hover {
		background-color: grey;
		cursor: pointer;
	}
	
	.dDay {
		background-color: orange;
	}
	
	table {
		background-color: white;
	}
</style>

</head>

<!-- Sidebar -->
<c:choose>
	<c:when test="${loginInfo.langMng eq 0}">
		<ul class="sidebar navbar-nav">
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="brwRtnDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> <i class="fas fa-fw fa-book-reader"></i>
					<span>도서 대출/반납</span>
			</a>
				<div class="dropdown-menu" aria-labelledby="brwRtnDropdown">
					<a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/brw_book.do">도서
						대출/반납</a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/book_held_list_brwd.do">대출된
						도서 목록</a>
				</div></li>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="bookDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> <i class="fas fa-fw fa-book"></i> <span>도서
						관리</span>
			</a>
				<div class="dropdown-menu" aria-labelledby="bookDropdown">
					<a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/book_held_list.do">도서
						목록</a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/reg_book.do">도서
						등록하기</a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/reg_book_batch.do">도서
						일괄 등록하기</a>

					<div class="dropdown-divider"></div>
					<a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/book_held_discard_list.do">폐기도서
						목록</a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/print_tag_setup.do">라벨
						출력</a>
				</div></li>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="memberDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> <i class="fas fa-fw fa-address-book"></i>
					<span>회원 관리</span>
			</a>
				<div class="dropdown-menu" aria-labelledby="memberDropdown">
					<a class="dropdown-item"
						href="${pageContext.request.contextPath}/member/member_list.do">회원
						목록</a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/member/join.do">회원
						등록하기</a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/member/grade_list.do">회원등급
						관리</a>
					<!-- <div class="dropdown-divider"></div>
		          <h6 class="dropdown-header">Other Pages:</h6>
		          <a class="dropdown-item" href="#">404 Page</a>
		          <a class="dropdown-item" href="#">Blank Page</a> -->
				</div></li>

			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="settingDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> <i class="fas fa-fw fa-cogs"></i> <span>환경
						설정</span>
			</a>
				<div class="dropdown-menu" aria-labelledby="settingDropdown">
					<a class="dropdown-item disabled"
						href="${pageContext.request.contextPath}/book/db_transfer.do">
						데이터베이스 이관 </a> <a class="dropdown-item disabled" id="btn_export_excel">
						Export Excel </a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/import_book_excel.do">
						도서 정보 가져오기 </a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/country_list.do">
						국가 관리 </a>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item"
						href="${pageContext.request.contextPath}/setting/language.do">
						언어/Language </a>
				</div></li>
				
				<li style="margin-top:5px; ">
          <div style="">
            <table id="calendar" border="1" align="center" style="border-color:black; ">
              <tbody class="cal-body">
                <tr><!-- label은 마우스로 클릭을 편하게 해줌 -->
                  <td class="arrow" onclick="prevCalendar()">
                    <label><</label>
                  </td>
                  <td align="center" id="tbCalendarYM" colspan="5">
                    yyyy년 m월
                  </td>
                  <td class="arrow"  onclick="nextCalendar()">
                    <label>></label>
                  </td>
                </tr>
                <tr>
                  <td align="center"><font color ="#F79DC2">일</font></td>
                  <td align="center">월</td>
                  <td align="center">화</td>
                  <td align="center">수</td>
                  <td align="center">목</td>
                  <td align="center">금</td>
                  <td align="center"><font color ="skyblue">토</font></td>
                </tr> 
              </tbody>
            </table>
          </div>
        </li>
				
		</ul>
	</c:when>
	<c:otherwise>
		<ul class="sidebar navbar-nav">
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="brwRtnDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> <i class="fas fa-fw fa-book-reader"></i>
					<span>Borrow/Return</span>
			</a>
				<div class="dropdown-menu" aria-labelledby="brwRtnDropdown">
					<a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/brw_book.do">도서
						대출/반납</a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/book_held_list_brwd.do">대출된
						도서 목록</a>
				</div></li>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="bookDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> <i class="fas fa-fw fa-book"></i> <span>Book</span>
			</a>
				<div class="dropdown-menu" aria-labelledby="bookDropdown">
					<a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/book_held_list.do">도서
						목록</a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/reg_book.do">도서
						등록하기</a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/reg_book_batch.do">도서
						일괄 등록하기</a>

					<div class="dropdown-divider"></div>
					<a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/book_held_discard_list.do">폐기도서
						목록</a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/print_tag_setup.do">라벨
						출력</a>
				</div></li>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="memberDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> <i class="fas fa-fw fa-address-book"></i>
					<span>Member</span>
			</a>
				<div class="dropdown-menu" aria-labelledby="memberDropdown">
					<a class="dropdown-item"
						href="${pageContext.request.contextPath}/member/member_list.do">회원
						목록</a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/member/join.do">회원
						등록하기</a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/member/grade_list.do">회원등급
						관리</a>
					<!-- <div class="dropdown-divider"></div>
		          <h6 class="dropdown-header">Other Pages:</h6>
		          <a class="dropdown-item" href="#">404 Page</a>
		          <a class="dropdown-item" href="#">Blank Page</a> -->
				</div></li>

			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="settingDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> <i class="fas fa-fw fa-cogs"></i> <span>Setting</span>
			</a>
				<div class="dropdown-menu" aria-labelledby="settingDropdown">
					<a class="dropdown-item disabled"
						href="${pageContext.request.contextPath}/book/db_transfer.do">
						데이터베이스 이관 </a> <a class="dropdown-item disabled" id="btn_export_excel">
						Export Excel </a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/import_book_excel.do">
						도서 정보 가져오기 </a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/book/country_list.do">
						국가 관리 </a>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item"
						href="${pageContext.request.contextPath}/setting/language.do">
						언어/Language </a>
				</div></li>
				
				 <li style="margin-top:5px; ">
          <div style="">
            <table id="calendar" border="1" align="center" style="border-color:black; ">
              <tbody class="cal-body">
                <tr><!-- label은 마우스로 클릭을 편하게 해줌 -->
                  <td class="arrow" onclick="prevCalendar()">
                    <label><</label>
                  </td>
                  <td align="center" id="tbCalendarYM" colspan="5">
                    yyyy년 m월
                  </td>
                  <td class="arrow"  onclick="nextCalendar()">
                    <label>></label>
                  </td>
                </tr>
                <tr>
                  <td align="center"><font color ="#F79DC2">일</font></td>
                  <td align="center">월</td>
                  <td align="center">화</td>
                  <td align="center">수</td>
                  <td align="center">목</td>
                  <td align="center">금</td>
                  <td align="center"><font color ="skyblue">토</font></td>
                </tr> 
              </tbody>
            </table>
          </div>
        </li>
		</ul>
	</c:otherwise>
</c:choose>

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

<script type="text/javascript">
        var today = new Date();//오늘 날짜//내 컴퓨터 로컬을 기준으로 today에 Date 객체를 넣어줌
        var date = new Date();//today의 Date를 세어주는 역할
        function prevCalendar() {//이전 달
        // 이전 달을 today에 값을 저장하고 달력에 today를 넣어줌
        //today.getFullYear() 현재 년도//today.getMonth() 월  //today.getDate() 일 
        //getMonth()는 현재 달을 받아 오므로 이전달을 출력하려면 -1을 해줘야함
         today = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());
         buildCalendar(); //달력 cell 만들어 출력 
        }
 
        function nextCalendar() {//다음 달
            // 다음 달을 today에 값을 저장하고 달력에 today 넣어줌
            //today.getFullYear() 현재 년도//today.getMonth() 월  //today.getDate() 일 
            //getMonth()는 현재 달을 받아 오므로 다음달을 출력하려면 +1을 해줘야함
             today = new Date(today.getFullYear(), today.getMonth() + 1, today.getDate());
             buildCalendar();//달력 cell 만들어 출력
        }
        function buildCalendar(){//현재 달 달력 만들기
            var doMonth = new Date(today.getFullYear(),today.getMonth(),1);
            //이번 달의 첫째 날,
            //new를 쓰는 이유 : new를 쓰면 이번달의 로컬 월을 정확하게 받아온다.     
            //new를 쓰지 않았을때 이번달을 받아오려면 +1을 해줘야한다. 
            //왜냐면 getMonth()는 0~11을 반환하기 때문
            var lastDate = new Date(today.getFullYear(),today.getMonth()+1,0);
            //이번 달의 마지막 날
            //new를 써주면 정확한 월을 가져옴, getMonth()+1을 해주면 다음달로 넘어가는데
            //day를 1부터 시작하는게 아니라 0부터 시작하기 때문에 
            //대로 된 다음달 시작일(1일)은 못가져오고 1 전인 0, 즉 전달 마지막일 을 가져오게 된다
            var tbCalendar = document.getElementById("calendar");
            //날짜를 찍을 테이블 변수 만듬, 일 까지 다 찍힘
            var tbCalendarYM = document.getElementById("tbCalendarYM");
            //테이블에 정확한 날짜 찍는 변수
            //innerHTML : js 언어를 HTML의 권장 표준 언어로 바꾼다
            //new를 찍지 않아서 month는 +1을 더해줘야 한다. 
             tbCalendarYM.innerHTML = "<span class='pickYear'>"+today.getFullYear()+"</span>" + "년 " + "<span class='pickMonth'>"+(today.getMonth()+ + 1)+"</span>" + "월"; 
 
             /*while은 이번달이 끝나면 다음달로 넘겨주는 역할*/
            while (tbCalendar.rows.length > 2) {
            //열을 지워줌
            //기본 열 크기는 body 부분에서 2로 고정되어 있다.
                  tbCalendar.deleteRow(tbCalendar.rows.length-1);
                  //테이블의 tr 갯수 만큼의 열 묶음은 -1칸 해줘야지 
                //30일 이후로 담을달에 순서대로 열이 계속 이어진다.
             }
             var row = null;
             row = tbCalendar.insertRow();
             //테이블에 새로운 열 삽입//즉, 초기화
             var cnt = 0;// count, 셀의 갯수를 세어주는 역할
            // 1일이 시작되는 칸을 맞추어 줌
             for (i=0; i<doMonth.getDay(); i++) {
             /*이번달의 day만큼 돌림*/
                  cell = row.insertCell();//열 한칸한칸 계속 만들어주는 역할
                  cnt = cnt + 1;//열의 갯수를 계속 다음으로 위치하게 해주는 역할
             }
            /*달력 출력*/
             for (i=1; i<=lastDate.getDate(); i++) { 
             //1일부터 마지막 일까지 돌림
                  //tr row에 class trDays클래스 추가하여, css 제어
                  
                  cell = row.insertCell();//열 한칸한칸 계속 만들어주는 역할
                  cell.innerHTML = i;//셀을 1부터 마지막 day까지 HTML 문법에 넣어줌
                  cell.classList.add('pickDay');
                  /*cell.id = "day"+i;*/
                  cnt = cnt + 1;//열의 갯수를 계속 다음으로 위치하게 해주는 역할
              if (cnt % 7 == 1) {/*일요일 계산*/
                  //1주일이 7일 이므로 일요일 구하기
                  //월화수목금토일을 7로 나눴을때 나머지가 1이면 cnt가 1번째에 위치함을 의미한다
                cell.innerHTML = i
                //1번째의 cell에만 색칠
                cell.classList.add('pickDay');
                cell.style.color = '#F79DC2';
            }    
              if (cnt%7 == 0){/* 1주일이 7일 이므로 토요일 구하기*/
                  //월화수목금토일을 7로 나눴을때 나머지가 0이면 cnt가 7번째에 위치함을 의미한다
                  cell.innerHTML = i;
                  //7번째의 cell에만 색칠
                   row = calendar.insertRow();
                   //토요일 다음에 올 셀을 추가
                   cell.classList.add('pickDay');
                   cell.style.color = 'skyblue';
              }
              /*오늘의 날짜에 노란색 칠하기*/
              if (today.getFullYear() == date.getFullYear()
                 && today.getMonth() == date.getMonth()
                 && i == date.getDate()) {
                  //달력에 있는 년,달과 내 컴퓨터의 로컬 년,달이 같고, 일이 오늘의 일과 같으면
                cell.bgColor = "#FAF58C";//셀의 배경색을 노랑으로 
               }
             }
        }
        const curPickDay = {
          activeDTag: null
        }

        


    </script>

<script language="javascript" type="text/javascript">
    buildCalendar();//

    const calBody = document.querySelector('.cal-body');
    let dDay = document.querySelector('.dDay');
    
    calBody.addEventListener('click', (e) => {
      
      if(e.target.classList.contains('pickDay')) {
        
        const pickYear = document.querySelector('.pickYear').textContent;
        const pickMonth = document.querySelector('.pickMonth').textContent;
        if(curPickDay.activeDTag) {
          curPickDay.activeDTag.classList.remove('dDay');
        }
        e.target.classList.add('dDay');
        curPickDay.activeDTag = e.target;

        console.log(pickYear+'-'+pickMonth+'-'+e.target.textContent);
      }
    });
</script>
