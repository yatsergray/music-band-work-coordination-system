package ua.yatsergray.backend.v2.service;

import java.util.UUID;

public interface JwtService {

    String generateInvitationToken(String userEmail, UUID musicBandId);

    boolean isInvitationTokenValid(String invitationToken, String userEmail, UUID musicBandId);
}
