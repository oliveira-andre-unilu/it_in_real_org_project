package lu.lamtco.timelink.persister;

import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.domain.TimestampEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@link TimestampEntry} entities.
 * Extends {@link JpaRepository} to provide standard data access methods.
 */
public interface TimestampEntryRepository extends JpaRepository<TimestampEntry, Long> {

    /**
     * Retrieves all timestamp entries associated with a specific employee.
     *
     * @param employee the {@link Employee} whose timestamp entries are to be retrieved
     * @return a {@link List} of {@link TimestampEntry} objects for the given employee
     */
    List<TimestampEntry> findByEmployee(Employee employee);
}