package lu.lamtco.timelink.services;

import lu.lamtco.timelink.domain.Customer;
import lu.lamtco.timelink.domain.Project;
import lu.lamtco.timelink.dto.ProjectDTO;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.persister.CustomerRepository;
import lu.lamtco.timelink.persister.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private CustomerRepository customerRepository;

    public ProjectService(ProjectRepository projectRepository, CustomerRepository customerRepository) {
        this.projectRepository = projectRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Project createProject(ProjectDTO newProject) throws UnexistingEntityException {
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
    public Project updateProject(ProjectDTO newProject, Long projectId) {
        Optional<Customer> relatedCustomer = this.customerRepository.findById(newProject.getCostumerId());
        Optional<Project> relatedProject = this.projectRepository.findById(projectId);

        if(relatedCustomer.isPresent() && relatedProject.isPresent()) {
            Customer customer = relatedCustomer.get();
            Project project = relatedProject.get();
            project.setCustomer(customer);
            project.setName(newProject.getName());
            project.setNumber(newProject.getNumber());
            project.setLocation(newProject.getLocation());
            return projectRepository.save(project);
        }else{
            return null;
        }
    }

    public Project getProject(Long projectId) throws UnexistingEntityException {
        Optional<Project> relatedProject = this.projectRepository.findById(projectId);
        if(relatedProject.isPresent()) {
            return relatedProject.get();
        }else{
            throw new UnexistingEntityException("The requested project does not exist");
        }
    }

    @Transactional
    public boolean deleteProject(Long projectId) throws UnexistingEntityException {
        Optional<Project> relatedProject = this.projectRepository.findById(projectId);
        if(relatedProject.isPresent()) {
            this.projectRepository.delete(relatedProject.get());
            return true;
        }else{
            throw new UnexistingEntityException("The requested project does not exist");
        }
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
