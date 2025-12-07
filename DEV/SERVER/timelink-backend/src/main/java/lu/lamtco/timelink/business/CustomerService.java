package lu.lamtco.timelink.business;

import lu.lamtco.timelink.domain.Customer;
import lu.lamtco.timelink.domain.Role;
import lu.lamtco.timelink.dto.CustomerDTO;
import lu.lamtco.timelink.exeptions.InvalidAuthentication;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnauthorizedActionException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.persister.CustomerRepository;
import lu.lamtco.timelink.security.AuthService;
import lu.lamtco.timelink.security.UserAuthData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Customer entities.
 * Handles creation, retrieval, update, and deletion of customers.
 * Validates user access and data conformity.
 *
 * @version 0.1
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuthService authService;

    /**
     * Constructor for CustomerService.
     * @param customerRepository Repository for customer persistence
     * @param authService Service for authentication and authorization
     */
    public CustomerService(CustomerRepository customerRepository, AuthService authService) {
        this.customerRepository = customerRepository;
        this.authService = authService;
    }

    /**
     * Creates a new customer.
     * Only ADMIN users can create a customer.
     * @param newCustomer DTO containing customer data
     * @param jwtToken JWT token for authentication
     * @return Created Customer entity
     * @throws NonConformRequestedDataException If data is invalid
     * @throws InvalidAuthentication If JWT token is invalid
     * @throws UnauthorizedActionException If user is not an ADMIN
     */
    @Transactional
    public Customer createCustomer(CustomerDTO newCustomer, String jwtToken) throws NonConformRequestedDataException, InvalidAuthentication, UnauthorizedActionException {
        this.verifyAdminAccess(jwtToken);

        if(this.isCostumerEntryConform(newCustomer)) {
            throw new NonConformRequestedDataException("The requested data was not conform to the requirements");
        }

        Customer customer = new Customer();
        customer.setCompanyName(newCustomer.getCompanyName());
        customer.setTaxNumber(newCustomer.getTaxNumber());
        customer.setEmail(newCustomer.getEmail());
        customer.setTel(newCustomer.getTel());
        customer.setFax(newCustomer.getFax());

        return customerRepository.save(customer);
    }

    /**
     * Retrieves all customers.
     * @param jwtToken JWT token for authentication
     * @return List of customers, empty list if none exist
     * @throws InvalidAuthentication If JWT token is invalid
     * @throws UnauthorizedActionException If user is not authorized
     */
    public List<Customer> findAllCustomers(String jwtToken) throws InvalidAuthentication, UnauthorizedActionException {
        authService.getAuthData(jwtToken);

        try {
            return customerRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Finds a customer by ID.
     * @param jwtToken JWT token for authentication
     * @param id ID of the customer
     * @return Customer entity if found, null otherwise
     * @throws InvalidAuthentication If JWT token is invalid
     * @throws UnauthorizedActionException If user is not authorized
     */
    public Customer findCustomerById(String jwtToken, long id) throws InvalidAuthentication, UnauthorizedActionException {
        authService.getAuthData(jwtToken);
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElse(null);
    }

    /**
     * Updates an existing customer.
     * Only ADMIN users can update a customer.
     * @param jwtToken JWT token for authentication
     * @param id ID of the customer to update
     * @param newCustomer DTO containing updated customer data
     * @return Updated Customer entity
     * @throws NonConformRequestedDataException If updated data is invalid
     * @throws UnexistingEntityException If customer does not exist
     * @throws InvalidAuthentication If JWT token is invalid
     * @throws UnauthorizedActionException If user is not an ADMIN
     */
    @Transactional
    public Customer updateCustomer(String jwtToken, long id, CustomerDTO newCustomer) throws NonConformRequestedDataException, UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        this.verifyAdminAccess(jwtToken);

        Customer customer = this.findCustomerById(jwtToken, id);
        if(customer == null) {
            throw new UnexistingEntityException("Customer with id " + id + " not found");
        }

        if(newCustomer.getCompanyName()!=null) {
            customer.setCompanyName(newCustomer.getCompanyName());
        }
        if(newCustomer.getTaxNumber()!=null) {
            customer.setTaxNumber(newCustomer.getTaxNumber());
        }
        if(newCustomer.getEmail()!=null) {
            if(!this.verifyEmail(newCustomer.getEmail())) {
                throw new NonConformRequestedDataException("The new email is not conform or is already taken by someone else");
            }
            customer.setEmail(newCustomer.getEmail());
        }
        if(newCustomer.getTel()!=null) {
            if(!this.verifyPhoneNumber(newCustomer.getTel())) {
                throw new NonConformRequestedDataException("The new phone number is not conform to the requirements");
            }
            customer.setTel(newCustomer.getTel());
        }
        if(newCustomer.getFax()!=null) {
            if(!this.verifyPhoneNumber(newCustomer.getFax())) {
                throw new NonConformRequestedDataException("The new fax is not conform to the requirements");
            }
            customer.setFax(newCustomer.getFax());
        }
        return customerRepository.save(customer);
    }

    /**
     * Deletes a customer by ID.
     * Only ADMIN users can delete a customer.
     * @param jwtToken JWT token for authentication
     * @param id ID of the customer to delete
     * @return True if deletion was successful
     * @throws UnexistingEntityException If customer does not exist
     * @throws InvalidAuthentication If JWT token is invalid
     * @throws UnauthorizedActionException If user is not an ADMIN
     */
    @Transactional
    public boolean deleteCustomer(String jwtToken, long id) throws UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        this.verifyAdminAccess(jwtToken);

        Customer customer = this.findCustomerById(jwtToken, id);
        if(customer == null) {
            throw new UnexistingEntityException("The requested Customer does not exist!");
        }

        customerRepository.delete(customer);
        return true;
    }

    // Helper methods

    /**
     * Verifies if a customer DTO conforms to expected data rules.
     * @param customerDTO CustomerDTO to validate
     * @return True if all fields are valid
     */
    private boolean isCostumerEntryConform(CustomerDTO customerDTO) {
        return this.verifyEmail(customerDTO.getEmail()) && this.verifyPhoneNumber(customerDTO.getTel()) && this.verifyPhoneNumber(customerDTO.getFax());
    }

    /**
     * Verifies if an email is valid and not already used.
     * @param email Email to check
     * @return True if email is valid and free
     */
    private boolean verifyEmail(String email) {
        Optional<Customer> existingCustomer = customerRepository.findByEmail(email);
        if (existingCustomer.isPresent() || !email.contains("@")) {
            return false;
        }
        return true;
    }

    /**
     * Verifies if a phone number is valid.
     * @param phoneNumber Phone number to check
     * @return True if the number is valid
     */
    private boolean verifyPhoneNumber(String phoneNumber) {
        try {
            if(phoneNumber.charAt(0) == '+') {
                Long.parseLong(phoneNumber.substring(1));
            } else {
                Long.parseLong(phoneNumber);
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Verifies that the user associated with the JWT token has ADMIN access.
     * @param jwtToken JWT token for authentication
     * @throws UnauthorizedActionException If user is not an ADMIN
     * @throws InvalidAuthentication If token is invalid
     */
    private void verifyAdminAccess(String jwtToken) throws UnauthorizedActionException, InvalidAuthentication {
        UserAuthData authData = authService.getAuthData(jwtToken);
        if(authData.role() != Role.ADMIN){
            throw new UnauthorizedActionException();
        }
    }
}