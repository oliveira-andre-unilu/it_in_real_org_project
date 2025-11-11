package lu.lamtco.timelink.persister;

import lu.lamtco.timelink.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {}

