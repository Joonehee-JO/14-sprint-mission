package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.message.domain.entity.Message;
import com.sprint.mission.discodeit.message.web.dto.MessageResponseDTO;
import com.sprint.mission.discodeit.user.web.dto.res.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = BinaryContentMapper.class
)
public interface MessageMapper {
    @Mapping(target = "channelId", source = "message.channel.id")
    @Mapping(target = "author", source = "author")                          // UserResponseDTO - 내부 online 조립
    @Mapping(target = "attachments", source = "message.imageList")          // BinaryRes - BM
    MessageResponseDTO toResponse(Message message, UserResponseDTO author);
}
// 매퍼가 더 보기힘든거같은데