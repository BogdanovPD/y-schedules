package org.why.studio.schedules.services;

import org.why.studio.schedules.dto.UserLog;

import java.util.List;

public interface UserLogService {

    List<UserLog> getUserLog(String userId);
    void saveUserLog(String userId, String message);
    void deleteOldLog();

}
