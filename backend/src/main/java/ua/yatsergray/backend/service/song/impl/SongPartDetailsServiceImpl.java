package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongPartDetailsDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartDetailsEditableDTO;
import ua.yatsergray.backend.domain.entity.band.BandSongVersion;
import ua.yatsergray.backend.domain.entity.song.Song;
import ua.yatsergray.backend.domain.entity.song.SongPart;
import ua.yatsergray.backend.domain.entity.song.SongPartDetails;
import ua.yatsergray.backend.exception.song.NoSuchSongPartDetailsException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartException;
import ua.yatsergray.backend.exception.song.SongPartDetailsConflictException;
import ua.yatsergray.backend.mapper.song.SongPartDetailsMapper;
import ua.yatsergray.backend.repository.band.BandSongVersionRepository;
import ua.yatsergray.backend.repository.song.SongPartDetailsRepository;
import ua.yatsergray.backend.repository.song.SongPartRepository;
import ua.yatsergray.backend.repository.song.SongRepository;
import ua.yatsergray.backend.service.song.SongPartDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SongPartDetailsServiceImpl implements SongPartDetailsService {
    private final SongPartDetailsMapper songPartDetailsMapper;
    private final SongPartDetailsRepository songPartDetailsRepository;
    private final SongPartRepository songPartRepository;
    private final SongRepository songRepository;
    private final BandSongVersionRepository bandSongVersionRepository;

    @Autowired
    public SongPartDetailsServiceImpl(SongPartDetailsMapper songPartDetailsMapper, SongPartDetailsRepository songPartDetailsRepository, SongPartRepository songPartRepository, SongRepository songRepository, BandSongVersionRepository bandSongVersionRepository) {
        this.songPartDetailsMapper = songPartDetailsMapper;
        this.songPartDetailsRepository = songPartDetailsRepository;
        this.songPartRepository = songPartRepository;
        this.songRepository = songRepository;
        this.bandSongVersionRepository = bandSongVersionRepository;
    }

    @Override
    public SongPartDetailsDTO addSongPartDetails(SongPartDetailsEditableDTO songPartDetailsEditableDTO) throws NoSuchSongPartException, SongPartDetailsConflictException {
        if (songPartDetailsEditableDTO.getSongUUID() != null && songPartDetailsEditableDTO.getBandSongVersionUUID() != null) {
            throw new SongPartDetailsConflictException(String.format("Song part details refers to song with id=%s and band song version with id=%s at the same time", songPartDetailsEditableDTO.getSongUUID(), songPartDetailsEditableDTO.getBandSongVersionUUID()));
        }

        SongPart songPart = songPartRepository.findById(songPartDetailsEditableDTO.getSongPartUUID())
                .orElseThrow(() -> new NoSuchSongPartException(String.format("Song part does not exist with id=%s", songPartDetailsEditableDTO.getSongPartUUID())));

        Song song = null;
        BandSongVersion bandSongVersion = null;

        if (songPartDetailsEditableDTO.getSongUUID() != null) {
            song = songRepository.findById(songPartDetailsEditableDTO.getSongUUID())
                    .orElseThrow(() -> new NoSuchSongPartException(String.format("Song does not exist with id=%s", songPartDetailsEditableDTO.getSongUUID())));
        } else {
            bandSongVersion = bandSongVersionRepository.findById(songPartDetailsEditableDTO.getBandSongVersionUUID())
                    .orElseThrow(() -> new NoSuchSongPartException(String.format("Band song version does not exist with id=%s", songPartDetailsEditableDTO.getBandSongVersionUUID())));
        }

        SongPartDetails songPartDetails = SongPartDetails.builder()
                .repeatNumber(songPartDetailsEditableDTO.getRepeatNumber())
                .sequenceNumber(songPartDetailsEditableDTO.getSequenceNumber())
                .songPart(songPart)
                .song(song)
                .bandSongVersion(bandSongVersion)
                .build();

        return songPartDetailsMapper.mapToSongPartDetailsDTO(songPartDetailsRepository.save(songPartDetails));
    }

    @Override
    public Optional<SongPartDetailsDTO> getSongPartDetailsById(UUID id) {
        return songPartDetailsRepository.findById(id).map(songPartDetailsMapper::mapToSongPartDetailsDTO);
    }

    @Override
    public List<SongPartDetailsDTO> getAllSongPartDetails() {
        return songPartDetailsMapper.mapAllToSongPartDetailsDTOList(songPartDetailsRepository.findAll());
    }

    @Override
    public SongPartDetailsDTO modifySongPartDetailsById(UUID id, SongPartDetailsEditableDTO songPartDetailsEditableDTO) throws NoSuchSongPartDetailsException, NoSuchSongPartException, SongPartDetailsConflictException {
        if (songPartDetailsEditableDTO.getSongUUID() != null && songPartDetailsEditableDTO.getBandSongVersionUUID() != null) {
            throw new SongPartDetailsConflictException(String.format("Song part details refers to song with id=%s and band song version with id=%s at the same time", songPartDetailsEditableDTO.getSongUUID(), songPartDetailsEditableDTO.getBandSongVersionUUID()));
        }

        SongPartDetails songPartDetails = songPartDetailsRepository.findById(id)
                .orElseThrow(() -> new NoSuchSongPartDetailsException(String.format("Song part details does not exist with id=%s", id)));
        SongPart songPart = songPartRepository.findById(songPartDetailsEditableDTO.getSongPartUUID())
                .orElseThrow(() -> new NoSuchSongPartException(String.format("Song part does not exist with id=%s", songPartDetailsEditableDTO.getSongPartUUID())));

        Song song = null;
        BandSongVersion bandSongVersion = null;

        if (songPartDetailsEditableDTO.getSongUUID() != null) {
            song = songRepository.findById(songPartDetailsEditableDTO.getSongUUID())
                    .orElseThrow(() -> new NoSuchSongPartException(String.format("Song does not exist with id=%s", songPartDetailsEditableDTO.getSongUUID())));
        } else {
            bandSongVersion = bandSongVersionRepository.findById(songPartDetailsEditableDTO.getBandSongVersionUUID())
                    .orElseThrow(() -> new NoSuchSongPartException(String.format("Band song version does not exist with id=%s", songPartDetailsEditableDTO.getBandSongVersionUUID())));
        }

        songPartDetails.setSequenceNumber(songPartDetailsEditableDTO.getSequenceNumber());
        songPartDetails.setRepeatNumber(songPartDetailsEditableDTO.getRepeatNumber());
        songPartDetails.setSongPart(songPart);
        songPartDetails.setSong(song);
        songPartDetails.setBandSongVersion(bandSongVersion);

        return songPartDetailsMapper.mapToSongPartDetailsDTO(songPartDetailsRepository.save(songPartDetails));
    }

    @Override
    public void removeSongPartDetailsById(UUID id) throws NoSuchSongPartDetailsException {
        if (!songPartDetailsRepository.existsById(id)) {
            throw new NoSuchSongPartDetailsException(String.format("Song part details does not exist with id=%s", id));
        }

        songPartDetailsRepository.deleteById(id);
    }
}
