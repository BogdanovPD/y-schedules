package org.why.studio.schedules.services;

import org.why.studio.schedules.dto.UserCalendar;

public interface UserCalendarService {

    void saveUserCalendar(UserCalendar userCalendar);
    String getUserCalendar(String userId);

}
