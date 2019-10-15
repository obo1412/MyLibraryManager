package com.gaimit.helper;

import java.util.regex.Pattern;

public class RegexHelper {


	/**
	 * 二쇱뼱吏� 臾몄옄�뿴�씠 怨듬갚�씠嫄곕굹 null�씤吏�瑜� 寃��궗
	 * 
	 * @param str
	 *            - 寃��궗�븷 臾몄옄�뿴
	 * @return boolean - 怨듬갚,null�씠 �븘�땺 寃쎌슦 true 由ы꽩
	 */
	public boolean isValue(String str) {
		boolean result = false;
		if (str != null) {
			result = !str.trim().equals("");
		}
		return result;
	}

	/**
	 * �닽�옄 紐⑥뼇�뿉 ���븳 �삎�떇 寃��궗
	 * 
	 * @param str
	 *            - 寃��궗�븷 臾몄옄�뿴
	 * @return boolean - �삎�떇�뿉 留욎쓣 寃쎌슦 true, 留욎� �븡�쓣 寃쎌슦 false
	 */
	public boolean isNum(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("^[0-9]*$", str);
		}
		return result;
	}

	/**
	 * �쁺臾몄쑝濡쒕쭔 援ъ꽦�릺�뿀�뒗吏��뿉 ���븳 �삎�떇 寃��궗
	 * 
	 * @param str
	 *            - 寃��궗�븷 臾몄옄�뿴
	 * @return boolean - �삎�떇�뿉 留욎쓣 寃쎌슦 true, 留욎� �븡�쓣 寃쎌슦 false
	 */
	public boolean isEng(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("^[a-zA-Z]*$", str);
		}
		return result;
	}

	/**
	 * �븳湲�濡쒕쭔 援ъ꽦�릺�뿀�뒗吏��뿉 ���븳 �삎�떇 寃��궗
	 * 
	 * @param str
	 *            - 寃��궗�븷 臾몄옄�뿴
	 * @return boolean - �삎�떇�뿉 留욎쓣 寃쎌슦 true, 留욎� �븡�쓣 寃쎌슦 false
	 */
	public boolean isKor(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("^[�꽦-�뀕媛�-�옡]*$", str);
		}
		return result;
	}

	/**
	 * �쁺臾멸낵 �닽�옄濡쒕쭔 援ъ꽦�릺�뿀�뒗吏��뿉 ���븳 �삎�떇 寃��궗
	 * 
	 * @param str
	 *            - 寃��궗�븷 臾몄옄�뿴
	 * @return boolean - �삎�떇�뿉 留욎쓣 寃쎌슦 true, 留욎� �븡�쓣 寃쎌슦 false
	 */
	public boolean isEngNum(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("^[a-zA-Z0-9]*$", str);
		}
		return result;
	}

	/**
	 * �븳湲�怨� �닽�옄濡쒕쭔 援ъ꽦�릺�뿀�뒗吏��뿉 ���븳 �삎�떇 寃��궗
	 * 
	 * @param str
	 *            - 寃��궗�븷 臾몄옄�뿴
	 * @return boolean - �삎�떇�뿉 留욎쓣 寃쎌슦 true, 留욎� �븡�쓣 寃쎌슦 false
	 */
	public boolean isKorNum(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("^[�꽦-�뀕媛�-�옡0-9]*$", str);
		}
		return result;
	}

	/**
	 * �씠硫붿씪 �삎�떇�씤吏��뿉 ���븳 寃��궗.
	 * 
	 * @param str
	 *            - 寃��궗�븷 臾몄옄�뿴
	 * @return boolean - �삎�떇�뿉 留욎쓣 寃쎌슦 true, 留욎� �븡�쓣 寃쎌슦 false
	 */
	public boolean isEmail(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", str);
		}
		return result;
	}

	/**
	 * "-"�뾾�씠 �빖�뱶�룿踰덊샇�씤吏��뿉 ���븳 �삎�떇寃��궗.
	 * 
	 * @param str
	 *            - 寃��궗�븷 臾몄옄�뿴
	 * @return boolean - �삎�떇�뿉 留욎쓣 寃쎌슦 true, 留욎� �븡�쓣 寃쎌슦 false
	 */
	public boolean isCellPhone(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", str);
		}
		return result;
	}

	/**
	 * "-"�뾾�씠 �쟾�솕踰덊샇�씤吏��뿉 ���븳 �삎�떇寃��궗.
	 * 
	 * @param str
	 *            - 寃��궗�븷 臾몄옄�뿴
	 * @return boolean - �삎�떇�뿉 留욎쓣 寃쎌슦 true, 留욎� �븡�쓣 寃쎌슦 false
	 */
	public boolean isTel(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("^\\d{2,3}\\d{3,4}\\d{4}$", str);
		}
		return result;
	}

	/**
	 * "-"�뾾�씠 二쇰�쇰쾲�샇�뿉 ���븳 �삎�떇寃��궗
	 * 
	 * @param str
	 *            - 寃��궗�븷 臾몄옄�뿴
	 * @return boolean - �삎�떇�뿉 留욎쓣 寃쎌슦 true, 留욎� �븡�쓣 寃쎌슦 false
	 */
	public boolean isJumin(String str) {
		boolean result = false;
		if (isValue(str)) {
			result = Pattern.matches("^\\d{6}[1-4]\\d{6}", str);
		}
		return result;
	}
}
