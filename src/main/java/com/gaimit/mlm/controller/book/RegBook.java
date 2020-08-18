package com.gaimit.mlm.controller.book;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
/*import java.io.PrintWriter;*/
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaimit.helper.ApiHelper;
import com.gaimit.helper.AuthorCode;
import com.gaimit.helper.FileInfo;
/*import com.gaimit.helper.FileInfo;*/
import com.gaimit.helper.UploadHelper;
import com.gaimit.helper.Util;
import com.gaimit.helper.WebHelper;
import com.gaimit.mlm.model.BbsFile;
import com.gaimit.mlm.model.Book;
import com.gaimit.mlm.model.BookHeld;
import com.gaimit.mlm.model.Manager;
import com.gaimit.mlm.service.BbsFileService;
import com.gaimit.mlm.service.BookHeldService;
import com.gaimit.mlm.service.BookService;
import com.gaimit.mlm.service.ManagerService;

@Controller
public class RegBook {
	/** log4j 객체 생성 및 사용할 객체 주입받기 */
	//private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);
	// --> import study.spring.helper.WebHelper;
	// --> import org.apache.logging.log4j.Logger;
	Logger logger = LoggerFactory.getLogger(RegBook.class);
	
	@Autowired
	WebHelper web;
	
	@Autowired
	Util util;
	
	@Autowired
	AuthorCode authorCode;
	
	// --> import study.jsp.helper.UploadHelper;
	@Autowired
	UploadHelper upload;
	
	@Autowired
	ApiHelper apiHelper;
	
	@Autowired
	ManagerService managerService;
	
	@Autowired
	BookHeldService bookHeldService;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	BbsFileService bbsFileService;
	
	/** 도서 등록 페이지 */
	@RequestMapping(value = "/book/reg_book.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView regBook(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		/** 로그인 여부 검사 */
		// 로그인중인 회원 정보 가져오기
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
		}
		
		String regChk = web.getString("straightReg");
		String regCheckBox = "";
		if(regChk!=null) {
			regCheckBox = "checked";
		}
		
		Book country = new Book();
		
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
		
		int lastEmptyLocalBarcode = 0;
		BookHeld lastLocalBarcode = new BookHeld();
		String barcodeInit = "";
		String newBarcode = "";
		int barcodeInitCount = 0;
		
		//국가 목록 조회
		List<Book> countryList = null;
		
