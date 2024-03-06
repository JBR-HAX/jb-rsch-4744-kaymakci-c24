package org.jetbrains.assignment.data.repository;

import org.jetbrains.assignment.data.entity.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface PositionRepository extends JpaRepository<PositionEntity, Long> { }
