package org.why.studio.schedules.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.why.studio.schedules.dto.UserInfo;
import org.why.studio.schedules.dto.UserInfoWithId;
import org.why.studio.schedules.dto.UserPrimarySpecialist;
import org.why.studio.schedules.services.UserPrimarySpecialistService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserSpecialistController {

    private final UserPrimarySpecialistService userPrimarySpecialistService;

    @PostMapping("user/specialist/request")
    public ResponseEntity<?> requestPrimarySpecialist(@RequestBody @Valid UserPrimarySpecialist userPrimarySpecialist) {
        userPrimarySpecialistService.requestSetPrimarySpecialist(userPrimarySpecialist);
        return ResponseEntity.ok().build();
    }

    @GetMapping("user/{id}/specialist")
    public ResponseEntity<UserInfo> getUserSpecialist(@PathVariable("id") String userId) {
        return ResponseEntity.ok(userPrimarySpecialistService.getUserPrimarySpecialist(userId));
    }

    @GetMapping("specialist/{id}/client-requests")
    public ResponseEntity<List<UserInfoWithId>> getPrimarySpecialistRequests(@PathVariable("id") String specId) {
        return ResponseEntity.ok(userPrimarySpecialistService.getUserRequestsForSpecialist(specId));
    }

    @GetMapping("specialist/{id}/clients")
    public ResponseEntity<List<UserInfoWithId>> getClients(@PathVariable("id") String specId) {
        return ResponseEntity.ok(userPrimarySpecialistService.getSpecialistClients(specId));
    }

    @PutMapping("specialist/{id}/accept-request/{user_id}")
    public ResponseEntity<?> acceptRequest(@PathVariable("id") String specId,
                                           @PathVariable("user_id") String userId) {
        userPrimarySpecialistService.acceptRequest(userId, specId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("specialist/{id}/reject-request/{user_id}")
    public ResponseEntity<?> rejectRequest(@PathVariable("id") String specId,
                                           @PathVariable("user_id") String userId) {
        userPrimarySpecialistService.rejectRequest(userId, specId);
        return ResponseEntity.ok().build();
    }

}
