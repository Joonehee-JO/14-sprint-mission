package com.sprint.mission.discodeit.repository.map.channel;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.map.AbstractMapCrudRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ChannelMapCrudRepositoryImpl extends AbstractMapCrudRepository<Channel> implements ChannelRepository {

}
