package org.why.studio.schedules.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrimarySpecialist {

    @NotBlank
    protected String userId;
    @NotBlank
    protected String specialistId;

}
