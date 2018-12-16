package cdi.afpa.api.web.rest;

import cdi.afpa.api.AfpappApp;

import cdi.afpa.api.domain.Trainee;
import cdi.afpa.api.repository.TraineeRepository;
import cdi.afpa.api.service.TraineeService;
import cdi.afpa.api.service.dto.TraineeDTO;
import cdi.afpa.api.service.mapper.TraineeMapper;
import cdi.afpa.api.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static cdi.afpa.api.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TraineeResource REST controller.
 *
 * @see TraineeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AfpappApp.class)
public class TraineeResourceIntTest {

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private TraineeMapper traineeMapper;

    @Autowired
    private TraineeService traineeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTraineeMockMvc;

    private Trainee trainee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TraineeResource traineeResource = new TraineeResource(traineeService);
        this.restTraineeMockMvc = MockMvcBuilders.standaloneSetup(traineeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trainee createEntity(EntityManager em) {
        Trainee trainee = new Trainee()
            .lastName(DEFAULT_LAST_NAME)
            .firstName(DEFAULT_FIRST_NAME);
        return trainee;
    }

    @Before
    public void initTest() {
        trainee = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrainee() throws Exception {
        int databaseSizeBeforeCreate = traineeRepository.findAll().size();

        // Create the Trainee
        TraineeDTO traineeDTO = traineeMapper.toDto(trainee);
        restTraineeMockMvc.perform(post("/api/trainees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traineeDTO)))
            .andExpect(status().isCreated());

        // Validate the Trainee in the database
        List<Trainee> traineeList = traineeRepository.findAll();
        assertThat(traineeList).hasSize(databaseSizeBeforeCreate + 1);
        Trainee testTrainee = traineeList.get(traineeList.size() - 1);
        assertThat(testTrainee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testTrainee.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
    }

    @Test
    @Transactional
    public void createTraineeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traineeRepository.findAll().size();

        // Create the Trainee with an existing ID
        trainee.setId(1L);
        TraineeDTO traineeDTO = traineeMapper.toDto(trainee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraineeMockMvc.perform(post("/api/trainees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traineeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trainee in the database
        List<Trainee> traineeList = traineeRepository.findAll();
        assertThat(traineeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTrainees() throws Exception {
        // Initialize the database
        traineeRepository.saveAndFlush(trainee);

        // Get all the traineeList
        restTraineeMockMvc.perform(get("/api/trainees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainee.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getTrainee() throws Exception {
        // Initialize the database
        traineeRepository.saveAndFlush(trainee);

        // Get the trainee
        restTraineeMockMvc.perform(get("/api/trainees/{id}", trainee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trainee.getId().intValue()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTrainee() throws Exception {
        // Get the trainee
        restTraineeMockMvc.perform(get("/api/trainees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainee() throws Exception {
        // Initialize the database
        traineeRepository.saveAndFlush(trainee);

        int databaseSizeBeforeUpdate = traineeRepository.findAll().size();

        // Update the trainee
        Trainee updatedTrainee = traineeRepository.findById(trainee.getId()).get();
        // Disconnect from session so that the updates on updatedTrainee are not directly saved in db
        em.detach(updatedTrainee);
        updatedTrainee
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME);
        TraineeDTO traineeDTO = traineeMapper.toDto(updatedTrainee);

        restTraineeMockMvc.perform(put("/api/trainees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traineeDTO)))
            .andExpect(status().isOk());

        // Validate the Trainee in the database
        List<Trainee> traineeList = traineeRepository.findAll();
        assertThat(traineeList).hasSize(databaseSizeBeforeUpdate);
        Trainee testTrainee = traineeList.get(traineeList.size() - 1);
        assertThat(testTrainee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTrainee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTrainee() throws Exception {
        int databaseSizeBeforeUpdate = traineeRepository.findAll().size();

        // Create the Trainee
        TraineeDTO traineeDTO = traineeMapper.toDto(trainee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTraineeMockMvc.perform(put("/api/trainees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traineeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trainee in the database
        List<Trainee> traineeList = traineeRepository.findAll();
        assertThat(traineeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrainee() throws Exception {
        // Initialize the database
        traineeRepository.saveAndFlush(trainee);

        int databaseSizeBeforeDelete = traineeRepository.findAll().size();

        // Get the trainee
        restTraineeMockMvc.perform(delete("/api/trainees/{id}", trainee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Trainee> traineeList = traineeRepository.findAll();
        assertThat(traineeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trainee.class);
        Trainee trainee1 = new Trainee();
        trainee1.setId(1L);
        Trainee trainee2 = new Trainee();
        trainee2.setId(trainee1.getId());
        assertThat(trainee1).isEqualTo(trainee2);
        trainee2.setId(2L);
        assertThat(trainee1).isNotEqualTo(trainee2);
        trainee1.setId(null);
        assertThat(trainee1).isNotEqualTo(trainee2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraineeDTO.class);
        TraineeDTO traineeDTO1 = new TraineeDTO();
        traineeDTO1.setId(1L);
        TraineeDTO traineeDTO2 = new TraineeDTO();
        assertThat(traineeDTO1).isNotEqualTo(traineeDTO2);
        traineeDTO2.setId(traineeDTO1.getId());
        assertThat(traineeDTO1).isEqualTo(traineeDTO2);
        traineeDTO2.setId(2L);
        assertThat(traineeDTO1).isNotEqualTo(traineeDTO2);
        traineeDTO1.setId(null);
        assertThat(traineeDTO1).isNotEqualTo(traineeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(traineeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(traineeMapper.fromId(null)).isNull();
    }
}
