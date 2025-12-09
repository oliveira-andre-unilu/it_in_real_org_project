package lu.lamtco.timelink.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    void testCustomerFieldsAndProjects() {
        Customer customer = new Customer();
        customer.setCompanyName("TestCompany");
        customer.setTaxNumber("12345");
        customer.setEmail("test@example.com");
        customer.setTel("123456789");
        customer.setFax("987654321");

        Project project = new Project();
        project.setName("Project A");
        project.setCustomer(customer);

        customer.getProjects().add(project);

        assertThat(customer.getCompanyName()).isEqualTo("TestCompany");
        assertThat(customer.getProjects()).hasSize(1).contains(project);
        assertThat(project.getCustomer()).isEqualTo(customer);
    }
}
