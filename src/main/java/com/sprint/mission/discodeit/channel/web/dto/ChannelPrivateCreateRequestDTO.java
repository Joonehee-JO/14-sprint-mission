package com.sprint.mission.discodeit.channel.web.dto;

import java.util.List;
import java.util.UUID;

public record ChannelPrivateCreateRequestDTO(List<UUID> participantIds)
{ }
