package lu.lamtco.timelink.rws;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lu.lamtco.timelink.dto.EmployeeDTO;
import lu.lamtco.timelink.exeptions.InvalidAuthentication;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnauthorizedActionException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.business.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lu.lamtco.timelink.domain.Employee;

import java.util.List;

/**
 * REST controller for managing employees.
 * Provides endpoints to create, read, update, and delete employees.
 *
 * @version 0.1
 */
@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employees", description = "Operations for managing employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Retrieves all employees in the system.
     *
     * @return List of all employees.
     */
    @Operation(summary = "Get all employees", description = "Retrieve a list of all employees in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class)))
    })
    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAllEmployees();
    }

    /**
     * Creates a new employee.
     *
     * @param jwtToken   JWT token for authentication.
     * @param newEmployee Employee data to create.
     * @return Created employee or appropriate HTTP status if creation fails.
     */
    @Operation(summary = "Create a new employee", description = "Add a new employee to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "498", description = "Invalid JWT token")
    })
    @PostMapping
    public ResponseEntity<Employee> create(@RequestHeader String jwtToken, @RequestBody EmployeeDTO newEmployee) {
        try {
            Employee created = employeeService.createEmployee(jwtToken, newEmployee);
            if (created == null) return ResponseEntity.badRequest().build();
            return ResponseEntity.ok(created);
        } catch (NonConformRequestedDataException e) {
            return ResponseEntity.badRequest().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Retrieves an employee by their unique ID.
     *
     * @param jwtToken JWT token for authentication.
     * @param id       ID of the employee.
     * @return Employee or appropriate HTTP status if not found or unauthorized.
     */
    @Operation(summary = "Get employee by ID", description = "Retrieve a single employee by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "498", description = "Invalid JWT token")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@RequestHeader String jwtToken, @PathVariable Long id) {
        try {
            Employee employee = employeeService.getEmployeeById(jwtToken, id);
            return ResponseEntity.ok(employee);
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Updates an existing employee by ID.
     *
     * @param jwtToken JWT token for authentication.
     * @param id       ID of the employee to update.
     * @param updated  Updated employee data.
     * @return Updated employee or appropriate HTTP status if update fails.
     */
    @Operation(summary = "Update employee by ID", description = "Update the details of an existing employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "498", description = "Invalid JWT token")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@RequestHeader String jwtToken, @PathVariable Long id, @RequestBody EmployeeDTO updated) {
        try {
            Employee employee = employeeService.updateEmployee(jwtToken, id, updated);
            return ResponseEntity.ok(employee);
        } catch (NonConformRequestedDataException e) {
            return ResponseEntity.badRequest().build();
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param jwtToken JWT token for authentication.
     * @param id       ID of the employee to delete.
     * @return True if deletion succeeded or appropriate HTTP status if failed.
     */
    @Operation(summary = "Delete employee by ID", description = "Remove an employee from the system by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "498", description = "Invalid JWT token")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@RequestHeader String jwtToken, @PathVariable Long id) {
        try {
            boolean deleted = employeeService.deleteEmployee(jwtToken, id);
            return ResponseEntity.ok(deleted);
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
    }
}