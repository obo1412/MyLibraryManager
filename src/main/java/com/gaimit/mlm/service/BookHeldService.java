package com.gaimit.mlm.service;

import java.util.List;

import com.gaimit.mlm.model.BookHeld;

/** 회원 관련 기능을 제공하기 위한 Service 계층 */
public interface BookHeldService {
		
	/**
	 * 책 목록조회(비밀정보를 제외한 모든 정보)
	 * @param Book - 책의 id 조회
	 * @throws Exception
	 */
	public List<BookHeld> getBookHeldList(BookHeld bookHeld) throws Exception;
	
	/**
	 * 
	 * @param book id_book
	 * @return book 테이블의 컬럼 전체
	 * @throws Exception
	 */
	public BookHeld getBookHelditem(BookHeld bookHeld) throws Exception;
	
	/**
	 * 
	 * @param bookHeld book 테이블 내에 isbn13 존재 여부 판단
	 * @throws Exception
	 */
	public int selectBookCount(BookHeld bookHeld) throws Exception;
	
	public BookHeld selectBookId(BookHeld bookHeld) throws Exception;
	
	public void insertBook(BookHeld bookHeld) throws Exception;
	
	
	public void insertBookHeld(BookHeld bookHeld) throws Exception;
	
	
	/**
	 * 복본기호를 체크하기 위하여
	 * book_id_book 인자를 이용하여 bookHeld안에 id가 또 있는지 검사
	 * @param bookHeld
	 * @return
	 * @throws Exception
	 */
	public int selectBookHeldCount(BookHeld bookHeld) throws Exception;
	
	/**
	 * lib 내에서 첫등록 체크
	 * @param bookHeld
	 * @return
	 * @throws Exception
	 */
	public int selectBookHeldFirstCount(BookHeld bookHeld) throws Exception;
	
	/**
	 * copy code 생성용
	 * @param bookHeld
	 * @return
	 * @throws Exception
	 */
	public int selectLastCopyCode(BookHeld bookHeld) throws Exception;
	
	/**
	 * local id barcode 마지막 구하기
	 * @param bookHeld
	 * @return
	 * @throws Exception
	 */
	public int selectEmptyLocalBarcode(BookHeld bookHeld) throws Exception;
	
	/**
	 * 바코드 첫번째 번호 1번이 아닌 경우, 1번을 만들어주고 1번이 있으면 사이번호를 위 함수로 찾는다
	 * @param bookHeld
	 * @return mysql에서 얻은 결과값이 null 수도 있다.
	 * 	그러나 int에는 null을 체크할 수 없으므로, Integer를 사용했다.
	 * @throws Exception
	 */
	public Integer selectFirstLocalBarcode(BookHeld bookHeld) throws Exception;
	
	/**
	 * 
	 * @param bookHeld
	 * @return
	 * @throws Exception
	 */
	public BookHeld selectLastLocalBarcode(BookHeld bookHeld) throws Exception;
	
	/**
	 * 바코드 중복체크를 위한 함수
	 * @param bookHeld
	 * @throws Exception
	 */
	public void selectDupCheckLocalBarcode(BookHeld bookHeld) throws Exception;
	
	/**
	 * 페이지를 위한 count
	 * @param bookHeld
	 * @return
	 * @throws Exception
	 */
	public int selectBookCountForPage(BookHeld bookHeld) throws Exception;
	
	/**
	 * 도서 폐기 반환값 없음
	 * @param bookHeld
	 * @throws Exception
	 */
	public void updateBookHeldDiscard(BookHeld bookHeld) throws Exception;
	
	/**
	 * 폐기된 도서 목록
	 * @param bookHeld 도서관 번호
	 * @return
	 * @throws Exception
	 */
	public List<BookHeld> getBookHeldDiscardList(BookHeld bookHeld) throws Exception;
	
	/**
	 * 폐기도서 카운트 페이지 목록
	 * @param bookHeld
	 * @return
	 * @throws Exception
	 */
	public int selectBookDiscardCountForPage(BookHeld bookHeld) throws Exception;
	
	/**
	 * 도서 정보 수정
	 * @param bookHeld
	 * @throws Exception
	 */
	public void updateBookHeldItem(BookHeld bookHeld) throws Exception;
	
	/**
	 * 프린트 리스트
	 * @param bookHeld
	 * @return
	 * @throws Exception
	 */
	public List<BookHeld> getPrintBookHeldList(BookHeld bookHeld) throws Exception;
}
