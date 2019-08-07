package org.why.studio.schedules.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.why.studio.schedules.dto.ConsultationRequestInput;
import org.why.studio.schedules.dto.ConsultationRequestOutputSpec;
import org.why.studio.schedules.dto.UserInfo;
import org.why.studio.schedules.entities.ConsultationRequestEntity;
import org.why.studio.schedules.entities.ServiceEntity;
import org.why.studio.schedules.repositories.ServiceRepository;
import org.why.studio.schedules.services.AuthService;

import static org.why.studio.schedules.util.Utils.getUuid;

@Component
@RequiredArgsConstructor
public class ConsultationRequestEntityConverterToConsultationRequestOutputSpec
        implements Converter<ConsultationRequestEntity, ConsultationRequestOutputSpec> {

    private final AuthService authService;

    @Override
    public ConsultationRequestOutputSpec convert(ConsultationRequestEntity consultationRequestEntity) {
        return ConsultationRequestOutputSpec.builder()
                .id(consultationRequestEntity.getId().toString())
                .serviceId(consultationRequestEntity.getService().getId())
                .startDateTime(consultationRequestEntity.getStartDateTime())
                .userInfo(authService.getUserInfo(
                        consultationRequestEntity.getUserId().toString()))
                .approved(consultationRequestEntity.getApproved())
                .build();
    }
}
