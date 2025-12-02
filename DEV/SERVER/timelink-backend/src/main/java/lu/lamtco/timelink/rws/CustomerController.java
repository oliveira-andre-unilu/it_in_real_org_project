package lu.lamtco.timelink.rws;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lu.lamtco.timelink.dto.CustomerDTO;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lu.lamtco.timelink.domain.Customer;
import lu.lamtco.timelink.persister.CustomerRepository;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customers", description = "Operations for managing customers")
public class CustomerController {

//    private final CustomerRepository repository;

    private final CustomerService service;

    public CustomerController(CustomerService service) {
    //    this.repository = repository;
        this.service = service;
    }

    @Operation(summary = "Get all customers", description = "Retrieve a list of all customers in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)))
    })
    @GetMapping
    public List<Customer> getAll() {
        return service.findAllCustomers();
    }

    @Operation(summary = "Create a new customer", description = "Add a new customer to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity")
    })
    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody CustomerDTO newCustomer) {
        //CHECKS: Data verification

        Customer result;

        try{
            result = service.createCustomer(newCustomer);
        } catch (NonConformRequestedDataException e) {
            return ResponseEntity.unprocessableEntity().build();
        }

        if(result == null) {
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok(result);
        }
    }

    @Operation(summary = "Get customer by ID", description = "Retrieve a single customer by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Long id) {
        Customer result = service.findCustomerById(id);
        if(result == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(result);
        }

        // Old code
        //        return repository.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update customer by ID", description = "Update the details of an existing customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "422", description = "Unprocessable input entity")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody CustomerDTO updated) {
        //CHECKS: Data verification

        Customer result;

        try {
            result = service.updateCustomer(id, updated);
        } catch (NonConformRequestedDataException e) {
            return ResponseEntity.unprocessableEntity().build();
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        }

        if(result == null) {
            return ResponseEntity.internalServerError().build();
        }else{
            return ResponseEntity.ok(result);
        }

        // Old code
        //        return repository.findById(id)
//                .map(c -> {
//                    c.setCompanyName(updated.getCompanyName());
//                    c.setTaxNumber(updated.getTaxNumber());
//                    c.setEmail(updated.getEmail());
//                    c.setTel(updated.getTel());
//                    c.setFax(updated.getFax());
//                    return ResponseEntity.ok(repository.save(c));
//                })
//                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete customer by ID", description = "Remove a customer from the system by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean result;
        try {
            result = service.deleteCustomer(id);
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);

        // Old code
//        return repository.findById(id)
//                .map(c -> {
//                    repository.delete(c);
//                    return ResponseEntity.noContent().build();
//                })
//                .orElse(ResponseEntity.notFound().build());
    }
}