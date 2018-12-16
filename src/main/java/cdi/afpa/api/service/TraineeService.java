package cdi.afpa.api.service;

import cdi.afpa.api.service.dto.TraineeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Trainee.
 */
public interface TraineeService {

    /**
     * Save a trainee.
     *
     * @param traineeDTO the entity to save
     * @return the persisted entity
     */
    TraineeDTO save(TraineeDTO traineeDTO);

    /**
     * Get all the trainees.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TraineeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" trainee.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TraineeDTO> findOne(Long id);

    /**
     * Delete the "id" trainee.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
