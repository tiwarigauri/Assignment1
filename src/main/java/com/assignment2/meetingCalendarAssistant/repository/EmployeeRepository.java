package com.assignment2.meetingCalendarAssistant.repository;

import com.assignment2.meetingCalendarAssistant.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
