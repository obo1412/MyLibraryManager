package com.gaimit.mlm.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaimit.mlm.model.Book;
import com.gaimit.mlm.service.BookService;

//--> import org.springframework.stereotype.Service; 
@Service
public class BookServiceImpl implements BookService {
	
	/** 처리 결과를 기록할 Log4J 객체 생성 */
	// --> import org.slf4j.Logger;
	// --> import org.slf4j.LoggerFactory;
	/*private static Logger logger = LoggerFactory.getLogger(LibraryServiceImpl.class);*/

	/** MyBatis */
	// --> import org.springframework.beans.factory.annotation.Autowired;
	// --> import org.apache.ibatis.session.SqlSession
	@Autowired
	SqlSession sqlSession;


	
	
	@Override
	public List<Book> getBookList(Book book) throws Exception {
		List<Book> result = null;
		try {
			result = sqlSession.selectList("BookMapper.selectBookList", book);
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


}
