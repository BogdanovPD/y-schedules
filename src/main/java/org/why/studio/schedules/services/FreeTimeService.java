package org.why.studio.schedules.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FreeTimeService {

    List<LocalDateTime> getSpecFreeTime(String userId, LocalDate date, int serviceDuration);
    List<LocalDateTime> getMonthFullBusyDays(String userId, LocalDate start);
}
