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
}
