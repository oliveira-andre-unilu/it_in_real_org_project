package lu.lamtco.timelink.persister;

import lu.lamtco.timelink.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Optional<Customer> findByEmail(String email);

    public Optional<Customer> findByTaxNumber(String taxNumber);
}
