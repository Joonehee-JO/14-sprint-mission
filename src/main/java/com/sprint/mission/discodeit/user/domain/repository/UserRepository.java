package com.sprint.mission.discodeit.user.domain.repository;

import com.sprint.mission.discodeit.global.exception.CustomErrorCode;
import com.sprint.mission.discodeit.global.exception.CustomException;
import com.sprint.mission.discodeit.message.domain.entity.Message;
import com.sprint.mission.discodeit.user.domain.entity.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
    default User getByIdOrThrow(UUID channelId) {
        return findById(channelId).orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));
    }

    default void validateExistsById(UUID userId) {
        if (!existsById(userId)) {
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
    }
}
