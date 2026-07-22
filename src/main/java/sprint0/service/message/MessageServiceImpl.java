package sprint0.service.message;

import sprint0.domain.Message;
import java.util.List;
import java.util.Optional;
import sprint0.repository.message.MessageRepository;

public class MessageServiceImpl implements MessageService{
    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> extractAllChannelMessage(Long channelId) {
        Optional<List<Message>> messageList = messageRepository.findChannelAllMessage(channelId);
        if (messageList.isPresent()){
            return messageList.get();
        }

        return List.of();
    }

    @Override
    public Message inputMessage(Long channelId, Long userId, String content) {
        Optional<Message> message = messageRepository.create(Message.makeMessage(userId, channelId, content));
        if(message.isPresent()){
            return message.get();
        }

        throw new RuntimeException("서버 문제로 메시지 전송 실패");
    }
}
