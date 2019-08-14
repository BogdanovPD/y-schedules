package org.why.studio.schedules.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_log")
public class UserLogEntity {

    @Id
    @GeneratedValue
    protected UUID id;

    //specialist or user
    @Column(nullable = false)
    protected UUID userId;
    @Column(nullable = false)
    protected LocalDateTime dateTime;
    @Column(nullable = false)
    protected String message;

}
