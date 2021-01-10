package com.palindromechecker.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.palindromechecker.entity.PalindromeInput;
import com.palindromechecker.entity.PalindromeStringCalc;

@Repository
public class PalindromeDto {

	@Autowired
	private RedisTemplate<String, Object> palindromeTemplate;

	public static final String HASH_KEY = "Palindrome1";

	@Value("${websocket.sender}")
	private String SENDER;

	@Value("${websocket.topic}")
	private String TOPIC;

	@Autowired
	LongestPalindrome longestPalindrome;

	public PalindromeInput save(PalindromeInput palind) {
		palindromeTemplate.opsForHash().put(HASH_KEY, palind.getContent(), palind);
		return palind;
	}

	public List<PalindromeStringCalc> findall() {

		List<Object> dataList = palindromeTemplate.opsForHash().values(HASH_KEY);
		List<PalindromeStringCalc> palindList = new ArrayList<>();

		for (Object prd : dataList) {
			PalindromeInput palind = (PalindromeInput) prd;
			String palindromeString = longestPalindrome.longestPalindrome(palind.getContent());

			PalindromeStringCalc palindCalc = new PalindromeStringCalc();

			palindCalc.setContent(palind.getContent());
			palindCalc.setTimeStamp(palind.getTimestamp());
			palindCalc.setLongest_palindrome_size(palindromeString.length());

			palindList.add(palindCalc);
		}
		return palindList;
	}

}
