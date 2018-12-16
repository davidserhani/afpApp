package cdi.afpa.api.service.mapper;

import cdi.afpa.api.domain.*;
import cdi.afpa.api.service.dto.TraineeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Trainee and its DTO TraineeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraineeMapper extends EntityMapper<TraineeDTO, Trainee> {



    default Trainee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Trainee trainee = new Trainee();
        trainee.setId(id);
        return trainee;
    }
}
