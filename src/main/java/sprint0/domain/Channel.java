package sprint0.domain;

import java.time.LocalDateTime;

/*
    채널은 1~10 번만 존재한다 가정
 */
public class Channel {
    private Long channelId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;                //유저 아이디
    private Long messageId;             //메시지 아이디

    public Channel(Long channelId, Long userId, Long messageId) {
        this.channelId = channelId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.userId = userId;
        this.messageId = messageId;
    }

    public Long getChannelId() {
        return channelId;
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

    public Long getMessageId() {
        return messageId;
    }
}
