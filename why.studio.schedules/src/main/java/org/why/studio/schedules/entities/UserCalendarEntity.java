package org.why.studio.schedules.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Table(name = "user_calendar")
@Builder
public class UserCalendarEntity {

    @Id
    private UUID userId;
    @Column(nullable = false)
    private String calendarId;

}
