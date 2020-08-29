package com.gaimit.mlm.controller.book;


import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaimit.helper.PageHelper;
import com.gaimit.helper.Util;
import com.gaimit.helper.WebHelper;
import com.gaimit.mlm.model.BookHeld;
import com.gaimit.mlm.model.Manager;
import com.gaimit.mlm.model.Member;
import com.gaimit.mlm.service.BookHeldService;
import com.gaimit.mlm.service.ManagerService;

@Controller
public class BookHeldList {
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
	ManagerService managerService;
	
	@Autowired
	BookHeldService bookHeldService;
	
	/** 도서 목록 페이지 */
	@RequestMapping(value = "/book/book_held_list.do", method = RequestMethod.GET)
	public ModelAndView doRun(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		int idLib = 0;
		
		/** 로그인 여부 검사 */
		// 로그인중인 회원 정보 가져오기
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
		} else {
			idLib = loginInfo.getIdLibMng();
		}
		
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(idLib);
		
		// 검색어 파라미터 받기 + Beans 설정
		int searchOpt = web.getInt("searchOpt");
		String keyword = web.getString("keyword", "");
		if(keyword!=null||keyword!=""){
			switch (searchOpt) {
			case 1:
				bookHeld.setTitle(keyword);
				break;
			case 2:
				bookHeld.setWriter(keyword);
				break;
			case 3:
				bookHeld.setPublisher(keyword);
				break;
			}
		}
		
		// 현재 페이지 번호에 대한 파라미터 받기
		int nowPage = web.getInt("page", 1);
		
		/** 2) 페이지 번호 구현하기 */
		// 전체 데이터 수 조회하기
		int totalCount = 0;
		try {
			totalCount = bookHeldService.selectBookCountForPage(bookHeld);
		}  catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		// 페이지 번호에 대한 연산 수행 후 조회조건값 지정을 위한 Beans에 추가하기
		page.pageProcess(nowPage, totalCount, 10, 5);
		bookHeld.setLimitStart(page.getLimitStart());
		bookHeld.setListCount(page.getListCount());
		//bookHeld.setCurrentListIndex(page.getIndexStart());
		
		/** 3) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		List<BookHeld> bookHeldList = null;
		try {
			bookHeldList = bookHeldService.getBookHeldList(bookHeld);
			if(bookHeldList != null) {
				for(int i=0; i<bookHeldList.size(); i++) {
					String classCode = bookHeldList.get(i).getClassificationCode();
					classCode = util.getFloatClsCode(classCode);
					if(classCode != null&&!"".equals(classCode)) {
						float classCodeFloat = Float.parseFloat(classCode);
						int classCodeInt = (int) (classCodeFloat);
						String classCodeColor = util.getColorKDC(classCodeInt);
						bookHeldList.get(i).setClassCodeColor(classCodeColor);
					}
				}
			}
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		
		/** 4) View 처리하기 */
		// 조회 결과를 View에게 전달한다.
		model.addAttribute("bookHeldList", bookHeldList);
		model.addAttribute("searchOpt", searchOpt);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("pageDefUrl", "/book/book_held_list.do");
		
