package com.gaimit.mlm.service;

import java.util.List;

import com.gaimit.mlm.model.BbsFile;

public interface BbsFileService {
	/**
	 * 파일 정보를 저장한다.
	 * @param file - 파일 데이터
	 * @throws Exception
	 */
	public void insertFile(BbsFile file) throws Exception;
	
	/**
	 * 하나의 게시물에 종속된 파일 목록을 조회한다.
	 * @param file - 게시물 일련번호를 저장하고 있는 JavaBeans
	 * @return 파일데이터 컬렉션
	 * @throws Exeption
	 */
	public List<BbsFile> selectFileList(BbsFile file) throws Exception;
	
	/**
	 * 하나의 게시물에 종속된 파일 목록을 삭제한다.
	 * @param document
	 * @throws Exception
	 */
	public void deleteFileAll(BbsFile file) throws Exception;
	
	/**
	 * 하나의 단일 파일에 대한 정보를 조회한다.
	 * @param document
	 * @return BbsFile - 저장된 경로 정보
	 * @throws Exception
	 */
	public BbsFile selectFile(BbsFile file) throws Exception;
	
	/**
	 * 하나의 단일 파일 정보를 삭제한다.
	 * @param document
	 * @throws Exception
	 */
	public void deleteFile(BbsFile file) throws Exception;
	
	/**
	 * 도서일괄등록을 위한 txt 파일 저장
	 * @param file
	 * @throws Exception
	 */
	public void insertRegBookFile(BbsFile file) throws Exception;
	
	/**
	 * 도서일괄등록을 위한 txt 파일 목록
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public List<BbsFile> selectRegBookFileListToday(BbsFile file) throws Exception;
	
}
