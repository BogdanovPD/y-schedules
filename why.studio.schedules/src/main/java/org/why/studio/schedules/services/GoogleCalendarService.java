package org.why.studio.schedules.services;

import com.google.api.services.calendar.model.Event;

import java.util.List;

public interface GoogleCalendarService {

    List<Event> getUpcomingEvents();

}
