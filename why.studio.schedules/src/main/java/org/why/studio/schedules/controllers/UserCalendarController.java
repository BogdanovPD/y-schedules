package org.why.studio.schedules.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.why.studio.schedules.dto.UserCalendar;
import org.why.studio.schedules.services.UserCalendarService;

@RestController
@RequiredArgsConstructor
public class UserCalendarController {

    private final UserCalendarService userCalendarService;

    @PostMapping("calendars")
    public ResponseEntity<?>saveUserCalendar(@RequestBody UserCalendar userCalendar) {
        userCalendarService.saveUserCalendar(userCalendar);
        return ResponseEntity.ok().build();
    }

}
