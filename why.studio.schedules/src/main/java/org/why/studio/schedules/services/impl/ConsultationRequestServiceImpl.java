package org.why.studio.schedules.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.why.studio.schedules.dto.ConsultationRequestInput;
import org.why.studio.schedules.dto.ConsultationRequestOutputSpec;
import org.why.studio.schedules.dto.ConsultationRequestOutputUser;
import org.why.studio.schedules.dto.UserInfo;
import org.why.studio.schedules.entities.ConsultationRequestEntity;
import org.why.studio.schedules.repositories.ConsultationRequestRepository;
import org.why.studio.schedules.services.AuthService;
import org.why.studio.schedules.services.ConsultationRequestService;
import org.why.studio.schedules.services.UserLogService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.why.studio.schedules.constants.SchedulesConstants.MIN_HOURS_BEFORE_DECLINE;
import static org.why.studio.schedules.util.Utils.convertCollectionToList;
import static org.why.studio.schedules.util.Utils.getUuid;

@Service
@Transactional
@RequiredArgsConstructor
public class ConsultationRequestServiceImpl implements ConsultationRequestService {

    private final ConversionService schedulesConverterService;
    private final ConsultationRequestRepository crRepository;
    private final AuthService authService;
    private final UserLogService userLogService;
    private final DateTimeFormatter dateTimeFormatter;

    @Override
    public void save(ConsultationRequestInput consultationRequest) {
        validateRequestUserData(consultationRequest);
        ConsultationRequestEntity consultationRequestEntity =
                schedulesConverterService.convert(consultationRequest, ConsultationRequestEntity.class);
        consultationRequestEntity = crRepository.save(consultationRequestEntity);
        logRequestAction(consultationRequestEntity, "Новый запрос на консультацию", "");
    }

    @Override
    public List<ConsultationRequestOutputSpec> getSpecialistConsultationRequests(String specId) {
        List<ConsultationRequestOutputSpec> consultationRequestOutputSpecs = convertCollectionToList(
                crRepository.findAllBySpecialistIdAndStartDateTimeIsAfter(getUuid(specId), LocalDateTime.now()),
                schedulesConverterService,
                ConsultationRequestEntity.class,
                ConsultationRequestOutputSpec.class);
        consultationRequestOutputSpecs.sort(Comparator.comparing(ConsultationRequestOutputSpec::getStartDateTime));
        return consultationRequestOutputSpecs;
    }

    @Override
    public List<ConsultationRequestOutputUser> getUserConsultationRequests(String userId) {
        List<ConsultationRequestOutputUser> consultationRequestOutputUsers = convertCollectionToList(
                crRepository.findAllByUserIdAndStartDateTimeIsAfter(getUuid(userId), LocalDateTime.now()),
                schedulesConverterService,
                ConsultationRequestEntity.class,
                ConsultationRequestOutputUser.class);
        consultationRequestOutputUsers.sort(Comparator.comparing(ConsultationRequestOutputUser::getStartDateTime));
        return consultationRequestOutputUsers;
    }

    @Override
    public void approveRequest(String requestId) {
        ConsultationRequestEntity requestEntity = getRequestAndSetApproved(requestId, true);
        logRequestAction(requestEntity, "Запрос на консультацию", "подтвержден специалистом " +
                "(Вы можете отменить консультацию воспользовавшись формой отмены. " +
                "Обращаем внимание, что, в соответствии с правилами, отмена должна осуществляться " +
                "не позднее чем за 6 часов до начала консультации, " +
                "в противном случае консультация должна быть оплачена)");
    }

    @Override
    public void rejectRequest(String requestId) {
        ConsultationRequestEntity requestEntity = getRequestAndSetApproved(requestId, false);
        logRequestAction(requestEntity, "Запрос на консультацию",
                "не подтвержден специалистом, попробуйте другое время " +
                        "или свяжитесь со специалистом для уточнения причин");
    }

    @Override
    public void declineRequest(String requestId) {
        UUID uuid = getUuid(requestId);
        ConsultationRequestEntity requestEntity = getConsultationRequestEntity(requestId);
        boolean isActive = requestEntity.getApproved() != null
                && requestEntity.getApproved();
        boolean paidDecline = isActive
                && requestEntity.getStartDateTime().minusHours(
                MIN_HOURS_BEFORE_DECLINE).isBefore(LocalDateTime.now());
        logRequestAction(requestEntity, isActive ? "Консультация" : "Запрос на консультацию",
                (isActive ? "отменена" : "отменен")
                        .concat(paidDecline
                                ? " (Консультация отменена менее чем за " + MIN_HOURS_BEFORE_DECLINE
                                + " часов до начала и должна быть оплачена)"
                                : ""));
        crRepository.deleteById(uuid);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    public void deleteOldRequests() {
        crRepository.deleteAll(crRepository.findAllByStartDateTimeIsBefore(LocalDateTime.now()));
    }

    private void validateRequestUserData(ConsultationRequestInput consultationRequest) {
        getUuid(consultationRequest.getUserId());
        getUuid(consultationRequest.getSpecialistId());
        authService.getUserInfo(consultationRequest.getUserId());
        authService.getUserInfo(consultationRequest.getSpecialistId());
    }

    private ConsultationRequestEntity getConsultationRequestEntity(String requestId) {
        return crRepository.findById(getUuid(requestId)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Запрос не найден по id=" + requestId)
        );
    }

    private ConsultationRequestEntity getRequestAndSetApproved(String requestId, boolean approved) {
        ConsultationRequestEntity requestEntity = getConsultationRequestEntity(requestId);
        requestEntity.setApproved(approved);
        return requestEntity;
    }

    private void logRequestAction(ConsultationRequestEntity r, String subject, String actionDesc) {
        UserInfo specUserInfo = authService.getUserInfo(r.getSpecialistId().toString());
        UserInfo userUserInfo = authService.getUserInfo(r.getUserId().toString());
        saveUserAction(r.getUserId(), buildMessage(r, subject, "специалиста", actionDesc, specUserInfo));
        saveUserAction(r.getSpecialistId(), buildMessage(r, subject, "пользователя", actionDesc, userUserInfo));
    }

    private void saveUserAction(UUID userId, String msg) {
        userLogService.saveUserLog(userId.toString(), msg);
    }

    private String buildMessage(ConsultationRequestEntity r,
                                String subject,
                                String userType,
                                String actionDesc,
                                UserInfo userInfo) {
        return String.format("%s (%s) на %s %s %s %s (%s) %s",
                subject,
                r.getService().getName(),
                r.getStartDateTime().format(dateTimeFormatter),
                userType,
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getEmail(),
                actionDesc
        );
    }
}
