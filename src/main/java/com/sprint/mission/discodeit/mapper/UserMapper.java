package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.user.domain.entity.User;
import com.sprint.mission.discodeit.user.web.dto.res.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = BinaryContentMapper.class
)
public interface UserMapper {
    @Mapping(target = "username", source = "user.name")
    @Mapping(target = "profile", source = "user.profileImage")
    @Mapping(target = "online", source = "online")
    UserResponseDTO toResponse(User user, boolean online);
}
/*
    todo : ㅈㅁ
    그러니까 지금 유저로부터는 유저스테이터스를 탐색할 그래프가 존재하지 않아서 서비스 내부에서 조합해야함 (단방향)
    근데 유저 정보를 응답으로 내려줄때 해당 유저 상태를 조회해 온라인상태를 함께 포함시킴.
    이때 바로 탐색할 수 있게 양방향관계로 둬도되는지
 */
