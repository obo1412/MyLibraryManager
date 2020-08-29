package com.gaimit.mlm.controller.book;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gaimit.helper.ApiHelper;
import com.gaimit.helper.AuthorCode;
import com.gaimit.helper.PageHelper;
import com.gaimit.helper.Util;
import com.gaimit.helper.WebHelper;
import com.gaimit.mlm.model.Book;
import com.gaimit.mlm.model.BookHeld;
import com.gaimit.mlm.model.Manager;
import com.gaimit.mlm.service.BookHeldService;
import com.gaimit.mlm.service.BookService;
import com.gaimit.mlm.service.ManagerService;

@Controller
public class SearchBook {
	/** log4j 객체 생성 및 사용할 객체 주입받기 */
	//private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);
	// --> import study.spring.helper.WebHelper;
	@Autowired
	WebHelper web;
	
	@Autowired
	PageHelper page;
	
	@Autowired
	Util util;
	
	@Autowired
	AuthorCode authorCode;
	
	@Autowired
	ManagerService managerService;
	
	@Autowired
	BookHeldService bookHeldService;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	ApiHelper apiHelper;
	
	/** 도서 검색 페이지 */
	@RequestMapping(value = "/book/search_book.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView doRun(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		/** 로그인 여부 검사 */
		// 로그인중인 회원 정보 가져오기
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
		}
		
		String isbn = web.getString("search-book-info", "");
		String bookTitle = web.getString("bookTitle", "");
		String author = web.getString("author", "");
		
		String regChk = web.getString("straightReg");
		String regCheckBox = "";
		if(regChk!=null) {
			regCheckBox = "checked";
		}
		
		/* 바코드 호출 */
		// 파라미터를 저장할 Beans
		Book country = new Book();
		
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
		bookHeld.setIsbn10Book(isbn);
		bookHeld.setIsbn13Book(isbn);
		bookHeld.setTitleBook(bookTitle);
		bookHeld.setWriterBook(author);
		
		//바코드 번호 생성을 위한 변수 선언
		int lastEmptyLocalBarcode = 0;
		BookHeld lastLocalBarcode = new BookHeld();
		String barcodeInit = "";
		String newBarcode = "";
		int barcodeInitCount = 0;
		
		int copyCode = 0;
		
		List<Book> countryList = null;
		
		//오늘 등록된 도서목록
		List<BookHeld> regTodayList = null;
		//페이지를 위한 파라미터
		int nowPage = web.getInt("page", 1);
		int totalCount = 0;
		
		String atcOut = null;
		
		int classCodeHead = 0;
		
		//aladin json 변수
		String category = null;
		String pubDate = null;
		//String으로 받아온 int 형태의 값은 int로 파싱 price page
		String price = null;
		int intPrice = 0;
		String itemPage = null;
		int intPage = 0;
		
		String isbn10 = null;
		String description = null;
		String cover = null;
		//aladin json 변수 끝
		
		//try catch 문 하나로 묶기위하여, 지역변수들 다 try문 밖으로 이동
		//try catch문이 하나로 유지되어야 트랜젝션 유지가능
		//트랜젝션은 하나의 try문에서 쿼리 오류 발생시,
		//동작했던 쿼리문 전체 롤백을 해야하므로.
		JSONObject jsonAladin = new JSONObject();
		JSONObject jsonSeoji = new JSONObject();
		ArrayList<String> xmlClassNoArray = new ArrayList<String>();
		
		String clsCode = null;	// view전달 변수
		
		//저자코드 생성을 위한 제목, 저자명 변수 1순위:알라딘 2순위: 국중
		String titleToCode = null;
		String authorToCode = null;
		String viewPublisher = null;
		
		String viewIsbn13 = null;
		
		//서지정보에서 볼륨코드를 담을 변수
		String volCode = null;
		//jsonSeoji.docs[0].VOL 왼쪽 코드로 뷰페이지에 구현됨.
		String bookSize = null;
		//그러나 바로등록을 위해 변수 담기
		
		//이하 복본 검사를 위한 변수
		BookHeld callIdBook = null;
		int copyCheckBookHeld = 0;
		int zeroCopyCode = 0;
		int firstCopyCode = 0;
		
