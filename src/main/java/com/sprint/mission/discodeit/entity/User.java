package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/*
    todo --------------------질문 리스트-------------------
    1. 저는 프라이빗으로 감쌌는데 jpa 지연로딩특성으로 프로텍티드가 맞다하는데 맞을까요?
    @NoArgsConstructor(access = AccessLevel.PRIVATE)

    2. 컨트롤러에서 DTO 를 통째로 던져 서비스 계층에서 리포지토리 전달 전 엔터티 생성 메서드로 객체를 생성해 리포지토리로 전달하려했는데요.
    이러면 domain - web 계층에서 web의 DTO 영역과 엔터티 생성 메서드가 결합이 되므로 뭔가 매끄럽지 않다는 느낌이 들었습니다. 그래서
     - 서비스 계층에서 빌드를 하는게 맞는 것 같은데 이러면 서비스 또한 컨트롤러의 DTO 와 결합되는데 이건 괜찮은건가요?
     - 아니면 컨트롤러에서 DTO 필드를 하나하나 꺼내 서비스 계층으로 던져주는게 맞나요? - 이러면 또 서비스계층에서 빌더로 일일이 조립하나요?

    - 기존에는 아래와 같이 엔티티 클래스에서 생성 메서드를 제공하려했습니다.
    public static User makeUser(String name){
        return User.builder(유저 필드 완성)
    }


    3. 도메인 필드에 널이 들어있을 수 도 있는 경우(사용자 선택사항) 이해 안가는게 rdbms에서도 무결성을 지키기위해 디폴트값을 설정할 수 있고,
    엔터티 부분에서도 jpa 디폴트설정을 할 수 있으며 빌더 어노테이션으로도 디폴트를 쓸수있고 심지어 컨트롤러에서도 결측치가 있다면 디폴트값을 설정할 수 있는데
    이럼 너무 여러 계층에서 검증을 실시하게 되어 어지러웠습니다.
    -> ai로 공부결과 메모리 상에서의 개체상태(버그발생 확률 큼)와 실제 저장될 개체 상태가 다를 수 있어 빌더 어노테이션과 orm 어노테이션으로 디폴트값을 설정하고
    rdbms 제약조건으로도 디폴트 설정을 걸어놓는게 맞다고 이해했는데(컨트롤러와 서비스 부분에서 검증은 하지 않음) 이게 맞는 방법인가요?

    ---- 이런 질문들은 그냥 AI 한테 해답을 구하는게 나을가요? 제가 지식이 부족한 채로 프로젝트를 진행할 때(프로젝트 전사적으로 에이전트를 쓴게 아닌 단순히 작업 파일에 대해서만 콘솔창으로 질문했습니다)
    빌더 패턴으로 엔터티 영역에서 메서드로 제공해주는게 좋다는 피드백을 받아서 이대로 했었는데 지금 회고해보면 이는 잘못된 설계였단 생각이 들어서요. AI 가 항상 맞는 말을 하는지 모르겠습니다.
 */



/*
    1. 빌더 패턴으로 서비스계층에서 엔터티 객체를 생성하기 위해 메서드를 정의
    2. 빌더 어노테이션으로 중간 누락되는 필드가 발생하지 않게 방지
    3. 모든인자생성자를 프라이빗으로 감싸 빌더 메서드에서 사용할 수 있게 정의
    4. JPA 는 리플렉션(클래스를 복제해서 들고있는 것)을 통해 빈 깡통을 만들고 여기에 세터를 통한 조립으로 개체를 완성하기에 빈생성자 필수
 */
@ToString
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User implements Serializable, IdMapper {
    private static final long serialVersionUID = 1L;

    UUID id;
    String userId;
    String userPassword;
    String name;
    String email;
    Integer age;
    UUID profileId; //프로필 사진 아이디 // 널이 들어올 수 있음 -> 널값을 허용할 것이기에 디비 계층 처리 안해도될듯
    @Builder.Default
    Instant createdAt = Instant.now();
    @Builder.Default
    Instant updatedAt = Instant.now();

    //일단 이름만 단순하게 수정
    public void update(String name){
        this.name = name;
        updatedAt = Instant.now();
    }
}
