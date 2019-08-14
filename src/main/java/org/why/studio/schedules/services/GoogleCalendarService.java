package org.why.studio.schedules.services;

import com.google.api.services.calendar.model.Event;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface GoogleCalendarService {

    void createUpcomingEvent(String userId, String specId, String serviceName,
                             LocalDateTime start, LocalDateTime end);

    List<Event> getUpcomingEvents(String calendarId, LocalDateTime start, @Nullable LocalDateTime end);
}
