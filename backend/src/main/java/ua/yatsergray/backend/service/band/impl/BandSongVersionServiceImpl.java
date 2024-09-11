package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.BandSongVersionDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandSongVersionEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.band.BandSongVersion;
import ua.yatsergray.backend.domain.entity.song.Key;
import ua.yatsergray.backend.domain.entity.song.Song;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchBandSongVersionException;
import ua.yatsergray.backend.exception.song.NoSuchKeyException;
import ua.yatsergray.backend.exception.song.NoSuchSongException;
import ua.yatsergray.backend.mapper.band.BandSongVersionMapper;
import ua.yatsergray.backend.repository.band.BandRepository;
import ua.yatsergray.backend.repository.band.BandSongVersionRepository;
import ua.yatsergray.backend.repository.song.KeyRepository;
import ua.yatsergray.backend.repository.song.SongRepository;
import ua.yatsergray.backend.service.band.BandSongVersionService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BandSongVersionServiceImpl implements BandSongVersionService {
    private final BandSongVersionMapper bandSongVersionMapper;
    private final BandSongVersionRepository bandSongVersionRepository;
    private final KeyRepository keyRepository;
    private final BandRepository bandRepository;
    private final SongRepository songRepository;

    @Autowired
    public BandSongVersionServiceImpl(BandSongVersionMapper bandSongVersionMapper, BandSongVersionRepository bandSongVersionRepository, KeyRepository keyRepository, BandRepository bandRepository, SongRepository songRepository) {
        this.bandSongVersionMapper = bandSongVersionMapper;
        this.bandSongVersionRepository = bandSongVersionRepository;
        this.keyRepository = keyRepository;
        this.bandRepository = bandRepository;
        this.songRepository = songRepository;
    }

    @Override
    public BandSongVersionDTO addBandSongVersion(BandSongVersionEditableDTO bandSongVersionEditableDTO) throws NoSuchKeyException, NoSuchBandException, NoSuchSongException {
        Key key = keyRepository.findById(bandSongVersionEditableDTO.getKeyUUID())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key does not exist with id=%s", bandSongVersionEditableDTO.getKeyUUID())));
        Band band = bandRepository.findById(bandSongVersionEditableDTO.getBandUUID())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band does not exist with id=%s", bandSongVersionEditableDTO.getBandUUID())));
        Song song = songRepository.findById(bandSongVersionEditableDTO.getSongUUID())
                .orElseThrow(() -> new NoSuchSongException(String.format("Song does not exist with id=%s", bandSongVersionEditableDTO.getSongUUID())));

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
    public BandSongVersionDTO modifyBandSongVersionById(UUID bandSongVersionId, BandSongVersionEditableDTO bandSongVersionEditableDTO) throws NoSuchBandSongVersionException, NoSuchKeyException, NoSuchBandException, NoSuchSongException {
        BandSongVersion bandSongVersion = bandSongVersionRepository.findById(bandSongVersionId)
                .orElseThrow(() -> new NoSuchBandSongVersionException(String.format("Band song version does not exist with id=%s", bandSongVersionId)));

        Key key = keyRepository.findById(bandSongVersionEditableDTO.getKeyUUID())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key does not exist with id=%s", bandSongVersionEditableDTO.getKeyUUID())));
        Band band = bandRepository.findById(bandSongVersionEditableDTO.getBandUUID())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band does not exist with id=%s", bandSongVersionEditableDTO.getBandUUID())));
        Song song = songRepository.findById(bandSongVersionEditableDTO.getSongUUID())
                .orElseThrow(() -> new NoSuchSongException(String.format("Song does not exist with id=%s", bandSongVersionEditableDTO.getSongUUID())));

        bandSongVersion.setKey(key);
        bandSongVersion.setBand(band);
        bandSongVersion.setSong(song);

        return bandSongVersionMapper.mapToBandSongVersionDTO(bandSongVersionRepository.save(bandSongVersion));
    }

    @Override
    public void removeBandSongVersionById(UUID bandSongVersionId) throws NoSuchBandSongVersionException {
        if (!bandSongVersionRepository.existsById(bandSongVersionId)) {
            throw new NoSuchBandSongVersionException(String.format("Band song version does not exist with id=%s", bandSongVersionId));
        }

        bandSongVersionRepository.deleteById(bandSongVersionId);
    }
}
