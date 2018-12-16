package cdi.afpa.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import cdi.afpa.api.service.TraineeService;
import cdi.afpa.api.web.rest.errors.BadRequestAlertException;
import cdi.afpa.api.web.rest.util.HeaderUtil;
import cdi.afpa.api.web.rest.util.PaginationUtil;
import cdi.afpa.api.service.dto.TraineeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Trainee.
 */
@RestController
@RequestMapping("/api")
public class TraineeResource {

    private final Logger log = LoggerFactory.getLogger(TraineeResource.class);

    private static final String ENTITY_NAME = "trainee";

    private final TraineeService traineeService;

    public TraineeResource(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    /**
     * POST  /trainees : Create a new trainee.
     *
     * @param traineeDTO the traineeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new traineeDTO, or with status 400 (Bad Request) if the trainee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trainees")
    @Timed
    public ResponseEntity<TraineeDTO> createTrainee(@RequestBody TraineeDTO traineeDTO) throws URISyntaxException {
        log.debug("REST request to save Trainee : {}", traineeDTO);
        if (traineeDTO.getId() != null) {
            throw new BadRequestAlertException("A new trainee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TraineeDTO result = traineeService.save(traineeDTO);
        return ResponseEntity.created(new URI("/api/trainees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trainees : Updates an existing trainee.
     *
     * @param traineeDTO the traineeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated traineeDTO,
     * or with status 400 (Bad Request) if the traineeDTO is not valid,
     * or with status 500 (Internal Server Error) if the traineeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trainees")
    @Timed
    public ResponseEntity<TraineeDTO> updateTrainee(@RequestBody TraineeDTO traineeDTO) throws URISyntaxException {
        log.debug("REST request to update Trainee : {}", traineeDTO);
        if (traineeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TraineeDTO result = traineeService.save(traineeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, traineeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trainees : get all the trainees.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trainees in body
     */
    @GetMapping("/trainees")
    @Timed
    public ResponseEntity<List<TraineeDTO>> getAllTrainees(Pageable pageable) {
        log.debug("REST request to get a page of Trainees");
        Page<TraineeDTO> page = traineeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trainees");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /trainees/:id : get the "id" trainee.
     *
     * @param id the id of the traineeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the traineeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/trainees/{id}")
    @Timed
    public ResponseEntity<TraineeDTO> getTrainee(@PathVariable Long id) {
        log.debug("REST request to get Trainee : {}", id);
        Optional<TraineeDTO> traineeDTO = traineeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(traineeDTO);
    }

    /**
     * DELETE  /trainees/:id : delete the "id" trainee.
     *
     * @param id the id of the traineeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trainees/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrainee(@PathVariable Long id) {
        log.debug("REST request to delete Trainee : {}", id);
        traineeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
