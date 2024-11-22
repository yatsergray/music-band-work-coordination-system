package ua.yatsergray.backend.service.song.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongPartDetailsDTO;
import ua.yatsergray.backend.domain.entity.band.BandSongVersion;
import ua.yatsergray.backend.domain.entity.song.Song;
import ua.yatsergray.backend.domain.entity.song.SongPart;
import ua.yatsergray.backend.domain.entity.song.SongPartDetails;
import ua.yatsergray.backend.domain.request.song.SongPartDetailsCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongPartDetailsUpdateRequest;
import ua.yatsergray.backend.exception.song.NoSuchSongPartDetailsException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartException;
import ua.yatsergray.backend.exception.song.SongPartDetailsAlreadyExistsException;
import ua.yatsergray.backend.exception.song.SongPartDetailsConflictException;
import ua.yatsergray.backend.mapper.song.SongPartDetailsMapper;
import ua.yatsergray.backend.repository.band.BandSongVersionRepository;
import ua.yatsergray.backend.repository.song.SongPartDetailsRepository;
import ua.yatsergray.backend.repository.song.SongPartRepository;
import ua.yatsergray.backend.repository.song.SongRepository;
import ua.yatsergray.backend.service.song.SongPartDetailsService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Transactional
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
    public SongPartDetailsDTO addSongPartDetails(SongPartDetailsCreateRequest songPartDetailsCreateRequest) throws NoSuchSongPartException, SongPartDetailsConflictException, SongPartDetailsAlreadyExistsException {
        if (Objects.isNull(songPartDetailsCreateRequest.getSongId()) && Objects.isNull(songPartDetailsCreateRequest.getBandSongVersionId())) {
            throw new SongPartDetailsConflictException("Song part details does not refer to either the Song or the Band song version");
        }

        if (!Objects.isNull(songPartDetailsCreateRequest.getSongId()) && !Objects.isNull(songPartDetailsCreateRequest.getBandSongVersionId())) {
            throw new SongPartDetailsConflictException("Song part details refers to the Song and Band song version at the same time");
        }

        SongPart songPart = songPartRepository.findById(songPartDetailsCreateRequest.getSongPartId())
                .orElseThrow(() -> new NoSuchSongPartException(String.format("Song part with id=\"%s\" does not exist", songPartDetailsCreateRequest.getSongPartId())));

        SongPartDetails songPartDetails = SongPartDetails.builder()
                .songPart(songPart)
                .sequenceNumber(songPartDetailsCreateRequest.getSequenceNumber())
                .repeatNumber(songPartDetailsCreateRequest.getRepeatNumber())
                .build();

        if (!Objects.isNull(songPartDetailsCreateRequest.getSongId())) {
            Song song = songRepository.findById(songPartDetailsCreateRequest.getSongId())
                    .orElseThrow(() -> new NoSuchSongPartException(String.format("Song with id=\"%s\" does not exist", songPartDetailsCreateRequest.getSongId())));

            if (!songPartRepository.existsByIdAndSongId(songPartDetailsCreateRequest.getSongPartId(), songPartDetailsCreateRequest.getSongId())) {
                throw new SongPartDetailsConflictException(String.format("Song part with id=\"%s\" does not belong to the Song with id=\"%s\"", songPartDetailsCreateRequest.getSongPartId(), songPartDetailsCreateRequest.getSongId()));
            }

            if (songPartDetailsRepository.existsBySongIdAndSequenceNumber(songPartDetailsCreateRequest.getSongId(), songPartDetailsCreateRequest.getSequenceNumber())) {
                throw new SongPartDetailsAlreadyExistsException(String.format("Song part details with songId=\"%s\" and sequenceNumber=\"%s\" already exists", songPartDetailsCreateRequest.getSongId(), songPartDetailsCreateRequest.getSequenceNumber().toString()));
            }

            songPartDetails.setSong(song);
        }

        if (!Objects.isNull(songPartDetailsCreateRequest.getBandSongVersionId())) {
            BandSongVersion bandSongVersion = bandSongVersionRepository.findById(songPartDetailsCreateRequest.getBandSongVersionId())
                    .orElseThrow(() -> new NoSuchSongPartException(String.format("Band song version with id=\"%s\" does not exist", songPartDetailsCreateRequest.getBandSongVersionId())));

            if (!songPartRepository.existsByIdAndSongId(songPartDetailsCreateRequest.getSongPartId(), bandSongVersion.getSong().getId())) {
                throw new SongPartDetailsConflictException(String.format("Song part with id=\"%s\" does not belong to the Song with id=\"%s\"", songPartDetailsCreateRequest.getSongPartId(), bandSongVersion.getSong().getId()));
            }

            if (songPartDetailsRepository.existsByBandSongVersionIdAndSequenceNumber(songPartDetailsCreateRequest.getBandSongVersionId(), songPartDetailsCreateRequest.getSequenceNumber())) {
                throw new SongPartDetailsAlreadyExistsException(String.format("Song part details with bandSongVersionId=\"%s\" and sequenceNumber=\"%s\" already exists", songPartDetailsCreateRequest.getBandSongVersionId(), songPartDetailsCreateRequest.getSequenceNumber().toString()));
            }

            songPartDetails.setBandSongVersion(bandSongVersion);
        }

        return songPartDetailsMapper.mapToSongPartDetailsDTO(songPartDetailsRepository.save(songPartDetails));
    }

    @Override
    public Optional<SongPartDetailsDTO> getSongPartDetailsById(UUID songPartDetailsId) {
        return songPartDetailsRepository.findById(songPartDetailsId).map(songPartDetailsMapper::mapToSongPartDetailsDTO);
    }

    @Override
    public List<SongPartDetailsDTO> getAllSongPartDetails() {
        return songPartDetailsMapper.mapAllToSongPartDetailsDTOList(songPartDetailsRepository.findAll());
    }

    @Override
    public SongPartDetailsDTO modifySongPartDetailsById(UUID songPartDetailsId, SongPartDetailsUpdateRequest songPartDetailsUpdateRequest) throws NoSuchSongPartDetailsException, SongPartDetailsAlreadyExistsException {
        SongPartDetails songPartDetails = songPartDetailsRepository.findById(songPartDetailsId)
                .orElseThrow(() -> new NoSuchSongPartDetailsException(String.format("Song part details with id=\"%s\" does not exist", songPartDetailsId)));

        if (!Objects.isNull(songPartDetails.getSong().getId()) && !songPartDetailsUpdateRequest.getSequenceNumber().equals(songPartDetails.getSequenceNumber()) && songPartDetailsRepository.existsBySongIdAndSequenceNumber(songPartDetails.getSong().getId(), songPartDetailsUpdateRequest.getSequenceNumber())) {
            throw new SongPartDetailsAlreadyExistsException(String.format("Song part details with songId=\"%s\" and sequenceNumber=\"%s\" already exists", songPartDetails.getSong().getId(), songPartDetailsUpdateRequest.getSequenceNumber().toString()));
        }

        if (!Objects.isNull(songPartDetails.getBandSongVersion().getId()) && !songPartDetailsUpdateRequest.getSequenceNumber().equals(songPartDetails.getSequenceNumber()) && songPartDetailsRepository.existsByBandSongVersionIdAndSequenceNumber(songPartDetails.getBandSongVersion().getId(), songPartDetailsUpdateRequest.getSequenceNumber())) {
            throw new SongPartDetailsAlreadyExistsException(String.format("Song part details with bandSongVersionId=\"%s\" and sequenceNumber=\"%s\" already exists", songPartDetails.getBandSongVersion().getId(), songPartDetailsUpdateRequest.getSequenceNumber().toString()));
        }

        songPartDetails.setSequenceNumber(songPartDetailsUpdateRequest.getSequenceNumber());
        songPartDetails.setRepeatNumber(songPartDetailsUpdateRequest.getRepeatNumber());

        return songPartDetailsMapper.mapToSongPartDetailsDTO(songPartDetailsRepository.save(songPartDetails));
    }

    @Override
    public void removeSongPartDetailsById(UUID songPartDetailsId) throws NoSuchSongPartDetailsException {
        if (!songPartDetailsRepository.existsById(songPartDetailsId)) {
            throw new NoSuchSongPartDetailsException(String.format("Song part details with id=\"%s\" does not exist", songPartDetailsId));
        }

        songPartDetailsRepository.deleteById(songPartDetailsId);
    }
}
