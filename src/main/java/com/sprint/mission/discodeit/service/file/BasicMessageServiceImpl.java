package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.file.message.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicMessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public Message createMessage(UUID userId, UUID channelId, String content) {
        Message message = Message.makeMessage(userId, channelId, content);
        return messageRepository.saveEntity(message);
    }

    @Override
    public void deleteMessage(UUID messageId) {
        messageRepository.deleteEntity(messageId);
    }

    @Override
    public List<Message> findAllMessageByChannel(UUID channelId) {
        return messageRepository.findAllMessageByChannel(channelId);
    }
}
