package com.sprint.mission.discodeit.channel.web.dto;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.channel.domain.entity.ChannelType;
import com.sprint.mission.discodeit.user.web.dto.res.UserResponseDTO;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ChannelResponseDTO(
    UUID id,
    String type,
    String name,
    String description,
    List<UserResponseDTO> participants,
    Instant lastMessageAt
) {

}
