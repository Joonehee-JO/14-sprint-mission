package com.sprint.mission.discodeit.channel.domain.service;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.channel.domain.repository.ChannelRepository;
import com.sprint.mission.discodeit.global.exception.CustomErrorCode;
import com.sprint.mission.discodeit.global.exception.CustomException;
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
}
