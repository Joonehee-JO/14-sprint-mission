package com.sprint.mission.discodeit.readstatus.web.dto;

import java.time.Instant;
import java.util.UUID;

public record ReadStatusCreateRequestDTO(
    UUID userId,
    UUID channelId,
    Instant lastReadAt          // 보내는쪽에서 그 시간을 보내는건가?
) {

}
