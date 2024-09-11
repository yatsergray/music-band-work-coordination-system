package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongPartKeyChordDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartKeyChordEditableDTO;
import ua.yatsergray.backend.domain.entity.song.Chord;
import ua.yatsergray.backend.domain.entity.song.Key;
import ua.yatsergray.backend.domain.entity.song.SongPart;
import ua.yatsergray.backend.domain.entity.song.SongPartKeyChord;
import ua.yatsergray.backend.exception.song.NoSuchChordException;
import ua.yatsergray.backend.exception.song.NoSuchKeyException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartKeyChordException;
import ua.yatsergray.backend.mapper.song.SongPartKeyChordMapper;
import ua.yatsergray.backend.repository.band.ChordRepository;
import ua.yatsergray.backend.repository.song.KeyRepository;
import ua.yatsergray.backend.repository.song.SongPartKeyChordRepository;
import ua.yatsergray.backend.repository.song.SongPartRepository;
import ua.yatsergray.backend.service.song.SongPartKeyChordService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SongPartKeyChordServiceImpl implements SongPartKeyChordService {
    private final SongPartKeyChordMapper songPartKeyChordMapper;
    private final SongPartKeyChordRepository songPartKeyChordRepository;
    private final KeyRepository keyRepository;
    private final ChordRepository chordRepository;
    private final SongPartRepository songPartRepository;

    @Autowired
    public SongPartKeyChordServiceImpl(SongPartKeyChordMapper songPartKeyChordMapper, SongPartKeyChordRepository songPartKeyChordRepository, KeyRepository keyRepository, ChordRepository chordRepository, SongPartRepository songPartRepository) {
        this.songPartKeyChordMapper = songPartKeyChordMapper;
        this.songPartKeyChordRepository = songPartKeyChordRepository;
        this.keyRepository = keyRepository;
        this.chordRepository = chordRepository;
        this.songPartRepository = songPartRepository;
    }

    @Override
    public SongPartKeyChordDTO addSongPartKeyChord(SongPartKeyChordEditableDTO songPartKeyChordEditableDTO) throws NoSuchKeyException, NoSuchChordException, NoSuchSongPartException {
        Key key = keyRepository.findById(songPartKeyChordEditableDTO.getKeyUUID())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key does not exist with id=%s", songPartKeyChordEditableDTO.getKeyUUID())));
        Chord chord = chordRepository.findById(songPartKeyChordEditableDTO.getChordUUID())
                .orElseThrow(() -> new NoSuchChordException(String.format("Chord does not exist with id=%s", songPartKeyChordEditableDTO.getChordUUID())));
        SongPart songPart = songPartRepository.findById(songPartKeyChordEditableDTO.getSongPartUUID())
                .orElseThrow(() -> new NoSuchSongPartException(String.format("Song part does not exist with id=%s", songPartKeyChordEditableDTO.getSongPartUUID())));

        SongPartKeyChord songPartKeyChord = SongPartKeyChord.builder()
                .sequenceNumber(songPartKeyChordEditableDTO.getSequenceNumber())
                .key(key)
                .chord(chord)
                .songPart(songPart)
                .build();

        return songPartKeyChordMapper.mapToSongPartKeyChordDTO(songPartKeyChordRepository.save(songPartKeyChord));
    }

    @Override
    public Optional<SongPartKeyChordDTO> getSongPartKeyChordById(UUID songPartKeyChordId) {
        return songPartKeyChordRepository.findById(songPartKeyChordId).map(songPartKeyChordMapper::mapToSongPartKeyChordDTO);
    }

    @Override
    public List<SongPartKeyChordDTO> getAllSongPartKeyChords() {
        return songPartKeyChordMapper.mapAllToSongPartKeyChordDTOList(songPartKeyChordRepository.findAll());
    }

    @Override
    public SongPartKeyChordDTO modifySongPartKeyChordById(UUID songPartKeyChordId, SongPartKeyChordEditableDTO songPartKeyChordEditableDTO) throws NoSuchSongPartKeyChordException, NoSuchKeyException, NoSuchChordException, NoSuchSongPartException {
        SongPartKeyChord songPartKeyChord = songPartKeyChordRepository.findById(songPartKeyChordId)
                .orElseThrow(() -> new NoSuchSongPartKeyChordException(String.format("Song part key chord does not exist with id=%s", songPartKeyChordId)));
        Key key = keyRepository.findById(songPartKeyChordEditableDTO.getKeyUUID())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key does not exist with id=%s", songPartKeyChordEditableDTO.getKeyUUID())));
        Chord chord = chordRepository.findById(songPartKeyChordEditableDTO.getChordUUID())
                .orElseThrow(() -> new NoSuchChordException(String.format("Chord does not exist with id=%s", songPartKeyChordEditableDTO.getChordUUID())));
        SongPart songPart = songPartRepository.findById(songPartKeyChordEditableDTO.getSongPartUUID())
                .orElseThrow(() -> new NoSuchSongPartException(String.format("Song part does not exist with id=%s", songPartKeyChordEditableDTO.getSongPartUUID())));

        songPartKeyChord.setSequenceNumber(songPartKeyChordEditableDTO.getSequenceNumber());
        songPartKeyChord.setKey(key);
        songPartKeyChord.setChord(chord);
        songPartKeyChord.setSongPart(songPart);

        return songPartKeyChordMapper.mapToSongPartKeyChordDTO(songPartKeyChordRepository.save(songPartKeyChord));
    }

    @Override
    public void removeSongPartKeyChordById(UUID songPartKeyChordId) throws NoSuchSongPartKeyChordException {
        if (!songPartKeyChordRepository.existsById(songPartKeyChordId)) {
            throw new NoSuchSongPartKeyChordException(String.format("Song part key chord does not exist with id=%s", songPartKeyChordId));
        }

        songPartKeyChordRepository.deleteById(songPartKeyChordId);
    }
}
