package com.chat.app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;

    @Column(length = 1000)
    private String content;

    @Enumerated(EnumType.STRING)
    private MessageType type = MessageType.GLOBAL;   // GLOBAL or TEAM

    private String team;                             // null for global messages

    private LocalDateTime timestamp = LocalDateTime.now();
}