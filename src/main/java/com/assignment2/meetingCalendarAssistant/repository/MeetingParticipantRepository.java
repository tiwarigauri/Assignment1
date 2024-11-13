package com.assignment2.meetingCalendarAssistant.repository;

import com.assignment2.meetingCalendarAssistant.entity.MeetingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant, Long> {}
