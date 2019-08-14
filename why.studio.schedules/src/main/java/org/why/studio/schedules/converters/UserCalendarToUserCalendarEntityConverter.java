package org.why.studio.schedules.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.why.studio.schedules.dto.UserCalendar;
import org.why.studio.schedules.entities.UserCalendarEntity;
import org.why.studio.schedules.repositories.UserCalendarRepository;

import static org.why.studio.schedules.util.Utils.getUuid;

@Component
@RequiredArgsConstructor
public class UserCalendarToUserCalendarEntityConverter implements Converter<UserCalendar, UserCalendarEntity> {

    private final UserCalendarRepository userCalendarRepository;

    @Override
    public UserCalendarEntity convert(UserCalendar userCalendar) {
        return userCalendarRepository.findById(getUuid(userCalendar.getUserId()))
                .map(uce -> {
                    uce.setCalendarId(userCalendar.getCalendarId());
                    return uce;
                })
                .orElseGet(() ->
                        UserCalendarEntity.builder()
                                .userId(getUuid(userCalendar.getUserId()))
                                .calendarId(userCalendar.getCalendarId())
                                .build()
                );
    }
}
