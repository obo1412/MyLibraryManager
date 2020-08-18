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
import com.gaimit.helper.QRCodeHelper;
import com.gaimit.helper.RegexHelper;
import com.gaimit.helper.Util;
import com.gaimit.helper.WebHelper;

import com.gaimit.mlm.model.Manager;
import com.gaimit.mlm.model.TagSetting;
import com.gaimit.mlm.model.BookHeld;
import com.gaimit.mlm.service.BookHeldService;
import com.gaimit.mlm.service.ManagerService;
import com.gaimit.mlm.service.TagSettingService;

@Controller
public class PrintTag {
	/** log4j 객체 생성 및 사용할 객체 주입받기 */
	//private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);
	// --> import study.spring.helper.WebHelper;
	@Autowired
	WebHelper web;
	// --> import study.jsp.helper.RegexHelper;
	@Autowired
	RegexHelper regex;
	
	@Autowired
	PageHelper page;
	
	@Autowired
	Util util;
	
	@Autowired
	ManagerService managerService;
	
	@Autowired
	BookHeldService bookHeldService;
	
	@Autowired
	TagSettingService tagSettingService;
	
	@Autowired
	QRCodeHelper qrCode;
	
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
		
		String dateSorting = web.getString("dateSorting");
		String targetSorting = web.getString("targetSorting");
		String titleSorting = web.getString("titleSorting");
		
		if(targetSorting!=null) {
			targetSorting = targetSorting.toUpperCase();
		}
		
		int rangeStart = web.getInt("rangeStart", 0);
		int rangeEnd = web.getInt("rangeEnd", 0);
		//range는 최종에서 +1 해줘야된다. 안그러면 숫자가 생겨버려서 실행조건이됨.
		int rangeLength = rangeEnd - rangeStart;
		
		if(dateSorting!= null || targetSorting!=null || titleSorting!=null || rangeLength!=0) {
			/** 3) Service를 통한 SQL 수행 */
			// 조회 결과를 저장하기 위한 객체
			BookHeld bookHeld = new BookHeld();
			bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
			
			if(dateSorting!=null) {
				bookHeld.setRegDate(dateSorting);
			}
			
			if(titleSorting!=null) {
				bookHeld.setTitleBook(titleSorting);
			}
			
			if(targetSorting!=null) {
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
				//3자리 분류기호 100자리 숫자만 남기고 편집.
				String classCode = bookHeldList.get(i).getClassificationCode();
				if(classCode == null) {
					classCode = "-1";
				}
				int classCodeInt = (int)Float.parseFloat(classCode);
				if(classCodeInt < 0) {
					bookHeldList.get(i).setClassCodeHead(classCodeInt);
					/*bookHeldList.get(i).setClassCodeColor(util.getColorKDC(classCodeInt));*/
				} else {
					int classHead1 = classCodeInt/100;
					int classCodeHead = classHead1 * 100;
					bookHeldList.get(i).setClassCodeHead(classCodeHead);
					bookHeldList.get(i).setClassCodeColor(util.getColorKDC(classCodeHead));
				}
				
				//QR코드 생성
				qrCode.makeQR(bookHeldList.get(i).getLocalIdBarcode(), 100, 100
						,"/var/packages/Tomcat7/target/src/webapps/downloads/upload/finebook4/qrcode/libNo"+loginInfo.getIdLibMng()+"/"
						,bookHeldList.get(i).getLocalIdBarcode()+".png" );
				
			}
			/** 4) View 처리하기 */
			// 조회 결과를 View에게 전달한다.
			model.addAttribute("bookHeldList", bookHeldList);
		} else {
			
			if (!regex.isValue(dateSorting)) {
				return web.redirect(null, "원하시는 날짜 또는 도서명, 등록번호를 입력하세요.");
			}
			
			if (!regex.isValue(targetSorting)) {
				return web.redirect(null, "원하시는 날짜 또는 도서명, 등록번호를 입력하세요.");
			}
			
			if (!regex.isValue(titleSorting)) {
				return web.redirect(null, "원하시는 날짜 또는 도서명, 등록번호를 입력하세요.");
			}
			
		}
		
		TagSetting tag = new TagSetting();
		tag.setIdLib(loginInfo.getIdLibMng());
		
		try {
			tag = tagSettingService.selectRollTagPositionValue(tag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("tag", tag);
		
		String movePage = "default";
		if(tagType == 1) {
			movePage = "opt1";
		} else if(tagType == 2) {
			movePage = "opt2";
		} else if(tagType == 10) {
			movePage = "roll_default";
		}
		return new ModelAndView("book/print_tag_"+ movePage);
	}
	
	@RequestMapping(value = "/book/print_position_setting.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView printTagPosition(Locale locale, Model model) {
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		/** 로그인 여부 검사 */
		// 로그인중인 회원 정보 가져오기
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
		}
		
		TagSetting tag = new TagSetting();
		tag.setIdLib(loginInfo.getIdLibMng());
		
		try {
			tag = tagSettingService.selectRollTagPositionValue(tag);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		model.addAttribute("tag", tag);
		
		return new ModelAndView("book/print_position_setting");
	}
	
	@RequestMapping(value = "/book/print_position_setting_ok.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView printTagPositionOk(Locale locale, Model model) {
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		/** 로그인 여부 검사 */
		// 로그인중인 회원 정보 가져오기
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
		}
		
		float marginLeft = web.getInt("margin-left");
		float tagWidth = web.getInt("tag-width");
		float tagHeight = web.getInt("tag-height");
		
		TagSetting tag = new TagSetting();
		tag.setIdLib(loginInfo.getIdLibMng());
		tag.setMarginLeft(marginLeft);
		tag.setTagWidth(tagWidth);
		tag.setTagHeight(tagHeight);
		
		try {
			tagSettingService.updateTagPosition(tag);
			tag = tagSettingService.selectRollTagPositionValue(tag);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		model.addAttribute("tag", tag);
		
		return web.redirect(web.getRootPath() + "/book/print_position_setting.do", "태그 위치가 수정되었습니다.");
		/*return new ModelAndView("book/print_position_setting");*/
	}
}
