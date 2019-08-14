package org.why.studio.schedules.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.why.studio.schedules.dto.Service;
import org.why.studio.schedules.entities.ServiceEntity;

@Component
public class ServiceEntityToServiceConverter implements Converter<ServiceEntity, Service> {
    @Override
    public Service convert(ServiceEntity serviceEntity) {
        return Service.builder()
                .id(serviceEntity.getId())
                .name(serviceEntity.getName())
                .duration(serviceEntity.getDuration())
                .price(serviceEntity.getPrice())
                .build();
    }
}
