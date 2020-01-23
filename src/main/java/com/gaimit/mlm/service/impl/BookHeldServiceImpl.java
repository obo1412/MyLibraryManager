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
			throw new Exception("조회된 책이 없습니다.");
		} catch (Exception e) {
			throw new Exception("데이터 조회에 실패했습니다.");
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
			throw new Exception("조회된 도서 정보가 없습니다앙.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("도서 정보 조회에 실패했습니다.");
		}
		return result;
	}



	/*
	 * book 테이블에 책 정보가 있는지 확인하고, 바로 insertbook과 insertbookHeld 까지 진행
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
			throw new Exception("조회된 id_book이 없습니다앙.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("도서 id_book 조회에 실패했습니다.");
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
	public int selectLastCopyCode(BookHeld bookHeld) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BookHeldMapper.selectLastCopyCode", bookHeld);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("마지막 복본기호 조회를 실패했습니다.");
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
			throw new Exception("LastLocalBarcode 조회에 실패했습니다.");
		}
		return result;
	}





}
