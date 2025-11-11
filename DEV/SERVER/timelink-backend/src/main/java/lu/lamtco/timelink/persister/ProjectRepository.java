package lu.lamtco.timelink.persister;

import lu.lamtco.timelink.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {}
