package com.sprint.mission.discodeit.service.user;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.web.controller.user.dto.UserCreateRequestDTO;
import global.exception.CustomErrorCode;
import global.exception.CustomException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

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

            어려워서 스킵
         */

        User createUser = User.builder().email(userCreateRequestDTO.getEmail())
            .userPassword(userCreateRequestDTO.getUserPassword())
            .name(userCreateRequestDTO.getName())
            .age(userCreateRequestDTO.getAge())
            .profileId(null)        // 그냥 안들어왔다 가정
            .build();

        //유저스테이터스 생성 제외

        return userRepository.saveEntity(createUser);
    }

    @Override
    public User findById(UUID id) {
        userRepository.findById(id);
    }

    @Override
    public User updateUser(UUID id, String name) {
        return null;
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAllEntity();
    }

    @Override
    public void deleteUser(UUID id) {

    }
}
