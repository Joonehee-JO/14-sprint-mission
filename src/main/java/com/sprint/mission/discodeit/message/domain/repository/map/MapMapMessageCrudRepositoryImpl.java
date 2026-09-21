package com.sprint.mission.discodeit.message.domain.repository.map;

import com.sprint.mission.discodeit.message.domain.entity.Message;
import com.sprint.mission.discodeit.abstractmaprepository.map.AbstractMapCrudRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class MapMapMessageCrudRepositoryImpl extends AbstractMapCrudRepository<Message> implements
    MapMessageRepository {

    @Override
    public Optional<Message> findLastMessageByChannelId(UUID channelId) {
        List<Message> messageList = super.findAllEntity();
        return messageList.stream()
            .filter(message -> message.getChannelId().equals(channelId))
            .max(Comparator.comparing(Message::getCreatedAt));
    }

    @Override
    public void deleteMessageByChannelId(UUID channelId) {
        List<Message> messageList = super.findAllEntity();
        List<Message> filteredMessages = messageList.stream()
            .filter(message -> message.getChannelId().equals(channelId))
            .toList();

        /*
            CME 조심
         */
        for (Message filteredMessage : filteredMessages) {
            super.deleteEntity(filteredMessage.getId());
        }
    }

    @Override
    public List<Message> findAllMessageByChannelId(UUID channelId) {
        List<Message> messageList = super.findAllEntity();
        return messageList.stream()
            .filter(message -> message.getChannelId().equals(channelId))
            .toList();
    }
}
