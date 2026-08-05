package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/*
    todo: 복습-------------
    빌더를 공부 전 -> 그냥 개발자가 어딘가에서 new 를 통해 인자를 만들 때 빠뜨리는 부분이나 순서, 가독성을 높이기위한 패턴으로 알음

    빌더는 가독성을 높여주기도 하지만 개체 자체로 어딘가에서 생성되고 수정되는 것을 막는 완전성을 지키기위한 방법이며
    필수 인자를 넣어서 어딘가에서 개체를 생성할 때 필수 필드를 놓치지않게 설정할 수 있음
    그러면 도메인 계층은 그자체로 완성되고 이 개체를 만드는 역할은 DTO 에서 담당
    DTO 에서 사용자가 던지거나 서비스에서 사용될 필드값을 설정해서 서비스로 던지면 서비스는 이 필드를 검증할 필요없이
    전처리와 후처리를 하여 리포지토리 컨트롤러 사이의 중간 다리 역할을 수행. 디비 관련 예외만을 검증

    이외에도 개체 자체에서 스태틱 메서드로 조립해 개체를 리턴해주는 정적 팩토리 메서드를 활용할 수 있는데 위의 방법을 선호
 */
@ToString
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Channel implements Serializable, IdMapper {
    private static final long serialVersionUID = 1L;

    @Builder.Default
    UUID id = UUID.randomUUID();

    String channelName;
    ChannelType channelType;    //생성 시점에 만들어지는 필수값으로 변경 디폴트값 삭제

    @Builder.Default
    Instant createdAt = Instant.now();
    @Builder.Default
    Instant updatedAt = Instant.now();

    static public Channel init(String channelName, ChannelType channelType){
        return Channel.builder()
            .channelName(channelName).channelType(channelType).build();
    }

    public void updateChannelName(String channelName){
        this.channelName = channelName;
        this.updatedAt = Instant.now();
    }
}
