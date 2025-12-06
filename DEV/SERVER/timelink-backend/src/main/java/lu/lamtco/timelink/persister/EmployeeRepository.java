package lu.lamtco.timelink.persister;

import lu.lamtco.timelink.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@link Employee} entities.
 * Extends {@link JpaRepository} to provide standard data access methods.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Finds an employee by their email address.
     *
     * @param email the email of the employee to find
     * @return an {@link Optional} containing the {@link Employee} if found, or empty if not found
     */
    Optional<Employee> findByEmail(String email);
}