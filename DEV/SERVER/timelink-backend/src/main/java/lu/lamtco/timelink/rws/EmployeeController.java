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
import lu.lamtco.timelink.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.persister.EmployeeRepository;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employees", description = "Operations for managing employees")
public class EmployeeController {

//    private final EmployeeRepository repository;

    private final EmployeeService employeeService;


    public EmployeeController(/*EmployeeRepository repository,*/ EmployeeService employeeService) {
    //    this.repository = repository;
        this.employeeService = employeeService;
    }

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

    @Operation(summary = "Create a new employee", description = "Add a new employee to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Employee> create(@RequestHeader String jwtToken, @RequestBody EmployeeDTO newEmployee) {
        //CHECKS: Data verification
        Employee created;
        try {
            created = employeeService.createEmployee(jwtToken,newEmployee);
        } catch (NonConformRequestedDataException e) {
            return ResponseEntity.badRequest().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
        if(created == null) {
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok(created);
        }
    }

    @Operation(summary = "Get employee by ID", description = "Retrieve a single employee by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@RequestHeader String jwtToken, @PathVariable Long id) {
        Employee employee;
        try {
            employee = employeeService.getEmployeeById(jwtToken, id);
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(employee);
        //Old code
//        return repository.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
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
    public ResponseEntity<Employee> update(@RequestHeader String jwtToken, @PathVariable Long id, @RequestBody EmployeeDTO updated) {
        Employee employee;
        try{
           employee = employeeService.updateEmployee(jwtToken, id, updated);
        } catch (NonConformRequestedDataException e) {
            return ResponseEntity.badRequest().build();
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(employee);

        // Old code
//        return repository.findById(id)
//                .map(e -> {
//                    e.setName(updated.getName());
//                    e.setSurname(updated.getSurName());
//                    e.setEmail(updated.getEmail());
//                    e.setPassword(updated.getPassword());
//                    e.setRole(updated.getRole());
//                    e.setHourlyRate(updated.getHourlyRate());
//                    return ResponseEntity.ok(repository.save(e));
//                })
//                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete employee by ID", description = "Remove an employee from the system by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@RequestHeader String jwtToken, @PathVariable Long id) {
        boolean deleted;
        try{
            deleted = employeeService.deleteEmployee(jwtToken, id);
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(deleted);
        // Old code
//        return repository.findById(id)
//                .map(e -> {
//                    repository.delete(e);
//                    return ResponseEntity.noContent().build();
//                })
//                .orElse(ResponseEntity.notFound().build());
    }
}