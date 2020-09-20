package com.wyj.util;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {

	public static boolean checkVerifyCode(HttpServletRequest request) {
		String verifyCodeExpected = (String)request.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
		if (verifyCodeExpected == null || !verifyCodeActual.equals(verifyCodeExpected) ) {
			return false;
		}
		return true;
	}
	
}
