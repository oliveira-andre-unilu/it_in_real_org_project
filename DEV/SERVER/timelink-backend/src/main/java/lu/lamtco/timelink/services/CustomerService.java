package lu.lamtco.timelink.services;

import lu.lamtco.timelink.domain.Customer;
import lu.lamtco.timelink.dto.CustomerDTO;
import lu.lamtco.timelink.persister.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer createCustomer(CustomerDTO newCustomer) {
        Customer customer = new Customer();
        customer.setCompanyName(newCustomer.getCompanyName());
        customer.setTaxNumber(newCustomer.getTaxNumber());
        customer.setEmail(newCustomer.getEmail());
        customer.setTel(newCustomer.getTel());
        customer.setFax(newCustomer.getFax());

        return customerRepository.save(customer);
    }
}
