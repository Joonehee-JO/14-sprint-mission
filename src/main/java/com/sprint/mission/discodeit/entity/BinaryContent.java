package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/*
    초기에 프로필 이미지인지 뭔지 그냥 채팅에만 파일을 첨부가능하게 하여
    필드에 유저아이디와 채널아이디, 파일이 저장될 주소정도만 갖게 설계를 생각했는데
    요구사항과 AI 를 통해 분석한 결과 요구사항에서는 어디서든 쓰일 수 있는 첨부파일 클래스를 만들고자 하였고
    여기까진 OK 였는데 <- 이러면 하나의 클래스로 모든 파일 개체를 담당할 수 있으니까

    필드를 설계해야하는데 그냥 여기 필드에 이게 프로필이미지 필드인지 채팅 필드에 작성된건지를 정의해서 만들고자하였지만(추가될때마다 새로운 필드를 추가하려했음 이넘으로라든지)
    이건 그냥 대충하는 방법이구

    --------AI 대안
    // User
    public class User {
        UUID id;
        UUID profileId; // BinaryContent의 PK를 FK로 참조
    }

    // Message
    public class Message {
        UUID id;
        List<UUID> attachmentIds; // BinaryContent의 PK들을 FK로 참조
    }

    ⭕ DB 외래키(FK)를 완벽하게 걸 수 있음
    User 테이블의 profile_id 컬럼은 BinaryContent 테이블의 id만 바라보는 명확한 FK가 됩니다. DB가 데이터 무결성을 확실하게 보장해 줍니다.

    ⭕ 객체지향적 역할 분담 (파일은 파일 일만 함)
    BinaryContent 입장에서는 "내가 어디에 쓰이는지"를 알 필요가 없습니다. 단순한 '파일 저장소의 파일 메타데이터 객체' 역할에 충실합니다.

    파일이 어디에 쓰이는지는 파일을 가져다 쓰는 주체(User, Message)가 책임집니다.
 */
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BinaryContent {
    @Builder.Default
    UUID id = UUID.randomUUID();
    String pathUrl;
    String fileName; //필요할까? 나는 그냥 파일주소만 정의했었음.
    String fileType; //이넘으로 정의해야할까?
    @Builder.Default
    Instant createdAd = Instant.now();
}
