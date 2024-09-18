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
import java.util.Objects;
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
    public SongPartDetailsDTO modifySongPartDetailsById(UUID songPartCategoryId, SongPartDetailsEditableDTO songPartDetailsEditableDTO) throws NoSuchSongPartDetailsException, NoSuchSongPartException, SongPartDetailsConflictException {
        SongPartDetails songPartDetails = songPartDetailsRepository.findById(songPartCategoryId)
                .orElseThrow(() -> new NoSuchSongPartDetailsException(String.format("Song part details with id=%s does not exist", songPartCategoryId)));

        return songPartDetailsMapper.mapToSongPartDetailsDTO(songPartDetailsRepository.save(configureSongPartDetails(songPartDetails, songPartDetailsEditableDTO)));
    }

    @Override
    public void removeSongPartDetailsById(UUID songPartCategoryId) throws NoSuchSongPartDetailsException {
        if (!songPartDetailsRepository.existsById(songPartCategoryId)) {
            throw new NoSuchSongPartDetailsException(String.format("Song part details with id=%s does not exist", songPartCategoryId));
        }

        songPartDetailsRepository.deleteById(songPartCategoryId);
    }

    private SongPartDetails configureSongPartDetails(SongPartDetails songPartDetails, SongPartDetailsEditableDTO songPartDetailsEditableDTO) throws SongPartDetailsConflictException, NoSuchSongPartException {
        if (!Objects.isNull(songPartDetailsEditableDTO.getSongUUID()) && !Objects.isNull(songPartDetailsEditableDTO.getBandSongVersionUUID())) {
            throw new SongPartDetailsConflictException(String.format("Song part details refers to song with id=%s and band song version with id=%s at the same time", songPartDetailsEditableDTO.getSongUUID(), songPartDetailsEditableDTO.getBandSongVersionUUID()));
        }

        SongPart songPart = songPartRepository.findById(songPartDetailsEditableDTO.getSongPartUUID())
                .orElseThrow(() -> new NoSuchSongPartException(String.format("Song part with id=%s does not exist", songPartDetailsEditableDTO.getSongPartUUID())));

        if (!Objects.isNull(songPartDetailsEditableDTO.getSongUUID())) {
            Song song = songRepository.findById(songPartDetailsEditableDTO.getSongUUID())
                    .orElseThrow(() -> new NoSuchSongPartException(String.format("Song with id=%s does not exist", songPartDetailsEditableDTO.getSongUUID())));

            if (Objects.isNull(songPartDetails.getId())) {
                if (songPartDetailsRepository.existsBySongIdAndSequenceNumber(songPartDetailsEditableDTO.getSongUUID(), songPartDetailsEditableDTO.getSequenceNumber())) {
                    throw new SongPartDetailsConflictException(String.format("Song part details with songId=%s and sequenceNumber=%d already exists", songPartDetailsEditableDTO.getSongUUID(), songPartDetailsEditableDTO.getSequenceNumber()));
                }
            } else {
                if ((!songPartDetailsEditableDTO.getSongUUID().equals(songPartDetails.getSong().getId()) || !songPartDetailsEditableDTO.getSequenceNumber().equals(songPartDetails.getSequenceNumber())) && songPartDetailsRepository.existsBySongIdAndSequenceNumber(songPartDetailsEditableDTO.getSongUUID(), songPartDetailsEditableDTO.getSequenceNumber())) {
                    throw new SongPartDetailsConflictException(String.format("Song part details with songId=%s and sequenceNumber=%d already exists", songPartDetailsEditableDTO.getSongUUID(), songPartDetailsEditableDTO.getSequenceNumber()));
                }
            }

            songPartDetails.setSong(song);
        }

        if (!Objects.isNull(songPartDetailsEditableDTO.getBandSongVersionUUID())) {
            BandSongVersion bandSongVersion = bandSongVersionRepository.findById(songPartDetailsEditableDTO.getBandSongVersionUUID())
                    .orElseThrow(() -> new NoSuchSongPartException(String.format("Band song version with id=%s does not exist", songPartDetailsEditableDTO.getBandSongVersionUUID())));

            if (Objects.isNull(songPartDetails.getId())) {
                if (songPartDetailsRepository.existsByBandSongVersionIdAndSequenceNumber(songPartDetailsEditableDTO.getBandSongVersionUUID(), songPartDetailsEditableDTO.getSequenceNumber())) {
                    throw new SongPartDetailsConflictException(String.format("Song part details with bandSongVersionId=%s and sequenceNumber=%d already exists", songPartDetailsEditableDTO.getBandSongVersionUUID(), songPartDetailsEditableDTO.getSequenceNumber()));
                }
            } else {
                if ((!songPartDetailsEditableDTO.getBandSongVersionUUID().equals(songPartDetails.getBandSongVersion().getId()) || !songPartDetailsEditableDTO.getSequenceNumber().equals(songPartDetails.getSequenceNumber())) && songPartDetailsRepository.existsByBandSongVersionIdAndSequenceNumber(songPartDetailsEditableDTO.getBandSongVersionUUID(), songPartDetailsEditableDTO.getSequenceNumber())) {
                    throw new SongPartDetailsConflictException(String.format("Song part details with bandSongVersionId=%s and sequenceNumber=%d already exists", songPartDetailsEditableDTO.getBandSongVersionUUID(), songPartDetailsEditableDTO.getSequenceNumber()));
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
