package org.why.studio.schedules.repositories;

import org.springframework.data.repository.CrudRepository;
import org.why.studio.schedules.entities.ConsultationRequestEntity;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ConsultationRequestRepository extends CrudRepository<ConsultationRequestEntity, UUID> {

    Set<ConsultationRequestEntity> findAllBySpecialistIdAndStartDateTimeIsAfter(UUID specId, LocalDateTime dateTime);
    Set<ConsultationRequestEntity> findAllByUserIdAndStartDateTimeIsAfter(UUID userId, LocalDateTime dateTime);
    Set<ConsultationRequestEntity> findAllByStartDateTimeIsBefore(LocalDateTime dateTime);
    Optional<ConsultationRequestEntity> findByIdAndApprovedIsTrue(UUID userId);
    Optional<ConsultationRequestEntity> findByIdAndApprovedIsFalse(UUID userId);
    Optional<ConsultationRequestEntity> findByUserIdAndSpecialistIdAndStartDateTime(UUID userId, UUID specId,
                                                                                    LocalDateTime startDateTime);

}
