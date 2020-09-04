package com.gaimit.mlm.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaimit.mlm.model.BookHeld;
import com.gaimit.mlm.service.BookHeldService;

//--> import org.springframework.stereotype.Service; 
@Service
public class BookHeldServiceImpl implements BookHeldService {
	
	/** 처리 결과를 기록할 Log4J 객체 생성 */
	// --> import org.slf4j.Logger;
	// --> import org.slf4j.LoggerFactory;
	private static Logger logger = LoggerFactory.getLogger(BookHeldServiceImpl.class);

	/** MyBatis */
	// --> import org.springframework.beans.factory.annotation.Autowired;
	// --> import org.apache.ibatis.session.SqlSession
	@Autowired
	SqlSession sqlSession;


	@Override
	public List<BookHeld> getBookHeldList(BookHeld bookHeld) throws Exception {
		List<BookHeld> result = null;
		try {
			result = sqlSession.selectList("BookHeldMapper.selectBookHeldList", bookHeld);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 도서가 없습니다.");
		} catch (Exception e) {
			throw new Exception("데이터 조회에 실패했습니다.");
		}
		return result;
	}
	
	@Override
	public List<BookHeld> selectBookHeldListToExcel(BookHeld bookHeld) throws Exception {
		List<BookHeld> result = null;
		try {
			result = sqlSession.selectList("BookHeldMapper.selectBookHeldListToExcel", bookHeld);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 도서(엑셀 추출)가 없습니다.");
		} catch (Exception e) {
			throw new Exception("엑셀 추출 도서 목록 조회에 실패했습니다.");
		}
		return result;
	}
	
	@Override
	public List<BookHeld> getRegTodayBookHeldList(BookHeld bookHeld) throws Exception {
		List<BookHeld> result = null;
		try {
			result = sqlSession.selectList("BookHeldMapper.selectRegTodayBookHeldList", bookHeld);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 도서가 없습니다.");
		} catch (Exception e) {
			throw new Exception("데이터 조회에 실패했습니다.");
		}
		return result;
	}
	
