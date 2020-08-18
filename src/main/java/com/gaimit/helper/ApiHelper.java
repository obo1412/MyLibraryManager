package com.gaimit.helper;

public class ApiHelper {
	//알라딘 과거 인증키
	/*String apiKey = "?ttbkey=ttbanfyanfy991303001";*/
	
	//2020.06.10 latest version key
	private String aladinTtbKey = "ttblib1207001";
	private String NLKcertKey = "6debf14330e5866f7c50d47a9c84ae8f";

	public String getAladinJsonIsbnResult(String isbn) {
		String result = null;
		
		String apiUrl = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey="
				+ aladinTtbKey +"&output=js&Version=20131101&OptResult=ebookList,usedList,reviewList";
		if(isbn.length() == 13) {
			result = apiUrl+"&itemIdType=ISBN13"+"&ItemId="+ isbn;
		} else if(isbn.length() == 10) {
			result = apiUrl+"&itemIdType=ISBN"+"&ItemId="+ isbn;
		}
		
		return result;
	}
	
	public String getSeojiJsonIsbnResult(String isbn) {
		String result = null;
		
		String apiUrl = "http://seoji.nl.go.kr/landingPage/SearchApi.do?cert_key="
				+ NLKcertKey + "&result_style=json&page_no=1&page_size=1";
		if(isbn.length() == 13) {
			result = apiUrl + "&isbn="+ isbn;
		} else if(isbn.length() == 10) {
			result = apiUrl +"&isbn="+ isbn;
		}
		
		return result;
	}
	
	public String getNlXmlIsbnResult(String isbn) {
		String result = null;
		
		String apiUrl = "https://www.nl.go.kr/NL/search/openApi/search.do?key="
		+ NLKcertKey + "&apiType=xml&pageSize=10&pageNum=1&detailSearch=true&isbnOp=isbn";
		if(isbn.length() == 13) {
			result = apiUrl + "&isbnCode="+ isbn;
		} else if(isbn.length() == 10) {
			result = apiUrl +"&isbnCode="+ isbn;
		}
		
		return result;
	}
	
	//SearchBook에 /book/search_nl_book.do 부분에 아직 수정이 안되었다.
	//현재 이 기능은 브라우저에선 결과값을 주지만,
	//코드 상태로는 서버 응답값이 502 에러가 발생한다. 나중에 처리하자
	public String getNlXmlTypeResult() {
		String result = "https://www.nl.go.kr/NL/search/openApi/search.do?key="
				+ NLKcertKey + "&apiType=xml&detailSearch=true";
		
		return result;
	}
}
