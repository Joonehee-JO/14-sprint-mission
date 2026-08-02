package com.sprint.mission.discodeit.service.user;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.web.controller.user.dto.UserCreateRequestDTO;
import global.exception.CustomErrorCode;
import global.exception.CustomException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BinaryContentService binaryContentService;

    @Override
    public User createUser(UserCreateRequestDTO userCreateRequestDTO) {
        userRepository.findByEmail(userCreateRequestDTO.getEmail())
            .ifPresent(user -> {
                throw new CustomException(CustomErrorCode.INVALID_USER_DUPLICATE_EMAIL);
            });

        /*
            DTO 에서 바이너리 컨텐트 들어오면
            1. 바이너리 컨텐트 저장 서비스 호출
            2. 서비스 호출 후 받아온 실제 저장소 매핑

            안들어올 경우
            1. 그냥 널로 세팅
         */
        UUID profileImageId = null;
        if(!Objects.isNull(userCreateRequestDTO.getProfileImage())){
            try{
                BinaryContent binaryContent  = binaryContentService.storeFile(userCreateRequestDTO.getProfileImage());
                profileImageId = binaryContent.getId();
            }catch (IOException e){
                log.error("파일 저장 에러 발생", e);
                throw new RuntimeException("파일 저장 문제 발생"); //todo : 커스텀 예외 만들기
            }
        }

        User createUser = User.builder().email(userCreateRequestDTO.getEmail())
            .userPassword(userCreateRequestDTO.getUserPassword())
            .name(userCreateRequestDTO.getName())
            .age(userCreateRequestDTO.getAge())
            .profileId(profileImageId)
            .build();

        //유저스테이터스 생성 제외

        return userRepository.saveEntity(createUser);
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));
    }

    //일단 이름만 변경 가능 하도록 설계
    @Override
    public User updateUser(UUID id, String name) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        user.update(name);

        //todo : 리포지토리 업데이트 메서드 호출
        return user;
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAllEntity();
    }

    @Override
    public void deleteUser(UUID id) {
        /*
            todo delete
            관련된 도메인도 같이 삭제합니다.
            BinaryContent(프로필), UserStatus
         */

        userRepository.deleteEntity(id);
    }
}
