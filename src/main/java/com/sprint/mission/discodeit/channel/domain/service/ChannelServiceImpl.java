package com.sprint.mission.discodeit.channel.domain.service;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.channel.domain.entity.ChannelType;
import com.sprint.mission.discodeit.channel.domain.repository.ChannelRepository;
import com.sprint.mission.discodeit.channel.domain.repository.map.MapChannelRepository;
import com.sprint.mission.discodeit.global.exception.CustomErrorCode;
import com.sprint.mission.discodeit.global.exception.CustomException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;

    @Override
    public Channel makeChannel(Channel channel) {

        return channelRepository.save(channel);
    }

    @Override
    public Channel findChannelById(UUID channelId) {

        return channelRepository.findById(channelId)
            .orElseThrow(() -> new CustomException(CustomErrorCode.CHANNEL_NOT_FOUND));
    }

    @Override
    public Channel updateChannel(UUID channelId, String updateName, String description) {
        Channel channel = this.findChannelById(channelId);
        channel.updateChannelNameDescription(updateName, description);

        return channel;
    }

    @Override
    public void deleteChannel(UUID channelId) {
        Channel channel = this.findChannelById(channelId);

        channelRepository.delete(channel);
    }

    @Override
    public List<Channel> findAllChannel() {
        return channelRepository.findAll();
    }

    @Override
    public List<Channel> findAllChannelByIds(List<UUID> channelIdList) {
        return channelRepository.findAllByIdIn(channelIdList);
    }

    @Override
    public List<Channel> findAllPublicChannel() {
        return channelRepository.findAllByChannelType(ChannelType.PUBLIC);
    }
}
