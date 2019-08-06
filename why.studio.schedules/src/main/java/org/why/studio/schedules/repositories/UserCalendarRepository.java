package org.why.studio.schedules.repositories;

import org.springframework.data.repository.CrudRepository;
import org.why.studio.schedules.entities.UserCalendarEntity;

import java.util.UUID;

public interface UserCalendarRepository extends CrudRepository<UserCalendarEntity, UUID> {}
