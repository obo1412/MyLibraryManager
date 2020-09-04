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
			throw new Exception("도서 대출에 실패했습니다.");
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
	public Borrow getBorrowItemByBarcodeBook(Borrow borrow) throws Exception {
		Borrow result = null;
		try {
			result = sqlSession.selectOne("BorrowMapper.selectBorrowItemByBarcodeBook", borrow);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 도서대출 정보가 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("도서대출 조회에 실패했습니다.");
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
			throw new Exception("변경된 도서대출 정보가 없습니다.");
		} catch (Exception e) {
			// sqlSession.rollback();
			logger.error(e.getLocalizedMessage());
			throw new Exception("도서 반납에 실패했습니다.");
		} finally {
			// sqlSession.commit();
		}		
	}
	
	@Override
	public void updateCancelBorrowEndDate(Borrow borrow) throws Exception {
		try {
			int result = sqlSession.update("BorrowMapper.updateCancelBorrowEndDate", borrow);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			// sqlSession.rollback();
			throw new Exception("반납취소된 도서대출 정보가 없습니다.");
		} catch (Exception e) {
			// sqlSession.rollback();
			logger.error(e.getLocalizedMessage());
			throw new Exception("도서 반납 취소에 실패했습니다.");
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
			throw new Exception("반납된 도서 정보가 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("도서반납 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public void getBorrowCountByBarcodeBook(Borrow borrow) throws Exception {
		try {
			int result = sqlSession.selectOne("BorrowMapper.selectBorrowCountByBarcodeBook", borrow);
			//중복된 데이터가 존재한다면?
			if(result > 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("이미 대출중인 도서입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("도서 대출 중복검사에 실패했습니다.");
		}
	}

	@Override
	public List<Borrow> selectBorrowListToday(Borrow borrow) throws Exception {
		List<Borrow> result = null;
		try {
			result = sqlSession.selectList("BorrowMapper.selectBorrowListToday", borrow);
		} catch (Exception e) {
			throw new Exception("오늘의 대출/반납 데이터 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public List<Borrow> selectRemainedBookOnLibrary(Borrow borrow) throws Exception {
		List<Borrow> result = null;
		try {
			result = sqlSession.selectList("BorrowMapper.selectRemainedBookOnLibrary", borrow);
		} catch (Exception e) {
			throw new Exception("보유중인(대출중이 아닌) 도서목록 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public int selectBrwBookCountByMemberId(Borrow borrow) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BorrowMapper.selectBrwBookCountByMemberId", borrow);
		} catch (Exception e) {
			throw new Exception("대출중인 도서 수 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public int selectBrwLimitByMemberId(Borrow borrow) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BorrowMapper.selectBrwLimitByMemberId", borrow);
		} catch (Exception e) {
			throw new Exception("회원의 최대 대여가능 권 수 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public List<Borrow> selectReturnListToday(Borrow borrow) throws Exception {
		List<Borrow> result = null;
		try {
			result = sqlSession.selectList("BorrowMapper.selectReturnListToday", borrow);
		} catch (Exception e) {
			throw new Exception("오늘 반납 예정 도서 데이터 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public int selectBorrowListCount(Borrow borrow) throws Exception {
		int result = 0;

		try {
			// 게시물 수가 0건인 경우도 있으므로, 
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("BorrowMapper.selectBorrowListCount", borrow);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("대출중인 도서count 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public int selectOverDueCountByMemberId(Borrow borrow) throws Exception {
		int result = 0;
		try {
			// 연체된 도서가 없는 경우도 있으니, 0 체크하지 않는다.
			result = sqlSession.selectOne("BorrowMapper.selectOverDueCountByMemberId", borrow);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("반납되지 않은 연체도서수 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public Borrow selectRestrictDate(Borrow borrow) throws Exception {
		Borrow result = null;
		try {
			//연체 도서가 없는 경우 null 정상상태
			result = sqlSession.selectOne("BorrowMapper.selectRestrictDate", borrow);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("반납된 연체도서 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public int selectBorrowCountDeleteBookHeldId(Borrow borrow) throws Exception {
		int result = 0;
		try {
			// 0일 경우 삭제로 넘어가고, 0보다 클 경우 폐기 처리를 위하여 에러 발생
			result = sqlSession.selectOne("BorrowMapper.selectBorrowCountDeleteBookHeldId", borrow);
			if(result > 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("대출내역이 존재하는 도서입니다 폐기 처리로 진행하세요.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("도서 삭제를 위한 대출내역 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public List<Borrow> selectBorrowMemberCountThisMonth(Borrow borrow) throws Exception {
		List<Borrow> result = null;
		try {
			result = sqlSession.selectList("BorrowMapper.selectBorrowMemberCountThisMonth", borrow);
		} catch (Exception e) {
			throw new Exception("이번 달 회원당 도서대출수 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public List<Borrow> selectBorrowBookCountThisMonth(Borrow borrow) throws Exception {
		List<Borrow> result = null;
		try {
			result = sqlSession.selectList("BorrowMapper.selectBorrowBookCountThisMonth", borrow);
		} catch (Exception e) {
			throw new Exception("이번 달 도서 대출 통계 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public Borrow selectBorrowItemByBookHeldId(Borrow borrow) throws Exception {
		Borrow result = null;
		try {
			result = sqlSession.selectOne("BorrowMapper.selectBorrowItemByBookHeldId", borrow);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("도서대출 조회(BookHeldId)에 실패했습니다.");
		}
		return result;
	}

	@Override
	public List<Borrow> selectMemberListForBorrow(Borrow borrow) throws Exception {
		List<Borrow> result = null;
		try {
			result = sqlSession.selectList("BorrowMapper.selectMemberListForBorrow", borrow);
		} catch (Exception e) {
			throw new Exception("도서 대출을 위한 회원 목록 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public Borrow selectMemberItemByMemberId(Borrow borrow) throws Exception {
		Borrow result = null;
		try {
			result = sqlSession.selectOne("BorrowMapper.selectMemberItemByMemberId", borrow);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("회원키 값으로 회원 조회에 실패했습니다.(도서 반납 처리 이후 절차)");
		}
		return result;
	}

}
