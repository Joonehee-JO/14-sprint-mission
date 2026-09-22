package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.channel.web.dto.ChannelResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelMapper {

    ChannelResponseDTO toResponse(Channel channel);
}
