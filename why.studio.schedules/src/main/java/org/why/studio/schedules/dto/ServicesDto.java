package org.why.studio.schedules.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicesDto {

    protected Set<@Valid Service> services;

}
