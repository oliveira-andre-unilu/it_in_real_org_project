package lu.lamtco.timelink.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer entity in the system.
 * A customer is usually a company or client associated with projects.
 *
 * @version 0.1
 */
@Entity
@Getter
@Setter
@Schema(description = "Customer entity representing a company or client")
public class Customer {

    /**
     * Unique identifier of the customer.
     * Example: 1
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the customer", example = "1")
    private Long id;

    /**
     * Name of the company.
     * Example: ACME Corp
     */
    @Schema(description = "Company name", example = "ACME Corp")
    private String companyName;

    /**
     * Tax number of the customer.
     * Example: 123456789
     */
    @Schema(description = "Tax number of the customer", example = "123456789")
    private String taxNumber;

    /**
     * Contact email address.
     * Example: contact@acme.com
     */
    @Schema(description = "Email address", example = "contact@acme.com")
    private String email;

    /**
     * Contact telephone number.
     * Example: +352 123 456789
     */
    @Schema(description = "Telephone number", example = "+352 123 456789")
    private String tel;

    /**
     * Fax number of the customer.
     * Example: +352 123 456780
     */
    @Schema(description = "Fax number", example = "+352 123 456780")
    private String fax;

    /**
     * List of projects associated with the customer.
     * This field is hidden in API documentation.
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Schema(hidden = true)
    private List<Project> projects = new ArrayList<>();

    /** Default constructor. */
    public Customer() {
        super();
    }

    /**
     * Constructs a new customer with the provided details.
     *
     * @param companyName the company name
     * @param taxNumber   the tax number
     * @param email       the email address
     * @param tel         the telephone number
     * @param fax         the fax number
     */
    public Customer(String companyName, String taxNumber, String email, String tel, String fax) {
        super();
        this.companyName = companyName;
        this.taxNumber = taxNumber;
        this.email = email;
        this.tel = tel;
        this.fax = fax;
    }
}