	@Override
	public int selectRegTodayBookCountForPage(BookHeld bookHeld) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BookHeldMapper.selectRegTodayBookCountForPage", bookHeld);
		} catch (Exception e) {
			throw new Exception("regToday 페이지를 위한 도서수 조회에 실패했습니다.");
		}
		return result;
	}


	@Override
	public BookHeld getBookHelditem(BookHeld bookHeld) throws Exception {
		BookHeld result = null;
		try {
			result = sqlSession.selectOne("BookHeldMapper.selectBookHeld", bookHeld);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 도서 정보가 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("도서 정보 조회에 실패했습니다.");
		}
		return result;
	}



	/*
	 * book 테이블에 도서 정보가 있는지 확인하고, 바로 insertbook과 insertbookHeld 까지 진행
	 */
	@Override
	public int selectBookCount(BookHeld bookHeld) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BookHeldMapper.selectBookCount", bookHeld);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("book 테이블에 존재 여부 검사에 실패했습니다.");
		}
		return result;
	}

	@Override
	public BookHeld selectBookId(BookHeld bookHeld) throws Exception {
		BookHeld result = null;
		try {
			result = sqlSession.selectOne("BookHeldMapper.selectBookId", bookHeld);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 book테이블의 id_book이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("book테이블의 id_book 조회에 실패했습니다.");
		}
		return result;
	}



	@Override
	public void insertBook(BookHeld bookHeld) throws Exception {
		try {
			int result = sqlSession.insert("BookHeldMapper.insertBook", bookHeld);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			// sqlSession.rollback();
			throw new Exception("Book 테이블에 저장된 도서 정보가 없습니다.");
		} catch (Exception e) {
			// sqlSession.rollback();
			logger.error(e.getLocalizedMessage());
			throw new Exception("Book 테이블 도서 등록에 실패했습니다.");
		} finally {
			// sqlSession.commit();
		}
	}




	@Override
	public void insertBookHeld(BookHeld bookHeld) throws Exception {
		try {
			int result = sqlSession.insert("BookHeldMapper.insertBookHeld", bookHeld);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			// sqlSession.rollback();
			throw new Exception("BookHeld 테이블에 저장된 도서 정보가 없습니다.");
		} catch (Exception e) {
			// sqlSession.rollback();
			logger.error(e.getLocalizedMessage());
			throw new Exception("BookHeld 테이블 도서 등록에 실패했습니다.");
		} finally {
			// sqlSession.commit();
		}
	}




	@Override
	public int selectBookHeldCount(BookHeld bookHeld) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BookHeldMapper.selectBookHeldCount", bookHeld);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("복본기호를 위한 bookHeld 테이블 존재 여부 검사에 실패했습니다.");
		}
		return result;
	}
	
	@Override
	public int selectBookHeldFirstCount(BookHeld bookHeld) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BookHeldMapper.selectBookHeldFirstCount", bookHeld);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("bookHeld 테이블 처음 등록 검사에 실패했습니다.");
		}
		return result;
	}




	@Override
	public int selectLastEmptyCopyCode(BookHeld bookHeld) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BookHeldMapper.selectLastEmptyCopyCode", bookHeld);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("비어있는 복본기호 또는 마지막 복본기호 조회에 실패했습니다.");
		}
		return result;
	}
	
	@Override
	public int selectFirstCopyCode(BookHeld bookHeld) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BookHeldMapper.selectFirstCopyCode", bookHeld);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("최초(최소) 복본기호 조회에 실패했습니다.");
		}
		return result;
	}
	
	@Override
	public int selectZeroCopyCodeCount(BookHeld bookHeld) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BookHeldMapper.selectZeroCopyCodeCount", bookHeld);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("0번 복본기호 조회에 실패했습니다.");
		}
		return result;
	}




	@Override
	public int selectEmptyLocalBarcode(BookHeld bookHeld) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BookHeldMapper.selectEmptyLocalBarcode", bookHeld);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("마지막 Empty LocalBarcode 조회에 실패했습니다.");
		}
		return result;
	}

	
	@Override
	public Integer selectFirstLocalBarcode(BookHeld bookHeld) throws Exception {
		Integer result = 0;
		try {
			result = sqlSession.selectOne("BookHeldMapper.selectFirstLocalBarcode", bookHeld);
			if(result == null) {
				result = 0;
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("첫번째 local barcode 조회에 실패했습니다.");
		}
		return result;
	}
	
	@Override
	public BookHeld selectLastLocalBarcode(BookHeld bookHeld) throws Exception {
		BookHeld result = null;
		try {
			result = sqlSession.selectOne("BookHeldMapper.selectLastLocalBarcode", bookHeld);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("마지막 barcode 조회에 실패했습니다.");
		}
		return result;
	}
	
	@Override
	public void selectDupCheckLocalBarcode(BookHeld bookHeld) throws Exception {
		try {
			int result = sqlSession.selectOne("BookHeldMapper.selectDupCheckLocalBarcode", bookHeld);
			// 일치하는 번호가 있다면 중복이 존재하는 것이다.
			if (result > 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("중복된 바코드 번호가 존재합니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("바코드 번호 중복검사에 실패했습니다.");
		}
	}


	@Override
	public int selectBookCountForPage(BookHeld bookHeld) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BookHeldMapper.selectBookCountForPage", bookHeld);
		} catch (Exception e) {
			throw new Exception("페이지를 위한 도서수 조회에 실패했습니다.");
		}
		return result;
	}


	@Override
	public void deleteBookHeldItem(BookHeld bookHeld) throws Exception {
		try {
			int result = sqlSession.delete("BookHeldMapper.deleteBookHeldItem", bookHeld);
			// 삭제된 데이터가 없다는 것은 WHERE절의 조건값이 맞지 않다는 의미.
			// 이 경우, 첫 번째 WHERE조건에서 사용되는 id값에 대한 데이터를 찾을 수 없다는 의미
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("이미 삭제된 도서입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("도서 삭제에 실패했습니다.");
		}
	}
	

	@Override
	public void updateBookHeldDiscard(BookHeld bookHeld) throws Exception {
		try {
			int result = sqlSession.update("BookHeldMapper.updateBookHeldDiscard", bookHeld);

			// 회원번호와 비밀번호가 일치하는 데이터가 0이라면, 비밀번호가 잘못된 상태
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("폐기된 도서가 없습니다.(바코드 불일치)");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("도서 폐기에 실패했습니다.");
		}
	}




	@Override
	public List<BookHeld> getBookHeldDiscardList(BookHeld bookHeld) throws Exception {
		List<BookHeld> result = null;
		try {
			result = sqlSession.selectList("BookHeldMapper.selectBookHeldDiscardList", bookHeld);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 폐기 도서가 없습니다.");
		} catch (Exception e) {
			throw new Exception("폐기 도서 조회에 실패했습니다.");
		}
		return result;
	}




	@Override
	public int selectBookDiscardCountForPage(BookHeld bookHeld) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BookHeldMapper.selectBookDiscardCountForPage", bookHeld);
		} catch (Exception e) {
			throw new Exception("(페이지) 폐기 도서수 조회에 실패했습니다.");
		}
		return result;
	}




	@Override
	public void updateBookHeldItem(BookHeld bookHeld) throws Exception {
		try {
			int result = sqlSession.update("BookHeldMapper.updateBookHeldItem", bookHeld);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("변경된 도서 데이터가 없습니다.");
		} catch (Exception e) {
			throw new Exception("도서 데이터 수정에 실패했습니다.");
		} 
		
	}




	@Override
	public List<BookHeld> getPrintBookHeldList(BookHeld bookHeld) throws Exception {
		List<BookHeld> result = null;
		try {
			result = sqlSession.selectList("BookHeldMapper.selectPrintBookHeldList", bookHeld);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("인쇄 도서 목록이 없습니다.");
		} catch (Exception e) {
			throw new Exception("인쇄 도서 목록 조회에 실패했습니다.");
		}
		return result;
	}




	@Override
	public void updateSortingIndex(BookHeld bookHeld) throws Exception {
		try {
			int result = sqlSession.update("BookHeldMapper.updateSortingIndex", bookHeld);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("변경된 도서 sortingIndex 데이터가 없습니다.");
		} catch (Exception e) {
			throw new Exception("sortingIndex 데이터 주입 실패했습니다.");
		}
	}




	@Override
	public void updateBookHeldTag(BookHeld bookHeld) throws Exception {
		try {
			int result = sqlSession.update("BookHeldMapper.updateBookHeldTag", bookHeld);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("변경된 도서 태그 데이터가 없습니다.");
		} catch (Exception e) {
			throw new Exception("태그 데이터 수정에 실패했습니다.");
		}
	}









}
