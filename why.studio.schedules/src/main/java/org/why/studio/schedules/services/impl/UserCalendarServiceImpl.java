package org.why.studio.schedules.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.why.studio.schedules.dto.UserCalendar;
import org.why.studio.schedules.entities.UserCalendarEntity;
import org.why.studio.schedules.repositories.UserCalendarRepository;
import org.why.studio.schedules.services.UserCalendarService;

import java.util.LinkedList;
import java.util.List;

import static org.why.studio.schedules.util.Utils.getUuid;

@Service
@RequiredArgsConstructor
public class UserCalendarServiceImpl implements UserCalendarService {

    private final UserCalendarRepository userCalendarRepository;
    private final ConversionService schedulesConverterService;

    @Value("${calendar.id}")
    private String resourceCalendarId;

    @Override
    @Transactional
    public void saveUserCalendar(UserCalendar userCalendar) {
        userCalendarRepository.save(schedulesConverterService.convert(userCalendar, UserCalendarEntity.class));
    }

    @Override
    @Transactional
    public String getUserCalendar(String userId) {
        return userCalendarRepository.findById(getUuid(userId))
                .map(UserCalendarEntity::getCalendarId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Календарь пользователя не найден")
                );
    }

    @Override
    public List<String> getCalendarIds(String userId) {
        List<String> calIds = new LinkedList<>();
        if (calIds.isEmpty()) {
            String userCalendarId = getUserCalendar(userId);
            calIds.add(userCalendarId);
            calIds.addAll(getResourcesCalendarIds());
        }
        return calIds;
    }

    //TODO: fetch From BD
    @Override
    public List<String> getResourcesCalendarIds() {
        return List.of(resourceCalendarId);
    }

}
