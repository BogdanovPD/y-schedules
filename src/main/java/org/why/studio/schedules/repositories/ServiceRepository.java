package org.why.studio.schedules.repositories;

import org.springframework.data.repository.CrudRepository;
import org.why.studio.schedules.entities.ServiceEntity;

import java.util.Set;
import java.util.UUID;

public interface ServiceRepository extends CrudRepository<ServiceEntity, Integer> {

    Set<ServiceEntity> findAll();
    Set<ServiceEntity> findAllBySpecialistsContains(UUID userId);

}
