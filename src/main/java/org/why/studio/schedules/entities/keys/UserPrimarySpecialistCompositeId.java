package org.why.studio.schedules.entities.keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrimarySpecialistCompositeId implements Serializable {

    protected UUID userId;
    protected boolean approved;

}
