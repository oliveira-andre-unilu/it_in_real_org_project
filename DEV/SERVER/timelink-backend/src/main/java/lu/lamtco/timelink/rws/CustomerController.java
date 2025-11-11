package lu.lamtco.timelink.rws;

import lu.lamtco.timelink.domain.Customer;
import lu.lamtco.timelink.persister.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    
    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Customer> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        return repository.save(customer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer updated) {
        return repository.findById(id)
                .map(c -> {
                    c.setCompanyName(updated.getCompanyName());
                    c.setTaxNumber(updated.getTaxNumber());
                    c.setEmail(updated.getEmail());
                    c.setTel(updated.getTel());
                    c.setFax(updated.getFax());
                    return ResponseEntity.ok(repository.save(c));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return repository.findById(id)
                .map(c -> { repository.delete(c); return ResponseEntity.noContent().build(); })
                .orElse(ResponseEntity.notFound().build());
    }
}

