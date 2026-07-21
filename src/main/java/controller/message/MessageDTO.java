package controller.message;

public class MessageDTO {
    private Long userId;
    private Long channelId;
    private String content;

    public MessageDTO(Long userId, Long channelId, String content) {
        validMessage(content);
        this.userId = userId;
        this.channelId = channelId;
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public String getContent() {
        return content;
    }

    private void validMessage(String content){
        if(content.isEmpty())throw new IllegalArgumentException("문자 메시지를 입력하여 보내주세요");
        else if(content.length() > 500) throw new IllegalArgumentException("문자 메시지는 최대 500글자만 가능합니다");
    }
}
