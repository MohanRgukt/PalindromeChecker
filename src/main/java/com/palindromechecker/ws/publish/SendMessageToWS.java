package com.palindromechecker.ws.publish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.palindromechecker.entity.ChatMessage;
import com.palindromechecker.entity.ChatMessage.MessageType;


@Component
public class SendMessageToWS {

	
	@Autowired
	private SimpMessagingTemplate simptemplate;
	
	public void sendMessageToBot(String content, String sender, String topic, ChatMessage chatMessag ) {
		chatMessag.setContent(content);
		chatMessag.setSender(sender);
		chatMessag.setType(MessageType.CHAT);
		this.simptemplate.convertAndSend(topic, chatMessag);
	}
}
