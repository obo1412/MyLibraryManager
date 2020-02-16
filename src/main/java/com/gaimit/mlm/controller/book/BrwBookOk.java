package com.gaimit.mlm.controller.book;

import java.io.IOException;
import java.util.List;
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

import com.gaimit.helper.RegexHelper;
import com.gaimit.helper.UploadHelper;
import com.gaimit.helper.WebHelper;
import com.gaimit.mlm.model.BookHeld;
import com.gaimit.mlm.model.Borrow;
import com.gaimit.mlm.model.Manager;
import com.gaimit.mlm.model.Member;
import com.gaimit.mlm.service.BookHeldService;
import com.gaimit.mlm.service.BrwService;
import com.gaimit.mlm.service.MemberService;

@Controller
public class BrwBookOk {
	/** (1) 사용하고자 하는 Helper + Service 객체 선언 */
	// --> import org.apache.logging.log4j.Logger;
	Logger logger = LoggerFactory.getLogger(BrwBookOk.class);
	// --> import org.apache.ibatis.session.SqlSession;
	@Autowired
	SqlSession sqlSession;
	// --> import study.jsp.helper.WebHelper;
	@Autowired
	WebHelper web;
	// --> import study.jsp.helper.RegexHelper;
	@Autowired
	RegexHelper regex;
	// --> import study.jsp.helper.UploadHelper;
	@Autowired
	UploadHelper upload;
	// --> import study.jsp.mysite.service.MemberService;
	@Autowired
	MemberService memberService;
	
	@Autowired
	BookHeldService bookHeldService;
	
	@Autowired
	BrwService brwService;

	@RequestMapping(value = "/book/brw_book_ok.do")
	public ModelAndView doRun(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();

		/** (3) 로그인 여부 검사 */
		// 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
		/*if (web.getSession("loginInfo") != null) {
			return web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
		}*/
		/** 로그인 여부 검사 */
		// 로그인중인 회원 정보 가져오기
		int idLib = 0;
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
		} else {
			idLib = loginInfo.getIdLibMng();
		}

		/** (4) 파일이 포함된 POST 파라미터 받기 */
		// <form>태그 안에 <input type="file">요소가 포함되어 있고,
		// <form>태그에 enctype="multipart/form-data"가 정의되어 있는 경우
		// WebHelper의 getString()|getInt() 메서드는 더 이상 사용할 수 없게 된다.
		/*try {
			upload.multipartRequest();
		} catch (Exception e) {
			return web.redirect(null, "multipart 데이터가 아닙니다.");
		}
*/
		// UploadHelper에서 텍스트 형식의 파라미터를 분류한 Map을 리턴받아서 값을 추출한다.
		//Map<String, String> paramMap = upload.getParamMap();
				
		/** brw_book에서 전달받은 member 파라미터를 Beans 객체에 담는다. */
		int memberId = web.getInt("memberId");
		String name = web.getString("name");
		String phone = web.getString("phone");
		
		//위에서 받은 파라미터를 이용하여, 해당 도서관에 사용자가 있는지 체크
		// 추후에 이름과 id코드도 받아와서 이 사용자가 맞는지 확인해야될 듯.
		Member member = new Member();
		member.setId(memberId);
		member.setIdLib(idLib);
		// 전달받은 파라미터는 값의 정상여부 확인을 위해서 로그로 확인
		logger.debug("memberId=" + memberId);
		logger.debug("idLib=" + idLib);
		
		//brw_book.jsp 에서  책에 대한 파라미터 받기
		String barcodeBook = web.getString("barcodeBook");
		
		//borrow insert를 위한 정보 수집
		Borrow brw = new Borrow();
		//borrow를 위한 1차 정보 주입 
		brw.setIdLibBrw(idLib);
		brw.setIdMemberBrw(memberId);
		brw.setLocalIdBarcode(barcodeBook);

		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(idLib);
		// bookHeld가 book을 상속받아서 아래 조건 성립됨.
		bookHeld.setLocalIdBarcode(barcodeBook);
		
		int brwLimit = 0;
		int brwNow = 0;
		int brwPsb = 0;
		
		//bookCode를 이용하여 도서 정보 호출
		try {
			//barcode로 id_brw count 검사, 있으면 대출중 NPE 반환
			brwService.getBorrowCountByBarcodeBook(brw);
			bookHeld = bookHeldService.getBookHelditem(bookHeld);
			
			brwLimit = brwService.selectBrwLimitByMemberId(brw);
			brwNow = brwService.selectBrwBookCountByMemberId(brw);
			brwPsb = brwLimit - brwNow;
			if(brwLimit - brwNow < 1) {
				return web.redirect(web.getRootPath() + "/book/brw_book.do", "대출 권수 초과입니다.");
			}
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		// 위 과정으로 도서정보가 나오면, 도서 대출을 위한 정보 수집 id_book
		if(bookHeld.getId() != 0) {
			brw.setBookHeldId(bookHeld.getId());
		}
		
		//대출된 도서 결과를 저장하기 위한 객체
		List<Borrow> brwListToday = null;
		
		// 대출도서 정보가 다모이면 borrow insert
		try {
			brwService.insertBorrow(brw);
			brwListToday = brwService.selectBorrowListToday(brw);
			//대출 후 대출 권수 최신화
			brwNow = brwService.selectBrwBookCountByMemberId(brw);
			brwPsb = brwLimit - brwNow;
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		

		// 전달받은 파라미터는 값의 정상여부 확인을 위해서 로그로 확인
		

		/** (6) 업로드 된 파일 정보 추출 */
		/*List<FileInfo> fileList = upload.getFileList();
		// 업로드 된 프로필 사진 경로가 저장될 변수
		String profileImg = null;

		// 업로드 된 파일이 존재할 경우만 변수값을 할당한다.
		if (fileList.size() > 0) {
			// 단일 업로드이므로 0번째 항목만 가져온다.
			FileInfo info = fileList.get(0);
			profileImg = info.getFileDir() + "/" + info.getFileName();
		}

		// 파일경로를 로그로 기록
		logger.debug("profileImg=" + profileImg);*/
		
		// 조회 결과를 View에게 전달한다.
		model.addAttribute("memberId", memberId);
		model.addAttribute("name", name);
		model.addAttribute("phone", phone);
		model.addAttribute("brwLimit", brwLimit);
		model.addAttribute("brwNow", brwNow);
		model.addAttribute("brwPsb", brwPsb);
		model.addAttribute("brwListToday", brwListToday);

		/** (9) 가입이 완료되었으므로 메인페이지로 이동 */
		return new ModelAndView("book/brw_book");
		/*return web.redirect(web.getRootPath() + "/book/brw_book.do", "도서 대출이 완료되었습니다.");*/
	}
}
