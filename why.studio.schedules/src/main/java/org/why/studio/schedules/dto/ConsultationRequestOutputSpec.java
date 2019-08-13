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
public class ConsultationRequestOutputSpec {

    protected String id;
    protected UserInfo userInfo;
    protected int serviceId;
    protected LocalDateTime startDateTime;
    protected Boolean approved;

}
