package com.sprint.mission.discodeit.web.controller.dto.req;


public record UserUpdateRequestDTO(
    String newUsername,
    String newEmail,
    String newPassword
) {}
