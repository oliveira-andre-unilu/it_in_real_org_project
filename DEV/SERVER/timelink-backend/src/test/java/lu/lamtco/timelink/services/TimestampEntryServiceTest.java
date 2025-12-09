package lu.lamtco.timelink.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.checkerframework.checker.units.qual.t;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.domain.Project;
import lu.lamtco.timelink.domain.Tag;
import lu.lamtco.timelink.domain.TimestampEntry;
import lu.lamtco.timelink.dto.TimeStampEntryDTO;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.persister.EmployeeRepository;
import lu.lamtco.timelink.persister.ProjectRepository;
import lu.lamtco.timelink.persister.TimestampEntryRepository;

public class TimestampEntryServiceTest {
    private TimestampEntryService timestampEntryService;
    private TimestampEntryRepository timestampEntryRepository;
    private EmployeeRepository employeeRepository;
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        timestampEntryRepository = mock(TimestampEntryRepository.class);
        projectRepository = mock(ProjectRepository.class);
        employeeRepository = mock(EmployeeRepository.class);
        timestampEntryService = new TimestampEntryService(timestampEntryRepository, projectRepository,
                employeeRepository);
    }

    // create timestamp with valid data should be saved
    @Test
    void createTimeStamp_ShouldSaveTimestamp_WhenValidData()
            throws NonConformRequestedDataException, UnexistingEntityException {
        TimeStampEntryDTO timeStampEntryDTO = new TimeStampEntryDTO();
        timeStampEntryDTO.setProjectId(1L);
        timeStampEntryDTO.setEmployeeId(1L);
        timeStampEntryDTO.setStartTime(LocalDateTime.now());
        timeStampEntryDTO.setDuration(8.0);
        timeStampEntryDTO.setLatitude("49.8153");
        timeStampEntryDTO.setLongitude("6.1296");
        timeStampEntryDTO.setTag(Tag.WORK);

        Project project = new Project();
        project.setId(1L);
        Employee employee = new Employee();
        employee.setId(1L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(timestampEntryRepository.save(any(TimestampEntry.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TimestampEntry result = timestampEntryService.createTimeStamp(timeStampEntryDTO);

        assertNotNull(result);
        assertEquals(project, result.getProject());
        assertEquals(employee, result.getEmployee());
        assertEquals(8.0, result.getDuration());
        assertEquals("49.8153", result.getLatitude());
        assertEquals("6.1296", result.getLongitude());
        assertEquals(Tag.WORK, result.getTag());
        verify(timestampEntryRepository, times(1)).save(any(TimestampEntry.class));

    }

    // create timestamp should throw execrption because project is not found
    @Test
    void createTimeStamp_ShouldThrowException_WhenProjectNotFound() {
        TimeStampEntryDTO timeStampEntryDTO = new TimeStampEntryDTO();
        timeStampEntryDTO.setProjectId(999L);
        timeStampEntryDTO.setEmployeeId(1L);

        when(projectRepository.findById(999L)).thenReturn(Optional.empty());
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(new Employee()));

        assertThrows(UnexistingEntityException.class, () -> timestampEntryService.createTimeStamp(timeStampEntryDTO));
    }

    // create timestamp with invalid duration, should throw exception
    @Test
    void createTimeStamp_ShouldThrowException_WhenInvalidDuration() {
        TimeStampEntryDTO timeStampEntryDTO = new TimeStampEntryDTO();
        timeStampEntryDTO.setProjectId(1L);
        timeStampEntryDTO.setEmployeeId(1L);
        timeStampEntryDTO.setDuration(25.0); // too long

        Project project = new Project();
        Employee employee = new Employee();

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        assertThrows(NonConformRequestedDataException.class,
                () -> timestampEntryService.createTimeStamp(timeStampEntryDTO));
    }

    // update timestamp valid data should work
    @Test
    void updateTimeStamp_ShouldUpdateTimestamp_WhenValidData()
            throws NonConformRequestedDataException, UnexistingEntityException {
        TimeStampEntryDTO timeStampEntryDTO = new TimeStampEntryDTO();
        timeStampEntryDTO.setProjectId(2L);
        timeStampEntryDTO.setEmployeeId(2L);
        timeStampEntryDTO.setStartTime(LocalDateTime.now());
        timeStampEntryDTO.setDuration(6.0);
        timeStampEntryDTO.setLatitude("48.8566");
        timeStampEntryDTO.setLongitude("2.3522");
        timeStampEntryDTO.setTag(Tag.BREAK);

        Project project = new Project();
        project.setId(2L);
        Employee employee = new Employee();
        employee.setId(2L);
        TimestampEntry existingTimestamp = new TimestampEntry();
        existingTimestamp.setId(1L);

        when(projectRepository.findById(2L)).thenReturn(Optional.of(project));
        when(employeeRepository.findById(2L)).thenReturn(Optional.of(employee));
        when(timestampEntryRepository.findById(1L)).thenReturn(Optional.of(existingTimestamp));
        when(timestampEntryRepository.save(any(TimestampEntry.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TimestampEntry result = timestampEntryService.updateTimeStamp(timeStampEntryDTO, 1L);

        assertNotNull(result);
        assertEquals(project, result.getProject());
        assertEquals(employee, result.getEmployee());
        assertEquals(6.0, result.getDuration());
        assertEquals("48.8566", result.getLatitude());
        assertEquals("2.3522", result.getLongitude());
        assertEquals(Tag.BREAK, result.getTag());
    }

    // delete when timestamp exist should work
    @Test
    void deleteTimeStamp_ShouldReturnTrue_WhenTimestampExists()
            throws NonConformRequestedDataException, UnexistingEntityException {
        long timestampId = 1L;
        TimestampEntry timestamp = new TimestampEntry();
        timestamp.setId(timestampId);

        when(timestampEntryRepository.findById(timestampId)).thenReturn(Optional.of(timestamp));

        boolean result = timestampEntryService.deleteTimeStamp(timestampId);

        assertTrue(result);
        verify(timestampEntryRepository, times(1)).delete(timestamp);
    }

    // delete timestamp with invalid id, should not work
    @Test
    void deleteTimeStamp_ShouldThrowException_WhenInvalidId() {
        Long invalidId = -1L;

        assertThrows(NonConformRequestedDataException.class, () -> timestampEntryService.deleteTimeStamp(invalidId));
    }

    // delete timestamp but not found so should not work
    @Test
    void deleteTimeStamp_ShouldThrowException_WhenTimestampNotFound() {
        long timestampId = 999L;

        when(timestampEntryRepository.findById(timestampId)).thenReturn(Optional.empty());

        assertThrows(UnexistingEntityException.class, () -> timestampEntryService.deleteTimeStamp(timestampId));
    }

    // get all timestamp, should work
    @Test
    void getAllTimeStamps_ShouldReturnList() {
        List<TimestampEntry> expectedTimestamps = Arrays.asList(new TimestampEntry(), new TimestampEntry());

        when(timestampEntryRepository.findAll()).thenReturn(expectedTimestamps);

        List<TimestampEntry> result = timestampEntryService.getAllTimeStamps();

        assertEquals(2, result.size());
        verify(timestampEntryRepository, times(1)).findAll();
    }

    // get timestamp by id
    @Test
    void getTimeStampById_ShouldReturnTimestamp_WhenExists()
            throws NonConformRequestedDataException, UnexistingEntityException {
        long timestampId = 1L;
        TimestampEntry expectedTimestamp = new TimestampEntry();
        expectedTimestamp.setId(timestampId);

        when(timestampEntryRepository.findById(timestampId)).thenReturn(Optional.of(expectedTimestamp));

        TimestampEntry result = timestampEntryService.getTimeStampById(timestampId);

        assertNotNull(result);
        assertEquals(timestampId, result.getId());
    }

    // get timestamp by id with invalid id should not pass
    @Test
    void getTimeStampById_ShouldThrowException_WhenInvalidId() {
        Long invalidId = 0L;

        assertThrows(NonConformRequestedDataException.class, () -> timestampEntryService.getTimeStampById(invalidId));
    }

    // get timestamp by id, not found
    @Test
    void getTimeStampById_ShouldThrowException_WhenNotFound() {
        long timestampId = 999L;

        when(timestampEntryRepository.findById(timestampId)).thenReturn(Optional.empty());

        assertThrows(UnexistingEntityException.class, () -> timestampEntryService.getTimeStampById(timestampId));
    }

}