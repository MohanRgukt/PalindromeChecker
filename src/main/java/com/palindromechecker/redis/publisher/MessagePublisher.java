package com.palindromechecker.redis.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.palindromechecker.entity.PalindromeInput;

@Component
public class MessagePublisher {
	@Autowired
	private RedisTemplate<String, Object> template;

	@Autowired
	private ChannelTopic topic;

	public String publish(PalindromeInput palindrome) {
		template.convertAndSend(topic.getTopic(), palindrome.toString());
		return "Event Published successfully";
	}
}
