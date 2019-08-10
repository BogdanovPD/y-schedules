package org.why.studio.schedules.services;

import com.google.api.services.calendar.model.Event;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public interface GoogleCalendarService {

    List<Event> getUpcomingEvents(String calendarId, LocalDateTime start, @Nullable LocalDateTime end);
}
