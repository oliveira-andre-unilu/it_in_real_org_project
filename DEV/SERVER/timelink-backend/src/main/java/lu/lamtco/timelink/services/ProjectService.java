package lu.lamtco.timelink.services;

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

    @Transactional
    public Project createProject(String jwtToken,ProjectDTO newProject) throws UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        //Verifying admin access
        authService.verifyAdminAccess(jwtToken);

        //Finding the related customer
        Optional<Customer> relatedCustomer = this.customerRepository.findById(newProject.getCostumerId());

        if(relatedCustomer.isPresent()) {

            this.verifyEntriesFrom(newProject);

            Customer customer = relatedCustomer.get();
            Project project = new Project();
            project.setCustomer(customer);
            project.setName(newProject.getName());
            project.setNumber(newProject.getNumber());
            project.setLocation(newProject.getLocation());
            return projectRepository.save(project);
        }else{
            throw new UnexistingEntityException("The related costumer does not exist!!!");
        }
    }

    @Transactional
    public Project updateProject(String jwtToken, ProjectDTO newProject, Long projectId) throws InvalidAuthentication, UnauthorizedActionException, UnexistingEntityException {
        //Verify admin access
        authService.verifyAdminAccess(jwtToken);

        Optional<Project> relatedProject = this.projectRepository.findById(projectId);

        if(relatedProject.isPresent()) {
            Project project = relatedProject.get();
            if(project.getCustomer()!=null){
                Optional<Customer> relatedCustomer = this.customerRepository.findById(newProject.getCostumerId());
                if(relatedCustomer.isPresent()) {
                    Customer customer = relatedCustomer.get();
                    project.setCustomer(customer);
                }else{
                    throw new UnexistingEntityException("The related costumer does not exist!!!");
                }
            }
            if(project.getName()!=null){
                project.setName(newProject.getName());
            }
            if(project.getNumber()!=null){
                project.setNumber(newProject.getNumber());
            }
            if(project.getLocation()!=null){
                project.setLocation(newProject.getLocation());
            }
            return projectRepository.save(project);
        }else{
            throw new UnexistingEntityException("The requested project does not exist!!!");
        }
    }

    public Project getProject(String jwtToken, Long projectId) throws UnexistingEntityException, InvalidAuthentication {
        //Simple authentication made
        authService.getAuthData(jwtToken);

        Optional<Project> relatedProject = this.projectRepository.findById(projectId);
        if(relatedProject.isPresent()) {
            return relatedProject.get();
        }else{
            throw new UnexistingEntityException("The requested project does not exist");
        }
    }

    @Transactional
    public boolean deleteProject(String jwtToken, Long projectId) throws UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        //Verify admin access
        authService.verifyAdminAccess(jwtToken);

        Optional<Project> relatedProject = this.projectRepository.findById(projectId);
        if(relatedProject.isPresent()) {
            this.projectRepository.delete(relatedProject.get());
            return true;
        }else{
            throw new UnexistingEntityException("The requested project does not exist");
        }
    }

    public List<Project> getAllProjects(String jwtToken) throws InvalidAuthentication {
        authService.getAuthData(jwtToken);
        return projectRepository.findAll();
    }

    //Helper methods

    private boolean verifyEntriesFrom(ProjectDTO projectDTO) throws UnexistingEntityException {
        //Verifying if project does not already exist
        Optional<Project> isProjectExisting = projectRepository.findByNumber(projectDTO.getNumber());
        if(isProjectExisting.isPresent()) {
            throw new UnexistingEntityException("Project already exists");
        }
        return true;
    }


}
