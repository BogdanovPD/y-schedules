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
@Entity
@Table(name = "consultation_requests")
@Builder
public class ConsultationRequestEntity {

    @Id
    @GeneratedValue
    protected UUID id;

    protected UUID userId;
    protected UUID specialistId;
    @ManyToOne
    @JoinColumn(name = "service_id")
    protected ServiceEntity service;
    protected LocalDateTime startDateTime;
    protected Boolean approved;

}
