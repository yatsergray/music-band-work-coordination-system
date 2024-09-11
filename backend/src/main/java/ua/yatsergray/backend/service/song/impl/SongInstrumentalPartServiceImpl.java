package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongInstrumentalPartDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongInstrumentalPartEditableDTO;
import ua.yatsergray.backend.domain.entity.band.StageRole;
import ua.yatsergray.backend.domain.entity.song.Song;
import ua.yatsergray.backend.domain.entity.song.SongInstrumentalPart;
import ua.yatsergray.backend.exception.band.NoSuchStageRoleException;
import ua.yatsergray.backend.exception.song.NoSuchSongException;
import ua.yatsergray.backend.exception.song.NoSuchSongInstrumentalPartException;
import ua.yatsergray.backend.mapper.song.SongInstrumentalPartMapper;
import ua.yatsergray.backend.repository.band.StageRoleRepository;
import ua.yatsergray.backend.repository.song.SongInstrumentalPartRepository;
import ua.yatsergray.backend.repository.song.SongRepository;
import ua.yatsergray.backend.service.song.SongInstrumentalPartService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public SongInstrumentalPartDTO addSongInstrumentalPart(SongInstrumentalPartEditableDTO songInstrumentalPartEditableDTO) throws NoSuchSongException, NoSuchStageRoleException {
        return songInstrumentalPartMapper.mapToSongInstrumentalPartDTO(songInstrumentalPartRepository.save(configureSongInstrumentalPart(new SongInstrumentalPart(), songInstrumentalPartEditableDTO)));
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
    public SongInstrumentalPartDTO modifySongInstrumentalPartById(UUID songInstrumentalPartId, SongInstrumentalPartEditableDTO songInstrumentalPartEditableDTO) throws NoSuchSongInstrumentalPartException, NoSuchSongException, NoSuchStageRoleException {
        SongInstrumentalPart songInstrumentalPart = songInstrumentalPartRepository.findById(songInstrumentalPartId)
                .orElseThrow(() -> new NoSuchSongInstrumentalPartException(String.format("Song instrumental part does not exist with id=%s", songInstrumentalPartId)));

        return songInstrumentalPartMapper.mapToSongInstrumentalPartDTO(songInstrumentalPartRepository.save(configureSongInstrumentalPart(songInstrumentalPart, songInstrumentalPartEditableDTO)));
    }

    @Override
    public void removeSongInstrumentalPartById(UUID songInstrumentalPartId) throws NoSuchSongInstrumentalPartException {
        if (songInstrumentalPartRepository.existsById(songInstrumentalPartId)) {
            throw new NoSuchSongInstrumentalPartException(String.format("Song instrumental part does not exist with id=%s", songInstrumentalPartId));
        }

        songInstrumentalPartRepository.deleteById(songInstrumentalPartId);
    }

    private SongInstrumentalPart configureSongInstrumentalPart(SongInstrumentalPart songInstrumentalPart, SongInstrumentalPartEditableDTO songInstrumentalPartEditableDTO) throws NoSuchSongException, NoSuchStageRoleException {
        Song song = songRepository.findById(songInstrumentalPartEditableDTO.getSongUUID())
                .orElseThrow(() -> new NoSuchSongException(String.format("Song does not exist with id=%s", songInstrumentalPartEditableDTO.getSongUUID())));
        StageRole stageRole = stageRoleRepository.findById(songInstrumentalPartEditableDTO.getStageRoleUUID())
                .orElseThrow(() -> new NoSuchStageRoleException(String.format("Stage role does not exist with id=%s", songInstrumentalPartEditableDTO.getStageRoleUUID())));

        songInstrumentalPart.setSong(song);
        songInstrumentalPart.setStageRole(stageRole);

        return songInstrumentalPart;
    }
}
