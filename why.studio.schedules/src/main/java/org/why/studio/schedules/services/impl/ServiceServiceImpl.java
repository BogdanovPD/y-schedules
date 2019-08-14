package org.why.studio.schedules.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.transaction.annotation.Transactional;
import org.why.studio.schedules.dto.Service;
import org.why.studio.schedules.entities.ServiceEntity;
import org.why.studio.schedules.repositories.ServiceRepository;
import org.why.studio.schedules.services.ServiceService;

import java.util.*;

import static org.why.studio.schedules.util.Utils.*;

@org.springframework.stereotype.Service
@Transactional
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ConversionService schedulesConverterService;
    private final ServiceRepository serviceRepository;

    @Override
    public List<Service> getAllServices() {
        List<Service> services = convertCollectionToList(serviceRepository.findAll(), schedulesConverterService,
                ServiceEntity.class, Service.class);
        services.sort(Comparator.comparing(Service::getId));
        return services;
    }

    @Override
    public Service saveService(Service service) {
        ServiceEntity serviceEntity = schedulesConverterService.convert(service, ServiceEntity.class);
        serviceEntity = serviceRepository.save(serviceEntity);
        service = schedulesConverterService.convert(serviceEntity, Service.class);
        return service;
    }

    @Override
    public void deleteService(int id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public void addServicesToUser(String userId, Set<Service> services) {
        UUID uuid = getUuid(userId);
        services.forEach(s -> {
            ServiceEntity serviceEntity = schedulesConverterService.convert(s, ServiceEntity.class);
            serviceEntity.getSpecialists().add(uuid);
            serviceRepository.save(serviceEntity);
        });
    }

    @Override
    public List<Service> getServicesByUserId(String userId) {
        List<Service> services = convertCollectionToList(
                serviceRepository.findAllBySpecialistsContains(getUuid(userId)),
                schedulesConverterService, ServiceEntity.class, Service.class);
        services.sort(Comparator.comparing(Service::getId));
        return services;
    }

}
