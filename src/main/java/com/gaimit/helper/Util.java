package com.gaimit.helper;

import com.gaimit.mlm.model.BookHeld;

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
		} else if(code >= 100 && code < 200) {
			result = "#B40404";
		} else if(code >= 200 && code < 300) {
			result = "#6E6E6E";
		} else if(code >= 300 && code < 400) {
			result = "#FF8000";
		} else if(code >= 400 && code < 500) {
			result = "#3B170B";
		} else if(code >= 500 && code < 600) {
			result = "#58ACFA";
		} else if(code >= 600 && code < 700) {
			result = "#F7D358";
		} else if(code >= 700 && code < 800) {
			result = "#5FB404";
		} else if(code >= 800 && code < 900) {
			result = "#0404B4";
		} else if(code >= 900 && code < 1000) {
			result = "#6A0888";
		} else {
			result = "#BDBDBD";
		}
		return result;
	}
	
	public BookHeld getChangWonColorKDC(int code) {
		BookHeld  result = new BookHeld();
		if(code == 0) {
			result.setClassCodeColor("#18248A");
			result.setClassCodeSection("총류");
		} else if(code >= 100 && code < 200) {
			result.setClassCodeColor("#F9BF2D");
			result.setClassCodeSection("철학");
		} else if(code >= 200 && code < 300) {
			result.setClassCodeColor("#E50136");
			result.setClassCodeSection("종교");
		} else if(code >= 300 && code < 400) {
			result.setClassCodeColor("#000000");
			result.setClassCodeSection("사회과학");
		} else if(code >= 400 && code < 500) {
			result.setClassCodeColor("#00834A");
			result.setClassCodeSection("자연과학");
		} else if(code >= 500 && code < 600) {
			result.setClassCodeColor("#6F6D72");
			result.setClassCodeSection("기술과학");
		} else if(code >= 600 && code < 700) {
			result.setClassCodeColor("#D50078");
			result.setClassCodeSection("예술");
		} else if(code >= 700 && code < 800) {
			result.setClassCodeColor("#0A95CE");
			result.setClassCodeSection("언어");
		} else if(code >= 800 && code < 900) {
			result.setClassCodeColor("#EA5503");
			result.setClassCodeSection("문학");
		} else if(code >= 900 && code < 1000) {
			result.setClassCodeColor("#4CA634");
			result.setClassCodeSection("역사");
		} else {
			result.setClassCodeColor("#FFFFFF");
			result.setClassCodeSection("미분류");
		}
		return result;
	}
	
	public String getNumVolumeCode(String vol) {
		String result = vol;
		vol = "";
		if(result != null && !"".equals(result)) {
			result = result.trim();
			if(result.indexOf(".")>=0) {
				result = result.substring(result.indexOf(".")+1);
			}
			
			for(int i=0; i<result.length(); i++) {
				int charNumCode = result.charAt(i);
				if((charNumCode>=48 && charNumCode<=57)||charNumCode==45) {
					vol += result.substring(i,i+1);
				}
			}
			
			while(!"".equals(vol)&&vol.substring(0,1).equals("0")) {
				vol = vol.substring(1);
			}
			result = vol;
		}
		return result;
	}
	
	public String getFloatClsCode(String str) {
		String result = str;
		str = "";
		if(result!=null&&!"".equals(result)) {
			for(int i=0; i<result.length(); i++) {
				int charNumCode = result.charAt(i);
				if((charNumCode>=48&&charNumCode<=57)||charNumCode==46) {
					str += result.substring(i,i+1);
				}
			}
			result = str;
		}
		return result;
	}
}
