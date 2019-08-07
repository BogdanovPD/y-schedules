package org.why.studio.schedules.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationRequestOutputUser {

    protected String id;
    protected String specialistFirstName;
    protected String specialistLastName;
    protected int serviceId;
    protected LocalDateTime startDateTime;
    protected Boolean approved;

}
