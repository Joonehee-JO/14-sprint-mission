package com.sprint.mission.discodeit.readstatus.web.dto;

import java.time.Instant;
import java.util.UUID;

public record ReadStatusCreateRequestDTO(
    UUID userId,
    UUID channelId,
    Instant lastReadAt          // ?
) {

}
