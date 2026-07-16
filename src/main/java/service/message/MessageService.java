package service.message;

import domain.Message;
import global.annotation.Comment;
import java.util.List;

public interface MessageService {
    @Comment("특정 채널의 모든 메시지 출력")
    List<Message> extractAllChannelMessage(Long channelId);
}
