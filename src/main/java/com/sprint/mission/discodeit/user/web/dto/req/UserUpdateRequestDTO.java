package com.sprint.mission.discodeit.user.web.dto.req;


import jakarta.validation.constraints.Email;

public record UserUpdateRequestDTO(
    String newUsername,
    @Email String newEmail,
    String newPassword
) {}
