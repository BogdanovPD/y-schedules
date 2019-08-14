package org.why.studio.schedules.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.why.studio.schedules.services.FreeTimeService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final FreeTimeService freeTimeService;

    @GetMapping(value = "free-time")
    public ResponseEntity<List<LocalDateTime>> getFreeTimeForSpecialist(@RequestParam("specId") String specId,
                                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                        @RequestParam("date") LocalDate date,
                                                                        @RequestParam("duration") int duration) {
        return ResponseEntity.ok(freeTimeService.getSpecFreeTime(specId, date, duration));
    }
}
