package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.BandSongVersionDTO;
import ua.yatsergray.backend.domain.request.band.BandSongVersionCreateRequest;
import ua.yatsergray.backend.domain.request.band.BandSongVersionUpdateRequest;
import ua.yatsergray.backend.exception.band.BandSongVersionConflictException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchBandSongVersionException;
import ua.yatsergray.backend.exception.song.NoSuchKeyException;
import ua.yatsergray.backend.exception.song.NoSuchSongException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BandSongVersionService {

    BandSongVersionDTO addBandSongVersion(BandSongVersionCreateRequest bandSongVersionCreateRequest) throws NoSuchBandException, NoSuchSongException, NoSuchKeyException, BandSongVersionConflictException;

    Optional<BandSongVersionDTO> getBandSongVersionById(UUID bandSongVersionId);

    List<BandSongVersionDTO> getAllBandSongVersions();

    BandSongVersionDTO modifyBandSongVersionById(UUID bandSongVersionId, BandSongVersionUpdateRequest bandSongVersionUpdateRequest) throws NoSuchBandSongVersionException, NoSuchBandException, NoSuchSongException, NoSuchKeyException, BandSongVersionConflictException;

    void removeBandSongVersionById(UUID bandSongVersionId) throws NoSuchBandSongVersionException;
}
