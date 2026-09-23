package com.sprint.mission.discodeit.user.application;

import com.sprint.mission.discodeit.binarycontent.application.BinaryApplicationService;
import com.sprint.mission.discodeit.binarycontent.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.user.domain.entity.User;
import com.sprint.mission.discodeit.user.domain.entity.UserStatus;
import com.sprint.mission.discodeit.binarycontent.domain.service.BinaryContentService;
import com.sprint.mission.discodeit.user.domain.repository.UserRepository;
import com.sprint.mission.discodeit.user.domain.repository.UserStatusRepository;
import com.sprint.mission.discodeit.user.domain.service.UserService;
import com.sprint.mission.discodeit.user.web.dto.req.UserCreateRequestDTO;
import com.sprint.mission.discodeit.user.web.dto.req.UserLoginRequestDTO;
import com.sprint.mission.discodeit.user.web.dto.req.UserUpdateRequestDTO;
import com.sprint.mission.discodeit.user.web.dto.res.UserResponseDTO;
import com.sprint.mission.discodeit.user.web.dto.res.UserUpdateResponseDTO;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserApplicationService {
    private final BinaryApplicationService binaryApplicationService;
    private final UserService userService;

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO createAccount(UserCreateRequestDTO userCreateRequestDTO, MultipartFile profileImage) {
        userService.validateEmailNotDuplicated(userCreateRequestDTO.getEmail());

        BinaryContent binaryContent = null;
        if(Objects.nonNull(profileImage) && !profileImage.isEmpty()){
            binaryContent = storeProfileImage(profileImage);
        }

        String encodedPassword = passwordEncoder.encode(userCreateRequestDTO.getPassword());
        User user = User.init(
            userCreateRequestDTO.getEmail(), encodedPassword, userCreateRequestDTO.getUsername(), binaryContent
        );


        user = userRepository.save(user);
        UserStatus userStatus = UserStatus.init(user);

        userStatusRepository.save(userStatus);

        return UserResponseDTO.of(user);
    }

    @Transactional
    public void deleteUserAccount(UUID userId){
        User user = userRepository.getByIdOrThrow(userId);
//        if(user.hasProfileImage()){
//            binaryContentService.deleteStoreFileById(user.getProfileId());
//        }

        userRepository.delete(user);
    }

    public List<UserResponseDTO> findAllUser(){
        List<User> userList = userRepository.findAll();
//        List<UserStatus> userStatusList = userStatusRepository.findAll();
//
//        Map<UUID, Boolean> uuidBooleanMap = userStatusList.stream()
//            .collect(Collectors.toMap(
//                userStatus -> userStatus.getUser().getId(),
//                UserStatus::isActive
//            ));

        return userList.stream()
            .map(UserResponseDTO::of)
            .toList();
    }

    @Transactional
    public UserResponseDTO login(UserLoginRequestDTO userLoginRequestDTO){
        // 이름으로 받음 - 이메일아님
        User user = userRepository.getByEmailOrThrow(userLoginRequestDTO.email());
        user.verifyPassword(passwordEncoder, userLoginRequestDTO.password());

        UserStatus userStatus = userStatusRepository.getByUserIdOrThrow(user.getId());

        userStatus.login();

        return UserResponseDTO.of(user);
    }


    @Transactional
    public UserUpdateResponseDTO updateUser(UUID userId, UserUpdateRequestDTO userUpdateRequestDTO, MultipartFile profileImage){
        User user = userRepository.getByIdOrThrow(userId);

        if(!user.getEmail().equals(userUpdateRequestDTO.newEmail())){
            userService.validateEmailNotDuplicated(userUpdateRequestDTO.newEmail());
        }

        user.updateAllField(
            userUpdateRequestDTO.newUsername(),
            userUpdateRequestDTO.newEmail(),
            passwordEncoder.encode(userUpdateRequestDTO.newPassword())
        );

        if(Objects.nonNull(profileImage) && !profileImage.isEmpty()){
            BinaryContent binaryContent = storeProfileImage(profileImage);
            user.updateProfileImage(binaryContent);
        }

        return UserUpdateResponseDTO.from(user);
    }

    @Transactional
    public UserStatus updateUserStatus(UUID userId, Instant activeAt) {
        UserStatus userStatus = userStatusRepository.getByUserIdOrThrow(userId);

        userStatus.activateUser(activeAt);

        return userStatus;
    }

    private BinaryContent storeProfileImage(MultipartFile profileImage){
        return binaryApplicationService.storeMultipartFile(profileImage);
    }
}
