package com.gaimit.mlm.controller.book;

import java.util.List;
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

@Controller
public class PrintTag {
	/** log4j 객체 생성 및 사용할 객체 주입받기 */
	//private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);
	// --> import study.spring.helper.WebHelper;
	@Autowired
	WebHelper web;
	
	@Autowired
	PageHelper page;
	
	@Autowired
	ManagerService managerService;
	
	@Autowired
	BookHeldService bookHeldService;
	
	/** 교수 목록 페이지 */
	@RequestMapping(value = "/book/print_tag_setup.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView printTagSetup(Locale locale, Model model) {
		
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
		
		List<BookHeld> bookHeldList = null;
		try {
			bookHeldList = bookHeldService.getPrintBookHeldList(bookHeld);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** 4) View 처리하기 */
		// 조회 결과를 View에게 전달한다.
		model.addAttribute("bookHeldList", bookHeldList);
		
		return new ModelAndView("book/print_tag_setup");
	}
	
	/** 교수 목록 페이지 */
	@RequestMapping(value = "/book/print_tag_page.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView printTag(Locale locale, Model model) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		/** 로그인 여부 검사 */
		// 로그인중인 회원 정보 가져오기
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
		}
		
		int tagType = web.getInt("tagType", 0);
		
		String dateSorting = web.getString("dateSorting", "");
		String targetSorting = web.getString("targetSorting", "");
		String titleSorting = web.getString("titleSorting", "");
		
		int rangeStart = web.getInt("rangeStart", 0);
		int rangeEnd = web.getInt("rangeEnd", 0);
		//range는 최종에서 +1 해줘야된다. 안그러면 숫자가 생겨버려서 실행조건이됨.
		int rangeLength = rangeEnd - rangeStart;
		
		
		/** 3) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
		
		if(!dateSorting.equals("")&&dateSorting!=null) {
			bookHeld.setRegDate(dateSorting);
		}
		
		if(!titleSorting.equals("")&&titleSorting!=null) {
			bookHeld.setTitleBook(titleSorting);
		}
		
		if(!targetSorting.equals("")&&targetSorting!=null) {
			targetSorting = targetSorting.toUpperCase();
			bookHeld.setLocalIdBarcode(targetSorting);
		}
		
		if(rangeEnd!=0 && rangeLength != 0) {
			bookHeld.setLimitStart(rangeStart);
			bookHeld.setListCount(rangeLength+1);
		}
		
		List<BookHeld> bookHeldList = null;
		try {
			bookHeldList = bookHeldService.getPrintBookHeldList(bookHeld);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		for(int i=0; i<bookHeldList.size(); i++) {
			String classCode = bookHeldList.get(i).getClassificationCode();
			if(classCode == null) {
				classCode = "-1";
			}
			int classCodeInt = (int)Float.parseFloat(classCode);
			if(classCodeInt < 0) {
				bookHeldList.get(i).setClassCodeHead(classCodeInt);
			} else {
				int classHead1 = classCodeInt/100;
				int classCodeHead = classHead1 * 100;
				bookHeldList.get(i).setClassCodeHead(classCodeHead);
			}
		}
		
		
		/** 4) View 처리하기 */
		// 조회 결과를 View에게 전달한다.
		model.addAttribute("bookHeldList", bookHeldList);
		
		String movePage = "default";
		if(tagType == 1) {
			movePage = "opt1";
		} else if(tagType == 2) {
			movePage = "opt2";
		}
		return new ModelAndView("book/print_tag_"+ movePage);
	}
}
