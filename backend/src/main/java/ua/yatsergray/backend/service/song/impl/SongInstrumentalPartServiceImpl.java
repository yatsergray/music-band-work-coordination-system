package ua.yatsergray.backend.service.song.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongInstrumentalPartDTO;
import ua.yatsergray.backend.domain.entity.band.StageRole;
import ua.yatsergray.backend.domain.entity.song.Song;
import ua.yatsergray.backend.domain.entity.song.SongInstrumentalPart;
import ua.yatsergray.backend.domain.request.song.SongInstrumentalPartCreateRequest;
import ua.yatsergray.backend.exception.band.NoSuchStageRoleException;
import ua.yatsergray.backend.exception.song.NoSuchSongException;
import ua.yatsergray.backend.exception.song.NoSuchSongInstrumentalPartException;
import ua.yatsergray.backend.exception.song.SongInstrumentalPartAlreadyExistsException;
import ua.yatsergray.backend.mapper.song.SongInstrumentalPartMapper;
import ua.yatsergray.backend.repository.band.StageRoleRepository;
import ua.yatsergray.backend.repository.song.SongInstrumentalPartRepository;
import ua.yatsergray.backend.repository.song.SongRepository;
import ua.yatsergray.backend.service.song.SongInstrumentalPartService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class SongInstrumentalPartServiceImpl implements SongInstrumentalPartService {
    private final SongInstrumentalPartMapper songInstrumentalPartMapper;
    private final SongInstrumentalPartRepository songInstrumentalPartRepository;
    private final SongRepository songRepository;
    private final StageRoleRepository stageRoleRepository;

    @Autowired
    public SongInstrumentalPartServiceImpl(SongInstrumentalPartMapper songInstrumentalPartMapper, SongInstrumentalPartRepository songInstrumentalPartRepository, SongRepository songRepository, StageRoleRepository stageRoleRepository) {
        this.songInstrumentalPartMapper = songInstrumentalPartMapper;
        this.songInstrumentalPartRepository = songInstrumentalPartRepository;
        this.songRepository = songRepository;
        this.stageRoleRepository = stageRoleRepository;
    }

    @Override
    public SongInstrumentalPartDTO addSongInstrumentalPart(SongInstrumentalPartCreateRequest songInstrumentalPartCreateRequest) throws NoSuchSongException, NoSuchStageRoleException, SongInstrumentalPartAlreadyExistsException {
        Song song = songRepository.findById(songInstrumentalPartCreateRequest.getSongId())
                .orElseThrow(() -> new NoSuchSongException(String.format("Song with id=\"%s\" does not exist", songInstrumentalPartCreateRequest.getSongId())));
        StageRole stageRole = stageRoleRepository.findById(songInstrumentalPartCreateRequest.getStageRoleId())
                .orElseThrow(() -> new NoSuchStageRoleException(String.format("Stage role with id=\"%s\" does not exist", songInstrumentalPartCreateRequest.getStageRoleId())));

        if (songInstrumentalPartRepository.existsBySongIdAndStageRoleId(songInstrumentalPartCreateRequest.getSongId(), songInstrumentalPartCreateRequest.getStageRoleId())) {
            throw new SongInstrumentalPartAlreadyExistsException(String.format("Song instrumental part with songId=\"%s\" and stageRoleId=\"%s\" already exists", songInstrumentalPartCreateRequest.getSongId(), songInstrumentalPartCreateRequest.getStageRoleId()));
        }

        SongInstrumentalPart songInstrumentalPart = SongInstrumentalPart.builder()
                .song(song)
                .stageRole(stageRole)
                .build();

        return songInstrumentalPartMapper.mapToSongInstrumentalPartDTO(songInstrumentalPartRepository.save(songInstrumentalPart));
    }

    @Override
    public Optional<SongInstrumentalPartDTO> getSongInstrumentalPartById(UUID songInstrumentalPartId) {
        return songInstrumentalPartRepository.findById(songInstrumentalPartId).map(songInstrumentalPartMapper::mapToSongInstrumentalPartDTO);
    }

    @Override
    public List<SongInstrumentalPartDTO> getAllSongInstrumentalParts() {
        return songInstrumentalPartMapper.mapAllToSongInstrumentalPartDTOList(songInstrumentalPartRepository.findAll());
    }

    @Override
    public void removeSongInstrumentalPartById(UUID songInstrumentalPartId) throws NoSuchSongInstrumentalPartException {
        if (songInstrumentalPartRepository.existsById(songInstrumentalPartId)) {
            throw new NoSuchSongInstrumentalPartException(String.format("Song instrumental part with id=\"%s\" does not exist", songInstrumentalPartId));
        }

        songInstrumentalPartRepository.deleteById(songInstrumentalPartId);
    }
}
