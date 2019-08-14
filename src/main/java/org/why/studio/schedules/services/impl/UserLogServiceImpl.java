package org.why.studio.schedules.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.why.studio.schedules.dto.UserLog;
import org.why.studio.schedules.entities.UserLogEntity;
import org.why.studio.schedules.repositories.UserLogRepository;
import org.why.studio.schedules.services.UserLogService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import static org.why.studio.schedules.util.Utils.convertCollectionToList;
import static org.why.studio.schedules.util.Utils.getUuid;

@Service
@Transactional
@RequiredArgsConstructor
public class UserLogServiceImpl implements UserLogService {

    private final UserLogRepository userLogRepository;
    private final ConversionService schedulesConverterService;
    private final DateTimeFormatter dateTimeFormatter;

    @Override
    public List<UserLog> getUserLog(String userId) {
        List<UserLog> userLogs = convertCollectionToList(
                userLogRepository.findAllByUserId(getUuid(userId)),
                schedulesConverterService,
                UserLogEntity.class,
                UserLog.class
        );
        userLogs.sort(Comparator.comparing(UserLog::getDateTime));
        return userLogs;
    }

    @Override
    public void saveUserLog(String userId, String message) {
        userLogRepository.save(
                UserLogEntity.builder()
                        .dateTime(LocalDateTime.now())
                        .userId(getUuid(userId))
                        .message(createMessage(message))
                        .build());
    }

    @Scheduled(cron = "0 30 0 * * ?")
    @Override
    public void deleteOldLog() {
        userLogRepository.deleteAll(userLogRepository.findAllByDateTimeIsBefore(LocalDateTime.now().minusMonths(3)));
    }

    private String createMessage(String message) {
        return String.format("%s:%s",
                LocalDateTime.now().format(dateTimeFormatter), message);
    }
}
