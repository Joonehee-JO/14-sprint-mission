package com.sprint.mission.discodeit.service.application;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.service.binarycontent.BinaryContentService;
import com.sprint.mission.discodeit.service.readstatus.ReadStatusService;
import com.sprint.mission.discodeit.service.user.UserService;
import com.sprint.mission.discodeit.service.userstatus.UserStatusService;
import com.sprint.mission.discodeit.web.controller.dto.req.UserCreateRequestDTO;
import global.exception.CustomErrorCode;
import global.exception.CustomException;
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

    public User createUser(UserCreateRequestDTO userCreateRequestDTO) {
        UUID profileImageId = null;
        if(!Objects.isNull(userCreateRequestDTO.getProfileImage())){
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

        return user;
    }

    public void deleteUserAccount(UUID id){
        User user = userService.findById(id);
        binaryContentService.deleteStoreFileById(user.getProfileId());
        userStatusService.deleteUserStatusByUserId(user.getId());
        userService.deleteUser(user.getId());
    }
}
