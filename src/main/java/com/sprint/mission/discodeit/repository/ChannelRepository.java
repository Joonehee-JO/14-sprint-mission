package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import java.util.List;
import java.util.UUID;

public interface ChannelRepository extends CrudRepository<Channel, UUID> {
    List<Channel> findAllChannelByIds(List<UUID> idList);
    List<Channel> findAllPublicChannel();
}
