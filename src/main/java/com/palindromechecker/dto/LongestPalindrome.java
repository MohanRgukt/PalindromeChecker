package com.palindromechecker.dto;

import org.springframework.stereotype.Component;

@Component
public class LongestPalindrome {

	public String longestPalindrome(String s) {
		if (s == null || s.length() < 1)
			return "";
		int start = 0;
		int end = 0;
		for (int i = 0; i < s.length(); i++) {
			int len1 = expandAroundCenter(s, i, i);
			int len2 = expandAroundCenter(s, i, i + 1);
			int len = Math.max(len1, len2);
			if (len > end - start) {
				start = i - (len - 1) / 2;
				end = i + len / 2;
			}
		}
		return hasOnlyDigits(s.substring(start, end + 1));
	}

	private int expandAroundCenter(String s, int left, int right) {
		int L = left, R = right;
		while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R) && ((int) s.charAt(L) >= 65)
				&& ((int) s.charAt(L) <= 90) && ((int) s.charAt(R) >= 65) && ((int) s.charAt(R) <= 90)) {
			L--;
			R++;
		}
		return R - L - 1;
	}

	private String hasOnlyDigits(String s) {

		if (s.charAt(0) >= '0' && s.charAt(0) <= '9') {
			return "";
		}

		return s;
	}

}
