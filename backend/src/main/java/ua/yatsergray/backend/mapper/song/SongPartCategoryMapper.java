package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.SongPartCategoryDTO;
import ua.yatsergray.backend.domain.entity.song.SongPartCategory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SongPartCategoryMapper {

    SongPartCategoryMapper INSTANCE = Mappers.getMapper(SongPartCategoryMapper.class);

    SongPartCategoryDTO mapToSongPartCategoryDTO(SongPartCategory songPartCategory);

    List<SongPartCategoryDTO> mapAllToSongPartCategoryDTOList(List<SongPartCategory> songPartCategories);
}
