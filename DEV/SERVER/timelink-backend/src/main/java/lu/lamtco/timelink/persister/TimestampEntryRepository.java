package lu.lamtco.timelink.persister;

import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.domain.TimestampEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimestampEntryRepository extends JpaRepository<TimestampEntry, Long> {

    List<TimestampEntry> findByEmployee(Employee employee);
}
