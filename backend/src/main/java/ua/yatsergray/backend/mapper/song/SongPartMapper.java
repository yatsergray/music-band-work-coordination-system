package ua.yatsergray.backend.mapper.song;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.song.SongPartDTO;
import ua.yatsergray.backend.domain.entity.song.SongPart;
import ua.yatsergray.backend.domain.entity.song.SongPartKeyChord;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mapper(componentModel = "spring", uses = {SongPartCategoryMapper.class, SongPartKeyChordMapper.class})
public interface SongPartMapper {

    SongPartMapper INSTANCE = Mappers.getMapper(SongPartMapper.class);

    @Mapping(target = "text", expression = "java(fillSongPartTextByChordsAccordingToKey(songPart))")
    @Mapping(source = "songPartCategory", target = "songPartCategoryDTO")
    @Mapping(source = "songPartKeyChords", target = "songPartKeyChordDTOList")
    SongPartDTO mapToSongPartDTO(SongPart songPart);

    List<SongPartDTO> mapAllToSongPartDTOList(List<SongPart> songParts);

    default String fillSongPartTextByChordsAccordingToKey(SongPart songPart) {
        Map<String, String> chordSequenceNumbersAndNames = new LinkedHashMap<>();

        for (SongPartKeyChord songPartKeyChord : songPart.getSongPartKeyChords()) {
            chordSequenceNumbersAndNames.put(songPartKeyChord.getSequenceNumber().toString(), songPartKeyChord.getChord().getName());
        }

        Pattern patternChord = Pattern.compile("\\[(\\d+)]");
        Matcher matcherChord = patternChord.matcher(songPart.getText());

        StringBuilder result = new StringBuilder();

        while (matcherChord.find()) {
            String chordSequenceNumber = matcherChord.group(1);
            String replacement = chordSequenceNumbersAndNames.getOrDefault(chordSequenceNumber, matcherChord.group());

            matcherChord.appendReplacement(result, replacement);
        }

        matcherChord.appendTail(result);

        return result.toString();
    }
}
