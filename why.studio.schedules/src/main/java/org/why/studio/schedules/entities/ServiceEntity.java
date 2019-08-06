package org.why.studio.schedules.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "services")
public class ServiceEntity {

    @Id
    @GeneratedValue(generator = "services_id_seq")
    protected int id;
    protected String name;
    @Column(name = "duration_minutes")
    protected int duration;
    protected int price;

    @ElementCollection
    @JoinTable(name = "services_specialists",
            joinColumns = @JoinColumn(name = "service_id"))
    @Column(name = "specialist_uuid", nullable = false)
    protected List<UUID> specialists = new LinkedList<>();

}
