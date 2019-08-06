package org.why.studio.schedules.services.impl;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.why.studio.schedules.services.GoogleCalendarService;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleCalendarServiceImpl implements GoogleCalendarService {

    @Value("${calendar.id}")
    private String calendarId;
    private final Calendar calendarService;

    @Override
    public List<Event> getUpcomingEvents() {
        long millis = System.currentTimeMillis();
        DateTime now = new DateTime(millis);
        Events events = null;
        try {
            events = calendarService.events().list(calendarId)
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
        } catch (IOException e) {
            log.error("Ошибка при получении списка предстоящих событий: " + e.getMessage());
        }
        if (events == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Events is null for some reason");
        }
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            log.warn("Предстоящих событий не найдено");
        }
        return items;
    }
}
