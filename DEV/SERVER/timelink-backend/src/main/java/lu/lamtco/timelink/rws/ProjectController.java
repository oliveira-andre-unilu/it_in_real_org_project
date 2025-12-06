package lu.lamtco.timelink.rws;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lu.lamtco.timelink.dto.ProjectDTO;
import lu.lamtco.timelink.exeptions.InvalidAuthentication;
import lu.lamtco.timelink.exeptions.UnauthorizedActionException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.business.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lu.lamtco.timelink.domain.Project;

import java.util.List;

/**
 * REST controller for managing projects.
 * Provides endpoints to create, read, update, and delete projects.
 *
 * @version 0.1
 */
@RestController
@RequestMapping("/api/projects")
@Tag(name = "Projects", description = "Operations for managing projects")
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @Operation(summary = "Get all projects", description = "Retrieve a list of all projects in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "498", description = "Invalid JWT token")
    })
    @GetMapping
    public ResponseEntity<List<Project>> getAll(@RequestHeader String jwtToken) {
        try {
            return ResponseEntity.ok(service.getAllProjects(jwtToken));
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        }
    }

    @Operation(summary = "Create a new project", description = "Add a new project to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "498", description = "Invalid JWT token"),
            @ApiResponse(responseCode = "404", description = "Referenced entity not found")
    })
    @PostMapping
    public ResponseEntity<Project> create(@RequestHeader String jwtToken, @RequestBody ProjectDTO newProject) {
        try {
            Project result = service.createProject(jwtToken, newProject);
            if (result == null) return ResponseEntity.badRequest().build();
            return ResponseEntity.ok(result);
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @Operation(summary = "Get project by ID", description = "Retrieve a single project by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "498", description = "Invalid JWT token")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Project> getById(@RequestHeader String jwtToken, @PathVariable Long id) {
        try {
            Project project = service.getProject(jwtToken, id);
            return ResponseEntity.ok(project);
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        }
    }

    @Operation(summary = "Update project by ID", description = "Update the details of an existing project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "498", description = "Invalid JWT token")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Project> update(@RequestHeader String jwtToken, @PathVariable Long id, @RequestBody ProjectDTO updated) {
        try {
            Project response = service.updateProject(jwtToken, updated, id);
            return ResponseEntity.ok(response);
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @Operation(summary = "Delete project by ID", description = "Remove a project from the system by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Project successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "498", description = "Invalid JWT token")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@RequestHeader String jwtToken, @PathVariable Long id) {
        try {
            boolean result = service.deleteProject(jwtToken, id);
            return ResponseEntity.ok(result);
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
    }
}