package com.assignment2.meetingCalendarAssistant.service;

import com.assignment2.meetingCalendarAssistant.entity.Employee;
import com.assignment2.meetingCalendarAssistant.entity.Meeting;
import com.assignment2.meetingCalendarAssistant.entity.MeetingParticipant;
import com.assignment2.meetingCalendarAssistant.repository.EmployeeRepository;
import com.assignment2.meetingCalendarAssistant.repository.MeetingRepository;
import com.assignment2.meetingCalendarAssistant.repository.MeetingParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingParticipantRepository participantRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Method to book a meeting
    public Meeting bookMeeting(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    // Method to find conflicts
    public List<Employee> findConflictingParticipants(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        if (meeting == null) {
            throw new IllegalArgumentException("Meeting not found.");
        }

        LocalDateTime meetingStart = meeting.getStartTime();
        LocalDateTime meetingEnd = meeting.getEndTime();

        List<MeetingParticipant> participants = participantRepository.findAll();
        return participants.stream()
                .filter(participant -> participant.getMeeting().getId() != meetingId)
                .filter(participant -> isConflict(participant.getMeeting().getStartTime(),
                        participant.getMeeting().getEndTime(),
                        meetingStart, meetingEnd))
                .map(MeetingParticipant::getEmployee)
                .distinct()
                .collect(Collectors.toList());
    }

    private boolean isConflict(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    // Method to find free slots for a given duration
    public List<LocalDateTime[]> findFreeSlots(Long employee1Id, Long employee2Id, int durationInMinutes) {
        Employee employee1 = employeeRepository.findById(employee1Id)
                .orElseThrow(() -> new IllegalArgumentException("Employee 1 not found"));
        Employee employee2 = employeeRepository.findById(employee2Id)
                .orElseThrow(() -> new IllegalArgumentException("Employee 2 not found"));

        List<LocalDateTime[]> freeSlots = new ArrayList<>();

        // Combine and sort meetings by start time
        List<Meeting> combinedMeetings = new ArrayList<>();
        combinedMeetings.addAll(employee1.getMeetings().stream().map(MeetingParticipant::getMeeting).collect(Collectors.toList()));
        combinedMeetings.addAll(employee2.getMeetings().stream().map(MeetingParticipant::getMeeting).collect(Collectors.toList()));
        combinedMeetings.sort(Comparator.comparing(Meeting::getStartTime));

        // Find gaps
        for (int i = 0; i < combinedMeetings.size() - 1; i++) {
            LocalDateTime endCurrent = combinedMeetings.get(i).getEndTime();
            LocalDateTime startNext = combinedMeetings.get(i + 1).getStartTime();

            if (Duration.between(endCurrent, startNext).toMinutes() >= durationInMinutes) {
                freeSlots.add(new LocalDateTime[]{endCurrent, startNext});
            }
        }

        return freeSlots;
    }

}
