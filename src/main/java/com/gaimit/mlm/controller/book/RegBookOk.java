package com.gaimit.mlm.controller.book;

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

import com.gaimit.helper.RegexHelper;
import com.gaimit.helper.UploadHelper;
import com.gaimit.helper.Util;
import com.gaimit.helper.WebHelper;
import com.gaimit.mlm.model.BookHeld;
import com.gaimit.mlm.model.Manager;
import com.gaimit.mlm.service.BookHeldService;

@Controller
public class RegBookOk {
	/** (1) 사용하고자 하는 Helper + Service 객체 선언 */
	// --> import org.apache.logging.log4j.Logger;
	Logger logger = LoggerFactory.getLogger(RegBookOk.class);
	// --> import org.apache.ibatis.session.SqlSession;
	@Autowired
	SqlSession sqlSession;
	// --> import study.jsp.helper.WebHelper;
	@Autowired
	WebHelper web;
	// --> import study.jsp.helper.RegexHelper;
	@Autowired
	RegexHelper regex;
	@Autowired
	Util util;
	// --> import study.jsp.helper.UploadHelper;
	@Autowired
	UploadHelper upload;
	
	@Autowired
	BookHeldService bookHeldService;

	@RequestMapping(value = "/book/reg_book_ok.do")
	public ModelAndView doRun(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();

		/** (3) 로그인 여부 검사 */
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
		}*/

		// UploadHelper에서 텍스트 형식의 파라미터를 분류한 Map을 리턴받아서 값을 추출한다.
		//Map<String, String> web = upload.getweb();
				
		/** reg_book에서 전달받은 book, bookheld 파라미터를 Beans 객체에 담는다. */
		String isbn13 = web.getString("isbn13");
		String isbn10 = web.getString("isbn10");
		String bookTitle = web.getString("bookTitle");
		String author = web.getString("author");
		String authorCode = web.getString("authorCode");
		String publisher = web.getString("publisher");
		String pubDate = web.getString("pubDate");
		String bookCateg = web.getString("bookCateg");
		int page = web.getInt("page");
		int price = web.getInt("price");
		String bookOrNot = web.getString("bookOrNot");
		int purOrDon = web.getInt("purOrDon");
		String classificationCode = web.getString("classificationCode");
		String additionalCode = web.getString("additionalCode");
		
		String volumeCode = web.getString("volumeCode");
		String volumeCode2 = "0";
		if(volumeCode!=null) {
			volumeCode2 = "v" + volumeCode;
		} else {
			volumeCode2 = "0";
		}
		
		String barcodeHead = web.getString("barcodeHead");
		if(barcodeHead==null||barcodeHead.equals("")) {
			barcodeHead = "";
		}
		String bookCover = web.getString("bookCover");
		String bookDesc = web.getString("bookDesc");
		
		
		// 전달받은 파라미터는 값의 정상여부 확인을 위해서 로그로 확인
		logger.debug("isbn13=" + isbn13);
		logger.debug("isbn10=" + isbn10);
		logger.debug("bookTitle=" + bookTitle);
		logger.debug("author=" + author);
		logger.debug("authorCode=" + authorCode);
		logger.debug("publisher=" + publisher);
		logger.debug("pubDate=" + pubDate);
		logger.debug("bookCateg=" + bookCateg);
		logger.debug("page=" + page);
		logger.debug("price=" + price);
		logger.debug("bookOrNot=" + bookOrNot);
		logger.debug("purOrDon=" + purOrDon);
		logger.debug("classificationCode=" + classificationCode);
		logger.debug("additionalCode=" + additionalCode);
		logger.debug("volumeCode=" + volumeCode);
		logger.debug("volumeCode2=" + volumeCode2);
		logger.debug("bookCover=" + bookCover);
		logger.debug("bookDesc=" + bookDesc);
		logger.debug("barcodeHead=" + barcodeHead);
		
		
		//book, bookHeld insert 위한 정보 수집
		BookHeld bookHeld = new BookHeld();
		BookHeld callIdBook = null;
		
