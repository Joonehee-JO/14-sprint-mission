package sprint0.service.message;

import sprint0.domain.Message;
import sprint0.global.annotation.Comment;
import java.util.List;

public interface MessageService {
    @Comment("특정 채널의 모든 메시지 출력")
    List<Message> extractAllChannelMessage(Long channelId);

    Message inputMessage(Long channelId, Long userId, String content);
}
