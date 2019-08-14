package org.why.studio.schedules.services;

import org.why.studio.schedules.dto.UserCalendar;

import java.util.List;

public interface UserCalendarService {

    void saveUserCalendar(UserCalendar userCalendar);
    String getUserCalendar(String userId);

    //TODO: fetch From BD
    List<String> getCalendarIds(String userId);

    //TODO: fetch From BD
    List<String> getResourcesCalendarIds();
}
