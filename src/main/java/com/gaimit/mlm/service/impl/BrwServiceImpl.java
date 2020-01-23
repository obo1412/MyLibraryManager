package com.gaimit.mlm.service.impl;

import java.util.List;

/*import java.util.List;*/

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaimit.mlm.model.Borrow;
import com.gaimit.mlm.service.BrwService;

//--> import org.springframework.stereotype.Service; 
@Service
public class BrwServiceImpl implements BrwService {
	
	/** 처리 결과를 기록할 Log4J 객체 생성 */
	// --> import org.slf4j.Logger;
	// --> import org.slf4j.LoggerFactory;
	private static Logger logger = LoggerFactory.getLogger(BrwServiceImpl.class);

	/** MyBatis */
	// --> import org.springframework.beans.factory.annotation.Autowired;
	// --> import org.apache.ibatis.session.SqlSession
	@Autowired
	SqlSession sqlSession;

	@Override
	public void insertBorrow(Borrow borrow) throws Exception {
		// 대여중인 도서인지 중복검사
		//getBorrowCountByBookCode(borrow);

		// 데이터 저장처리 = 가입
		// not null로 설정된 값이 설정되지 않았다면 예외 발생됨.
		try {
			int result = sqlSession.insert("BorrowMapper.insertBrw", borrow);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			// sqlSession.rollback();
			throw new Exception("저장된 도서 정보가 없습니다.");
		} catch (Exception e) {
			// sqlSession.rollback();
			logger.error(e.getLocalizedMessage());
			throw new Exception("도서 대여에 실패했습니다.");
		} finally {
			// sqlSession.commit();
		}
		
	}

	@Override
	public List<Borrow> getBorrowList(Borrow borrow) throws Exception {
		List<Borrow> result = null;
		try {
			result = sqlSession.selectList("BorrowMapper.selectBorrowList", borrow);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 대출중인 도서가 없습니다.");
		} catch (Exception e) {
			throw new Exception("대출중 도서 데이터 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public Borrow getBorrowItemByBookCode(Borrow borrow) throws Exception {
		Borrow result = null;
		try {
			result = sqlSession.selectOne("BorrowMapper.selectBorrowItemByBookCode", borrow);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 도서대여 정보가 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("도서대여 조회에 실패했습니다.");
		}
		
		return result;
	}

	@Override
	public List<Borrow> getBorrowListByMbrId(Borrow borrow) throws Exception {
		List<Borrow> result = null;
		try {
			result = sqlSession.selectList("BorrowMapper.selectBorrowListByMbrId", borrow);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("회원으로 조회된 대출중인 도서가 없습니다.");
		} catch (Exception e) {
			throw new Exception("대출중 도서 데이터 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public void updateBorrowEndDate(Borrow borrow) throws Exception {
		try {
			int result = sqlSession.update("BorrowMapper.updateBorrowEndDate", borrow);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			// sqlSession.rollback();
			throw new Exception("변경된 도서대여 정보가 없습니다.");
		} catch (Exception e) {
			// sqlSession.rollback();
			logger.error(e.getLocalizedMessage());
			throw new Exception("도서 반납에 실패했습니다.");
		} finally {
			// sqlSession.commit();
		}		
	}

	@Override
	public Borrow getBorrowRtnd(Borrow borrow) throws Exception {
		Borrow result = null;
		try {
			result = sqlSession.selectOne("BorrowMapper.selectBorrowRtnd", borrow);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("반납된 도서대여 정보가 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("도서반납 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public void getBorrowCountByBookCode(Borrow borrow) throws Exception {
		try {
			int result = sqlSession.selectOne("BorrowMapper.selectBorrowCountByBookCode", borrow);
			System.out.println("************************"+result);
			//중복된 데이터가 존재한다면?
			if(result > 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("이미 대여중인 도서 입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("도서 대여 중복검사에 실패했습니다.");
		}
	}

	@Override
	public List<Borrow> selectBorrowListToday(Borrow borrow) throws Exception {
		List<Borrow> result = null;
		try {
			result = sqlSession.selectList("BorrowMapper.selectBorrowListToday", borrow);
		} catch (Exception e) {
			throw new Exception("금일의 대출/반납 데이터 조회에 실패했습니다.");
		}
		return result;
	}

}
