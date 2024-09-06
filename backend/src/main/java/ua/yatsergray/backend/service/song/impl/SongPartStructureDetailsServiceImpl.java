package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongPartStructureDetailsDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartStructureDetailsEditableDTO;
import ua.yatsergray.backend.domain.entity.song.SongPart;
import ua.yatsergray.backend.domain.entity.song.SongPartStructureDetails;
import ua.yatsergray.backend.domain.entity.song.SongStructure;
import ua.yatsergray.backend.exception.song.NoSuchSongPartException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartStructureDetailsException;
import ua.yatsergray.backend.exception.song.NoSuchSongStructureException;
import ua.yatsergray.backend.mapper.song.SongPartStructureDetailsMapper;
import ua.yatsergray.backend.repository.song.SongPartRepository;
import ua.yatsergray.backend.repository.song.SongPartStructureDetailsRepository;
import ua.yatsergray.backend.repository.song.SongStructureRepository;
import ua.yatsergray.backend.service.song.SongPartStructureDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SongPartStructureDetailsServiceImpl implements SongPartStructureDetailsService {
    private final SongPartStructureDetailsMapper songPartStructureDetailsMapper;
    private final SongPartStructureDetailsRepository songPartStructureDetailsRepository;
    private final SongPartRepository songPartRepository;
    private final SongStructureRepository songStructureRepository;

    @Autowired
    public SongPartStructureDetailsServiceImpl(SongPartStructureDetailsMapper songPartStructureDetailsMapper, SongPartStructureDetailsRepository songPartStructureDetailsRepository, SongPartRepository songPartRepository, SongStructureRepository songStructureRepository) {
        this.songPartStructureDetailsMapper = songPartStructureDetailsMapper;
        this.songPartStructureDetailsRepository = songPartStructureDetailsRepository;
        this.songPartRepository = songPartRepository;
        this.songStructureRepository = songStructureRepository;
    }

    @Override
    public SongPartStructureDetailsDTO addSongPartStructureDetails(SongPartStructureDetailsEditableDTO songPartStructureDetailsEditableDTO) throws NoSuchSongPartException, NoSuchSongStructureException {
        SongPart songPart = songPartRepository.findById(songPartStructureDetailsEditableDTO.getSongPartUUID())
                .orElseThrow(() -> new NoSuchSongPartException(String.format("Song part does not exist with id=%s", songPartStructureDetailsEditableDTO.getSongPartUUID())));
        SongStructure songStructure = songStructureRepository.findById(songPartStructureDetailsEditableDTO.getSongPartUUID())
                .orElseThrow(() -> new NoSuchSongStructureException(String.format("Song structure does not exist with id=%s", songPartStructureDetailsEditableDTO.getSongPartUUID())));

        SongPartStructureDetails songPartStructureDetails = songPartStructureDetailsMapper.mapToSongPartStructureDetails(songPartStructureDetailsEditableDTO);

        songPartStructureDetails.setSongPart(songPart);
        songPartStructureDetails.setSongStructure(songStructure);

        return songPartStructureDetailsMapper.mapToSongPartStructureDetailsDTO(songPartStructureDetailsRepository.save(songPartStructureDetails));
    }

    @Override
    public Optional<SongPartStructureDetailsDTO> getSongPartStructureDetailsById(UUID id) {
        return songPartStructureDetailsRepository.findById(id).map(songPartStructureDetailsMapper::mapToSongPartStructureDetailsDTO);
    }

    @Override
    public List<SongPartStructureDetailsDTO> getAllSongPartStructureDetails() {
        return songPartStructureDetailsMapper.mapAllToSongPartStructureDetailsDTOList(songPartStructureDetailsRepository.findAll());
    }

    @Override
    public SongPartStructureDetailsDTO modifySongPartStructureDetailsById(UUID id, SongPartStructureDetailsEditableDTO songPartStructureDetailsEditableDTO) throws NoSuchSongPartStructureDetailsException, NoSuchSongPartException, NoSuchSongStructureException {
        SongPartStructureDetails songPartStructureDetails = songPartStructureDetailsRepository.findById(id)
                .orElseThrow(() -> new NoSuchSongPartStructureDetailsException(String.format("Song part structure details does not exist with id=%s", id)));
        SongPart songPart = songPartRepository.findById(songPartStructureDetailsEditableDTO.getSongPartUUID())
                .orElseThrow(() -> new NoSuchSongPartException(String.format("Song part does not exist with id=%s", songPartStructureDetailsEditableDTO.getSongPartUUID())));
        SongStructure songStructure = songStructureRepository.findById(songPartStructureDetailsEditableDTO.getSongPartUUID())
                .orElseThrow(() -> new NoSuchSongStructureException(String.format("Song structure does not exist with id=%s", songPartStructureDetailsEditableDTO.getSongPartUUID())));

        songPartStructureDetails.setSequenceNumber(songPartStructureDetailsEditableDTO.getSequenceNumber());
        songPartStructureDetails.setRepeatNumber(songPartStructureDetailsEditableDTO.getRepeatNumber());
        songPartStructureDetails.setSongPart(songPart);
        songPartStructureDetails.setSongStructure(songStructure);

        return songPartStructureDetailsMapper.mapToSongPartStructureDetailsDTO(songPartStructureDetailsRepository.save(songPartStructureDetails));
    }

    @Override
    public void removeSongPartStructureDetailsById(UUID id) throws NoSuchSongPartStructureDetailsException {
        if (!songPartStructureDetailsRepository.existsById(id)) {
            throw new NoSuchSongPartStructureDetailsException(String.format("Song part structure details does not exist with id=%s", id));
        }

        songPartStructureDetailsRepository.deleteById(id);
    }
}
