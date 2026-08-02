package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Message implements Serializable, IdMapper {
    private static final long serialVersionUID = 1L;

    UUID id = UUID.randomUUID();
    UUID userId;
    UUID channelId;
    String content;

    /*
        메시지에 첨부파일이 들어올 수 있을 수 있기에 리스트로 처리
        필드메시지는 이 리스트를 갖고 있을수도 있고 안갖고있을수도 있음
        이미지를 올리지 않았을 때 이게 들어있는지 체크 중 런타임에러가 날 수 있으므로 빈 리스트를 디폴트로 들고 있게하라함
        -------todo 질문리스트 -----------
        궁금한 것 -> 이러면 무수히 많은 메시지가 생성될때마다 필요치않은 필드가 생기는데 이건 메모리 낭비 아닌가요?
     */
    @Builder.Default
    List<BinaryContent> imageList = new ArrayList<>();
    @Builder.Default
    Instant createdAt = Instant.now();
    @Builder.Default
    Instant updatedAt = Instant.now();

    public void update(String content){
        this.content = content;
        this.updatedAt = Instant.now();
    }
}
