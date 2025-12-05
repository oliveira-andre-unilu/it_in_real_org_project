package lu.lamtco.timelink.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Schema(description = "Customer entity representing a company or client")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the customer", example = "1")
    private Long id;

    @Schema(description = "Company name", example = "ACME Corp")
    private String companyName;

    @Schema(description = "Tax number of the customer", example = "123456789")
    private String taxNumber;

    @Schema(description = "Email address", example = "contact@acme.com")
    private String email;

    @Schema(description = "Telephone number", example = "+49 123 456789")
    private String tel;

    @Schema(description = "Fax number", example = "+49 123 456780")
    private String fax;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Schema(hidden = true)
    private List<Project> projects = new ArrayList<>();

    public Customer() {
        super();
    }

    public Customer(String companyName, String taxNumber, String email, String tel, String fax) {
        super();
        this.companyName = companyName;
        this.taxNumber = taxNumber;
        this.email = email;
        this.tel = tel;
        this.fax = fax;
    }
}