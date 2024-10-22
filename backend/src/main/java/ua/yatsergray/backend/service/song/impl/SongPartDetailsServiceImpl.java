package ua.yatsergray.backend.service.song.impl;

import jakarta.transaction.Transactional;
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
    public SongPartDetailsDTO addSongPartDetails(SongPartDetailsEditableDTO songPartDetailsEditableDTO) throws NoSuchSongPartException, SongPartDetailsConflictException, SongPartDetailsAlreadyExistsException {
        // TODO: Generate talkback for songPartDetails.getSong() or songPartDetails.getBandSongVersion()

        return songPartDetailsMapper.mapToSongPartDetailsDTO(songPartDetailsRepository.save(configureSongPartDetails(new SongPartDetails(), songPartDetailsEditableDTO)));
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
    public SongPartDetailsDTO modifySongPartDetailsById(UUID songPartDetailsId, SongPartDetailsEditableDTO songPartDetailsEditableDTO) throws NoSuchSongPartDetailsException, NoSuchSongPartException, SongPartDetailsConflictException, SongPartDetailsAlreadyExistsException {
        SongPartDetails songPartDetails = songPartDetailsRepository.findById(songPartDetailsId)
                .orElseThrow(() -> new NoSuchSongPartDetailsException(String.format("Song part details with id=\"%s\" does not exist", songPartDetailsId)));

        return songPartDetailsMapper.mapToSongPartDetailsDTO(songPartDetailsRepository.save(configureSongPartDetails(songPartDetails, songPartDetailsEditableDTO)));
    }

    @Override
    public void removeSongPartDetailsById(UUID songPartDetailsId) throws NoSuchSongPartDetailsException {
        if (!songPartDetailsRepository.existsById(songPartDetailsId)) {
            throw new NoSuchSongPartDetailsException(String.format("Song part details with id=\"%s\" does not exist", songPartDetailsId));
        }

        // TODO: Generate talkback for songPartDetails.getSong() or songPartDetails.getBandSongVersion()

        songPartDetailsRepository.deleteById(songPartDetailsId);
    }

    private SongPartDetails configureSongPartDetails(SongPartDetails songPartDetails, SongPartDetailsEditableDTO songPartDetailsEditableDTO) throws SongPartDetailsConflictException, NoSuchSongPartException, SongPartDetailsAlreadyExistsException {
        if (Objects.isNull(songPartDetailsEditableDTO.getSongId()) && Objects.isNull(songPartDetailsEditableDTO.getBandSongVersionId())) {
            throw new SongPartDetailsConflictException("Song part details does not refer to either the Song or the Band song version");
        }

        if (!Objects.isNull(songPartDetailsEditableDTO.getSongId()) && !Objects.isNull(songPartDetailsEditableDTO.getBandSongVersionId())) {
            throw new SongPartDetailsConflictException("Song part details refers to the Song and Band song version at the same time");
        }

        SongPart songPart = songPartRepository.findById(songPartDetailsEditableDTO.getSongPartId())
                .orElseThrow(() -> new NoSuchSongPartException(String.format("Song part with id=\"%s\" does not exist", songPartDetailsEditableDTO.getSongPartId())));

        if (!Objects.isNull(songPartDetailsEditableDTO.getSongId())) {
            Song song = songRepository.findById(songPartDetailsEditableDTO.getSongId())
                    .orElseThrow(() -> new NoSuchSongPartException(String.format("Song with id=\"%s\" does not exist", songPartDetailsEditableDTO.getSongId())));

            if (!songPartRepository.existsByIdAndSongId(songPartDetailsEditableDTO.getSongPartId(), songPartDetailsEditableDTO.getSongId())) {
                throw new SongPartDetailsConflictException(String.format("Song part with id=\"%s\" does not belong to the Song with id=\"%s\"", songPartDetailsEditableDTO.getSongPartId(), songPartDetailsEditableDTO.getSongId()));
            }

            if (Objects.isNull(songPartDetails.getId())) {
                if (songPartDetailsRepository.existsBySongIdAndSequenceNumber(songPartDetailsEditableDTO.getSongId(), songPartDetailsEditableDTO.getSequenceNumber())) {
                    throw new SongPartDetailsAlreadyExistsException(String.format("Song part details with songId=\"%s\" and sequenceNumber=\"%s\" already exists", songPartDetailsEditableDTO.getSongId(), songPartDetailsEditableDTO.getSequenceNumber().toString()));
                }
            } else {
                if ((!songPartDetailsEditableDTO.getSongId().equals(songPartDetails.getSong().getId()) || !songPartDetailsEditableDTO.getSequenceNumber().equals(songPartDetails.getSequenceNumber())) && songPartDetailsRepository.existsBySongIdAndSequenceNumber(songPartDetailsEditableDTO.getSongId(), songPartDetailsEditableDTO.getSequenceNumber())) {
                    throw new SongPartDetailsAlreadyExistsException(String.format("Song part details with songId=\"%s\" and sequenceNumber=\"%s\" already exists", songPartDetailsEditableDTO.getSongId(), songPartDetailsEditableDTO.getSequenceNumber().toString()));
                }
            }

            songPartDetails.setSong(song);
        }

        if (!Objects.isNull(songPartDetailsEditableDTO.getBandSongVersionId())) {
            BandSongVersion bandSongVersion = bandSongVersionRepository.findById(songPartDetailsEditableDTO.getBandSongVersionId())
                    .orElseThrow(() -> new NoSuchSongPartException(String.format("Band song version with id=\"%s\" does not exist", songPartDetailsEditableDTO.getBandSongVersionId())));

            if (!songPartRepository.existsByIdAndSongId(songPartDetailsEditableDTO.getSongPartId(), bandSongVersion.getSong().getId())) {
                throw new SongPartDetailsConflictException(String.format("Song part with id=\"%s\" does not belong to the Song with id=\"%s\"", songPartDetailsEditableDTO.getSongPartId(), bandSongVersion.getSong().getId()));
            }

            if (Objects.isNull(songPartDetails.getId())) {
                if (songPartDetailsRepository.existsByBandSongVersionIdAndSequenceNumber(songPartDetailsEditableDTO.getBandSongVersionId(), songPartDetailsEditableDTO.getSequenceNumber())) {
                    throw new SongPartDetailsAlreadyExistsException(String.format("Song part details with bandSongVersionId=\"%s\" and sequenceNumber=\"%s\" already exists", songPartDetailsEditableDTO.getBandSongVersionId(), songPartDetailsEditableDTO.getSequenceNumber().toString()));
                }
            } else {
                if ((!songPartDetailsEditableDTO.getBandSongVersionId().equals(songPartDetails.getBandSongVersion().getId()) || !songPartDetailsEditableDTO.getSequenceNumber().equals(songPartDetails.getSequenceNumber())) && songPartDetailsRepository.existsByBandSongVersionIdAndSequenceNumber(songPartDetailsEditableDTO.getBandSongVersionId(), songPartDetailsEditableDTO.getSequenceNumber())) {
                    throw new SongPartDetailsAlreadyExistsException(String.format("Song part details with bandSongVersionId=\"%s\" and sequenceNumber=\"%s\" already exists", songPartDetailsEditableDTO.getBandSongVersionId(), songPartDetailsEditableDTO.getSequenceNumber().toString()));
                }
            }

            songPartDetails.setBandSongVersion(bandSongVersion);
        }

        songPartDetails.setSequenceNumber(songPartDetailsEditableDTO.getSequenceNumber());
        songPartDetails.setRepeatNumber(songPartDetailsEditableDTO.getRepeatNumber());
        songPartDetails.setSongPart(songPart);

        return songPartDetails;
    }
}
