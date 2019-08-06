package org.why.studio.schedules.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_calendar")
public class UserCalendarEntity {

    @Id
    private UUID userId;
    @Column(nullable = false)
    private String calendarId;

}
