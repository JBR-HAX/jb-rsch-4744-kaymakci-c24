package org.jetbrains.assignment.service;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.assignment.data.entity.MovementEntity;
import org.jetbrains.assignment.data.entity.PositionEntity;
import org.jetbrains.assignment.data.repository.MovementRepository;
import org.jetbrains.assignment.domain.Movement;
import org.jetbrains.assignment.domain.Position;
import org.jetbrains.assignment.data.repository.PositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationService {

    private PositionRepository positionRepository;
    private MovementRepository movementRepository;

    public LocationService(PositionRepository positionRepository, MovementRepository movementRepository) {
        this.positionRepository = positionRepository;
        this.movementRepository = movementRepository;
    }

    public List<Position> changeLocation(List<Movement> movements) {
        Position position = new Position(0, 0);
        List<Position> allPositions = new ArrayList<>();
        allPositions.add(position);

        for (Movement move : movements) {
            switch (move.direction()) {
            case EAST -> position = new Position(position.x() + move.steps(), position.y());
            case WEST -> position = new Position(position.x() - move.steps(), position.y());
            case NORTH -> position = new Position(position.x(), position.y() + move.steps());
            case SOUTH -> position = new Position(position.x(), position.y() - move.steps());
            }
            allPositions.add(position);
        }

        persistAllPoisitions(allPositions);
        persistAllMovements(movements);

        return allPositions;
    }

    @Transactional
    public void persistAllMovements(List<Movement> movements) {
        for (Movement movement : movements) {
            MovementEntity movementEntity = new MovementEntity();
            movementEntity.setDirection(movement.direction().name());
            movementEntity.setSteps(movement.steps());
            movementRepository.save(movementEntity);
        }
    }

    @Transactional
    public void persistAllPoisitions(List<Position> allPositions) {
        for (Position position : allPositions) {
            PositionEntity positionEntity = new PositionEntity();
            positionEntity.setX(position.x());
            positionEntity.setY(position.y());
            positionRepository.save(positionEntity);
        }
    }
}
