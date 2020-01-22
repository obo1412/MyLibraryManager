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
import com.gaimit.helper.Util;
import com.gaimit.helper.WebHelper;
import com.gaimit.mlm.model.Manager;
import com.gaimit.mlm.model.BookHeld;
import com.gaimit.mlm.service.BookHeldService;
import com.gaimit.mlm.service.ManagerService;

@Controller
public class RegBook {
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
	
	/** 교수 목록 페이지 */
	@RequestMapping(value = "/book/reg_book.do", method = {RequestMethod.GET, RequestMethod.POST})
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
		
		BookHeld bookHeld = new BookHeld();
		
		bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
		
		BookHeld lastLocalBarcode = null;
		String lastLocalBarcode2 = null;
		String barcodeInit = "";
		//barcode 호출
		try {
			lastLocalBarcode = bookHeldService.selectLastLocalBarcode(bookHeld);
			if(lastLocalBarcode!=null) {
				lastLocalBarcode2 = lastLocalBarcode.getLocalIdBarcode();
				barcodeInit = util.strExtract(lastLocalBarcode2);
			}
			
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		model.addAttribute("barcodeInit", barcodeInit);
		

		return new ModelAndView("book/reg_book");
	}	
}
