package ua.yatsergray.backend.service.song.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongPartKeyChordDTO;
import ua.yatsergray.backend.domain.entity.song.Chord;
import ua.yatsergray.backend.domain.entity.song.Key;
import ua.yatsergray.backend.domain.entity.song.SongPart;
import ua.yatsergray.backend.domain.entity.song.SongPartKeyChord;
import ua.yatsergray.backend.domain.request.song.SongPartKeyChordCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongPartKeyChordUpdateRequest;
import ua.yatsergray.backend.exception.song.*;
import ua.yatsergray.backend.mapper.song.SongPartKeyChordMapper;
import ua.yatsergray.backend.repository.song.ChordRepository;
import ua.yatsergray.backend.repository.song.KeyRepository;
import ua.yatsergray.backend.repository.song.SongPartKeyChordRepository;
import ua.yatsergray.backend.repository.song.SongPartRepository;
import ua.yatsergray.backend.service.song.SongPartKeyChordService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
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
    public SongPartKeyChordDTO addSongPartKeyChord(SongPartKeyChordCreateRequest songPartKeyChordCreateRequest) throws NoSuchKeyException, NoSuchChordException, NoSuchSongPartException, SongPartKeyChordAlreadyExistsException, SongPartKeyChordConflictException {
        Key key = keyRepository.findById(songPartKeyChordCreateRequest.getKeyId())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key with id=\"%s\" does not exist", songPartKeyChordCreateRequest.getKeyId())));
        Chord chord = chordRepository.findById(songPartKeyChordCreateRequest.getChordId())
                .orElseThrow(() -> new NoSuchChordException(String.format("Chord with id=\"%s\" does not exist", songPartKeyChordCreateRequest.getChordId())));
        SongPart songPart = songPartRepository.findById(songPartKeyChordCreateRequest.getSongPartId())
                .orElseThrow(() -> new NoSuchSongPartException(String.format("Song part with id=\"%s\" does not exist", songPartKeyChordCreateRequest.getSongPartId())));

        if (!songPart.getSong().getKey().equals(key) && !songPart.getSong().getKeys().contains(key)) {
            throw new SongPartKeyChordConflictException(String.format("Key with id=\"%s\" does not belong to the Song with id=\"%s\"", songPartKeyChordCreateRequest.getKeyId(), songPart.getSong().getId()));
        }

        if (songPartKeyChordRepository.existsBySongPartIdAndKeyIdAndSequenceNumber(songPartKeyChordCreateRequest.getSongPartId(), songPartKeyChordCreateRequest.getKeyId(), songPartKeyChordCreateRequest.getSequenceNumber())) {
            throw new SongPartKeyChordAlreadyExistsException(String.format("Song part key chord with songPartId=\"%s\", keyId=\"%s\" and sequenceNumber=\"%d\" already exists", songPartKeyChordCreateRequest.getSongPartId(), songPartKeyChordCreateRequest.getKeyId(), songPartKeyChordCreateRequest.getSequenceNumber()));
        }

        SongPartKeyChord songPartKeyChord = SongPartKeyChord.builder()
                .sequenceNumber(songPartKeyChordCreateRequest.getSequenceNumber())
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
    public SongPartKeyChordDTO modifySongPartKeyChordById(UUID songPartKeyChordId, SongPartKeyChordUpdateRequest songPartKeyChordUpdateRequest) throws NoSuchSongPartKeyChordException, NoSuchKeyException, NoSuchChordException, NoSuchSongPartException, SongPartKeyChordAlreadyExistsException, SongPartKeyChordConflictException {
        SongPartKeyChord songPartKeyChord = songPartKeyChordRepository.findById(songPartKeyChordId)
                .orElseThrow(() -> new NoSuchSongPartKeyChordException(String.format("Song part key chord with id=\"%s\" does not exist", songPartKeyChordId)));
        Chord chord = chordRepository.findById(songPartKeyChordUpdateRequest.getChordId())
                .orElseThrow(() -> new NoSuchChordException(String.format("Chord with id=\"%s\" does not exist", songPartKeyChordUpdateRequest.getChordId())));

        songPartKeyChord.setChord(chord);

        return songPartKeyChordMapper.mapToSongPartKeyChordDTO(songPartKeyChordRepository.save(songPartKeyChord));
    }

    @Override
    public void removeSongPartKeyChordById(UUID songPartKeyChordId) throws NoSuchSongPartKeyChordException {
        if (!songPartKeyChordRepository.existsById(songPartKeyChordId)) {
            throw new NoSuchSongPartKeyChordException(String.format("Song part key chord with id=\"%s\" does not exist", songPartKeyChordId));
        }

        songPartKeyChordRepository.deleteById(songPartKeyChordId);
    }
}
