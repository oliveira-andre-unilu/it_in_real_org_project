package lu.lamtco.timelink.services;

import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.domain.Project;
import lu.lamtco.timelink.domain.TimestampEntry;
import lu.lamtco.timelink.dto.TimeStampEntryDTO;
import lu.lamtco.timelink.exeptions.InvalidAuthentication;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnauthorizedActionException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.persister.EmployeeRepository;
import lu.lamtco.timelink.persister.ProjectRepository;
import lu.lamtco.timelink.persister.TimestampEntryRepository;
import lu.lamtco.timelink.security.AuthService;
import lu.lamtco.timelink.security.UserAuthData;
import lu.lamtco.timelink.system.GeneralSettings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service responsible for managing timestamp entries.
 * Handles creation, updates, retrieval, and deletion of timestamps,
 * with proper authentication, authorization, and validation.
 *
 * @version 0.1
 */
@Service
public class TimestampEntryService {

    private final TimestampEntryRepository timestampEntryRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final AuthService authService;

    public TimestampEntryService(TimestampEntryRepository timestampEntryRepository, ProjectRepository projectRepository, EmployeeRepository employeeRepository, AuthService authService) {
        this.timestampEntryRepository = timestampEntryRepository;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.authService = authService;
    }

    /**
     * Creates a new timestamp entry for a given employee and project.
     * Admins can create timestamps for any employee; non-admins can only create their own.
     *
     * @param jwtToken     The JWT used for authentication.
     * @param newTimeStamp DTO containing timestamp details.
     * @return The created TimestampEntry entity.
     * @throws NonConformRequestedDataException If timestamp data is invalid.
     * @throws UnexistingEntityException        If the employee or project does not exist.
     * @throws InvalidAuthentication            If the JWT is invalid.
     * @throws UnauthorizedActionException      If a non-admin tries to create timestamps for others.
     */
    @Transactional
    public TimestampEntry createTimeStamp(String jwtToken, TimeStampEntryDTO newTimeStamp) throws NonConformRequestedDataException, UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        boolean requestFromAdmin = true;
        try {
            authService.verifyAdminAccess(jwtToken);
        } catch (UnauthorizedActionException e) {
            requestFromAdmin = false;
        }

        Optional<Project> project = projectRepository.findById(newTimeStamp.getProjectId());
        Optional<Employee> employee = employeeRepository.findById(newTimeStamp.getEmployeeId());

