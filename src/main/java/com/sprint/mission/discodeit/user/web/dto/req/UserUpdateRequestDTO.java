package com.sprint.mission.discodeit.user.web.dto.req;


public record UserUpdateRequestDTO(
    String newUsername,
    String newEmail,
    String newPassword
) {}
