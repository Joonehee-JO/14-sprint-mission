package com.sprint.mission.discodeit.user.web.dto.res;

import com.sprint.mission.discodeit.user.domain.entity.User;
import java.time.Instant;
import java.util.UUID;

public record UserUpdateResponseDTO(
        UUID id,
        Instant createdAt,
        Instant updatedAt,
        String username,
        String email,
        String password,
        UUID profileId
    ) {
    public static UserUpdateResponseDTO from(User user) {
        return new UserUpdateResponseDTO(
            user.getId(),
            user.getCreatedAt(),
            user.getUpdatedAt(),
            user.getName(),
            user.getEmail(),
            user.getUserPassword(),
            user.getProfileImage().getId()
        );
    }
}
