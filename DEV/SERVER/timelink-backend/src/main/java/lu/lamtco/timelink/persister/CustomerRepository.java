package lu.lamtco.timelink.persister;

import lu.lamtco.timelink.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}