		return new ModelAndView("book/book_held_list");
	}
	
	/** 도서 목록 페이지 */
	@RequestMapping(value = "/book/book_held_list_member.do", method = RequestMethod.GET)
	public ModelAndView bookHeldListMember(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		int idLib = 0;
		
		/** 로그인 여부 검사 */
		// 로그인중인 회원 정보 가져오기
		Member loginInfo = (Member) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
		} else {
			idLib = loginInfo.getIdLib();
		}
		
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(idLib);
		
		// 검색어 파라미터 받기 + Beans 설정
		int searchOpt = web.getInt("searchOpt");
		String keyword = web.getString("keyword", "");
		if(keyword!=null||keyword!=""){
			switch (searchOpt) {
			case 1:
				bookHeld.setTitle(keyword);
				break;
			case 2:
				bookHeld.setWriter(keyword);
				break;
			case 3:
				bookHeld.setPublisher(keyword);
				break;
			}
		}
		
		// 현재 페이지 번호에 대한 파라미터 받기
		int nowPage = web.getInt("page", 1);
		
		/** 2) 페이지 번호 구현하기 */
		// 전체 데이터 수 조회하기
		int totalCount = 0;
		try {
			totalCount = bookHeldService.selectBookCountForPage(bookHeld);
		}  catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		// 페이지 번호에 대한 연산 수행 후 조회조건값 지정을 위한 Beans에 추가하기
		page.pageProcess(nowPage, totalCount, 10, 5);
		bookHeld.setLimitStart(page.getLimitStart());
		bookHeld.setListCount(page.getListCount());
		//bookHeld.setCurrentListIndex(page.getIndexStart());
		
		/** 3) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		List<BookHeld> bookHeldList = null;
		try {
			bookHeldList = bookHeldService.getBookHeldList(bookHeld);
			if(bookHeldList != null) {
				for(int i=0; i<bookHeldList.size(); i++) {
					String classCode = bookHeldList.get(i).getClassificationCode();
					classCode = util.getFloatClsCode(classCode);
					if(classCode != null&&!"".equals(classCode)) {
						float classCodeFloat = Float.parseFloat(classCode);
						int classCodeInt = (int) (classCodeFloat);
						String classCodeColor = util.getColorKDC(classCodeInt);
						bookHeldList.get(i).setClassCodeColor(classCodeColor);
					}
				}
			}
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		
		/** 4) View 처리하기 */
		// 조회 결과를 View에게 전달한다.
		model.addAttribute("bookHeldList", bookHeldList);
		model.addAttribute("searchOpt", searchOpt);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("pageDefUrl", "/book/book_held_list_member.do");
		
		return new ModelAndView("book/book_held_list_member");
	}
	
	/** 도서 등록 페이지 */
	@ResponseBody
	@RequestMapping(value = "/book/book_held_list_to_excel.do", method = RequestMethod.POST)
	public void excelExtractBookHeldList(Locale locale, Model model,
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
		
		String targetYear = web.getString("targetYear");
		
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
		bookHeld.setRegDate(targetYear);
		
		List<BookHeld> bookHeldList = null;
		
		Date now = new Date();
		SimpleDateFormat nowForm = new SimpleDateFormat("yyyyMMddHHmmss");
		String nowStr = nowForm.format(now);
		String defaultPath = "/var/packages/Tomcat7/target/src/webapps/downloads/upload/finebook4/excel/libNo"+loginInfo.getIdLibMng();
		String filePath = defaultPath+"/"+"도서목록_"+nowStr+".xlsx";
		
		FileOutputStream fos = null;
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("도서목록");
		XSSFRow titleRow = null;
		XSSFRow row = null;
		
		//엑셀로 내보내기 위한 준비
		
		try {
			bookHeldList = bookHeldService.selectBookHeldListToExcel(bookHeld);
			
			titleRow = sheet.createRow(0);
			titleRow.createCell(0).setCellValue("번호");
			titleRow.createCell(1).setCellValue("도서명");
			titleRow.createCell(2).setCellValue("저자명");
			titleRow.createCell(3).setCellValue("출판사");
			titleRow.createCell(4).setCellValue("출판일");
			titleRow.createCell(5).setCellValue("가격");
			titleRow.createCell(6).setCellValue("ISBN10");
			titleRow.createCell(7).setCellValue("ISBN13");
			titleRow.createCell(8).setCellValue("카테고리");
			titleRow.createCell(9).setCellValue("서가");
			titleRow.createCell(10).setCellValue("등록일");
			titleRow.createCell(11).setCellValue("도서등록번호");
			titleRow.createCell(12).setCellValue("구매/기증");
			titleRow.createCell(13).setCellValue("부가기호");
			titleRow.createCell(14).setCellValue("십진분류");
			titleRow.createCell(15).setCellValue("저자기호");
			titleRow.createCell(16).setCellValue("권차기호");
			titleRow.createCell(17).setCellValue("복본기호");
			titleRow.createCell(18).setCellValue("태그");
			titleRow.createCell(19).setCellValue("RF ID");
			titleRow.createCell(20).setCellValue("상품구분");
			titleRow.createCell(21).setCellValue("페이지");
			titleRow.createCell(22).setCellValue("도서크기");
			titleRow.createCell(23).setCellValue("이미지링크");
			titleRow.createCell(24).setCellValue("국가");
			
			for(int i=1; i<=bookHeldList.size(); i++) {
				int j = i-1;
				row = sheet.createRow(i);
				row.createCell(0).setCellValue(i);
				row.createCell(1).setCellValue(bookHeldList.get(j).getTitle());
				row.createCell(2).setCellValue(bookHeldList.get(j).getWriter());
				row.createCell(3).setCellValue(bookHeldList.get(j).getPublisher());
				row.createCell(4).setCellValue(bookHeldList.get(j).getPubDate());
				row.createCell(5).setCellValue(bookHeldList.get(j).getPrice());
				row.createCell(6).setCellValue(bookHeldList.get(j).getIsbn10());
				row.createCell(7).setCellValue(bookHeldList.get(j).getIsbn13());
				row.createCell(8).setCellValue(bookHeldList.get(j).getCategory());
				row.createCell(9).setCellValue(bookHeldList.get(j).getBookShelf());
				row.createCell(10).setCellValue(bookHeldList.get(j).getRegDate());
				row.createCell(11).setCellValue(bookHeldList.get(j).getLocalIdBarcode());
				row.createCell(12).setCellValue(bookHeldList.get(j).getPurchasedOrDonated());
				row.createCell(13).setCellValue(bookHeldList.get(j).getAdditionalCode());
				row.createCell(14).setCellValue(bookHeldList.get(j).getClassificationCode());
				row.createCell(15).setCellValue(bookHeldList.get(j).getAuthorCode());
				row.createCell(16).setCellValue(bookHeldList.get(j).getVolumeCode());
				row.createCell(17).setCellValue(bookHeldList.get(j).getCopyCode());
				row.createCell(18).setCellValue(bookHeldList.get(j).getTag());
				row.createCell(19).setCellValue(bookHeldList.get(j).getRfId());
				row.createCell(20).setCellValue(bookHeldList.get(j).getBookOrNot());
				row.createCell(21).setCellValue(bookHeldList.get(j).getPage());
				row.createCell(22).setCellValue(bookHeldList.get(j).getBookSize());
				row.createCell(23).setCellValue(bookHeldList.get(j).getImageLink());
				row.createCell(24).setCellValue(bookHeldList.get(j).getNameCountry());
			}
			
			File uploadDirFile = new File(defaultPath);
			if(!uploadDirFile.exists()) {
				uploadDirFile.mkdirs();
			}
			
			fos = new FileOutputStream(filePath);
			workbook.write(fos);
			workbook.close();
			fos.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// --> import java.util.HashMap;
		// --> import java.util.Map;
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("filePath", filePath);
		
		// --> import com.fasterxml.jackson.databind.ObjectMapper;
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());	
		}
	}
}
