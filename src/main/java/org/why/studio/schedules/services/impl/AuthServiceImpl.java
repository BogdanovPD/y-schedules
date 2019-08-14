package org.why.studio.schedules.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.why.studio.schedules.dto.UserInfo;
import org.why.studio.schedules.services.AuthService;
import org.why.studio.schedules.services.util.RestHelperService;

import static org.why.studio.schedules.util.Utils.getUuid;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RestHelperService restHelperService;

    @Value("${services.auth.user-info}")
    private String userInfoUrl;

    @Override
    public UserInfo getUserInfo(String userId) {
        getUuid(userId);
        return restHelperService.sendGetRequest(
                UriComponentsBuilder.fromUriString(userInfoUrl.concat(userId)), new UserInfo());
    }
}
