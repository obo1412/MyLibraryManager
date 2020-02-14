package com.gaimit.mlm.service;

import java.util.List;

import com.gaimit.mlm.model.Borrow;

/** 회원 관련 기능을 제공하기 위한 Service 계층 */
public interface BrwService {
	
	/**
	 * 
	 * @param borrow  도서관번호, 책id, 회원id
	 * @throws Exception
	 */
	public void insertBorrow(Borrow borrow) throws Exception;
	
	/**
	 * 
	 * @param borrow
	 * @return 대출중인 도서 목록 반환
	 * @throws Exception
	 */
	public List<Borrow> getBorrowList(Borrow borrow) throws Exception;
	
	/**
	 * 
	 * @param borrow bookCode 책의 고유코드로 borrow 테이블 대여 여부 검색하여
	 * @return 대여중인 그 책이 맞으면 그 대여정보 반환
	 * @throws Exception
	 */
	public Borrow getBorrowItemByBarcodeBook(Borrow borrow) throws Exception;
	
	/**
	 * 
	 * @param borrow
	 * @return
	 * @throws Exception
	 */
	public List<Borrow> getBorrowListByMbrId(Borrow borrow) throws Exception;
	
	/**
	 * 책 반납
	 * @param borrow idLibBrw IdBookBrw 두 조건
	 * @throws Exception
	 */
	public void updateBorrowEndDate(Borrow borrow) throws Exception;
	
	/**
	 * endDate 취소= null로 바꾸기
	 * @param borrow
	 * @throws Exception
	 */
	public void updateCancelBorrowEndDate(Borrow borrow) throws Exception;
	
	/**
	 * 반납된 책의 반납시간을 얻기 위함
	 * @param borrow idLibBrw IdBookBrw 두 조건
	 * @return 
	 * @throws Exception
	 */
	public Borrow getBorrowRtnd(Borrow borrow) throws Exception;
	
	/** 
	 * 책이 대여중인지 체크
	 * @param borrow inner join book 테이블의 idbookCode로 검사
	 * @throws Exception
	 */
	public void getBorrowCountByBarcodeBook(Borrow borrow) throws Exception;
	
	/**
	 * 오늘 날짜의 대여/반납 기록 호출
	 * @param borrow
	 * @return
	 * @throws Exception
	 */
	public List<Borrow> selectBorrowListToday(Borrow borrow) throws Exception;
	
	/**
	 * 현재 도서관에 남아있는 책 리스트 (대출중이 아닌 책들)
	 * @param borrow
	 * @return
	 * @throws Exception
	 */
	public List<Borrow> selectRemainedBookOnLibrary(Borrow borrow) throws Exception;
	
	/**
	 * 멤버의 현재 대출중인 도서 권수 확인
	 * @param borrow
	 * @return
	 * @throws Exception
	 */
	public int selectBrwBookCountByMemberId(Borrow borrow) throws Exception;
	
	/**
	 * 단일 멤버의 brw_limit 조회
	 * @param borrow
	 * @return
	 * @throws Exception
	 */
	public int selectBrwLimitByMemberId(Borrow borrow) throws Exception;
	
	
}
