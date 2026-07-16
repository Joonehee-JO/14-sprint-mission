package domain;

import java.time.LocalDateTime;

public class Message {
    private Long messageId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;             //사용자 매핑 용
    private String content;

    public Message(Long messageId,  Long userId, String content) {
        this.messageId = messageId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.userId = userId;
        this.content = content;
    }

    public Long getMessageId() {
        return messageId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }
}
