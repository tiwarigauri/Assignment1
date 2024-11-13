package com.assignment2.meetingCalendarAssistant.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "employee")
    private List<MeetingParticipant> meetings;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMeetings(List<MeetingParticipant> meetings) {
        this.meetings = meetings;
    }

    public List<MeetingParticipant> getMeetings() {
        return meetings;
    }
}
