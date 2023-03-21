package com.piyushtyagi.elevatorservice.repository;

import com.piyushtyagi.elevatorservice.entity.Elevator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ElevatorRepository extends JpaRepository<Elevator, Long> {

}
