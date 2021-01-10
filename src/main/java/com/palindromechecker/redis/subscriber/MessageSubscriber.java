package com.palindromechecker.redis.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.palindromechecker.dto.PalindromeDto;
import com.palindromechecker.entity.ChatMessage;
import com.palindromechecker.entity.PalindromeInput;
import com.palindromechecker.entity.PalindromeStringCalc;
import com.palindromechecker.ws.publish.SendMessageToWS;

@Service
public class MessageSubscriber implements MessageListener {

	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	PalindromeDto palindromeDto;

	@Autowired
	SendMessageToWS sendMessageToWs;

	Logger log = LoggerFactory.getLogger(MessageSubscriber.class);

	private String sender = "Spring-Redis";

	private String topic = "/topic/public";

	@Override
	public void onMessage(Message message, byte[] pattern) {

		try {

			log.info("Consumed Message===" + message);
			PalindromeInput pi = objectMapper.readValue(message.toString(), PalindromeInput.class);
			palindromeDto.save(pi);
			sendMessageToWs.sendMessageToBot(message.toString(), sender, topic, new ChatMessage());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
