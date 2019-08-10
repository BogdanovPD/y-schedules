package org.why.studio.schedules.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.why.studio.schedules.dto.UserLog;
import org.why.studio.schedules.entities.UserLogEntity;

@Component
public class UserLogEntityToUserLogConverter implements Converter<UserLogEntity, UserLog> {

    @Override
    public UserLog convert(UserLogEntity userLogEntity) {
        return UserLog.builder()
                .dateTime(userLogEntity.getDateTime())
                .message(userLogEntity.getMessage())
                .build();
    }
}
