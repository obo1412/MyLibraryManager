package com.gaimit.mlm.controller.member;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gaimit.helper.WebHelper;
import com.gaimit.mlm.model.Manager;
import com.gaimit.mlm.service.ManagerService;
import com.gaimit.mlm.service.MemberService;

@Controller
public class Join {

	/** (1) 사용하고자 하는 Helper 객체 선언 */
	// --> import study.jsp.helper.WebHelper;
	@Autowired
	WebHelper web;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	ManagerService managerService;
	
	@RequestMapping(value="/member/join.do")
	public ModelAndView doRun(Locale locale, Model model, 
			HttpServletRequest request, HttpServletResponse response) {	
		
		/** (2) WebHelper 초기화 */
		web.init();
		
		/** (3) 로그인 여부 검사 */
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		int idLib = 0;
		// 관리자 로그인 중이라면 관리자의 도서관 id를 가져온다.
		if (web.getSession("loginInfo") == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인이 필요합니다.");
		} else {
			idLib = loginInfo.getIdLibMng();
		}
	

		/** (8) Service를 통한 데이터베이스 저장 처리 */
		int lastId = 0;
		try {
			lastId = memberService.selectLastJoinedId();
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		model.addAttribute("lastId", lastId);
		model.addAttribute("idLib", idLib);
		
		return new ModelAndView("member/join");
	}
}
