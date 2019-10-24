package org.why.studio.schedules.services;

import org.why.studio.schedules.dto.UserInfo;
import org.why.studio.schedules.dto.UserInfoWithId;
import org.why.studio.schedules.dto.UserPrimarySpecialist;

import java.util.List;

public interface UserPrimarySpecialistService {

    void requestSetPrimarySpecialist(UserPrimarySpecialist userPrimarySpecialist);
    List<UserInfoWithId> getUserRequestsForSpecialist(String specId);
    List<UserInfoWithId> getSpecialistClients(String specId);
    UserInfo getUserPrimarySpecialistInfo(String userId);
    String getUserPrimarySpecialistId(String userId);
    void acceptRequest(String userId, String specId);
    void rejectRequest(String userId, String specId);
}
