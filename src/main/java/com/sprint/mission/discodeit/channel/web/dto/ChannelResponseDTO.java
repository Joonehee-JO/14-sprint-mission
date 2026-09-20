package com.sprint.mission.discodeit.channel.web.dto;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.channel.domain.entity.ChannelType;
import java.time.Instant;
import java.util.UUID;

public record ChannelResponseDTO(
    UUID id,
    String type,
    String name,
    String description,
    Instant createdAt,
    Instant updatedAt
) {
    public static ChannelResponseDTO from(Channel channel){
        String channelType = channel.getChannelType().equals(ChannelType.PUBLIC) ? "PUBLIC" : "PRIVATE";

        return new ChannelResponseDTO(
            channel.getId(),
            channelType,
            channel.getChannelName(),
            channel.getDescription(),
            channel.getCreatedAt(),
            channel.getUpdatedAt()
        );
    }
}
