package ua.yatsergray.backend.service.song.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.KeyDTO;
import ua.yatsergray.backend.domain.dto.song.editable.KeyEditableDTO;
import ua.yatsergray.backend.domain.entity.song.Key;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.song.KeyAlreadyExistsException;
import ua.yatsergray.backend.exception.song.NoSuchKeyException;
import ua.yatsergray.backend.mapper.song.KeyMapper;
import ua.yatsergray.backend.repository.band.BandSongVersionRepository;
import ua.yatsergray.backend.repository.song.KeyRepository;
import ua.yatsergray.backend.repository.song.SongPartKeyChordRepository;
import ua.yatsergray.backend.repository.song.SongRepository;
import ua.yatsergray.backend.service.song.KeyService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class KeyServiceImpl implements KeyService {
    private final KeyMapper keyMapper;
    private final KeyRepository keyRepository;
    private final SongRepository songRepository;
    private final BandSongVersionRepository bandSongVersionRepository;
    private final SongPartKeyChordRepository songPartKeyChordRepository;

    @Autowired
    public KeyServiceImpl(KeyMapper keyMapper, KeyRepository keyRepository, SongRepository songRepository, BandSongVersionRepository bandSongVersionRepository, SongPartKeyChordRepository songPartKeyChordRepository) {
        this.keyMapper = keyMapper;
        this.keyRepository = keyRepository;
        this.songRepository = songRepository;
        this.bandSongVersionRepository = bandSongVersionRepository;
        this.songPartKeyChordRepository = songPartKeyChordRepository;
    }

    @Override
    public KeyDTO addKey(KeyEditableDTO keyEditableDTO) throws KeyAlreadyExistsException {
        return keyMapper.mapToKeyDTO(keyRepository.save(configureKey(new Key(), keyEditableDTO)));
    }

    @Override
    public Optional<KeyDTO> getKeyById(UUID keyId) {
        return keyRepository.findById(keyId).map(keyMapper::mapToKeyDTO);
    }

    @Override
    public List<KeyDTO> getAllKeys() {
        return keyMapper.mapAllToKeyDTOList(keyRepository.findAll());
    }

    @Override
    public KeyDTO modifyKeyById(UUID keyId, KeyEditableDTO keyEditableDTO) throws NoSuchKeyException, KeyAlreadyExistsException {
        Key key = keyRepository.findById(keyId)
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key with id=\"%s\" does not exist", keyId)));

        return keyMapper.mapToKeyDTO(keyRepository.save(configureKey(key, keyEditableDTO)));
    }

    @Override
    public void removeKeyById(UUID keyId) throws NoSuchKeyException, ChildEntityExistsException {
        if (!keyRepository.existsById(keyId)) {
            throw new NoSuchKeyException(String.format("Key with id=\"%s\" does not exist", keyId));
        }

        checkIfKeyHasChildEntity(keyId);

        keyRepository.deleteById(keyId);
    }

    private Key configureKey(Key key, KeyEditableDTO keyEditableDTO) throws KeyAlreadyExistsException {
        if (Objects.isNull(key.getId())) {
            if (keyRepository.existsByName(keyEditableDTO.getName())) {
                throw new KeyAlreadyExistsException(String.format("Key with name=\"%s\" already exists", keyEditableDTO.getName()));
            }
        } else {
            if (!keyEditableDTO.getName().equals(key.getName()) && keyRepository.existsByName(keyEditableDTO.getName())) {
                throw new KeyAlreadyExistsException(String.format("Key with name=\"%s\" already exists", keyEditableDTO.getName()));
            }
        }

        key.setName(keyEditableDTO.getName());

        return key;
    }

    private void checkIfKeyHasChildEntity(UUID keyId) throws ChildEntityExistsException {
        long keyChildEntityAmount = bandSongVersionRepository.countByKeyId(keyId);

        if (keyChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%d Band song version(s) depend(s) on the Key with id=%s", keyChildEntityAmount, keyId));
        }

        keyChildEntityAmount = songRepository.countByKeyId(keyId);

        if (keyChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%d Song(s) depend(s) on the Key with id=%s", keyChildEntityAmount, keyId));
        }

        keyChildEntityAmount = songPartKeyChordRepository.countByKeyId(keyId);

        if (keyChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%d Song part key chord(s) depend(s) on the Key with id=%s", keyChildEntityAmount, keyId));
        }

        keyChildEntityAmount = songRepository.countByKeysId(keyId);

        if (keyChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%d Song(s) depend(s) on the Key with id=%s", keyChildEntityAmount, keyId));
        }
    }
}
