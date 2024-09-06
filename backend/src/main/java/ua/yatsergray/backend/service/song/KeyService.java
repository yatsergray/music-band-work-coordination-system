package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.KeyDTO;
import ua.yatsergray.backend.domain.dto.song.editable.KeyEditableDTO;
import ua.yatsergray.backend.exception.song.NoSuchKeyException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KeyService {

    KeyDTO addKey(KeyEditableDTO keyEditableDTO);

    Optional<KeyDTO> getKeyById(UUID id);

    List<KeyDTO> getAllKeys();

    KeyDTO modifyKeyById(UUID id, KeyEditableDTO keyEditableDTO) throws NoSuchKeyException;

    void removeKeyById(UUID id) throws NoSuchKeyException;
}
