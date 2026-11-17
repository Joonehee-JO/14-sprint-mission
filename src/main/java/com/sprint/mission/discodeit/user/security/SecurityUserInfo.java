package com.sprint.mission.discodeit.user.security;

import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SecurityUserInfo(UUID userId, String username) {

    public static SecurityUserInfo init(UUID userId, String username) {
        return SecurityUserInfo.builder()
            .userId(userId)
            .username(username)
            .build();
    }
}
