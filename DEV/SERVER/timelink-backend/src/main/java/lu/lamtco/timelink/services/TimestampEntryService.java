package lu.lamtco.timelink.services;

import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.domain.Project;
import lu.lamtco.timelink.domain.TimestampEntry;
import lu.lamtco.timelink.dto.TimeStampEntryDTO;
import lu.lamtco.timelink.persister.EmployeeRepository;
import lu.lamtco.timelink.persister.ProjectRepository;
import lu.lamtco.timelink.persister.TimestampEntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public TimestampEntry createTimeStamp(TimeStampEntryDTO newTimeStamp) {
        //Looking for both employee and project
        Optional<Project> project = projectRepository.findById(newTimeStamp.getProjectId());
        Optional<Employee> employee = employeeRepository.findById(newTimeStamp.getEmployeeId());

        if(project.isPresent() && employee.isPresent()) {
            Project finalProject = project.get();
            Employee finalEmployee = employee.get();

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
            return null;
        }
    }

    @Transactional
    public TimestampEntry updateTimeStamp(TimeStampEntryDTO newTimeStamp, Long timestampId) {
        //Looking for all the needed information
        Optional<Project> project = projectRepository.findById(newTimeStamp.getProjectId());
        Optional<Employee> employee = employeeRepository.findById(newTimeStamp.getEmployeeId());
        Optional<TimestampEntry> timestampEntry = timestampEntryRepository.findById(timestampId);

        if(project.isPresent() && employee.isPresent() && timestampEntry.isPresent()) {
            Project finalProject = project.get();
            Employee finalEmployee = employee.get();
            TimestampEntry finalTimestampEntry = timestampEntry.get();

            finalTimestampEntry.setProject(finalProject);
            finalTimestampEntry.setEmployee(finalEmployee);
            finalTimestampEntry.setStartingTime(newTimeStamp.getStartTime());
            finalTimestampEntry.setDuration(newTimeStamp.getDuration());
            finalTimestampEntry.setLatitude(newTimeStamp.getLatitude());
            finalTimestampEntry.setLongitude(newTimeStamp.getLongitude());
            finalTimestampEntry.setTag(newTimeStamp.getTag());
            return timestampEntryRepository.save(finalTimestampEntry);
        }else{
            return null;
        }
    }

}
