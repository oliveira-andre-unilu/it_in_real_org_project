package lu.lamtco.timelink.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String number;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String location; // optional

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimestampEntry> timestamps = new ArrayList<>();
}
