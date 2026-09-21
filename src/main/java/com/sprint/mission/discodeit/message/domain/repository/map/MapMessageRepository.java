package com.sprint.mission.discodeit.message.domain.repository.map;

import com.sprint.mission.discodeit.message.domain.entity.Message;
import com.sprint.mission.discodeit.abstractmaprepository.CrudRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MapMessageRepository extends CrudRepository<Message, UUID>{
    Optional<Message> findLastMessageByChannelId(UUID channelId);
    

    void deleteMessageByChannelId(UUID channelId);
    List<Message> findAllMessageByChannelId(UUID channelId);
}
