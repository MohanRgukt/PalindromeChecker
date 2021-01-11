package com.palindromechecker.redis.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	
	@Value("${websocket.sender}")
	private String SENDER;

	@Value("${websocket.topic}")
	private String TOPIC;
	
	@Override
	public void onMessage(Message message, byte[] pattern) {

		try {
			log.info("Message Subscribed");
			PalindromeInput pi = objectMapper.readValue(message.toString(), PalindromeInput.class);
			palindromeDto.save(pi);
			log.info("Data Saved to DB");
			sendMessageToWs.sendMessageToBot(message.toString(), SENDER, TOPIC, new ChatMessage());
			log.info("Message Published to webSocket");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}

	}

}
