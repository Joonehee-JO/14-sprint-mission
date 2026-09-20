package com.sprint.mission.discodeit.user.web.dto;

import java.time.Instant;

public record UserStatusUpdateRequestDTO(
    Instant newLastActiveAt
) {

}
