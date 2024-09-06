package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.KeyDTO;
import ua.yatsergray.backend.domain.dto.song.editable.KeyEditableDTO;
import ua.yatsergray.backend.exception.song.NoSuchKeyException;
import ua.yatsergray.backend.mapper.song.KeyMapper;
import ua.yatsergray.backend.repository.song.KeyRepository;
import ua.yatsergray.backend.service.song.KeyService;

import java.util.List;
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
    public KeyDTO addKey(KeyEditableDTO keyEditableDTO) {
        return keyMapper.mapToKeyDTO(keyRepository.save(keyMapper.mapToKey(keyEditableDTO)));
    }

    @Override
    public Optional<KeyDTO> getKeyById(UUID id) {
        return keyRepository.findById(id).map(keyMapper::mapToKeyDTO);
    }

    @Override
    public List<KeyDTO> getAllKeys() {
        return keyMapper.mapAllToKeyDTOList(keyRepository.findAll());
    }

    @Override
    public KeyDTO modifyKeyById(UUID id, KeyEditableDTO keyEditableDTO) throws NoSuchKeyException {
        return keyRepository.findById(id)
                .map(key -> {
                    key.setName(keyEditableDTO.getName());

                    return keyMapper.mapToKeyDTO(keyRepository.save(key));
                })
                .orElseThrow(() -> new NoSuchKeyException(String.format("Key does not exist with id=%s", id)));
    }

    @Override
    public void removeKeyById(UUID id) throws NoSuchKeyException {
        if (!keyRepository.existsById(id)) {
            throw new NoSuchKeyException(String.format("Key does not exist with id=%s", id));
        }

        keyRepository.deleteById(id);
    }
}
