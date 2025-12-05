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


    @Transactional
    public TimestampEntry createTimeStamp(String jwtToken, TimeStampEntryDTO newTimeStamp) throws NonConformRequestedDataException, UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        //Verifying if the request comes from an admin
        boolean requestFromAdmin = true;
        try{
            authService.verifyAdminAccess(jwtToken);
        } catch (UnauthorizedActionException e) {
            requestFromAdmin = false;
        }


        //Looking for both employee and project
        Optional<Project> project = projectRepository.findById(newTimeStamp.getProjectId());
        Optional<Employee> employee = employeeRepository.findById(newTimeStamp.getEmployeeId());

        if(project.isPresent() && employee.isPresent()) {
            Project finalProject = project.get();
            Employee finalEmployee = employee.get();

            //Verifying self identity if user is not admin
            if(!requestFromAdmin){
                authService.verifySelfIdentityAccess(jwtToken, finalEmployee.getId(), finalEmployee.getEmail());
            }

            if(!this.verifyTimeStamp(newTimeStamp)) {
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
        }else{
            throw new UnexistingEntityException("The requested TimeStamp does not have a valid project/employee id");
        }
    }

    @Transactional
    public TimestampEntry updateTimeStamp(String jwtToken, TimeStampEntryDTO newTimeStamp, Long timestampId) throws NonConformRequestedDataException, UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        //Looking for all the needed information
        Optional<Project> project = projectRepository.findById(newTimeStamp.getProjectId());
        Optional<Employee> employee = employeeRepository.findById(newTimeStamp.getEmployeeId());
        TimestampEntry finalTimestampEntry = this.getTimeStampById(jwtToken, timestampId);

        if(project.isPresent()) {
            Project finalProject = project.get();
            finalTimestampEntry.setProject(finalProject);
        }
        if(employee.isPresent()) {
            Employee finalEmployee = employee.get();
            finalTimestampEntry.setEmployee(finalEmployee);
        }
        if(this.verifyTimeStamp(newTimeStamp)){
            throw new NonConformRequestedDataException("The requested TimeStamp is not conform!!!");
        }
        if(newTimeStamp.getStartTime()!=null){
            finalTimestampEntry.setStartingTime(newTimeStamp.getStartTime());
        }
        if(newTimeStamp.getDuration()!=null){
            finalTimestampEntry.setDuration(newTimeStamp.getDuration());
        }
        if(newTimeStamp.getLatitude()!=null){
            finalTimestampEntry.setLatitude(newTimeStamp.getLatitude());
        }
        if(newTimeStamp.getLongitude()!=null){
            finalTimestampEntry.setLongitude(newTimeStamp.getLongitude());
        }
        if(newTimeStamp.getTag()!=null){
            finalTimestampEntry.setTag(newTimeStamp.getTag());
        }

        return timestampEntryRepository.save(finalTimestampEntry);
    }

    @Transactional
    public boolean deleteTimeStamp(String jwtToken, Long timestampId) throws NonConformRequestedDataException, UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        if(timestampId == null || timestampId < 0) {
            throw new NonConformRequestedDataException("The requested TimeStamp does not have a valid id");
        }
        TimestampEntry timestampEntry = this.getTimeStampById(jwtToken, timestampId);
        return true;
    }

    public List<TimestampEntry> getAllTimeStamps(String jwtToken) throws InvalidAuthentication {
        try{
            authService.verifyAdminAccess(jwtToken);
            return timestampEntryRepository.findAll();
        }catch (UnauthorizedActionException e){
            return getSelfTimeEntries(jwtToken);
        }
    }

    private List<TimestampEntry> getSelfTimeEntries(String jwtToken) throws InvalidAuthentication {
        UserAuthData userAuthData = authService.getAuthData(jwtToken);
        long id = userAuthData.id();
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee employee;
        if(optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();
        }else{
            throw new InvalidAuthentication("The authenticated Employee does not exist");
        }
        return timestampEntryRepository.findByEmployee(employee);
    }

    public TimestampEntry getTimeStampById(String jwtToken, Long timestampId) throws NonConformRequestedDataException, UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        if(timestampId == null || timestampId <= 0) {
            throw new NonConformRequestedDataException("The requested id is invalid");
        }

        Optional<TimestampEntry> timestampEntry = timestampEntryRepository.findById(timestampId);
        if(timestampEntry.isPresent()) {
            TimestampEntry result = timestampEntry.get();
            //Security verification
            try{
                authService.verifyAdminAccess(jwtToken);
            } catch (UnauthorizedActionException e) {
                Employee employee = result.getEmployee();
                authService.verifySelfIdentityAccess(jwtToken, employee.getId(), employee.getEmail());
            }
            return result;
        }else{
            throw new UnexistingEntityException("The requested timestamp does not exist");
        }
    }

    //Helper methods

    private boolean verifyTimeStamp(TimeStampEntryDTO timestampEntry) {
        // verifying duration
        if(timestampEntry.getDuration() == null || timestampEntry.getDuration() < 0 || timestampEntry.getDuration() > GeneralSettings.getMaximumAllowedHoursOfWork()) {
            return false;
        }
        return this.verifyCoordinate(timestampEntry.getLatitude(), timestampEntry.getLongitude());
    }

    private boolean verifyCoordinate(String latitude, String longitude) {
        if(latitude == null || longitude == null || latitude.isEmpty() || longitude.isEmpty()) {
            return false;
        }
        //Verifying latitude
        if(Double.parseDouble(latitude) < -90 || Double.parseDouble(latitude) > 90) {
            return false;
        }
        //Verifying longitude
        if(Double.parseDouble(longitude) < -180 || Double.parseDouble(longitude) > 180) {
            return false;
        }
        return true;
    }

}
