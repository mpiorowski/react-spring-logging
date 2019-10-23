package base.api.rest.forum.categories;

import base.api.config.mapper.MappersConfig;
import base.api.domain.forum.categories.CategoryEntity;
import base.api.domain.forum.categories.CategoryNewestEntity;
import base.api.rest.forum.categories.dto.CategoryAdditionalDto;
import base.api.rest.forum.categories.dto.CategoryRequestDto;
import base.api.rest.forum.categories.dto.CategoryRespondDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(config = MappersConfig.class)
@Service
public interface CategoryMapper {

  CategoryRespondDto entityToDto(CategoryEntity categoryEntity);

  CategoryEntity dtoToEntity(CategoryRequestDto categoryRequestDto);

  CategoryRespondDto categoriesEntitiesToDto(CategoryEntity categoryEntity, CategoryNewestEntity categoryNewestEntity);
}
