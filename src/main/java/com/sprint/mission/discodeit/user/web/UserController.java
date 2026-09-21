package com.sprint.mission.discodeit.user.web;

import com.sprint.mission.discodeit.user.domain.entity.UserStatus;
import com.sprint.mission.discodeit.user.application.UserApplicationService;
import com.sprint.mission.discodeit.user.web.dto.UserCreateRequestDTO;
import com.sprint.mission.discodeit.user.web.dto.UserStatusUpdateRequestDTO;
import com.sprint.mission.discodeit.user.web.dto.UserUpdateRequestDTO;
import com.sprint.mission.discodeit.user.web.dto.UserResponseDTO;
import com.sprint.mission.discodeit.user.web.dto.UserStatusResponseDTO;
import com.sprint.mission.discodeit.user.web.dto.UserUpdateResponseDTO;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserApplicationService userApplicationService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAllUserAccount(){
        List<UserResponseDTO> response = userApplicationService.findAllUser();

        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUserAccount(
        @RequestPart(value = "userCreateRequest") UserCreateRequestDTO userCreateRequestDTO,
        @RequestPart(value = "profile", required = false) MultipartFile profileImage
    ){
        UserResponseDTO response = userApplicationService.createAccount(userCreateRequestDTO, profileImage);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserAccount(@PathVariable UUID userId){
        userApplicationService.deleteUserAccount(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build();
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserUpdateResponseDTO> updateUserAccount(
        @PathVariable UUID userId ,
        @RequestPart(value = "userUpdateRequest") UserUpdateRequestDTO userUpdateRequestDTO,
        @RequestPart(value = "profile", required = false) MultipartFile profileImage
    ){
        UserUpdateResponseDTO response = userApplicationService.updateUser(userId,
            userUpdateRequestDTO, profileImage);

        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }

    @PatchMapping("/{userId}/userStatus")
    public ResponseEntity<UserStatusResponseDTO> activateUserStatus(@PathVariable UUID userId, @RequestBody
        UserStatusUpdateRequestDTO userStatusUpdateRequestDTO){
        UserStatus updatedUserStatus = userApplicationService.updateUserStatus(userId,
            userStatusUpdateRequestDTO.newLastActiveAt());

        UserStatusResponseDTO response = UserStatusResponseDTO.from(updatedUserStatus);

        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }
}
