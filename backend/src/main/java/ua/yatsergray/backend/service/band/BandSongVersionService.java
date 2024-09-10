package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.BandSongVersionDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandSongVersionEditableDTO;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchBandSongVersionException;
import ua.yatsergray.backend.exception.song.NoSuchKeyException;
import ua.yatsergray.backend.exception.song.NoSuchSongException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BandSongVersionService {

    BandSongVersionDTO addBandSongVersion(BandSongVersionEditableDTO bandSongVersionEditableDTO) throws NoSuchBandException, NoSuchSongException, NoSuchKeyException;

    Optional<BandSongVersionDTO> getBandSongVersionById(UUID id);

    List<BandSongVersionDTO> getAllBandSongVersions();

    BandSongVersionDTO modifyBandSongVersionById(UUID id, BandSongVersionEditableDTO bandSongVersionEditableDTO) throws NoSuchBandSongVersionException, NoSuchBandException, NoSuchSongException, NoSuchKeyException;

    void removeBandSongVersionById(UUID id) throws NoSuchBandSongVersionException;
}
