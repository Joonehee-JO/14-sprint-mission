package com.sprint.mission.discodeit.user.web.dto;


public record UserUpdateRequestDTO(
    String newUsername,
    String newEmail,
    String newPassword
) {}
