package lu.lamtco.timelink.persister;

import lu.lamtco.timelink.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Customer} entities.
 * Provides CRUD operations and custom query methods for customers.
 *
 * @version 0.1
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Finds a customer by their email address.
     *
     * @param email the email of the customer to search for
     * @return an {@link Optional} containing the found {@link Customer}, or empty if no customer is found
     */
    Optional<Customer> findByEmail(String email);
}