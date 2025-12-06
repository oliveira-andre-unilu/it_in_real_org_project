package lu.lamtco.timelink.rws;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lu.lamtco.timelink.dto.CustomerDTO;
import lu.lamtco.timelink.exeptions.InvalidAuthentication;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnauthorizedActionException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lu.lamtco.timelink.domain.Customer;

import java.util.List;

/**
 * REST controller for managing customers.
 * Provides endpoints to create, read, update, and delete customers.
 *
 * @version 0.1
 */
@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customers", description = "Operations for managing customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    /**
     * Retrieves all customers in the system.
     *
     * @param jwtToken JWT token for authentication.
     * @return List of customers or appropriate HTTP status if authentication fails.
     */
    @Operation(summary = "Get all customers", description = "Retrieve a list of all customers in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "498", description = "Invalid authentication token")
    })
    @GetMapping
    public ResponseEntity<List<Customer>> getAll(@RequestHeader String jwtToken) {
        try {
            return ResponseEntity.ok(service.findAllCustomers(jwtToken));
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Creates a new customer.
     *
     * @param jwtToken    JWT token for authentication.
     * @param newCustomer Customer data to create.
     * @return Created customer or appropriate HTTP status if creation fails.
     */
    @Operation(summary = "Create a new customer", description = "Add a new customer to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity"),
            @ApiResponse(responseCode = "498", description = "Invalid authentication token")
    })
    @PostMapping
    public ResponseEntity<Customer> create(@RequestHeader String jwtToken, @RequestBody CustomerDTO newCustomer) {
        try {
            Customer result = service.createCustomer(newCustomer, jwtToken);
            if (result == null) return ResponseEntity.badRequest().build();
            return ResponseEntity.ok(result);
        } catch (NonConformRequestedDataException e) {
            return ResponseEntity.unprocessableEntity().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Retrieves a customer by their unique ID.
     *
     * @param jwtToken JWT token for authentication.
     * @param id       ID of the customer.
     * @return Customer or appropriate HTTP status if not found or unauthorized.
     */
    @Operation(summary = "Get customer by ID", description = "Retrieve a single customer by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "498", description = "Invalid authentication token")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@RequestHeader String jwtToken, @PathVariable Long id) {
        try {
            Customer result = service.findCustomerById(jwtToken, id);
            return result == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Updates an existing customer by ID.
     *
     * @param jwtToken JWT token for authentication.
     * @param id       ID of the customer to update.
     * @param updated  Updated customer data.
     * @return Updated customer or appropriate HTTP status if update fails.
     */
    @Operation(summary = "Update customer by ID", description = "Update the details of an existing customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "422", description = "Unprocessable input entity"),
            @ApiResponse(responseCode = "498", description = "Invalid authentication token")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@RequestHeader String jwtToken, @PathVariable Long id, @RequestBody CustomerDTO updated) {
        try {
            Customer result = service.updateCustomer(jwtToken, id, updated);
            if (result == null) return ResponseEntity.internalServerError().build();
            return ResponseEntity.ok(result);
        } catch (NonConformRequestedDataException e) {
            return ResponseEntity.unprocessableEntity().build();
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Deletes a customer by their ID.
     *
     * @param jwtToken JWT token for authentication.
     * @param id       ID of the customer to delete.
     * @return True if deletion succeeded or appropriate HTTP status if failed.
     */
    @Operation(summary = "Delete customer by ID", description = "Remove a customer from the system by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer successfully deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "498", description = "Invalid authentication token")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@RequestHeader String jwtToken, @PathVariable Long id) {
        try {
            boolean result = service.deleteCustomer(jwtToken, id);
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