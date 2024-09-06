package ua.yatsergray.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FileURLToFileNameMapper {

    @Named("fileURLToFileName")
    default String mapFileURLToFileName(String fileURL) {
        return "";
    }
}
