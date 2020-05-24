package com.gaimit.mlm.controller.book;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gaimit.helper.RegexHelper;
import com.gaimit.helper.UploadHelper;
import com.gaimit.helper.WebHelper;
import com.gaimit.mlm.model.BookHeld;
import com.gaimit.mlm.model.Manager;
import com.gaimit.mlm.service.BookHeldService;

@Controller
public class DbTransfer {
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	// --> import org.apache.logging.log4j.Logger;
	private static Logger logger = LoggerFactory.getLogger(DbTransfer.class);
	@Autowired
	SqlSession sqlSession;
	@Autowired
	WebHelper web;
	@Autowired
	UploadHelper upload;
	@Autowired
	RegexHelper regex;
	@Autowired
	BookHeldService bookHeldService;
	
	@RequestMapping(value = "/book/db_transfer.do")
	public ModelAndView DbTransferV(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {		
		web.init();
		
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		if (web.getSession("loginInfo") == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인이 필요합니다.");
		}

		return new ModelAndView("book/db_transfer");
	}
	
	// mdb 데이터 MySQL에 저장 test.
	public void mdbConvertTest() {
		Connection conn;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			
			conn = DriverManager.getConnection("jdbc:ucanaccess://E:\\downloads/Finebook.mdb"); // mdb 파일 위치 설정.
			Statement s = conn.createStatement();		
			ResultSet rs = s.executeQuery("SELECT * FROM 도서이동테스트");
			
			// 성능 때문에 반복문 안에서 new는 하는 것은 피해야함. 
			BookHeld bookHeld = new BookHeld();
			String title = "";
			String writer = "";
			
			while (rs.next()) {				
				title = rs.getString(1);
				writer = rs.getString(2);
				
				// console에서 mdb 값 확인.
			    logger.debug("mdb 1열="+title);
			    logger.debug("mdb 2열="+writer);
			    
			    // mdb 값 data로 설정.
				bookHeld.setTitleBook(title);
				bookHeld.setWriterBook(writer);
				
				// MySQL 삽입.
				bookHeldService.insertBook(bookHeld);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/book/db_transfer_ok.do")
	public ModelAndView DbTransferOk(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {		
		web.init();
		
		mdbConvertTest();

		String url = web.getRootPath() + "/book/reg_book_batch.do";
		return web.redirect(url, "도서 데이터베이스 이관 테스트 완료");
	}
	
	@ResponseBody
	@RequestMapping(value= "/export_bookheld_excel_ok.do", method = RequestMethod.POST)
	public void exportExcel(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		web.init();
		
		Manager loginInfo = (Manager) web.getSession("loginInfo");
		
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (web.getSession("loginInfo") == null) {
			web.printJsonRt("로그인 후에 이용 가능합니다.");
		}
		
		BookHeld bookHeld = new BookHeld();
		bookHeld.setLibraryIdLib(loginInfo.getIdLibMng());
		
		//엑셀로 내보내기 위한 준비
		String bookDbName = loginInfo.getNameLib()+"_도서등록정보";
		String defaultPath = "/var/packages/Tomcat7/target/src/webapps/downloads/upload/ExportData";
		String filePath = defaultPath+"/"+bookDbName+".xlsx";
		
		FileOutputStream fos = null;
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		XSSFRow titleRow = null;
		XSSFRow row = null;
		
		//엑셀로 내보내기 위한 준비
		
		List<BookHeld> bookHeldList = null;
		
		try {
			bookHeldList = bookHeldService.getBookHeldList(bookHeld);
			
			titleRow = sheet.createRow(0);
			titleRow.createCell(0).setCellValue("번호");
			titleRow.createCell(1).setCellValue("도서명");
			titleRow.createCell(2).setCellValue("저자명");
			titleRow.createCell(3).setCellValue("출판사");
			titleRow.createCell(4).setCellValue("출판일");
			titleRow.createCell(5).setCellValue("ISBN13");
			titleRow.createCell(6).setCellValue("분류기호");
			titleRow.createCell(7).setCellValue("저자기호");
			titleRow.createCell(8).setCellValue("권차기호");
			titleRow.createCell(9).setCellValue("별치기호");
			titleRow.createCell(10).setCellValue("복본기호");
			titleRow.createCell(11).setCellValue("서가");
			titleRow.createCell(12).setCellValue("도서등록일");
			titleRow.createCell(13).setCellValue("도서등록번호");
			titleRow.createCell(14).setCellValue("수입구분");
			titleRow.createCell(15).setCellValue("도서상태");
			
			
			
			/*for(int i=1; i<=bookHeldList.size(); i++) {
				int j = i-1;
				row = sheet.createRow(i);
				noCell = row.createCell(0);
				noCell.setCellValue(i);
				guestCell = row.createCell(1);
				guestCell.setCellValue(guestList.get(j).getNameGuest());
				giftCell = row.createCell(2);
				giftCell.setCellValue(guestList.get(j).getCelebrationGift());
				rmkCell = row.createCell(3);
				rmkCell.setCellValue(guestList.get(j).getRemarks());
				hostCell = row.createCell(4);
				hostCell.setCellValue(guestList.get(j).getNameHost());
			}*/
			
			File file = new File(defaultPath);
			file.mkdirs();
			
			fos = new FileOutputStream(filePath);
			workbook.write(fos);
			workbook.close();
			fos.close();
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}
		
		
	}
}