		//barcode 호출
		try {
			//국가 목록 조회
			countryList = bookService.selectCountryListOnly(country);
			
			//바코드 헤드 검사
			lastLocalBarcode = bookHeldService.selectLastLocalBarcode(bookHeld);
			//바코드 헤드가 null 이 아니면 최종값이 있다는 것 그 헤드를 사용하면 된다
			if(lastLocalBarcode != null) {
				barcodeInit = lastLocalBarcode.getLocalIdBarcode();
				barcodeInit = util.strExtract(barcodeInit);
				//바코드말머리가 있다면 말머리의 길이를 구한다.
				// *말머리의 길이로 mapper에서 바코드 select함
				barcodeInitCount = barcodeInit.length();
			}
			//바코드 말머리의 길이를 bookHeld에 주입
			bookHeld.setBarcodeInitCount(barcodeInitCount);
			
			//바코드 번호가 1번인지 검사
			int firstBarcode = bookHeldService.selectFirstLocalBarcode(bookHeld);
			//1번이면, 중간에 비어 있는 바코드 숫자로 바코드 등록
			//1이 아니면 1로 바코드 등록
			if(firstBarcode == 1 ) {
				lastEmptyLocalBarcode = bookHeldService.selectEmptyLocalBarcode(bookHeld);
			} else {
				lastEmptyLocalBarcode = 1;
			}
			
			newBarcode = util.makeStrLength(8, barcodeInit, lastEmptyLocalBarcode);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		//직전에 등록된 도서를 확인하기 위한 목록
		List<BookHeld> regTodayList = null;
		try {
			regTodayList = bookHeldService.getRegTodayBookHeldList(bookHeld);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		
		model.addAttribute("countryList", countryList);
		model.addAttribute("newBarcode", newBarcode);
		model.addAttribute("barcodeInit", barcodeInit);
		model.addAttribute("regTodayList", regTodayList);
		
		model.addAttribute("regCheckBox", "checked");

		return new ModelAndView("book/reg_book");
	}
	
	
	/** 도서 등록 페이지 */
	@RequestMapping(value = "/book/reg_book_batch.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView regBookBatch(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		/** 로그인 여부 검사 */
		// 로그인중인 회원 정보 가져오기
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
		}
		
		BbsFile file = new BbsFile();
		file.setIdLibFile(loginInfo.getIdLibMng());
		
		List<BbsFile> fileList = null;
		
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
		
		int lastEmptyLocalBarcode = 0;
		BookHeld lastLocalBarcode = new BookHeld();
		String barcodeInit = "";
		String newBarcode = "";
		int barcodeInitCount = 0;
	
		//barcode 호출
		try {
			fileList = bbsFileService.selectRegBookFileListToday(file);
			
			//바코드 헤드 검사
			lastLocalBarcode = bookHeldService.selectLastLocalBarcode(bookHeld);
			//바코드 헤드가 null 이 아니면 최종값이 있다는 것 그 헤드를 사용하면 된다
			if(lastLocalBarcode != null) {
				barcodeInit = lastLocalBarcode.getLocalIdBarcode();
				barcodeInit = util.strExtract(barcodeInit);
				//바코드말머리가 있다면 말머리의 길이를 구한다.
				// *말머리의 길이로 mapper에서 바코드 select함
				barcodeInitCount = barcodeInit.length();
			}
			//바코드 말머리의 길이를 bookHeld에 주입
			bookHeld.setBarcodeInitCount(barcodeInitCount);
			
			//바코드 번호가 1번인지 검사
			int firstBarcode = bookHeldService.selectFirstLocalBarcode(bookHeld);
			//1번이면, 중간에 비어 있는 바코드 숫자로 바코드 등록
			//1이 아니면 1로 바코드 등록
			if(firstBarcode == 1 ) {
				lastEmptyLocalBarcode = bookHeldService.selectEmptyLocalBarcode(bookHeld);
			} else {
				lastEmptyLocalBarcode = 1;
			}
			
			newBarcode = util.makeStrLength(8, barcodeInit, lastEmptyLocalBarcode);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		model.addAttribute("newBarcode", newBarcode);
		model.addAttribute("barcodeInit", barcodeInit);
		model.addAttribute("fileList", fileList);

		return new ModelAndView("book/reg_book_batch");
	}
	
	@RequestMapping(value = "/book/reg_book_batch_check.do")
	public ModelAndView regBookBatchCheck(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();

		/** (3) 로그인 여부 검사 */
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		
		// 관리자 로그인 중이라면 관리자의 도서관 id를 가져온다.
		if (web.getSession("loginInfo") == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인이 필요합니다.");
		}

		/** (4) 파일이 포함된 POST 파라미터 받기 */
		// <form>태그 안에 <input type="file">요소가 포함되어 있고,
		// <form>태그에 enctype="multipart/form-data"가 정의되어 있는 경우
		// WebHelper의 getString()|getInt() 메서드는 더 이상 사용할 수 없게 된다.
		try {
			upload.multipartRequest();
		} catch (Exception e) {
			return web.redirect(null, "multipart 데이터가 아닙니다.");
		}

		// UploadHelper에서 텍스트 형식의 파라미터를 분류한 Map을 리턴받아서 값을 추출한다.
		/*Map<String, String> paramMap = upload.getParamMap();*/
		
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
		
		List<String> titleArr = new ArrayList<>();
		List<String> authorArr = new ArrayList<>();
		//파일에서 넘어오는 isbn을 담을 array 변수
		List<String> callIsbn = new ArrayList<>();
		//분류기호가 담길 array 변수 선언
		List<String> clsCodeArray = new ArrayList<>();
		//알라딘api에서 저자이름을 가지고 올 변수 선언
		List<String> atcOutArray = new ArrayList<>();
		//copyCode
		List<String> copyCodeArray = new ArrayList<>();
		/** (6) 업로드 된 파일 정보 추출 */
		List<FileInfo> fileList = upload.getFileList();
		//분류기호가 담길 array 변수 선언
		List<String> volumeCodeArray = new ArrayList<>();
		
		//jsonArray 타입 결과값 받기
		ArrayList<JSONObject> bookInfoArray = new ArrayList<JSONObject>();
		try {
			// 업로드 된 파일의 수 만큼 반복 처리 한다.
			for (int i = 0; i < fileList.size(); i++) {
				// 업로드 된 정보 하나 추출하여 데이터베이스에 저장하기 위한 형태로 가공해야 한다.
				FileInfo info = fileList.get(i);

				// DB에 저장하기 위한 항목 생성
				BbsFile file = new BbsFile();

				// 도서관 아이디 지정
				file.setIdLibFile(loginInfo.getIdLibMng());
				
				// 데이터 복사
				file.setOriginName(info.getOrginName());
				file.setFileDir(info.getFileDir());
				file.setFileName(info.getFileName());
				file.setContentType(info.getContentType());
				file.setFileSize(info.getFileSize());
				
				// 저장처리
				bbsFileService.insertRegBookFile(file);
				
				String loadFilePath = info.getFileDir() + "/" + info.getFileName();
				FileInputStream fileStream = new FileInputStream(loadFilePath);
				InputStreamReader isr = new InputStreamReader(fileStream, "UTF-8");
				BufferedReader brFile = new BufferedReader(isr);
				String line = null;
				
				while((line = brFile.readLine()) != null) {
					//저자코드 생성을 위한 제목, 저자명 변수 1순위:알라딘 2순위: 국중
					String titleToCode = null;
					String authorToCode = null;
					
					//국중 분류기호 호출 시작
					//분류기호 3종 -> 1서지 2국중 3서지부가기호를 위한 변수 선언
					String kdcStr = null;
					String clsNo = null;
					String eac3 = null;
					String clsCode = null;	// clsCodeArray에 if분기로 종합해서 전달할 최종변수
					
					//서지정보에서 볼륨코드를 담을 변수
					String volCode = null;
					
					//알라딘 api 호출 시작
					JSONObject jsonAladin = new JSONObject();
					
					//apiHelper 참고
					String apiUrlFull = apiHelper.getAladinJsonIsbnResult(line);
					
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
					// 알라딘 api 호출
					
					/*이하 서지정보호출 제목 저자를 넣기 위해 순서 바꿈
					 * */
					// 서지정보 api 호출
					JSONObject jsonSeoji = new JSONObject();
					
					// apiHelper 참고
					String apiUrlSeojiFull = apiHelper.getSeojiJsonIsbnResult(line);
					
					URL urlSeoji = new URL(apiUrlSeojiFull);
					HttpURLConnection conSeoji = (HttpURLConnection)urlSeoji.openConnection();
					conSeoji.setRequestMethod("GET");
					conSeoji.getResponseCode(); // 응답코드 리턴 200번대 404 등등
					
					int respVal = conSeoji.getResponseCode();
					int resFirstLetter = respVal / 100;
					
					logger.debug("api요청 응답값 ="+respVal);
					logger.debug("api요청 응답값 앞자리 ="+resFirstLetter);
					
					BufferedReader brSeoji = new BufferedReader(new InputStreamReader(conSeoji.getInputStream(),"UTF-8"));
					
					String resultSeoji = brSeoji.readLine();
					
					brSeoji.close();

					JSONParser jsonParserSeoji = new JSONParser();
					jsonSeoji = (JSONObject) jsonParserSeoji.parse(resultSeoji);
					//서지정보 호출 끝
					
					//서지 관련 로직
					if(jsonSeoji == null) {
						logger.debug("jsonSeoji 응답 값이 없습니다. null");
					}
					
					if(!jsonSeoji.get("TOTAL_COUNT").equals("0")&&(resFirstLetter == 2)) {
						JSONArray itemSeojiArray = (JSONArray) jsonSeoji.get("docs");
						JSONObject itemObj = (JSONObject) itemSeojiArray.get(0);
						
						Object kdc = itemObj.get("KDC");
						Object eac = itemObj.get("EA_ADD_CODE");
						kdcStr = String.valueOf(kdc);
						
						String eacStr = String.valueOf(eac);
						//5자리중 뒤에 3자리만
						eac3 = eacStr.substring(2);
						
						Object titleSeo = itemObj.get("TITLE");
						Object authorSeo = itemObj.get("AUTHOR");
						
						authorToCode = String.valueOf(authorSeo);
						titleToCode = String.valueOf(titleSeo);
						
						Object volCodeObj = itemObj.get("VOL");
						volCode = String.valueOf(volCodeObj);
					}
					volumeCodeArray.add(volCode);
					// 서지 호출 정보 끝     권차기호(볼륨코드) 추가하는거 챙기자
					logger.debug("7.jsonSeoji="+jsonSeoji);
					logger.debug("8.kdc, eac3="+kdcStr+", "+eac3);
					/*이상 서지정보호출 제목 저자를 넣기 위해 순서 바꿈
					 * */
					
					/*이하 aladin json 코드와 국중api 값 섞임
					 * 알라딘에서 제목과 저자를 불러오지 못하면 국중 값을 사용하기 위함*/
					ArrayList<String> xmlClassNoArray = new ArrayList<String>();
					ArrayList<String> titleAndAuthor = new ArrayList<String>();
					
					// apiHelper 참고
					String apiUrlFullNl = apiHelper.getNlXmlIsbnResult(line);
					
					URL urlNl = new URL(apiUrlFullNl);
					HttpURLConnection conNl = (HttpURLConnection)urlNl.openConnection();
					conNl.setRequestMethod("GET");
					conNl.getResponseCode(); // 응답코드 리턴 200번대 404 등등
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document doc =builder.parse(conNl.getInputStream());
					
					NodeList nodeList = doc.getElementsByTagName("item");
					if(nodeList.getLength()>0) {
						for(int j =0; j<nodeList.getLength(); j++) {
							for(Node node = nodeList.item(j).getFirstChild(); node!=null;
								node=node.getNextSibling()) {
								if(node.getNodeName().equals("title_info")) {
									titleAndAuthor.add(node.getTextContent());
								}
								if(node.getNodeName().equals("author_info")) {
									titleAndAuthor.add(node.getTextContent());
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
					// 국중 분류기호 호출 끝
					logger.debug("1.xmlClassNoArray="+xmlClassNoArray);
					logger.debug("2.clsNo="+clsNo);
					logger.debug("3.국중 titleToCode="+titleToCode);
					logger.debug("4.국중 authorToCode="+authorToCode);
					/*이상 aladin json 코드와 국중api 값 섞임
					 * 알라딘에서 제목과 저자를 불러오지 못하면 국중 값을 사용하기 위함*/
					
					//이하 알라딘 api 연산 위 국중의 제목과 저자명을 알라딘 없을시 대체하기위함
					//저자코드가 담길 변수, 값이 없으면 null, 값이 있으면 아래 연산 후 값 담김.
					String atcOut = null;
					//copyCode가 연산되지 않을 경우에도 변수에 담겨야 하기 때문에 여기다 선언
					int copyCode = 0;
					// 결과 값이 없을 경우는 이하 정보를 호출 할 수 없음.
					// 따라서 item변수를 기점으로 조건 분기.
					if(jsonAladin.get("item") != null && !jsonAladin.get("item").equals("")) {
						//json타입의 값인 jsonAladin에서 특정값을 가지고옴.
						JSONArray itemArray = (JSONArray) jsonAladin.get("item");
						JSONObject itemObj = (JSONObject) itemArray.get(0);
						//item의 [0] <- 첫번째 값을 가져옴
						Object authorObj = itemObj.get("author");
						Object titleObj = itemObj.get("title");
						
						//authorToCode = (String)authorObj;
						//위처럼도 casting만 바꾸는 방법도 있음.
						if(!"".equals(authorObj)&&!"".equals(titleObj)) {
							authorToCode = String.valueOf(authorObj);
							titleToCode = String.valueOf(titleObj);
						}
					}
					//위 알라딘에서 제목 불러와서 ToCode 변수에 대입
					
					//뷰페이지로 전달하기 위한 LIST에 값 주입
					titleArr.add(titleToCode);
					authorArr.add(authorToCode);
					
					if(titleToCode!=null&&authorToCode!=null) {
						
						atcOut = authorCode.authorCodeGen(authorToCode)
								+ authorCode.titleFirstLetter(titleToCode);
						
						//이하 copy code 추출
						bookHeld.setIsbn10Book(line);
						bookHeld.setIsbn13Book(line);
						bookHeld.setTitleBook(titleToCode);
						bookHeld.setWriterBook(authorToCode);
						
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
								/*복본이 존재하는지 체크*/
								if(copyCheckBookHeld > 1) {
									/* 결과값이 0이면 복본기호는 0이 아니라는 말이고, 결과값이 1이면 0이라는 말 */
									int zeroCopyCode = bookHeldService.selectZeroCopyCodeCount(bookHeld);
									if(zeroCopyCode == 1) {
										/*0번복본이 있는 상태, 최초 복본기호가 2가 아니면 2로 지정
										 * 중간에 2번 복본이 빠졌다는 것.*/
										int firstCopyCode = bookHeldService.selectFirstCopyCode(bookHeld);
										if(firstCopyCode != 2) {
											copyCode = 2;
										} else {
											/*최초 복본기호가 2가 있다면, 그 이상의 빈번호임.*/
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
							/* 복본기호 호출 끝 */
						}
					}
					
					//clsNo 요놈이 null인 경우에, clsNo.length()이 것만 실행해도
					//아래는 죽은 함수가 되어버림.
					if(kdcStr!=null&&kdcStr.length()!=0) {
						clsCode = kdcStr;
					} else if(clsNo!=null&&clsNo.length()!=0) {
						clsCode = clsNo;
					} else if(eac3!=null&&eac3.length()!=0){
						clsCode = eac3;
					}
					//3단계 절차 거친 분류기호 clsCode를 배열에 삽입
					clsCodeArray.add(clsCode);
					
					copyCodeArray.add(String.valueOf(copyCode));
					
					atcOutArray.add(atcOut);
					logger.debug("5. aladin="+jsonAladin);
					logger.debug("6. atcOut="+atcOut);
					
					//상단에 bookInfoArray를 선언하하여 변수로 사용할 것이면 아래처럼 사용하면 됨.
					bookInfoArray.add(jsonAladin);
					callIsbn.add(line);
					//파일로 읽어들인 isbn 호출
					//알라딘 정보 호출 끝
				} //while 문 끝
				brFile.close();
			}
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		model.addAttribute("callIsbn", callIsbn);
		model.addAttribute("clsCodeArray", clsCodeArray);
		model.addAttribute("atcOutArray", atcOutArray);
		model.addAttribute("copyCodeArray", copyCodeArray);
		model.addAttribute("bookInfoArray", bookInfoArray);
		model.addAttribute("titleArr", titleArr);
		model.addAttribute("authorArr", authorArr);
		model.addAttribute("volumeCodeArray", volumeCodeArray);

		/** (9) 가입이 완료되었으므로 메인페이지로 이동 */
		return new ModelAndView("book/reg_book_batch_check");
	}
	
	@RequestMapping(value = "/book/reg_book_batch_ok.do")
	public ModelAndView regBookBatchOk(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();

		/** (3) 로그인 여부 검사 */
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		
		// 관리자 로그인 중이라면 관리자의 도서관 id를 가져온다.
		if (web.getSession("loginInfo") == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인이 필요합니다.");
		}

		// UploadHelper에서 텍스트 형식의 파라미터를 분류한 Map을 리턴받아서 값을 추출한다.
		/*Map<String, String> paramMap = upload.getParamMap();*/
		/*String name = paramMap.get("name");*/
		
		String[] regExceptionArray = web.getStringArray("regException");
		//위 값이 1이면 등록제외, 0이면 등록진행
		String[] isbnArray = web.getStringArray("isbn");
		String[] titleArray = web.getStringArray("title");
		String[] authorArray = web.getStringArray("author");
		String[] classCodeArray = web.getStringArray("classCode");
		String[] authorCodeArray = web.getStringArray("authorCode");
		
		String[] addiCodeArray = web.getStringArray("additionalCode");
		String[] volCodeArray = web.getStringArray("volumeCode");
		String[] bookCategArray = web.getStringArray("bookCateg");
		String[] publisherArray = web.getStringArray("publisher");
		String[] pubDateArray = web.getStringArray("pubDate");
		String[] pageArray = web.getStringArray("page");
		String[] priceArray = web.getStringArray("price");
		String[] bookOrNotArray = web.getStringArray("bookOrNot");
		String[] purOrDonArray = web.getStringArray("purOrDon");
		String[] isbn10Array = web.getStringArray("isbn10");
		String[] bookCoverArray = web.getStringArray("bookCover");
		String[] bookDescArray = web.getStringArray("bookDesc");
		
		/*for(int i=0; i<titleArray.length; i++) {
			System.out.println(i+"번째 값 regException="+regExceptionArray[i]);
			System.out.println(i+"번째 값 isbn="+isbnArray[i]);
			System.out.println(i+"번째 값 title="+titleArray[i]);
			System.out.println(i+"번째 값 author="+authorArray[i]);
			System.out.println(i+"번째 값 classcode="+classCodeArray[i]);
			System.out.println(i+"번째 값 authorcode="+authorCodeArray[i]);
			System.out.println(i+"번째 값 addcode="+addiCodeArray[i]);
			System.out.println(i+"번째 값 volcode="+volCodeArray[i]);
			System.out.println(i+"번째 값 bookCateg="+bookCategArray[i]);
			System.out.println(i+"번째 값 publisher="+publisherArray[i]);
			System.out.println(i+"번째 값 pubDate="+pubDateArray[i]);
			System.out.println(i+"번째 값 page="+pageArray[i]);
			System.out.println(i+"번째 값 price="+priceArray[i]);
			System.out.println(i+"번째 값 bookornot="+bookOrNotArray[i]);
			System.out.println(i+"번째 값 purordona="+purOrDonArray[i]);
			System.out.println(i+"번째 값 isbn10="+isbn10Array[i]);
			System.out.println(i+"번째 값 bookcover="+bookCoverArray[i]);
			System.out.println(i+"번째 값 bookdesc="+bookDescArray[i]);
			System.out.println("**********************************");
			System.out.println("");
		}*/
		
		try {
			for(int i=0; i<titleArray.length; i++) {
				if(regExceptionArray[i].equals("1")) {
					continue;
				}
				BookHeld bookHeld = new BookHeld();
				bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
				
				bookHeld.setIsbn13Book(isbnArray[i]);
				bookHeld.setTitleBook(titleArray[i]);
				bookHeld.setWriterBook(authorArray[i]);
				bookHeld.setAuthorCode(authorCodeArray[i]);
				bookHeld.setClassificationCode(classCodeArray[i]);
				
				bookHeld.setIsbn10Book(isbn10Array[i]);
				bookHeld.setPublisherBook(publisherArray[i]);
				String thisPubDate = null;
				if(pubDateArray[i].length()>0) {
					thisPubDate = pubDateArray[i];
				}
				bookHeld.setPubDateBook(thisPubDate);
				bookHeld.setCategoryBook(bookCategArray[i]);
				
				int thisPage = 0;
				if(pageArray[i]!=null&&(pageArray[i].length()>0)) {
					thisPage = Integer.parseInt(pageArray[i]);
				}
				bookHeld.setPage(thisPage);
				int thisPrice = 0;
				if(priceArray[i]!=null&&(priceArray[i].length()>0)) {
				thisPrice = Integer.parseInt(priceArray[i]);
				}
				bookHeld.setPriceBook(thisPrice);
				bookHeld.setBookOrNot(bookOrNotArray[i]);
				int thisPurOrDon = Integer.parseInt(purOrDonArray[i]);
				bookHeld.setPurchasedOrDonated(thisPurOrDon);
				bookHeld.setAdditionalCode(addiCodeArray[i]);
				bookHeld.setVolumeCode(volCodeArray[i]);
				bookHeld.setImageLink(bookCoverArray[i]);
				bookHeld.setDescriptionBook(bookDescArray[i]);
				bookHeld.setAvailable(1);
				
				BookHeld lastLocalBarcode = new BookHeld(); //바코드 헤드를 위한 마지막 바코드 참조
				String barcodeInit = "";
				int lastEmptyLocalBarcode = 0;	//빈자리 바코드 번호가 들어갈 변수
				String newBarcode = "";			//생성될 전체 바코드번호.
				//바코드 헤드 검사
				lastLocalBarcode = bookHeldService.selectLastLocalBarcode(bookHeld);
				//바코드 헤드가 null 이 아니면 최종값이 있다는 것 그 헤드를 사용하면 된다
				if(lastLocalBarcode != null) {
					barcodeInit = lastLocalBarcode.getLocalIdBarcode();
					barcodeInit = util.strExtract(barcodeInit);
				}
				//바코드 번호가 1번인지 검사
				int firstBarcode = bookHeldService.selectFirstLocalBarcode(bookHeld);
				//1번이면, 중간에 비어 있는 바코드 숫자로 바코드 등록
				//1이 아니면 1로 바코드 등록
				if(firstBarcode == 1 ) {
					lastEmptyLocalBarcode = bookHeldService.selectEmptyLocalBarcode(bookHeld);
				} else {
					lastEmptyLocalBarcode = 1;
				}
				
				newBarcode = util.makeStrLength(8, barcodeInit, lastEmptyLocalBarcode);
				//소문자 바코드를 대문자로 변환, 바코드 생성 및 bookHeld에 주입
				newBarcode = newBarcode.toUpperCase();
				bookHeld.setLocalIdBarcode(newBarcode);
				
				//위 비어있는 바코드 번호를 솔팅index에 주입
				bookHeld.setSortingIndex(lastEmptyLocalBarcode);
				
				BookHeld callIdBook = null;
				int checkBookTable= bookHeldService.selectBookCount(bookHeld);
				if (checkBookTable > 0) {
					//id_book을 받아오기 위한 객체
					callIdBook = bookHeldService.selectBookId(bookHeld);
					//callIdBook에 id_book을 담고 bookHeld에 전달.
					bookHeld.setBookIdBook(callIdBook.getIdBook());
					
					//if 복본이 있는지 체크후 insertBookHeld;
					int copyCheckBookHeld = bookHeldService.selectBookHeldCount(bookHeld);
					if(copyCheckBookHeld == 0) {
						//bookheld 테이블에 없으면 바로 등록
						bookHeldService.insertBookHeld(bookHeld);
					} else if(copyCheckBookHeld > 1){
						int zeroCopyCode = bookHeldService.selectZeroCopyCodeCount(bookHeld);
						if(zeroCopyCode == 1) {
							//bookheld 테이블에 여러권 존재시 2번 시작기준 빠진 번호 검색
							int firstCopyCode = bookHeldService.selectFirstCopyCode(bookHeld);
							if(firstCopyCode != 2) {
								bookHeld.setCopyCode(2);
								bookHeldService.insertBookHeld(bookHeld);
							} else {
								int lastEmptyCopyCode = bookHeldService.selectLastEmptyCopyCode(bookHeld);
								bookHeld.setCopyCode(lastEmptyCopyCode);
								bookHeldService.insertBookHeld(bookHeld);
							}
						} else {
							bookHeld.setCopyCode(0);
							bookHeldService.insertBookHeld(bookHeld);
						}
						
					} else if(copyCheckBookHeld == 1) {
						//위 경우 도서관에 책이 반드시 있는 경우다.
						 //아래의 최초복본기호 체크가 copy_code != 0이 아닌 조건으로
						 //검색했기 때문에, null이 나올 수 있다.
						 //따라서 firstcopycode가 null이면 도서의 복본기호는 0이라는 뜻.
						 //이 경우엔 복본기호를 2로 지정하고
						 //0이 아닌 모든 경우엔 새로등록할 도서의 복본기호를 0으로 설정
						int zeroCopyCode = bookHeldService.selectZeroCopyCodeCount(bookHeld);
						if(zeroCopyCode == 1) {
							bookHeld.setCopyCode(2);
						} else {
							bookHeld.setCopyCode(0);
						}
						bookHeldService.insertBookHeld(bookHeld);
					}
				} else if (checkBookTable == 0) {
					//book에 아예 없을 때
					bookHeldService.insertBook(bookHeld);
					//id_book을 받아오기 위한 객체
					callIdBook = bookHeldService.selectBookId(bookHeld);
					//callIdBook에 id_book을 담고 bookHeld에 전달.
					bookHeld.setBookIdBook(callIdBook.getIdBook());
					
					bookHeldService.insertBookHeld(bookHeld);
				}
			}
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		

		/** (9) 가입이 완료되었으므로 메인페이지로 이동 */
		/*return new ModelAndView("book/reg_book_batch");*/
		return web.redirect(web.getRootPath() + "/book/reg_book_batch.do", "도서 일괄 등록이 완료되었습니다.");
	}
	
	/** 도서 등록 페이지 */
	@ResponseBody
	@RequestMapping(value = "/book/author_code.do", method = RequestMethod.POST)
	public void makeAuthorCode(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		
		web.init();
		/** 로그인 여부 검사 */
		// 로그인중인 회원 정보 가져오기
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			web.printJsonRt("로그인 후 이용 가능합니다.");
			/*return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");*/
		}
		
		String result = null;
		int copyCode = 0;
		String isbn = web.getString("thisIsbn", "");
		String title = web.getString("thisTitle");
		String author = web.getString("thisAuthor");
		
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
		bookHeld.setIsbn10Book(isbn);
		bookHeld.setIsbn13Book(isbn);
		bookHeld.setTitleBook(title);
		bookHeld.setWriterBook(author);
		
		try {
			if((title!=null&&title.length()!=0)&&(author!=null&&author.length()!=0)) {
				//공백 제거
				title = title.trim();
				author = author.trim();
				
				logger.debug(title);
				logger.debug(author);
				
				result = authorCode.authorCodeGen(author)
						+ authorCode.titleFirstLetter(title);
				logger.debug(result);
				
				//이하 copyCode 관련
				int checkBookTable= bookHeldService.selectBookCount(bookHeld);
				if(checkBookTable > 0) {
					/* copyCode 복본기호 호출 */
					BookHeld callIdBook = bookHeldService.selectBookId(bookHeld);
					
					//null 체크를 위하여 Integer로 형변환
					if(((Integer) callIdBook.getIdBook() != 0) && ((Integer) callIdBook.getIdBook() != null)) {
						bookHeld.setBookIdBook(callIdBook.getIdBook());
						int copyCheckBookHeld = bookHeldService.selectBookHeldCount(bookHeld);
						/*복본이 존재하는지 체크*/
						if(copyCheckBookHeld > 1) {
							/* 결과값이 0이면 복본기호는 0이 아니라는 말이고, 결과값이 1이면 0이라는 말 */
							int zeroCopyCode = bookHeldService.selectZeroCopyCodeCount(bookHeld);
							if(zeroCopyCode == 1) {
								/*0번복본이 있는 상태, 최초 복본기호가 2가 아니면 2로 지정
								 * 중간에 2번 복본이 빠졌다는 것.*/
								int firstCopyCode = bookHeldService.selectFirstCopyCode(bookHeld);
								if(firstCopyCode != 2) {
									copyCode = 2;
								} else {
									/*최초 복본기호가 2가 있다면, 그 이상의 빈번호임.*/
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
					/* 복본기호 호출 끝 */
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// --> import java.util.HashMap;
		// --> import java.util.Map;
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("result", result);
		data.put("copyCode", copyCode);
		
		// --> import com.fasterxml.jackson.databind.ObjectMapper;
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());	
		}
	}
	
}
