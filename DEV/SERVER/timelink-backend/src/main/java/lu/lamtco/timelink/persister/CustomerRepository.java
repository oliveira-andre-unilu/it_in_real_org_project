package lu.lamtco.timelink.persister;

import lu.lamtco.timelink.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@link Customer} entities.
 * Extends {@link JpaRepository} to provide standard data access methods.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Finds a customer by their email address.
     *
     * @param email the email of the customer to find
     * @return an {@link Optional} containing the {@link Customer} if found, or empty if not found
     */
    Optional<Customer> findByEmail(String email);
}