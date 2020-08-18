package com.gaimit.helper;

/**
 * 기본적인 공통 기능들을 묶어 놓은 클래스
 */
public class Util {
	// ----------- 싱글톤 객체 생성 시작 -----------
	private static Util current = null;

	public static Util getInstance() {
		if (current == null) {
			current = new Util();
		}
		return current;
	}

	public static void freeInstance() {
		current = null;
	}

	private Util() {
		super();
	}
	// ----------- 싱글톤 객체 생성 끝 -----------

	/**
	 * 범위를 갖는 랜덤값을 생성하여 리턴하는 메서드 
	 * @param min - 범위 안에서의 최소값
	 * @param max - 범위 안에서의 최대값
	 * @return min~max 안에서의 랜덤값
	 */
	public int random(int min, int max) {
		int num = (int) ((Math.random() * (max - min + 1)) + min);
		return num;
	}

	/**
	 * 랜덤한 비밀번호를 생성하여 리턴한다.
	 * @return String
	 */
	public String getRandomPassword() {
		// 리턴할 문자열
		String password = "";

		// A~Z, a~z, 1~0 
		String words = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		// 글자길이
		int words_len = words.length();

		for (int i = 0; i < 8; i++) {
			// 랜덤한 위치에서 한 글자를 추출한다.
			int random = random(0, words_len - 1);
			String c = words.substring(random, random + 1);

			// 추출한 글자를 미리 준비한 변수에 추가한다.
			password += c;
		}

		return password;
	}
	
	public String getRandomPassword(int num) {
		// 리턴할 문자열
		String password = "";

		// A~Z, 1~0 소문자 뺀다. 소문자 넣고 싶으면 위처럼 다 적어넣으면 된다. 
		String words = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		// 글자길이
		int words_len = words.length();

		for (int i = 0; i < num; i++) {
			// 랜덤한 위치에서 한 글자를 추출한다.
			int random = random(0, words_len - 1);
			String c = words.substring(random, random + 1);

			// 추출한 글자를 미리 준비한 변수에 추가한다.
			password += c;
		}

		return password;
	}
	
	/**
	 * 숫자만 추출해서 int로 변환
	 * @param str
	 * @return
	 */
	public int numExtract(String str) {
		int result = 0;
		if(!str.equals("")||str!=null) {
			result = Integer.parseInt(str.replaceAll("[^0-9]", ""));
		}
		return result;
	}
	
	/**
	 * 문자만 추출하기
	 * @param str
	 * @return
	 */
	public String strExtract(String str) {
		String result ="";
		if(!str.equals("")||str!=null) {
			result = str.replaceAll("[0-9]", "");
		}
		return result;
	}
	
	
	public String makeStrLength(int len, String str, int i) {
		String result = null;
		String k = "0";
		for(int j=0; j<len; j++) {
			result = str + k + i;
			k += "0";
			if(result.length() == len) {
				break;
			}
		}
		return result;
	}
	
	public String getColorKDC(int code) {
		String result = null;
		if(code == 0) {
			result = "#0B6121";
		} else if(code == 100) {
			result = "#B40404";
		} else if(code == 200) {
			result = "#6E6E6E";
		} else if(code == 300) {
			result = "#FF8000";
		} else if(code == 400) {
			result = "#3B170B";
		} else if(code == 500) {
			result = "#2E9AFE";
		} else if(code == 600) {
			result = "#F7D358";
		} else if(code == 700) {
			result = "#5FB404";
		} else if(code == 800) {
			result = "#0404B4";
		} else if(code == 900) {
			result = "#6A0888";
		} else {
			result = "#BDBDBD";
		}
		return result;
	}
}
