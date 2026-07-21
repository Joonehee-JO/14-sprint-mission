package global.exception;

public enum CustomErrorCode {
    INVALID_CHANNEL_ADMISSION("채널은 1~10번 채널로만 이동가능합니다"),
    INVALID_INIT_USER("계정을 생성하고 진행해주세요");

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
