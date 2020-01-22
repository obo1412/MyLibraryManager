package com.gaimit.mlm.service;

import java.util.List;

import com.gaimit.mlm.model.Member;

/** 회원 관련 기능을 제공하기 위한 Service 계층 */
public interface MemberService {
	/**
	 * 아이디 중복검사
	 * @param member - 아이디
	 * @throws Exception - 중복된 데이터인 경우 예외 발생함
	 */
	public void selectUserIdCount(Member member) throws Exception;
	
	/**
	 * 이메일 중복검사
	 * @param member - 이메일
	 * @throws Exception - 중복된 데이터인 경우 예외 발생함
	 */
	public void selectEmailCount(Member member) throws Exception;
	
	
	/**
	 * 마지막 등록 id 조회
	 * @param member - 최종 pk키 확인하기
	 * @throws Exception
	 */
	public int selectLastJoinedId() throws Exception;
	
	
	/**
	 * 회원가입(아이디,이메일 중복검사 후 가입처리)
	 * @param member - 일련번호, 가입일시,변경일시를 제외한 모든 정보
	 * @throws Exception
	 */
	public void insertMember(Member member) throws Exception;
	
	/**
	 * 회원목록조회(비밀정보를 제외한 모든 정보)
	 * @param member - 회원 id 조회
	 * @throws Exception
	 */
	public List<Member> getMemberList(Member member) throws Exception;
	
	/**
	 * 도서관별 회원목록조회(모든 정보)
	 * @param member - 도서관 id_lib 조회
	 * @throws Exception
	 */
	public List<Member> getMemberListByLib(Member member) throws Exception;
	
	/**
	 * 
	 * @param member 이름과 도서관번호
	 * @return 이름과 도서관 번호가 일치하는 회원 목록
	 * @throws Exception
	 */
	public List<Member> getMemberListByLibAndName(Member member) throws Exception;
	
	/**
	 * 회원수 조회(아이디)
	 * @param member - 회원 id 개수 조회
	 * @throws Exception
	 */
	public int getMemberCount(Member member) throws Exception;
	
	/**
	 * 
	 * @param member 회원 이름과 도서관id
	 * @return
	 * @throws Exception
	 */
	public int getMemberCountByNameAndIdLib(Member member) throws Exception;
	
	/**
	 * 로그인
	 * @param member - 아이디 비밀번호
	 * @return 회원정보
	 * @throws Exception
	 */
	/*public Member selectLoginInfo(Member member) throws Exception;*/
	
	/**
	 * 비밀번호 변경
	 * @param member - 이메일주소. 비밀번호
	 * @throws Exception
	 */
	public void updateMemberPasswordByEmail(Member member) throws Exception;
	
	/**
	 * 비밀번호 검사
	 * @param member
	 * @throws Exception
	 */
	public void selectMemberPasswordCount(Member member) throws Exception;
	
	/**
	 * 회원탈퇴
	 * @param member - 회원번호, 비밀번호
	 * @throws Exception
	 */
	public void deleteMember(Member member) throws Exception;
	
	/**
	 * 회원정보 수정
	 * @param member
	 * @throws Exception
	 */
	public void updateMember(Member member) throws Exception;
	
	/**
	 * 일련번호에 의한 회원정보 조회
	 * @param member
	 * @throws Exception
	 */
	public Member selectMember(Member member) throws Exception;
	
	/**
	 * 고객 등급 목록 조회
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public List<Member> selectGrade(Member member) throws Exception;
	
	/**
	 * idLib와 phone으로 전화번호 중복 검사
	 * @param member
	 * @throws Exception
	 */
	public void selectPhoneCount(Member member) throws Exception;
	
	/**
	 * idLib와 gradeId를 이용하여 등급 아이템 조회
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public Member getGradeItem(Member member) throws Exception;
	
	/**
	 * gradeId와 idLib를 이용하여 등급 수정
	 * @param member
	 * @throws Exception
	 */
	public void updateGrade(Member member) throws Exception;
	
	/**
	 * grade 추가
	 * @param member
	 * @throws Exception
	 */
	public void insertGrade(Member member) throws Exception;
	
	/**
	 * grade 이름 중복검사
	 * @param member
	 * @throws Exception
	 */
	public void selectGradeNameCount(Member member) throws Exception;
}
