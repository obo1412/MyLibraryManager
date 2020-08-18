package com.gaimit.mlm.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gaimit.helper.WebHelper;
import com.gaimit.mlm.model.Manager;
import com.gaimit.mlm.service.BbsDocumentService;
import com.gaimit.mlm.service.BrwService;
import com.gaimit.mlm.service.ManagerService;

@Controller
public class Setting {
	
	Logger logger = LoggerFactory.getLogger(Setting.class);
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	WebHelper web;
	
	@Autowired
	BbsDocumentService bbsDocumentService;
	
	@Autowired
	BrwService brwService;
	
	@Autowired
	ManagerService managerService;
	
	@RequestMapping(value= "/setting/language.do")
	public ModelAndView doRun(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		web.init();
		
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", null);
		}
		
		try {
			
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** (4) 최신 글 목록을 View에 전달 */
		//model.addAttribute("noticeList", noticeList);
		
		// "/WEB-INF/views/index.jsp"파일을 View로 사용한다.
		return new ModelAndView("setting/language");
	}
	
	@RequestMapping(value= "/setting/language_change_ok.do")
	public ModelAndView changeLangOk(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		web.init();
		
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", null);
		}
		
		int langNo = web.getInt("language");
		
		Manager manager = new Manager();
		manager.setIdMng(loginInfo.getIdMng());
		manager.setLangMng(langNo);
		
		Manager editInfo = null;
		
		try {
			managerService.updateManagerLanguage(manager);
			editInfo = managerService.selectManager(manager);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		web.removeSession("loginInfo");
		web.setSession("loginInfo", editInfo);

		/** (4) 최신 글 목록을 View에 전달 */
		//model.addAttribute("noticeList", noticeList);
		
		// "/WEB-INF/views/index.jsp"파일을 View로 사용한다.
		//return new ModelAndView("setting/language");
		return web.redirect(web.getRootPath() + "/setting/language.do", "언어 정보가 수정되었습니다.");
	}
	
}
