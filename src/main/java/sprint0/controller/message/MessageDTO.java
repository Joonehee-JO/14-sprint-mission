package sprint0.controller.message;

import sprint0.global.exception.CustomErrorCode;
import sprint0.global.exception.CustomException;

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
        if(content.isEmpty())throw new CustomException(CustomErrorCode.INVALID_MESSAGE_EMPTY);
        if(content.length() > 500) throw new CustomException(CustomErrorCode.INVALID_MESSAGE_MAX_LENGTH);
    }
}
