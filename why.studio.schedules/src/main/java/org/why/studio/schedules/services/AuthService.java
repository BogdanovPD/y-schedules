package org.why.studio.schedules.services;

import org.why.studio.schedules.dto.UserInfo;

public interface AuthService {

    UserInfo getUserInfo(String userId);

}
