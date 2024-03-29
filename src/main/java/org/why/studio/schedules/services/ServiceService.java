package org.why.studio.schedules.services;

import org.why.studio.schedules.dto.Service;

import java.util.List;
import java.util.Set;

public interface ServiceService {

    List<Service> getAllServices();
    Service saveService(Service service);
    void deleteService(int id);
    void addServicesToUser(String userId, Set<Service> services);
    List<Service> getServicesByUserId(String userId);

}
