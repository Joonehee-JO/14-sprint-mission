package com.sprint.mission.discodeit.message.domain.repository;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.global.exception.CustomErrorCode;
import com.sprint.mission.discodeit.global.exception.CustomException;
import com.sprint.mission.discodeit.message.domain.entity.Message;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    default Message getByIdOrThrow(UUID channelId) {
        return findById(channelId).orElseThrow(() -> new CustomException(CustomErrorCode.MESSAGE_NOT_FOUND));
    }

    // findLastMessage
    Optional<Message> findTopByChannelIdOrderByCreatedAtDesc(UUID channelId);
}
