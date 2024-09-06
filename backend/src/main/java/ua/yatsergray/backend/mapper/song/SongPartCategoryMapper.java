package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.SongPartCategoryDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartCategoryEditableDTO;
import ua.yatsergray.backend.domain.entity.song.SongPartCategory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SongPartCategoryMapper {

    SongPartCategoryMapper INSTANCE = Mappers.getMapper(SongPartCategoryMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "songParts", ignore = true)
    SongPartCategory mapToSongPartCategory(SongPartCategoryEditableDTO songPartCategoryEditableDTO);

    SongPartCategoryDTO mapToSongPartCategoryDTO(SongPartCategory songPartCategory);

    SongPartCategoryEditableDTO mapToSongPartCategoryEditableDTO(SongPartCategory songPartCategory);

    List<SongPartCategoryDTO> mapAllToSongPartCategoryDTOList(List<SongPartCategory> songPartCategories);
}
