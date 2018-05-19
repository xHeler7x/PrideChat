package me.kwiatkowsky.PrideChat.model;

import me.kwiatkowsky.PrideChat.web.Session;
import org.springframework.beans.factory.annotation.Autowired;

public class ChatMessage {

    @Autowired
    private Session session;
    private MessageType type;
    private String content;
    private String sender;

    public ChatMessage(){
        this.sender = session.getUser().getUsername();
    }

    public enum MessageType{
        CHAT,
        JOIN,
        LEAVE
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
