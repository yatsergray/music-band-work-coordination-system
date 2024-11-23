package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.BandSongVersionDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.band.BandSongVersion;
import ua.yatsergray.backend.domain.entity.song.Key;
import ua.yatsergray.backend.domain.entity.song.Song;
import ua.yatsergray.backend.domain.entity.song.SongPart;
import ua.yatsergray.backend.domain.entity.song.SongPartKeyChord;
import ua.yatsergray.backend.domain.request.band.BandSongVersionCreateRequest;
import ua.yatsergray.backend.domain.request.band.BandSongVersionUpdateRequest;
import ua.yatsergray.backend.exception.band.BandSongVersionConflictException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchBandSongVersionException;
import ua.yatsergray.backend.exception.song.NoSuchKeyException;
import ua.yatsergray.backend.exception.song.NoSuchSongException;
import ua.yatsergray.backend.mapper.band.BandSongVersionMapper;
import ua.yatsergray.backend.repository.band.BandRepository;
import ua.yatsergray.backend.repository.band.BandSongVersionRepository;
import ua.yatsergray.backend.repository.song.*;
import ua.yatsergray.backend.service.band.BandSongVersionService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class BandSongVersionServiceImpl implements BandSongVersionService {
    private final BandSongVersionMapper bandSongVersionMapper;
    private final BandSongVersionRepository bandSongVersionRepository;
    private final KeyRepository keyRepository;
    private final BandRepository bandRepository;
    private final SongRepository songRepository;
    private final SongPartDetailsRepository songPartDetailsRepository;
    private final SongPartRepository songPartRepository;
    private final SongPartKeyChordRepository songPartKeyChordRepository;

    @Autowired
    public BandSongVersionServiceImpl(BandSongVersionMapper bandSongVersionMapper, BandSongVersionRepository bandSongVersionRepository, KeyRepository keyRepository, BandRepository bandRepository, SongRepository songRepository, SongPartDetailsRepository songPartDetailsRepository, SongPartRepository songPartRepository, SongPartKeyChordRepository songPartKeyChordRepository) {
        this.bandSongVersionMapper = bandSongVersionMapper;
        this.bandSongVersionRepository = bandSongVersionRepository;
        this.keyRepository = keyRepository;
        this.bandRepository = bandRepository;
        this.songRepository = songRepository;
        this.songPartDetailsRepository = songPartDetailsRepository;
        this.songPartRepository = songPartRepository;
        this.songPartKeyChordRepository = songPartKeyChordRepository;
    }

    @Override
    public BandSongVersionDTO addBandSongVersion(BandSongVersionCreateRequest bandSongVersionCreateRequest) throws NoSuchKeyException, NoSuchBandException, NoSuchSongException, BandSongVersionConflictException {
        // TODO: inherit original song structure

        Key key = keyRepository.findById(bandSongVersionCreateRequest.getKeyId())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key with id=\"%s\" does not exist", bandSongVersionCreateRequest.getKeyId())));
        Band band = bandRepository.findById(bandSongVersionCreateRequest.getBandId())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", bandSongVersionCreateRequest.getBandId())));
        Song song = songRepository.findById(bandSongVersionCreateRequest.getSongId())
                .orElseThrow(() -> new NoSuchSongException(String.format("Song with id=\"%s\" does not exist", bandSongVersionCreateRequest.getSongId())));

        if (!song.getKey().equals(key) && !song.getKeys().contains(key)) {
            throw new BandSongVersionConflictException(String.format("Song with id=\"%s\" does not have Key with id=\"%s\"", bandSongVersionCreateRequest.getSongId(), bandSongVersionCreateRequest.getKeyId()));
        }

        BandSongVersion bandSongVersion = BandSongVersion.builder()
                .key(key)
                .band(band)
                .song(song)
                .build();

        return bandSongVersionMapper.mapToBandSongVersionDTO(bandSongVersionRepository.save(bandSongVersion));
    }

    @Override
    public Optional<BandSongVersionDTO> getBandSongVersionById(UUID bandSongVersionId) {
        return bandSongVersionRepository.findById(bandSongVersionId).map(bandSongVersionMapper::mapToBandSongVersionDTO);
    }

    @Override
    public List<BandSongVersionDTO> getAllBandSongVersions() {
        return bandSongVersionMapper.mapAllToBandSongVersionDTOList(bandSongVersionRepository.findAll());
    }

    @Override
    public BandSongVersionDTO modifyBandSongVersionById(UUID bandSongVersionId, BandSongVersionUpdateRequest bandSongVersionUpdateRequest) throws NoSuchBandSongVersionException, NoSuchKeyException, BandSongVersionConflictException {
        BandSongVersion bandSongVersion = bandSongVersionRepository.findById(bandSongVersionId)
                .orElseThrow(() -> new NoSuchBandSongVersionException(String.format("Band song version with id=\"%s\" does not exist", bandSongVersionId)));
        Key key = keyRepository.findById(bandSongVersionUpdateRequest.getKeyId())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key with id=\"%s\" does not exist", bandSongVersionUpdateRequest.getKeyId())));

        if (!bandSongVersion.getSong().getKey().equals(key) && !bandSongVersion.getSong().getKeys().contains(key)) {
            throw new BandSongVersionConflictException(String.format("Song with id=\"%s\" does not have Key with id=\"%s\"", bandSongVersion.getSong().getId(), bandSongVersionUpdateRequest.getKeyId()));
        }

        bandSongVersion.setKey(key);

        return bandSongVersionMapper.mapToBandSongVersionDTO(bandSongVersionRepository.save(bandSongVersion));
    }

    @Override
    public void removeBandSongVersionById(UUID bandSongVersionId) throws NoSuchBandSongVersionException {
        if (!bandSongVersionRepository.existsById(bandSongVersionId)) {
            throw new NoSuchBandSongVersionException(String.format("Band song version with id=\"%s\" does not exist", bandSongVersionId));
        }

        List<SongPartKeyChord> availableToDeleteSongPartKeyChords = songPartKeyChordRepository.findAvailableToDeleteByBandSongVersionId(bandSongVersionId);
        List<SongPart> availableToDeleteSongParts = songPartRepository.findAvailableToDeleteByBandSongVersionId(bandSongVersionId);

        songPartDetailsRepository.deleteByBandSongVersionId(bandSongVersionId);
        songPartKeyChordRepository.deleteAll(availableToDeleteSongPartKeyChords);
        songPartRepository.deleteAll(availableToDeleteSongParts);
        bandSongVersionRepository.deleteById(bandSongVersionId);
    }
}
