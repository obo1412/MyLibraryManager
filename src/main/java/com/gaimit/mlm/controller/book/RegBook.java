package com.gaimit.mlm.controller.book;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gaimit.helper.FileInfo;
/*import com.gaimit.helper.FileInfo;*/
import com.gaimit.helper.UploadHelper;
import com.gaimit.helper.Util;
import com.gaimit.helper.WebHelper;
import com.gaimit.mlm.model.BookHeld;
import com.gaimit.mlm.model.Manager;
import com.gaimit.mlm.service.BookHeldService;
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
	
	// --> import study.jsp.helper.UploadHelper;
	@Autowired
	UploadHelper upload;
	
	@Autowired
	ManagerService managerService;
	
	@Autowired
	BookHeldService bookHeldService;
	
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
		
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
		
		int lastEmptyLocalBarcode = 0;
		BookHeld lastLocalBarcode = new BookHeld();
		String barcodeInit = "";
		String newBarcode = "";
		int barcodeInitCount = 0;
	
		//barcode 호출
		try {
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
		
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
		
		int lastEmptyLocalBarcode = 0;
		BookHeld lastLocalBarcode = new BookHeld();
		String barcodeInit = "";
		String newBarcode = "";
		int barcodeInitCount = 0;
	
		//barcode 호출
		try {
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

		return new ModelAndView("book/reg_book_batch");
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
		
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());

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
		
		String defaultPath = "/var/packages/Tomcat7/target/src/webapps/downloads/upload";
		
		/** (6) 업로드 된 파일 정보 추출 */
		List<FileInfo> fileList = upload.getFileList();
		// 업로드 된 프로필 사진 경로가 저장될 변수
		String batchFile = null;
		String fileName = null;

		// 업로드 된 파일이 존재할 경우만 변수값을 할당한다.
		if (fileList.size() > 0) {
			// 단일 업로드이므로 0번째 항목만 가져온다.
			FileInfo info = fileList.get(0);
			batchFile = info.getFileDir() + "/" + info.getFileName();
			fileName = info.getFileName();
		}

		// 파일경로를 로그로 기록
		logger.debug("batchFile=" + batchFile);
		logger.debug("fileName=" + fileName);

		String loadFilePath = defaultPath + "/" + fileName;

		/** (8) Service를 통한 데이터베이스 저장 처리 */
		try {
			FileInputStream fileStream = null;
			fileStream = new FileInputStream(loadFilePath);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** (9) 가입이 완료되었으므로 메인페이지로 이동 */
		return web.redirect(web.getRootPath() + "/book/reg_book_batch.do", "파일 업로드됨.");
	}
}
