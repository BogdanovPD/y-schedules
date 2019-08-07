package org.why.studio.schedules.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_primary_specialist")
public class UserPrimarySpecialistEntity {

    @Id
    protected UUID userId;
    @Column(nullable = false)
    protected UUID specialistId;
    protected boolean approved;

}