        if (project.isPresent() && employee.isPresent()) {
            Project finalProject = project.get();
            Employee finalEmployee = employee.get();

            if (!requestFromAdmin) {
                authService.verifySelfIdentityAccess(jwtToken, finalEmployee.getId(), finalEmployee.getEmail());
            }

            if (!this.verifyTimeStamp(newTimeStamp)) {
                throw new NonConformRequestedDataException("The requested TimeStamp is not conform!!!");
            }

            TimestampEntry timestampEntry = new TimestampEntry();
            timestampEntry.setProject(finalProject);
            timestampEntry.setEmployee(finalEmployee);
            timestampEntry.setStartingTime(newTimeStamp.getStartTime());
            timestampEntry.setDuration(newTimeStamp.getDuration());
            timestampEntry.setLatitude(newTimeStamp.getLatitude());
            timestampEntry.setLongitude(newTimeStamp.getLongitude());
            timestampEntry.setTag(newTimeStamp.getTag());
            return timestampEntryRepository.save(timestampEntry);
        } else {
            throw new UnexistingEntityException("The requested TimeStamp does not have a valid project/employee id");
        }
    }

    /**
     * Updates an existing timestamp entry.
     *
     * @param jwtToken    The JWT used for authentication.
     * @param newTimeStamp DTO with updated timestamp values.
     * @param timestampId ID of the timestamp to update.
     * @return The updated TimestampEntry entity.
     * @throws NonConformRequestedDataException If the timestamp data is invalid.
     * @throws UnexistingEntityException        If the timestamp, employee, or project does not exist.
     * @throws InvalidAuthentication            If JWT authentication fails.
     * @throws UnauthorizedActionException      If the user is not allowed to update this timestamp.
     */
    @Transactional
    public TimestampEntry updateTimeStamp(String jwtToken, TimeStampEntryDTO newTimeStamp, Long timestampId) throws NonConformRequestedDataException, UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        Optional<Project> project = projectRepository.findById(newTimeStamp.getProjectId());
        Optional<Employee> employee = employeeRepository.findById(newTimeStamp.getEmployeeId());
        TimestampEntry finalTimestampEntry = this.getTimeStampById(jwtToken, timestampId);

        if (project.isPresent()) {
            finalTimestampEntry.setProject(project.get());
        }
        if (employee.isPresent()) {
            finalTimestampEntry.setEmployee(employee.get());
        }
        if (this.verifyTimeStamp(newTimeStamp)) {
            throw new NonConformRequestedDataException("The requested TimeStamp is not conform!!!");
        }
        if (newTimeStamp.getStartTime() != null) {
            finalTimestampEntry.setStartingTime(newTimeStamp.getStartTime());
        }
        if (newTimeStamp.getDuration() != null) {
            finalTimestampEntry.setDuration(newTimeStamp.getDuration());
        }
        if (newTimeStamp.getLatitude() != null) {
            finalTimestampEntry.setLatitude(newTimeStamp.getLatitude());
        }
        if (newTimeStamp.getLongitude() != null) {
            finalTimestampEntry.setLongitude(newTimeStamp.getLongitude());
        }
        if (newTimeStamp.getTag() != null) {
            finalTimestampEntry.setTag(newTimeStamp.getTag());
        }

        return timestampEntryRepository.save(finalTimestampEntry);
    }

    /**
     * Deletes a timestamp entry by ID.
     *
     * @param jwtToken   The JWT used for authentication.
     * @param timestampId ID of the timestamp to delete.
     * @return true if deletion was successful.
     * @throws NonConformRequestedDataException If the ID is invalid.
     * @throws UnexistingEntityException        If the timestamp does not exist.
     * @throws InvalidAuthentication            If JWT authentication fails.
     * @throws UnauthorizedActionException      If the user is not allowed to delete this timestamp.
     */
    @Transactional
    public boolean deleteTimeStamp(String jwtToken, Long timestampId) throws NonConformRequestedDataException, UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        if (timestampId == null || timestampId < 0) {
            throw new NonConformRequestedDataException("The requested TimeStamp does not have a valid id");
        }
        TimestampEntry timestampEntry = this.getTimeStampById(jwtToken, timestampId);
        return true;
    }

    /**
     * Retrieves all timestamps. Admins get all entries; non-admins get their own.
     *
     * @param jwtToken The JWT used for authentication.
     * @return List of TimestampEntry objects.
     * @throws InvalidAuthentication If JWT authentication fails.
     */
    public List<TimestampEntry> getAllTimeStamps(String jwtToken) throws InvalidAuthentication {
        try {
            authService.verifyAdminAccess(jwtToken);
            return timestampEntryRepository.findAll();
        } catch (UnauthorizedActionException e) {
            return getSelfTimeEntries(jwtToken);
        }
    }

    /**
     * Helper to retrieve timestamps for the authenticated user only.
     */
    private List<TimestampEntry> getSelfTimeEntries(String jwtToken) throws InvalidAuthentication {
        UserAuthData userAuthData = authService.getAuthData(jwtToken);
        long id = userAuthData.id();
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee employee = optionalEmployee.orElseThrow(() -> new InvalidAuthentication("The authenticated Employee does not exist"));
        return timestampEntryRepository.findByEmployee(employee);
    }

    /**
     * Retrieves a timestamp entry by its ID.
     * Admins can access any entry; non-admins can only access their own.
     *
     * @param jwtToken    The JWT used for authentication.
     * @param timestampId ID of the timestamp.
     * @return The requested TimestampEntry.
     * @throws NonConformRequestedDataException If the ID is invalid.
     * @throws UnexistingEntityException        If the timestamp does not exist.
     * @throws InvalidAuthentication            If authentication fails.
     * @throws UnauthorizedActionException      If the user is not allowed to access this timestamp.
     */
    public TimestampEntry getTimeStampById(String jwtToken, Long timestampId) throws NonConformRequestedDataException, UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        if (timestampId == null || timestampId <= 0) {
            throw new NonConformRequestedDataException("The requested id is invalid");
        }

        Optional<TimestampEntry> timestampEntry = timestampEntryRepository.findById(timestampId);
        if (timestampEntry.isPresent()) {
            TimestampEntry result = timestampEntry.get();
            try {
                authService.verifyAdminAccess(jwtToken);
            } catch (UnauthorizedActionException e) {
                Employee employee = result.getEmployee();
                authService.verifySelfIdentityAccess(jwtToken, employee.getId(), employee.getEmail());
            }
            return result;
        } else {
            throw new UnexistingEntityException("The requested timestamp does not exist");
        }
    }

    // Helper methods

    /**
     * Validates a timestamp entry's duration and coordinates.
     */
    private boolean verifyTimeStamp(TimeStampEntryDTO timestampEntry) {
        if (timestampEntry.getDuration() == null || timestampEntry.getDuration() < 0
                || timestampEntry.getDuration() > GeneralSettings.getMaximumAllowedHoursOfWork()) {
            return false;
        }
        return this.verifyCoordinate(timestampEntry.getLatitude(), timestampEntry.getLongitude());
    }

    /**
     * Validates latitude and longitude.
     */
    private boolean verifyCoordinate(String latitude, String longitude) {
        if (latitude == null || longitude == null || latitude.isEmpty() || longitude.isEmpty()) {
            return false;
        }
        double lat = Double.parseDouble(latitude);
        double lon = Double.parseDouble(longitude);
        return lat >= -90 && lat <= 90 && lon >= -180 && lon <= 180;
    }
}