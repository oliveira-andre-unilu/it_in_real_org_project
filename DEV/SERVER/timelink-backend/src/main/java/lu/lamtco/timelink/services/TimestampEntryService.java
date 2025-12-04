package lu.lamtco.timelink.services;

import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.domain.Project;
import lu.lamtco.timelink.domain.TimestampEntry;
import lu.lamtco.timelink.dto.TimeStampEntryDTO;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.persister.EmployeeRepository;
import lu.lamtco.timelink.persister.ProjectRepository;
import lu.lamtco.timelink.persister.TimestampEntryRepository;
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

    public TimestampEntryService(TimestampEntryRepository timestampEntryRepository, ProjectRepository projectRepository, EmployeeRepository employeeRepository) {
        this.timestampEntryRepository = timestampEntryRepository;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
    }


    @Transactional
    public TimestampEntry createTimeStamp(TimeStampEntryDTO newTimeStamp) throws NonConformRequestedDataException, UnexistingEntityException {
        //Looking for both employee and project
        Optional<Project> project = projectRepository.findById(newTimeStamp.getProjectId());
        Optional<Employee> employee = employeeRepository.findById(newTimeStamp.getEmployeeId());

        if(project.isPresent() && employee.isPresent()) {
            Project finalProject = project.get();
            Employee finalEmployee = employee.get();

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
    public TimestampEntry updateTimeStamp(TimeStampEntryDTO newTimeStamp, Long timestampId) throws NonConformRequestedDataException, UnexistingEntityException {
        //Looking for all the needed information
        Optional<Project> project = projectRepository.findById(newTimeStamp.getProjectId());
        Optional<Employee> employee = employeeRepository.findById(newTimeStamp.getEmployeeId());
        Optional<TimestampEntry> timestampEntry = timestampEntryRepository.findById(timestampId);

        if(project.isPresent() && employee.isPresent() && timestampEntry.isPresent()) {
            Project finalProject = project.get();
            Employee finalEmployee = employee.get();
            TimestampEntry finalTimestampEntry = timestampEntry.get();
            if(this.verifyTimeStamp(newTimeStamp)){
                throw new NonConformRequestedDataException("The requested TimeStamp is not conform!!!");
            }
            finalTimestampEntry.setProject(finalProject);
            finalTimestampEntry.setEmployee(finalEmployee);
            finalTimestampEntry.setStartingTime(newTimeStamp.getStartTime());
            finalTimestampEntry.setDuration(newTimeStamp.getDuration());
            finalTimestampEntry.setLatitude(newTimeStamp.getLatitude());
            finalTimestampEntry.setLongitude(newTimeStamp.getLongitude());
            finalTimestampEntry.setTag(newTimeStamp.getTag());
            return timestampEntryRepository.save(finalTimestampEntry);
        }else{
            throw new UnexistingEntityException("One of the selected entites does not have a valid id");
        }
    }

    @Transactional
    public boolean deleteTimeStamp(Long timestampId) throws NonConformRequestedDataException, UnexistingEntityException {
        if(timestampId == null || timestampId < 0) {
            throw new NonConformRequestedDataException("The requested TimeStamp does not have a valid id");
        }
        Optional<TimestampEntry> timestampEntry = timestampEntryRepository.findById(timestampId);
        if(timestampEntry.isPresent()) {
            timestampEntryRepository.delete(timestampEntry.get());
            return true;
        }else{
            throw new UnexistingEntityException("The requested TimeStamp does not exist");
        }
    }

    public List<TimestampEntry> getAllTimeStamps() {
        return timestampEntryRepository.findAll();
    }

    public TimestampEntry getTimeStampById(Long timestampId) throws NonConformRequestedDataException, UnexistingEntityException {
        if(timestampId == null || timestampId <= 0) {
            throw new NonConformRequestedDataException("The requested id is invalid");
        }

        Optional<TimestampEntry> timestampEntry = timestampEntryRepository.findById(timestampId);
        if(timestampEntry.isPresent()) {
            return timestampEntry.get();
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
