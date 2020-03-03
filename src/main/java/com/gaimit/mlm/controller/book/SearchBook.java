package com.gaimit.mlm.controller.book;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.gaimit.helper.AuthorCode;
import com.gaimit.helper.Util;
import com.gaimit.helper.WebHelper;
import com.gaimit.mlm.model.BookHeld;
import com.gaimit.mlm.model.Manager;
import com.gaimit.mlm.service.BookHeldService;
import com.gaimit.mlm.service.ManagerService;

@Controller
public class SearchBook {
	/** log4j 객체 생성 및 사용할 객체 주입받기 */
	//private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);
	// --> import study.spring.helper.WebHelper;
	@Autowired
	WebHelper web;
	
	@Autowired
	Util util;
	
	@Autowired
	AuthorCode authorCode;
	
	@Autowired
	ManagerService managerService;
	
	@Autowired
	BookHeldService bookHeldService;
	
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
		
		/* 바코드 호출 */
		// 파라미터를 저장할 Beans
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
		
		try {
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
			
			/* copyCode 복본기호 호출 */
			if(isbn==null || isbn.equals("")) {
				if((bookTitle==null || bookTitle.equals("")) || (author==null || author.equals(""))) {
					return web.redirect(null, "복본기호 체크를 위하여 도서 제목과 저자를 작성하여주세요");
				}
			}
			
			//아래 selectBookId가 null일 경우 NPE발생, NPE삭제하지 않고 처리할 수가 없어서
			//그냥 selectBookCount로 조건을 걸어둠.
			int checkBookTable= bookHeldService.selectBookCount(bookHeld);
			if(checkBookTable > 0) {
				/* copyCode 복본기호 호출 */
				BookHeld callIdBook = bookHeldService.selectBookId(bookHeld);
				
				//null 체크를 위하여 Integer로 형변환
				if(((Integer) callIdBook.getIdBook() != 0) && ((Integer) callIdBook.getIdBook() != null)) {
					bookHeld.setBookIdBook(callIdBook.getIdBook());
					int copyCheckBookHeld = bookHeldService.selectBookHeldCount(bookHeld);
					if(copyCheckBookHeld > 0) {
						int lastCopyCode = bookHeldService.selectLastCopyCode(bookHeld);
						if(lastCopyCode == 0) {
							copyCode = 2;
						} else {
							lastCopyCode += 1;
							copyCode = lastCopyCode;
						}
					}
				}
				/* 복본기호 호출 끝 */
			}
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		
		//알라딘api에서 저자이름을 가지고 올 변수 선언
		String authorToCode = null;
		String titleToCode = null;
		
		/** 3) url openapi 수신 */
		String aladinTtbKey = "ttblib1207001";
		// 알라딘 api 에서 json 수신
		JSONObject jsonAladin = new JSONObject();
		try {
			String apiUrl = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey="+aladinTtbKey+"&output=js&Version=20131101&OptResult=ebookList,usedList,reviewList";
			/*String apiKey = "?ttbkey=ttbanfyanfy991303001";*/
			String apiUrlItem = apiUrl;
			String apiUrlFull = null;
			if(isbn.length() == 13) {
				apiUrlFull = apiUrlItem + "&itemIdType=ISBN13"+"&ItemId="+ isbn;
			} else if(isbn.length() == 10) {
				apiUrlFull = apiUrlItem +"&itemIdType=ISBN"+"&ItemId="+ isbn;
			}
			URL url = new URL(apiUrlFull);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.getResponseCode(); // 응답코드 리턴 200번대 404 등등
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			
			String result = br.readLine();
			
			br.close();

			JSONParser jsonParser = new JSONParser();
			jsonAladin = (JSONObject) jsonParser.parse(result);
			//json타입으로 값을 가져옴
			
			//json타입의 값인 jsonAladin에서 특정값을 가지고옴.
			JSONArray itemArray = (JSONArray) jsonAladin.get("item");
			JSONObject itemObj = (JSONObject) itemArray.get(0);
			//item의 [0] <- 첫번째 값을 가져옴
			Object authorObj = itemObj.get("author");
			Object titleObj = itemObj.get("title");
			
			//authorToCode = (String)authorObj;
			//위처럼도 casting만 바꾸는 방법도 있음.
			authorToCode = String.valueOf(authorObj);
			titleToCode = String.valueOf(titleObj);
			
		} catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		//알라딘 api 정보를 토대로 저자기호 만들기
		String atcOut =
				authorCode.authorCodeGen(authorToCode)
				+ authorCode.titleFirstLetter(titleToCode);
		
		
		String NLKcertKey = "6debf14330e5866f7c50d47a9c84ae8f";
		// 서지정보에서 api 수신
		JSONObject jsonSeoji = new JSONObject();
		try {
			String apiUrl = "http://seoji.nl.go.kr/landingPage/SearchApi.do?cert_key="+NLKcertKey+"&result_style=json&page_no=1&page_size=1";
			String apiUrlFull = null;
			if(isbn.length() == 13) {
				apiUrlFull = apiUrl + "&isbn="+ isbn;
			} else if(isbn.length() == 10) {
				apiUrlFull = apiUrl +"&isbn="+ isbn;
			}
			URL url = new URL(apiUrlFull);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.getResponseCode(); // 응답코드 리턴 200번대 404 등등
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			
			String result = br.readLine();
			
			br.close();

			JSONParser jsonParser = new JSONParser();
			jsonSeoji = (JSONObject) jsonParser.parse(result);
		} catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		//국립중앙도서관 아래 api검색
		//http://www.nl.go.kr/app/nl/search/openApi/search.jsp?key=6debf14330e5866f7c50d47a9c84ae8f&category=dan&detailSearch=true&isbnOp=isbn&isbnCode=8984993727
		// 국중은 openapi가 xml 형태밖에 없는 듯하여 xml 호출 구조
		ArrayList<String> xmlClassNoArray = new ArrayList<String>();
		try {
			String apiUrl = "http://www.nl.go.kr/app/nl/search/openApi/search.jsp?key="+NLKcertKey+"&category=dan&detailSearch=true&isbnOp=isbn";
			String apiUrlFull = null;
			if(isbn.length() == 13) {
				apiUrlFull = apiUrl + "&isbnCode="+ isbn;
			} else if(isbn.length() == 10) {
				apiUrlFull = apiUrl +"&isbnCode="+ isbn;
			}
			URL url = new URL(apiUrlFull);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.getResponseCode(); // 응답코드 리턴 200번대 404 등등
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc =builder.parse(con.getInputStream());
			
			NodeList nodeList = doc.getElementsByTagName("item");
			for(int i =0; i<nodeList.getLength(); i++) {
				for(Node node = nodeList.item(i).getFirstChild(); node!=null;
					node=node.getNextSibling()) {
					if(node.getNodeName().equals("class_no")) {
						xmlClassNoArray.add(node.getTextContent());
					}
				}
			}
				
		} catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		//ArrayList<String>은 get(index)과 set(index, 인자)로 컨트롤된다.
		/*xmlClassNoArray.set(0, "asdfa");
		System.out.println(xmlClassNoArray.get(0));*/
		
		
		
		/** 4) View 처리하기 */
		// 조회 결과를 View에게 전달한다.
		/*model.addAttribute("keyword", keyword);*/
		model.addAttribute("isbn", isbn);
		model.addAttribute("atcOut", atcOut);
		model.addAttribute("barcodeInit", barcodeInit);
		model.addAttribute("newBarcode", newBarcode);
		model.addAttribute("copyCode", copyCode);
		model.addAttribute("jsonAladin", jsonAladin);
		model.addAttribute("jsonSeoji", jsonSeoji);
		model.addAttribute("xmlClassNoArray", xmlClassNoArray);
		
		return new ModelAndView("book/reg_book");
	}
	
	
	
