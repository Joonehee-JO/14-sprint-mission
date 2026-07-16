package service.message;

import domain.Message;
import java.util.List;
import java.util.Optional;
import repository.message.MessageRepository;

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
}
