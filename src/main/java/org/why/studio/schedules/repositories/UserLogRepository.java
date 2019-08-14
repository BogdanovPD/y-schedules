package org.why.studio.schedules.repositories;

import org.springframework.data.repository.CrudRepository;
import org.why.studio.schedules.entities.UserLogEntity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public interface UserLogRepository extends CrudRepository<UserLogEntity, UUID> {

    Set<UserLogEntity> findAllByUserId(UUID userId);
    Set<UserLogEntity> findAllByDateTimeIsBefore(LocalDateTime localDateTime);

}