		//manager로부터 도서관번호 부여.
		bookHeld.setLibraryIdLib(idLib);
		bookHeld.setIsbn13Book(isbn13);
		bookHeld.setIsbn10Book(isbn10);
		bookHeld.setTitleBook(bookTitle);
		bookHeld.setWriterBook(author);
		bookHeld.setAuthorCode(authorCode);
		bookHeld.setPublisherBook(publisher);
		bookHeld.setPubDateBook(pubDate);
		bookHeld.setCategoryBook(bookCateg);
		bookHeld.setPage(page);
		bookHeld.setPriceBook(price);
		bookHeld.setBookOrNot(bookOrNot);
		bookHeld.setPurchasedOrDonated(purOrDon);
		bookHeld.setClassificationCode(classificationCode);
		bookHeld.setAdditionalCode(additionalCode);
		bookHeld.setVolumeCode(volumeCode2);
		bookHeld.setImageLink(bookCover);
		bookHeld.setDescriptionBook(bookDesc);
		bookHeld.setAvailable(1);
		
		
		
		//barcode 작성을 위한 로직
		BookHeld lastLocalBarcode = null;
		String lastLocalBarcode2 = null;
		
		try {
			//최종 barcodeHead를 가지고 와서 함수에 적용.
			//처음일 경우 이 함수를 호출할지 말지 걸어야되나?
			int firstBookHeld = bookHeldService.selectBookHeldFirstCount(bookHeld);
			if(firstBookHeld > 0) {
				lastLocalBarcode = bookHeldService.selectLastLocalBarcode(bookHeld);
				lastLocalBarcode2 = lastLocalBarcode.getLocalIdBarcode();
			} else {
				lastLocalBarcode2 = barcodeHead + "0";
			}
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		//요 아래 8자리 문자로 만드는 함수 만들어서 넣기
		int barNum = util.numExtract(lastLocalBarcode2)+1;
		String barStr = util.strExtract(lastLocalBarcode2);
		String localBarcode = util.makeStrLength(8, barStr, barNum);
		//위 함수로 완성된 바코드 넣기
		bookHeld.setLocalIdBarcode(localBarcode);
		
		
		try {
			int checkBookTable= bookHeldService.selectBookCount(bookHeld);
			if (checkBookTable > 0) {
				//id_book을 받아오기 위한 객체
				callIdBook = bookHeldService.selectBookId(bookHeld);
				//callIdBook에 id_book을 담고 bookHeld에 전달.
				bookHeld.setBookIdBook(callIdBook.getIdBook());
				
				//if 복본이 있는지 체크후 insertBookHeld;
				int copyCheckBookHeld = bookHeldService.selectBookHeldCount(bookHeld);
				if(copyCheckBookHeld == 0) {
					bookHeldService.insertBookHeld(bookHeld);
				} else {
					int lastCopyCode = bookHeldService.selectLastCopyCode(bookHeld);
					if(lastCopyCode == 0) {
						bookHeld.setCopyCode(2);
						bookHeldService.insertBookHeld(bookHeld);
					} else {
						lastCopyCode += 1;
						bookHeld.setCopyCode(lastCopyCode);
						bookHeldService.insertBookHeld(bookHeld);
					}
					
				}
			} else if (checkBookTable == 0) {
				//book에 아예 없을 때
				bookHeldService.insertBook(bookHeld);
				//id_book을 받아오기 위한 객체
				callIdBook = bookHeldService.selectBookId(bookHeld);
				//callIdBook에 id_book을 담고 bookHeld에 전달.
				bookHeld.setBookIdBook(callIdBook.getIdBook());
				
				bookHeldService.insertBookHeld(bookHeld);
			}
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		

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
		//model.addAttribute("brwList", list);

		/** (9) 가입이 완료되었으므로 메인페이지로 이동 */
		return web.redirect(web.getRootPath() + "/book/reg_book.do", "도서 등록이 완료되었습니다.");
	}
}
