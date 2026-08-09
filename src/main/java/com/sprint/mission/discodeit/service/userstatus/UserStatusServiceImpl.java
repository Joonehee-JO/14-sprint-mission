package com.sprint.mission.discodeit.service.userstatus;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/*
    todo
    나는 처음에 서비스가 비대해져서 s1 s2 로 계층을 나누려했고
    이러다보니 그냥 간단한 데이터 흐름 컨트롤이 중복되는거 같아서 별로라고 생각했는데
    또 이걸 서비스 계층 하나로 합칠 것을 다시 생각해봤는데
    이럼 메시지서비스에서 메시지를 생성하는 흐름을 생각해봄
    그럼 메시지 서비스에서 해당 유저가 존재하는지 호출하고
    유저스테이터스 서비스에서 유저를 업데이트하고
    이 반복을 작성했다 치자
    그럼 이후에 또 다른 서비스를 만들때 뭔가 서비스를 호출하고 호출하면서 처음에는 적게 서비스를 호출하다가
    나중에는 서비스 호출 범벅이 될것같기도 하다는 느낌이 들음
    이건 맞는건가? 그냥 작성해보지도 않고 생각해서 더러운건가

    그러니까 제 고민은 그냥 처음에 단순하게 crud 요구사항을 짤 때는 서비스 결합이 없었는데 요구사항이 추가되면서 기존 코드에 다른 서비스를 호출하는일이
     잦아지고 이러다보면 나중에 서비스를 계속 호출하게 될수도있다가 맞는 생각인지

     그럼 이게 요구사항이 추가될때마다 서비스 호출 흐름을 어디서 추가해야하는지 이러다보면
     서비스 호출 덩어리가 될거같은데 이 호출을 어디 서비스에서 제어하는게맞고
     그럼 또다시 다른 서비스 호출을 다른데두면 어딘가에서 어디를 호출하는지 모를거같기도하고
     이거땜에 서비스를 계층을 나눈다가 맞는거같음

     나는 그냥 단순 프로젝트는 이렇게 계층을 나누는게 아닌거같다라는 생각을 했었는데 다시 생각해보면 계층을 나누는게 맞는거같다는거임


     UserStatusService 고도화
create
[ ] DTO를 활용해 파라미터를 그룹화합니다.
[ ] 관련된 User가 존재하지 않으면 예외를 발생시킵니다.
[ ] 같은 User와 관련된 객체가 이미 존재하면 예외를 발생시킵니다.
 */
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
