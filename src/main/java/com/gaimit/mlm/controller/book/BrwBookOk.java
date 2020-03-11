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
		int idLib = 0;
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
		} else {
			idLib = loginInfo.getIdLibMng();
		}
		
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
		
		//미반납 연체
		int overDueCount = 0;
		//반납된 연체 기간이 남아있을 경우
		Borrow overDueDate = new Borrow();
		String restrictDate = null;
		
		
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
			//연체 제한 정보 호출 도서 숫자 반영을 위하여
			//update보다 이후에 실행문 호출
			//아래 정보 근거로 대출 제한 조건 문 실행하고 현재는 그냥 대출 가능
			overDueCount = brwService.selectOverDueCountByMemberId(brw);
			overDueDate = brwService.selectRestrictDate(brw);
			if(overDueDate != null) {
				restrictDate = overDueDate.getRestrictDateBrw();
			}
			
			brwService.insertBorrow(brw);
			brwListToday = brwService.selectBorrowListToday(brw);
			//대출 후 대출 권수 최신화
			brwNow = brwService.selectBrwBookCountByMemberId(brw);
			brwPsb = brwLimit - brwNow;
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		// 조회 결과를 View에게 전달한다.
		model.addAttribute("memberId", memberId);
		model.addAttribute("name", name);
		model.addAttribute("phone", phone);
		model.addAttribute("brwLimit", brwLimit);
		model.addAttribute("brwNow", brwNow);
		model.addAttribute("brwPsb", brwPsb);
		model.addAttribute("brwListToday", brwListToday);
		model.addAttribute("overDueCount", overDueCount);
		model.addAttribute("restrictDate", restrictDate);

		/** (9) 가입이 완료되었으므로 메인페이지로 이동 */
		return new ModelAndView("book/brw_book");
		/*return web.redirect(web.getRootPath() + "/book/brw_book.do", "도서 대출이 완료되었습니다.");*/
	}
}
