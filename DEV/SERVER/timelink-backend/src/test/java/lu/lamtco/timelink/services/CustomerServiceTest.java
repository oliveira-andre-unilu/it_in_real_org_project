package lu.lamtco.timelink.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

public class CustomerServiceTest {
    CustomerService customerService;
    CustomerRepository customerRepository;
    AuthService authService;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        authService = mock(AuthService.class);
        customerService = new CustomerService(customerRepository, authService);
    }

    // create customer with success
    @Test
    void createCustomer_ShouldSaveCustomer_WhenAdminAndDataConform()
            throws NonConformRequestedDataException, InvalidAuthentication, UnauthorizedActionException {
        String jwtToken = "adminToken";
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCompanyName("Company");
        customerDTO.setEmail("test@example.com");
        customerDTO.setFax("+352987654321");
        customerDTO.setTel("+352123456789");
        customerDTO.setTaxNumber("LU12345678");

        UserAuthData adminAuth = new UserAuthData("admin@test.com", 1L, Role.ADMIN);

        when(authService.getAuthData(jwtToken)).thenReturn(adminAuth);

        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Customer result = customerService.createCustomer(customerDTO, jwtToken);

        assertNotNull(result);
        assertEquals("Company", result.getCompanyName());
        assertEquals("test@example.com", result.getEmail());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    // create customer, not an admin so not working
    @Test
    void createCustomer_ShouldThrowUnauthorizedActionException_WhenNotAdmin() throws InvalidAuthentication {
        String jwtToken = "staffToken";
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmail("test@company.com");

        UserAuthData userAuth = new UserAuthData("staff@test.com", 2L, Role.STAFF);
        when(authService.getAuthData(jwtToken)).thenReturn(userAuth);

        assertThrows(UnauthorizedActionException.class, () -> customerService.createCustomer(customerDTO, jwtToken));
    }

    // create customer but invalid email so throw exception
    @Test
    void createCustomer_ShouldThrowNonConformRequestedDataException_WhenEmailInvalid() throws InvalidAuthentication {
        String jwtToken = "adminToken";
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmail("invalid-email"); // no @

        UserAuthData adminAuth = new UserAuthData("admin@test.com", 1L, Role.ADMIN);
        when(authService.getAuthData(jwtToken)).thenReturn(adminAuth);

        assertThrows(NonConformRequestedDataException.class,
                () -> customerService.createCustomer(customerDTO, jwtToken));
    }

    // find all customers with valid token return list
    @Test
    void findAllCustomers_ShouldReturnList_WhenTokenValid() throws InvalidAuthentication, UnauthorizedActionException {
        String jwtToken = "validToken";
        List<Customer> expectedCustomers = Arrays.asList(new Customer(), new Customer());

        UserAuthData authData = new UserAuthData("test@example.com", 1L, Role.STAFF);
        when(authService.getAuthData(jwtToken)).thenReturn(authData);
        when(customerRepository.findAll()).thenReturn(expectedCustomers);

        List<Customer> result = customerService.findAllCustomers(jwtToken);

        assertEquals(2, result.size());
        verify(authService, times(1)).getAuthData(jwtToken);
    }

    // same but return empty list because repository throws an exception
    @Test
    void findAllCustomers_ShouldReturnEmptyList_WhenRepositoryThrowsException()
            throws InvalidAuthentication, UnauthorizedActionException {
        String jwtToken = "validToken";

        UserAuthData authData = new UserAuthData("test@example.com", 1L, Role.STAFF);
        when(authService.getAuthData(jwtToken)).thenReturn(authData);
        when(customerRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        List<Customer> result = customerService.findAllCustomers(jwtToken);

        assertTrue(result.isEmpty());
    }

    // return customer by id
    @Test
    void findCustomerById_ShouldReturnCustomer_WhenExists() throws InvalidAuthentication, UnauthorizedActionException {
        String jwtToken = "validToken";
        long customerId = 1L;
        Customer expectedCustomer = new Customer();
        expectedCustomer.setId(customerId);

        UserAuthData authData = new UserAuthData("test@example.com", 1L, Role.STAFF);
        when(authService.getAuthData(jwtToken)).thenReturn(authData);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(expectedCustomer));

        Customer result = customerService.findCustomerById(jwtToken, customerId);

        assertNotNull(result);
        assertEquals(customerId, result.getId());
    }

    // find custimer by id, return null
    @Test
    void findCustomerById_ShouldReturnNull_WhenNotExists() throws InvalidAuthentication, UnauthorizedActionException {
        String jwtToken = "validToken";
        long customerId = 999L;

        UserAuthData authData = new UserAuthData("test@example.com", 1L, Role.STAFF);
        when(authService.getAuthData(jwtToken)).thenReturn(authData);
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        Customer result = customerService.findCustomerById(jwtToken, customerId);

        assertNull(result);
    }

    // update customer, admin and valid data
    @Test
    void updateCustomer_ShouldUpdateCustomer_WhenAdminAndValidData() throws InvalidAuthentication,
            UnauthorizedActionException, NonConformRequestedDataException, UnexistingEntityException {
        String jwtToken = "adminToken";
        long customerId = 1L;
        CustomerDTO updateDTO = new CustomerDTO();
        updateDTO.setCompanyName("Updated Company");
        updateDTO.setEmail("updated@company.com");

        Customer existingCustomer = new Customer();
        existingCustomer.setId(customerId);
        existingCustomer.setCompanyName("Old Company");
        existingCustomer.setEmail("old@company.com");

        UserAuthData adminAuth = new UserAuthData("admin@test.com", 1L, Role.ADMIN);
        when(authService.getAuthData(jwtToken)).thenReturn(adminAuth);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.findByEmail("updated@company.com")).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Customer result = customerService.updateCustomer(jwtToken, customerId, updateDTO);

        assertEquals("Updated Company", result.getCompanyName());
        assertEquals("updated@company.com", result.getEmail());
        verify(customerRepository, times(1)).save(existingCustomer);
    }

    // delete customer, admin perform this operation, customer exist
    @Test
    void deleteCustomer_ShouldReturnTrue_WhenAdminAndCustomerExists()
            throws InvalidAuthentication, UnauthorizedActionException, UnexistingEntityException {
        String jwtToken = "adminToken";
        long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);

        UserAuthData adminAuth = new UserAuthData("admin@test.com", 1L, Role.ADMIN);
        when(authService.getAuthData(jwtToken)).thenReturn(adminAuth);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        boolean result = customerService.deleteCustomer(jwtToken, customerId);

        assertTrue(result);
        verify(customerRepository, times(1)).delete(customer);
    }

}
