package org.why.studio.schedules.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.why.studio.schedules.dto.ConsultationRequestOutputSpec;
import org.why.studio.schedules.entities.ConsultationRequestEntity;
import org.why.studio.schedules.services.AuthService;

@Component
@RequiredArgsConstructor
public class ConsultationRequestEntityConverterToConsultationRequestOutputSpecConverter
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
