package com.palindromechecker.entity;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@RedisHash("Palindrome1")
public class PalindromeInput implements Serializable {
	private static final long serialVersionUID = 1L;
	private String content;
	private String timestamp;

	@Override
	public String toString() {
		return "{\"content\":\"" + content + "\", \"timestamp\":\"" + timestamp + "\"}";
	}
}
