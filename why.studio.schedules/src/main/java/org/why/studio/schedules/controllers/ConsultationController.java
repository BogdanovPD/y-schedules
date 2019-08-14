package org.why.studio.schedules.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.why.studio.schedules.dto.ConsultationRequestInput;
import org.why.studio.schedules.dto.ConsultationRequestOutputSpec;
import org.why.studio.schedules.dto.ConsultationRequestOutputUser;
import org.why.studio.schedules.services.ConsultationRequestService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationRequestService consultationRequestService;

    @PostMapping(value = "consultation-requests")
    public ResponseEntity<?> saveConsultationRequest(@RequestBody @Valid ConsultationRequestInput consultationRequest) {
        consultationRequestService.save(consultationRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "consultation-requests/user/{id}")
    public ResponseEntity<List<ConsultationRequestOutputUser>> getUserConsultationRequests(
            @PathVariable("id") String userId) {
        return ResponseEntity.ok(consultationRequestService.getUserConsultationRequests(userId));
    }

    @GetMapping(value = "consultation-requests/spec/{id}")
    public ResponseEntity<List<ConsultationRequestOutputSpec>> getSpecialistConsultationRequests(
            @PathVariable("id") String specId) {
        return ResponseEntity.ok(consultationRequestService.getSpecialistConsultationRequests(specId));
    }

    @PutMapping(value = "consultation-requests/{id}/approve")
    public ResponseEntity<?> approveConsultationRequest(@PathVariable("id") String requestId) {
        consultationRequestService.approveRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "consultation-requests/{id}/reject")
    public ResponseEntity<?> rejectConsultationRequest(@PathVariable("id") String requestId) {
        consultationRequestService.rejectRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "consultation-requests/{id}/decline")
    public ResponseEntity<?> declineConsultationRequest(@PathVariable("id") String requestId) {
        consultationRequestService.declineRequest(requestId);
        return ResponseEntity.ok().build();
    }

}
