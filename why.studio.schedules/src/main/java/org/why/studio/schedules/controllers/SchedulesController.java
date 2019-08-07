package org.why.studio.schedules.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.why.studio.schedules.dto.ConsultationRequestInput;
import org.why.studio.schedules.dto.Service;
import org.why.studio.schedules.services.ServiceService;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class SchedulesController {

    private final ServiceService serviceService;

    @GetMapping(value = "services")
    public ResponseEntity<List<Service>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    @PostMapping(value = "services")
    public ResponseEntity<Service> saveService(@RequestBody Service service) {
        return ResponseEntity.ok(serviceService.saveService(service));
    }

    @DeleteMapping(value = "services/{id}")
    public ResponseEntity<?> deleteService(@PathVariable("id") String id) {
        serviceService.deleteService(Integer.parseInt(id));
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "specialist/{id}/services")
    public ResponseEntity<List<Service>> getUserServices(@PathVariable("id") String userId,
                                               @RequestBody Set<Service> services) {
        serviceService.addServicesToUser(userId, services);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "specialist/{id}/services")
    public ResponseEntity<?> addServicesToUser(@PathVariable("id") String userId,
                                               @RequestBody Set<Service> services) {
        serviceService.addServicesToUser(userId, services);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "consultation-requests")
    public ResponseEntity<?> getConsultationRequests(@RequestBody ConsultationRequestInput consultationRequest) {
        return null;
    }

}
