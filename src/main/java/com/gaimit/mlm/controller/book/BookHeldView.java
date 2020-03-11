package com.gaimit.mlm.controller.book;

import java.util.Locale;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gaimit.helper.PageHelper;
import com.gaimit.helper.WebHelper;

import com.gaimit.mlm.model.Manager;
import com.gaimit.mlm.model.BookHeld;
import com.gaimit.mlm.service.BookHeldService;
import com.gaimit.mlm.service.ManagerService;
import com.gaimit.mlm.service.MemberService;

@Controller
public class BookHeldView {
	/** log4j 객체 생성 및 사용할 객체 주입받기 */
	//private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);
	// --> import study.spring.helper.WebHelper;
	@Autowired
	WebHelper web;
	
	@Autowired
	PageHelper page;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	ManagerService managerService;
	
	@Autowired
	BookHeldService bookHeldService;
	
	/** 도서 상세 정보 */
	@RequestMapping(value = "/book/book_held_view.do", method = RequestMethod.GET)
	public ModelAndView viewBook(Locale locale, Model model) {
		
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
		
		String barcodeBook = web.getString("localIdBarcode");
		int bookHeldId = web.getInt("bookHeldId");
		
		// 파라미터를 저장할 Beans
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(idLib);
		bookHeld.setLocalIdBarcode(barcodeBook);
		bookHeld.setId(bookHeldId);
		
		/** 3) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		BookHeld bookHeldItem = new BookHeld();
		try {
			bookHeldItem = bookHeldService.getBookHelditem(bookHeld);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		
		/** 4) View 처리하기 */
		// 조회 결과를 View에게 전달한다.
		model.addAttribute("bookHeldItem", bookHeldItem);
		
		return new ModelAndView("book/book_held_view");
	}
	
	/** 도서 정보 수정 */
	@RequestMapping(value = "/book/book_held_edit.do", method = RequestMethod.GET)
	public ModelAndView editBook(Locale locale, Model model) {
		
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
		
		String barcodeBook = web.getString("localIdBarcode");
		
		// 파라미터를 저장할 Beans
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(idLib);
		bookHeld.setLocalIdBarcode(barcodeBook);
		
		/** 3) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		BookHeld bookHeldItem = new BookHeld();
		try {
			bookHeldItem = bookHeldService.getBookHelditem(bookHeld);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		
		/** 4) View 처리하기 */
		// 조회 결과를 View에게 전달한다.
		model.addAttribute("bookHeldItem", bookHeldItem);
		
		return new ModelAndView("book/book_held_edit");
	}
	
	@RequestMapping(value = "/book/book_held_edit_ok.do", method = RequestMethod.POST)
	public ModelAndView editBookOk(Locale locale, Model model) {
		
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
		
		int idBookHeld = web.getInt("id");
		String bookShelf = web.getString("bookShelf");
		String barcodeBook = web.getString("localIdBarcode");
		int purchasedOrDonated = web.getInt("purchasedOrDonated");
		String additionalCode = web.getString("additionalCode");
		int copyCode = web.getInt("copyCode");
		
		
		// 파라미터를 저장할 Beans
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(idLib);
		bookHeld.setId(idBookHeld);
		bookHeld.setBookShelf(bookShelf);
		bookHeld.setLocalIdBarcode(barcodeBook);
		bookHeld.setPurchasedOrDonated(purchasedOrDonated);
		bookHeld.setAdditionalCode(additionalCode);
		bookHeld.setCopyCode(copyCode);
		
		/** 3) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		BookHeld bookHeldItem = new BookHeld();
		try {
			bookHeldService.updateBookHeldItem(bookHeld);
			bookHeldItem = bookHeldService.getBookHelditem(bookHeld);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		
		/** 4) View 처리하기 */
		// 조회 결과를 View에게 전달한다.
		model.addAttribute("bookHeldItem", bookHeldItem);
		
		return new ModelAndView("book/book_held_view");
	}
}
