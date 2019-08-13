package org.why.studio.schedules.services.impl;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.FreeBusyRequest;
import com.google.api.services.calendar.model.FreeBusyRequestItem;
import com.google.api.services.calendar.model.FreeBusyResponse;
import com.google.api.services.calendar.model.TimePeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.why.studio.schedules.services.FreeTimeService;
import org.why.studio.schedules.services.UserCalendarService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import static org.why.studio.schedules.constants.SchedulesConstants.*;
import static org.why.studio.schedules.util.Utils.*;

@Service
@RequiredArgsConstructor
public class FreeTimeServiceImpl implements FreeTimeService {

    private final Calendar calendarService;
    private final UserCalendarService userCalendarService;

    @Override
    public List<LocalDateTime> getSpecFreeTime(String userId, LocalDate date, int serviceDuration) {
        LocalDateTime start = date.atStartOfDay().isBefore(LocalDateTime.now())
                //если записываюся сегодня, близжайшая запись минимум через MIN_HOURS_BEFORE_DECLINE
                ? LocalDateTime.now().plusHours(MIN_HOURS_BEFORE_DECLINE).truncatedTo(ChronoUnit.HOURS)
                : date.atStartOfDay().plusHours(FIRST_CONSULTATION_HOUR);
        LocalDateTime end = date.atStartOfDay().plusDays(1).minusSeconds(1);


        FreeBusyResponse freeBusyResponse = getFreebusyResponse(userId, start, end);
        List<TimePeriod> busy = new LinkedList<>();
        userCalendarService.getCalendarIds(userId).forEach(
                calId -> busy.addAll(freeBusyResponse.getCalendars().get(calId).getBusy()));
        LinkedList<TimePeriod> busyTimePeriods = new LinkedList<>(new LinkedHashSet<>(busy));
        return getUserFreeTimeList(serviceDuration, start, busyTimePeriods);
    }

    private List<FreeBusyRequestItem> getCalendars(String userId) {
        List<FreeBusyRequestItem> freeBusyRequestItems = new LinkedList<>();
        for(String calId : userCalendarService.getCalendarIds(userId)) {
            FreeBusyRequestItem itemUserCalendar = new FreeBusyRequestItem();
            itemUserCalendar.setId(calId);
            freeBusyRequestItems.add(itemUserCalendar);
        }
        return freeBusyRequestItems;
    }

    private FreeBusyResponse getFreebusyResponse(String userId, LocalDateTime start, LocalDateTime end) {
        FreeBusyRequest freeBusyRequest = new FreeBusyRequest();
        freeBusyRequest.setItems(getCalendars(userId));
        freeBusyRequest.setTimeZone(TimeZone.getDefault().getID());
        freeBusyRequest.setTimeMin(getDateTime(start));
        freeBusyRequest.setTimeMax(getDateTime(end));
        FreeBusyResponse freeBusyResponse;
        try {
            freeBusyResponse = calendarService.freebusy().query(freeBusyRequest).execute();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ошибка при получении свободного времени: " + e.getMessage());
        }
        return freeBusyResponse;
    }

    private List<LocalDateTime> getUserFreeTimeList(int serviceDuration, LocalDateTime bookingStart,
                                                    List<TimePeriod> busy) {
        List<LocalDateTime> userFreeTimes = new LinkedList<>();
        int i = bookingStart.getHour();
        while (i <= LAST_CONSULTATION_HOUR) {
            LocalDateTime start = bookingStart.withHour(i);
            LocalDateTime end = start.plusMinutes(serviceDuration);
            if (!isBusy(busy, start, end) && end.minusSeconds(1).getHour() < END_DAY) {
                userFreeTimes.add(start);
            }
            i++;
        }
        return userFreeTimes;
    }

    private boolean isBusy(List<TimePeriod> busy, LocalDateTime start, LocalDateTime end) {
        return busy.stream().anyMatch(tp -> {
            LocalDateTime busyPeriodStart = getLocalDateTime(tp.getStart());
            LocalDateTime busyPeriodEnd = getLocalDateTime(tp.getEnd());
            return checkBusyPeriodIncludesDates(start, end, busyPeriodStart, busyPeriodEnd)
                    || checkDatesIncludeBusyPeriod(start, end, busyPeriodStart, busyPeriodEnd);
        });
    }

    private boolean checkBusyPeriodIncludesDates(LocalDateTime start, LocalDateTime end,
                                                 LocalDateTime busyPeriodStart, LocalDateTime busyPeriodEnd) {
        return isBetweenDateTimes(start, busyPeriodStart, true, busyPeriodEnd, false)
                || isBetweenDateTimes(end, busyPeriodStart, false, busyPeriodEnd, true);
    }

    private boolean checkDatesIncludeBusyPeriod(LocalDateTime start, LocalDateTime end,
                                                LocalDateTime busyPeriodStart, LocalDateTime busyPeriodEnd) {
        return isBetweenDateTimes(busyPeriodStart, start, true, end, false)
                || isBetweenDateTimes(busyPeriodEnd, start, false, end, true);
    }
}
