package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.BandSongVersionDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandSongVersionEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.band.BandSongVersion;
import ua.yatsergray.backend.domain.entity.song.Key;
import ua.yatsergray.backend.domain.entity.song.Song;
import ua.yatsergray.backend.exception.band.BandSongVersionConflictException;
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
    public BandSongVersionDTO addBandSongVersion(BandSongVersionEditableDTO bandSongVersionEditableDTO) throws NoSuchKeyException, NoSuchBandException, NoSuchSongException, BandSongVersionConflictException {
        return bandSongVersionMapper.mapToBandSongVersionDTO(bandSongVersionRepository.save(configureBandSongVersion(new BandSongVersion(), bandSongVersionEditableDTO)));
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
    public BandSongVersionDTO modifyBandSongVersionById(UUID bandSongVersionId, BandSongVersionEditableDTO bandSongVersionEditableDTO) throws NoSuchBandSongVersionException, NoSuchKeyException, NoSuchBandException, NoSuchSongException, BandSongVersionConflictException {
        BandSongVersion bandSongVersion = bandSongVersionRepository.findById(bandSongVersionId)
                .orElseThrow(() -> new NoSuchBandSongVersionException(String.format("Band song version with id=%s does not exist", bandSongVersionId)));

        return bandSongVersionMapper.mapToBandSongVersionDTO(bandSongVersionRepository.save(configureBandSongVersion(bandSongVersion, bandSongVersionEditableDTO)));
    }

    @Override
    public void removeBandSongVersionById(UUID bandSongVersionId) throws NoSuchBandSongVersionException {
        if (!bandSongVersionRepository.existsById(bandSongVersionId)) {
            throw new NoSuchBandSongVersionException(String.format("Band song version with id=%s does not exist", bandSongVersionId));
        }

        bandSongVersionRepository.deleteById(bandSongVersionId);
    }

    private BandSongVersion configureBandSongVersion(BandSongVersion bandSongVersion, BandSongVersionEditableDTO bandSongVersionEditableDTO) throws NoSuchKeyException, NoSuchBandException, NoSuchSongException, BandSongVersionConflictException {
        Key key = keyRepository.findById(bandSongVersionEditableDTO.getKeyId())
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key with id=%s does not exist", bandSongVersionEditableDTO.getKeyId())));
        Band band = bandRepository.findById(bandSongVersionEditableDTO.getBandId())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=%s does not exist", bandSongVersionEditableDTO.getBandId())));
        Song song = songRepository.findById(bandSongVersionEditableDTO.getSongId())
                .orElseThrow(() -> new NoSuchSongException(String.format("Song with id=%s does not exist", bandSongVersionEditableDTO.getSongId())));

        if (!song.getKeys().contains(key)) {
            throw new BandSongVersionConflictException(String.format("Song with id=%s does not have key with id=%s", bandSongVersionEditableDTO.getSongId(), bandSongVersionEditableDTO.getKeyId()));
        }

        bandSongVersion.setKey(key);
        bandSongVersion.setBand(band);
        bandSongVersion.setSong(song);

        return bandSongVersion;
    }
}
