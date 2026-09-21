package com.sprint.mission.discodeit.user.web.dto;

import com.sprint.mission.discodeit.user.domain.entity.UserStatus;
import java.time.Instant;
import java.util.UUID;

public record UserStatusResponseDTO(
    UUID id,
    Instant createdAt,
    Instant updatedAt,
    UUID userId,
    Instant lastActiveAt,
    boolean online
) {
    public static UserStatusResponseDTO from(UserStatus userStatus){
        return new UserStatusResponseDTO(
            userStatus.getId(),
            userStatus.getCreatedAt(),
            userStatus.getUpdatedAt(),
            userStatus.getUser().getId(),
            userStatus.getLastActiveAt(),
            userStatus.isOnline()
            );
    }
}
