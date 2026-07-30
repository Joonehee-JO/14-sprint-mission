package sprint0.controller.channel;

import global.exception.CustomErrorCode;
import global.exception.CustomException;

public class ChannelDTO{
    private Long channelId;
    private Long userId;

    //채널 이동시에는 무조건 유저 아이디와 같이 묶임
    public ChannelDTO(Long channelId, Long userId) {
        validChannelId(channelId, userId);
        this.channelId = channelId;
        this.userId = userId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public Long getUserId() {
        return userId;
    }

    /*
            @Valid 역할 / 서비스에서도 체크하고 입력검증에서도 한번 더 체크
            근데 타입 체크나 표현식 체크 이런건 어떻게 할지 잘 모르겠음
         */
    private void validChannelId(Long channelId, Long userId){
        if(userId == null) throw new CustomException(CustomErrorCode.INVALID_USER_INIT);
        if(channelId <= 0 || channelId > 10) throw new CustomException(CustomErrorCode.INVALID_CHANNEL_ADMISSION);
    }
}
