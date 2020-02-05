<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>

<!-- 아래기능은, ajax 기본 호출-->
<%-- var name = null;
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
console.log(name); --%>