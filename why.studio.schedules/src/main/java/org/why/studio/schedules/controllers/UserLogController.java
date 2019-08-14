package org.why.studio.schedules.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.why.studio.schedules.dto.UserLog;
import org.why.studio.schedules.services.UserLogService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserLogController {

    private final UserLogService userLogService;

    @GetMapping(value = "log/user/{id}")
    public ResponseEntity<List<UserLog>> getUserLog(@PathVariable("id") String userId) {
        return ResponseEntity.ok(userLogService.getUserLog(userId));
    }

}
