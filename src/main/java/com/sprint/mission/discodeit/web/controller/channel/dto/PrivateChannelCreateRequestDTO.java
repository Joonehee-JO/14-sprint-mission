package com.sprint.mission.discodeit.web.controller.channel.dto;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
    todo : 복습용 -----------------------------------
    사용자가 대충(선택사항으로) 고른건 기본값으로  /// 실수한 것은 밸리드로 검증 . 필수선택도 포함

    디비에서 NULL 을 바라보는 관점
    1. 아직 들어오지 않은 값 - 이벤트가 발생해야 정해짐.
    2. 존재할 수 없는 상태 - 여자인데 병역여부
    3. 결측값(선택안함) - 사용자의 실수 또는 선택여부로 들어오지 않은값
    4. 절대 널이 들어오면 안되는데 널이 들어온 경우

    스프링에서 이를 처리하는 방법
    1 -> 쿠팡 배송 테이블의 배달사필드 or 우리 프로젝트에서 업데이트 필드 // 뭔가 이벤트가 발생해야 값이 정해짐. 이것또한 디폴트값으로
    처리할지 널로 처리할지 결정해야함
        1. 널 자체를 물리적인 정보로 보고 싶다면 -> 빌드시에 널로 설정하고 그대로 디비저장
        2. 1번으로 처리한다면 널값을 갖고 프로그래밍을 하게되는데 이게 싫다면 초기 만들어질 때 기본값을 설정
        ---------- 널 자체를 물리적인 값으로 보는 1번을 권장

    2 ->

    3 -> 사용자 프로필 이미지 // 선택사항이지만 안 정했을 경우 널이 들어옴. 그럼 이걸 디비에서 널로 처리해야할지 기본값으로 처리해야할지
    골라야함. 기본값으로 처리한다면 개체 생성 시점에 빌더 디폴트로 정해주고 널로 처리한다면 그 상태 그대로 저장 // 디폴트값 설정 권장

    4 -> 사용자 이름 , 채널 이름 // 검증 단계에서 1차로 필터링 및 2,3차 진행
    [1차: DTO 검증] ──> [2차: Entity / Objects.requireNonNull] ──> [3차: DB NOT NULL] (ORM 도 담당)
 */


@Setter
@Getter
@AllArgsConstructor
public class PrivateChannelCreateRequestDTO {
    String channelName;
    List<UUID> userList;        //두 필드는 4번에 해당
}
