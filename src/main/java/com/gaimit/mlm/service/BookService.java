package com.gaimit.mlm.service;

import java.util.List;

import com.gaimit.mlm.model.Book;

/** 회원 관련 기능을 제공하기 위한 Service 계층 */
public interface BookService {
		
	/**
	 * 책 목록조회(비밀정보를 제외한 모든 정보)
	 * @param Book - 책의 id 조회
	 * @throws Exception
	 */
	public List<Book> getBookList(Book book) throws Exception;
	
	
}
