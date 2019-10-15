package com.gaimit.helper;


/** 
 * 湲곕낯�쟻�씤 怨듯넻 湲곕뒫�뱾�쓣 臾띠뼱 �넃�� �겢�옒�뒪
 */
public class Util {

	
	/**
	 * 踰붿쐞瑜� 媛뽯뒗 �옖�뜡媛믪쓣 �깮�꽦�븯�뿬 由ы꽩�븯�뒗 硫붿꽌�뱶
	 * @param min 踰붿쐞 �븞�뿉�꽌�쓽 理쒖냼媛�
	 * @param max 踰붿쐞 �븞�뿉�꽌�쓽 理쒕�媛�
	 * @return �븞�뿉�꽌�쓽 �옖�뜡媛�
	 */
	public int random(int min, int max) {
		int num = (int) ((Math.random()*(max - min + 1)) + min);
		return num;
	}
	
	
	public String getRandomPassword() {
		//由ы꽩�븷 臾몄옄�뿴
		String password = "";
		
		// A~Z, a~z, 1~0
		String words = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		//湲��옄湲몄씠
		int words_len = words.length();
		
		for(int i=0; i<8; i++) {
			//�옖�뜡�븳 �쐞移섏뿉�꽌 �븳 湲��옄瑜� 異붿텧�븳�떎.
			int random = random(0, words_len -1);
			String c = words.substring(random,random +1);
			
			//異붿텧�븳 湲��옄瑜� 誘몃━ 以�鍮꾪븳 蹂��닔�뿉 異붽��븳�떎.
			password += c;
		}
		
		return password;
	}
}
