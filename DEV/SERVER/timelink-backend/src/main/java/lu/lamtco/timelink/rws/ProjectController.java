package lu.lamtco.timelink.rws;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lu.lamtco.timelink.dto.ProjectDTO;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.services.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lu.lamtco.timelink.domain.Project;
import lu.lamtco.timelink.persister.ProjectRepository;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@Tag(name = "Projects", description = "Operations for managing projects")
public class ProjectController {

    private final ProjectRepository repository;
    private final ProjectService service;

    public ProjectController(ProjectRepository repository, ProjectService service) {
        this.repository = repository;
        this.service = service;
    }

    @Operation(summary = "Get all projects", description = "Retrieve a list of all projects in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class)))
    })
    @GetMapping
    public List<Project> getAll() {
        return repository.findAll();
    }

    @Operation(summary = "Create a new project", description = "Add a new project to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Project> create(@RequestBody ProjectDTO newProject) {
        Project result = null;
        try {
            result = service.createProject(newProject);
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        }
        if(result == null) {
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok(result);
        }
    }

    @Operation(summary = "Get project by ID", description = "Retrieve a single project by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Project> getById(@PathVariable Long id) {
        Project project;
        try{
            project = service.getProject(id);
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(project);
        //Old code
//        return repository.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update project by ID", description = "Update the details of an existing project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Project> update(@PathVariable Long id, @RequestBody ProjectDTO updated) {

        //CHECKS: Data verification
        Project response = service.updateProject(updated, id);

        if(response == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(response);
        }

//        return repository.findById(id)
//                .map(p -> {
//                    p.setName(updated.getName());
//                    p.setNumber(updated.getNumber());
//                    p.setLocation(updated.getLocation());
//                    p.setCustomer(updated.getCustomer());
//                    return ResponseEntity.ok(repository.save(p));
//                })
//                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete project by ID", description = "Remove a project from the system by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Project successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean result = false;
        try{
            result = service.deleteProject(id);
        } catch (UnexistingEntityException e) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
        //Old code
//        return repository.findById(id)
//                .map(p -> {
//                    repository.delete(p);
//                    return ResponseEntity.noContent().build();
//                })
//                .orElse(ResponseEntity.notFound().build());
    }
}