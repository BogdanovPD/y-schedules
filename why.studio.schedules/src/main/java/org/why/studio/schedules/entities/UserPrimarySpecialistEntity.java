package org.why.studio.schedules.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.why.studio.schedules.entities.keys.UserPrimarySpecialistCompositeId;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
@Table(name = "user_primary_specialist")
@IdClass(UserPrimarySpecialistCompositeId.class)
public class UserPrimarySpecialistEntity {

    @Id
    protected UUID userId;
    @Column(nullable = false)
    protected UUID specialistId;
    @ColumnDefault("false")
    @Id
    protected boolean approved;

}
