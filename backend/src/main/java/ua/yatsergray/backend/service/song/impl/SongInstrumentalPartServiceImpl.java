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
        Song song = songRepository.findById(songInstrumentalPartEditableDTO.getSongUUID())
                .orElseThrow(() -> new NoSuchSongException(String.format("Song does not exist with id=%s", songInstrumentalPartEditableDTO.getSongUUID())));
        StageRole stageRole = stageRoleRepository.findById(songInstrumentalPartEditableDTO.getStageRoleUUID())
                .orElseThrow(() -> new NoSuchStageRoleException(String.format("Stage role does not exist with id=%s", songInstrumentalPartEditableDTO.getStageRoleUUID())));

        SongInstrumentalPart songInstrumentalPart = new SongInstrumentalPart();

        songInstrumentalPart.setSong(song);
        songInstrumentalPart.setStageRole(stageRole);

        return songInstrumentalPartMapper.mapToSongInstrumentalPartDTO(songInstrumentalPartRepository.save(songInstrumentalPart));
    }

    @Override
    public Optional<SongInstrumentalPartDTO> getSongInstrumentalPartById(UUID id) {
        return songInstrumentalPartRepository.findById(id).map(songInstrumentalPartMapper::mapToSongInstrumentalPartDTO);
    }

    @Override
    public List<SongInstrumentalPartDTO> getAllSongInstrumentalParts() {
        return songInstrumentalPartMapper.mapAllToSongInstrumentalPartDTOList(songInstrumentalPartRepository.findAll());
    }

    @Override
    public SongInstrumentalPartDTO modifySongInstrumentalPartById(UUID id, SongInstrumentalPartEditableDTO songInstrumentalPartEditableDTO) throws NoSuchSongInstrumentalPartException, NoSuchSongException, NoSuchStageRoleException {
        SongInstrumentalPart songInstrumentalPart = songInstrumentalPartRepository.findById(id)
                .orElseThrow(() -> new NoSuchSongInstrumentalPartException(String.format("Song instrumental part does not exist with id=%s", id)));
        Song song = songRepository.findById(songInstrumentalPartEditableDTO.getSongUUID())
                .orElseThrow(() -> new NoSuchSongException(String.format("Song does not exist with id=%s", songInstrumentalPartEditableDTO.getSongUUID())));
        StageRole stageRole = stageRoleRepository.findById(songInstrumentalPartEditableDTO.getStageRoleUUID())
                .orElseThrow(() -> new NoSuchStageRoleException(String.format("Stage role does not exist with id=%s", songInstrumentalPartEditableDTO.getStageRoleUUID())));
//        String audioFileName = songInstrumentalPartEditableDTO.getAudioFileName();
//        String videoFileName = songInstrumentalPartEditableDTO.getVideoFileName();
//        String tabFileName = songInstrumentalPartEditableDTO.getTabFileName();

//        songInstrumentalPart.setAudioFileURL(audioFileName);
//        songInstrumentalPart.setVideoFileURL(videoFileName);
//        songInstrumentalPart.setTabFileURL(tabFileName);
        songInstrumentalPart.setSong(song);
        songInstrumentalPart.setStageRole(stageRole);

        return songInstrumentalPartMapper.mapToSongInstrumentalPartDTO(songInstrumentalPartRepository.save(songInstrumentalPart));
    }

    @Override
    public void removeSongInstrumentalPartById(UUID id) throws NoSuchSongInstrumentalPartException {
        if (songInstrumentalPartRepository.existsById(id)) {
            throw new NoSuchSongInstrumentalPartException(String.format("Song instrumental part does not exist with id=%s", id));
        }

        songInstrumentalPartRepository.deleteById(id);
    }
}
