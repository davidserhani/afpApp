package cdi.afpa.api.service;

import cdi.afpa.api.service.dto.TrainingDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Training.
 */
public interface TrainingService {

    /**
     * Save a training.
     *
     * @param trainingDTO the entity to save
     * @return the persisted entity
     */
    TrainingDTO save(TrainingDTO trainingDTO);

    /**
     * Get all the trainings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TrainingDTO> findAll(Pageable pageable);


    /**
     * Get the "id" training.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TrainingDTO> findOne(Long id);

    /**
     * Delete the "id" training.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
