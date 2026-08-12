package com.sprint.mission.discodeit.service.user;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.binarycontent.BinaryContentService;
import com.sprint.mission.discodeit.service.userstatus.UserStatusService;
import com.sprint.mission.discodeit.web.controller.dto.req.UserCreateRequestDTO;
import global.exception.CustomErrorCode;
import global.exception.CustomException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/*
    todo : 리팩토링
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BinaryContentService binaryContentService;
    private final UserStatusService userStatusService;

    @Override
    public User createUser(User user) {
        userRepository.findByEmail(user.getEmail())
            .ifPresent(email -> {
                throw new CustomException(CustomErrorCode.USER_DUPLICATE_EMAIL);
            });

        return userRepository.saveEntity(user);
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));
    }

    //일단 이름만 변경 가능 하도록 설계
    @Override
    public User updateUser(UUID id, String name) {
        User user = this.findById(id);

       user.updateName(name);

        return userRepository.saveEntity(user);
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAllEntity();
    }

    /*
        삭제메서드는 단독 호출로 불리지 않는다
     */
    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteEntity(id);
    }

    @Override
    public boolean existAllByIdList(List<UUID> idList) {
        log.info("--------------- info {} ", idList);
        return userRepository.existAllById(idList);
    }
}
