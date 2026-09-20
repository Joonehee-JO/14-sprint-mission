package com.sprint.mission.discodeit.channel.domain.repository.map;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.abstractmaprepository.CrudRepository;
import java.util.List;
import java.util.UUID;

public interface MapChannelRepository extends CrudRepository<Channel, UUID> {
    List<Channel> findAllChannelByIds(List<UUID> idList);
    List<Channel> findAllPublicChannel();
}
