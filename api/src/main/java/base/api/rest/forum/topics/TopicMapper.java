package base.api.rest.forum.topics;

import base.api.config.mapper.MappersConfig;
import base.api.domain.forum.topics.TopicEntity;
import base.api.domain.forum.topics.TopicWithPostsEntity;
import base.api.rest.forum.topics.dto.TopicDataDto;
import base.api.rest.forum.topics.dto.TopicRequestDto;
import base.api.rest.forum.topics.dto.TopicResponseDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(config = MappersConfig.class)
@Service
public interface TopicMapper {

  TopicResponseDto entityToDto(TopicEntity entity);

  TopicDataDto entityToDataDto(TopicEntity entity);

  TopicResponseDto topicEntityWithPostToDto(TopicWithPostsEntity entity);

  TopicEntity requestDtoToEntity(TopicRequestDto dto);
}
