package ua.yatsergray.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ua.yatsergray.backend.domain.dto.band.BandDTO;
import ua.yatsergray.backend.domain.dto.band.BandSongVersionDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandEditableDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandSongVersionEditableDTO;
import ua.yatsergray.backend.domain.dto.song.*;
import ua.yatsergray.backend.domain.dto.song.editable.*;
import ua.yatsergray.backend.domain.type.song.SongPartCategoryType;
import ua.yatsergray.backend.service.band.impl.BandServiceImpl;
import ua.yatsergray.backend.service.band.impl.BandSongVersionServiceImpl;
import ua.yatsergray.backend.service.song.impl.*;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

//    @Bean
//    CommandLineRunner runner(
//            ArtistServiceImpl artistService,
//            KeyServiceImpl keyServiceImpl,
//            TimeSignatureServiceImpl timeSignatureServiceImpl,
//            SongServiceImpl songServiceImpl,
//            SongPartServiceImpl songPartServiceImpl,
//            SongPartCategoryServiceImpl songPartCategoryServiceImpl,
//            SongPartDetailsServiceImpl songPartDetailsServiceImpl, SongPartKeyChordServiceImpl songPartKeyChordServiceImpl, ChordServiceImpl chordServiceImpl, BandServiceImpl bandServiceImpl, BandSongVersionServiceImpl bandSongVersionServiceImpl) {
//        return args -> {
//            ArtistDTO artist1 = artistService.addArtist(
//                    ArtistEditableDTO.builder()
//                            .name("artist1")
//                            .build()
//            );
//
//            KeyDTO key1 = keyServiceImpl.addKey(
//                    KeyEditableDTO.builder()
//                            .name("key1")
//                            .build()
//            );
//            KeyDTO key2 = keyServiceImpl.addKey(
//                    KeyEditableDTO.builder()
//                            .name("key2")
//                            .build()
//            );
//            KeyDTO key3 = keyServiceImpl.addKey(
//                    KeyEditableDTO.builder()
//                            .name("key3")
//                            .build()
//            );
//            KeyDTO key4 = keyServiceImpl.addKey(
//                    KeyEditableDTO.builder()
//                            .name("key4")
//                            .build()
//            );
//
//            TimeSignatureDTO timeSignature1 = timeSignatureServiceImpl.addTimeSignature(
//                    TimeSignatureEditableDTO.builder()
//                            .beats(4)
//                            .duration(4)
//                            .build()
//            );
//
//            SongDTO song1 = songServiceImpl.addSong(
//                    SongEditableDTO.builder()
//                            .name("song1")
//                            .artistId(artist1.getId())
//                            .keyId(key1.getId())
//                            .timeSignatureId(timeSignature1.getId())
//                            .bpm(60)
//                            .build()
//            );
//            SongDTO song2 = songServiceImpl.addSong(
//                    SongEditableDTO.builder()
//                            .name("song2")
//                            .artistId(artist1.getId())
//                            .keyId(key2.getId())
//                            .timeSignatureId(timeSignature1.getId())
//                            .bpm(80)
//                            .build()
//            );
//
//            song1 = songServiceImpl.addSongKey(
//                    song1.getId(),
//                    SongKeyEditableDTO.builder()
//                            .keyId(key2.getId())
//                            .build()
//            );
//            song1 = songServiceImpl.addSongKey(
//                    song1.getId(),
//                    SongKeyEditableDTO.builder()
//                            .keyId(key3.getId())
//                            .build()
//            );
//            song2 = songServiceImpl.addSongKey(
//                    song2.getId(),
//                    SongKeyEditableDTO.builder()
//                            .keyId(key1.getId())
//                            .build()
//            );
//            song2 = songServiceImpl.addSongKey(
//                    song2.getId(),
//                    SongKeyEditableDTO.builder()
//                            .keyId(key4.getId())
//                            .build()
//            );
//
//            SongPartCategoryDTO songPartCategory1 = songPartCategoryServiceImpl.addSongPartCategory(
//                    SongPartCategoryEditableDTO.builder()
//                            .name("Verse")
//                            .type(SongPartCategoryType.VERSE)
//                            .build()
//            );
//            SongPartCategoryDTO songPartCategory2 = songPartCategoryServiceImpl.addSongPartCategory(
//                    SongPartCategoryEditableDTO.builder()
//                            .name("Chorus")
//                            .type(SongPartCategoryType.CHORUS)
//                            .build()
//            );
//
//            String songPart1Text = """
//                    [1]           [2]
//                    Все я віддаю Тобі:
//                                 [3]
//                    Нагороди й тягарі.
//                                   [4]
//                    Перед Тобою схиляюсь,
//                                  [5]
//                    Перед Тобою схиляюсь.
//                    """;
//            String songPart2Text = """
//                    [1]           [2]
//                    Я Тобі поступлюсь, Ісус,
//                                     [3]
//                    Роби усе, що Ти хочеш, Бог,
//                                     [4]
//                    Роби усе, що Ти хочеш.
//                    """;
//            String songPart3Text = """
//                           [1]
//                    Невидиму зброю нам дав
//                             [2]
//                    Щоб ворог схилився і впав
//                            [3]
//                    Коли славлю Тебе, Бог
//                        [4]
//                    Коли є навколо біда
//                    """;
//            String songPart4Text = """
//                    [1]        [2]
//                    Ісус, Ти переміг
//                          [3]
//                    Весь світ у Твоїх ніг
//                               [4]
//                    Ти правиш навіки
//                    """;
//
//            SongPartDTO songPart1 = songPartServiceImpl.addSongPart(
//                    SongPartEditableDTO.builder()
//                            .songId(song1.getId())
//                            .songPartCategoryId(songPartCategory1.getId())
//                            .typeNumber(1)
//                            .text(songPart1Text)
//                            .build()
//            );
//            SongPartDTO songPart2 = songPartServiceImpl.addSongPart(
//                    SongPartEditableDTO.builder()
//                            .songId(song1.getId())
//                            .songPartCategoryId(songPartCategory2.getId())
//                            .typeNumber(1)
//                            .text(songPart2Text)
//                            .build()
//            );
//            SongPartDTO songPart3 = songPartServiceImpl.addSongPart(
//                    SongPartEditableDTO.builder()
//                            .songId(song2.getId())
//                            .songPartCategoryId(songPartCategory1.getId())
//                            .typeNumber(1)
//                            .text(songPart3Text)
//                            .build()
//            );
//            SongPartDTO songPart4 = songPartServiceImpl.addSongPart(
//                    SongPartEditableDTO.builder()
//                            .songId(song2.getId())
//                            .songPartCategoryId(songPartCategory2.getId())
//                            .typeNumber(1)
//                            .text(songPart4Text)
//                            .build()
//            );
//
//            SongPartDetailsDTO songPartDetails1 = songPartDetailsServiceImpl.addSongPartDetails(
//              SongPartDetailsEditableDTO.builder()
//                      .songId(song1.getId())
//                      .songPartId(songPart1.getId())
//                      .sequenceNumber(1)
//                      .repeatNumber(1)
//                      .build()
//            );
//            SongPartDetailsDTO songPartDetails2 = songPartDetailsServiceImpl.addSongPartDetails(
//                    SongPartDetailsEditableDTO.builder()
//                            .songId(song1.getId())
//                            .songPartId(songPart2.getId())
//                            .sequenceNumber(2)
//                            .repeatNumber(2)
//                            .build()
//            );
//            SongPartDetailsDTO songPartDetails3 = songPartDetailsServiceImpl.addSongPartDetails(
//                    SongPartDetailsEditableDTO.builder()
//                            .songId(song2.getId())
//                            .songPartId(songPart3.getId())
//                            .sequenceNumber(1)
//                            .repeatNumber(1)
//                            .build()
//            );
//            SongPartDetailsDTO songPartDetails4 = songPartDetailsServiceImpl.addSongPartDetails(
//                    SongPartDetailsEditableDTO.builder()
//                            .songId(song2.getId())
//                            .songPartId(songPart4.getId())
//                            .sequenceNumber(2)
//                            .repeatNumber(2)
//                            .build()
//            );
//
//            ChordDTO chord1 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord1")
//                            .build()
//            );
//            ChordDTO chord2 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord2")
//                            .build()
//            );
//            ChordDTO chord3 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord3")
//                            .build()
//            );
//            ChordDTO chord4 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord4")
//                            .build()
//            );
//            ChordDTO chord5 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord5")
//                            .build()
//            );
//            ChordDTO chord6 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord6")
//                            .build()
//            );
//            ChordDTO chord7 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord7")
//                            .build()
//            );
//            ChordDTO chord8 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord8")
//                            .build()
//            );
//            ChordDTO chord9 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord9")
//                            .build()
//            );
//            ChordDTO chord10 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord10")
//                            .build()
//            );
//            ChordDTO chord11 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord11")
//                            .build()
//            );
//            ChordDTO chord12 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord12")
//                            .build()
//            );
//            ChordDTO chord13 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord13")
//                            .build()
//            );
//            ChordDTO chord14 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord14")
//                            .build()
//            );
//            ChordDTO chord15 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord15")
//                            .build()
//            );
//            ChordDTO chord16 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord16")
//                            .build()
//            );
//            ChordDTO chord17 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord17")
//                            .build()
//            );
//            ChordDTO chord18 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord18")
//                            .build()
//            );
//            ChordDTO chord19 = chordServiceImpl.addChord(
//                    ChordEditableDTO.builder()
//                            .name("chord19")
//                            .build()
//            );
//
//            SongPartKeyChordDTO songPartKeyChord1 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart1.getId())
//                            .keyId(key1.getId())
//                            .chordId(chord1.getId())
//                            .sequenceNumber(1)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord2 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart1.getId())
//                            .keyId(key1.getId())
//                            .chordId(chord2.getId())
//                            .sequenceNumber(2)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord3 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart1.getId())
//                            .keyId(key1.getId())
//                            .chordId(chord3.getId())
//                            .sequenceNumber(3)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord4 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart1.getId())
//                            .keyId(key1.getId())
//                            .chordId(chord4.getId())
//                            .sequenceNumber(4)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord5 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart1.getId())
//                            .keyId(key1.getId())
//                            .chordId(chord5.getId())
//                            .sequenceNumber(5)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord6 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart2.getId())
//                            .keyId(key1.getId())
//                            .chordId(chord1.getId())
//                            .sequenceNumber(1)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord7 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart2.getId())
//                            .keyId(key1.getId())
//                            .chordId(chord2.getId())
//                            .sequenceNumber(2)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord8 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart2.getId())
//                            .keyId(key1.getId())
//                            .chordId(chord3.getId())
//                            .sequenceNumber(3)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord9 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart2.getId())
//                            .keyId(key1.getId())
//                            .chordId(chord4.getId())
//                            .sequenceNumber(4)
//                            .build()
//            );
//
//            SongPartKeyChordDTO songPartKeyChord10 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart1.getId())
//                            .keyId(key2.getId())
//                            .chordId(chord6.getId())
//                            .sequenceNumber(1)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord11 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart1.getId())
//                            .keyId(key2.getId())
//                            .chordId(chord7.getId())
//                            .sequenceNumber(2)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord12 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart1.getId())
//                            .keyId(key2.getId())
//                            .chordId(chord8.getId())
//                            .sequenceNumber(3)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord13 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart1.getId())
//                            .keyId(key2.getId())
//                            .chordId(chord9.getId())
//                            .sequenceNumber(4)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord14 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart1.getId())
//                            .keyId(key2.getId())
//                            .chordId(chord10.getId())
//                            .sequenceNumber(5)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord15 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart2.getId())
//                            .keyId(key2.getId())
//                            .chordId(chord6.getId())
//                            .sequenceNumber(1)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord16 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart2.getId())
//                            .keyId(key2.getId())
//                            .chordId(chord7.getId())
//                            .sequenceNumber(2)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord17 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart2.getId())
//                            .keyId(key2.getId())
//                            .chordId(chord8.getId())
//                            .sequenceNumber(3)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord18 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart2.getId())
//                            .keyId(key2.getId())
//                            .chordId(chord9.getId())
//                            .sequenceNumber(4)
//                            .build()
//            );
//
//            SongPartKeyChordDTO songPartKeyChord19 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart1.getId())
//                            .keyId(key3.getId())
//                            .chordId(chord11.getId())
//                            .sequenceNumber(1)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord20 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart1.getId())
//                            .keyId(key3.getId())
//                            .chordId(chord12.getId())
//                            .sequenceNumber(2)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord21 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart1.getId())
//                            .keyId(key3.getId())
//                            .chordId(chord13.getId())
//                            .sequenceNumber(3)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord22 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart1.getId())
//                            .keyId(key3.getId())
//                            .chordId(chord14.getId())
//                            .sequenceNumber(4)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord23 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart1.getId())
//                            .keyId(key3.getId())
//                            .chordId(chord15.getId())
//                            .sequenceNumber(5)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord24 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart2.getId())
//                            .keyId(key3.getId())
//                            .chordId(chord11.getId())
//                            .sequenceNumber(1)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord25 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart2.getId())
//                            .keyId(key3.getId())
//                            .chordId(chord12.getId())
//                            .sequenceNumber(2)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord26 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart2.getId())
//                            .keyId(key3.getId())
//                            .chordId(chord13.getId())
//                            .sequenceNumber(3)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord27 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart2.getId())
//                            .keyId(key3.getId())
//                            .chordId(chord14.getId())
//                            .sequenceNumber(4)
//                            .build()
//            );
//
//            SongPartKeyChordDTO songPartKeyChord28 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart3.getId())
//                            .keyId(key1.getId())
//                            .chordId(chord1.getId())
//                            .sequenceNumber(1)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord29 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart3.getId())
//                            .keyId(key1.getId())
//                            .chordId(chord2.getId())
//                            .sequenceNumber(2)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord30 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart3.getId())
//                            .keyId(key1.getId())
//                            .chordId(chord3.getId())
//                            .sequenceNumber(3)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord31 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart3.getId())
//                            .keyId(key1.getId())
//                            .chordId(chord4.getId())
//                            .sequenceNumber(4)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord32 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart4.getId())
//                            .keyId(key1.getId())
//                            .chordId(chord1.getId())
//                            .sequenceNumber(1)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord33 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart4.getId())
//                            .keyId(key1.getId())
//                            .chordId(chord2.getId())
//                            .sequenceNumber(2)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord34 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart4.getId())
//                            .keyId(key1.getId())
//                            .chordId(chord3.getId())
//                            .sequenceNumber(3)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord35 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart4.getId())
//                            .keyId(key1.getId())
//                            .chordId(chord4.getId())
//                            .sequenceNumber(4)
//                            .build()
//            );
//
//            SongPartKeyChordDTO songPartKeyChord36 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart3.getId())
//                            .keyId(key2.getId())
//                            .chordId(chord6.getId())
//                            .sequenceNumber(1)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord37 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart3.getId())
//                            .keyId(key2.getId())
//                            .chordId(chord7.getId())
//                            .sequenceNumber(2)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord38 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart3.getId())
//                            .keyId(key2.getId())
//                            .chordId(chord8.getId())
//                            .sequenceNumber(3)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord39 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart3.getId())
//                            .keyId(key2.getId())
//                            .chordId(chord9.getId())
//                            .sequenceNumber(4)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord40 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart4.getId())
//                            .keyId(key2.getId())
//                            .chordId(chord6.getId())
//                            .sequenceNumber(1)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord41 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart4.getId())
//                            .keyId(key2.getId())
//                            .chordId(chord7.getId())
//                            .sequenceNumber(2)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord42 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart4.getId())
//                            .keyId(key2.getId())
//                            .chordId(chord8.getId())
//                            .sequenceNumber(3)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord43 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart4.getId())
//                            .keyId(key2.getId())
//                            .chordId(chord9.getId())
//                            .sequenceNumber(4)
//                            .build()
//            );
//
//            SongPartKeyChordDTO songPartKeyChord44 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart3.getId())
//                            .keyId(key4.getId())
//                            .chordId(chord16.getId())
//                            .sequenceNumber(1)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord45 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart3.getId())
//                            .keyId(key4.getId())
//                            .chordId(chord17.getId())
//                            .sequenceNumber(2)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord46 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart3.getId())
//                            .keyId(key4.getId())
//                            .chordId(chord18.getId())
//                            .sequenceNumber(3)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord47 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart3.getId())
//                            .keyId(key4.getId())
//                            .chordId(chord19.getId())
//                            .sequenceNumber(4)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord48 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart4.getId())
//                            .keyId(key4.getId())
//                            .chordId(chord16.getId())
//                            .sequenceNumber(1)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord50 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart4.getId())
//                            .keyId(key4.getId())
//                            .chordId(chord17.getId())
//                            .sequenceNumber(2)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord51 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart4.getId())
//                            .keyId(key4.getId())
//                            .chordId(chord18.getId())
//                            .sequenceNumber(3)
//                            .build()
//            );
//            SongPartKeyChordDTO songPartKeyChord52 = songPartKeyChordServiceImpl.addSongPartKeyChord(
//                    SongPartKeyChordEditableDTO.builder()
//                            .songPartId(songPart4.getId())
//                            .keyId(key4.getId())
//                            .chordId(chord19.getId())
//                            .sequenceNumber(4)
//                            .build()
//            );
//
//            BandDTO band1 = bandServiceImpl.addBand(
//                    BandEditableDTO.builder()
//                            .name("band1")
//                            .build()
//            );
//
//            BandSongVersionDTO bandSongVersion1 = bandSongVersionServiceImpl.addBandSongVersion(
//                    BandSongVersionEditableDTO.builder()
//                            .bandId(band1.getId())
//                            .songId(song1.getId())
//                            .keyId(key1.getId())
//                            .build()
//            );
//            BandSongVersionDTO bandSongVersion2 = bandSongVersionServiceImpl.addBandSongVersion(
//                    BandSongVersionEditableDTO.builder()
//                            .bandId(band1.getId())
//                            .songId(song2.getId())
//                            .keyId(key2.getId())
//                            .build()
//            );
//            BandSongVersionDTO bandSongVersion3 = bandSongVersionServiceImpl.addBandSongVersion(
//                    BandSongVersionEditableDTO.builder()
//                            .bandId(band1.getId())
//                            .songId(song1.getId())
//                            .keyId(key1.getId())
//                            .build()
//            );
//
//            SongPartDetailsDTO songPartDetails5 = songPartDetailsServiceImpl.addSongPartDetails(
//                    SongPartDetailsEditableDTO.builder()
//                            .bandSongVersionId(bandSongVersion1.getId())
//                            .songPartId(songPart1.getId())
//                            .sequenceNumber(1)
//                            .repeatNumber(1)
//                            .build()
//            );
//            SongPartDetailsDTO songPartDetails6 = songPartDetailsServiceImpl.addSongPartDetails(
//                    SongPartDetailsEditableDTO.builder()
//                            .bandSongVersionId(bandSongVersion1.getId())
//                            .songPartId(songPart2.getId())
//                            .sequenceNumber(2)
//                            .repeatNumber(2)
//                            .build()
//            );
//            SongPartDetailsDTO songPartDetails7 = songPartDetailsServiceImpl.addSongPartDetails(
//                    SongPartDetailsEditableDTO.builder()
//                            .bandSongVersionId(bandSongVersion2.getId())
//                            .songPartId(songPart3.getId())
//                            .sequenceNumber(1)
//                            .repeatNumber(1)
//                            .build()
//            );
//            SongPartDetailsDTO songPartDetails8 = songPartDetailsServiceImpl.addSongPartDetails(
//                    SongPartDetailsEditableDTO.builder()
//                            .bandSongVersionId(bandSongVersion2.getId())
//                            .songPartId(songPart4.getId())
//                            .sequenceNumber(2)
//                            .repeatNumber(2)
//                            .build()
//            );
//            SongPartDetailsDTO songPartDetails9 = songPartDetailsServiceImpl.addSongPartDetails(
//                    SongPartDetailsEditableDTO.builder()
//                            .bandSongVersionId(bandSongVersion3.getId())
//                            .songPartId(songPart1.getId())
//                            .sequenceNumber(1)
//                            .repeatNumber(1)
//                            .build()
//            );
//            SongPartDetailsDTO songPartDetails10 = songPartDetailsServiceImpl.addSongPartDetails(
//                    SongPartDetailsEditableDTO.builder()
//                            .bandSongVersionId(bandSongVersion3.getId())
//                            .songPartId(songPart2.getId())
//                            .sequenceNumber(2)
//                            .repeatNumber(2)
//                            .build()
//            );
//        };
//    }
}
