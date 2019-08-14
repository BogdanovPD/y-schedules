package org.why.studio.schedules.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationRequestInput {

    @NotBlank
    protected String userId;
    @NotBlank
    protected String specialistId;
    @Min(1)
    protected int serviceId;
    @NotNull
    protected LocalDateTime startDateTime;

}
