package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.KeyDTO;
import ua.yatsergray.backend.domain.dto.song.editable.KeyEditableDTO;
import ua.yatsergray.backend.domain.entity.song.Key;
import ua.yatsergray.backend.exception.song.KeyAlreadyExistsException;
import ua.yatsergray.backend.exception.song.NoSuchKeyException;
import ua.yatsergray.backend.mapper.song.KeyMapper;
import ua.yatsergray.backend.repository.song.KeyRepository;
import ua.yatsergray.backend.service.song.KeyService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class KeyServiceImpl implements KeyService {
    private final KeyMapper keyMapper;
    private final KeyRepository keyRepository;

    @Autowired
    public KeyServiceImpl(KeyMapper keyMapper, KeyRepository keyRepository) {
        this.keyMapper = keyMapper;
        this.keyRepository = keyRepository;
    }

    @Override
    public KeyDTO addKey(KeyEditableDTO keyEditableDTO) throws KeyAlreadyExistsException {
        return keyMapper.mapToKeyDTO(configureKey(new Key(), keyEditableDTO));
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
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key with id=%s does not exist", keyId)));

        return keyMapper.mapToKeyDTO(configureKey(key, keyEditableDTO));
    }

    @Override
    public void removeKeyById(UUID keyId) throws NoSuchKeyException {
        if (!keyRepository.existsById(keyId)) {
            throw new NoSuchKeyException(String.format("Key with id=%s does not exist", keyId));
        }

        keyRepository.deleteById(keyId);
    }

    private Key configureKey(Key key, KeyEditableDTO keyEditableDTO) throws KeyAlreadyExistsException {
        if (Objects.isNull(key.getId())) {
            if (keyRepository.existsByName(keyEditableDTO.getName())) {
                throw new KeyAlreadyExistsException(String.format("Key with name=%s already exists", keyEditableDTO.getName()));
            }
        } else {
            if (!keyEditableDTO.getName().equals(key.getName()) && keyRepository.existsByName(keyEditableDTO.getName())) {
                throw new KeyAlreadyExistsException(String.format("Key with name=%s already exists", keyEditableDTO.getName()));
            }
        }

        key.setName(keyEditableDTO.getName());

        return key;
    }
}
