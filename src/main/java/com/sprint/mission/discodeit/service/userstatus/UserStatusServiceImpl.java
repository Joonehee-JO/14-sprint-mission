package com.sprint.mission.discodeit.service.userstatus;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserStatusServiceImpl implements UserStatusService{
    private final UserStatusRepository userStatusRepository;

    @Override
    public UserStatus createUserStatus(UserStatus userStatus) {
        if(Objects.isNull(userStatus)){
            throw new IllegalArgumentException("null이 들어오면 안됨");
        }

        if(userStatusRepository.findUserStatusByUserId(userStatus.getUserId()).isPresent()){
            log.info("해당 유저아이디를 필드로 갖는 개체가 존재함 들어온 유저아이디 : {}", userStatus.getUserId());
            throw new IllegalArgumentException("해당 유저아이디를 필드로 갖는 개체가 존재함");
        }

        return userStatusRepository.saveEntity(userStatus);
    }

    @Override
    public UserStatus findUserStatus(UUID userStatusId) {
        if(Objects.isNull(userStatusId)){
            throw new IllegalArgumentException("null이 들어오면 안됨");
        }

        return userStatusRepository.findById(userStatusId)
            .orElseThrow(() -> new IllegalArgumentException("해당 id 개체 존재하지 않음"));
    }

    @Override
    public List<UserStatus> findAllUserStatus() {
        return userStatusRepository.findAllEntity();
    }

    @Override
    public UserStatus updateUserStatusByUserId(UUID userId) {
        if(Objects.isNull(userId)){
            throw new IllegalArgumentException("null이 들어오면 안됨");
        }

        UserStatus userStatus = userStatusRepository.findUserStatusByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 유저아이디를 필드로 가진 개체가 없음"));

        userStatus.activateUser();

        return userStatusRepository.saveEntity(userStatus);
    }

    @Override
    public void deleteUserStatusByUserId(UUID userId) {
        if(Objects.isNull(userId)){
            throw new IllegalArgumentException("null이 들어오면 안됨");
        }

        UserStatus userStatus = userStatusRepository.findUserStatusByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 유저아이디를 필드로 가진 개체가 없음"));

        userStatusRepository.deleteEntity(userStatus.getId());
    }
}
