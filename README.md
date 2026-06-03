# Real-Time Chat Application

A real-time chat application built using Spring Boot, WebSocket, STOMP, SockJS, and JavaScript.

## Features

* Real-time messaging
* Multiple users can chat simultaneously
* WebSocket-based communication
* STOMP messaging protocol
* Modern responsive user interface
* Automatic message broadcasting to all connected clients

## Tech Stack

* Java 21
* Spring Boot
* Spring WebSocket
* STOMP
* SockJS
* HTML
* CSS
* JavaScript

## How to Run

1. Clone the repository

```bash
git clone https://github.com/your-username/real-time-chat-app.git
```

2. Navigate to the project

```bash
cd real-time-chat-app
```

3. Run the application

```bash
mvn spring-boot:run
```

4. Open the browser

```text
http://localhost:8080
```

5. Open multiple tabs/windows to test real-time messaging.

## Architecture

```text
Client
   ↓
WebSocket (/chat)
   ↓
Spring Boot
   ↓
@MessageMapping("/sendMessage")
   ↓
@SendTo("/topic/messages")
   ↓
All Connected Clients
```

## Future Improvements

* Private messaging
* User authentication
* Chat rooms
* Message persistence using a database
* Typing indicators
* Online user tracking

## Author
Ankit

