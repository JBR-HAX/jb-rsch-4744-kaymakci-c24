package org.jetbrains.assignment.service;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.assignment.data.entity.MovementEntity;
import org.jetbrains.assignment.data.entity.PositionEntity;
import org.jetbrains.assignment.data.repository.PositionRepository;
import org.jetbrains.assignment.domain.DirectionEnum;
import org.jetbrains.assignment.domain.Movement;
import org.jetbrains.assignment.domain.Position;
import org.jetbrains.assignment.data.repository.MovementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovementService {

    private MovementRepository movementRepository;
    private PositionRepository positionRepository;

    public MovementService(MovementRepository movementRepository, PositionRepository positionRepository) {
        this.movementRepository = movementRepository;
        this.positionRepository = positionRepository;
    }

    public List<Movement> makeMovements(List<Position> positions) {

        Position initialPosition = positions.get(0);

        List<Movement> movements = new ArrayList<>();

        for (int i = 1; i < positions.size(); i++) {
            Position nextPosition = positions.get(i);

            if (nextPosition.x() > initialPosition.x()) {
                movements.add(new Movement(DirectionEnum.EAST, nextPosition.x() - initialPosition.x()));
            } else if (nextPosition.x() < initialPosition.x()) {
                movements.add(new Movement(DirectionEnum.WEST, initialPosition.x() - nextPosition.x()));
            } else if (nextPosition.y() > initialPosition.y()) {
                movements.add(new Movement(DirectionEnum.NORTH, nextPosition.y() - initialPosition.y()));
            } else if (nextPosition.y() < initialPosition.y()) {
                movements.add(new Movement(DirectionEnum.SOUTH, initialPosition.y() - nextPosition.y()));
            }

            initialPosition = nextPosition;
        }

        persistAllMovements(movements);
        persistInputPositions(positions);

        return movements;
    }

    @Transactional
    public void persistInputPositions(List<Position> positions) {
        for (Position position : positions) {
            PositionEntity positionEntity = new PositionEntity();
            positionEntity.setX(position.x());
            positionEntity.setY(position.y());
            positionRepository.save(positionEntity);
        }

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

}
