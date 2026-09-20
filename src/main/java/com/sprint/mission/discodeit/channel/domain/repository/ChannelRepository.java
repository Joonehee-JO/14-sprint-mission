package com.sprint.mission.discodeit.channel.domain.repository;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.channel.domain.entity.ChannelType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, UUID> {
    List<Channel> findAllByIdIn(List<UUID> idList);
    List<Channel> findAllByChannelType(ChannelType channelType);
}
