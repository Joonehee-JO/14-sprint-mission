package com.sprint.mission.discodeit.user.web.dto;

import com.sprint.mission.discodeit.user.domain.entity.User;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record UserResponseDTO(
    UUID id,
    Instant createdAt,
    Instant updatedAt,
    String username,
    String email,
    String password,
    UUID profileId
) {
    public static UserResponseDTO of(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getCreatedAt(),
            user.getUpdatedAt(),
            user.getName(),
            user.getEmail(),
            user.getUserPassword(),
            user.getProfileId()
        );
    }
}
