package service.channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
    초기 그냥 간략하게 하고자 채널 도메인이나 리포지토리 구현 스킵했는데 진행하다 보니 해당 기능이 필요함을 느낌
    일단 리포지토리 기능을 서비스로 구현했는데
    실무에서도 이렇게되면 다시 구현하는지 그냥 애초에 이럴일이 없는지
 */
public class ChannelServiceImpl implements ChannelService{
    //<유저번호, 채널번호> 로 구현
    private final Map<Long, Long> struct = new ConcurrentHashMap<>();

    @Override
    public Long moveChannel(Long targetChannelId, Long userId) {
        if(targetChannelId < 1 || targetChannelId > 10) throw new IllegalArgumentException("1~10번 채널 이동만 가능");

        struct.put(userId, targetChannelId);
        return targetChannelId;
    }

    @Override
    public Long extractChannelId(Long userId) {
        if(struct.containsKey(userId)){
            return struct.get(userId);
        }

        throw new IllegalArgumentException("먼저 채널을 선택하여 주세요");
    }
}
