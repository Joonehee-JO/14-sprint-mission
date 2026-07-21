package global.exception;

public enum CustomErrorCode {
    INVALID_CHANNEL_ADMISSION("채널은 1~10번 채널로만 이동가능합니다"),
    INVALID_USER_INIT("계정을 생성하고 진행해주세요"),
    INVALID_USER_NAME_EMPTY("이름을 제대로 입력해주세요"),
    INVALID_MESSAGE_EMPTY("문자 메시지를 입력하여 보내주세요"),
    INVALID_MESSAGE_MAX_LENGTH("문자 메시지는 최대 500글자만 가능합니다");

    private final String message;

    private CustomErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "CustomErrorCode{" +
            "message='" + message + '\'' +
            '}';
    }
}
