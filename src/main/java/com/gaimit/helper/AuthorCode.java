package com.gaimit.helper;

import java.util.HashMap;
import java.util.regex.Pattern;

public class AuthorCode {
	/**
	 * 주어진 문자열이 공백이거나 null인지를 검사
	 * 
	 * @param str
	 *            - 검사할 문자열
	 * @return boolean - 공백,null이 아닐 경우 true 리턴
	 */
	public boolean isValue(String str) {
		boolean result = false;
		if (str != null) {
			result = !str.trim().equals("");
		}
		return result;
	}
	/**
	 * 한글로만 구성되었는지에 대한 형식 검사
	 * 
	 * @param str
	 *            - 검사할 문자열
	 * @return boolean - 형식에 맞을 경우 true, 맞지 않을 경우 false
	 */
	public boolean isKor(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("^[ㄱ-ㅎ가-힣]*$", str);
		}
		return result;
	}
	
	
	public String titleFirstLetter(String str) {
		String result = null;
		str = str.replaceAll("\\(", "");
		String FirstLett = str.substring(0,1);
		
		if(isKor(FirstLett)) {
			result = toKoChosung(FirstLett);
		} else {
			result = FirstLett;
		}
		return result;
	}

	public String authorCodeGen(String str) {
		String result = null;
		
		// 문자열 좌우, 문자열 안의 공백 제거
		str = str.trim();
		str = str.replaceAll(" ", "");
		str = str.replaceAll("\\p{Z}", "");
		str = str.replaceAll("\\[", "");
		
		String[] arrAuth = str.split(":");
		if(arrAuth.length>1) {
			if(arrAuth[0].equals("글")||arrAuth[0].equals("저자")
				||arrAuth[0].equals("글쓴이")||arrAuth[0].equals("지은이")
				||arrAuth[0].equals("엮은이")||arrAuth[0].equals("원작")
				||arrAuth[0].equals("기획")
				||(arrAuth[0].indexOf("그림")>1)||(arrAuth[0].indexOf("사진")>1)
				||(arrAuth[0].indexOf("·")>1)) {
				arrAuth[1] = arrAuth[1].replaceAll("\\[", "");
				str = arrAuth[1];
			} else {
				arrAuth[0] = arrAuth[0].replaceAll("\\[", "");
				str = arrAuth[0];
			}
		} else {
			arrAuth[0] = arrAuth[0].replaceAll("\\[", "");
			str = arrAuth[0];
		}
		
		if(str.length()>1) {
			String FirstLet = str.substring(0, 1);
			String SecLet = str.substring(1, 2);
			String twoWord = FirstLet + SecLet;
			if(isKor(twoWord)) {
				String SecAlp = toKoJaso(SecLet);
				String SecCode = ac5th(SecAlp.substring(0, 1), SecAlp.substring(1, 2));
				result = FirstLet + SecCode;
			} else {
				result = FirstLet;
			}
		} else if(str.length()==1) {
			String FirstLet = str.substring(0, 1);
			result = FirstLet;
		}
		return result;
	}

	public static String ac5th(String str1, String str2) {
		HashMap<String, String> ac = new HashMap<String, String>();
		String result = "";

		ac.put("ㄱ", "1");
		ac.put("ㄲ", "1");
		ac.put("ㄴ", "19");
		ac.put("ㄷ", "2");
		ac.put("ㄸ", "2");
		ac.put("ㄹ", "29");
		ac.put("ㅁ", "3");
		ac.put("ㅂ", "4");
		ac.put("ㅃ", "4");
		ac.put("ㅅ", "5");
		ac.put("ㅆ", "5");
		ac.put("ㅇ", "6");
		ac.put("ㅈ", "7");
		ac.put("ㅉ", "7");
		ac.put("ㅊ", "8");
		ac.put("ㅋ", "87");
		ac.put("ㅌ", "88");
		ac.put("ㅍ", "89");
		ac.put("ㅎ", "9");

		if (str1.equals("ㅊ")) {
			ac.put("ㅏ", "2");
			ac.put("ㅐ", "2");
			ac.put("ㅑ", "2");
			ac.put("ㅒ", "2");

			ac.put("ㅓ", "3");
			ac.put("ㅔ", "3");
			ac.put("ㅕ", "3");
			ac.put("ㅖ", "3");

			ac.put("ㅗ", "4");
			ac.put("ㅘ", "4");
			ac.put("ㅙ", "4");
			ac.put("ㅚ", "4");
			ac.put("ㅛ", "4");

			ac.put("ㅜ", "5");
			ac.put("ㅝ", "5");
			ac.put("ㅞ", "5");
			ac.put("ㅟ", "5");
			ac.put("ㅠ", "5");
			ac.put("ㅡ", "5");
			ac.put("ㅢ", "5");

			ac.put("ㅣ", "6");

			result += ac.get(str1);
			result += ac.get(str2);
		} else if (str1.equals("ㄴ") || str1.equals("ㄹ") || str1.equals("ㅋ") || str1.equals("ㅌ") || str1.equals("ㅍ")) {
			result += ac.get(str1);
		} else {
			ac.put("ㅏ", "2");

			ac.put("ㅐ", "3");
			ac.put("ㅑ", "3");
			ac.put("ㅒ", "3");

			ac.put("ㅓ", "4");
			ac.put("ㅔ", "4");
			ac.put("ㅕ", "4");
			ac.put("ㅖ", "4");

			ac.put("ㅗ", "5");
			ac.put("ㅘ", "5");
			ac.put("ㅙ", "5");
			ac.put("ㅚ", "5");
			ac.put("ㅛ", "5");

			ac.put("ㅜ", "6");
			ac.put("ㅝ", "6");
			ac.put("ㅞ", "6");
			ac.put("ㅟ", "6");
			ac.put("ㅠ", "6");

			ac.put("ㅡ", "7");
			ac.put("ㅢ", "7");
			ac.put("ㅣ", "8");

			result += ac.get(str1);
			result += ac.get(str2);
		}
		return result;
	}

	// 일반 분해
	private final static char[] KO_INIT_S = { 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ',
			'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' }; // 19

	private final static char[] KO_INIT_M = { 'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ',
			'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ' }; // 21
	private final static char[] KO_INIT_E = { 0, 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ',
			'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' }; // 28

	// 완전 분해
	private final static char[][] KO_ATOM_S = { { 'ㄱ' }, { 'ㄱ', 'ㄱ' }, { 'ㄴ' }, { 'ㄷ' }, { 'ㄷ', 'ㄷ' }, { 'ㄹ' }, { 'ㅁ' },
			{ 'ㅂ' }, { 'ㅂ', 'ㅂ' }, { 'ㅅ' }, { 'ㅅ', 'ㅅ' }, { 'ㅇ' }, { 'ㅈ' }, { 'ㅈ', 'ㅈ' }, { 'ㅊ' }, { 'ㅋ' }, { 'ㅌ' },
			{ 'ㅍ' }, { 'ㅎ' } };
	private final static char[][] KO_ATOM_M = { { 'ㅏ' }, { 'ㅐ' }, { 'ㅑ' }, { 'ㅒ' }, { 'ㅓ' }, { 'ㅔ' }, { 'ㅕ' }, { 'ㅖ' },
			{ 'ㅗ' }, { 'ㅗ', 'ㅏ' }, { 'ㅗ', 'ㅐ' }, { 'ㅗ', 'ㅣ' }, { 'ㅛ' }, { 'ㅜ' }, { 'ㅜ', 'ㅓ' }, { 'ㅜ', 'ㅔ' },
			{ 'ㅜ', 'ㅣ' }, { 'ㅠ' }, { 'ㅡ' }, { 'ㅡ', 'ㅣ' }, { 'ㅣ' } };
	private final static char[][] KO_ATOM_E = { {}, { 'ㄱ' }, { 'ㄱ', 'ㄱ' }, { 'ㄱ', 'ㅅ' }, { 'ㄴ' }, { 'ㄴ', 'ㅈ' },
			{ 'ㄴ', 'ㅎ' }, { 'ㄷ' }, { 'ㄹ' }, { 'ㄹ', 'ㄱ' }, { 'ㄹ', 'ㅁ' }, { 'ㄹ', 'ㅂ' }, { 'ㄹ', 'ㅅ' }, { 'ㄹ', 'ㅌ' },
			{ 'ㄹ', 'ㅍ' }, { 'ㄹ', 'ㅎ' }, { 'ㅁ' }, { 'ㅂ' }, { 'ㅂ', 'ㅅ' }, { 'ㅅ' }, { 'ㅅ', 'ㅅ' }, { 'ㅇ' }, { 'ㅈ' },
			{ 'ㅊ' }, { 'ㅋ' }, { 'ㅌ' }, { 'ㅍ' }, { 'ㅎ' } };
	// 쌍자음이나 이중모음을 분해
	private final static char[][] KO_ATOM_P = { { 'ㄱ' }, { 'ㄱ', 'ㄱ' }, { 'ㄱ', 'ㅅ' }, { 'ㄴ' }, { 'ㄴ', 'ㅈ' },
			{ 'ㄴ', 'ㅎ' }, { 'ㄷ' }, { 'ㄸ' }, { 'ㄹ' }, { 'ㄹ', 'ㄱ' }, { 'ㄹ', 'ㅁ' }, { 'ㄹ', 'ㅂ' }, { 'ㄹ', 'ㅅ' },
			{ 'ㄹ', 'ㄷ' }, { 'ㄹ', 'ㅍ' }, { 'ㄹ', 'ㅎ' }, { 'ㅁ' }, { 'ㅂ' }, { 'ㅂ', 'ㅂ' }, { 'ㅂ', 'ㅅ' }, { 'ㅅ' },
			{ 'ㅅ', 'ㅅ' }, { 'ㅇ' }, { 'ㅈ' }, { 'ㅈ', 'ㅈ' }, { 'ㅊ' }, { 'ㅋ' }, { 'ㅌ' }, { 'ㅍ' }, { 'ㅎ' }, { 'ㅏ' }, { 'ㅐ' },
			{ 'ㅑ' }, { 'ㅒ' }, { 'ㅓ' }, { 'ㅔ' }, { 'ㅕ' }, { 'ㅖ' }, { 'ㅗ' }, { 'ㅗ', 'ㅏ' }, { 'ㅗ', 'ㅐ' }, { 'ㅗ', 'ㅣ' },
			{ 'ㅛ' }, { 'ㅜ' }, { 'ㅜ', 'ㅓ' }, { 'ㅜ', 'ㅔ' }, { 'ㅜ', 'ㅣ' }, { 'ㅠ' }, { 'ㅡ' }, { 'ㅡ', 'ㅣ' }, { 'ㅣ' } };

	/** 한글부분을 초성으로 교체합니다. */
	public static String toKoChosung(String text) {
		if (text == null) {
			return null;
		}

		// 한글자가 한글자와 그대로 대응됨.
		// 때문에 기존 텍스트를 토대로 작성된다.
		char[] rv = text.toCharArray();
		char ch;

		for (int i = 0; i < rv.length; i++) {
			ch = rv[i];
			if (ch >= '가' && ch <= '힣') {
				rv[i] = KO_INIT_S[(ch - '가') / 588]; // 21 * 28
			}
		}

		return new String(rv);
	}

	/**
	 * 한글부분을 자소로 분리합니다. <br>
	 * 많다 = [ㅁㅏㄶㄷㅏ]
	 */
	public static String toKoJaso(String text) {
		if (text == null) {
			return null;
		}
		// StringBuilder의 capacity가 0으로 등록되는 것 방지.
		if (text.length() == 0) {
			return "";
		}

		// 한글자당 최대 3글자가 될 수 있다.
		// 추가 할당 없이 사용하기위해 capacity 를 최대 글자 수 만큼 지정하였다.
		StringBuilder rv = new StringBuilder(text.length() * 3);

		for (char ch : text.toCharArray()) {
			if (ch >= '가' && ch <= '힣') {
				// 한글의 시작부분을 구함
				int ce = ch - '가';
				// 초성을 구함
				rv.append(KO_INIT_S[ce / (588)]); // 21 * 28
				// 중성을 구함
				rv.append(KO_INIT_M[(ce = ce % (588)) / 28]); // 21 * 28
				// 종성을 구함
				if ((ce = ce % 28) != 0) {
					rv.append(KO_INIT_E[ce]);
				}
			} else {
				rv.append(ch);
			}
		}

		return rv.toString();
	}

	/**
	 * 한글부분을 자소로 완전 분리합니다. <br>
	 * 많다 = [ㅁㅏㄴㅎㄷㅏ]
	 */
	public static String toKoJasoAtom(String text) {
		if (text == null) {
			return null;
		}
		// StringBuilder의 capacity가 0으로 등록되는 것 방지.
		if (text.length() == 0) {
			return "";
		}

		// 한글자당 최대 6글자가 될 수 있다.
		// 추가 할당 없이 사용하기위해 capacity 를 최대 글자 수 만큼 지정하였다.
		StringBuilder rv = new StringBuilder(text.length() * 6);

		for (char ch : text.toCharArray()) {
			if (ch >= '가' && ch <= '힣') {
				// 한글의 시작부분을 구함
				int ce = ch - '가';
				// 초성을 구함
				rv.append(KO_ATOM_S[ce / (588)]); // 21 * 28
				// 중성을 구함
				rv.append(KO_ATOM_M[(ce = ce % (588)) / 28]); // 21 * 28
				// 종성을 구함
				if ((ce = ce % 28) != 0) {
					rv.append(KO_ATOM_E[ce]);
				}
			}
			// 쌍자음과 이중모음 분리
			else if (ch >= 'ㄱ' && ch <= 'ㅣ') {
				rv.append(KO_ATOM_P[ch - 'ㄱ']);
			} else {
				rv.append(ch);
			}
		}

		return rv.toString();
	}
}
