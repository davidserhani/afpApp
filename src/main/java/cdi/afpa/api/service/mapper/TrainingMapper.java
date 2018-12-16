package cdi.afpa.api.service.mapper;

import cdi.afpa.api.domain.*;
import cdi.afpa.api.service.dto.TrainingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Training and its DTO TrainingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TrainingMapper extends EntityMapper<TrainingDTO, Training> {



    default Training fromId(Long id) {
        if (id == null) {
            return null;
        }
        Training training = new Training();
        training.setId(id);
        return training;
    }
}
