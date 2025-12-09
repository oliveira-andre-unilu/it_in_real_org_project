package lu.lamtco.timelink.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class CustomerDTOTest {

    // create customer dto with all args
    @Test
    void shouldCreateCustomerDTOWithAllFields() {
        CustomerDTO dto = new CustomerDTO();
        dto.setCompanyName("Test Company");
        dto.setTaxNumber("LU12345678");
        dto.setEmail("test@company.com");
        dto.setTel("+352123456789");
        dto.setFax("+352987654321");

        assertEquals("Test Company", dto.getCompanyName());
        assertEquals("LU12345678", dto.getTaxNumber());
        assertEquals("test@company.com", dto.getEmail());
        assertEquals("+352123456789", dto.getTel());
        assertEquals("+352987654321", dto.getFax());
    }

    // create customer dto with null values
    @Test
    void shouldHandleNullValues() {
        CustomerDTO dto = new CustomerDTO();

        assertNull(dto.getCompanyName());
        assertNull(dto.getTaxNumber());
        assertNull(dto.getEmail());
        assertNull(dto.getTel());
        assertNull(dto.getFax());
    }

    // update values
    @Test
    void shouldUpdateValues() {
        CustomerDTO dto = new CustomerDTO();
        dto.setCompanyName("Old Name");
        dto.setCompanyName("New Name");

        assertEquals("New Name", dto.getCompanyName());
    }

}
