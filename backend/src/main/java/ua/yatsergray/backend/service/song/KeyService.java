package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.KeyDTO;
import ua.yatsergray.backend.domain.request.song.KeyCreateUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.song.KeyAlreadyExistsException;
import ua.yatsergray.backend.exception.song.NoSuchKeyException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KeyService {

    KeyDTO addKey(KeyCreateUpdateRequest keyCreateUpdateRequest) throws KeyAlreadyExistsException;

    Optional<KeyDTO> getKeyById(UUID keyId);

    List<KeyDTO> getAllKeys();

    KeyDTO modifyKeyById(UUID keyId, KeyCreateUpdateRequest keyCreateUpdateRequest) throws NoSuchKeyException, KeyAlreadyExistsException;

    void removeKeyById(UUID keyId) throws NoSuchKeyException, ChildEntityExistsException;
}
