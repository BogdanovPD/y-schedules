package org.why.studio.schedules.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.why.studio.schedules.dto.Service;
import org.why.studio.schedules.dto.ServicesDto;
import org.why.studio.schedules.services.ServiceService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ServicesController {

    private final ServiceService serviceService;

    @PostMapping(value = "services")
    public ResponseEntity<Service> saveService(@RequestBody @Valid Service service) {
        return ResponseEntity.ok(serviceService.saveService(service));
    }

    @GetMapping(value = "services")
    public ResponseEntity<List<Service>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    @DeleteMapping(value = "services/{id}")
    public ResponseEntity<?> deleteService(@PathVariable("id") String id) {
        serviceService.deleteService(Integer.parseInt(id));
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "specialist/{id}/services")
    public ResponseEntity<?> addServicesToUser(@PathVariable("id") String userId,
                                               @RequestBody ServicesDto servicesDto) {
        serviceService.addServicesToUser(userId, servicesDto.getServices());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "specialist/{id}/services")
    public ResponseEntity<List<Service>> getUserServices(@PathVariable("id") String userId) {
        serviceService.getServicesByUserId(userId);
        return ResponseEntity.ok().build();
    }

}