	/** 복본기호 체크 */
	@RequestMapping(value = "/book/book_held_check_copyCode.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView callCopyCode(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		/** 로그인 여부 검사 */
		// 로그인중인 회원 정보 가져오기
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
		}
		
		String newBarcode = web.getString("newBarcode");
		
		String isbn = web.getString("search-book-info", "");
		String bookTitle = web.getString("bookTitle", "");
		String author = web.getString("author", "");
		
		int copyCode = 0;
		
		String atcOut =
				authorCode.authorCodeGen(bookTitle)
				+ authorCode.titleFirstLetter(author);
		
		if(!bookTitle.equals("") && !author.equals("")) {
			/* 바코드 호출 */
			// 파라미터를 저장할 Beans
			BookHeld bookHeld = new BookHeld();
			bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
			bookHeld.setIsbn10Book(isbn);
			bookHeld.setIsbn13Book(isbn);
			bookHeld.setTitleBook(bookTitle);
			bookHeld.setWriterBook(author);
			
			try {
				//아래 selectBookId가 null일 경우 NPE발생, NPE삭제하지 않고 처리할 수가 없어서
				//그냥 selectBookCount로 조건을 걸어둠.
				int checkBookTable= bookHeldService.selectBookCount(bookHeld);
				if(checkBookTable > 0) {
					/* copyCode 복본기호 호출 */
					BookHeld callIdBook = bookHeldService.selectBookId(bookHeld);
					
					//null 체크를 위하여 Integer로 형변환
					if(((Integer) callIdBook.getIdBook() != 0) && ((Integer) callIdBook.getIdBook() != null)) {
						bookHeld.setBookIdBook(callIdBook.getIdBook());
						int copyCheckBookHeld = bookHeldService.selectBookHeldCount(bookHeld);
						if(copyCheckBookHeld > 0) {
							int lastCopyCode = bookHeldService.selectLastCopyCode(bookHeld);
							if(lastCopyCode == 0) {
								copyCode = 2;
							} else {
								lastCopyCode += 1;
								copyCode = lastCopyCode;
							}
						}
					}
					/* 복본기호 호출 끝 */
				}
			} catch (Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
			}
		} else {
			return web.redirect(null, "복본기호 체크를 위하여 도서 제목과 저자를 작성하여주세요");
		}
		
