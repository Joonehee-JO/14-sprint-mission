package sprint0.service.channel;

import sprint0.global.annotation.Comment;

public interface ChannelService {
    @Comment("채널 이동하는 메서드")
    Long moveChannel(Long targetChannelId, Long userId);

    @Comment("해당 유저가 속한 채널 꺼내오는 메서드")
    Long extractChannelId(Long userId);

    //@Comment("유저 꺼내오는 메서드")
    //List<User> extractUserId(Long channelId);
}
