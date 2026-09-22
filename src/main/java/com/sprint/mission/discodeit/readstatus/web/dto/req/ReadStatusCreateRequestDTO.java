package com.sprint.mission.discodeit.readstatus.web.dto.req;

import java.time.Instant;
import java.util.UUID;

public record ReadStatusCreateRequestDTO(
    UUID userId,
    UUID channelId,
    Instant lastReadAt          // ?
) {

}
