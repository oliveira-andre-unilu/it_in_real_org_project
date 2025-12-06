package lu.lamtco.timelink.business;

import lu.lamtco.timelink.domain.Customer;
import lu.lamtco.timelink.domain.Project;
import lu.lamtco.timelink.dto.ProjectDTO;
import lu.lamtco.timelink.exeptions.InvalidAuthentication;
import lu.lamtco.timelink.exeptions.UnauthorizedActionException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.persister.CustomerRepository;
import lu.lamtco.timelink.persister.ProjectRepository;
import lu.lamtco.timelink.security.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service responsible for managing projects and their relationship to customers.
 * Handles creation, updates, retrieval and deletion with proper authentication
 * and authorization checks.
 */
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CustomerRepository customerRepository;
    private final AuthService authService;

    public ProjectService(ProjectRepository projectRepository, CustomerRepository customerRepository, AuthService authService) {
        this.projectRepository = projectRepository;
        this.customerRepository = customerRepository;
        this.authService = authService;
    }

    /**
     * Creates a new project linked to a customer. Only admins may perform this action.
     *
     * @param jwtToken   The JWT used to authenticate the request.
     * @param newProject DTO containing the project details.
     * @return The created Project entity.
     * @throws UnexistingEntityException   If the customer does not exist or the project already exists.
     * @throws InvalidAuthentication       If the JWT is invalid.
     * @throws UnauthorizedActionException If the user is not an admin.
     */
    @Transactional
    public Project createProject(String jwtToken, ProjectDTO newProject) throws UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        authService.verifyAdminAccess(jwtToken);

        Optional<Customer> relatedCustomer = this.customerRepository.findById(newProject.getCostumerId());

        if (relatedCustomer.isPresent()) {

            this.verifyEntriesFrom(newProject);

            Customer customer = relatedCustomer.get();
            Project project = new Project();
            project.setCustomer(customer);
            project.setName(newProject.getName());
            project.setNumber(newProject.getNumber());
            project.setLocation(newProject.getLocation());
            return projectRepository.save(project);
        } else {
            throw new UnexistingEntityException("The related costumer does not exist!!!");
        }
    }

    /**
     * Updates an existing project. Only admins may perform this action.
     *
     * @param jwtToken   The JWT used to authenticate the request.
     * @param newProject DTO with updated project values.
     * @param projectId  ID of the project to update.
     * @return The updated Project entity.
     * @throws InvalidAuthentication       If the JWT is invalid.
     * @throws UnauthorizedActionException If the user is not an admin.
     * @throws UnexistingEntityException   If the project or customer does not exist.
     */
    @Transactional
    public Project updateProject(String jwtToken, ProjectDTO newProject, Long projectId) throws InvalidAuthentication, UnauthorizedActionException, UnexistingEntityException {
        authService.verifyAdminAccess(jwtToken);

        Optional<Project> relatedProject = this.projectRepository.findById(projectId);

        if (relatedProject.isPresent()) {
            Project project = relatedProject.get();
            if (project.getCustomer() != null) {
                Optional<Customer> relatedCustomer = this.customerRepository.findById(newProject.getCostumerId());
                if (relatedCustomer.isPresent()) {
                    Customer customer = relatedCustomer.get();
                    project.setCustomer(customer);
                } else {
                    throw new UnexistingEntityException("The related costumer does not exist!!!");
                }
            }
            if (project.getName() != null) {
                project.setName(newProject.getName());
            }
            if (project.getNumber() != null) {
                project.setNumber(newProject.getNumber());
            }
            if (project.getLocation() != null) {
                project.setLocation(newProject.getLocation());
            }
            return projectRepository.save(project);
        } else {
            throw new UnexistingEntityException("The requested project does not exist!!!");
        }
    }

    /**
     * Retrieves a project by its ID. Any authenticated user may access this.
     *
     * @param jwtToken  The JWT used for authentication.
     * @param projectId The project ID.
     * @return The requested Project entity.
     * @throws UnexistingEntityException If the project does not exist.
     * @throws InvalidAuthentication     If authentication fails.
     */
    public Project getProject(String jwtToken, Long projectId) throws UnexistingEntityException, InvalidAuthentication {
        authService.getAuthData(jwtToken);

        Optional<Project> relatedProject = this.projectRepository.findById(projectId);
        if (relatedProject.isPresent()) {
            return relatedProject.get();
        } else {
            throw new UnexistingEntityException("The requested project does not exist");
        }
    }

    /**
     * Deletes a project by its ID. Only admins may perform this action.
     *
     * @param jwtToken  The JWT used to authenticate the request.
     * @param projectId The ID of the project to delete.
     * @return true if the project was deleted.
     * @throws UnexistingEntityException   If the project does not exist.
     * @throws InvalidAuthentication       If the JWT is invalid.
     * @throws UnauthorizedActionException If the user is not an admin.
     */
    @Transactional
    public boolean deleteProject(String jwtToken, Long projectId) throws UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        authService.verifyAdminAccess(jwtToken);

        Optional<Project> relatedProject = this.projectRepository.findById(projectId);
        if (relatedProject.isPresent()) {
            this.projectRepository.delete(relatedProject.get());
            return true;
        } else {
            throw new UnexistingEntityException("The requested project does not exist");
        }
    }

    /**
     * Returns all projects. Any authenticated user may access this.
     *
     * @param jwtToken The JWT used for authentication.
     * @return A list of all projects.
     * @throws InvalidAuthentication If authentication fails.
     */
    public List<Project> getAllProjects(String jwtToken) throws InvalidAuthentication {
        authService.getAuthData(jwtToken);
        return projectRepository.findAll();
    }

    /**
     * Helper method that verifies whether a project already exists based on its number.
     *
     * @param projectDTO The project DTO to validate.
     * @return true if validation succeeds.
     * @throws UnexistingEntityException If a project with the same number already exists.
     */
    private boolean verifyEntriesFrom(ProjectDTO projectDTO) throws UnexistingEntityException {
        Optional<Project> isProjectExisting = projectRepository.findByNumber(projectDTO.getNumber());
        if (isProjectExisting.isPresent()) {
            throw new UnexistingEntityException("Project already exists");
        }
        return true;
    }
}