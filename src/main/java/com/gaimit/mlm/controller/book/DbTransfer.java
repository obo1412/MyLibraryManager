package com.gaimit.mlm.controller.book;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.gaimit.helper.FileInfo;
import com.gaimit.helper.RegexHelper;
import com.gaimit.helper.UploadHelper;
import com.gaimit.helper.Util;
import com.gaimit.helper.WebHelper;
import com.gaimit.mlm.model.BbsFile;
import com.gaimit.mlm.model.BookHeld;
import com.gaimit.mlm.model.Manager;
import com.gaimit.mlm.service.BbsFileService;
import com.gaimit.mlm.service.BookHeldService;

@Controller
public class DbTransfer {
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	// --> import org.apache.logging.log4j.Logger;
	private static Logger logger = LoggerFactory.getLogger(DbTransfer.class);
	@Autowired
	SqlSession sqlSession;
	@Autowired
	WebHelper web;
	@Autowired
	Util util;
	@Autowired
	UploadHelper upload;
	@Autowired
	RegexHelper regex;
	@Autowired
	BookHeldService bookHeldService;
	@Autowired
	BbsFileService bbsFileService;
	
	@RequestMapping(value = "/book/db_transfer.do")
	public ModelAndView DbTransferV(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {		
		web.init();
		
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		if (web.getSession("loginInfo") == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인이 필요합니다.");
		}

		return new ModelAndView("book/db_transfer");
	}
	
	// mdb 데이터 MySQL에 저장 test.
	public void mdbConvertTest() {
		Connection conn;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			
			conn = DriverManager.getConnection("jdbc:ucanaccess://E:\\downloads/Finebook.mdb"); // mdb 파일 위치 설정.
			Statement s = conn.createStatement();		
			ResultSet rs = s.executeQuery("SELECT * FROM 도서이동테스트");
			
			// 성능 때문에 반복문 안에서 new는 하는 것은 피해야함. 
			BookHeld bookHeld = new BookHeld();
			String title = "";
			String writer = "";
			
			while (rs.next()) {				
				title = rs.getString(1);
				writer = rs.getString(2);
				
				// console에서 mdb 값 확인.
			    logger.debug("mdb 1열="+title);
			    logger.debug("mdb 2열="+writer);
			    
			    // mdb 값 data로 설정.
				bookHeld.setTitleBook(title);
				bookHeld.setWriterBook(writer);
				
				// MySQL 삽입.
				bookHeldService.insertBook(bookHeld);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/book/db_transfer_ok.do")
	public ModelAndView DbTransferOk(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {		
		web.init();
		
		mdbConvertTest();

		String url = web.getRootPath() + "/book/reg_book_batch.do";
		return web.redirect(url, "도서 데이터베이스 이관 테스트 완료");
	}
	
	@ResponseBody
	@RequestMapping(value= "/export_bookheld_excel_ok.do", method = RequestMethod.POST)
	public void exportExcel(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		web.init();
		
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (web.getSession("loginInfo") == null) {
			web.printJsonRt("로그인 후에 이용 가능합니다.");
		}
		
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
		
		//엑셀로 내보내기 위한 준비
		String bookDbName = loginInfo.getNameLib()+"_도서등록정보";
		String defaultPath = "/var/packages/Tomcat7/target/src/webapps/downloads/upload/ExportData";
		String filePath = defaultPath+"/"+bookDbName+".xlsx";
		
		FileOutputStream fos = null;
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		XSSFRow titleRow = null;
		XSSFRow row = null;
		
		//엑셀로 내보내기 위한 준비
		
		List<BookHeld> bookHeldList = null;
		
		try {
			bookHeldList = bookHeldService.getBookHeldList(bookHeld);
			
			titleRow = sheet.createRow(0);
			titleRow.createCell(0).setCellValue("번호");
			titleRow.createCell(1).setCellValue("도서명");
			titleRow.createCell(2).setCellValue("저자명");
			titleRow.createCell(3).setCellValue("출판사");
			titleRow.createCell(4).setCellValue("출판일");
			titleRow.createCell(5).setCellValue("ISBN13");
			titleRow.createCell(6).setCellValue("분류기호");
			titleRow.createCell(7).setCellValue("저자기호");
			titleRow.createCell(8).setCellValue("권차기호");
			titleRow.createCell(9).setCellValue("별치기호");
			titleRow.createCell(10).setCellValue("복본기호");
			titleRow.createCell(11).setCellValue("서가");
			titleRow.createCell(12).setCellValue("도서등록일");
			titleRow.createCell(13).setCellValue("도서등록번호");
			titleRow.createCell(14).setCellValue("수입구분");
			titleRow.createCell(15).setCellValue("도서상태");
			
			
			
			/*for(int i=1; i<=bookHeldList.size(); i++) {
				int j = i-1;
				row = sheet.createRow(i);
				noCell = row.createCell(0);
				noCell.setCellValue(i);
				guestCell = row.createCell(1);
				guestCell.setCellValue(guestList.get(j).getNameGuest());
				giftCell = row.createCell(2);
				giftCell.setCellValue(guestList.get(j).getCelebrationGift());
				rmkCell = row.createCell(3);
				rmkCell.setCellValue(guestList.get(j).getRemarks());
				hostCell = row.createCell(4);
				hostCell.setCellValue(guestList.get(j).getNameHost());
			}*/
			
			File file = new File(defaultPath);
			file.mkdirs();
			
			fos = new FileOutputStream(filePath);
			workbook.write(fos);
			workbook.close();
			fos.close();
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}
	}
	
	
	
	@RequestMapping(value= "/book/import_book_excel.do", method = RequestMethod.GET)
	public ModelAndView importBookExcel(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		/** 로그인 여부 검사 */
		// 로그인중인 회원 정보 가져오기
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
		}
		
		
		return new ModelAndView("book/import_book_excel");
	}
	
	@RequestMapping(value = "/book/import_book_excel_check.do")
	public ModelAndView importBookExcelCheck(Locale locale, Model model, HttpServletRequest request,
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
		
		/** (6) 업로드 된 파일 정보 추출 */
		List<FileInfo> fileList = upload.getFileList();
		
		try {
			for (int i = 0; i < fileList.size(); i++) {
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
				
				// DB에 파일 업로드 저장처리
				bbsFileService.insertRegBookFile(file);
				
				int extPos = info.getFileName().lastIndexOf(".");
				String ext = info.getFileName().substring(extPos+1);
				logger.debug(ext);
				
				String loadFilePath = info.getFileDir() + "/" + info.getFileName();
				FileInputStream fis = new FileInputStream(loadFilePath);
				
				if(ext.equals("xlsx")) {
					XSSFWorkbook workbook = new XSSFWorkbook(fis);
					int sheetNum = workbook.getNumberOfSheets();
					logger.info("현재 시트 번호 = "+sheetNum);
					
					XSSFSheet curSheet = workbook.getSheetAt(0);
					int row_crt = curSheet.getPhysicalNumberOfRows();
					logger.info("최종 행 번호 = "+row_crt);
					//최초 행의 마지막 셀 수 = 마지막 컬럼(수평) 번호
					int lastCellCount = curSheet.getRow(0).getPhysicalNumberOfCells();
					logger.info("엑셀 db 목록의 컬럼 개수= "+lastCellCount);
					
					String[][] theArr = new String[row_crt][lastCellCount];
					
					
					for (int j=0; j<row_crt; j++) {
						//i번 행을 읽어온다.
						XSSFRow horizonRow = curSheet.getRow(j);
						if(horizonRow != null) {
							for(int k=0; k<lastCellCount; k++) {
								theArr[j][k] = String.valueOf(horizonRow.getCell(k));
								/*logger.info("colArr["+j+"]["+k+"]의 값 = "+theArr[j][k]);*/
							}
						}
					}
					model.addAttribute("theArr", theArr);
					workbook.close();
				} else {
					HSSFWorkbook workbook = new HSSFWorkbook(fis);
					int sheetNum = workbook.getNumberOfSheets();
					logger.info("현재 시트 번호 = "+sheetNum);
					
					HSSFSheet curSheet = workbook.getSheetAt(0);
					int row_crt = curSheet.getPhysicalNumberOfRows();
					logger.info("최종 행 번호 = "+row_crt);
					//최초 행의 마지막 셀 수 = 마지막 컬럼(수평) 번호
					int lastCellCount = curSheet.getRow(0).getPhysicalNumberOfCells();
					logger.info("엑셀 db 목록의 컬럼 개수= "+lastCellCount);
					
					String[][] theArr = new String[row_crt][lastCellCount];
					
					
					for (int j=0; j<row_crt; j++) {
						//i번 행을 읽어온다.
						HSSFRow horizonRow = curSheet.getRow(j);
						if(horizonRow != null) {
							for(int k=0; k<lastCellCount; k++) {
								theArr[j][k] = String.valueOf(horizonRow.getCell(k));
								/*logger.info("colArr["+j+"]["+k+"]의 값 = "+theArr[j][k]);*/
							}
						}
					}
					model.addAttribute("theArr", theArr);
					workbook.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

		/** (9) 가입이 완료되었으므로 메인페이지로 이동 */
		return new ModelAndView("book/import_book_excel_check");
	}
	

	@RequestMapping(value= "/book/import_book_excel_ok.do", method = RequestMethod.POST)
	public ModelAndView importBookExcelOk(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		web.init();
		
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (web.getSession("loginInfo") == null) {
			web.printJsonRt("로그인 후에 이용 가능합니다.");
		}
		
		int colLast = web.getInt("colLast");
		
		//colH 머리값을 가져옴 컬럼의 주제
		Map<String, String> colH = new HashMap<String, String>();
		for(int i=0; i<=colLast; i++) {
			colH.put("colH"+i, web.getString("colH"+i));
			logger.debug("colH"+i+"의 값 = "+colH.get("colH"+i));
		}
		
		/*System.out.println("title의 키값"+getKey(colH, "title"));
		System.out.println("author의 키값"+getKey(colH, "author"));
		System.out.println("publisher의 키값"+getKey(colH, "publisher"));
		System.out.println("classNo의 키값"+getKey(colH, "classNo"));*/
		
		String titleCol = getKey(colH, "title");
		String titleIdxStr = titleCol.substring(titleCol.length()-1, titleCol.length());
		int titleIdx = Integer.parseInt(titleIdxStr);
		
		String authorCol = getKey(colH, "author");
		String authorIdxStr = authorCol.substring(authorCol.length()-1, authorCol.length());
		int authorIdx = Integer.parseInt(authorIdxStr);
		
		String publisherCol = getKey(colH, "publisher");
		String publisherIdxStr = publisherCol.substring(publisherCol.length()-1, publisherCol.length());
		int publisherIdx = Integer.parseInt(publisherIdxStr);
		
		/*String classNoCol = getKey(colH, "classNo");
		String classNoIdxStr = classNoCol.substring(classNoCol.length()-1, classNoCol.length());
		int classNoIdx = Integer.parseInt(classNoIdxStr);*/
		
		/*System.out.println("titleindex는 "+titleIdx);
		System.out.println("authorIdx는 "+authorIdx);
		System.out.println("publisherIdx는 "+publisherIdx);
		System.out.println("classNoIdx는 "+classNoIdx);*/
		
		
		//col 값을 받아옴
		Map<String, String[]> col = new HashMap<String, String[]>();
		for(int i=0; i<=colLast; i++) {
			col.put("col"+i, web.getStringArray("col"+i));
		}
		
		/*for(int i=0; i<4; i++) {
			for(int j=0; j<9; j++) {
				System.out.println("col"+i+"의 "+j+"번째 값="+col.get("col"+i)[j]);
			}
		}*/
		
		/*System.out.println("col.get길이 "+col.get("col0").length);*/
		
		try {
			for(int i=0; i<col.get("col0").length; i++) {
				BookHeld bookHeld = new BookHeld();
				//id_book을 받아오기 위한 객체
				BookHeld callIdBook = null;
				bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
				
				bookHeld.setTitleBook(col.get("col"+titleIdx)[i]);
				bookHeld.setWriterBook(col.get("col"+authorIdx)[i]);
				bookHeld.setPublisherBook(col.get("col"+publisherIdx)[i]);
				
				//등록번호 처리를 위한 코드
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
				//등록번호 처리를 위한 코드 끝
				
				//Book 테이블에 자료 존재하는지 여부 검사
				//자료있으면 book참조, 없으면 새로 등록
				int checkBookTable= bookHeldService.selectBookCount(bookHeld);
				if (checkBookTable > 0) {
					//id_book을 받아오기 위한 객체
					callIdBook = bookHeldService.selectBookId(bookHeld);
					//callIdBook에 id_book을 담고 bookHeld에 전달.
					bookHeld.setBookIdBook(callIdBook.getIdBook());
					bookHeldService.insertBookHeld(bookHeld);
				} else if (checkBookTable == 0) {
					//book 테이블에 등록
					bookHeldService.insertBook(bookHeld);
					callIdBook = bookHeldService.selectBookId(bookHeld);
					//callIdBook에 id_book을 담고 bookHeld에 전달.
					bookHeld.setBookIdBook(callIdBook.getIdBook());
					bookHeldService.insertBookHeld(bookHeld);
				}
			}
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		return web.redirect(web.getRootPath() + "/book/book_held_list.do", "도서 엑셀데이터 등록이 완료되었습니다.");
	}

	//map에서 value 값으로 key 값을 가져옴
	public static <K, V> K getKey(Map<K, V> map, V value) {
		for (K key : map.keySet()) {
			if(value.equals(map.get(key))) {
				return key;
			}
		}
		return null;
	}
}

