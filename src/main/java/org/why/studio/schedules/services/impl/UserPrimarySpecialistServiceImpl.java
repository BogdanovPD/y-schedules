package org.why.studio.schedules.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.why.studio.schedules.dto.UserInfo;
import org.why.studio.schedules.dto.UserInfoWithId;
import org.why.studio.schedules.dto.UserPrimarySpecialist;
import org.why.studio.schedules.entities.UserPrimarySpecialistEntity;
import org.why.studio.schedules.repositories.UserPrimarySpecialistRepository;
import org.why.studio.schedules.services.AuthService;
import org.why.studio.schedules.services.UserLogService;
import org.why.studio.schedules.services.UserPrimarySpecialistService;

import java.util.List;
import java.util.stream.Collectors;

import static org.why.studio.schedules.util.Utils.getUuid;

@Service
@RequiredArgsConstructor
public class UserPrimarySpecialistServiceImpl implements UserPrimarySpecialistService {

    private final ConversionService schedulesConverterService;
    private final UserPrimarySpecialistRepository userPrimarySpecialistRepository;
    private final AuthService authService;
    private final UserLogService userLogService;

    @Override
    public void requestSetPrimarySpecialist(UserPrimarySpecialist userPrimarySpecialist) {
        userPrimarySpecialistRepository.save(
                schedulesConverterService.convert(userPrimarySpecialist, UserPrimarySpecialistEntity.class));
        logAction(userPrimarySpecialist.getUserId(), userPrimarySpecialist.getSpecialistId(),
                "Вы хотели бы стать клиентом специалиста", "хотел бы стать вашим клиентом");
    }

    @Override
    public List<UserInfoWithId> getUserRequestsForSpecialist(String specId) {
        List<UserPrimarySpecialistEntity> primarySpecialistEntities =
                userPrimarySpecialistRepository
                        .findAllBySpecialistIdAndApprovedIsFalse(getUuid(specId));
        return primarySpecialistEntities.stream().map(pse -> {
            String userId = pse.getUserId().toString();
            return UserInfoWithId.builder()
                    .userId(userId)
                    .userInfo(authService.getUserInfo(userId))
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserInfoWithId> getSpecialistClients(String specId) {
        return userPrimarySpecialistRepository.findAllBySpecialistIdAndApprovedIsTrue(getUuid(specId))
                .stream().map(psr -> UserInfoWithId.builder()
                        .userId(psr.getUserId().toString())
                        .userInfo(authService.getUserInfo(psr.getUserId().toString()))
                        .build()).collect(Collectors.toList());
    }

    @Override
    public UserInfo getUserPrimarySpecialistInfo(String userId) {
        return userPrimarySpecialistRepository.findByUserIdAndApprovedIsTrue(getUuid(userId))
                .map(pse -> authService.getUserInfo(pse.getSpecialistId().toString()))
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Основной специалист не найден для пользователь id=" + userId));
    }

    @Override
    public String getUserPrimarySpecialistId(String userId) {
        return userPrimarySpecialistRepository.findByUserIdAndApprovedIsTrue(getUuid(userId))
                .map(userPrimarySpecialistEntity -> userPrimarySpecialistEntity.getSpecialistId().toString())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Основной специалист не найден для пользователь id=" + userId));
    }

    @Override
    public void acceptRequest(String userId, String specId) {
        userPrimarySpecialistRepository.findByUserIdAndApprovedIsTrue(getUuid(userId))
                .ifPresent(userPrimarySpecialistRepository::delete); //если у пользователя уже есть другой осн. специалист - удалим соответствие
        userPrimarySpecialistRepository.findByUserIdAndApprovedIsFalse(getUuid(userId)).ifPresent(pse -> {
            UserPrimarySpecialistEntity newPse = UserPrimarySpecialistEntity.builder()
                    .userId(pse.getUserId())
                    .specialistId(pse.getSpecialistId())
                    .approved(true)
                    .build();
            userPrimarySpecialistRepository.save(newPse);
        });
        userPrimarySpecialistRepository.findByUserIdAndApprovedIsFalse(getUuid(userId))
                .ifPresent(userPrimarySpecialistRepository::delete);
        logAction(userId, specId, "Вы стали клиентом специалиста", "стал вашим клиентом");
    }

    @Override
    public void rejectRequest(String userId, String specId) {
        UserPrimarySpecialistEntity userPrimarySpecialistEntity =
                userPrimarySpecialistRepository.findByUserIdAndApprovedIsFalse(getUuid(userId))
                        .orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Не найдено запросов от клиента id=" + userId)
                        );
        userPrimarySpecialistRepository.delete(userPrimarySpecialistEntity);
        logRejectRequest(userId, specId);
    }

    private void logRejectRequest(String userId, String specId) {
        UserInfo specUserInfo = authService.getUserInfo(specId);
        UserInfo userUserInfo = authService.getUserInfo(userId);
        saveUserLog(userId, String.format("Специалист %s %s (%s) сейчас не готов вас взять," +
                        " возможно у него нет свободного времени," +
                        " вы можете связаться со специалистом, чтобы уточнить подробности ",
                specUserInfo.getFirstName(),
                specUserInfo.getLastName(),
                specUserInfo.getEmail()));
        saveUserLog(specId, String.format("Вы отклонили запрос стать вашим клиентом от пользователя %s %s (%s)",
                userUserInfo.getFirstName(),
                userUserInfo.getLastName(),
                userUserInfo.getEmail()));
    }

    private void logAction(String userId, String specId, String userAction, String specAction) {
        UserInfo specUserInfo = authService.getUserInfo(specId);
        UserInfo userUserInfo = authService.getUserInfo(userId);
        saveUserLog(specId, String.format("%s %s (%s) %s",
                userUserInfo.getFirstName(),
                userUserInfo.getLastName(),
                userUserInfo.getEmail(),
                specAction));
        saveUserLog(userId, String.format("%s %s %s (%s)",
                userAction,
                specUserInfo.getFirstName(),
                specUserInfo.getLastName(),
                specUserInfo.getEmail()));
    }

    private void saveUserLog(String userId, String msg) {
        userLogService.saveUserLog(userId, msg);
    }
}
