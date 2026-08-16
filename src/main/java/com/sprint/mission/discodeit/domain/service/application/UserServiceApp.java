package com.sprint.mission.discodeit.domain.service.application;

import com.sprint.mission.discodeit.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.domain.entity.User;
import com.sprint.mission.discodeit.domain.entity.UserStatus;
import com.sprint.mission.discodeit.domain.service.binarycontent.BinaryContentService;
import com.sprint.mission.discodeit.domain.service.user.UserService;
import com.sprint.mission.discodeit.domain.service.userstatus.UserStatusService;
import com.sprint.mission.discodeit.web.controller.dto.req.UserCreateRequestDTO;
import com.sprint.mission.discodeit.web.controller.dto.req.UserLoginRequestDTO;
import com.sprint.mission.discodeit.web.controller.dto.res.UserResponseDTO;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceApp {
    private final UserService userService;
    private final BinaryContentService binaryContentService;
    private final UserStatusService userStatusService;

    public UserResponseDTO createUser(UserCreateRequestDTO userCreateRequestDTO) {
        UUID profileImageId = null;
        if(Objects.nonNull(userCreateRequestDTO.getProfileImage())){
            BinaryContent binaryContent  = binaryContentService.storeFile(userCreateRequestDTO.getProfileImage());
            profileImageId = binaryContent.getId();
        }

        User user = User.init(userCreateRequestDTO.getEmail(),
            userCreateRequestDTO.getUserPassword(), userCreateRequestDTO.getName(),
            userCreateRequestDTO.getAge());

        if(Objects.nonNull(profileImageId)){
            user.updateProfileImage(profileImageId);
        }

        user = userService.createUser(user);

        UserStatus userStatus = UserStatus.init(user.getId());
        userStatusService.createUserStatus(userStatus);

        return UserResponseDTO.of(user, userStatus.isActivated());
    }

    public void deleteUserAccount(UUID id){
        User user = userService.findById(id);
        if(user.hasProfileImage()){
            binaryContentService.deleteStoreFileById(user.getProfileId());
        }
        userStatusService.deleteUserStatusByUserId(user.getId());
        userService.deleteUser(user.getId());
    }

    public User login(UserLoginRequestDTO userLoginRequestDTO){
        User user = userService.findUserByEmail(userLoginRequestDTO.email());
        user.verifyPassword(userLoginRequestDTO.password());

        return user;
    }
}
