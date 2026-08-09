package global.exception;

public enum CustomErrorCode {
    INVALID_CHANNEL_ADMISSION("채널은 1~10번 채널로만 이동가능합니다"),
    INVALID_USER_INIT("계정을 생성하고 진행해주세요"),
    INVALID_USER_NAME_EMPTY("이름을 제대로 입력해주세요"),
    INVALID_MESSAGE_EMPTY("문자 메시지를 입력하여 보내주세요"),
    INVALID_MESSAGE_MAX_LENGTH("문자 메시지는 최대 500글자만 가능합니다"),

    //새로 추가부분(어떤 서비스 예외인지 다 나눠야 할듯)
    INVALID_USER_DUPLICATE_EMAIL("이미 존재하는 계정입니다."),
    USER_NOT_FOUND("유저 조회를 실패하였습니다");

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
