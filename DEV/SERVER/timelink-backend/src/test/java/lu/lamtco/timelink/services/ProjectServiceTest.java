package lu.lamtco.timelink.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lu.lamtco.timelink.domain.Customer;
import lu.lamtco.timelink.domain.Project;
import lu.lamtco.timelink.dto.ProjectDTO;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.persister.CustomerRepository;
import lu.lamtco.timelink.persister.ProjectRepository;

public class ProjectServiceTest {
    private ProjectRepository projectRepository;
    private CustomerRepository customerRepository;
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectRepository = mock(ProjectRepository.class);
        customerRepository = mock(CustomerRepository.class);
        projectService = new ProjectService(projectRepository, customerRepository);
    }

    // create project, should work
    @Test
    void createProject_ShouldSaveProject_WhenCustomerExists() throws UnexistingEntityException {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setCostumerId(1L);
        projectDTO.setName("Project");
        projectDTO.setNumber("PROJ-001");
        projectDTO.setLocation("Luxembourg");

        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(projectRepository.findByNumber("PROJ-001")).thenReturn(Optional.empty());
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Project result = projectService.createProject(projectDTO);

        assertNotNull(result);
        assertEquals("Project", result.getName());
        assertEquals("PROJ-001", result.getNumber());
        assertEquals("Luxembourg", result.getLocation());
        assertEquals(customer, result.getCustomer());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    // create project, customer not exist so should not work
    @Test
    void createProject_ShouldThrowException_WhenCustomerNotExists() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setCostumerId(999L);

        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(UnexistingEntityException.class, () -> projectService.createProject(projectDTO));
    }

    // create project with project number already exist, not work
    @Test
    void createProject_ShouldThrowException_WhenProjectNumberAlreadyExists() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setCostumerId(1L);
        projectDTO.setNumber("EXIST-001");

        Customer customer = new Customer();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(projectRepository.findByNumber("EXIST-001")).thenReturn(Optional.of(new Project()));

        assertThrows(UnexistingEntityException.class, () -> projectService.createProject(projectDTO));
    }

    // update project, whould work
    @Test
    void updateProject_ShouldUpdateProject_WhenBothExist() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setCostumerId(1L);
        projectDTO.setName("Updated");
        projectDTO.setNumber("PROJ-002");
        projectDTO.setLocation("Updated");

        Customer customer = new Customer();
        customer.setId(1L);

        Project existingProject = new Project();
        existingProject.setId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(existingProject));
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Project result = projectService.updateProject(projectDTO, 1L);

        assertNotNull(result);
        assertEquals("Updated", result.getName());
        assertEquals("PROJ-002", result.getNumber());
        assertEquals("Updated", result.getLocation());
        assertEquals(customer, result.getCustomer());
    }

    // update project, customer not existing so must return null
    @Test
    void updateProject_ShouldReturnNull_WhenCustomerNotExists() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setCostumerId(999L);

        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        Project result = projectService.updateProject(projectDTO, 1L);

        assertNull(result);
    }

    // get project, should work when it exist
    @Test
    void getProject_ShouldReturnProject_WhenExists() throws UnexistingEntityException {
        long projectId = 1L;
        Project expectedProject = new Project();
        expectedProject.setId(projectId);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(expectedProject));

        Project result = projectService.getProject(projectId);

        assertNotNull(result);
        assertEquals(projectId, result.getId());
    }

    // get project, not exsiting so throw exception
    @Test
    void getProject_ShouldThrowException_WhenNotExists() {
        long projectId = 999L;

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(UnexistingEntityException.class, () -> projectService.getProject(projectId));
    }

    // delete project, project exist, return true
    @Test
    void deleteProject_ShouldReturnTrue_WhenProjectExists() throws UnexistingEntityException {
        long projectId = 1L;
        Project project = new Project();
        project.setId(projectId);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        boolean result = projectService.deleteProject(projectId);

        assertTrue(result);
        verify(projectRepository, times(1)).delete(project);
    }

    // delete project, not existing, throw exception
    @Test
    void deleteProject_ShouldThrowException_WhenProjectNotExists() {
        long projectId = 999L;

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(UnexistingEntityException.class, () -> projectService.deleteProject(projectId));
    }

}
