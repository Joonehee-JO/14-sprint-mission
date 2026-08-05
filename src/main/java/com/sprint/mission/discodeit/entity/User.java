
package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.IdMapper;
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
    1. 컨트롤러에서 DTO 를 통째로 던져 서비스 계층에서 리포지토리 전달 전 엔터티 생성 메서드로 객체를 생성해 리포지토리로 전달하려했는데요.
    이러면 domain - web 계층에서 web의 DTO 영역과 엔터티 생성 메서드가 결합이 되므로 뭔가 매끄럽지 않다는 느낌이 들었습니다. 그래서
     - 서비스 계층에서 빌드를 하는게 맞는 것 같은데 이러면 서비스 또한 컨트롤러의 DTO 와 결합되는데 이건 괜찮은건가요?
     - 아니면 컨트롤러에서 DTO 필드를 하나하나 꺼내 서비스 계층으로 던져주는게 맞나요? - 이러면 또 서비스계층에서 빌더로 일일이 조립하나요?

    - 기존에는 아래와 같이 엔티티 클래스에서 생성 메서드를 제공하려했습니다.
    public static User makeUser(String name){
        return User.builder(유저 필드 완성)
    }


    2. 도메인 필드에 널이 들어있을 수 도 있는 경우(사용자 선택사항) 이해 안가는게 rdbms에서도 무결성을 지키기위해 디폴트값을 설정할 수 있고,
    엔터티 부분에서도 jpa 디폴트설정을 할 수 있으며 빌더 어노테이션으로도 디폴트를 쓸수있고 심지어 컨트롤러에서도 디폴트값을 설정할 수 있는데
    이럼 너무 여러 계층에서 검증을 실시하게 되어 어지러웠습니다.
    -> ai로 공부결과 메모리 상에서의 개체상태(버그발생 확률 큼)와 실제 저장될 개체 상태가 다를 수 있어 빌더 어노테이션과 orm 어노테이션으로 디폴트값을 설정하고
    rdbms 제약조건으로도 디폴트 설정을 걸어놓는게 맞다고 이해했는데(컨트롤러와 서비스 부분에서 검증은 하지 않음) 이게 맞는 방법인가요?

*/



/*
    1. 빌더 패턴으로 서비스계층에서 엔터티 객체를 생성하기 위해 메서드를 정의
    2. 빌더 어노테이션으로 중간 누락되는 필드가 발생하지 않게 방지
    3. 모든인자생성자를 프라이빗으로 감싸 빌더 메서드에서 사용할 수 있게 정의
    4. JPA 는 리플렉션(클래스를 복제해서 들고있는 것)을 통해 빈 깡통을 만들고 여기에 세터를 통한 조립으로 개체를 완성하기에 빈생성자 필수
 */


/*
    ------- todo : 복습용 ----------
    컨트롤러는 어플리케이션 호출  > 어플리케이션에서 도메인 꺼내고 "갱신하고" 저장 > 서비스 저장 실행 > 리포지토리 저장
 */
@ToString
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class User implements Serializable, IdMapper {
    private static final long serialVersionUID = 1L;

    @Builder.Default
    UUID id = UUID.randomUUID();        //객체 생성 시 자동 할당

    //필수 입력 란
    String email;
    String userPassword;
    String name;
    Integer age;

    @Builder.Default
    UUID profileId = UUID.fromString("00000000-0000-0000-0000-000000000000");      //todo 프로젝트에서 제공하는 기본 이미지를 설정 - 추후 리팩토링
    @Builder.Default
    Instant createdAt = Instant.now();
    @Builder.Default
    Instant updatedAt = Instant.now();


    public static User init(String email, String userPassword, String name, Integer age){
        return User.builder().
            email(email).userPassword(userPassword).name(name).age(age).build();
    }

    public void updateName(String name){
        this.name = name;
        updatedAt = Instant.now();
    }

    //프로필 이미지 업데이트 시
    public void updateProfileImage(UUID id){
        this.id = id;
    }
}
