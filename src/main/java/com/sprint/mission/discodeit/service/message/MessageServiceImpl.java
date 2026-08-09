package com.sprint.mission.discodeit.service.message;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService{
    private final MessageRepository messageRepository;

    @Override
    public Message createMessage(Message message, List<UUID> imageList) {
        if(Objects.isNull(message)){
            throw new IllegalArgumentException("null x");
        }

        if(Objects.nonNull(imageList)){
            message.updateMessageImagesFiled(imageList);
        }

        return messageRepository.saveEntity(message);
    }

    @Override
    public Message findMessageById(UUID messageId) {
        if(Objects.isNull(messageId)){
            throw new IllegalArgumentException("null x");
        }

        return messageRepository.findById(messageId)
            .orElseThrow(() -> new IllegalArgumentException("해당 메시지가 존재하지 않습니다"));
    }

    @Override
    public void deleteMessage(UUID messageId) {
        if(Objects.isNull(messageId)){
            throw new IllegalArgumentException("null x");
        }

        this.findMessageById(messageId);

        messageRepository.deleteEntity(messageId);
    }

    @Override
    public List<Message> findAllMessageByChannelId(UUID channelId) {
        if(Objects.isNull(channelId)){
            throw new IllegalArgumentException("null x");
        }

        return messageRepository.findAllMessageByChannelId(channelId);
    }

    @Override
    public Optional<Message> findLastMessageByChannelId(UUID channelId) {
        if(Objects.isNull(channelId)){
            throw new IllegalArgumentException("null x");
        }

        /*
            옵셔널로 다시 던지는 이유 - 아무런 메시지가 없는 경우 널이기 때문에
            널이 들어있을 수도 있는 상황에서 바로 컨트롤러로 넘어가는게 아닌 s1 계층으로 던지기 때문에
            s1에서 혹시 이 객체에 접근하려는 순간 런타임에러 발생가능
         */
        return messageRepository.findLastMessageByChannelId(channelId);
    }

    @Override
    public void deleteMessageByChannelId(UUID channelId) {
        if(Objects.isNull(channelId)){
            throw new IllegalArgumentException("null x");
        }

        messageRepository.deleteMessageByChannelId(channelId);
    }

    @Override
    public Message updateMessageContent(UUID messageId, String content) {
        if(Objects.isNull(messageId)){
            throw new IllegalArgumentException("null x");
        }

        Message message = this.findMessageById(messageId);
        message.updateContent(content);

        //맵구조라 세이브로 호출
        return messageRepository.saveEntity(message);
    }
}
