package sprint0.domain;

import java.time.LocalDateTime;

public class User {
    private final Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;

    //생성자 열어놨는데 빌더로 객체 생성해서 리턴하도록 하려합니다 - private으로 봐주세요
    //빌더로 받는게 맞는가 - 테스트 검증 시 매우 불편했음
    public User(Long userId, String name) {
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getName() {
        return name;
    }

    /*
        null을 넣어도되는지
     */
    public static User makeUser(String userDTO){
        return new User(null, userDTO);
    }
}
