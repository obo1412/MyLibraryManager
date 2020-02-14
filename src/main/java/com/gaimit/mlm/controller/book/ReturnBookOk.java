package com.gaimit.mlm.controller.book;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaimit.helper.PageHelper;
import com.gaimit.helper.WebHelper;

import com.gaimit.mlm.model.Member;
import com.gaimit.mlm.model.Manager;
import com.gaimit.mlm.model.Borrow;
import com.gaimit.mlm.service.BookService;
import com.gaimit.mlm.service.BrwService;
import com.gaimit.mlm.service.ManagerService;
import com.gaimit.mlm.service.MemberService;

@Controller
public class ReturnBookOk {
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
	BookService bookService;
	
	@Autowired
	BrwService brwService;
	
	/** 도서 반납 페이지 */
	@RequestMapping(value = "/book/return_book_ok.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView RtnBook(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		
		/*response.setContentType("application/json");*/
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		int idLib = 0;
		
		/** 로그인 여부 검사 */
		// 로그인중인 회원 정보 가져오기
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			/*web.printJsonRt("로그인 정보가 명확하지 않습니다. 다시 로그인 해주세요.");*/
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
		} else {
			idLib = loginInfo.getIdLibMng();
		}
		
		// web으로부터 책 코드 번호 수신
		String barcodeBook = web.getString("barcodeBookRtn", "");
		int idMemberBrw = web.getInt("idMemberBrw", 0);
		String name = web.getString("name", "");
		String phone = web.getString("phone", "");
		int brwLimit = web.getInt("brwLimit", 0);
		
		//멤버가 현재 대여중인 도서 권수
		int brwNow = 0;
		//앞으로 대여가능한 도서 권수
		int brwPsb = 0;
		
		if(barcodeBook.equals("")) {
			return web.redirect(web.getRootPath() + "/book/brw_book.do", "도서바코드를 입력하세요.");
		}
		
		// 파라미터를 저장할 Beans
		Member member = new Member();
		member.setIdLib(idLib);
		
		/*Book book = new Book();
		book.setIdLibBook(idLib);
		book.setIdCodeBook(bookCode);*/
		
		Borrow brw = new Borrow();
		// 아래 brw로 idBrw 호출을 위한 객체
		Borrow brwSe = new Borrow();
		// 아래 멤버id(회원id)로 검색에 사용할 객체 생성 아직미구현
		List<Borrow> brwRmnList = null;
		List<Borrow> brwListToday = null;
		
		brw.setIdLibBrw(idLib);
		brw.setLocalIdBarcode(barcodeBook);
		
		if(!(barcodeBook.equals(""))) {
			try {
				brwSe = brwService.getBorrowItemByBarcodeBook(brw);
				idMemberBrw = brwSe.getIdMemberBrw();
				name = brwSe.getName();
				phone = brwSe.getPhone();
				brw.setIdBrw(brwSe.getIdBrw());
				brw.setIdMemberBrw(brwSe.getIdMemberBrw());
				brwService.updateBorrowEndDate(brw);
				brwNow = brwService.selectBrwBookCountByMemberId(brw);
				//책으로 검색 시작 => 그 책을 빌린 회원id로 더 빌려간 책이 없는지 확인.
				brwRmnList = brwService.getBorrowListByMbrId(brw);
				//오늘 대출/반납 도서 조회
				brwListToday = brwService.selectBorrowListToday(brw);
				
			}  catch (Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
			}
		}
		
		// 대출가능 권수 계산
		brwPsb = brwLimit - brwNow;
		
		/*brw.setIdBrw(idBrw);*/
		
		/*Map<String, Object> data = new HashMap<String, Object>();
		data.put("rtndItem", brwSe);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());	
		}*/
		
		// 검색어 파라미터 받기 + Beans 설정
		/*String keyword = web.getString("keyword", "");
		member.setName(keyword);*/
		
		// 현재 페이지 번호에 대한 파라미터 받기
		/*int nowPage = web.getInt("page", 1);*/
		
