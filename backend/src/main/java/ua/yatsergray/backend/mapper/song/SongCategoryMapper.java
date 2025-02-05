package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.SongCategoryDTO;
import ua.yatsergray.backend.domain.entity.song.SongCategory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SongCategoryMapper {

    SongCategoryMapper INSTANCE = Mappers.getMapper(SongCategoryMapper.class);

    SongCategoryDTO mapToSongCategoryDTO(SongCategory songCategory);

    List<SongCategoryDTO> mapAllToSongCategoryDTOList(List<SongCategory> songCategories);
}
