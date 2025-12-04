package lu.lamtco.timelink.services;

import lu.lamtco.timelink.domain.Customer;
import lu.lamtco.timelink.dto.CustomerDTO;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.persister.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    //Methods used in the rws
    @Transactional
    public Customer createCustomer(CustomerDTO newCustomer) throws NonConformRequestedDataException {
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

    public List<Customer> findAllCustomers() {
        try{
            return customerRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Customer findCustomerById(long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()) {
            return customer.get();
        }else{
            return null;
        }
    }

    @Transactional
    public Customer updateCustomer(long id, CustomerDTO newCustomer) throws NonConformRequestedDataException , UnexistingEntityException {

        //Verifying parameters
        Customer customer = this.findCustomerById(id);
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
    public boolean deleteCustomer(long id) throws UnexistingEntityException {
        Customer customer = this.findCustomerById(id);
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
}
