package org.jetbrains.assignment.data.repository;

import org.jetbrains.assignment.data.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends JpaRepository<MovementEntity, Long> { }
