package org.why.studio.schedules.services.impl;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.why.studio.schedules.services.GoogleCalendarService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.why.studio.schedules.util.Utils.getDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleCalendarServiceImpl implements GoogleCalendarService {

    private final Calendar calendarService;

    @Override
    public List<Event> getUpcomingEvents(String calendarId, LocalDateTime start,  LocalDateTime end) {
        Events events = null;
        try {
            Calendar.Events.List list = calendarService.events().list(calendarId)
                    .setTimeMin(getDateTime(start))
                    .setOrderBy("startTime")
                    .setSingleEvents(true);
            if (end != null && end.isAfter(start)) {
                list.setTimeMax(getDateTime(end));
            }
            events = list.execute();
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
