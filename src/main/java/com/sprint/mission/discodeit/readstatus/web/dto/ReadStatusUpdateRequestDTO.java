package com.sprint.mission.discodeit.readstatus.web.dto;

import java.time.Instant;

public record ReadStatusUpdateRequestDTO(
    Instant newLastReadAt
) {}
