package sprint0.domain;

import java.time.LocalDateTime;

public class Message {
    private Long messageId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;             //사용자 매핑 용
    private Long channelId;
    private String content;

    //얘도 메서드로 조립 private 으로 봐주세요
    public Message(Long messageId,  Long userId, String content, Long channelId) {
        this.messageId = messageId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.userId = userId;
        this.content = content;
        this.channelId = channelId;
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

    public Long getChannelId() {
        return channelId;
    }

    public static Message makeMessage(Long userId, Long channelId, String content){
        return new Message(null, userId, content, channelId );
    }


    //임시용
    @Override
    public String toString() {
        return "Message{" +
            "messageId=" + messageId +
            ", userId=" + userId +
            ", channelId=" + channelId +
            ", content='" + content + '\'' +
            '}';
    }

    public String outputMessageDetail(Long userId) {
        if(this.userId.equals(userId)){
            return "\t\t\t\t\t\t\t\t\t\t\t\t" + content;
        }
        else{
            return "["+ userId + "] : "+ content;
        }
    }
}
