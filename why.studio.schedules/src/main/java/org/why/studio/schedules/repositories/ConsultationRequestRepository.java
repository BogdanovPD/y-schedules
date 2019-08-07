package org.why.studio.schedules.repositories;

import org.springframework.data.repository.CrudRepository;
import org.why.studio.schedules.entities.ConsultationRequestEntity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public interface ConsultationRequestRepository extends CrudRepository<ConsultationRequestEntity, UUID> {

    Set<ConsultationRequestEntity> findAllBySpecialistIdAndStartDateTimeIsBefore(UUID specId, LocalDateTime dateTime);
    Set<ConsultationRequestEntity> findAllByUserIdAndStartDateTimeIsBefore(UUID userId, LocalDateTime dateTime);

}
