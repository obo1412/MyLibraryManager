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
/*import com.gaimit.mlm.model.BbsDocument;
import com.gaimit.mlm.service.BbsDocumentService;*/

@Controller
public class Index {
	
	Logger logger = LoggerFactory.getLogger(Index.class);
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	WebHelper web;
	
	@RequestMapping(value= {"/", "/index.do"})
	public ModelAndView doRun(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		web.init();
		
		

		// "/WEB-INF/views/index.jsp"파일을 View로 사용한다.
		return new ModelAndView("index");
	}
	
	/**
	 * 특정 카테고리에 대한 상위 n개의 게시물 가져오기
	 * @param category - 가져올 카테고리
	 * @param listCount - 가져올 게시물 수
	 * @return
	 * @throws Exception
	 */

}
