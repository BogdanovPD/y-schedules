package org.why.studio.schedules.repositories;

import org.springframework.data.repository.CrudRepository;
import org.why.studio.schedules.entities.UserPrimarySpecialistEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserPrimarySpecialistRepository extends CrudRepository<UserPrimarySpecialistEntity, UUID> {
    List<UserPrimarySpecialistEntity> findAllBySpecialistIdAndApprovedIsFalse(UUID specId);
    List<UserPrimarySpecialistEntity> findAllBySpecialistIdAndApprovedIsTrue(UUID specId);
    Optional<UserPrimarySpecialistEntity> findByUserIdAndApprovedIsTrue(UUID userId);
    Optional<UserPrimarySpecialistEntity> findByUserIdAndApprovedIsFalse(UUID userId);
}