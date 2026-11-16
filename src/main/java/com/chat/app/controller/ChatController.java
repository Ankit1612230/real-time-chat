package com.chat.app.controller;

import com.chat.app.model.ChatMessage;
import com.chat.app.model.MessageType;
import com.chat.app.repository.ChatMessageRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import java.time.LocalDateTime;

@Controller
public class ChatController {

    private final ChatMessageRepository repository;

    public ChatController(ChatMessageRepository repository) {
        this.repository = repository;
    }

    // GLOBAL CHAT: save, then broadcast to everyone
    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage sendGlobal(ChatMessage message) {
        message.setId(null);                       // never trust client id
        message.setType(MessageType.GLOBAL);
        message.setTeam(null);
        message.setTimestamp(LocalDateTime.now());
        return repository.save(message);           // returns saved msg with id
    }
    // TEAM CHAT: save, then send only to that team's topic
    @MessageMapping("/team/{team}")
    @SendTo("/topic/team/{team}")
    public ChatMessage sendTeam(@DestinationVariable String team, ChatMessage message) {
        message.setId(null);
        message.setType(MessageType.TEAM);
        message.setTeam(team);
        message.setTimestamp(LocalDateTime.now());
        return repository.save(message);
    }

    @GetMapping("/")
    public String chat() {
        return "chat";
    }
}