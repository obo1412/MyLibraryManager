package com.gaimit.mlm.controller.book;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
		int memberId = web.getInt("memberId", 0);
		String name = web.getString("name", "");
		String phone = web.getString("phone", "");
		int brwLimit = web.getInt("brwLimit", 0);
		
		// 파라미터를 저장할 Beans
		Member member = new Member();
		member.setIdLib(idLib);
		member.setName(searchName);
		
		/** 3) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		List<Member> list = null;
		String memberName = member.getName();
		
		//멤버가 현재 대여중인 도서 권수
		int brwNow = 0;
		//앞으로 대여가능한 도서 권수
		int brwPsb = 0;
		
		//미반납 연체
		int overDueCount = 0;
		//반납된 연체 기간이 남아있을 경우
		Borrow overDueDate = new Borrow();
		String restrictDate = null;
		
		
		if(!memberName.equals("")) {
			try {
				list = memberService.getMemberListByLibAndName(member);
			} catch (Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
			}
		}
		
		//list 결과값이 null이 아니면
		if(list != null) {
			model.addAttribute("list", list);
			
			if(list.size() < 2 && list.size() > 0) {
				memberId = list.get(0).getId();
				name = list.get(0).getName();
				phone = list.get(0).getPhone();
				String grade = list.get(0).getGradeName();
				brwLimit = list.get(0).getBrwLimit();
				int dateLimit = list.get(0).getDateLimit();
				
				// 현재 대여중인 도서 권수 호출.
				try {
					Borrow brwTemp = new Borrow();
					brwTemp.setIdLibBrw(idLib);
					brwTemp.setIdMemberBrw(memberId);
					brwNow = brwService.selectBrwBookCountByMemberId(brwTemp);
					//연체 제한 정보 호출
					overDueCount = brwService.selectOverDueCountByMemberId(brwTemp);
					overDueDate = brwService.selectRestrictDate(brwTemp);
					if(overDueDate != null) {
						restrictDate = overDueDate.getRestrictDateBrw();
					}
				} catch (Exception e) {
					return web.redirect(null, e.getLocalizedMessage());
				}
				
				// 대출가능 권수 계산
				brwPsb = brwLimit - brwNow;
				
				model.addAttribute("brwNow", brwNow);
				model.addAttribute("brwPsb", brwPsb);
				model.addAttribute("grade", grade);
				model.addAttribute("brwLimit", brwLimit);
				model.addAttribute("dateLimit", dateLimit);
				model.addAttribute("memberId", memberId);
				model.addAttribute("name", name);
				model.addAttribute("phone", phone);
				model.addAttribute("overDueCount", overDueCount);
				model.addAttribute("restrictDate", restrictDate);
			}
		}
		
		//남은 오늘의도서현황, 회원의 반납전 도서를 위한 객체
		List<Borrow> brwRmnList = null;
		List<Borrow> brwListToday = null;
		Borrow brw = new Borrow();
		brw.setIdLibBrw(idLib);
		brw.setIdMemberBrw(memberId);
		
		try {
			overDueCount = brwService.selectOverDueCountByMemberId(brw);
			overDueDate = brwService.selectRestrictDate(brw);
			if(overDueDate != null) {
				restrictDate = overDueDate.getRestrictDateBrw();
			}
			
			if(brwLimit != 0) {
				brwNow = brwService.selectBrwBookCountByMemberId(brw);
				brwPsb = brwLimit - brwNow;
			}
			
			if(memberId != 0) {
				brwRmnList = brwService.getBorrowListByMbrId(brw);
			}
			brwListToday = brwService.selectBorrowListToday(brw);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** 4) View 처리하기 */
		// 조회 결과를 View에게 전달한다.
		model.addAttribute("memberId", memberId);
		model.addAttribute("name", name);
		model.addAttribute("phone", phone);
		model.addAttribute("brwLimit", brwLimit);
		model.addAttribute("brwNow", brwNow);
		model.addAttribute("brwPsb", brwPsb);
		model.addAttribute("brwRmnList", brwRmnList);
		model.addAttribute("brwListToday", brwListToday);
		model.addAttribute("overDueCount", overDueCount);
		model.addAttribute("restrictDate", restrictDate);
		
		return new ModelAndView("book/brw_book");
	}
	
	/** 도서 등록 페이지 */
	@ResponseBody
	@RequestMapping(value = "/book/brw_search_member.do", method = RequestMethod.POST)
	public void makeAuthorCode(Locale locale, Model model,
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
		
		String searchKeyword = web.getString("searchKeyword", "");
		System.out.println(searchKeyword);
		
		Member member = new Member();
		// 이름에다가 그냥 검색 키워드 다 넣고, 이름으로 전화번호, 회원번호 검색
		member.setIdLib(loginInfo.getIdLibMng());
		member.setName(searchKeyword);
		
		List<Member> memberList = null;
		
		try {
			memberList = memberService.selectMemberListForBorrow(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// --> import java.util.HashMap;
		// --> import java.util.Map;
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("memberList", memberList);
		
		// --> import com.fasterxml.jackson.databind.ObjectMapper;
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());	
		}
	}
}
