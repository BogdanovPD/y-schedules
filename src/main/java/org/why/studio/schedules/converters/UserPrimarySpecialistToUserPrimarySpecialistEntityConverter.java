package org.why.studio.schedules.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.why.studio.schedules.dto.UserPrimarySpecialist;
import org.why.studio.schedules.entities.UserPrimarySpecialistEntity;

import static org.why.studio.schedules.util.Utils.getUuid;

@Component
public class UserPrimarySpecialistToUserPrimarySpecialistEntityConverter implements Converter<UserPrimarySpecialist,
        UserPrimarySpecialistEntity> {
    @Override
    public UserPrimarySpecialistEntity convert(UserPrimarySpecialist userPrimarySpecialist) {
        return UserPrimarySpecialistEntity.builder()
                .userId(getUuid(userPrimarySpecialist.getUserId()))
                .specialistId(getUuid(userPrimarySpecialist.getSpecialistId()))
                .build();
    }
}
