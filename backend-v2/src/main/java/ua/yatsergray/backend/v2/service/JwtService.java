package ua.yatsergray.backend.v2.service;

import java.util.UUID;

public interface JwtService {

    String generateUserToMusicBandInvitationToken(String userEmail, UUID musicBandId);

    boolean isUserToMusicBandInvitationTokenValid(String token, String userEmail, UUID musicBandId);
}
