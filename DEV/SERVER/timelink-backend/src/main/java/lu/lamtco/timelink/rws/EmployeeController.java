package lu.lamtco.timelink.rws;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.persister.EmployeeRepository;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employees", description = "Operations for managing employees")
public class EmployeeController {

    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Get all employees", description = "Retrieve a list of all employees in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class)))
    })
    @GetMapping
    public List<Employee> getAll() {
        return repository.findAll();
    }

    @Operation(summary = "Create a new employee", description = "Add a new employee to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        return repository.save(employee);
    }

    @Operation(summary = "Get employee by ID", description = "Retrieve a single employee by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update employee by ID", description = "Update the details of an existing employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee updated) {
        return repository.findById(id)
                .map(e -> {
                    e.setName(updated.getName());
                    e.setSurname(updated.getSurname());
                    e.setEmail(updated.getEmail());
                    e.setPassword(updated.getPassword());
                    e.setRole(updated.getRole());
                    e.setHourlyRate(updated.getHourlyRate());
                    return ResponseEntity.ok(repository.save(e));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete employee by ID", description = "Remove an employee from the system by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return repository.findById(id)
                .map(e -> {
                    repository.delete(e);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}