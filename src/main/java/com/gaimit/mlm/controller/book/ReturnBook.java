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

import com.gaimit.mlm.model.Member;
import com.gaimit.mlm.model.Manager;
import com.gaimit.mlm.model.Borrow;
import com.gaimit.mlm.service.BookService;
import com.gaimit.mlm.service.BrwService;
import com.gaimit.mlm.service.ManagerService;
import com.gaimit.mlm.service.MemberService;

@Controller
public class ReturnBook {
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
	
	/** 교수 목록 페이지 */
	@RequestMapping(value = "/book/return_book.do", method = {RequestMethod.GET, RequestMethod.POST})
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
		
		// web으로부터 책 코드 번호 수신
		String bookCode = web.getString("bookCode", "");
		
		// 파라미터를 저장할 Beans
		Member member = new Member();
		member.setIdLib(idLib);
		
		/*Book book = new Book();
		book.setIdLibBook(idLib);
		book.setIdCodeBook(bookCode);*/
		
		Borrow brw = new Borrow();
		// 멤버id(회원id)로 검색에 사용할 객체 생성
		Borrow brwSe = new Borrow();
		List<Borrow> brwList = null;
		
		
		/**
		 * 여기 수정해야됨***************************
		 */
		brw.setIdLibBrw(idLib);
		//brw.setIdCodeBook(bookCode);
		
		
		//책으로 검색 시작 => 그 책을 빌린 회원id로 더 빌려간 책이 없는지 확인.
		int memberId = 0;
		
		if(!(bookCode.equals(""))) {
			try {
				brw = brwService.getBorrowItemByBookCode(brw);
			}  catch (Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
			}
			
			//가져온 memberId로 brw2
			memberId = brw.getIdMemberBrw();
			brwSe.setIdMemberBrw(memberId);
			try {
				brwList = brwService.getBorrowListByMbrId(brwSe);
			}  catch (Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
			}
		}
		
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
		
		
		if(brw != null) {
			model.addAttribute("brwItem", brw);
			model.addAttribute("brwList", brwList);
		}
		
		/** 4) View 처리하기 */
		// 조회 결과를 View에게 전달한다.
		/*model.addAttribute("keyword", keyword);*/
		/*model.addAttribute("page", page);*/
		
		return new ModelAndView("book/return_book");
	}	
}
