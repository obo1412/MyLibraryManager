package com.gaimit.mlm.service;

import com.gaimit.mlm.model.TagSetting;

/** 회원 관련 기능을 제공하기 위한 Service 계층 */
public interface TagSettingService {
	/**
	 * 아이디 중복검사
	 * @param member - 아이디
	 * @throws Exception - 중복된 데이터인 경우 예외 발생함
	 */
	public TagSetting selectRollTagPositionValue(TagSetting tag) throws Exception;
	
	
	public void updateTagPosition(TagSetting tag) throws Exception;
}
