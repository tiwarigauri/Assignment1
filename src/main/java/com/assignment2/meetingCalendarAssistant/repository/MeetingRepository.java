package com.assignment2.meetingCalendarAssistant.repository;

import com.assignment2.meetingCalendarAssistant.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {}
