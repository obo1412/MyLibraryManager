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
public class BrwBook {
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
	@RequestMapping(value = "/book/brw_book.do", method = {RequestMethod.GET, RequestMethod.POST})
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
		
		String searchName = web.getString("search-name", "");
		
		// 파라미터를 저장할 Beans
		Member member = new Member();
		member.setIdLib(idLib);
		member.setName(searchName);
		
		/*Book book = new Book();
		book.setIdLibBook(idLib);*/
		
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
		
		/** 3) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		List<Member> list = null;
		
		Member item = null;
		int CountMember = 0;
		
		String memberName = member.getName();
		try {
			//책대여를 위한 회원조회
			if(!(memberName.equals(""))) {
				CountMember = memberService.getMemberCountByNameAndIdLib(member);
				if(CountMember == 1) {
					if(!(memberName.equals(""))) {
						item = memberService.selectMember(member);
					}
				} else if(CountMember > 1) {
					list = memberService.getMemberListByLibAndName(member);
				}
			}
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		if(item != null) {
			int memberId = item.getId();
			String name = item.getName();
			String phone = item.getPhone();
			String grade = item.getGradeName();
			int brwLimit = item.getBrwLimit();
			int dateLimit = item.getDateLimit();
			
			model.addAttribute("grade", grade);
			model.addAttribute("brwLimit", brwLimit);
			model.addAttribute("dateLimit", dateLimit);
			model.addAttribute("memberId", memberId);
			model.addAttribute("name", name);
			model.addAttribute("phone", phone);
		}
		
		if(list != null) {
			model.addAttribute("list", list);
		}
		
		List<Borrow> brwList = null;
		Borrow brw = new Borrow();
		brw.setIdLibBrw(idLib);
		
		try {
			brwList = brwService.selectBorrowListToday(brw);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** 4) View 처리하기 */
		// 조회 결과를 View에게 전달한다.
		model.addAttribute("CountMember", CountMember);
		model.addAttribute("brwList", brwList);
		/*model.addAttribute("keyword", keyword);*/
		model.addAttribute("page", page);
		
		return new ModelAndView("book/brw_book");
	}	
}
