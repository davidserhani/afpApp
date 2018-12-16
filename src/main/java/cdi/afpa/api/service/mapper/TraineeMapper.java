package cdi.afpa.api.service.mapper;

import cdi.afpa.api.domain.*;
import cdi.afpa.api.service.dto.TraineeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Trainee and its DTO TraineeDTO.
 */
@Mapper(componentModel = "spring", uses = {TrainingMapper.class})
public interface TraineeMapper extends EntityMapper<TraineeDTO, Trainee> {

    @Mapping(source = "training.id", target = "trainingId")
    TraineeDTO toDto(Trainee trainee);

    @Mapping(source = "trainingId", target = "training")
    Trainee toEntity(TraineeDTO traineeDTO);

    default Trainee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Trainee trainee = new Trainee();
        trainee.setId(id);
        return trainee;
    }
}
