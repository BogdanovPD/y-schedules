package org.why.studio.schedules.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.why.studio.schedules.dto.Service;
import org.why.studio.schedules.entities.ServiceEntity;
import org.why.studio.schedules.repositories.ServiceRepository;

@Component
@RequiredArgsConstructor
public class ServiceToServiceEntityConverter implements Converter<Service, ServiceEntity> {

    private final ServiceRepository serviceRepository;

    @Override
    public ServiceEntity convert(Service service) {
        return serviceRepository.findById(service.getId())
                .map(se -> updateData(se,  service))
                .orElse(ServiceEntity.builder()
                        .name(service.getName())
                        .duration(service.getDuration())
                        .price(service.getPrice())
                        .build());
    }

    private ServiceEntity updateData(ServiceEntity serviceEntity, Service service) {
        serviceEntity.setDuration(service.getDuration());
        serviceEntity.setName(service.getName());
        serviceEntity.setPrice(service.getPrice());
        return serviceEntity;
    }
}
