package com.sprint.mission.discodeit.service.user;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.binarycontent.BinaryContentService;
import com.sprint.mission.discodeit.web.controller.user.dto.UserCreateRequestDTO;
import global.exception.CustomErrorCode;
import global.exception.CustomException;
import java.util.List;
import java.util.Objects;
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
            BinaryContent binaryContent  = binaryContentService.storeFile(userCreateRequestDTO.getProfileImage());
            profileImageId = binaryContent.getId();
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
//        User user = userRepository.findById(id)
//            .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        //이미 만든거 재활용하면됨.
        User user = this.findById(id);

        //개체 상태 변경해서 바로 리포지토리 세이브 호출 - 따로 업데이트 메서드 없이 저장메서드 실행하면됨 (Map<User.getId, User> 형태임)
        user.update(name);

        userRepository.saveEntity(user);
        return user;
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAllEntity();
    }

    @Override
    public void deleteUser(UUID id) {

        /*
            자기거 지가 사용
            예외 터졌다면 저 메서드에서 래핑해줬으니 바로 삭제메서드 호출
         */
        User user = this.findById(id);


        userRepository.deleteEntity(id);
    }
}
