package lu.lamtco.timelink.services;

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

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private AuthService authService;

    public CustomerService(CustomerRepository customerRepository, AuthService authService) {
        this.customerRepository = customerRepository;
        this.authService = authService;
    }


    //Methods used in the rws
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

    public List<Customer> findAllCustomers(String jwtToken) throws InvalidAuthentication, UnauthorizedActionException {
        authService.getAuthData(jwtToken);

        try{
            return customerRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Customer findCustomerById(String jwtToken, long id) throws InvalidAuthentication, UnauthorizedActionException {
        authService.getAuthData(jwtToken);
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()) {
            return customer.get();
        }else{
            return null;
        }
    }

    @Transactional
    public Customer updateCustomer(String jwtToken, long id, CustomerDTO newCustomer) throws NonConformRequestedDataException, UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        this.verifyAdminAccess(jwtToken);

        //Verifying parameters
        Customer customer = this.findCustomerById(jwtToken, id); //verification done at this stage
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

    @Transactional
    public boolean deleteCustomer(String jwtToken, long id) throws UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        this.verifyAdminAccess(jwtToken);

        Customer customer = this.findCustomerById(jwtToken, id); //verification done at this stage
        if(customer == null) {
            throw new UnexistingEntityException("The requested Custumer does not existi!");
        }

        customerRepository.delete(customer);
        return true;
    }

    // Helper functions
    private boolean isCostumerEntryConform(CustomerDTO customerDTO) {
        //Verifying all other data conformity
        return this.verifyEmail(customerDTO.getEmail()) && this.verifyPhoneNumber(customerDTO.getTel()) && this.verifyPhoneNumber(customerDTO.getFax());
    }

    private boolean verifyEmail(String email) {
        //Verifying is user does not already exist (by name)
        Optional<Customer> existingCustomer = customerRepository.findByEmail(email);
        if (existingCustomer.isPresent()) {
            return false;
        }

        if(!email.contains("@")){
            return false;
        }
        return true;
    }
    private boolean verifyPhoneNumber(String phoneNumber) {
        try{
            if(phoneNumber.charAt(0)=='+'){
                Long.parseLong(phoneNumber.substring(1));
            }else{
                Long.parseLong(phoneNumber);
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void verifyAdminAccess(String jwtToken) throws UnauthorizedActionException, InvalidAuthentication {
        UserAuthData authData = authService.getAuthData(jwtToken);
        if(authData.role()!= Role.ADMIN){
            throw new UnauthorizedActionException();
        }
    }
}
