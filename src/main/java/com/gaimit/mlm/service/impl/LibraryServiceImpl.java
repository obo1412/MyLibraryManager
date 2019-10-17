package com.gaimit.mlm.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaimit.mlm.model.Library;
import com.gaimit.mlm.service.LibraryService;

//--> import org.springframework.stereotype.Service; 
@Service
public class LibraryServiceImpl implements LibraryService {
	
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
	public List<Library> getLibraryList(Library library) throws Exception {
		List<Library> result = null;
		try {
			result = sqlSession.selectList("LibraryMapper.selectLibraryList", library);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			throw new Exception("데이터 조회에 실패했습니다.");
		}
		return result;
	}


}
