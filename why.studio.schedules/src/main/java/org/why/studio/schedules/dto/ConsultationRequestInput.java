package org.why.studio.schedules.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationRequestInput {

    protected String userId;
    protected String specialistId;
    protected int serviceId;
    protected LocalDateTime startDateTime;

}