		try {
			//국가목록 조회
			countryList = bookService.selectCountryListOnly(country);
			
			/*바코드 호출 바코드 헤드 검사*/
			lastLocalBarcode = bookHeldService.selectLastLocalBarcode(bookHeld);
			/*바코드 헤드가 null 이 아니면 최종값이 있다는 것 그 헤드를 사용하면 된다*/
			if(lastLocalBarcode != null) {
				barcodeInit = lastLocalBarcode.getLocalIdBarcode();
				barcodeInit = util.strExtract(barcodeInit);
				/*바코드말머리가 있다면 말머리의 길이를 구한다.
				 *말머리의 길이로 mapper에서 바코드 select함*/
				barcodeInitCount = barcodeInit.length();
			}
			/*바코드 말머리의 길이를 bookHeld에 주입*/
			bookHeld.setBarcodeInitCount(barcodeInitCount);
			
			/*바코드 번호가 1번인지 검사*/
			int firstBarcode = bookHeldService.selectFirstLocalBarcode(bookHeld);
			/*1번이면, 중간에 비어 있는 바코드 숫자로 바코드 등록*/
			/*1이 아니면 1로 바코드 등록*/
			if(firstBarcode == 1) {
				lastEmptyLocalBarcode = bookHeldService.selectEmptyLocalBarcode(bookHeld);
			} else {
				lastEmptyLocalBarcode = 1;
			}
			
			newBarcode = util.makeStrLength(8, barcodeInit, lastEmptyLocalBarcode);
			/* 바코드 호출 끝 */
			
			//아래 selectBookId가 null일 경우 NPE발생, NPE삭제하지 않고 처리할 수가 없어서
			//그냥 selectBookCount로 조건을 걸어둠.
			int checkBookTable= bookHeldService.selectBookCount(bookHeld);
			if(checkBookTable > 0) {
				/* copyCode 복본기호 호출 */
				callIdBook = bookHeldService.selectBookId(bookHeld);
				
				//null 체크를 위하여 Integer로 형변환
				if(((Integer) callIdBook.getIdBook() != 0) && ((Integer) callIdBook.getIdBook() != null)) {
					bookHeld.setBookIdBook(callIdBook.getIdBook());
					copyCheckBookHeld = bookHeldService.selectBookHeldCount(bookHeld);
					/*복본이 존재하는지 체크*/
					if(copyCheckBookHeld > 1) {
						/* 결과값이 0이면 복본기호는 0이 아니라는 말이고, 결과값이 1이면 0이라는 말 */
						zeroCopyCode = bookHeldService.selectZeroCopyCodeCount(bookHeld);
						if(zeroCopyCode == 1) {
							/*0번복본이 있는 상태, 최초 복본기호가 2가 아니면 2로 지정
							 * 중간에 2번 복본이 빠졌다는 것.*/
							firstCopyCode = bookHeldService.selectFirstCopyCode(bookHeld);
							if(firstCopyCode != 2) {
								copyCode = 2;
								bookHeld.setCopyCode(copyCode);
							} else {
								/*최초 복본기호가 2가 있다면, 그 이상의 빈번호임.*/
								copyCode = bookHeldService.selectLastEmptyCopyCode(bookHeld);
								bookHeld.setCopyCode(copyCode);
							}
						} else {
							//0번 복본이 없는 상태엔 걍 0번으로
							copyCode = 0;
							bookHeld.setCopyCode(copyCode);
						}
						
					} else if(copyCheckBookHeld == 1) {
						zeroCopyCode = bookHeldService.selectZeroCopyCodeCount(bookHeld);
						if(zeroCopyCode == 1) {
							copyCode = 2;
							bookHeld.setCopyCode(copyCode);
						}//암시롱 안하면 그냥 원래 선언한대로 0
					}
				}
				/* 복본기호 호출 끝 */
			}

			
			//분류기호 3단계 절차 아래 순서대로
			String kdcStr = null;	// 서지 KDC
			String clsNo = null;	// 국중 class_no
			String eac3 = null;		// 서지 EA_ADD_CODE
			
			// 알라딘 api 에서 json 수신
			//apiHelper로 OpenApi호출 중앙관리
			String apiUrlFull = apiHelper.getAladinJsonIsbnResult(isbn);
			//apiHelper로 OpenApi호출
			URL url = new URL(apiUrlFull);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.getResponseCode(); // 응답코드 리턴 200번대 404 등등
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			String result = br.readLine();
			br.close();
	
			JSONParser jsonParser = new JSONParser();
			jsonAladin = (JSONObject) jsonParser.parse(result);
			
			//서지, 국중, 알라딘 순으로 변수를 불러와야하는데,
			//아래 변수선언을 일찍해놓으면, 순서가 맞지 않아서,
			// 의미없는 객체 선언이므로, 주석처리
			//서지와 국중을 불러들인 이후에, 알라딘 값이 존재할 경우에 알라딘 값 대입.
			/*if(jsonAladin.get("item") != null && !jsonAladin.get("item").equals("")) {
				//json타입으로 값을 가져옴
				JSONArray itemArray = (JSONArray) jsonAladin.get("item");
				JSONObject itemObj = (JSONObject) itemArray.get(0);
				// 알라딘 api 호출
			}*/
			
			/*이하 서지정보호출 제목 저자를 넣기 위해 순서 바꿈
			 * */
			// 서지정보 api 호출
			
			
			String apiUrlSeojiFull = apiHelper.getSeojiJsonIsbnResult(isbn);
			
			URL urlSeoji = new URL(apiUrlSeojiFull);
			HttpURLConnection conSeoji = (HttpURLConnection)urlSeoji.openConnection();
			conSeoji.setRequestMethod("GET");
			conSeoji.getResponseCode(); // 응답코드 리턴 200번대 404 등등
			
			BufferedReader brSeoji = new BufferedReader(new InputStreamReader(conSeoji.getInputStream(),"UTF-8"));
			
			String resultSeoji = brSeoji.readLine();
			
			brSeoji.close();
	
			JSONParser jsonParserSeoji = new JSONParser();
			jsonSeoji = (JSONObject) jsonParserSeoji.parse(resultSeoji);
			
			if(!jsonSeoji.get("TOTAL_COUNT").equals("0")) {
				JSONArray itemSeojiArray = (JSONArray) jsonSeoji.get("docs");
				JSONObject itemSeojiObj = (JSONObject) itemSeojiArray.get(0);
				
				Object kdc = itemSeojiObj.get("KDC");
				Object eac = itemSeojiObj.get("EA_ADD_CODE");
				kdcStr = String.valueOf(kdc);
				
				String eacStr = String.valueOf(eac);
				//5자리중 뒤에 3자리만
				if(eacStr!=null&& !"".equals(eacStr)) {
					eac3 = eacStr.substring(2);
				}
				
				Object titleSeo = itemSeojiObj.get("TITLE");
				Object authorSeo = itemSeojiObj.get("AUTHOR");
				
				authorToCode = String.valueOf(authorSeo);
				titleToCode = String.valueOf(titleSeo);
				
				Object volCodeObj = itemSeojiObj.get("VOL");
				volCode = String.valueOf(volCodeObj);
				
				Object bookSizeObj = itemSeojiObj.get("BOOK_SIZE");
				bookSize = String.valueOf(bookSizeObj);
				
				Object publisherObj = itemSeojiObj.get("PUBLISHER");
				viewPublisher = String.valueOf(publisherObj);
				
				Object isbnObj = itemSeojiObj.get("EA_ISBN");
				viewIsbn13 = String.valueOf(isbnObj);
			}
			
			//국립중앙도서관 아래 api검색
			//http://www.nl.go.kr/app/nl/search/openApi/search.jsp?key=6debf14330e5866f7c50d47a9c84ae8f&category=dan&detailSearch=true&isbnOp=isbn&isbnCode=8984993727
			// 국중은 openapi가 xml 형태밖에 없는 듯하여 xml 호출 구조
			
			ArrayList<String> titleAndAuthor = new ArrayList<String>();
				
			String apiUrlFullNl = apiHelper.getNlXmlIsbnResult(isbn);
			URL urlNl = new URL(apiUrlFullNl);
			HttpURLConnection conNl = (HttpURLConnection)urlNl.openConnection();
			conNl.setRequestMethod("GET");
			conNl.getResponseCode(); // 응답코드 리턴 200번대 404 등등
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc =builder.parse(conNl.getInputStream());
			
			NodeList nodeList = doc.getElementsByTagName("item");
			if(nodeList.getLength()>0) {
				for(int i =0; i<nodeList.getLength(); i++) {
					for(Node node = nodeList.item(i).getFirstChild(); node!=null;
						node=node.getNextSibling()) {
						if(node.getNodeName().equals("title_info")) {
							titleAndAuthor.add(node.getTextContent());
						}
						if(node.getNodeName().equals("author_info")) {
							titleAndAuthor.add(node.getTextContent());
						}
						if(node.getNodeName().equals("pub_info")) {
							//위에 먼저 서지에 정보를 넣고,
							//국중에 정보가 있으면 국중 정보를 대입.
							viewPublisher = node.getTextContent();
						}
						if(node.getNodeName().equals("isbn")) {
							viewIsbn13 = node.getTextContent();
						}
						if(node.getNodeName().equals("class_no")) {
							xmlClassNoArray.add(node.getTextContent());
						}
					}
				}
				if(xmlClassNoArray.get(0).length()!=0&&xmlClassNoArray.get(0)!=null) {
					clsNo = xmlClassNoArray.get(0);
				}
				if(titleAndAuthor.get(0).length()!=0&&titleAndAuthor.get(0)!=null) {
					titleToCode = titleAndAuthor.get(0);
				}
				if(titleAndAuthor.get(1).length()!=0&&titleAndAuthor.get(1)!=null) {
					authorToCode = titleAndAuthor.get(1);
				}
			}
			
			if(jsonAladin.get("item") != null && !jsonAladin.get("item").equals("")) {
			
				//json타입의 값인 jsonAladin에서 특정값을 가지고옴.
				JSONArray itemArray = (JSONArray) jsonAladin.get("item");
				JSONObject itemObj = (JSONObject) itemArray.get(0);
				//item의 [0] <- 첫번째 값을 가져옴
				Object authorObj = itemObj.get("author");
				Object titleObj = itemObj.get("title");
				Object publisherObj = itemObj.get("publisher");
				Object isbn13Obj = itemObj.get("isbn13");
				
				//authorToCode = (String)authorObj;
				//위처럼도 casting만 바꾸는 방법도 있음.
				if(!"".equals(authorObj)&&!"".equals(titleObj)) {
					authorToCode = String.valueOf(authorObj);
					titleToCode = String.valueOf(titleObj);
				}
				
				if(!"".equals(publisherObj)) {
					viewPublisher = String.valueOf(publisherObj);
				}
				
				if(!"".equals(isbn13Obj)) {
					viewIsbn13 = String.valueOf(isbn13Obj);
				}
				
				Object objCategory = itemObj.get("categoryName");
				category = String.valueOf(objCategory);
				Object objPubDate = itemObj.get("pubDate");
				pubDate = String.valueOf(objPubDate);
				JSONObject objSubInfo = (JSONObject) itemObj.get("subInfo");
				Object objPage = objSubInfo.get("itemPage");
				itemPage = String.valueOf(objPage);
				intPage = 0;
				if(!"".equals(itemPage)&&!"0".equals(itemPage)) {
					intPage = Integer.parseInt(itemPage);
				}
				Object objPrice = itemObj.get("priceStandard");
				price = String.valueOf(objPrice);
				intPrice = 0;
				if(!"".equals(price)&&!"0".equals(price)) {
					intPrice = Integer.parseInt(price);
				}
				Object objIsbn10 = itemObj.get("isbn");
				isbn10 = String.valueOf(objIsbn10);
				Object objCover = itemObj.get("cover");
				cover = String.valueOf(objCover);
				Object objDescription = itemObj.get("description");
				description = String.valueOf(objDescription);
			}
			
			//우선순위에 따라서 최종에 남은 도서명과 저자명으로
			//저자코드 생성
			if(titleToCode!=null&&authorToCode!=null) {
				atcOut = authorCode.authorCodeGen(authorToCode)
						+ authorCode.titleFirstLetter(titleToCode);
			}
			
			//ArrayList<String>은 get(index)과 set(index, 인자)로 컨트롤된다.
			/*xmlClassNoArray.set(0, "asdfa");
			System.out.println(xmlClassNoArray.get(0));*/
			
			//kdcStr!=null&&kdcStr.length()!=0 이 조건으로 했을 경우에 length() 부분에서
			// NPE 에러가 발생한다.
			if(kdcStr!=null&&(!"".equals(kdcStr))) {
				clsCode = kdcStr;
			} else if(clsNo!=null&&(!"".equals(clsNo))) {
				clsCode = clsNo;
			} else if(eac3!=null&&(!"".equals(eac3))) {
				clsCode = eac3;
			}
			
			if("".equals(clsCode)||"0".equals(clsCode)||clsCode==null) {
				
			} else {
				int classHead1 = (int) Float.parseFloat(clsCode)/100;
				classCodeHead = classHead1*100;
			}
			
			totalCount = bookHeldService.selectRegTodayBookCountForPage(bookHeld);
			page.pageProcess(nowPage, totalCount, 10, 5);
			bookHeld.setLimitStart(page.getLimitStart());
			bookHeld.setListCount(page.getListCount());
			
			regTodayList = bookHeldService.getRegTodayBookHeldList(bookHeld);
			if(regTodayList != null) {
				for(int i=0; i<regTodayList.size(); i++) {
					String classCode = regTodayList.get(i).getClassificationCode();
					classCode = util.getFloatClsCode(classCode);
					if(classCode != null&&!"".equals(classCode)) {
						float classCodeFloat = Float.parseFloat(classCode);
						int classCodeInt = (int) (classCodeFloat);
						String classCodeColor = util.getColorKDC(classCodeInt);
						regTodayList.get(i).setClassCodeColor(classCodeColor);
					}
				}
			}
			
			//바로 등록 체크시 동작 *******************
			if(regChk != null && clsCode != null && !"".equals(isbn)) {
				if((titleToCode==null || "".equals(titleToCode)) || (authorToCode==null || "".equals(authorToCode))) {
					return web.redirect(null, "도서명 또는 저자명이 비어있습니다.");
				}
				
				//json타입의 값인 jsonAladin에서 특정값을 가지고옴.
				//위에 선언됨
				/*JSONArray itemArray = (JSONArray) jsonAladin.get("item");
				JSONObject itemObj = (JSONObject) itemArray.get(0);*/
				//item의 [0] <- 첫번째 값을 가져옴
				
				
				//book테이블에 도서가 없을경우 등록을 위하여.
				bookHeld.setTitleBook(titleToCode);
				bookHeld.setWriterBook(authorToCode);
				bookHeld.setCategoryBook(category);
				//publisher는 위에서 viewPublisher에 정보를 순차적으로 모았음.
				bookHeld.setPublisherBook(viewPublisher);
				bookHeld.setPubDateBook(pubDate);
				bookHeld.setPriceBook(intPrice);
				bookHeld.setIsbn10Book(isbn10);
				//book테이블에 도서가 있는지 체크하기 위하여.
				bookHeld.setIsbn13Book(isbn);
				bookHeld.setDescriptionBook(description);
				//book테이블에 없는 도서일 경우를 위하여 정보 주입
				
				//manager로부터 도서관번호 부여.
				bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
				bookHeld.setIsbn13(isbn);
				bookHeld.setIsbn10(isbn10);
				bookHeld.setTitle(titleToCode);
				bookHeld.setWriter(authorToCode);
				bookHeld.setAuthorCode(atcOut);
				//publisher는 위에서 viewPublisher에 정보를 순차적으로 모았음.
				bookHeld.setPublisher(viewPublisher);
				bookHeld.setPubDate(pubDate);
				bookHeld.setCategory(category);
				bookHeld.setPage(intPage);
				bookHeld.setBookSize(bookSize);
				bookHeld.setPrice(intPrice);
				bookHeld.setBookOrNot("BOOK");
				bookHeld.setPurchasedOrDonated(1);
				bookHeld.setClassificationCode(clsCode);
				/*bookHeld.setAdditionalCode(additionalCode);*/
				//바로 등록시 별치기호 없음 
				volCode = util.getNumVolumeCode(volCode);
				bookHeld.setVolumeCode(volCode);
				bookHeld.setImageLink(cover);
				bookHeld.setDescription(description);
				bookHeld.setAvailable(1);
				
				bookHeld.setIdCountry(1);
				
				
				/*String viewBarcodeInit = util.strExtract(newBarcode);*/
				int viewBarNum = util.numExtract(newBarcode);
				
				/*if(newBarcode.length() != 8) {
					return web.redirect(null, "바코드를 8자리로 맞추어 주세요.");
				} else if(viewBarcodeInit.length() > 3 ) {
					return web.redirect(null, "바코드 머리 글자수는 3자리 이하여야 합니다.");
				}*/
				
				//초기에 한번 선언되어 있음.
				/*//바코드 번호 생성을 위한 변수 선언
				int lastEmptyLocalBarcode = 0; //바코드 번호 빈자리 
				BookHeld lastLocalBarcode = new BookHeld(); //바코드 헤드를 위한 마지막 바코드 참조
				String barcodeInit = ""; 
				int barcodeInitCount = 0;*/
				
				//barcode 호출
				
				//바코드 헤드 검사
				/*lastLocalBarcode = bookHeldService.selectLastLocalBarcode(bookHeld);*/
				/*바코드 헤드가 null 이 아니면 최종값이 있다는 것 그 헤드를 사용하면 된다*/
				/*if(lastLocalBarcode != null) {
					barcodeInit = lastLocalBarcode.getLocalIdBarcode();
					barcodeInit = util.strExtract(barcodeInit);
					//바코드말머리가 있다면 말머리의 길이를 구한다.
					 //말머리의 길이로 mapper에서 바코드 select함
					barcodeInitCount = barcodeInit.length();
				}
				//바코드 말머리의 길이를 bookHeld에 주입
				bookHeld.setBarcodeInitCount(barcodeInitCount);*/
				//바코드 뒤 숫자 중복검사를 위하여 값 주입
				bookHeld.setNewBarcodeForDupCheck(viewBarNum);
				//위 viewBarNum를 중복검사 변수로 사용.
				//중복되는 번호가 있다면 impl 단계에서 예외처리
				bookHeldService.selectDupCheckLocalBarcode(bookHeld);
				
				/*//바코드 번호가 1번인지 검사
				firstBarcode = bookHeldService.selectFirstLocalBarcode(bookHeld);
				//1번이면, 중간에 비어 있는 바코드 숫자로 바코드 등록
				//1이 아니면 1로 바코드 등록
				if(firstBarcode == 1 ) {
					lastEmptyLocalBarcode = bookHeldService.selectEmptyLocalBarcode(bookHeld);
				} else {
					lastEmptyLocalBarcode = 1;
				}*/
				
				//위 비어있는 바코드 번호를 솔팅index에 주입
				bookHeld.setSortingIndex(lastEmptyLocalBarcode);
				
				/*뷰페이지에서 넘어온 바코드 숫자와 ok컨트롤러에서 조사한 바코드 뒤숫자가
				 * 같지 않으면, 콜백 발생. */
				/*if(viewBarNum != lastEmptyLocalBarcode) {
					return web.redirect(null, "최신 바코드 번호가 일치하지 않습니다.");
				}*/
		
				/* 바코드 호출 끝 */
				
				/*소문자 바코드를 대문자로 변환*/
				newBarcode = newBarcode.toUpperCase();
				bookHeld.setLocalIdBarcode(newBarcode);

				if (checkBookTable > 0) {
					//위에 bookHeld변수에 이미 bookId를 넣었음.
					//copyCode도 이미 다 들어가있음
					bookHeldService.insertBookHeld(bookHeld);
				} else if (checkBookTable == 0) {
					//book에 아예 없을 때
					bookHeldService.insertBook(bookHeld);
					//id_book을 받아오기 위한 객체
					callIdBook = bookHeldService.selectBookId(bookHeld);
					//callIdBook에 id_book을 담고 bookHeld에 전달.
					bookHeld.setBookIdBook(callIdBook.getIdBook());
					
					bookHeldService.insertBookHeld(bookHeld);
				}
				
				
				
				// 조회 결과를 View에게 전달한다.
				//model.addAttribute("brwList", list);
				//redirect로 페이지 이동시 CUD 중복처리 방지를 위해 초기화
				//위 VIEW페이지 전달 변수가 소용없어짐

				/** (9) 가입이 완료되었으므로 메인페이지로 이동 */
				return web.redirect(web.getRootPath() + "/book/reg_book_regChked.do", null);
				//modelAndView 리턴 기능을 사용하면, 페이지 초기화가 안되서 새로고침만해도
				//중복된 도서가 등록된다.
				/*return new ModelAndView("book/reg_book");*/
			}
			
			
		} catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** 4) View 처리하기 */
		// 조회 결과를 View에게 전달한다.
		/*model.addAttribute("keyword", keyword);*/
		model.addAttribute("countryList", countryList);
		model.addAttribute("isbn", isbn);
		model.addAttribute("atcOut", atcOut);
		model.addAttribute("barcodeInit", barcodeInit);
		model.addAttribute("newBarcode", newBarcode);
		model.addAttribute("copyCode", copyCode);
		model.addAttribute("bookTitle", titleToCode);
		model.addAttribute("author", authorToCode);
		model.addAttribute("publisher", viewPublisher);
		model.addAttribute("isbn13", viewIsbn13);
		model.addAttribute("jsonAladin", jsonAladin);
		model.addAttribute("jsonSeoji", jsonSeoji);
		model.addAttribute("xmlClassNoArray", xmlClassNoArray);
		model.addAttribute("clsCode", clsCode);
		model.addAttribute("classCodeHead", classCodeHead);
		model.addAttribute("regCheckBox", regCheckBox);
		model.addAttribute("regTodayList", regTodayList);
		model.addAttribute("page", page);
		model.addAttribute("pageDefUrl", "/book/reg_book.do");
		model.addAttribute("bookSize", bookSize);
		
		return new ModelAndView("book/reg_book");
	}
	
	
	
	/** 복본기호 체크 */
	/*@RequestMapping(value = "/book/book_held_check_copyCode.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView callCopyCode(Locale locale, Model model) {
		
		*//** 1) WebHelper 초기화 및 파라미터 처리 *//*
		web.init();
		
		*//** 로그인 여부 검사 *//*
		// 로그인중인 회원 정보 가져오기
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
		}
		
		Book country = new Book();
		
		String newBarcode = web.getString("newBarcode");
		
		String isbn = web.getString("search-book-info", "");
		String bookTitle = web.getString("bookTitle", "");
		String author = web.getString("author", "");
		String classCode = web.getString("classificationCode","");
		String additionalCode = web.getString("additionalCode","");
		String volumeCode = web.getString("volumeCode","");
		String bookCateg = web.getString("bookCateg","");
		String publisher = web.getString("publisher","");
		String pubDate = web.getString("pubDate","");
		String page = web.getString("page","");
		String price = web.getString("price","");
		//검색창 아닌 아래 isbn 항목
		String isbn13 = web.getString("isbn13","");
		String isbn10 = web.getString("isbn10","");
		String bookCover = web.getString("bookCover","");
		String bookDesc = web.getString("bookDesc","");
		
		int copyCode = 0;
		
		String atcOut = null;
		if(author!=null&&!author.equals("")) {
			atcOut = authorCode.authorCodeGen(author)
					+ authorCode.titleFirstLetter(bookTitle);
		}
		
		//국가 목록 조회
		List<Book> countryList = null;
		
		if(!bookTitle.equals("") && !author.equals("")) {
			// 바코드 호출 
			// 파라미터를 저장할 Beans
			BookHeld bookHeld = new BookHeld();
			bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
			bookHeld.setIsbn10Book(isbn);
			bookHeld.setIsbn13Book(isbn);
			bookHeld.setTitleBook(bookTitle);
			bookHeld.setWriterBook(author);
			
			try {
				//국가 목록 조회
				countryList = bookService.selectCountryListOnly(country);
				//아래 selectBookId가 null일 경우 NPE발생, NPE삭제하지 않고 처리할 수가 없어서
				//그냥 selectBookCount로 조건을 걸어둠.
				int checkBookTable= bookHeldService.selectBookCount(bookHeld);
				if(checkBookTable > 0) {
					 copyCode 복본기호 호출 
					BookHeld callIdBook = bookHeldService.selectBookId(bookHeld);
					
					//null 체크를 위하여 Integer로 형변환
					if(((Integer) callIdBook.getIdBook() != 0) && ((Integer) callIdBook.getIdBook() != null)) {
						bookHeld.setBookIdBook(callIdBook.getIdBook());
						int copyCheckBookHeld = bookHeldService.selectBookHeldCount(bookHeld);
						//복본이 존재하는지 체크
						if(copyCheckBookHeld > 1) {
							// 결과값이 0이면 복본기호는 0이 아니라는 말이고, 결과값이 1이면 0이라는 말 
							int zeroCopyCode = bookHeldService.selectZeroCopyCodeCount(bookHeld);
							if(zeroCopyCode == 1) {
								//0번복본이 있는 상태, 최초 복본기호가 2가 아니면 2로 지정
								 //* 중간에 2번 복본이 빠졌다는 것.
								int firstCopyCode = bookHeldService.selectFirstCopyCode(bookHeld);
								if(firstCopyCode != 2) {
									copyCode = 2;
								} else {
									//최초 복본기호가 2가 있다면, 그 이상의 빈번호임.
									copyCode = bookHeldService.selectLastEmptyCopyCode(bookHeld);
								}
							} else {
								//0번 복본이 없는 상태엔 걍 0번으로
								copyCode = 0;
							}
							
						} else if(copyCheckBookHeld == 1) {
							int zeroCopyCode = bookHeldService.selectZeroCopyCodeCount(bookHeld);
							if(zeroCopyCode == 1) {
								copyCode = 2;
							}//암시롱 안하면 그냥 원래 선언한대로 0
						}
					}
					 //복본기호 호출 끝 
				}
			} catch (Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
			}
		} else {
			return web.redirect(null, "복본기호 체크를 위하여 도서 제목과 저자를 작성하여주세요");
		}
		
		model.addAttribute("countryList", countryList);
		model.addAttribute("copyCode", copyCode);
		model.addAttribute("bookTitle", bookTitle);
		model.addAttribute("author", author);
		model.addAttribute("classCode", classCode);
		model.addAttribute("additionalCode", additionalCode);
		model.addAttribute("volumeCode", volumeCode);
		model.addAttribute("bookCateg", bookCateg);
		model.addAttribute("atcOut", atcOut);
		model.addAttribute("publisher", publisher);
		model.addAttribute("pubDate", pubDate);
		model.addAttribute("page", page);
		model.addAttribute("price", price);
		model.addAttribute("isbn13", isbn13);
		model.addAttribute("isbn10", isbn10);
		model.addAttribute("bookCover", bookCover);
		model.addAttribute("bookDesc", bookDesc);
		model.addAttribute("newBarcode", newBarcode);
		
		return new ModelAndView("book/reg_book");
	}*/
	
	
	
	
	
	
	/** 국중에서 도서 검색 */
	@RequestMapping(value = "/book/search_nl_book.do", method = RequestMethod.GET)
	public ModelAndView nlSearch(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		/** 로그인 여부 검사 */
		// 로그인중인 회원 정보 가져오기
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
		}
		
		int searchOpt = web.getInt("searchOpt");
		String keyword = web.getString("search-book-info", "");
		
		if(keyword != null && !keyword.equals("")) {
			String NLKcertKey = "6debf14330e5866f7c50d47a9c84ae8f";
			String apiUrl = null;
			
			//현재 이 기능은 브라우저에선 결과값을 주지만,
			//코드 상태로는 서버 응답값이 502 에러가 발생한다. 나중에 처리하자
			switch (searchOpt) {
			case 1:
				apiUrl = "http://www.nl.go.kr/app/nl/search/openApi/search.jsp?key="+NLKcertKey+"&category=dan&detailSearch=true&isbnOp=isbn&isbnCode=";
				break;
			case 2:
				apiUrl = "http://www.nl.go.kr/app/nl/search/openApi/search.jsp?key="+NLKcertKey+"&category=dan&detailSearch=true&f1=title&v1=";
				break;
			case 3:
				apiUrl = "http://www.nl.go.kr/app/nl/search/openApi/search.jsp?key="+NLKcertKey+"&category=dan&detailSearch=true&f1=author&v1=";
				break;
			case 4:
				apiUrl = "http://www.nl.go.kr/app/nl/search/openApi/search.jsp?key="+NLKcertKey+"&pageSize=100&category=dan&detailSearch=true&f1=title&f2=author";
				break;
			}
			
			//국립중앙도서관 아래 api검색
			//http://www.nl.go.kr/app/nl/search/openApi/search.jsp?key=6debf14330e5866f7c50d47a9c84ae8f&category=dan&detailSearch=true&isbnOp=isbn&isbnCode=8984993727
			// 국중은 openapi가 xml 형태밖에 없는 듯하여 xml 호출 구조
			ArrayList<Object> xmlArray = new ArrayList<Object>();
			try {
				
				String apiUrlFull = null;
				apiUrlFull = apiUrl + keyword;
				
				if(searchOpt == 4) {
					String[] array = keyword.split(" ");
					apiUrlFull = apiUrl + "&v1="+ array[0] +"&v2="+ array[1];
				}
				
				
				URL url = new URL(apiUrlFull);
				HttpURLConnection con = (HttpURLConnection)url.openConnection();
				con.setRequestMethod("GET");
				con.getResponseCode(); // 응답코드 리턴 200번대 404 등등
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc =builder.parse(con.getInputStream());
				
				NodeList nodeList = doc.getElementsByTagName("item");
				for(int i =0; i< nodeList.getLength(); i++) {
					Map<String, String> tempMap = new HashMap<String, String>();
					for(Node node = nodeList.item(i).getFirstChild(); node!=null; node=node.getNextSibling()) {
						if(node.getNodeName().equals("title_info")) {
							tempMap.put("title_info", node.getTextContent());
						}
						if(node.getNodeName().equals("author_info")) {
							tempMap.put("author_info", node.getTextContent());
						}
						if(node.getNodeName().equals("pub_info")) {
							tempMap.put("pub_info", node.getTextContent());
						}
						if(node.getNodeName().equals("isbn")) {
							tempMap.put("isbn", node.getTextContent());
						}
						if(node.getNodeName().equals("class_no")) {
							tempMap.put("class_no", node.getTextContent());
							xmlArray.add(tempMap);
						}
					}
				}
				model.addAttribute("xmlArray", xmlArray);
			} catch(Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
			}
		}
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("searchOpt", searchOpt);
		
		
		return new ModelAndView("book/search_nl_book");
	}
}
