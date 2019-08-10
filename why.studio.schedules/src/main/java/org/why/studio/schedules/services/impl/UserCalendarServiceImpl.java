package org.why.studio.schedules.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.why.studio.schedules.dto.UserCalendar;
import org.why.studio.schedules.entities.UserCalendarEntity;
import org.why.studio.schedules.repositories.UserCalendarRepository;
import org.why.studio.schedules.services.UserCalendarService;

import static org.why.studio.schedules.util.Utils.getUuid;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCalendarServiceImpl implements UserCalendarService {

    private final UserCalendarRepository userCalendarRepository;
    private final ConversionService schedulesConverterService;

    @Override
    public void saveUserCalendar(UserCalendar userCalendar) {
        userCalendarRepository.save(schedulesConverterService.convert(userCalendar, UserCalendarEntity.class));
    }

    @Override
    public String getUserCalendar(String userId) {
        return userCalendarRepository.findById(getUuid(userId))
                .map(UserCalendarEntity::getCalendarId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Календарь юзера не найден")
                );
    }
}
