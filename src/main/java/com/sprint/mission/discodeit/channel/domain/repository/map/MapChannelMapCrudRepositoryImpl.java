package com.sprint.mission.discodeit.channel.domain.repository.map;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.channel.domain.entity.ChannelType;
import com.sprint.mission.discodeit.abstractmaprepository.map.AbstractMapCrudRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class MapChannelMapCrudRepositoryImpl extends AbstractMapCrudRepository<Channel> implements
    MapChannelRepository {

    @Override
    public List<Channel> findAllChannelByIds(List<UUID> idList) {
        List<Channel> channelList = super.findAllEntity();
        return channelList.stream()
            .filter(channel -> idList.contains(channel.getId()))
            .toList();
    }

    @Override
    public List<Channel> findAllPublicChannel() {
        List<Channel> channelList = super.findAllEntity();
        return channelList.stream()
            .filter(channel -> channel.getChannelType().equals(ChannelType.PUBLIC))
            .toList();
    }
}