		/** 2) 페이지 번호 구현하기 */
		// 전체 데이터 수 조회하기
		/*int totalCount = 0;
		try {
			totalCount = memberService.getMemberCount(member);
		}  catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}*/
		
		// 페이지 번호에 대한 연산 수행 후 조회조건값 지정을 위한 Beans에 추가하기
		/*page.pageProcess(nowPage, totalCount, 10, 5);
		member.setLimitStart(page.getLimitStart());
		member.setListCount(page.getListCount());*/
		
		
		/*if(brw != null) {
			model.addAttribute("brwItem", brw);
		}*/
		
		/** 4) View 처리하기 */
		// 조회 결과를 View에게 전달한다.
		/*model.addAttribute("keyword", keyword);*/
		/*model.addAttribute("page", page);*/
		model.addAttribute("memberId", idMemberBrw);
		model.addAttribute("name", name);
		model.addAttribute("phone", phone);
		model.addAttribute("brwLimit", brwLimit);
		model.addAttribute("brwNow", brwNow);
		model.addAttribute("brwPsb", brwPsb);
		model.addAttribute("brwListToday", brwListToday);
		model.addAttribute("brwRmnList", brwRmnList);
		
		return new ModelAndView("book/brw_book");
		/*return web.redirect(web.getRootPath() + "/book/brw_book.do", "도서 반납이 완료되었습니다.");*/
	}
	
	@RequestMapping(value = "/book/return_cancel_book_ok.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView RtnCancelBook(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		
		/** 1) WebHelper 초기화 및 파라미터 처리 */
		web.init();
		
		int idLib = 0;
		
		/** 로그인 여부 검사 */
		// 로그인중인 회원 정보 가져오기
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			/*web.printJsonRt("로그인 정보가 명확하지 않습니다. 다시 로그인 해주세요.");*/
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
		} else {
			idLib = loginInfo.getIdLibMng();
		}
		
		String barcodeBookRtnCancle = web.getString("barcodeBookRtnCancle");
		int idBrw = web.getInt("idBrw");
		int idMemberBrw = web.getInt("idMemberBrw", 0);
		String name = web.getString("name", "");
		String phone = web.getString("phone", "");
		int brwLimit = web.getInt("brwLimit", 0);
		
		//멤버가 현재 대여중인 도서 권수
		int brwNow = 0;
		//앞으로 대여가능한 도서 권수
		int brwPsb = 0;
		
		Borrow brw = new Borrow();
		brw.setIdLibBrw(idLib);
		brw.setLocalIdBarcode(barcodeBookRtnCancle);
		brw.setIdBrw(idBrw);
		brw.setIdMemberBrw(idMemberBrw);
		// 아래 멤버id(회원id)로 검색에 사용할 객체 생성 아직미구현
		List<Borrow> brwRmnList = null;
		List<Borrow> brwListToday = null;
		
		try {
			brwService.updateCancelBorrowEndDate(brw);
			//책으로 검색 시작 => 그 책을 빌린 회원id로 더 빌려간 책이 없는지 확인.
			brwNow = brwService.selectBrwBookCountByMemberId(brw);
			brwRmnList = brwService.getBorrowListByMbrId(brw);
			//오늘 대출/반납 도서 조회
			brwListToday = brwService.selectBorrowListToday(brw);
			
		}  catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		// 대출가능 권수 계산
		brwPsb = brwLimit - brwNow;
		
		model.addAttribute("memberId", idMemberBrw);
		model.addAttribute("name", name);
		model.addAttribute("phone", phone);
		model.addAttribute("brwLimit", brwLimit);
		model.addAttribute("brwNow", brwNow);
		model.addAttribute("brwPsb", brwPsb);
		model.addAttribute("brwListToday", brwListToday);
		model.addAttribute("brwRmnList", brwRmnList);
		
		return new ModelAndView("book/brw_book");
	}
	
	
}
