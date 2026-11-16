package com.chat.app.repository;

import com.chat.app.model.ChatMessage;
import com.chat.app.model.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // last 50 global messages, newest first
    List<ChatMessage> findTop50ByTypeOrderByTimestampDesc(MessageType type);

    // last 50 messages for one team, newest first
    List<ChatMessage> findTop50ByTypeAndTeamOrderByTimestampDesc(MessageType type, String team);
}