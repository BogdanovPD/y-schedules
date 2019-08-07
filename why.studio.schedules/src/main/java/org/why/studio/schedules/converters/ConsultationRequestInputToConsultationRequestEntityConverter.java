package org.why.studio.schedules.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.why.studio.schedules.dto.ConsultationRequestInput;
import org.why.studio.schedules.entities.ConsultationRequestEntity;
import org.why.studio.schedules.entities.ServiceEntity;
import org.why.studio.schedules.repositories.ServiceRepository;

import static org.why.studio.schedules.util.Utils.getUuid;

@Component
@RequiredArgsConstructor
public class ConsultationRequestInputToConsultationRequestEntityConverter
        implements Converter<ConsultationRequestInput, ConsultationRequestEntity> {

    private final ServiceRepository serviceRepository;

    @Override
    public ConsultationRequestEntity convert(ConsultationRequestInput consultationRequestInput) {
        ServiceEntity serviceEntity = serviceRepository.findById(consultationRequestInput.getServiceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Услуга не найдена по id=" + consultationRequestInput.getServiceId()));
        return ConsultationRequestEntity.builder()
                .service(serviceEntity)
                .specialistId(getUuid(consultationRequestInput.getSpecialistId()))
                .userId(getUuid(consultationRequestInput.getUserId()))
                .build();
    }
}