		model.addAttribute("copyCode", copyCode);
		model.addAttribute("bookTitle", bookTitle);
		model.addAttribute("author", author);
		model.addAttribute("atcOut", atcOut);
		model.addAttribute("newBarcode", newBarcode);
		
		return new ModelAndView("book/reg_book");
	}
	
	
	
	
	
	
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
		
		String isbn = web.getString("search-book-info", "");
		
		if(isbn != null && !isbn.equals("")) {
			String NLKcertKey = "6debf14330e5866f7c50d47a9c84ae8f";
			//국립중앙도서관 아래 api검색
			//http://www.nl.go.kr/app/nl/search/openApi/search.jsp?key=6debf14330e5866f7c50d47a9c84ae8f&category=dan&detailSearch=true&isbnOp=isbn&isbnCode=8984993727
			// 국중은 openapi가 xml 형태밖에 없는 듯하여 xml 호출 구조
			ArrayList<Object> xmlArray = new ArrayList<Object>();
			try {
				String apiUrl = "http://www.nl.go.kr/app/nl/search/openApi/search.jsp?key="+NLKcertKey+"&category=dan&detailSearch=true&isbnOp=isbn";
				String apiUrlFull = null;
				if(isbn.length() == 13) {
					apiUrlFull = apiUrl + "&isbnCode="+ isbn;
				} else if(isbn.length() == 10) {
					apiUrlFull = apiUrl +"&isbnCode="+ isbn;
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
		
		model.addAttribute("isbn", isbn);
		
		
		return new ModelAndView("book/search_nl_book");
	}
}
