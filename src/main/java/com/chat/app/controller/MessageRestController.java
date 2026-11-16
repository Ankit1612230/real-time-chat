package com.chat.app.controller;

import com.chat.app.model.ChatMessage;
import com.chat.app.model.MessageType;
import com.chat.app.repository.ChatMessageRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@CrossOrigin(origins = "http://localhost:*")

@RestController
@RequestMapping("/api/messages")
public class MessageRestController {

    private final ChatMessageRepository repository;

    public MessageRestController(ChatMessageRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/global")
    public List<ChatMessage> globalHistory() {
        List<ChatMessage> list = new ArrayList<>(
                repository.findTop50ByTypeOrderByTimestampDesc(MessageType.GLOBAL));
        Collections.reverse(list);                 // oldest first for display
        return list;
    }
    @GetMapping("/team/{team}")
    public List<ChatMessage> teamHistory(@PathVariable String team) {
        List<ChatMessage> list = new ArrayList<>(
                repository.findTop50ByTypeAndTeamOrderByTimestampDesc(MessageType.TEAM, team));
        Collections.reverse(list);
        return list;
    }
}