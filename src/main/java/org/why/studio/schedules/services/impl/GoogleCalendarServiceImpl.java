package org.why.studio.schedules.services.impl;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.why.studio.schedules.dto.UserInfo;
import org.why.studio.schedules.services.AuthService;
import org.why.studio.schedules.services.GoogleCalendarService;
import org.why.studio.schedules.services.UserCalendarService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import static org.why.studio.schedules.util.Utils.getDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleCalendarServiceImpl implements GoogleCalendarService {

    private final Calendar calendarService;
    private final UserCalendarService userCalendarService;
    private final AuthService authService;

    @Override
    public void createUpcomingEvent(String userId, String specId, String serviceName,
                                    LocalDateTime start, LocalDateTime end) {
        String userCalendarId = userCalendarService.getUserCalendar(specId);
        UserInfo userInfo = authService.getUserInfo(userId);
        UserInfo specInfo = authService.getUserInfo(specId);
        String eventSummary = createEventSummary(userInfo, specInfo, serviceName);

        EventDateTime eventStart = new EventDateTime();
        eventStart.setDateTime(getDateTime(start));
        eventStart.setTimeZone(TimeZone.getDefault().getID());

        EventDateTime eventEnd = new EventDateTime();
        eventEnd.setDateTime(getDateTime(end));
        eventEnd.setTimeZone(TimeZone.getDefault().getID());

        Event event = new Event();
        event.setSummary(eventSummary);
        event.setStart(eventStart);
        event.setEnd(eventEnd);

        List<EventAttendee> eventAttendees = new LinkedList<>();

        userCalendarService.getResourcesCalendarIds().forEach(resId -> {
            EventAttendee room = new EventAttendee();
            room.setResource(true);
            room.setEmail(resId);
            eventAttendees.add(room);
        });

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(90),
                new EventReminder().setMethod("popup").setMinutes(40),
        };

        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        event.setAttendees(eventAttendees);

        try {
            calendarService.events().insert(userCalendarId, event).execute();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Не удается создать событие: " + e.getMessage());
        }
    }

    private String createEventSummary(UserInfo userInfo, UserInfo specInfo, String serviceName) {
        return String.format("Клиент: %s %s, Специалист: %s %s, %s",
                userInfo.getFirstName(), userInfo.getLastName(),
                specInfo.getFirstName(), specInfo.getLastName(),
                serviceName);
    }

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
