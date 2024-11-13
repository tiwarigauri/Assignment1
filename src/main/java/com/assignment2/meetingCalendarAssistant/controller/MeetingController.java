package com.assignment2.meetingCalendarAssistant.controller;

import com.assignment2.meetingCalendarAssistant.entity.Employee;
import com.assignment2.meetingCalendarAssistant.entity.Meeting;
import com.assignment2.meetingCalendarAssistant.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @PostMapping("/book")
    public Meeting bookMeeting(@RequestBody Meeting meeting) {
        return meetingService.bookMeeting(meeting);
    }

    @GetMapping("/conflicts/{meetingId}")
    public List<Employee> getConflictingParticipants(@PathVariable Long meetingId) {
        return meetingService.findConflictingParticipants(meetingId);
    }

    @GetMapping("/freeslots")
    public List<LocalDateTime[]> getFreeSlots(@RequestParam Long employee1Id, @RequestParam Long employee2Id, @RequestParam int duration) {
        return meetingService.findFreeSlots(employee1Id, employee2Id, duration);
    }
}
