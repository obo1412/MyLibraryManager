package com.gaimit.mlm.controller.book;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.gaimit.helper.WebHelper;

import com.gaimit.mlm.model.Manager;
import com.gaimit.mlm.model.Member;
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
	
	/** 도서 상세 정보 */
	@RequestMapping(value = "/book/book_held_view_member.do", method = RequestMethod.GET)
	public ModelAndView viewBookMember(Locale locale, Model model) {
		
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
		
		return new ModelAndView("book/book_held_view_member");
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
		String title = web.getString("title");
		String author = web.getString("author");
		String publisher = web.getString("publisher");
		String pubDate = web.getString("pubDate");
		int price = web.getInt("price");
		String isbn13 = web.getString("isbn13");
		String isbn10 = web.getString("isbn10");
		String category = web.getString("category");
		String bookShelf = web.getString("bookShelf");
		/*String barcodeBook = web.getString("localIdBarcode");*/
		int purchasedOrDonated = web.getInt("purchasedOrDonated");
		String additionalCode = web.getString("additionalCode");
		String classificationCode = web.getString("classificationCode");
		String authorCode = web.getString("authorCode");
		String volumeCode = web.getString("volumeCode");
		String tag = web.getString("tag");
		String rfId = web.getString("rfId");
		int page = web.getInt("page");
		String bookOrNot = web.getString("bookOrNot");
		String imageLink = web.getString("imageLink");
		/*int copyCode = web.getInt("copyCode");*/
		
		
		// 파라미터를 저장할 Beans
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(idLib);
		bookHeld.setId(idBookHeld);
		bookHeld.setTitle(title);
		bookHeld.setWriter(author);
		bookHeld.setPublisher(publisher);
		bookHeld.setPubDate(pubDate);
		bookHeld.setPrice(price);
		bookHeld.setIsbn10(isbn10);
		bookHeld.setIsbn13(isbn13);
		bookHeld.setCategory(category);
		bookHeld.setBookShelf(bookShelf);
		/*bookHeld.setLocalIdBarcode(barcodeBook);*/
		bookHeld.setPurchasedOrDonated(purchasedOrDonated);
		bookHeld.setAdditionalCode(additionalCode);
		bookHeld.setClassificationCode(classificationCode);
		bookHeld.setAuthorCode(authorCode);
		bookHeld.setVolumeCode(volumeCode);
		bookHeld.setTag(tag);
		bookHeld.setRfId(rfId);
		bookHeld.setPage(page);
		bookHeld.setBookOrNot(bookOrNot);
		bookHeld.setImageLink(imageLink);
		/*bookHeld.setCopyCode(copyCode);*/
		
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
	
	@ResponseBody
	@RequestMapping(value= "book/edit_book_held_tag_ok.do", method = RequestMethod.POST)
	public void editBookHeldTag(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		
		web.init();
		
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			web.printJsonRt("로그인 후에 이용 가능합니다.");
		}
		
		int id = web.getInt("book_held_id");
		String tag = web.getString("tag");
		
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
		bookHeld.setId(id);
		bookHeld.setTag(tag);
		
		
		try {
			bookHeldService.updateBookHeldTag(bookHeld);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}

		// --> import java.util.HashMap;
		// --> import java.util.Map;
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "TAG EDIT OK");
		
		// --> import com.fasterxml.jackson.databind.ObjectMapper;
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}
	}
}
