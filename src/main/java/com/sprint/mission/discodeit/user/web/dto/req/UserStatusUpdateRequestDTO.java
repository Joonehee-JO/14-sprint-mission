package com.sprint.mission.discodeit.user.web.dto.req;

import java.time.Instant;

public record UserStatusUpdateRequestDTO(
    Instant newLastActiveAt
) {

}
