package com.sprint.mission.discodeit.service.user;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.web.controller.dto.req.UserCreateRequestDTO;
import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(UserCreateRequestDTO userCreateRequestDTO);
    User findById(UUID id);
    User updateUser(UUID id, String name);
    List<User> findAllUser();
    void deleteUser(UUID id);
}
