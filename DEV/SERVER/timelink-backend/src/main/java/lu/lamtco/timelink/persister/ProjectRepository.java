package lu.lamtco.timelink.persister;

import lu.lamtco.timelink.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@link Project} entities.
 * Extends {@link JpaRepository} to provide standard data access methods.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Finds a project by its unique project number.
     *
     * @param number the project number to search for
     * @return an {@link Optional} containing the {@link Project} if found, or empty if not found
     */
    Optional<Project> findByNumber(String number);
}