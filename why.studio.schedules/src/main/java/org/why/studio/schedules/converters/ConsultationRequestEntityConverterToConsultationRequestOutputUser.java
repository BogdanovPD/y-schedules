package org.why.studio.schedules.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.why.studio.schedules.dto.ConsultationRequestOutputUser;
import org.why.studio.schedules.dto.UserInfo;
import org.why.studio.schedules.entities.ConsultationRequestEntity;
import org.why.studio.schedules.services.AuthService;

@Component
@RequiredArgsConstructor
public class ConsultationRequestEntityConverterToConsultationRequestOutputUser
        implements Converter<ConsultationRequestEntity, ConsultationRequestOutputUser> {

    private final AuthService authService;

    @Override
    public ConsultationRequestOutputUser convert(ConsultationRequestEntity consultationRequestEntity) {
        String userId = consultationRequestEntity.getSpecialistId().toString();
        UserInfo userInfo = authService.getUserInfo(userId);
        if (userInfo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден по id=" + userId);
        }
        return ConsultationRequestOutputUser.builder()
                .id(consultationRequestEntity.getId().toString())
                .serviceId(consultationRequestEntity.getService().getId())
                .startDateTime(consultationRequestEntity.getStartDateTime())
                .specialistFirstName(userInfo.getFirstName())
                .specialistLastName(userInfo.getLastName())
                .approved(consultationRequestEntity.getApproved())
                .build();
    }
}
