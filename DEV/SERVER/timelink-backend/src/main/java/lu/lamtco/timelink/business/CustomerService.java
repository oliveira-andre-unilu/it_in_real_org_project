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
 * Service responsible for handling customer-related operations.
 * It provides methods to create, update, retrieve and delete customers,
 * including authorization checks and basic validation logic.
 *
 * @version 0.1
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuthService authService;

    public CustomerService(CustomerRepository customerRepository, AuthService authService) {
        this.customerRepository = customerRepository;
        this.authService = authService;
    }

    /**
     * Creates a new customer after verifying admin access and data conformity.
     *
     * @param newCustomer the customer data
     * @param jwtToken the JWT used for authentication
     * @return the saved customer entity
     * @throws NonConformRequestedDataException if provided data is invalid
     * @throws InvalidAuthentication if the token is invalid
     * @throws UnauthorizedActionException if the user is not allowed to perform this action
     */
    @Transactional
    public Customer createCustomer(CustomerDTO newCustomer, String jwtToken) throws NonConformRequestedDataException, InvalidAuthentication, UnauthorizedActionException {
        this.verifyAdminAccess(jwtToken);

        if (this.isCostumerEntryConform(newCustomer)) {
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
     * Returns a list of all customers.
     *
     * @param jwtToken token used for authentication
     * @return list of customers or empty list if an error occurs
     * @throws InvalidAuthentication if the token is invalid
     * @throws UnauthorizedActionException if the user is not authorized
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
     * Finds a customer by its ID.
     *
     * @param jwtToken token used for authentication
     * @param id customer ID
     * @return the customer or null if not found
     * @throws InvalidAuthentication if the token is invalid
     * @throws UnauthorizedActionException if the user is not authorized
     */
    public Customer findCustomerById(String jwtToken, long id) throws InvalidAuthentication, UnauthorizedActionException {
        authService.getAuthData(jwtToken);
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElse(null);
    }

    /**
     * Updates a customer's fields with the provided data.
     *
     * @param jwtToken token used for authentication
     * @param id customer ID
     * @param newCustomer updated customer data
     * @return the updated customer
     * @throws NonConformRequestedDataException if provided data is invalid
     * @throws UnexistingEntityException if the customer does not exist
     * @throws InvalidAuthentication if the token is invalid
     * @throws UnauthorizedActionException if the user is not authorized
     */
    @Transactional
    public Customer updateCustomer(String jwtToken, long id, CustomerDTO newCustomer) throws NonConformRequestedDataException, UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        this.verifyAdminAccess(jwtToken);

        Customer customer = this.findCustomerById(jwtToken, id);
        if (customer == null) {
            throw new UnexistingEntityException("Customer with id " + id + " not found");
        }

        if (newCustomer.getCompanyName() != null) {
            customer.setCompanyName(newCustomer.getCompanyName());
        }
        if (newCustomer.getTaxNumber() != null) {
            customer.setTaxNumber(newCustomer.getTaxNumber());
        }
        if (newCustomer.getEmail() != null) {
            if (!this.verifyEmail(newCustomer.getEmail())) {
                throw new NonConformRequestedDataException("The new email is not conform or is already taken by someone else");
            }
            customer.setEmail(newCustomer.getEmail());
        }
        if (newCustomer.getTel() != null) {
            if (!this.verifyPhoneNumber(newCustomer.getTel())) {
                throw new NonConformRequestedDataException("The new phone number is not conform to the requirements");
            }
            customer.setTel(newCustomer.getTel());
        }
        if (newCustomer.getFax() != null) {
            if (!this.verifyPhoneNumber(newCustomer.getFax())) {
                throw new NonConformRequestedDataException("The new fax is not conform to the requirements");
            }
            customer.setFax(newCustomer.getFax());
        }

        return customerRepository.save(customer);
    }

    /**
     * Deletes a customer by ID.
     *
     * @param jwtToken token used for authentication
     * @param id customer ID
     * @return true if deleted successfully
     * @throws UnexistingEntityException if the customer does not exist
     * @throws InvalidAuthentication if the token is invalid
     * @throws UnauthorizedActionException if the user is not authorized
     */
    @Transactional
    public boolean deleteCustomer(String jwtToken, long id) throws UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        this.verifyAdminAccess(jwtToken);

        Customer customer = this.findCustomerById(jwtToken, id);
        if (customer == null) {
            throw new UnexistingEntityException("The requested Customer does not exist!");
        }

        customerRepository.delete(customer);
        return true;
    }

    // Helper functions

    /**
     * Checks whether the provided customer data is valid.
     */
    private boolean isCostumerEntryConform(CustomerDTO customerDTO) {
        return this.verifyEmail(customerDTO.getEmail())
                && this.verifyPhoneNumber(customerDTO.getTel())
                && this.verifyPhoneNumber(customerDTO.getFax());
    }

    /**
     * Validates an email and ensures that it is not already taken.
     */
    private boolean verifyEmail(String email) {
        Optional<Customer> existingCustomer = customerRepository.findByEmail(email);
        if (existingCustomer.isPresent()) {
            return false;
        }
        return email.contains("@");
    }

    /**
     * Validates a phone or fax number.
     */
    private boolean verifyPhoneNumber(String phoneNumber) {
        try {
            if (phoneNumber.charAt(0) == '+') {
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
     * Ensures the caller has admin privileges.
     *
     * @param jwtToken token used for authentication
     * @throws UnauthorizedActionException if user is not admin
     * @throws InvalidAuthentication if token is invalid
     */
    private void verifyAdminAccess(String jwtToken) throws UnauthorizedActionException, InvalidAuthentication {
        UserAuthData authData = authService.getAuthData(jwtToken);
        if (authData.role() != Role.ADMIN) {
            throw new UnauthorizedActionException();
        }
    }
}