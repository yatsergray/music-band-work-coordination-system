package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.KeyDTO;
import ua.yatsergray.backend.domain.dto.song.editable.KeyEditableDTO;
import ua.yatsergray.backend.exception.song.KeyAlreadyExistsException;
import ua.yatsergray.backend.exception.song.NoSuchKeyException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KeyService {

    KeyDTO addKey(KeyEditableDTO keyEditableDTO) throws KeyAlreadyExistsException;

    Optional<KeyDTO> getKeyById(UUID keyId);

    List<KeyDTO> getAllKeys();

    KeyDTO modifyKeyById(UUID keyId, KeyEditableDTO keyEditableDTO) throws NoSuchKeyException, KeyAlreadyExistsException;

    void removeKeyById(UUID keyId) throws NoSuchKeyException;
}
