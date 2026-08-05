package com.sprint.mission.discodeit.repository.deprecated.file.message;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.deprecated.file.FileCrudRepository;
import java.util.List;
import java.util.UUID;

public interface MessageRepository extends FileCrudRepository<Message, UUID> {
    List<Message> findAllMessageByChannel(UUID channelID);
}
