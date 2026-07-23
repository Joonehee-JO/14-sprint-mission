package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(String name);
    User findById(UUID id);
    User updateUser(UUID id, String name);
    List<User> findAllUser();
    void deleteUser(UUID id);
}
