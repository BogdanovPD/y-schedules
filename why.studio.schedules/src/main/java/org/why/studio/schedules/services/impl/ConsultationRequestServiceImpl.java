package org.why.studio.schedules.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.why.studio.schedules.dto.ConsultationRequestInput;
import org.why.studio.schedules.dto.ConsultationRequestOutputSpec;
import org.why.studio.schedules.dto.ConsultationRequestOutputUser;
import org.why.studio.schedules.entities.ConsultationRequestEntity;
import org.why.studio.schedules.repositories.ConsultationRequestRepository;
import org.why.studio.schedules.services.AuthService;
import org.why.studio.schedules.services.ConsultationRequestService;

import java.time.LocalDateTime;
import java.util.List;

import static org.why.studio.schedules.util.Utils.convertCollectionToList;
import static org.why.studio.schedules.util.Utils.getUuid;

@Service
@Transactional
@RequiredArgsConstructor
public class ConsultationRequestServiceImpl implements ConsultationRequestService {

    private final ConsultationRequestRepository crRepository;
    private final AuthService authService;
    private final ConversionService schedulesConverterService;

    @Override
    public void save(ConsultationRequestInput consultationRequest) {
        validateRequestUserData(consultationRequest);
        ConsultationRequestEntity consultationRequestEntity =
                schedulesConverterService.convert(consultationRequest, ConsultationRequestEntity.class);
        crRepository.save(consultationRequestEntity);
    }

    @Override
    public List<ConsultationRequestOutputSpec> getSpecialistConsultationRequests(String specId) {
        return convertCollectionToList(
                crRepository.findAllBySpecialistIdAndStartDateTimeIsBefore(getUuid(specId),LocalDateTime.now()),
                schedulesConverterService,
                ConsultationRequestEntity.class,
                ConsultationRequestOutputSpec.class);
    }

    @Override
    public List<ConsultationRequestOutputUser> getUserConsultationRequests(String userId) {
        return convertCollectionToList(
                crRepository.findAllByUserIdAndStartDateTimeIsBefore(getUuid(userId),LocalDateTime.now()),
                schedulesConverterService,
                ConsultationRequestEntity.class,
                ConsultationRequestOutputUser.class);
    }

    @Override
    public void removeRequest(String requestId) {
        crRepository.deleteById(getUuid(requestId));
    }

    @Override
    public void acceptRequest(String requestId) {
        getRequestAndSetApproved(requestId, true);
    }

    @Override
    public void declineRequest(String requestId) {
        getRequestAndSetApproved(requestId, false);
    }

    private void validateRequestUserData(ConsultationRequestInput consultationRequest) {
        getUuid(consultationRequest.getUserId());
        getUuid(consultationRequest.getSpecialistId());
        authService.getUserInfo(consultationRequest.getUserId());
        authService.getUserInfo(consultationRequest.getSpecialistId());
    }

    private void getRequestAndSetApproved(String requestId, boolean approved) {
        ConsultationRequestEntity requestEntity = crRepository.findById(getUuid(requestId)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Запрос не найден по id=" + requestId)
        );
        requestEntity.setApproved(approved);
    }
}
