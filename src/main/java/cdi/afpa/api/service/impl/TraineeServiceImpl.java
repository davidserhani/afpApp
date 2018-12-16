package cdi.afpa.api.service.impl;

import cdi.afpa.api.service.TraineeService;
import cdi.afpa.api.domain.Trainee;
import cdi.afpa.api.repository.TraineeRepository;
import cdi.afpa.api.service.dto.TraineeDTO;
import cdi.afpa.api.service.mapper.TraineeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Trainee.
 */
@Service
@Transactional
public class TraineeServiceImpl implements TraineeService {

    private final Logger log = LoggerFactory.getLogger(TraineeServiceImpl.class);

    private final TraineeRepository traineeRepository;

    private final TraineeMapper traineeMapper;

    public TraineeServiceImpl(TraineeRepository traineeRepository, TraineeMapper traineeMapper) {
        this.traineeRepository = traineeRepository;
        this.traineeMapper = traineeMapper;
    }

    /**
     * Save a trainee.
     *
     * @param traineeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TraineeDTO save(TraineeDTO traineeDTO) {
        log.debug("Request to save Trainee : {}", traineeDTO);

        Trainee trainee = traineeMapper.toEntity(traineeDTO);
        trainee = traineeRepository.save(trainee);
        return traineeMapper.toDto(trainee);
    }

    /**
     * Get all the trainees.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TraineeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trainees");
        return traineeRepository.findAll(pageable)
            .map(traineeMapper::toDto);
    }


    /**
     * Get one trainee by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TraineeDTO> findOne(Long id) {
        log.debug("Request to get Trainee : {}", id);
        return traineeRepository.findById(id)
            .map(traineeMapper::toDto);
    }

    /**
     * Delete the trainee by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trainee : {}", id);
        traineeRepository.deleteById(id);
    }
}
